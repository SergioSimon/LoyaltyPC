package loyalty.control;



import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringReader;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.GZIPInputStream;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import loyalty.bean.Cliente;
import loyalty.bean.Comercio;
import loyalty.bean.CustomTrustManager;
import loyalty.bean.InfoLogin;
import loyalty.bean.Operacion;
import loyalty.bean.Promocion;
import loyalty.bean.Sector;
import loyalty.bean.TicketCompra;
import loyalty.bean.Usuario;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.CookieStore;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.params.ClientPNames;
import org.apache.http.client.protocol.ClientContext;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.PoolingClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;
import org.xml.sax.InputSource;




public class Server{
	public static final int BAJA_SISTEMA = 0;
	public static final int SOPORTE_TECNICO= 1;
	private static String Login = "";
	private static String Pass = "";
	private static SAXBuilder builder = new SAXBuilder(false);
	//usar compresion gzip o nod
	private static boolean UseGZip = true;
	
	//tiempo de expiracion de peticion maximo
	private static int timeOut = 30;
	//http://localhost:8080/Loyalty/loyaltynfcservlet
	
	private static String ServletUrl =  "http://loyalty.gammasolutions.es:7999/LoyaltyServletPC/loyaltyservletpc";
	private static String ServletUrlSSL = "https://loyalty.gammasolutions.es:9442/LoyaltyServletPC/loyaltyservletpc";
	//"http://loyalty.gammasolutions.es:7999/LoyaltyNFCServlet/loyaltynfcservlet";
	//"https://loyalty.gammasolutions.es:9442/LoyaltyNFCServlet/loyaltynfcservlet";
	//loyalty.gammasolutions.es
	//88.2.231.140
	
	//private static String ServletUrl = "https://www.google.com:443";
	
	//public static Context myContext;
	
	
	
	
	private static HttpParams httpParams = null;
   

	private static HttpClient httpclient = new DefaultHttpClient();
	private static CookieStore cookieStore = new BasicCookieStore();
	// Create local HTTP context
	private static HttpContext localContext = new BasicHttpContext();
	
   
	
	/**
	* Permite saber el estado de la conexion actual con el servidor
	* @return true si hay conexion, false en el caso contrario
	*/
	public static boolean conexionServidor(){
		init(false);
			String [][] params = {	{"TYPE", "I"}};

			String source = postPage(ServletUrl, null, 10, params);
			if(source == null) return false;
			Element root = getRootDom(source);
			if(root == null){
				return false;
			}
			
			String status = root.getAttributeValue("STATUS");
			//System.out.println("status es "+status);
			if(status == null || !status.equalsIgnoreCase("1")){
				return false;
			}
			//System.out.println(root.getChildren());
			//System.out.println(root.getChild("ISALIVE"));
			String statusConsulta = root.getChild("ISALIVE").getAttributeValue("STATUS");
			if(statusConsulta.equalsIgnoreCase("1")){
				return true;
			}else{
				return false;
			}
	}
	
	private static void init(boolean forzar){
		if(httpParams!= null && !forzar) return;
		
		httpParams = new BasicHttpParams();
			
		cookieStore = new BasicCookieStore();
		// Create local HTTP context
		localContext = new BasicHttpContext();
		
		httpParams.setParameter(ClientPNames.ALLOW_CIRCULAR_REDIRECTS, true);
	    HttpConnectionParams.setConnectionTimeout(httpParams, timeOut*1000);
	    HttpConnectionParams.setSoTimeout(httpParams, timeOut*1000);
	    
	    httpclient = new DefaultHttpClient(httpParams);
	    localContext.setAttribute(ClientContext.COOKIE_STORE, cookieStore);
		SchemeRegistry schemeRegistry = new SchemeRegistry ();
		schemeRegistry.register (new Scheme ("http",8000, PlainSocketFactory.getSocketFactory()));
		try{
			SSLContext sslcontext = null;
			sslcontext = SSLContext.getInstance("SSL");
			TrustManager tm [] = {new CustomTrustManager()};
			sslcontext.init(null, tm, null);
			SSLSocketFactory cssl = new SSLSocketFactory(sslcontext, SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
			//CustomSSLSocketFactory cssl = new CustomSSLSocketFactory();
			schemeRegistry.register (new Scheme ("https",9443,  cssl));
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		//SingleConnectionManager 
		PoolingClientConnectionManager  cm = new PoolingClientConnectionManager (schemeRegistry);
		httpclient = new DefaultHttpClient(cm, httpParams);
	}
	
	/**
	 * Permite abrir una sesion nueva del usuario
	 * @param user Login del usuario
	 * @param pass Password para el logueo
	 * @return true si los datos son correctos y false en caso contrario o si hay error de conexion
	 * @throws NoSuchAlgorithmException 
	 * @throws KeyManagementException 
	 */
	public static InfoLogin LogIn(String user,String pass) {	
		init(true);
		InfoLogin login=null;
		String [][] params = {	{"TYPE", "L"},
								{"USER", user},
								{"PASS", pass}};
		
		String source = postPage(ServletUrl, null, 10, params);
		if(source == null) return null;
		Element root = getRootDom(source);
		if(root == null){
			return null;
		}
		
		String status = root.getAttributeValue("STATUS");
		//System.out.println("status es "+status);
		if(status == null || !status.equalsIgnoreCase("1")){
			return null;
		}
		
		Element statusConsulta = root.getChild("LOGIN");
		if(statusConsulta.getAttributeValue("STATUS").equalsIgnoreCase("1")){
			Login = user;
			Pass = pass;
			String permiso=statusConsulta.getChild("PERMISO").getText();
			String idTienda=statusConsulta.getChild("IDTIENDA").getText();
			String nombreTienda=statusConsulta.getChild("NOMBRETIENDA").getText();
			String divisaCompra=statusConsulta.getChild("DIVISACOMPRA").getText();
			int compra=Integer.parseInt(divisaCompra);
			String divisaCanjeo=statusConsulta.getChild("DIVISACANJEO").getText();
			double canjeo=Double.parseDouble(divisaCanjeo);
			String URL=statusConsulta.getChild("URLCOMERCIO").getText();
			String email=statusConsulta.getChild("EMAIL").getText();
			String idDivisaCompra=statusConsulta.getChild("IDDIVISACOMPRA").getText();
			int idCompra=Integer.parseInt(idDivisaCompra);
			String idDivisaCanjeo=statusConsulta.getChild("IDDIVISACANJEO").getText();
			int idCanjeo=Integer.parseInt(idDivisaCanjeo);
			login=new InfoLogin(user,pass,email,permiso,idTienda,nombreTienda,compra,canjeo,idCompra,idCanjeo,URL);
			return login;
		}else{
			return null;
		}
	}
	
	/**
	* Permite desloguearse de la sesion actualmente activa
	* @return true si se ha deslogueado correctamente y false en caso contrario o si hay error de conexion
	*/
	public static boolean LogOut(){	
		init(false);
		String [][] params = {	{"TYPE", "O"}};

		String source = postPage(ServletUrlSSL, null, 10, params);
		init(true);
		if(source == null) return false;
		Element root = getRootDom(source);
		if(root == null){
			return false;
		}
		
		String status = root.getAttributeValue("STATUS");
		//System.out.println("status es "+status);
		if(status == null || !status.equalsIgnoreCase("1")){
			return false;
		}
		
		String statusConsulta = root.getChild("LOGOUT").getAttributeValue("STATUS");
		if(statusConsulta.equalsIgnoreCase("1")){
			return true;
		}else{
			return false;
		}
	}
	
	
	/**
	* Usado para buscar a los clientes registrados en el sistema
	* 
	*/
	public static ArrayList<Cliente> consultaCliente(String UID_IMEI){
		 init(false);
		 ArrayList<Cliente> cliente=new ArrayList<Cliente>();
		 
		 String [][] params = {	{"TYPE", "D"},
				 				{"UIDTAG_IMEI", UID_IMEI}};
		
			String source = postPage(ServletUrlSSL, null, 3, params);
			if(source == null) return null;
			
			Element root = getRootDom(source);
			if(root == null){
				return null;
			}
			
			
			String status = root.getAttributeValue("STATUS");
			//System.out.println("status es "+status);
			if(status == null || !status.equalsIgnoreCase("1")){
				return null;
			}
			
			Element statusConsulta = root.getChild("CLIENTES");
			if(statusConsulta.getAttributeValue("STATUS").equalsIgnoreCase("1")){
				List<Element> datos = statusConsulta.getChildren("DATOS");
				for (int i = 0; i < datos.size(); i++) {
					String idCliente = datos.get(i).getChild("IDCLIENTE").getTextTrim();
					String nombreCliente = datos.get(i).getChild("NOMBRE").getTextTrim();
					String apesCliente = datos.get(i).getChild("APELLIDOS").getTextTrim();
					String dni = datos.get(i).getChild("DNI").getTextTrim();
					int puntos=Integer.parseInt(datos.get(i).getChild("PUNTOS").getTextTrim());
					String email = datos.get(i).getChild("EMAIL").getTextTrim();
					String user = datos.get(i).getChild("USER").getTextTrim();
					String pass = datos.get(i).getChild("PASS").getTextTrim();
					String fecha=datos.get(i).getChild("FECHAALTA").getTextTrim();
					String activo=datos.get(i).getChild("ACTIVO").getTextTrim();
					boolean alta=false;
					if(activo.compareTo("true")==0)
						alta=true;
					if(activo.compareTo("false")==0)
						alta=false;
					Cliente c = new Cliente(idCliente,nombreCliente,apesCliente,dni,email,user,pass,puntos,fecha,alta);
					cliente.add(c);
				}
			}
			return cliente;
	}
	
	
	/**
	* Usado para buscar a el cliente del telefono Movil NFC
	* 
	*/
	public static Cliente ConsultaClienteUser(String user){
		 init(false);
		 Cliente cliente=null;
		 
		 String [][] params = {	{"TYPE", "W"},
				 				{"USER", user}};
		
			String source = postPage(ServletUrlSSL, null, 3, params);
			if(source == null) return null;
			
			Element root = getRootDom(source);
			if(root == null){
				return null;
			}
			
			
			String status = root.getAttributeValue("STATUS");
			//System.out.println("status es "+status);
			if(status == null || !status.equalsIgnoreCase("1")){
				return null;
			}
		
			Element statusConsulta = root.getChild("DATOS");
			if(statusConsulta.getAttributeValue("STATUS").equalsIgnoreCase("1")){
				String idCliente = statusConsulta.getChild("IDCLIENTE").getTextTrim();
				String nombreCliente = statusConsulta.getChild("NOMBRE").getTextTrim();
				String apesCliente = statusConsulta.getChild("APELLIDOS").getTextTrim();
				String dni = statusConsulta.getChild("DNI").getTextTrim();
				int puntos=Integer.parseInt(statusConsulta.getChild("PUNTOS").getTextTrim());
				String email = statusConsulta.getChild("EMAIL").getTextTrim();
				String user1 = statusConsulta.getChild("USER").getTextTrim();
				String pass = statusConsulta.getChild("PASS").getTextTrim();
				String fecha=statusConsulta.getChild("FECHAALTA").getTextTrim();
				String activo=statusConsulta.getChild("ACTIVO").getTextTrim();
					boolean alta=false;
					if(activo.compareTo("true")==0)
						alta=true;
					if(activo.compareTo("false")==0)
						alta=false;
					cliente = new Cliente(idCliente,nombreCliente,apesCliente,dni,email,user1,pass,puntos,fecha,alta);
			}
			return cliente;
	}
	
	
	/**
	* Usado para buscar a los clientes registrados en el sistema
	* 
	*/
	public static Cliente editarCliente(String idCliente,String nombreCliente,String apesCliente,String dniCliente,String emailCliente,
			String usuario,String contraseña,String puntos,String fechaAlta){
		 init(false);
		 Cliente cliente=null;
		 
		 String [][] params = {	{"TYPE", "F"},
				 				{"IDCLIENTE", idCliente},
				 				{"NOMBRE",nombreCliente},
				 				{"APELLIDOS",apesCliente},
				 				{"DNI",dniCliente},
				 				{"EMAIL",emailCliente},
				 				{"USER",usuario},
				 				{"PASS",contraseña},
				 				{"PUNTOS",puntos},
				 				{"FECHAALTA",fechaAlta}};
		
			String source = postPage(ServletUrlSSL, null, 3, params);
			if(source == null) return null;
			
			Element root = getRootDom(source);
			if(root == null){
				return null;
			}
			
			
			String status = root.getAttributeValue("STATUS");
			//System.out.println("status es "+status);
			if(status == null || !status.equalsIgnoreCase("1")){
				return null;
			}
		
			Element statusConsulta = root.getChild("DATOS");
			if(statusConsulta.getAttributeValue("STATUS").equalsIgnoreCase("1")){

					idCliente = statusConsulta.getChild("IDCLIENTE").getTextTrim();
					nombreCliente = statusConsulta.getChild("NOMBRE").getTextTrim();
					apesCliente = statusConsulta.getChild("APELLIDOS").getTextTrim();
					dniCliente = statusConsulta.getChild("DNI").getTextTrim();
					emailCliente = statusConsulta.getChild("EMAIL").getTextTrim();
					usuario = statusConsulta.getChild("USER").getTextTrim();
					contraseña = statusConsulta.getChild("PASS").getTextTrim();
					puntos=statusConsulta.getChild("PUNTOS").getTextTrim();
					fechaAlta=statusConsulta.getChild("FECHAALTA").getTextTrim();
					cliente = new Cliente(idCliente,nombreCliente,apesCliente,dniCliente,emailCliente,
							usuario,contraseña,0,fechaAlta,true);
			}
			return cliente;
	}
	
	
	/**
	*Alta Cliente
	* 
	*/
	public static Cliente insertarCliente(String nombreCliente,String apesCliente,String dniCliente,String emailCliente,
			String usuario,String contraseña,String uidTag,String imei){
		 init(false);
		 Cliente cliente=null;
		 
		 String [][] params = {	{"TYPE", "E"},
				 				{"NOMBRE",nombreCliente},
				 				{"APELLIDOS",apesCliente},
				 				{"DNI",dniCliente},
				 				{"EMAIL",emailCliente},
				 				{"USER",usuario},
				 				{"PASS",contraseña},
				 				{"UIDTAG",uidTag},
				 				{"IMEI",imei}};
		
			String source = postPage(ServletUrlSSL, null, 3, params);
			if(source == null) return null;
			
			Element root = getRootDom(source);
			if(root == null){
				return null;
			}
			
			
			String status = root.getAttributeValue("STATUS");
			//System.out.println("status es "+status);
			if(status == null || !status.equalsIgnoreCase("1")){
				return null;
			}
		
			Element statusConsulta = root.getChild("DATOS");
			if(statusConsulta.getAttributeValue("STATUS").equalsIgnoreCase("1")){

					String idCliente = statusConsulta.getChild("IDCLIENTE").getTextTrim();
					nombreCliente = statusConsulta.getChild("NOMBRE").getTextTrim();
					apesCliente = statusConsulta.getChild("APELLIDOS").getTextTrim();
					dniCliente = statusConsulta.getChild("DNI").getTextTrim();
					emailCliente = statusConsulta.getChild("EMAIL").getTextTrim();
					usuario = statusConsulta.getChild("USER").getTextTrim();
					contraseña = statusConsulta.getChild("PASS").getTextTrim();
					int puntos=Integer.parseInt(statusConsulta.getChild("PUNTOS").getTextTrim());
					String fechaAlta=statusConsulta.getChild("FECHAALTA").getTextTrim();
					cliente = new Cliente(idCliente,nombreCliente,apesCliente,dniCliente,emailCliente,
							usuario,contraseña,puntos,fechaAlta,true);
			}
			return cliente;
	}
	
	
	/**
	* Baja Cliente
	* 
	*/
	public static boolean bajaCliente(String idCliente){
		 init(false);
		 String [][] params = {	{"TYPE", "Q"},
				 				{"IDCLIENTE", idCliente}};
		
			String source = postPage(ServletUrlSSL, null, 3, params);
			if(source == null) return false;
			
			Element root = getRootDom(source);
			if(root == null){
				return false;
			}
			
			
			String status = root.getAttributeValue("STATUS");
	        System.out.println("status es "+status);
	        if(!status.equalsIgnoreCase("1")){
	        	 return false;
	        }
	        String statusQuery = root.getChild("BAJACLIENTE").getAttributeValue("STATUS");
	        if(statusQuery.equalsIgnoreCase("1")){
	            	return true;
	        }else{
	            	return false;
	        }
	}
	
	
	/**
	* Usado para duplicar la tarjeta de un cliente
	* 
	*/
	public static boolean duplicarTarjeta(String idCliente,String UIDTag){
		 init(false);
		 
		 String [][] params = {	{"TYPE", "J"},
				 				{"IDCLIENTE", idCliente},
				 				{"UIDTAG",UIDTag}};
		
			String source = postPage(ServletUrlSSL, null, 3, params);
			if(source == null) return false;
			
			Element root = getRootDom(source);
			if(root == null){
				return false;
			}
			
			
			String status = root.getAttributeValue("STATUS");
	        System.out.println("status es "+status);
	        if(!status.equalsIgnoreCase("1")){
	        	 return false;
	        }
	        String statusQuery = root.getChild("DUPLICADO").getAttributeValue("STATUS");
	        if(statusQuery.equalsIgnoreCase("1")){
	            	return true;
	        }else{
	            	return false;
	        }
	}
	
	
	
	/////PROMOCIONES
	/**
	 * Busca y devuelve el listado de promociones disponibles segun ciertos criterios
	 * 
	 * @param sectorBuscar el sector de las promociones a buscar
	 * @param idTiendaBuscar tienda con las promociones a buscar
	 * @param puntosBuscar limite maximo de punto a gastar en promocion
	 * @param limit numero maximo de resultados
	 * @param offset offset de los resultados a devolver
	 * @return
	 */
	public static ArrayList<Promocion> getPromociones(String codigo,String puntos){
		init(false);
		ArrayList<Promocion> promociones = new ArrayList<Promocion>();
		String source="";
		String [][] params;
		
		if(puntos.compareTo("")==0){
			 params = new String [] []{	{"TYPE", "P"},
										{"CODIGOOPERACION", codigo}};
		}else{
			params = new String [][]{	{"TYPE", "P"},
										{"PUNTOS", puntos}};
		}

		source = postPage(ServletUrl, null, 3, params);
	
		//System.out.println("RECIBO:\n"+source);
		if(source == null) return null;
		
		Element root = getRootDom(source);
		if(root == null){
			return null;
		}
		String status = root.getAttributeValue("STATUS");
		
		//System.out.println("status es "+status);
		if(status == null || !status.equalsIgnoreCase("1")){
			return null;
		}
	
		Element statusConsulta = root.getChild("PROMOCIONES");
		if(statusConsulta.getAttributeValue("STATUS").equalsIgnoreCase("1")){
			List<Element> datos = statusConsulta.getChildren("PROMOCION");
			for (int i = 0; i < datos.size(); i++) {
				String idPromocion = datos.get(i).getChild("IDPROMOCION").getTextTrim();
				String idTienda = datos.get(i).getChild("IDTIENDA").getTextTrim();
				String nombTienda = datos.get(i).getChild("NOMBRETIENDA").getTextTrim();
				int points = Integer.parseInt(datos.get(i).getChild("PUNTOS").getTextTrim());
				String descripcion = datos.get(i).getChild("DESCRIPCION").getTextTrim();
				String fechaInicio = datos.get(i).getChild("FECHAINICIO").getTextTrim();
				String fechaFin = datos.get(i).getChild("FECHAFIN").getTextTrim();
				double importe=Double.parseDouble(datos.get(i).getChild("IMPORTE").getTextTrim());
				String activo=datos.get(i).getChild("ACTIVA").getTextTrim();
				String URL=datos.get(i).getChild("URL").getTextTrim();
				boolean Activo=false;
				if(activo.compareTo("true")==0){
					Activo=true;
				}
				Promocion o = new Promocion(idPromocion,idTienda,nombTienda,descripcion,fechaInicio,fechaFin,points,importe,URL,Activo);
				promociones.add(o);
			}
		}else{
			return null;
		}
		return promociones;
	}
	
	
	public static boolean activarPromocion(String idPromocion,String nombreTienda,String fechaInicio,String fechaFin){
		init(false);
		boolean exito=false;
		 
		String [][] params = {	{"TYPE", "Z"},
								{"IDPROMOCION",idPromocion},
								{"FECHAINICIO",fechaInicio},
								{"FECHAFIN",fechaFin}};

		String source = postPage(ServletUrl, null, 3, params);
	
		//System.out.println("RECIBO:\n"+source);
		if(source == null) return exito;
		
		Element root = getRootDom(source);
		if(root == null){
			return exito;
		}
		String status = root.getAttributeValue("STATUS");
		//System.out.println("status es "+status);
		if(status == null || !status.equalsIgnoreCase("1")){
			return exito;
		}
	
		Element statusConsulta = root.getChild("ACTIVARPROMOCION");
		if(statusConsulta.getAttributeValue("STATUS").equalsIgnoreCase("1")){
			exito=true;
		}
		return exito;
	}
	
	//Baja promocion
	public static boolean bajaPromocion(String idPromocion){
		init(false);

		boolean exito=false;
		 
		String [][] params = {	{"TYPE", "B"},
								{"IDPROMOCION",idPromocion}};

		String source = postPage(ServletUrl, null, 3, params);
	
		//System.out.println("RECIBO:\n"+source);
		if(source == null) return exito;
		
		Element root = getRootDom(source);
		if(root == null){
			return exito;
		}
		String status = root.getAttributeValue("STATUS");
		//System.out.println("status es "+status);
		if(status == null || !status.equalsIgnoreCase("1")){
			return exito;
		}
	
		Element statusConsulta = root.getChild("BAJAPROMOCION");
		if(statusConsulta.getAttributeValue("STATUS").equalsIgnoreCase("1")){

			exito=true;
		}
		return exito;
	}
	
	//Editar promocion
	public static Promocion editarPromocion(String idPromocion,String descripcion,String fechaIni,String fechaFin,
			String puntos,String importe,String URL){
		init(false);
		Promocion promocion = null;
		 
		String [][] params = {	{"TYPE", "N"},
								{"IDPROMOCION", idPromocion},
								{"DESCRIPCION",descripcion},
								{"FECHAINICIO",fechaIni},
								{"FECHAFIN",fechaFin},
								{"PUNTOS",puntos},
								{"IMPORTE",importe},
								{"URL",URL}};

		String source = postPage(ServletUrl, null, 3, params);
	
		//System.out.println("RECIBO:\n"+source);
		if(source == null) return null;
		
		Element root = getRootDom(source);
		if(root == null){
			return null;
		}
		String status = root.getAttributeValue("STATUS");
		
		//System.out.println("status es "+status);
		if(status == null || !status.equalsIgnoreCase("1")){
			return null;
		}
	
		Element statusConsulta = root.getChild("PROMOCION");
		if(statusConsulta.getAttributeValue("STATUS").equalsIgnoreCase("1")){
				String idPromo = statusConsulta.getChild("IDPROMOCION").getTextTrim();
				String idTienda = statusConsulta.getChild("IDTIENDA").getTextTrim();
				String nombTienda = statusConsulta.getChild("NOMBRETIENDA").getTextTrim();
				int points = Integer.parseInt(statusConsulta.getChild("PUNTOS").getTextTrim());
				String descrip = statusConsulta.getChild("DESCRIPCION").getTextTrim();
				String fechaInicio = statusConsulta.getChild("FECHAINICIO").getTextTrim();
				String fechaFinal = statusConsulta.getChild("FECHAFIN").getTextTrim();
				double cantidad=Double.parseDouble(statusConsulta.getChild("IMPORTE").getTextTrim());
				String activo=statusConsulta.getChild("ACTIVA").getTextTrim();
				String url=statusConsulta.getChild("URL").getTextTrim();
				boolean Activo=true;
				promocion=new Promocion(idPromo,idTienda,nombTienda,descripcion,fechaInicio,fechaFin,points,cantidad,URL,Activo);	
			}else{
				return null;
			}	
		return promocion;
	}
	
	//alta Promocion
	public static boolean altaPromocion(String descripcion,String fechaInicio,String fechaFin,String puntos,String importe,
			String url){
		init(false);
		boolean exito=false;
		 
		String [][] params = {	{"TYPE", "A"},
								{"DESCRIPCION",descripcion},
								{"FECHAINICIO",fechaInicio},
								{"FECHAFIN",fechaFin},
								{"PUNTOSNECESARIOS",puntos},
								{"IMPORTE",importe},
								{"URL",url}};

		String source = postPage(ServletUrl, null, 3, params);
	
		//System.out.println("RECIBO:\n"+source);
		if(source == null) return exito;
		
		Element root = getRootDom(source);
		if(root == null){
			return exito;
		}
		String status = root.getAttributeValue("STATUS");
		//System.out.println("status es "+status);
		if(status == null || !status.equalsIgnoreCase("1")){
			return exito;
		}
	
		Element statusConsulta = root.getChild("ALTAPROMOCION");
		if(statusConsulta.getAttributeValue("STATUS").equalsIgnoreCase("1")){
			exito=true;
		}
		return exito;
	}
	
	
	public static boolean AcogerPromocion(String idCliente,String idPromocion){
		 init(false);
		 
		 String [][] params = {	{"TYPE", "U"},
				 				{"IDCLIENTE", idCliente},
				 				{"IDPROMOCION",idPromocion}};
		
			String source = postPage(ServletUrlSSL, null, 3, params);
			if(source == null) return false;
			
			Element root = getRootDom(source);
			if(root == null){
				return false;
			}
			
			
			String status = root.getAttributeValue("STATUS");
	        System.out.println("status es "+status);
	        if(!status.equalsIgnoreCase("1")){
	        	 return false;
	        }
	        String statusQuery = root.getChild("USARPROMOCION").getAttributeValue("STATUS");
	        if(statusQuery.equalsIgnoreCase("1")){
	            	return true;
	        }else{
	            	return false;
	        }
	}
	
	
	
	
	//**
	/*Para recuperar los datos de logeo
	/*
	*/
	public static boolean recuperarLogeo(String mail){
		init(false);
		boolean exito=false;
		 
		String [][] params = {	{"TYPE", "R"},
								{"EMAIL",mail}};

		String source = postPage(ServletUrl, null, 3, params);
	
		//System.out.println("RECIBO:\n"+source);
		if(source == null) return exito;
		
		Element root = getRootDom(source);
		if(root == null){
			return exito;
		}
		String status = root.getAttributeValue("STATUS");
		//System.out.println("status es "+status);
		if(status == null || !status.equalsIgnoreCase("1")){
			return exito;
		}
	
		Element statusConsulta = root.getChild("RECUPERAR");
		if(statusConsulta.getAttributeValue("STATUS").equalsIgnoreCase("1")){
			exito=true;
		}
		return exito;
	}
	
	
		//**
		/*Para contactar con administracion y resolver incidencias
		/*
		*/
		public static boolean contactarAdministracion(String mail,String tipoSolicitud,String asunto){
			init(false);
			boolean exito=false;
			 
			String [][] params = {	{"TYPE", "T"},
									{"EMAIL",mail},
									{"TIPOSOLICITUD",tipoSolicitud},
									{"ASUNTO",asunto}};

			String source = postPage(ServletUrl, null, 3, params);
		
			//System.out.println("RECIBO:\n"+source);
			if(source == null) return exito;
			
			Element root = getRootDom(source);
			if(root == null){
				return exito;
			}
			String status = root.getAttributeValue("STATUS");
			//System.out.println("status es "+status);
			if(status == null || !status.equalsIgnoreCase("1")){
				return exito;
			}
		
			Element statusConsulta = root.getChild("CONTACTA");
			if(statusConsulta.getAttributeValue("STATUS").equalsIgnoreCase("1")){
				exito=true;
			}
			return exito;
		}
		
		
		
		
		////Para insertar una compra
		//Editar promocion
		public static TicketCompra insertarCompra(Cliente user,String idTienda,String UID,String importe,String concepto,
				String nfc){
			init(false);
			TicketCompra ticket = null;
			 
			String [][] params = {	{"TYPE", "X"},
									{"ID", UID},
									{"IMPORTE",importe},
									{"CONCEPTO",concepto},
									{"NFC",nfc}};

			String source = postPage(ServletUrl, null, 3, params);
		
			//System.out.println("RECIBO:\n"+source);
			if(source == null) return null;
			
			Element root = getRootDom(source);
			if(root == null){
				return null;
			}
			String status = root.getAttributeValue("STATUS");
			
			//System.out.println("status es "+status);
			if(status == null || !status.equalsIgnoreCase("1")){
				return null;
			}
		
			Element statusConsulta = root.getChild("TICKETCOMPRA");
			if(statusConsulta.getAttributeValue("STATUS").equalsIgnoreCase("1")){
					String idTien=statusConsulta.getChild("IDTIENDA").getTextTrim();
					int id=Integer.parseInt(idTien);
					int puntosCompra = Integer.parseInt(statusConsulta.getChild("PUNTOSCOMPRA").getTextTrim());
					int puntosTotales =  Integer.parseInt(statusConsulta.getChild("PUNTOSTOTALES").getTextTrim());
					String nombre = statusConsulta.getChild("NOMBRE").getTextTrim();
					String apellidos = statusConsulta.getChild("APELLIDOS").getTextTrim();
					String dni = statusConsulta.getChild("DNI").getTextTrim();
					String conceptoCompra = statusConsulta.getChild("CONCEPTO").getTextTrim();
					double importeCompra=Double.parseDouble(statusConsulta.getChild("IMPORTE").getTextTrim());
					String fecha=statusConsulta.getChild("FECHA").getTextTrim();
					ticket=new TicketCompra(user,id,puntosCompra,importeCompra,fecha,conceptoCompra);	
				}else{
					return null;
				}	
			return ticket;
		}
		
		
		
		//**
		/*Para la modificacion de divisas
		/*
		*/
		public static boolean modificarAcumularPuntos(String idDivisa,String euros,String puntos){
			init(false);
			boolean exito=false;
			 
			String [][] params = {	{"TYPE", "V"},
									{"IDDIVISA",idDivisa},
									{"EUROS",euros},
									{"PUNTOS",puntos}};
			String source = postPage(ServletUrl, null, 3, params);
				
			//System.out.println("RECIBO:\n"+source);
			if(source == null) return exito;
				
			Element root = getRootDom(source);
			if(root == null){
				return exito;
			}
			String status = root.getAttributeValue("STATUS");
			//System.out.println("status es "+status);
			if(status == null || !status.equalsIgnoreCase("1")){
				return exito;
			}
				
			Element statusConsulta = root.getChild("ACUMULARDIVISA");
			if(statusConsulta.getAttributeValue("STATUS").equalsIgnoreCase("1")){
				exito=true;
			}
			return exito;
		}
		
		public static boolean descontarEurosPuntos(String idDivisa,String puntos,String euros){
			init(false);
			boolean exito=false;
			 
			String [][] params = {	{"TYPE", "H"},
									{"IDDIVISA",idDivisa},
									{"PUNTOS",puntos},
									{"EUROS",euros}};
			String source = postPage(ServletUrl, null, 3, params);
				
			//System.out.println("RECIBO:\n"+source);
			if(source == null) return exito;
				
			Element root = getRootDom(source);
			if(root == null){
				return exito;
			}
			String status = root.getAttributeValue("STATUS");
			//System.out.println("status es "+status);
			if(status == null || !status.equalsIgnoreCase("1")){
				return exito;
			}
				
			Element statusConsulta = root.getChild("CANJEODIVISA");
			if(statusConsulta.getAttributeValue("STATUS").equalsIgnoreCase("1")){
				exito=true;
			}
			return exito;
		}
	
	
//	
//	/**
//	* Usado para la recuperacion de los datos del login (Login y Password)
//	* @param email direccion del correo electronico de la cuenta a recuperar la contraseña
//	* @return true si los datos se han enviado correctamente, false en caso contrario o se ha producido error de conexion
//	*/
//	public static boolean enviarEmail(String email){
//		init(false);
//		String [][] params = {	{"TYPE", "R"},
//								{"EMAIL", email}};
//
//		String source = postPage(ServletUrlSSL, null, 3, params);
//		if(source == null) return false;
//				
//		Element root = getRootDom(source);
//		if(root == null){
//			return false;
//		}
//		
//		char status = root.getAttributeValue("STATUS").charAt(0);
//		switch (status) {
//			case '0':
//				if(!LogIn(Login, Pass))return false;
//				return enviarEmail(email);
//			case '1':
//				String statusConsulta = root.getChild("RECUPERAR").getAttributeValue("STATUS");
//				if(statusConsulta.equalsIgnoreCase("1")){
//					return true;
//				}else{
//					return false;
//				}
//			case '2':
//				return false;
//			default:
//				return false;
//		}	
//	}
//	
//	/**
//	* Obtiene los datos del usuario actualmente logueado
//	* @return Usuario con los datos del usuario actualmente loqueado, null en caso de error
//	*/
//	public static Usuario getDatos(){ //datos del ususario
//		init(false);
//		String [][] params = {	{"TYPE", "D"}};
//
//			String source = postPage(ServletUrlSSL, null, 3, params);
//			if(source == null) return null;
//			
//			Element root = getRootDom(source);
//			if(root == null){
//				return null;
//			}
//			
//			char status = root.getAttributeValue("STATUS").charAt(0);
//			switch (status) {
//			case '0':
//				if(!LogIn(Login, Pass))return null;
//				return getDatos();
//			case '1':
//				Element datos = root.getChild("DATOS");
//				String statusConsulta = datos.getAttributeValue("STATUS");
//				if(statusConsulta.equalsIgnoreCase("1")){
//					String nombre = datos.getChild("NOMBRE").getTextTrim();
//					String apellidos = datos.getChild("APELLIDOS").getTextTrim();
//					String DNI = datos.getChild("DNI").getTextTrim();
//					String email = datos.getChild("EMAIL").getTextTrim();
//					String login = datos.getChild("USER").getTextTrim();
//					String pass = datos.getChild("PASS").getTextTrim();
//					int puntos = Integer.parseInt(datos.getChild("PUNTOS").getTextTrim());
//					String fechaAlta = datos.getChild("FECHAALTA").getTextTrim();
//					return new Usuario(nombre, apellidos, DNI, email, login, pass, puntos, fechaAlta);
//				}else{
//					return null;
//				}
//			case '2':
//				return null;
//			default:
//				return null;
//			}	
//	}
//	
//	
//	
//	/**
//	* Lista del historico de operaciones de compra
//	* 
//	* @param limit numero maximo de resultados
//	* @param offset posicion de resultados a devolver
//	* @return lista con los resultados obtenidos
//	*/
//	public static ArrayList<Operacion> getMovimientos(int limit, int offset){ //OPERACIONES ACUMULAR
//		init(false);
//		 ArrayList<Operacion> operaciones = new ArrayList<Operacion>();
//		 
//		 String [][] params = {	{"TYPE", "A"},
//				 				{"LIMIT", limit+""},
//				 				{"OFFSET", offset+""}};
//
//			String source = postPage(ServletUrlSSL, null, 3, params);
//			if(source == null) return null;
//			
//			Element root = getRootDom(source);
//			if(root == null){
//				return null;
//			}
//			
//			char status = root.getAttributeValue("STATUS").charAt(0);
//			switch (status) {
//			case '0':
//				if(!LogIn(Login, Pass))return null;
//				return getMovimientos(limit, offset);
//			case '1':
//				
//				Element acumular = root.getChild("ACUMULAR");
//				
//				String statusConsulta = acumular.getAttributeValue("STATUS");
//				if(statusConsulta.equalsIgnoreCase("1")){
//					List<Element> datos = acumular.getChildren("COMPRA");
//					for (int i = 0; i < datos.size(); i++) {
//						String idCompra = datos.get(i).getChild("IDCOMPRA").getTextTrim();
//						String idTienda = datos.get(i).getChild("IDTIENDA").getTextTrim();
//						String nombreTienda = datos.get(i).getChild("NOMBRETIENDA").getTextTrim();
//						int puntos = Integer.parseInt(datos.get(i).getChild("PUNTOS").getTextTrim());
//						double importe = Double.parseDouble(datos.get(i).getChild("IMPORTE").getTextTrim());
//						String fecha = datos.get(i).getChild("FECHA").getTextTrim();
//						Operacion o = new Operacion(idCompra, idTienda, nombreTienda, puntos, importe, "", fecha, false);
//						operaciones.add(o);
//					}
//					return operaciones;
//				}else{
//					return operaciones;
//				}
//			case '2':
//				return null;
//			default:
//				return null;
//			}	
//	}
//	
//	
//	
//	public static TicketCompra insertarPuntos(String userLogin, double importe, String concepto){ //OPERACIONES ACUMULAR
//		init(false);
//		TicketCompra ticket = null;
//		 System.out.println("Insertando puntos");
//		 String [][] params = {	{"TYPE", "X"},
//				 				{"ID", userLogin},
//				 				{"IMPORTE", importe+""},
//				 				{"CONCEPTO", concepto+""}};
//
//			String source = postPage(ServletUrlSSL, null, 3, params);//"<RESPONSE STATUS= \"1\" OPTYPE=\"X\"><TICKETCOMPRA STATUS=\"1\" ><PUNTOSCOMPRA>17</PUNTOSCOMPRA ><PUNTOSTOTALES>1200</PUNTOSTOTALES ><NOMBRE>Juan</NOMBRE><APELLIDOS>Jose Maria de la Vega Fernandez</APELLIDOS><DNI>8756853</DNI></TICKETCOMPRA></RESPONSE>";
//			//
//			if(source == null) return null;
//			
//			Element root = getRootDom(source);
//			if(root == null){
//				return null;
//			}
//			
//			char status = root.getAttributeValue("STATUS").charAt(0);
//			switch (status) {
//			case '0':
//				if(!LogIn(Login, Pass))return null;
//				return insertarPuntos( userLogin,  importe,  concepto);
//			case '1':
//				
//				Element TICKETCOMPRA = root.getChild("TICKETCOMPRA");
//				
//				String statusConsulta = TICKETCOMPRA.getAttributeValue("STATUS");
//				if(statusConsulta.equalsIgnoreCase("1")){
//					
//					
//					
//					int PUNTOSCOMPRA = Integer.parseInt(TICKETCOMPRA.getChild("PUNTOSCOMPRA").getTextTrim());
//					int PUNTOSTOTALES = Integer.parseInt(TICKETCOMPRA.getChild("PUNTOSTOTALES").getTextTrim());
//					String NOMBRE = TICKETCOMPRA.getChild("NOMBRE").getTextTrim();
//					String APELLIDOS = TICKETCOMPRA.getChild("APELLIDOS").getTextTrim();
//					String DNI = TICKETCOMPRA.getChild("DNI").getTextTrim();
//
//					
//					
//					Usuario user = new Usuario(NOMBRE, APELLIDOS, DNI, "", "", "", PUNTOSTOTALES, "");
//					ticket = new TicketCompra(user, PUNTOSCOMPRA);
//					return ticket;
//				}else{
//					return ticket;
//				}
//			case '2':
//				return null;
//			default:
//				return null;
//			}	
//	}
//	
//	
//	/**
//	 * Lista del historico de operaciones de canjeos
//	 * 
//	 * @param limit numero maximo de resultados
//	 * @param offset posicion de resultados a devolver
//	 * @return lista con los resultados obtenidos
//	 */
//	public static ArrayList<Operacion> getPromocionesUsadas(int limit, int offset){// operacion canjear
//		init(false);
//		ArrayList<Operacion> operaciones = new ArrayList<Operacion>();
//		 
//		String [][] params = {	{"TYPE", "S"},
//								{"LIMIT", limit+""},
//								{"OFFSET", offset+""}};
//		
//
//		String source = postPage(ServletUrlSSL, null, 3, params);
//		if(source == null) return null;
//		
//		Element root = getRootDom(source);
//		if(root == null){
//			return null;
//		}
//		
//		char status = root.getAttributeValue("STATUS").charAt(0);
//		switch (status) {
//		case '0':
//			if(!LogIn(Login, Pass))return null;
//			return getPromocionesUsadas(limit, offset);
//		case '1':
//			
//			Element canjeos = root.getChild("CANJEOS");
//			
//			String statusConsulta = canjeos.getAttributeValue("STATUS");
//			if(statusConsulta.equalsIgnoreCase("1")){
//				List<Element> datos = canjeos.getChildren("CANJEO");
//				for (int i = 0; i < datos.size(); i++) {
//					String idCompra = datos.get(i).getChild("IDOPERACION").getTextTrim();
//					String idTienda = datos.get(i).getChild("IDTIENDA").getTextTrim();
//					String nombreTienda = datos.get(i).getChild("NOMBRETIENDA").getTextTrim();
//					int puntos = Integer.parseInt(datos.get(i).getChild("PUNTOS").getTextTrim());
//					double importe = Double.parseDouble(datos.get(i).getChild("IMPORTE").getTextTrim());
//					String concepto = datos.get(i).getChild("CONCEPTO").getTextTrim();
//					String fecha = datos.get(i).getChild("FECHA").getTextTrim();
//					Operacion o = new Operacion(idCompra, idTienda, nombreTienda, puntos, importe, concepto, fecha, true);
//					operaciones.add(o);
//				}
//				return operaciones;
//			}else{
//				return operaciones;
//			}
//		case '2':
//			return null;
//		default:
//			return null;
//		}	
//	}
//	
//	
//	
//	/**
//	 * Devuelve el listado de todos los comercios registrados
//	 * 
//	 * @return lista con los resultados obtenidos
//	 */
//	public static ArrayList<Comercio> getComercios(){
//		init(false);
//		ArrayList<Comercio> comercios = new ArrayList<Comercio>();
//		 
//		String [][] params = {	{"TYPE", "C"}};
//
//		String source = postPage(ServletUrl, null, 3, params);
//		if(source == null) return null;
//		
//		Element root = getRootDom(source);
//		if(root == null){
//			return null;
//		}
//		
//		char status = root.getAttributeValue("STATUS").charAt(0);
//		switch (status) {
//		case '0':
//			if(!LogIn(Login, Pass))return null;
//			return getComercios();
//		case '1':
//			
//			Element listaComercios = root.getChild("COMERCIOS");
//			
//			String statusConsulta = listaComercios.getAttributeValue("STATUS");
//			if(statusConsulta.equalsIgnoreCase("1")){
//				List<Element> datos = listaComercios.getChildren("COMERCIO");
//				for (int i = 0; i < datos.size(); i++) {
//					String idTienda = datos.get(i).getChild("ID").getTextTrim();
//					String nombreTienda = datos.get(i).getChild("NOMBRE").getTextTrim();
//					String ubicacion = datos.get(i).getChild("UBICACION").getTextTrim();
//					String horario = datos.get(i).getChild("HORARIO").getTextTrim();
//					String descripcion = datos.get(i).getChild("DESCRIPCION").getTextTrim();
//					String sector = datos.get(i).getChild("SECTOR").getTextTrim();
//					String url = datos.get(i).getChild("URL").getTextTrim();
//					String tel = datos.get(i).getChild("TELEFONO").getTextTrim();
////					Comercio c = new Comercio(idTienda, nombreTienda, ubicacion, horario, descripcion, sector, url, tel);
////					comercios.add(c);
//				}
//				return comercios;
//			}else{
//				return comercios;
//			}
//		case '2':
//			return null;
//		default:
//			return null;
//		}	
//		
//	}
//	
//	
//	
//	
//	
//	
//	
//	/**
//	* Obtiene el listado de todos los sectores disponibles
//	* @return Lista de sectores disponibles
//	*/
//	public static ArrayList<Sector> getSectores(){
//		init(false);
//		ArrayList<Sector> sectoresArray = new ArrayList<Sector>();
//		 
//		String [][] params = {	{"TYPE", "E"}};
//
//		String source = postPage(ServletUrl, null, 3, params);
//		if(source == null) return null;
//		
//		Element root = getRootDom(source);
//		if(root == null){
//			return null;
//		}
//		
//		char status = root.getAttributeValue("STATUS").charAt(0);
//		switch (status) {
//		case '0':
//			if(!LogIn(Login, Pass))return null;
//			return getSectores();
//		case '1':
//			
//			Element sectores = root.getChild("SECTORES");
//			
//			String statusConsulta = sectores.getAttributeValue("STATUS");
//			if(statusConsulta.equalsIgnoreCase("1")){
//				List<Element> datos = sectores.getChildren("SECTOR");
//				for (int i = 0; i < datos.size(); i++) {
//					String idSector = datos.get(i).getChild("IDSECTOR").getTextTrim();
//					String tipo = datos.get(i).getChild("TIPO").getTextTrim();
//					Sector o = new Sector(idSector, tipo);
//					sectoresArray.add(o);
//				}
//				
//			}
//			return sectoresArray;
//		case '2':
//			return null;
//		default:
//			return null;
//		}
//	}
//	
//	/**
//	 * Permite contactar con el servicio si se produce alguna incidencia
//	 * 
//	 * @param tipoSolicitud el tipo de la incidencia
//	 * @param asunto El texto con la descripcion de la incidencia
//	 * @return true si todo ha ido bien, false en caso contrario
//	 */
//	public static boolean contacta(int tipoSolicitud,String asunto){
//		init(false);
//		String [][] params = {	{"TYPE", "T"},
//								{"TIPOSOLICITUD", tipoSolicitud+""},
//								{"ASUNTO", asunto}};
//
//			String source = postPage(ServletUrlSSL, null, 3, params);
//			if(source == null) return false;
//			
//			Element root = getRootDom(source);
//			if(root == null){
//				return false;
//			}
//			
//			String status = root.getAttributeValue("STATUS");
//			//System.out.println("status es "+status);
//			if(status == null || !status.equalsIgnoreCase("1")){
//				if(!LogIn(Login, Pass))return false;
//				return contacta(tipoSolicitud, asunto);
//			}
//			
//			String statusConsulta = root.getChild("CONTACTA").getAttributeValue("STATUS");
//			if(statusConsulta.equalsIgnoreCase("1")){
//				return true;
//			}else{
//				return false;
//			}
//	}
//	
//	
	
	
	
	
	private static String postPage(String url, String referer, int count, String[][] params)  {
		System.out.println("******************************** INICIO PETICION **************************** "+count);
    	String resp = "";
    	//System.out.print("*");
    	if(count ==0) return null;
    	
    	HttpResponse response3 = null;
    			try {
    				HttpPost post = new HttpPost(url);
    				
    				
    				//Aniadimos parametros de la peticion
    				//System.out.println("Parametros de la peticion:");
    	    		 List<NameValuePair> formparams = new ArrayList<NameValuePair>();
    	    		for (int i = 0; i < params.length; i++) {
    	    			System.out.print("	"+params[i][0]+" "+params[i][1]);
    	    			if(params[i][0]!= null && params[i][1]!= null && !params[i][1].equalsIgnoreCase("-1")){
    	    				System.out.println(" AÑADIDO");
    	    				formparams.add(new BasicNameValuePair(params[i][0], params[i][1]));
    	    			}else System.out.println(" NO AÑADIDO");
	    				 
					}
    	    		//System.out.println();
    	    		
    	    		 // set up httppost
    	    		 UrlEncodedFormEntity entity = new UrlEncodedFormEntity(formparams, "UTF-8");
    	    		 post.setEntity(entity);
    	    		 post.addHeader("User-Agent","Mozilla/5.0 (Windows NT 6.1; rv:8.0) Gecko/20100101 Firefox/8.0");
    	    		 post.addHeader("Accept","text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
    	    		 post.addHeader("Accept-Language","es-es,es;q=0.8,en-us;q=0.5,en;q=0.3");
    	    		 post.addHeader("Accept-Charset","ISO-8859-1,utf-8;q=0.7,*;q=0.7");
    	    		 if(UseGZip)post.addHeader("Accept-Encoding", "gzip");
    	    		 post.addHeader("Content-Type","application/x-www-form-urlencoded");
    	    			
   	    			if(referer != null)	post.addHeader("Referer", referer);
   	    			//System.out.println("ANTES DE LAPETICION");
    				response3 = httpclient.execute(post, localContext);
    				//System.out.println("DESPUES DE LAPETICION");
    			}catch (Exception e) {
					// TODO: handle exception
    				e.printStackTrace();
    				System.out.println(e.getMessage());
    				init(false);
    				return postPage(url, referer, count-1, params);
				}
    			//long contBytes = 0;
    			
    			InputStream instream = null;// = response3.getEntity().getContent();
    			try {
    				Header contentEncoding = response3.getFirstHeader("Content-Encoding");
    				if (contentEncoding != null && contentEncoding.getValue().equalsIgnoreCase("gzip")) {
    					//System.out.println("GZIP!!!!!!!!!!!!!!!!");
    					HttpEntity entity = response3.getEntity();
    					instream = new GZIPInputStream(entity.getContent());
    				    
    				    final char[] buffer = new char[0x10000];
    				    StringBuilder out = new StringBuilder();
    				    Reader in = new InputStreamReader(instream, "UTF-8");
    				    int read;
    				    do {
    				      read = in.read(buffer, 0, buffer.length);
    				      if (read>0) {
    				        out.append(buffer, 0, read);
    				      }
    				    } while (read>=0);
    				    resp = new String(buffer);
    				}else{
    					resp = EntityUtils.toString(response3.getEntity());
    				}
    				EntityUtils.consume(response3.getEntity());
    			} catch (ParseException e) {
    				// TODO Auto-generated catch block
    				e.printStackTrace();
    			} catch (IOException e) {
    				// TODO Auto-generated catch block
    				e.printStackTrace();
    			}
    			
    			//resp = StringEscapeUtils.unescapeHtml3(resp).trim();
    			resp = resp.trim();
    			System.out.println(resp);
    			System.out.println("******************************** FIN PETICION ****************************");
    			//System.out.println("COMPRIMIDO:		"+contBytes);
    			//System.out.println("SIN COMPRIMIR:	"+resp.length());
    			
    			if(resp.length()==0)return null;
    			return resp;
    	}
	
	private static Element getRootDom(String source){
		System.out.println("PARSEO");
		System.out.println(source);
		
		
		Document doc = null;
		try {
			doc = builder.build(new InputSource(new StringReader(source)));
			Element root = doc.getRootElement();
			return root;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	

	
	

	
//	public static void main(String[] args) {
//		//String test = "<?xml version=\"1.0\" encoding=\"utf-8\" ?>   <RESPONSE STATUS=\"1\" OPTYPE=\"C\"> 	<COMERCIOS STATUS=\"1\"> 	 		 		<COMERCIO> 			<ID>2</ID> 			<NOMBRE>MC DONALD'S</NOMBRE> 			<UBICACION>Planta Alta,N&uacute;mero de Local: A00020</UBICACION> 			<HORARIO>10:00 a 01:00 h</HORARIO> 			<DESCRIPCION>En La Gavia las mejores firmas de moda y complementos, servicios, hogar, ocio y restauraci&oacute;n se han unido para crear el complejo comercial m&aacute;s completo, atractivo y de mayor tama&ntilde;o de la ciudad de Madrid. En La Gavia podr&aacute;s encontrar ?De todo para todos?</DESCRIPCION> 			<SECTOR>Restauraci&oacute;n</SECTOR> 			<URL>http://88.2.231.140/Imagenes/nophoto.jpg </URL> 		</COMERCIO> 	 		 		<COMERCIO> 			<ID>1</ID> 			<NOMBRE>BURGUER KING</NOMBRE> 			<UBICACION>Planta Alta,N&uacute;mero de Local: A00007</UBICACION> 			<HORARIO>10:00 a 01:00 h</HORARIO> 			<DESCRIPCION>En La Gavia las mejores firmas de moda y complementos, servicios, hogar, ocio y restauraci&oacute;n se han unido para crear el complejo comercial m&aacute;s completo, atractivo y de mayor tama&ntilde;o de la ciudad de Madrid. En La Gavia podr&aacute;s encontrar ?De todo para todos?</DESCRIPCION> 			<SECTOR>Restauraci&oacute;n</SECTOR> 			<URL>http://88.2.231.140/Imagenes/nophoto.jpg </URL> 		</COMERCIO> 	 		 		<COMERCIO> 			<ID>3</ID> 			<NOMBRE>GAMBRINUS</NOMBRE> 			<UBICACION>Planta Alta,N&uacute;mero de Local: A00009</UBICACION> 			<HORARIO>10:00 a 01:00 h</HORARIO> 			<DESCRIPCION>En La Gavia las mejores firmas de moda y complementos, servicios, hogar, ocio y restauraci&oacute;n se han unido para crear el complejo comercial m&aacute;s completo, atractivo y de mayor tama&ntilde;o de la ciudad de Madrid. En La Gavia podr&aacute;s encontrar ?De todo para todos?</DESCRIPCION> 			<SECTOR>Restauraci&oacute;n</SECTOR> 			<URL>http://88.2.231.140/Imagenes/nophoto.jpg</URL> 		</COMERCIO> 	 		 		<COMERCIO> 			<ID>4</ID> 			<NOMBRE>FNAC</NOMBRE> 			<UBICACION>Planta Alta,N&uacute;mero de Local: A00004-B0011</UBICACION> 			<HORARIO>10:00 a 22:00 h</HORARIO> 			<DESCRIPCION>Todo el ocio, tecnolog&iacute;a y cultura lo encontrar&aacute;s en el FNAC La Gavia.  ACCESO POR PLANTA BAJA Y PLANTA ALTA</DESCRIPCION> 			<SECTOR>Diversos</SECTOR> 			<URL>http://88.2.231.140/Imagenes/nophoto.jpg</URL> 		</COMERCIO> 	 		 		<COMERCIO> 			<ID>6</ID> 			<NOMBRE>PARTY FIESTA</NOMBRE> 			<UBICACION>Planta Baja,N&uacute;mero de Local: B00121</UBICACION> 			<HORARIO>10:00 a 22:00 h</HORARIO> 			<DESCRIPCION>Somos cadena l&iacute;der en el &aacute;mbito de art&iacute;culos para fiestas, disfraces, art&iacute;culos de bromas, despedidas de solteros, temporadas.  Te ayudamos en tu evento. Ven y divi&eacute;rtete!!</DESCRIPCION> 			<SECTOR>Diversos</SECTOR> 			<URL>http://88.2.231.140/Imagenes/nophoto.jpg</URL> 		</COMERCIO> 	 		 		<COMERCIO> 			<ID>5</ID> 			<NOMBRE>GAME</NOMBRE> 			<UBICACION>Planta Baja,N&uacute;mero de Local: B001200096</UBICACION> 			<HORARIO>10:00 a 22:00 h</HORARIO> 			<DESCRIPCION>GAME tu especialista en videojuegos. En nuestras tiendas encontrar&aacute;s todas las novedades y promociones exclusivas, adem&aacute;s con la tarjeta de socio podr&aacute;s alquilar, comprar y vender seminuevos garantizados y obtener beneficios al instante.Juega mucho m&aacute;s pagando mucho menos.</DESCRIPCION> 			<SECTOR>Diversos</SECTOR> 			<URL>http://88.2.231.140/Imagenes/nophoto.jpg</URL> 		</COMERCIO> 	 		 		<COMERCIO> 			<ID>8</ID> 			<NOMBRE>MANGO HE</NOMBRE> 			<UBICACION>Planta Baja,N&uacute;mero de Local: B00109</UBICACION> 			<HORARIO>10:00 a 22:00 h</HORARIO> 			<DESCRIPCION>En La Gavia las mejores firmas de moda y complementos, servicios, hogar, ocio y restauraci&oacute;n se han unido para crear el complejo comercial m&aacute;s completo, atractivo y de mayor tama&ntilde;o de la ciudad de Madrid. En La Gavia podr&aacute;s encontrar ?De todo para todos?</DESCRIPCION> 			<SECTOR>Moda</SECTOR> 			<URL>http://88.2.231.140/Imagenes/nophoto.jpg</URL> 		</COMERCIO> 	 		 		<COMERCIO> 			<ID>9</ID> 			<NOMBRE>BLANCO</NOMBRE> 			<UBICACION>Planta Baja,N&uacute;mero de Local: B00011</UBICACION> 			<HORARIO>10:00 a 22:00 h</HORARIO> 			<DESCRIPCION>Blanco es una empresa espa&ntilde;ola especializada en el dise&ntilde;o, producci&oacute;n, distribuci&oacute;n y comercializaci&oacute;n de todo tipo de accesorios y prendas de vestir para la mujer y el hombre joven, de esp&iacute;ritu urbano y desenfadado. Cada temporada encontrar&aacute;s las &uacute;ltimas novedades en moda a precios inmejorables.</DESCRIPCION> 			<SECTOR>Moda</SECTOR> 			<URL>http://88.2.231.140/Imagenes/nophoto.jpg</URL> 		</COMERCIO> 	 		 		<COMERCIO> 			<ID>7</ID> 			<NOMBRE>JACK&amp;JONES</NOMBRE> 			<UBICACION>Planta Baja,N&uacute;mero de Local: B00111-112</UBICACION> 			<HORARIO>10:00 a 22:00 h</HORARIO> 			<DESCRIPCION>En La Gavia las mejores firmas de moda y complementos, servicios, hogar, ocio y restauraci&oacute;n se han unido para crear el complejo comercial m&aacute;s completo, atractivo y de mayor tama&ntilde;o de la ciudad de Madrid. En La Gavia podr&aacute;s encontrar ?De todo para todos?</DESCRIPCION> 			<SECTOR>Moda</SECTOR> 			<URL>http://88.2.231.140/Imagenes/nophoto.jpg</URL> 		</COMERCIO> 	 		 		<COMERCIO> 			<ID>12</ID> 			<NOMBRE>PALPELO</NOMBRE> 			<UBICACION>Planta Baja,N&uacute;mero de Local: B00124</UBICACION> 			<HORARIO>10:00 a 22:00 h</HORARIO> 			<DESCRIPCION>En La Gavia las mejores firmas de moda y complementos, servicios, hogar, ocio y restauraci&oacute;n se han unido para crear el complejo comercial m&aacute;s completo, atractivo y de mayor tama&ntilde;o de la ciudad de Madrid. En La Gavia podr&aacute;s encontrar ?De todo para todos?</DESCRIPCION> 			<SECTOR>Servicios</SECTOR> 			<URL>http://88.2.231.140/Imagenes/nophoto.jpg</URL> 		</COMERCIO> 	 		 		<COMERCIO> 			<ID>11</ID> 			<NOMBRE>UNICO</NOMBRE> 			<UBICACION>Planta Baja,N&uacute;mero de Local: B00026</UBICACION> 			<HORARIO>10:00 a 22:00 h</HORARIO> 			<DESCRIPCION>En La Gavia las mejores firmas de moda y complementos, servicios, hogar, ocio y restauraci&oacute;n se han unido para crear el complejo comercial m&aacute;s completo, atractivo y de mayor tama&ntilde;o de la ciudad de Madrid. En La Gavia podr&aacute;s encontrar ?De todo para todos?</DESCRIPCION> 			<SECTOR>Servicios</SECTOR> 			<URL>http://88.2.231.140/Imagenes/nophoto.jpg</URL> 		</COMERCIO> 	 		 		<COMERCIO> 			<ID>10</ID> 			<NOMBRE>NAILIS</NOMBRE> 			<UBICACION>Planta Baja,N&uacute;mero de Local: B00064</UBICACION> 			<HORARIO>10:00 a 22:00 h</HORARIO> 			<DESCRIPCION>En La Gavia las mejores firmas de moda y complementos, servicios, hogar, ocio y restauraci&oacute;n se han unido para crear el complejo comercial m&aacute;s completo, atractivo y de mayor tama&ntilde;o de la ciudad de Madrid. En La Gavia podr&aacute;s encontrar ?De todo para todos?</DESCRIPCION> 			<SECTOR>Servicios</SECTOR> 			<URL>http://88.2.231.140/Imagenes/nophoto.jpg</URL> 		</COMERCIO> 	 		 		<COMERCIO> 			<ID>15</ID> 			<NOMBRE>PORTICO</NOMBRE> 			<UBICACION>Planta Baja,N&uacute;mero de Local: B00038</UBICACION> 			<HORARIO>10:00 a 22:00 h</HORARIO> 			<DESCRIPCION>En La Gavia las mejores firmas de moda y complementos, servicios, hogar, ocio y restauraci&oacute;n se han unido para crear el complejo comercial m&aacute;s completo, atractivo y de mayor tama&ntilde;o de la ciudad de Madrid. En La Gavia podr&aacute;s encontrar ?De todo para todos?</DESCRIPCION> 			<SECTOR>Hogar</SECTOR> 			<URL>http://88.2.231.140/Imagenes/nophoto.jpg</URL> 		</COMERCIO> 	 		 		<COMERCIO> 			<ID>13</ID> 			<NOMBRE>A LOJA DO GATO PRETO</NOMBRE> 			<UBICACION>Planta Baja,N&uacute;mero de Local: B00022-23</UBICACION> 			<HORARIO>10:00 a 22:00 h</HORARIO> 			<DESCRIPCION>En La Gavia las mejores firmas de moda y complementos, servicios, hogar, ocio y restauraci&oacute;n se han unido para crear el complejo comercial m&aacute;s completo, atractivo y de mayor tama&ntilde;o de la ciudad de Madrid. En La Gavia podr&aacute;s encontrar ?De todo para todos?</DESCRIPCION> 			<SECTOR>Hogar</SECTOR> 			<URL>http://88.2.231.140/Imagenes/nophoto.jpg</URL> 		</COMERCIO> 	 		 		<COMERCIO> 			<ID>14</ID> 			<NOMBRE>ENMARK2</NOMBRE> 			<UBICACION>Planta Baja,N&uacute;mero de Local: B00048</UBICACION> 			<HORARIO>10:00 a 22:00 h</HORARIO> 			<DESCRIPCION>En La Gavia las mejores firmas de moda y complementos, servicios, hogar, ocio y restauraci&oacute;n se han unido para crear el complejo comercial m&aacute;s completo, atractivo y de mayor tama&ntilde;o de la ciudad de Madrid. En La Gavia podr&aacute;s encontrar ?De todo para todos?</DESCRIPCION> 			<SECTOR>Hogar</SECTOR> 			<URL>http://88.2.231.140/Imagenes/nophoto.jpg</URL> 		</COMERCIO> 	 	</COMERCIOS> </RESPONSE>";
//		//System.out.println(conexionServidor());
//		//System.out.println(LogIn("nfc", "NFC")); //OK
//		//System.out.println(LogOut());
//		//System.out.println(enviarEmail("hola@chachy.com"));
//		//System.out.println(getDatos());
//		//System.out.println(getMovimientos(20, 0));
//		//System.out.println(getPromocionesUsadas(20, 0));
//		
//		//System.out.println(conexionServidor());
//		
//		
//		LogIn("dmitry", "dmitry");
//		TicketCompra t = insertarPuntos("ddmitry", 20, "prueba");
//		
//		System.out.println(t.getUser().getApellidos());
//		System.out.println(t.getUser().getNombre());
//		System.out.println(t.getUser().getDNI());
//		System.out.println(t.getUser().getPuntos());
//		System.out.println(t.getImporte());
//		
//		/*
//		System.out.println(conexionServidor());
//		System.out.println(getDatos());
//		System.out.println(conexionServidor());
//		System.out.println(getDatos());
//		System.out.println(LogOut());
//		System.out.println(conexionServidor());
//		System.out.println(getDatos());
//		System.out.println(getPromociones(null, null, -1, 50, 0));*/
//		//String s = "JACK&JONES";
//		//s = StringEscapeUtils.escapeHtml3(s);//ahora 's' será JACK&amp;JONES
//		
//		//System.out.println(LogIn("f", "ff"));
//		//System.out.println(getPromociones(null, "3", -1, 20, 0));
//		//System.out.println(contacta("Error", "Probamos"));
//		//String params[][] = new String[0][2];
//		//System.out.println(postPage("https://google.es:443", null, 3, params));
//		//System.out.println();
//		
//		
//		
//		
//		
//		
//		/*
//		System.out.println(conexionServidor());
//		System.out.println(conexionServidor());
//		System.out.println(conexionServidor());
//		System.out.println(conexionServidor());
//		System.out.println(conexionServidor());
//		System.out.println(conexionServidor());
//		System.out.println(getComercios());
//		System.out.println(LogIn("SSG", "ssimon"));
//		boolean lon = LogIn("dmitry", "dmitry");
//		System.out.println(getComercios());
//		if(lon)return;
//		*/
//		
//		
//		
//		
//		
//		
//		/*
//		test = StringEscapeUtils.unescapeHtml3(test);
//		getRootDom(test);
//		System.out.println("*****************************************************************************************************");
//		test = StringEscapeUtils.escapeXml(test);
//		getRootDom(test);
//		System.out.println("*****************************************************************************************************");
//		
//		
//		
//		System.out.println(StringEscapeUtils.escapeXml("   ñú&ggg"));*/
//		
//	}

//	public void pruebaEstres(String[] args){
//		int numeroIteraciones = 10;
//		try{
//		String params = args[0];
//		numeroIteraciones= Integer.parseInt(params.trim());
//		}catch (Exception e) {
//			// TODO: handle exception
//			
//		}
//		
//		System.out.println("Ejecutando "+numeroIteraciones+" iteraciones...");
//		int contLogIn = 0;
//		int contLogOut = 0;
//		int contGetComercios = 0;
//		int contGetDatos = 0;
//		int contGetMovimientos = 0;
//		int contGetPromociones = 0;
//		int contGetPromocionesUsadas = 0;
//		int contGetSectores = 0;
//		int contContacta = 0;
//		int contEnviarEmail = 0;
//		int contConexion = 0;
//		
//		
//		long TcontLogIn = 0;
//		long TcontLogOut = 0;
//		long TcontGetComercios = 0;
//		long TcontGetDatos = 0;
//		long TcontGetMovimientos = 0;
//		long TcontGetPromociones = 0;
//		long TcontGetPromocionesUsadas = 0;
//		long TcontGetSectores = 0;
//		long TcontContacta = 0;
//		long TcontEnviarEmail = 0;
//		long TcontConexion = 0;
//		
//		
//		int intervalo = numeroIteraciones/10;
//		long init = System.currentTimeMillis();
//		
//		for (int i = 0; i < numeroIteraciones; i++) {
//			init = System.currentTimeMillis();
//			if(conexionServidor())contConexion++;
//			TcontConexion = TcontConexion + (System.currentTimeMillis()-init);
//			init = System.currentTimeMillis();
//			
//			if(LogIn("dmitry", "dmitry"))contLogIn++;
//			TcontLogIn = TcontLogIn + (System.currentTimeMillis()-init);
//			init = System.currentTimeMillis();
//			
//			if(getComercios() != null)contGetComercios++;
//			TcontGetComercios = TcontGetComercios + (System.currentTimeMillis()-init);
//			init = System.currentTimeMillis();
//			
//			if(getDatos() != null)contGetDatos++;
//			TcontGetDatos = TcontGetDatos + (System.currentTimeMillis()-init);
//			init = System.currentTimeMillis();
//			
//			if(getMovimientos(10, 0)!= null)contGetMovimientos++;
//			TcontGetMovimientos = TcontGetMovimientos + (System.currentTimeMillis()-init);
//			init = System.currentTimeMillis();
//			
//			if(getPromociones(null, null, -1, 100, 0)!= null)contGetPromociones++;
//			TcontGetPromociones = TcontGetPromociones + (System.currentTimeMillis()-init);
//			init = System.currentTimeMillis();
//			
//			if(getPromocionesUsadas(100, 0)!= null)contGetPromocionesUsadas++;
//			TcontGetPromocionesUsadas = TcontGetPromocionesUsadas + (System.currentTimeMillis()-init);
//			init = System.currentTimeMillis();
//			
//			if(getSectores()!= null)contGetSectores++;
//			TcontGetSectores = TcontGetSectores + (System.currentTimeMillis()-init);
//			init = System.currentTimeMillis();
//			
//			
//			if(contacta(BAJA_SISTEMA, "PRUEBA DE ESTRES"))contContacta++;
//			TcontContacta = TcontContacta + (System.currentTimeMillis()-init);
//			init = System.currentTimeMillis();
//			
//			if(enviarEmail("pruebas.diggia@gammasolutions.es"))contEnviarEmail++;
//			TcontEnviarEmail = TcontEnviarEmail + (System.currentTimeMillis()-init);
//			init = System.currentTimeMillis();
//			
//			
//		
//			if(LogOut())contLogOut++;
//			TcontLogOut = TcontLogOut + (System.currentTimeMillis()-init);
//			
//			
//			if(i%intervalo==0)System.out.print("|");
//		}
//		
//		System.out.println();
//		System.out.println("					% Error		Tiempo medio(ms)");
//		System.out.println("Conexion				"+(((double)numeroIteraciones-(double)contConexion)/(double)numeroIteraciones)*100+"		"+(TcontConexion/numeroIteraciones));
//		System.out.println("LogIn					"+(((double)numeroIteraciones-(double)contLogIn)/(double)numeroIteraciones)*100+"		"+(TcontLogIn/numeroIteraciones));
//		System.out.println("GetComercios				"+(((double)numeroIteraciones-(double)contGetComercios)/(double)numeroIteraciones)*100+"		"+(TcontGetComercios/numeroIteraciones));
//		System.out.println("GetDatos				"+(((double)numeroIteraciones-(double)contGetDatos)/(double)numeroIteraciones)*100+"		"+(TcontGetDatos/numeroIteraciones));
//		System.out.println("GetMovimientos				"+(((double)numeroIteraciones-(double)contGetMovimientos)/(double)numeroIteraciones)*100+"		"+(TcontGetMovimientos/numeroIteraciones));
//		System.out.println("GetPromociones				"+(((double)numeroIteraciones-(double)contGetPromociones)/(double)numeroIteraciones)*100+"		"+(TcontGetPromociones/numeroIteraciones));
//		System.out.println("GetPromocionesUsadas			"+(((double)numeroIteraciones-(double)contGetPromocionesUsadas)/(double)numeroIteraciones)*100+"		"+(TcontGetPromocionesUsadas/numeroIteraciones));
//		System.out.println("GetSectores				"+(((double)numeroIteraciones-(double)contGetSectores)/(double)numeroIteraciones)*100+"		"+(TcontGetSectores/numeroIteraciones));
//		System.out.println("Contacta				"+(((double)numeroIteraciones-(double)contContacta)/(double)numeroIteraciones)*100+"		"+(TcontContacta/numeroIteraciones));
//		System.out.println("EnviarEmail				"+(((double)numeroIteraciones-(double)contEnviarEmail)/(double)numeroIteraciones)*100+"		"+(TcontEnviarEmail/numeroIteraciones));
//		System.out.println("LogOut					"+(((double)numeroIteraciones-(double)contLogOut)/(double)numeroIteraciones)*100+"		"+(TcontLogOut/numeroIteraciones));
//		
//		
//		
//		
		
		
		
		
		
	//}
	
}