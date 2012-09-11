package loyalty.gclientes;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import loyalty.control.Server;



public class BajaCliente {
	
	JFrame frameBaja=new JFrame();

	public BajaCliente(String idCliente,String nombreCliente,String apellidosCliente,String DNI,String email,
			String usuario,String contraseña,String puntos,String fechaAlta,JFrame frameConsulta,JPanel panelConsulta,
			JPanel panel_Autenticacion,DefaultTableModel dtmConsulta,JTable tabla_Consulta){
		frameBaja=frameConsulta;
		panelConsulta.setVisible(true);
		eliminarCliente(idCliente,panelConsulta,dtmConsulta,tabla_Consulta);
	}
	
	void eliminarCliente(String idCliente,JPanel panelConsulta,DefaultTableModel dtmConsulta,JTable tabla_Consulta){
		 int n = JOptionPane.showConfirmDialog(panelConsulta,"¿Desea dar de baja al cliente de la BBDD?","Baja cliente",
					JOptionPane.YES_NO_OPTION);
			   if(n==0){  
				   boolean exito=Server.bajaCliente(idCliente);
				   if(exito){
					   int fila=tabla_Consulta.getRowCount();
					   for(int i=0;i<fila;i++){
						   String valor=(String) tabla_Consulta.getValueAt(i,0);
						   if(idCliente.compareTo(valor)==0){
							   tabla_Consulta.setValueAt("Baja",i,9); 
							   int v = JOptionPane.showConfirmDialog(panelConsulta,"Baja realizada correctamente","Baja realizada",
									JOptionPane.CLOSED_OPTION);
						   }
					   }
				   }else{
					   int v = JOptionPane.showConfirmDialog(panelConsulta,"Error. Imposible realizar la baja.","Error baja",
								JOptionPane.CLOSED_OPTION);
				   }
			   }
	}//fin eliminarCliente()
}
