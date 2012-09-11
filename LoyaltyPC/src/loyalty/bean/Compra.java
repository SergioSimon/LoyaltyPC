/**
 *
 * @author Alberto Miera Ruiz
 */
package loyalty.bean;

import org.apache.commons.lang3.StringEscapeUtils;

/**
 * Clase que se encarga encapsula los datos de una compra.
 * @author  Alberto Miera Ruiz
 * @since  1.0
 */
public class Compra {
     /**
     * ID de una compra.
    */
    private String idCompra = "";

    /**
     * ID del cliente que realiza la compra.
    */
	private String idCliente = "";

    /**
     * ID de la tienda donde se realiza la compra.
    */
	private String idTienda = "";

    /**
     * Puntos acumulados en la compra.
    */
	private String puntos= "";

    /**
     * Importe de la compra.
    */
	private String importe = "";

    /**
     * Fecha de la compra.
    */
	private String fecha= "";

    /**
     * Concepto de la compra.
    */
	private String concepto = "";

    /**
     * Nombre de la tienda donde se realiza la compra.
    */
	private String nombre="";
	
	
	/**
     * Constructor de la clase.
     *
     * @param idCompra ID de una compra
     * @param idCliente ID del cliente que realiza la compra
     * @param idTienda ID de la tienda donde se realiza la compra
     * @param puntos Puntos acumulados en la compra
     * @param importe Importe de la compra
     * @param fecha Fecha de la compra
     * @param concepto Concepto de la compra
     * @param nombre Nombre de la tienda donde se realiza la compra
    */
	public Compra(String idCompra,String idCliente,String idTienda,String puntos,String importe,
			String fecha,String concepto,String nombre) {

            this.idCompra=idCompra;
            this.idCliente =idCliente ;
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
     * Devuelve el ID de la compra.
     *
     * @return ID que identifica la compra
    */
    public String getIdCompra() {
		return idCompra;
	}

	/**
     * Establece el ID de la compra.
     *
     * @param idCompra ID que identifica la compra
    */
    public void setIdCompra(String idCompra) {
		this.idCompra = idCompra;
	}


    /**
     * Devuelve el ID del cliente.
     *
     * @return ID que identifica el cliente
    */
    public String getIdCliente() {
		return idCliente;
	}
	
	/**
     * Establece el ID del cliente.
     *
     * @param idCliente ID del cliente
    */
    public void setIdCliente(String idCliente) {
		this.idCliente = idCliente;
	}
	

     /**
     * Devuelve el ID de la tienda.
     *
     * @return ID de la tienda
    */
    public String getIdTienda() {
		return idTienda;
	}
	
	/**
     * Establece el ID de la tienda.
     *
     * @param idTienda ID de la tienda
    */
    public void setIdTienda(String idTienda) {
		this.idTienda = idTienda;
	}
	
	/**
     * Devuelve los puntos acumulados del cliente.
     *
     * @return Puntos acumulados del cliente
    */
    public String getPuntos() {
		return puntos;
	}
	
	/**
     * Establece los puntos del cliente.
     *
     * @param puntos Puntos del cliente
    */
    public void setPuntos(String puntos) {
		this.puntos = puntos;
	}
	
	/**
     * Devuelve el importe de la compra.
     *
     * @return Importe de la compra
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
     * @return Fecha de la compra
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
     * Devuelve el concpeto de la compra.
     *
     * @return Concepto de la compra
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
     * Devuelve el nombre de la tienda donde se realiza la compra
     *
     * @return Nombre de la tienda
    */
    public String getNombreTienda() {
		return nombre;
	}
	
	/**
     * Establece el nombre de la tienda.
     *
     * @param nombre Nombre de la tienda
    */
    public void setNombreTienda(String nombre) {
		this.nombre = nombre;
	}
}