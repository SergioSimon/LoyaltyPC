
/**
 *
 * @author Alberto Miera Ruiz
 */
package loyalty.bean;

import org.apache.commons.lang3.StringEscapeUtils;


/**
 * Clase que se encarga encapsula los datos de un cliente.
 * @author  Alberto Miera Ruiz
 * @since  1.0
 */
public class Cliente{

     /**
     * ID del cliente.
    */
    private String ID = "";

    /**
     * Nombre del cliente.
    */
	private String Nombre = "";

    /**
     * Apellidos del cliente.
    */
	private String Apellidos = "";

    /**
     * DNI del cliente.
    */
	private String DNI = "";

    /**
     * Email del cliente.
    */
	private String Email = "";

    /**
     * Usuario de logeo del cliente.
    */
	private String User = "";

    /**
     * Password de logeo del cliente.
    */
	private String Pass = "";

    /**
     * Puntos del cliente.
    */
	private int Puntos = 0;

    /**
     * Fecha de alta del cliente.
    */
    private String FechaAlta = "";

    /**
     * Indica si el cliente está activo o en baja.
    */
    private boolean activo= true;
	
	/**
     * Constructor de la clase.
     *
     * @param id Id del cliente
     * @param nombre Nombre del cliente
     * @param apellidos Apellidos del cliente
     * @param dNI DNI del cliente
     * @param email Email del cliente
     * @param user Usuario de logeo del cliente
     * @param pass Contraseña de logeo del cliente
     * @param puntos Puntos acumulados del cliente
     * @param fechaAlta Fecha de alta del cliente
     * @param activo Indicativo de cliente activo o no
    */
	public Cliente(String id,String nombre, String apellidos, String dNI, String email,
			String user, String pass, int puntos, String fechaAlta,boolean activo) {

        ID=StringEscapeUtils.escapeXml(id);
		Nombre =StringEscapeUtils.escapeXml(nombre) ;
		Apellidos = StringEscapeUtils.escapeXml(apellidos);
		DNI = StringEscapeUtils.escapeXml(dNI);
		Email = StringEscapeUtils.escapeXml(email);
		User = StringEscapeUtils.escapeXml(user);
		Pass = StringEscapeUtils.escapeXml(pass);
		Puntos = puntos;
		FechaAlta = StringEscapeUtils.escapeXml(fechaAlta);
        this.activo=activo;
	}
	

    /**
     * Devuelve el ID del cliente.
     *
     * @return ID que identifica al cliente
    */
    public String getID() {
		return ID;
	}

	/**
     * Establece el ID del cliente.
     *
     * @param id ID que identifica al cliente
    */
    public void setID(String id) {
		ID = id;
	}

    /**
     * Devuelve el nombre del cliente.
     *
     * @return Nombre del cliente
    */
    public String getNombre() {
		return Nombre;
	}
	
	/**
     * Establece el nombre del cliente.
     *
     * @param nombre Nombre del cliente
    */
    public void setNombre(String nombre) {
		Nombre = nombre;
	}
	
	/**
     * Devuelve los apellidos del cliente.
     *
     * @return Apellidos del cliente
    */
    public String getApellidos() {
		return Apellidos;
	}
	
	/**
     * Establece los apellidos del cliente.
     *
     * @param apellidos Apellidos del cliente
    */
    public void setApellidos(String apellidos) {
		Apellidos = apellidos;
	}
	
	/**
     * Devuelve el DNI del cliente.
     *
     * @return DNI del cliente
    */
    public String getDNI() {
		return DNI;
	}
	
	/**
     * Establece el DNI del cliente.
     *
     * @param dNI DNI del cliente
    */
    public void setDNI(String dNI) {
		DNI = dNI;
	}
	
	/**
     * Devuelve el email del cliente.
     *
     * @return Email del cliente
    */
    public String getEmail() {
		return Email;
	}
	
	/**
     * Establece el email del cliente.
     *
     * @param email Email del cliente
    */
    public void setEmail(String email) {
		Email = email;
	}
	
	/**
     * Devuelve el usuario de logeo del cliente.
     *
     * @return User del cliente
    */
    public String getUser() {
		return User;
	}
	
	/**
     * Establece el usuario de logeo del cliente.
     *
     * @param user Usuario de logeo del cliente
    */
    public void setUser(String user) {
		User = user;
	}
	
	/**
     * Devuelve la contraseña de logeo del cliente.
     *
     * @return Contraseña de logeo del cliente
    */
    public String getPass() {
		return Pass;
	}
	
	/**
     * Establece la contraseña de logeo del cliente.
     *
     * @param pass Contraseña de logeo del cliente
    */
    public void setPass(String pass) {
		Pass = pass;
	}
	
	/**
     * Devuelve los puntos acumulados del cliente.
     *
     * @return Puntos acumulados del cliente
    */
    public int getPuntos() {
		return Puntos;
	}
	
	/**
     * Establece los puntos acumulados del cliente.
     *
     * @param puntos Puntos acumulados del cliente
    */
    public void setPuntos(int puntos) {
		Puntos = puntos;
	}
	
	/**
     * Devuelve la fecha de alta del cliente.
     *
     * @return Fecha de alta del cliente
    */
    public String getFechaAlta() {
		return FechaAlta;
	}
	
	/**
     * Establece la fecha de alta del cliente.
     *
     * @param fechaAlta Fecha de alta del cliente
    */
    public void setFechaAlta(String fechaAlta) {
		FechaAlta = fechaAlta;
	}

    /**
     * Devuelve el estado de cliente activo o baja
     *
     * @return Booleano true si el cliente está activo o no
    */
    public boolean getActivo() {
		return activo;
	}

	/**
     * Establece el estado de un cliente acitvo/baja.
     *
     * @param fechaAlta Fecha de alta del cliente
    */
    public void setActivo(boolean activo) {
		this.activo=activo;
	}
}