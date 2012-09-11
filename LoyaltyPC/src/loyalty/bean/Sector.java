package loyalty.bean;

import org.apache.commons.lang3.StringEscapeUtils;


public class Sector{
	
	private String idSector = "";
	private String tipo = "";
	
	
	
	public Sector(String idSector, String tipo) {
		this.idSector =StringEscapeUtils.escapeXml( idSector);
		this.tipo = StringEscapeUtils.escapeXml(tipo);
		
	}

    public String getIdSector() {
		return idSector;
	}
	public void setIdSector(String idSector) {
		this.idSector = idSector;
	}

    public String getTipoSector() {
		return tipo;
	}
	public void setTipoSector(String tipo) {
		this.tipo = tipo;
	}

    
}