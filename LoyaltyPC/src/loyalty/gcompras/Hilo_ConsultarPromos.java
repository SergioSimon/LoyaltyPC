package loyalty.gcompras;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;




public class Hilo_ConsultarPromos extends Thread{
static gammasol.nfc2.emu.SCard14emu scard14;
	
	private static JLabel mano;
	private static JTextField text_imeiUID;
	private static JTextField text_puntos;
	private static DefaultTableModel dtmConsulta;
	private static JTable tabla_Consulta;
	private static JScrollPane scrollPaneConsulta;
	private static String idTienda;
	private static JPanel panelConsulta;
	private static boolean terminar;
	
	public void setTextField(JTextField text_imeiUID) {
		Hilo_ConsultarPromos.text_imeiUID=text_imeiUID;
	}

	public void setTextnombre(JTextField text_puntos) {
		Hilo_ConsultarPromos.text_puntos=text_puntos;
	}

	public void setDtm(DefaultTableModel dtmConsulta) {
		Hilo_ConsultarPromos.dtmConsulta=dtmConsulta;
	}

	public void setTabla(JTable tabla_Consulta) {
		Hilo_ConsultarPromos.tabla_Consulta=tabla_Consulta;
	}

	public void setScroll(JScrollPane scrollPaneConsulta) {
		Hilo_ConsultarPromos.scrollPaneConsulta=scrollPaneConsulta;
	}
	public void setIdTienda(String idTienda) {
		Hilo_ConsultarPromos.idTienda=idTienda;
	}
	public void setPanel(JPanel panelConsulta) {
		Hilo_ConsultarPromos.panelConsulta=panelConsulta;
	}

	public void setTerminar(boolean terminar) {
		Hilo_ConsultarPromos.terminar=terminar;
	}
	
	public void setMano(JLabel mano) {
		Hilo_ConsultarPromos.mano=mano;
	}
	
	
	//Para iniciar el hilo
	public synchronized  void run (){
		PromosCliente.ejemploTarjetasYMoviles(text_imeiUID,text_puntos,dtmConsulta,tabla_Consulta,scrollPaneConsulta,idTienda,
				panelConsulta,mano);
	    System.gc();	
	}
}
