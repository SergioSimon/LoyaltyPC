/**
 *
 * @author Alberto Miera Ruiz
 */
package loyalty.bean;


import org.apache.commons.lang3.StringEscapeUtils;

/**
 * Clase que se encarga encapsula una promoción de un comercio.
 * @author  Alberto Miera Ruiz
 * @since  1.0
 */
public class Promocion{
	
    /**
     * ID que identifica la promoción.
    */
    private String IdPromocion = "";

    /**
     * ID que identifica la tienda.
    */
	private String IdTienda = "";

    /**
     * Nombre de la tienda.
    */
	private String NombreTienda = "";

    /**
     * Descripción de la promoción.
    */
	private String Descripcion = "";

    /**
     * Fecha de inicio de la promoción.
    */
	private String FechaInicio = "";

    /**
     * Fecha de fin de la promoción.
    */
	private String FechaFin = "";

    /**
     * URL de descarga de logos.
    */
    private String URL = "";

    /**
     * Puntos necesarios para la promoción.
    */
	private int Puntos = 0;

    /**
     * Importe de la compra.
    */
    private double Importe = 0.0;

    /**
     * Promoción activa o no.
    */
    private boolean activa=false;


    /**
     * Constructor de la clase.
     *
     * @param idPromocion ID que identifica la promoción
     * @param idTienda ID que identifica la tienda
     * @param nombreTienda Nombre de la tienda
     * @param descripcion Descripción de la promoción
     * @param fechaInicio Fecha de inicio de la promoción
     * @param fechaFin Fecha de fin de la promoción
     * @param puntos Puntos necesarios para la promoción
     * @param importe Importe de la compra
     * @param URL URL de descarga de logos
     * @param activa Promoción activa o no
    */
    public Promocion(String idPromocion, String idTienda, String nombreTienda,
			String descripcion, String fechaInicio, String fechaFin, int puntos,double importe,String URL,boolean activa) {
		super();
		IdPromocion = StringEscapeUtils.escapeXml(idPromocion);
		IdTienda =StringEscapeUtils.escapeXml(idTienda) ;
		NombreTienda = StringEscapeUtils.escapeXml(nombreTienda);
		Descripcion = StringEscapeUtils.escapeXml(descripcion);
		FechaInicio = StringEscapeUtils.escapeXml(fechaInicio);
		FechaFin = StringEscapeUtils.escapeXml(fechaFin);
		Puntos = puntos;
        Importe = importe;
        this.activa=activa;
        this.URL=URL;
	}

//    public Promocion(String idPromocion, String idTienda, String nombreTienda,
//			String descripcion, String fechaInicio, String fechaFin, int puntos,int importe,boolean activa) {
//		super();
//		IdPromocion = StringEscapeUtils.escapeXml(idPromocion);
//		IdTienda =StringEscapeUtils.escapeXml(idTienda) ;
//		NombreTienda = StringEscapeUtils.escapeXml(nombreTienda);
//		Descripcion = StringEscapeUtils.escapeXml(descripcion);
//		FechaInicio = StringEscapeUtils.escapeXml(fechaInicio);
//		FechaFin = StringEscapeUtils.escapeXml(fechaFin);
//		Puntos = puntos;
//        Importe = importe;
//        this.activa=activa;
//
//	}

	 /**
     * Devuelve el ID de la promocion.
     *
     * @return ID de la promocion
    */
    public String getIdPromocion() {
		return IdPromocion;
	}

    /**
     * Establece el ID de la promoción.
     *
     * @param idPromocion ID de la promoción
    */
	public void setIdPromocion(String idPromocion) {
		IdPromocion = idPromocion;
	}

    /**
     * Devuelve el ID de la tienda.
     *
     * @return ID de la tienda
    */
	public String getIdTienda() {
		return IdTienda;
	}

    /**
     * Establece el ID de la tienda.
     *
     * @param idTienda ID de la tienda
    */
	public void setIdTienda(String idTienda) {
		IdTienda = idTienda;
	}

     /**
     * Devuelve el nombre de la tienda.
     *
     * @return Nombre de la tienda
    */
	public String getNombreTienda() {
		return NombreTienda;
	}

    /**
     * Establece el nombre de la tienda.
     *
     * @param nombreTienda Nombre de la tienda
    */
	public void setNombreTienda(String nombreTienda) {
		NombreTienda = nombreTienda;
	}

     /**
     * Devuelve la descripcion de una promoción.
     *
     * @return Descripción de una promoción
    */
	public String getDescripcion() {
		return Descripcion;
	}

    /**
     * Establece la descripcion de la promoción.
     *
     * @param descripcion Descripcion de la promoció
    */
	public void setDescripcion(String descripcion) {
		Descripcion = descripcion;
	}

    /**
     * Devuelve la fecha de inicio de una promoción.
     *
     * @return Fecha de inicio de una promoción
    */
	public String getFechaInicio() {
		return FechaInicio;
	}

    /**
     * Establece la fecha de inicio de la promoción.
     *
     * @param fechaInicio Fecha de inicio de la promoción
    */
	public void setFechaInicio(String fechaInicio) {
		FechaInicio = fechaInicio;
	}

    /**
     * Devuelve la fecha de fin de una promoción.
     *
     * @return Fecha de fin de una promoción
    */
	public String getFechaFin() {
		return FechaFin;
	}

     /**
     * Establece la fecha de fin de la promoción.
     *
     * @param fechaFin Fecha fin de la promoción
    */
	public void setFechaFin(String fechaFin) {
		FechaFin = fechaFin;
	}

    /**
     * Devuelve los puntos necesarios para acogerse a una promoción.
     *
     * @return Puntos necesarios para acogerse a una promoción
    */
	public int getPuntos() {
		return Puntos;
	}

     /**
     * Establece los puntos necesarios para acogerse a una promoción.
     *
     * @param puntos Puntos necesarios para acogerse a una promoción
    */
	public void setPuntos(int puntos) {
		Puntos = puntos;
	}

    /**
     * Devuelve el importe de la promoción.
     *
     * @return Importe de la promoción
    */
    public double getImporte() {
		return Importe;
	}

     /**
     * Establece el importe de la compra.
     *
     * @param importe Importe de la compra
    */
    public void setImporte(double importe) {
		Importe = importe;
	}

    /**
     * Devuelve si la promoción está activa o no.
     *
     * @return Si la promoción está activa o no
    */
    public boolean getActiva() {
		return activa;
	}

    /**
     * Establece si la promoción está activa o no.
     *
     * @param activa Si la promoción está activa o no
    */
	public void setActiva(boolean activa) {
		this.activa = activa;
	}

     /**
     * Devuelve la URL de descarga de logos.
     *
     * @return URL de descarga de logos
    */
    public String getURL() {
		return URL;
	}

    /**
     * Establece la URL de descarga de logos.
     *
     * @param URL URL de descarga de logos
    */
	public void setURL(String URL) {
		this.URL = URL;
	}

    /**
     * Devuelve la promoción en cadena.
     *
     * @return Promoción en cadena
    */
    @Override
	public String toString() {
		return "Promocion [IdPromocion=" + IdPromocion + ", IdTienda="
				+ IdTienda + ", NombreTienda=" + NombreTienda
				+ ", Descripcion=" + Descripcion + ", FechaInicio="
				+ FechaInicio + ", FechaFin=" + FechaFin + ", Puntos=" + Puntos
				+ ", Importe=" + Importe + "]";
	}
	
}