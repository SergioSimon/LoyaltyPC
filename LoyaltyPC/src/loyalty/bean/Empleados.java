/**
 *
 * @author Alberto Miera Ruiz
 */
package loyalty.bean;

import org.apache.commons.lang3.StringEscapeUtils;

/**
 * Clase que se encarga encapsula los datos de un empleado.
 * @author  Alberto Miera Ruiz
 * @since  1.0
 */
public class Empleados {
	/**
     * ID del trabajador.
    */
    private String IDTrabajador = "";

    /**
     * ID de la tienda.
    */
	private String IDTienda="";

    /**
     * Usuario de logeo para el empleado.
    */
	private String User = "";

    /**
     * Contraseña de logeo para el empleado.
    */
	private String Pass = "";

    /**
     * Permiso de acceso para el empleado.
    */
	private int Permiso = 0;

    /**
     * Email de contacto del empleado.
    */
    private String Email="";
	
	
	/**
     * Constructor de la clase.
     *
     * @param idTrabajador ID del trabajador
     * @param idTienda ID de la tienda
     * @param user Usuario de logeo del trabajador
     * @param pass Contraseña de logeo del trabajador
     * @param permiso Permiso de acceso del trabajador
     * @param email Email de contacto del trabajador
    */
	public Empleados(String idTrabajador,String idTienda,String user, String pass, int permiso, String email) {

        IDTrabajador=StringEscapeUtils.escapeXml(idTrabajador);
        IDTienda =StringEscapeUtils.escapeXml(idTienda) ;
		User = StringEscapeUtils.escapeXml(user);
		Pass = StringEscapeUtils.escapeXml(pass);
		Permiso = permiso;
        Email= email;
	}
	

    /**
     * Devuelve el ID del trabajador.
     *
     * @return ID que identifica al trabajador
    */
    public String getIDTrabajador() {
		return IDTrabajador;
	}

	/**
     * Establece el ID del trabajador.
     *
     * @param id ID que identifica al trabajador
    */
    public void setIDTrabajador(String id) {
		IDTrabajador = id;
	}
	
	/**
     * Devuelve el ID d la tienda.
     *
     * @return ID que identifica la tienda
    */
    public String getIDTienda() {
			return IDTienda;
	}

	/**
     * Establece el ID de la tienda.
     *
     * @param id ID que identifica la tienda
    */
    public void setIDTienda(String id) {
			IDTienda = id;
	}
	
	/**
     * Devuelve el Usuario de logeo del trabajador.
     *
     * @return Usaurio de logeo del trabajador
    */
    public String getUser() {
		return User;
	}
	
	/**
     * Establece el usuario de logeo del trabajador.
     *
     * @param user Usuario de logeo del trabajador.
    */
    public void setUser(String user) {
		User = user;
	}
	
	/**
     * Devuelve la contraseña de logeo del trabajador.
     *
     * @return Contraseña de logeo del trabajador
    */
    public String getPass() {
		return Pass;
	}
	
	/**
     * Establece la contraseña de logeo del trabajador.
     *
     * @param pass Contraseña de logeo del trabajador
    */
    public void setPass(String pass) {
		Pass = pass;
	}
	
	/**
     * Devuelve el permiso de acceso del trabajador.
     *
     * @return Permiso de acceso del trabajador
    */
    public int getPermiso() {
		return Permiso;
	}
	
	/**
     * Establece el permiso de acceso del trabajador.
     *
     * @param permiso Permiso de acceso del trabajador
    */
    public void setPermiso(int permiso) {
		Permiso = permiso;
	}

    /**
     * Devuelve el email del trabajador.
     *
     * @return Email del trabajador
    */
    public String getEmail() {
		return Email;
	}

	/**
     * Establece el email del trabajador.
     *
     * @param email Email del trabajador
    */
    public void setEmail(String email) {
		Email = email;
	}
}
