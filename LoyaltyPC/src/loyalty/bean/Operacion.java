/**
 *
 * @author Alberto Miera Ruiz
 */
package loyalty.bean;

import org.apache.commons.lang3.StringEscapeUtils;

/**
 * Clase que se encarga encapsula una operación de compra o canjeo en un comercio.
 * @author  Alberto Miera Ruiz
 * @since  1.0
 */
public class Operacion{
	
     /**
     * ID que identifica la operación.
    */
    private String IdOperacion = "";

    /**
     * ID que identifica el comercio donde se realiza la operación.
    */
	private String IdTienda = "";

    /**
     * Nombre del comercio donde se realiza la operación.
    */
	private String NombreTienda = "";

    /**
     * Puntos acumulados/canjeados en la operación.
    */
	private int Puntos = 0;

    /**
     * Importe de la compra de la operación.
    */
	private double Importe = 0.0;

    /**
     * Concepto de la operación.
    */
	private String Concepto = "";

    /**
     * Fecha de la operación.
    */
	private String Fecha = "";

    /**
     * Si la operación es de canjeo o no
    */
	private boolean isCanjeo = false;

    /**
     * Constructor de la clase.
     *
     * @param idOperacion ID que identifica la operación
     * @param idTienda ID que identifica el comercio donde se realiza la operación
     * @param nombreTienda Nombre del comercio donde se realiza la operación
     * @param puntos Puntos acumulados/canjeados en la operación
     * @param importe Importe de la compra de la operación
     * @param concepto Concepto de la operación
     * @param fecha Si la operación es de canjeo o no
    */
	public Operacion(String idOperacion, String idTienda, String nombreTienda,
			int puntos, double importe, String concepto, String fecha,
			boolean isCanjeo) {
		IdOperacion = StringEscapeUtils.escapeXml(idOperacion);
		IdTienda =StringEscapeUtils.escapeXml(idTienda) ;
		NombreTienda = StringEscapeUtils.escapeXml(nombreTienda);
		Puntos = puntos;
		Importe = importe;
		Concepto = StringEscapeUtils.escapeXml(concepto);
		Fecha =StringEscapeUtils.escapeXml(fecha) ;
		this.isCanjeo = isCanjeo;
	}


    /**
     * Devuelve el ID de la operación de canjeo o compra.
     *
     * @return ID de la operación de canjeo o compra
    */
    public String getIdOperacion() {
		return IdOperacion;
	}

	/**
     * Establece el ID de la operación de canjeo o compra.
     *
     * @param idOperacion ID de la operación de canjeo o compra
    */
    public void setIdOperacion(String idOperacion) {
		IdOperacion = idOperacion;
	}

	/**
     * Devuelve el ID de la tienda donde se realiza la operación de canjeo o compra.
     *
     * @return ID de la tienda donde se realiza la operación de canjeo o compra
    */
    public String getIdTienda() {
		return IdTienda;
	}

	/**
     * Establece el ID de la tienda donde se realiza la operación de canjeo o compra.
     *
     * @param idTienda ID de la tienda donde se realiza la operación de canjeo o compra
    */
    public void setIdTienda(String idTienda) {
		IdTienda = idTienda;
	}

	/**
     * Devuelve el nombre de la tienda donde se realiza la operación de canjeo o compra.
     *
     * @return Nombre de la tienda donde se realiza la operación de canjeo o compra
    */
    public String getNombreTienda() {
		return NombreTienda;
	}

	/**
     * Establece el nombre de la tienda donde se realiza la operación de canjeo o compra.
     *
     * @param nombreTienda Nombre de la tienda donde se realiza la operación de canjeo o compra
    */
    public void setNombreTienda(String nombreTienda) {
		NombreTienda = nombreTienda;
	}

	/**
     * Devuelve los puntos de la operación de canjeo o compra.
     *
     * @return Puntos de la operación de canjeo o compra
    */
    public int getPuntos() {
		return Puntos;
	}

	/**
     * Establece los puntos de la operación de canjeo o compra.
     *
     * @param puntos Puntos de la operación de canjeo o compra
    */
    public void setPuntos(int puntos) {
		Puntos = puntos;
	}

	/**
     * Devuelve el importe de la operación de canjeo o compra.
     *
     * @return Importe de la operación de canjeo o compra
    */
    public double getImporte() {
		return Importe;
	}

	/**
     * Establece el importe de la operación de canjeo o compra.
     *
     * @param importe Importe de la operación de canjeo o compra
    */
    public void setImporte(double importe) {
		Importe = importe;
	}

	/**
     * Devuelve el concepto de la operación de canjeo o compra.
     *
     * @return Concepto de la operación de canjeo o compra
    */
    public String getConcepto() {
		return Concepto;
	}

	/**
     * Establece el concepto de la operación de canjeo o compra.
     *
     * @param concepto Concepto de la operación de canjeo o compra
    */
    public void setConcepto(String concepto) {
		Concepto = concepto;
	}

	/**
     * Devuelve la fecha de la operación de canjeo o compra.
     *
     * @return Fecha de la operación de canjeo o compra
    */
    public String getFecha() {
		return Fecha;
	}

	/**
     * Establece la fecha de la operación de canjeo o compra.
     *
     * @param fecha Fecha de la operación de canjeo o compra
    */
    public void setFecha(String fecha) {
		Fecha = fecha;
	}

	/**
     * Devuelve si la operación es de canjeo o compra.
     *
     * @return Si la operación es de canjeo o compra
    */
    public boolean isCanjeo() {
		return isCanjeo;
	}

	/**
     * Establece si la operación es de canjeo o compra.
     *
     * @param isCanjeo Si la operación es de canjeo o compra
    */
    public void setCanjeo(boolean isCanjeo) {
		this.isCanjeo = isCanjeo;
	}
	
	
	
	
	
}