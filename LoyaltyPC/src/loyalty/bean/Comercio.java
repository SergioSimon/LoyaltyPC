package loyalty.bean;

import org.apache.commons.lang3.StringEscapeUtils;












public class Comercio{
	
	private String IdTienda = "";
	private String Nombre = "";
	private String Ubucacion = "";
	private String Horario = "";
	private String Descripcion = "";
	private String Sector = "";
	private String URL = "";
    private String idDivisaAcumular = "";
	private String idDivisaCanjeo = "";
	private String telefono = "";
	
	public Comercio(String idTienda, String nombre, String ubucacion,
			String horario, String descripcion, String sector, String iddivisaacumular,String iddivisacanjeo, String uRL,String Telefono) {
		IdTienda = StringEscapeUtils.escapeXml(idTienda);
		Nombre = StringEscapeUtils.escapeXml(nombre);
		Ubucacion =StringEscapeUtils.escapeXml(ubucacion) ;
		Horario = StringEscapeUtils.escapeXml(horario);
		Descripcion = StringEscapeUtils.escapeXml(descripcion);
		Sector = StringEscapeUtils.escapeXml(sector);
		URL = StringEscapeUtils.escapeXml(uRL);
        idDivisaAcumular=StringEscapeUtils.escapeXml(iddivisaacumular);
        idDivisaCanjeo=StringEscapeUtils.escapeXml(iddivisacanjeo);
        telefono=StringEscapeUtils.escapeXml(Telefono);
	}

    public String getTelefono() {
		return telefono;
	}
	public void setTelefono(String Telefono) {
		telefono=Telefono;
	}

    public String getIdDivisaAcumular() {
		return idDivisaAcumular;
	}
	public void setIdDivisaAcumular(String iddivisaacumular) {
		idDivisaAcumular = iddivisaacumular;
	}

    public String getIdDivisaCanjeo() {
		return idDivisaCanjeo;
	}
	public void setIdDivisaCanjeo(String iddivisacanjeo) {
		idDivisaCanjeo = iddivisacanjeo;
	}

	public String getIdTienda() {
		return IdTienda;
	}
	public void setIdTienda(String idTienda) {
		IdTienda = idTienda;
	}
	public String getNombre() {
		return Nombre;
	}
	public void setNombre(String nombre) {
		Nombre = nombre;
	}
	public String getUbicacion() {
		return Ubucacion;
	}
	public void setUbicacion(String ubucacion) {
		Ubucacion = ubucacion;
	}
	public String getHorario() {
		return Horario;
	}
	public void setHorario(String horario) {
		Horario = horario;
	}
	public String getDescripcion() {
		return Descripcion;
	}
	public void setDescripcion(String decripcion) {
		Descripcion = decripcion;
	}
	public String getSector() {
		return Sector;
	}
	public void setSector(String sector) {
		Sector = sector;
	}
	public String getURL() {
		return URL;
	}
	public void setURL(String uRL) {
		URL = uRL;
	}
	
	
	
	
	
	
}