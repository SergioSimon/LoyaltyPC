/**
 *
 * @author Alberto Miera Ruiz
 */
package loyalty.bean;

/**
 * Clase que se encarga encapsular una compra.
 * @author  Alberto Miera Ruiz
 * @since  1.0
 */
public class TicketCompra {

	/**
     * Usuario que realiza la compra.
    */
    private Cliente user;

    /**
     * Importe de la compra.
    */
	private double importe;

    /**
     * Concepto de la compra.
    */
    private String concepto;

    /**
     * Fecha de la compra.
    */
    private String fecha = "";

    /**
     * Puntos acumulados por usuario en la compra.
    */
    private int puntosAcumulados;

    /**
     * ID de la tienda donde se realiza la compra.
    */
    private int idTienda;

    /**
     * Constructor de la clase.
     *
     * @param user Usuario que realiza la compra
     * @param idTienda ID de la tienda donde se realiza la compra
     * @param puntosAcumulados Puntos acumulados en la compra
     * @param importe Importe de la compra
     * @param fecha Fecha de la compra
     * @param concepto Concepto de la compra
    */
    public TicketCompra(Cliente user,  int idTienda, int puntosAcumulados,double importe, String fecha, String concepto) {
		super();
		this.user = user;
        this.idTienda =idTienda;
        this.puntosAcumulados=puntosAcumulados;
		this.importe = importe;
        this.fecha=fecha;
        this.concepto=concepto;
	}


     /**
     * Devuelve usuario que realiza la compra.
     *
     * @return Usuario que realiza la compra
    */
	public Cliente getUser() {
		return user;
	}

     /**
     * Establece el usuario que realiza la compra.
     *
     * @param user Usuario que realiza la compra
    */
	public void setUser(Cliente user) {
		this.user = user;
	}

     /**
     * Devuelve el importe de la compra.
     *
     * @return Importe de la compra
    */
	public double getImporte() {
		return importe;
	}

     /**
     * Establece el importe de la compra.
     *
     * @param importe Importe de la compra
    */
	public void setImporte(double importe) {
		this.importe = importe;
	}
	
     /**
     * Devuelve el concepto de la compra.
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
     * Devuelve los puntos acumulados en la compra.
     *
     * @return Puntos acumulados en la compra
    */
    public int getPuntosAcumulados() {
		return puntosAcumulados;
	}

    /**
     * Establece los puntos acumulados en la compra.
     *
     * @param puntosAcumulados Puntos acumulados en la compra
    */
	public void setPuntosAcumulados( int puntosAcumulados) {
		this.puntosAcumulados = puntosAcumulados;
    }

    /**
     * Devuelve el ID de la tienda donde se realiza la compra.
     *
     * @return ID de la tienda donde se realiza la compra
    */
    public int getIdTienda() {
		return idTienda;
	}

    /**
     * Establece el ID de la tienda donde se realiza la compra.
     *
     * @param idTienda ID de la tienda donde se realiza la compra
    */
	public void setIdTienda( int idTienda) {
		this.idTienda = idTienda;
    }

    /**
     * Devuelve la compra en formato cadena.
     *
     * @return Compra en formato cadena
    */
	@Override
	public String toString() {
		return "TicketCompra [user=" + user + ", importe=" + importe + "]";
	}

}
