/**
 *
 * @author Alberto Miera Ruiz
 */
package loyalty.bean;

import org.apache.commons.lang3.StringEscapeUtils;


/**
 * Clase que se encarga del canjeo de puntos cuando realiza una compra.
 * @author  Alberto Miera Ruiz
 * @since  1.0
 */
public class Canjeo {
    
    /**
     * ID de la operación de canjeo.
    */
    private String idCanjeo = "";

    /**
     * ID del cliente.
    */
	private String idCliente = "";

    /**
     * ID de la tienda.
    */
	private String idTienda = "";

    /**
     * Puntos del cliente.
    */
	private String puntos= "";

    /**
     * Importe de la compra involucrada en el canjeo.
    */
	private String importe = "";

    /**
     * Fecha de la operación.
    */
	private String fecha= "";

    /**
     * Concepto de la compra.
    */
	private String concepto = "";

    /**
     * Nombre de la tienda.
    */
	private String nombre="";
	
	
	 /**
     * Constructor de la clase.
     *
     * @param idCanjeo ID de la operación de canjeo
     * @param idCliente ID del cliente
     * @param idTienda ID de la tienda
     * @param puntos Puntos del cliente
     * @param importe Importe de la compra involucrada en el canjeo
     * @param concepto Concepto de la compra
     * @param fecha Fecha de la operación
     * @param nombre Nombre de la tienda
    */
	public Canjeo(String idCanjeo,String idCliente,String idTienda,String puntos,String importe,
			String concepto,String fecha,String nombre) {


        this.idCanjeo=idCanjeo;
        this.idCliente =idCliente;
        this.idTienda = idTienda;
        this.puntos = puntos;
        this.importe = importe;
        this.fecha = fecha;
        this.concepto = concepto;
        this.nombre = nombre;



//        idCompra=StringEscapeUtils.escapeXml(idCompra);
//        idCliente =StringEscapeUtils.escapeXml(idCliente) ;
//        idTienda = StringEscapeUtils.escapeXml(idTienda);
//        puntos = StringEscapeUtils.escapeXml(puntos);
//        importe = StringEscapeUtils.escapeXml(importe);
//        fecha = StringEscapeUtils.escapeXml(fecha);
//        concepto = StringEscapeUtils.escapeXml(concepto);
//        nombre = StringEscapeUtils.escapeXml(nombre);
	}
	

     /**
     * Devuelve el ID de la operación de canjeo.
     *
     * @return ID de la operación de canjeo
    */
    public String getIdCanjeo() {
		return idCanjeo;
	}

	/**
     * Establece el ID de la operación de canjeo.
     *
     * @param idCanjeo ID de la operación de canjeo
    */
    public void setIdCanjeo(String idCanjeo) {
		this.idCanjeo = idCanjeo;
	}


    /**
     * Devuelve el ID del cliente que realiza la compra.
     *
     * @return ID del cliente que realiza la compra
    */
    public String getIdCliente() {
		return idCliente;
	}
	
	/**
     * Establece el ID del cliente que realiza la compra
     *
     * @param idCliente ID del cliente que realiza la compra
    */
    public void setIdCliente(String idCliente) {
		this.idCliente = idCliente;
	}
	
	 /**
     * Devuelve el ID de la tienda donde se realiza la compra.
     *
     * @return ID de la tienda donde se realiza la compra
    */
    public String getIdTienda() {
		return idTienda;
	}
	
	/**
     * Establece el ID de la tienda donde se realiza la compra.
     *
     * @param idTienda ID de la tienda donde se realiza la compra
    */
    public void setIdTienda(String idTienda) {
		this.idTienda = idTienda;
	}
	
	/**
     * Devuelve los puntos del cliente que realiza la compra.
     *
     * @return  Puntos del cliente que realiza la compra
    */
    public String getPuntos() {
		return puntos;
	}
	
	/**
     * Establece los puntos del cliente que realiza la compra.
     *
     * @param puntos Puntos del cliente que realiza la compra
    */
    public void setPuntos(String puntos) {
		this.puntos = puntos;
	}
	
	/**
     * Devuelve el importe de la compra.
     *
     * @return  Importe de la compra
    */
    public String getImporte() {
		return importe;
	}
	
	/**
     * Establece el importe de la compra.
     *
     * @param importe Importe de la compra
    */
    public void setImporte(String importe) {
		this.importe = importe;
	}
	
	/**
     * Devuelve la fecha de la compra.
     *
     * @return  Fecha de la compra
    */
    public String getFecha() {
		return fecha;
	}
	
	/**
     * Establece la fecha de la compra.
     *
     * @param fecha Fecha de la compra
    */
    public void setFecha(String fecha) {
		this.fecha = fecha;
	}
	
	/**
     * Devuelve el concepto de la compra.
     *
     * @return  Concepto de la compra
    */
    public String getConcepto() {
		return concepto;
	}
	
	/**
     * Establece el concepto de la compra.
     *
     * @param concepto Concepto de la compra
    */
    public void setConcepto(String concepto) {
		this.concepto = concepto;
	}
	
	/**
     * Devuelve el nombre de la tienda donde se realiza la compra.
     *
     * @return  Nombre de la tienda donde se realiza la compra
    */
    public String getNombre() {
		return nombre;
	}
	
	/**
     * Establece el nombre de la tienda donde se realiza la compra.
     *
     * @param nombre Nombre de la tienda donde se realiza la compra
    */
    public void setNombre(String nombre) {
		this.nombre = nombre;
	}
}