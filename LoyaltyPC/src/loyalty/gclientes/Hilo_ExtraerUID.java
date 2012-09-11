package loyalty.gclientes;

import java.awt.TextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import loyalty.bean.TicketCompra;
import loyalty.control.Server;


public class Hilo_ExtraerUID extends Thread{
	
	static gammasol.nfc2.emu.SCard14emu scard14;
	
	
	private static JTextField text_uidTarjeta;
	private static JPanel panelConsulta;
	private static JPanel panelDuplicarTarjeta;
	private static String idCliente="";
	
	public void setTextField(JTextField text_uidTarjeta){
		Hilo_ExtraerUID.text_uidTarjeta=text_uidTarjeta;
	}
	
	public void setPanel(JPanel panelConsulta) {
		Hilo_ExtraerUID.panelConsulta=panelConsulta;
	}
	
	public void setPanelDuplicar(JPanel panelDuplicarTarjeta) {
		Hilo_ExtraerUID.panelDuplicarTarjeta=panelDuplicarTarjeta;		
	}
	
	public void setIdCliente(String idCliente) {
		Hilo_ExtraerUID.idCliente=idCliente;
	}
	
	
	//Para iniciar el hilo
	public synchronized  void run (){
	    TarjetasMoviles.ejemploTarjetasYMoviles(text_uidTarjeta,panelConsulta,panelDuplicarTarjeta,idCliente);
	    System.gc();	
    }
	
}
