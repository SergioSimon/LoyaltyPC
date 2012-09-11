/**
 *
 * @author Alberto Miera Ruiz
 */
package loyalty.bean;


/**
 * Clase que se encarga encapsula los datos de logeo de un empleado.
 * @author  Alberto Miera Ruiz
 * @since  1.0
 */
public class InfoLogin {
	
    /**
     * Permiso de acceso del trabajador.
    */
    String permiso="";

    /**
     * ID de la tienda donde trabaja el empleado.
    */
	String idTienda="";

    /**
     * Nombre de la tienda donde trabaja el empleado.
    */
    String nombreTienda="";

    /**
     * URL de logo de la tienda donde trabaja el empleado.
    */
    String URL="";

    /**
     * Usuario de acceso del empleado.
    */
    String user="";

    /**
     * Contraseña de acceso del empleado.
    */
    String pass="";

    /**
     * Email del empleado.
    */
    String email="";

    /**
     * Factor de cambio euros por puntos.
    */
    int factorDivisaCompra=0;

    /**
     * Factor de cambio puntos por euros.
    */
    double factorDivisaCanjeo=0;

     /**
     *ID de cambio euros por puntos.
    */
    int IDDivisaCompra=0;

     /**
     *ID de cambio puntos por euros.
    */
    int IDDivisaCanjeo=0;
 
    
    /**
     * Constructor de la clase.
     *
     * @param user Usuario de logeo del empleado
     * @param pass Contraseña de logeo del empleado
     * @param email Email de contacto del empleado
     * @param permiso Permiso de acceso del empleado
     * @param idTienda ID de la tienda donde trabaja el empleado
     * @param nombreTienda Nombre de la tienda donde trabaja el empleado
     * @param factorDivisaCompra Factor de cambio euros por puntos
     * @param factorDivisaCanjeo Factor de cambio puntos por euros
     * @param iDDivisaCompra ID de cambio euros por puntos
     * @param iDDivisaCanjeo ID de cambio puntos por euros
     * @param URL URL de descarga de imágenes del comercio
    */
    public InfoLogin(String user, String pass,String email,String permiso,String idTienda,String nombreTienda,int factorDivisaCompra,
    		double factorDivisaCanjeo,int iDDivisaCompra,int iDDivisaCanjeo,String URL){
        this.user = user;
        this.pass =  pass;
        this.email=email;
        this.permiso = permiso;
        this.idTienda =  idTienda; 
        this.nombreTienda = nombreTienda;
        this.factorDivisaCompra=factorDivisaCompra;
        this.factorDivisaCanjeo=factorDivisaCanjeo;
        this.IDDivisaCompra=iDDivisaCompra;
        this.IDDivisaCanjeo=iDDivisaCanjeo;
        this.URL =  URL;
    }
   
    /**
     * Devuelve el usuario de logeo del trabajador.
     *
     * @return Usuario de logeo del trabajador
    */
    public String getUser(){
        return user;
    }

     /**
     * Devuelve la contraseña de logeo del trabajador.
     *
     * @return Contraseña de logeo del trabajador
    */
    public String getPass(){
        return pass;
    }

     /**
     * Devuelve el email de contacto del trabajador.
     *
     * @return Email de contacto del trabajador
    */
    public String getEmail(){
        return email;
    }

    /**
     * Devuelve permiso de acceso del trabajador.
     *
     * @return Ppermiso de acceso del trabajador
    */
    public String getPermiso(){
        return permiso;
    }

    /**
     * Devuelve ID de la tienda donde trabaja el empleado.
     *
     * @return ID de la tienda donde trabaja el empleado
    */
    public String getIdTienda(){
        return idTienda;
    }

    /**
     * Devuelve nombre de la tienda donde trabaja el empleado.
     *
     * @return Nombre de la tienda donde trabaja el empleado
    */
    public String getNombreTienda(){
        return nombreTienda;
    }

    /**
     * Devuelve el factor de cambio euros por puntos en compras.
     *
     * @return Factor de cambio euros por puntos en comrpas
    */
    public int getFactorDivisaCompra(){
        return factorDivisaCompra;
    }

    /**
     * Devuelve el factor de cambio puntos por euros en canjeos.
     *
     * @return Factor de cambio euros por puntos en canjeos
    */
    public double getFactorDivisaCanjeo(){
        return factorDivisaCanjeo;
    }

     /**
     * Devuelve el ID de cambio euros por puntos en compras.
     *
     * @return ID de cambio euros por puntos en compras
    */
    public int getIDDivisaCompra(){
        return IDDivisaCompra;
    }

    /**
     * Devuelve el ID de cambio puntos por euros en canjeos.
     *
     * @return ID de cambio euros por puntos en canjeos
    */
    public int getIDDivisaCanjeo(){
        return IDDivisaCanjeo;
    }

    /**
     * Devuelve la URL de descarga de logos.
     *
     * @return URL de descarga de logos
    */
    public String getURL(){
        return URL;
    }
}