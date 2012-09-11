package loyalty.bean;
/*
 * MPontiNet: Aplicación de información académica de la UPSA
 * Autores: José Agustín Rodríguez Torres y Rodrigo Pérez vázquez
 * Tutor: Roberto Berjón Gallinas
 * Trabajo fin de grado - Grado en Ingeniería Informática
 * Universidad Pontificia de Salamanca
 * Año: 2011
 */



import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.X509TrustManager;

/**
 * La clase PontiNetTrustManager nos dará por válido el certificado sin firmar 
 * de nuestro servidor de aplicaciones.
 * @author Agustín Rodríguez Torres y Rodrigo Pérez Vázquez. Tutor: Roberto Berjón Gallinas
 */
public class CustomTrustManager implements  X509TrustManager{

	/* (non-Javadoc)
	 * @see javax.net.ssl.X509TrustManager#checkClientTrusted(java.security.cert.X509Certificate[], java.lang.String)
	 */
	public void checkClientTrusted(X509Certificate[] chain, String authType)throws CertificateException {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see javax.net.ssl.X509TrustManager#checkServerTrusted(java.security.cert.X509Certificate[], java.lang.String)
	 */
	public void checkServerTrusted(X509Certificate[] chain, String authType)throws CertificateException {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see javax.net.ssl.X509TrustManager#getAcceptedIssuers()
	 */
	public X509Certificate[] getAcceptedIssuers() {
		// TODO Auto-generated method stub
		return null;
	}

}
