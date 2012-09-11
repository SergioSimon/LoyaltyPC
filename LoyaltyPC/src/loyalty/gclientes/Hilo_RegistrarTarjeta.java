package loyalty.gclientes;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class Hilo_RegistrarTarjeta extends Thread{
	static gammasol.nfc2.emu.SCard14emu scard14;
	
	
	private static JTextField text_imeiUID;
	private static JTextField text_nombre;
	private static JTextField text_apellidos;
	private static JTextField text_dni;
	private static JTextField text_email;
	private static JTextField text_user;
	private static JTextField text_pass;
	private static JTextField text_puntos;
	private static JTextField text_fechaAlta;
	private static JPanel panelAlta;
	private static JLabel lb_mensaje=new JLabel("\"ACERQUE LA TARJETA NFC AL LECTOR\"");

	
	public void setTextField(JTextField text_imeiUID){
		Hilo_RegistrarTarjeta.text_imeiUID=text_imeiUID;
	}
	
	public void setPanel(JPanel panelAlta) {
		Hilo_RegistrarTarjeta.panelAlta=panelAlta;
	}
	
	public void setLabel(JLabel lb_mensaje) {
		Hilo_RegistrarTarjeta.lb_mensaje=lb_mensaje;
	}
	public void setTextnombre(JTextField text_nombre) {
		Hilo_RegistrarTarjeta.text_nombre=text_nombre;
	}

	public void setTextapes(JTextField text_apellidos) {
		Hilo_RegistrarTarjeta.text_apellidos=text_apellidos;
	}

	public void setTextdni(JTextField text_dni) {
		Hilo_RegistrarTarjeta.text_dni=text_dni;
	}

	public void setTextemail(JTextField text_email) {
		Hilo_RegistrarTarjeta.text_email=text_email;
	}

	public void setTextuser(JTextField text_user) {
		Hilo_RegistrarTarjeta.text_user=text_user;
	}

	public void setTextpas(JPasswordField text_pass) {
		Hilo_RegistrarTarjeta.text_pass=text_pass;
	}

	public void setTextpuntos(JTextField text_puntos) {
		Hilo_RegistrarTarjeta.text_puntos=text_puntos;
	}

	public void setTextfecha(JTextField text_fechaAlta) {
		Hilo_RegistrarTarjeta.text_fechaAlta=text_fechaAlta;
	}
	
	
	//Para iniciar el hilo
	public synchronized  void run (){
	    Tarjetas.ejemploTarjetasYMoviles(text_imeiUID,panelAlta,lb_mensaje,text_nombre,text_apellidos,text_dni,text_email,
	    		text_user,text_pass,text_puntos,text_fechaAlta);
	    System.gc();	
	}
}
