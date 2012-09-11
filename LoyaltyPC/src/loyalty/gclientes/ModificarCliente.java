package loyalty.gclientes;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.AbstractButton;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import loyalty.bean.Cliente;
import loyalty.control.Server;


public class ModificarCliente {
	
	JFrame frameModificarCliente;
	JPanel panelModificarCliente=new JPanel();
	
	ImageIcon iconoSalir = new ImageIcon(getClass().getResource("/loyalty/images/exit.png")); 
	ImageIcon iconoAtras = new ImageIcon(getClass().getResource("/loyalty/images/btnVolver.png"));
	 
	Icon imagenCabecera = new ImageIcon(getClass().getResource("/loyalty/images/cabecera.png"));
	ImageIcon iconoModificar = new ImageIcon(getClass().getResource("/loyalty/images/modificarCliente.png"));
	
	JButton botonAtras=new JButton("", iconoAtras);
	JButton botonSalir=new JButton("", iconoSalir);
	
	JLabel fondo_imagen=new JLabel();
	JLabel lb_cabecera=new JLabel(imagenCabecera);
	JLabel titulo = new JLabel("MODIFICAR CLIENTE");
	
	JLabel lb_idCliente=new JLabel("·Identificador:");
	JLabel lb_nombreCliente=new JLabel("·Nombre:");
	JLabel lb_apesCliente=new JLabel("·Apellidos:");
	JLabel lb_DNICliente=new JLabel("·D.N.I.:");
	JLabel lb_Email=new JLabel("·Email:");
	JLabel lb_Usuario=new JLabel("·Usuario:");
	JLabel lb_Contraseña=new JLabel("·Contraseña:");
	JLabel lb_puntos=new JLabel("·Puntos:");
	JLabel lb_fechaAlta=new JLabel("·Fecha Alta:");

	JTextField text_idCliente=new JTextField(50);
	JTextField text_nombreCliente=new JTextField(50);
	JTextField text_apesCliente=new JTextField(50);
	JTextField text_dniCliente=new JTextField(50);
	JTextField text_Email=new JTextField(50);
	JTextField text_Usuario=new JTextField(50);
	JTextField text_Contraseña=new JTextField(50);
	JTextField text_puntos=new JTextField(50);
	JTextField text_fechaAlta=new JTextField(50);
	
	JButton boton_modificarCliente=new JButton("",iconoModificar);
	
	String idCliente="";
	String nombreCliente="";
	String apesCliente="";
	String dniCliente="";
	String emailCliente="";
	String usuario="";
	String contraseña="";
	String puntos="";
	String fechaAlta="";
	
	Cliente cliente=null;
	

	public ModificarCliente(String idCliente,String nombreCliente,String apellidosCliente,String DNI,String email,
			String usuario,String contraseña,String puntos,String fechaAlta,JFrame frameConsulta,JPanel panelConsulta,
			JPanel panel_Autenticacion,DefaultTableModel dtmConsulta,JTable tabla_consulta){
		 
		 frameModificarCliente=frameConsulta;
		 panelConsulta.setVisible(false);
		 configurarInterfaz(idCliente,nombreCliente,apellidosCliente,DNI,email,usuario,contraseña,puntos,fechaAlta);
		 controlarEventos(panelConsulta,panel_Autenticacion,dtmConsulta,tabla_consulta);
	}
	
	
	void configurarInterfaz(String idCliente,String nombreCliente,String apellidosCliente,String DNI,String email,
			String usuario,String contraseña,String puntos,String fechaAlta){
		 Color colorFondoPantalla = new Color(0xe0e2e9);
	     String fuenteTexto = "Trebuchet";
	     int estiloTexto = Font.PLAIN;
	     
	     Toolkit tk = Toolkit.getDefaultToolkit();
	     Dimension pantalla=tk.getScreenSize();
	     int tamTextoBotones = 18;
	     int offsetBasico = 10;

	     int posXExit = pantalla.width - iconoSalir.getIconWidth() - offsetBasico;
	     int posYExit = (imagenCabecera.getIconHeight()/2)-(iconoSalir.getIconHeight()/2);
	     
	     int posXAtras = posXExit - iconoSalir.getIconWidth() - offsetBasico;
		 int posYAtras = posYExit;

	     int anchuraInput=350;
		 int alturaInput=35;
		 int anchuraTexto=140;

		 int offsetSuperiorFila01 = 80 + offsetBasico;
		 int offsetSuperiorFila02 = offsetSuperiorFila01 + offsetBasico + alturaInput;
		 int offsetSuperiorFila03 = offsetSuperiorFila02 + offsetBasico + alturaInput;
		 int offsetSuperiorFila04 = offsetSuperiorFila03 + offsetBasico + alturaInput;
		 int offsetSuperiorFila05 = offsetSuperiorFila04 + offsetBasico + alturaInput;
		 int offsetSuperiorFila06 = offsetSuperiorFila05 + offsetBasico + alturaInput;
		 
		 int offsetLateralColumna01 = offsetBasico;
		 int offsetLateralColumna02 = offsetLateralColumna01 + offsetBasico + anchuraInput + anchuraTexto;
		 int offsetLateralColumna03 = offsetLateralColumna01 + offsetBasico + anchuraInput + anchuraTexto;
	     
		 int offsetLateralInputColumna01 = offsetLateralColumna01 + anchuraTexto;
		 int offsetLateralInputColumna02 = offsetLateralColumna02 + anchuraTexto;
		 
		 int offsetLateralRadio01 = offsetLateralInputColumna01;
		 int offsetLateralRadio02 = offsetLateralRadio01 + anchuraTexto;
		 
		 //int posXBotonAlta = offsetLateralColumna02 + anchuraInput + anchuraTexto - iconoAlta.getIconWidth();
		    
		 int alturaTitulo = 50;
		 int anchuraTitulo = 500;
		 int offsetLateralTitulo = 100;
		 int offsetSuperiorTitulo = 15;   

	     //Añadimos y configuramos el panel_modificar
		 panelModificarCliente.setLayout(null);
		 panelModificarCliente.setBounds(0,0,pantalla.width, pantalla.height);
		 panelModificarCliente.setBackground(colorFondoPantalla);
		 frameModificarCliente.add(panelModificarCliente);
		 
		 titulo.setBounds(offsetLateralTitulo, offsetSuperiorTitulo, anchuraTitulo, alturaTitulo);
		 titulo.setFont(new Font(fuenteTexto,estiloTexto,30));
		 titulo.setForeground(colorFondoPantalla);
		 titulo.setVisible(true);
		 panelModificarCliente.add(titulo);
		 
		 botonAtras.setBounds(posXAtras,posYAtras,iconoSalir.getIconWidth(), iconoSalir.getIconHeight());
	     botonAtras.setFont(new Font(fuenteTexto,estiloTexto,tamTextoBotones));
	     botonAtras.setVisible(true);
	     panelModificarCliente.add(botonAtras); 	
	     
	     botonSalir.setBounds(posXExit, posYExit, iconoSalir.getIconWidth(), iconoSalir.getIconHeight());
	     botonSalir.setFont(new Font(fuenteTexto,estiloTexto,tamTextoBotones));
	     botonSalir.setVisible(true);
	     panelModificarCliente.add(botonSalir);
	     
		 lb_cabecera.setBounds(0,0,1400,83);
		 lb_cabecera.setVisible(true);
		 panelModificarCliente.add(lb_cabecera);

	     botonAtras.setVerticalTextPosition(AbstractButton.BOTTOM);//Alineamos el texto abajo
	     botonAtras.setHorizontalTextPosition(AbstractButton.CENTER);
	     botonSalir.setVerticalTextPosition(AbstractButton.BOTTOM);//Alineamos el texto abajo
	     botonSalir.setHorizontalTextPosition(AbstractButton.CENTER);
	     
	     lb_idCliente.setBounds(offsetLateralColumna01,offsetSuperiorFila01,anchuraTexto, alturaInput);
	     lb_idCliente.setFont(new Font(fuenteTexto,estiloTexto,tamTextoBotones));
	     lb_idCliente.setVisible(true);
	     panelModificarCliente.add(lb_idCliente); 
		 text_idCliente.setBounds(offsetLateralInputColumna01,offsetSuperiorFila01,anchuraInput, alturaInput);
		 text_idCliente.setFont(new Font(fuenteTexto,estiloTexto,tamTextoBotones));
		 text_idCliente.setForeground(Color.gray);
		 text_idCliente.setVisible(true);
		 text_idCliente.setEditable(false);
		 text_idCliente.setText(idCliente);
		 panelModificarCliente.add(text_idCliente);
		 
		 lb_nombreCliente.setBounds(offsetLateralColumna02,offsetSuperiorFila01,anchuraTexto, alturaInput);
		 lb_nombreCliente.setFont(new Font(fuenteTexto,estiloTexto,tamTextoBotones));
		 lb_nombreCliente.setVisible(true);
	     panelModificarCliente.add(lb_nombreCliente); 
		 text_nombreCliente.setBounds(offsetLateralInputColumna02,offsetSuperiorFila01,anchuraInput, alturaInput);
		 text_nombreCliente.setFont(new Font(fuenteTexto,estiloTexto,tamTextoBotones));
		 text_nombreCliente.setVisible(true);
		 text_nombreCliente.setText(nombreCliente);
		 panelModificarCliente.add(text_nombreCliente);
		 
		 lb_apesCliente.setBounds(offsetLateralColumna01,offsetSuperiorFila02,anchuraTexto, alturaInput);
		 lb_apesCliente.setFont(new Font(fuenteTexto,estiloTexto,tamTextoBotones));
		 lb_apesCliente.setVisible(true);
	     panelModificarCliente.add(lb_apesCliente); 
		 text_apesCliente.setBounds(offsetLateralInputColumna01,offsetSuperiorFila02,anchuraInput, alturaInput);
		 text_apesCliente.setFont(new Font(fuenteTexto,estiloTexto,tamTextoBotones));
		 text_apesCliente.setVisible(true);
		 text_apesCliente.setText(apellidosCliente);
		 panelModificarCliente.add(text_apesCliente);
		 
		 lb_DNICliente.setBounds(offsetLateralColumna02,offsetSuperiorFila02,anchuraTexto, alturaInput);
		 lb_DNICliente.setFont(new Font(fuenteTexto,estiloTexto,tamTextoBotones));
		 lb_DNICliente.setVisible(true);
	     panelModificarCliente.add(lb_DNICliente); 
		 text_dniCliente.setBounds(offsetLateralInputColumna02,offsetSuperiorFila02,anchuraInput, alturaInput);
		 text_dniCliente.setFont(new Font(fuenteTexto,estiloTexto,tamTextoBotones));
		 text_dniCliente.setVisible(true);
		 text_dniCliente.setText(DNI);
		 panelModificarCliente.add(text_dniCliente);
		 
		 lb_Email.setBounds(offsetLateralColumna01,offsetSuperiorFila03,anchuraTexto, alturaInput);
		 lb_Email.setFont(new Font(fuenteTexto,estiloTexto,tamTextoBotones));
		 lb_Email.setVisible(true);
	     panelModificarCliente.add(lb_Email); 
		 text_Email.setBounds(offsetLateralInputColumna01,offsetSuperiorFila03,anchuraInput, alturaInput);
		 text_Email.setFont(new Font(fuenteTexto,estiloTexto,tamTextoBotones));
		 text_Email.setVisible(true);
		 text_Email.setText(email);
		 panelModificarCliente.add(text_Email);
		 
		 lb_Usuario.setBounds(offsetLateralColumna02,offsetSuperiorFila03,anchuraTexto, alturaInput);
		 lb_Usuario.setFont(new Font(fuenteTexto,estiloTexto,tamTextoBotones));
		 lb_Usuario.setVisible(true);
	     panelModificarCliente.add(lb_Usuario); 
		 text_Usuario.setBounds(offsetLateralInputColumna02,offsetSuperiorFila03,anchuraInput, alturaInput);
		 text_Usuario.setFont(new Font(fuenteTexto,estiloTexto,tamTextoBotones));
		 text_Usuario.setVisible(true);
		 text_Usuario.setText(usuario);
		 panelModificarCliente.add(text_Usuario);
		 
		 lb_Contraseña.setBounds(offsetLateralColumna01,offsetSuperiorFila04,anchuraTexto, alturaInput);
		 lb_Contraseña.setFont(new Font(fuenteTexto,estiloTexto,tamTextoBotones));
		 lb_Contraseña.setVisible(true);
	     panelModificarCliente.add(lb_Contraseña); 
		 text_Contraseña.setBounds(offsetLateralInputColumna01,offsetSuperiorFila04,anchuraInput, alturaInput);
		 text_Contraseña.setFont(new Font(fuenteTexto,estiloTexto,tamTextoBotones));
		 text_Contraseña.setVisible(true);
		 text_Contraseña.setText(contraseña);
		 panelModificarCliente.add(text_Contraseña);
		 
		 lb_puntos.setBounds(offsetLateralColumna02,offsetSuperiorFila04,anchuraTexto, alturaInput);
		 lb_puntos.setFont(new Font(fuenteTexto,estiloTexto,tamTextoBotones));
		 lb_puntos.setVisible(true);
	     panelModificarCliente.add(lb_puntos); 
		 text_puntos.setBounds(offsetLateralInputColumna02,offsetSuperiorFila04,anchuraInput, alturaInput);
		 text_puntos.setFont(new Font(fuenteTexto,estiloTexto,tamTextoBotones));
		 text_puntos.setVisible(true);
		 text_puntos.setEditable(false);
		 text_puntos.setForeground(Color.gray);
		 text_puntos.setText(puntos);
		 panelModificarCliente.add(text_puntos);
		 
		 lb_fechaAlta.setBounds(offsetLateralColumna01,offsetSuperiorFila05,anchuraTexto, alturaInput);
		 lb_fechaAlta.setFont(new Font(fuenteTexto,estiloTexto,tamTextoBotones));
		 lb_fechaAlta.setVisible(true);
	     panelModificarCliente.add(lb_fechaAlta); 
		 text_fechaAlta.setBounds(offsetLateralInputColumna01,offsetSuperiorFila05,anchuraInput, alturaInput);
		 text_fechaAlta.setFont(new Font(fuenteTexto,estiloTexto,tamTextoBotones));
		 text_fechaAlta.setVisible(true);
		 text_fechaAlta.setText(fechaAlta);
		 text_fechaAlta.setEditable(false);
		 text_fechaAlta.setForeground(Color.gray);
		 panelModificarCliente.add(text_fechaAlta);
		 
		 boton_modificarCliente.setBounds(offsetLateralInputColumna02+anchuraInput-iconoModificar.getIconWidth(),offsetSuperiorFila05,iconoModificar.getIconWidth(), iconoModificar.getIconHeight());
		 boton_modificarCliente.setFont(new Font(fuenteTexto,estiloTexto,tamTextoBotones));
		 boton_modificarCliente.setVisible(true);
	     panelModificarCliente.add(boton_modificarCliente); 	 	
	}//fin configurarInterfaz()
	
	
	void controlarEventos(final JPanel panelConsulta,final JPanel panel_Autenticacion,final DefaultTableModel dtmConsulta,final JTable tabla_consulta){
		botonSalir.addActionListener(new ActionListener(){
			public void actionPerformed (ActionEvent e){	  
				int n = JOptionPane.showConfirmDialog (panelModificarCliente,"¿Desea cerrar sesión?","¿Cerrar sesión?",JOptionPane.YES_NO_OPTION);
		        if(n==0){
		        	  boolean LogOut=Server.LogOut();
		        	  panelModificarCliente.setVisible(false);
		        	  panel_Autenticacion.setVisible(true);
					  System.gc();
		        }
			}
	    });
	 
	    botonAtras.addActionListener(new ActionListener(){
			public void actionPerformed (ActionEvent e){	
				panelModificarCliente.setVisible(false);
				panelConsulta.setVisible(true);
				System.gc();
			}
	    });	
	    
	    boton_modificarCliente.addActionListener(new ActionListener(){
			public void actionPerformed (ActionEvent e){	
				idCliente=text_idCliente.getText();
				nombreCliente=text_nombreCliente.getText();
				apesCliente=text_apesCliente.getText();
				dniCliente=text_dniCliente.getText();
				emailCliente=text_Email.getText();
				usuario=text_Usuario.getText();
				contraseña=text_Contraseña.getText();
				puntos=text_puntos.getText();
				fechaAlta=text_fechaAlta.getText();
				modificarCliente(idCliente,nombreCliente,apesCliente,dniCliente,emailCliente,usuario,contraseña,
						puntos,fechaAlta,dtmConsulta,tabla_consulta,panelConsulta);
			}
	    });	
	}//fin controlarEventos()
	
	
	void modificarCliente(String idCliente,String nombreCliente,String apesCliente,String dniCliente,String emailCliente,
			String usuario,String contraseña,String puntos,String fechaAlta,DefaultTableModel dtmConsulta,
			JTable tabla_consulta,JPanel panelConsulta){
		 
		int n = JOptionPane.showConfirmDialog (panelModificarCliente,"¿Desea modificar los datos del cliente?",
				 "Modificar datos clientes",JOptionPane.OK_OPTION);
		 if(n==0){
			 cliente=Server.editarCliente(idCliente,nombreCliente,apesCliente,dniCliente,emailCliente,usuario,contraseña,puntos,fechaAlta);
			 if(cliente!=null){
				 int s= JOptionPane.showConfirmDialog (panelModificarCliente,"Cliente modificado con éxito.",
						 "Datos modificados",JOptionPane.CLOSED_OPTION);
				 if(s==0){
					    int fila=tabla_consulta.getRowCount();
				        for(int i=0;i<fila;i++){
					       String valor=(String) tabla_consulta.getValueAt(i,0);
					      if(idCliente.compareTo(valor)==0){
						     dtmConsulta.removeRow(i);
						     i=fila;
					      }
				        }
				        Object date[]={idCliente,nombreCliente,apesCliente,dniCliente,emailCliente,usuario,contraseña,puntos,fechaAlta,"Alta"};
				        dtmConsulta.addRow(date);
				        panelModificarCliente.setVisible(false);
				        panelConsulta.setVisible(true);
				 }
			 }else{
				 JOptionPane.showConfirmDialog (panelModificarCliente,"Error modificando cliente.",
						 "Error operación modificar datos",JOptionPane.CLOSED_OPTION);
			 }
	     }
		
	}//fin modificarCliente()
}
