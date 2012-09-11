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

import loyalty.control.Server;

public class RegistrarMovil {
	JFrame frameDuplicarTarjeta;
	JPanel panelDuplicarTarjeta=new JPanel();
	
	ImageIcon iconoAlta = new ImageIcon(getClass().getResource("/loyalty/images/alta.png"));
	ImageIcon iconoSalir = new ImageIcon(getClass().getResource("/loyalty/images/exit.png")); 
	ImageIcon iconoAtras = new ImageIcon(getClass().getResource("/loyalty/images/btnVolver.png"));
	 
	Icon imagenCabecera = new ImageIcon(getClass().getResource("/loyalty/images/cabecera.png"));
	
	JButton botonAtras=new JButton("", iconoAtras);
	JButton botonSalir=new JButton("", iconoSalir);
	JButton botonAlta=new JButton("", iconoAlta);
	
	JLabel fondo_imagen=new JLabel();
	JLabel lb_cabecera=new JLabel(imagenCabecera);
	
	JLabel titulo = new JLabel("REGISTRAR MÓVIL CLIENTE");
	
	JLabel lb_idCliente=new JLabel("·Id. Cliente:");
	JLabel lb_uidTag=new JLabel("·IMEI Móvil:");
	JLabel lb_descripcion=new JLabel("\"SITÚE LA TARJETA NFC EN EL LECTOR\"");
	
	JTextField text_idCliente=new JTextField(50);
	JTextField text_uidTarjeta=new JTextField(50);
	
	
	
	
	public RegistrarMovil(String idCliente,JFrame frameConsulta,JPanel panelConsulta,
			JPanel panel_Autenticacion,DefaultTableModel dtmConsulta,JTable tabla_Consulta){
		frameDuplicarTarjeta=frameConsulta;
		panelConsulta.setVisible(false);
		configurarInterfaz(idCliente);
		controlarEventos(panelConsulta,panel_Autenticacion);
		Hilo_ExtraerUID mihilo = new Hilo_ExtraerUID();
		mihilo.setIdCliente(idCliente);
		mihilo.setTextField(text_uidTarjeta);
		mihilo.setPanel(panelConsulta);
		mihilo.setPanelDuplicar(panelDuplicarTarjeta);
       	mihilo.start();
	}
	
	
	void configurarInterfaz(String idCliente){
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
		 int offsetSuperiorFila07 = offsetSuperiorFila06 + offsetBasico + alturaInput;
		 
		 int offsetLateralColumna01 = offsetBasico;
		 int offsetLateralColumna02 = offsetLateralColumna01 + offsetBasico + anchuraInput + anchuraTexto;
	     
		 int offsetLateralInputColumna01 = offsetLateralColumna01 + anchuraTexto;
		 int offsetLateralInputColumna02 = offsetLateralColumna02 + anchuraTexto;
		 
		 int offsetLateralRadio01 = offsetLateralInputColumna01;
		 int offsetLateralRadio02 = offsetLateralRadio01 + anchuraTexto;
		 
		 int posXBotonAlta = offsetLateralColumna02 + anchuraInput + anchuraTexto - iconoAlta.getIconWidth();
		    
		 int alturaTitulo = 50;
		 int anchuraTitulo = 500;
		 int offsetLateralTitulo = 100;
		 int offsetSuperiorTitulo = 15;   
		 
		 //Añadimos y configuramos el panelAlta
	     panelDuplicarTarjeta.setLayout(null);
	     panelDuplicarTarjeta.setBounds(0,0,pantalla.width, pantalla.height);
	     panelDuplicarTarjeta.setBackground(colorFondoPantalla);
		 frameDuplicarTarjeta.add(panelDuplicarTarjeta);
		 
		 titulo.setBounds(offsetLateralTitulo, offsetSuperiorTitulo, anchuraTitulo, alturaTitulo);
		 titulo.setFont(new Font(fuenteTexto,estiloTexto,30));
		 titulo.setForeground(colorFondoPantalla);
		 titulo.setVisible(true);
		 panelDuplicarTarjeta.add(titulo);
		 
		 botonAtras.setBounds(posXAtras,posYAtras,iconoSalir.getIconWidth(), iconoSalir.getIconHeight());
	     botonAtras.setFont(new Font(fuenteTexto,estiloTexto,tamTextoBotones));
	     botonAtras.setVisible(true);
	     panelDuplicarTarjeta.add(botonAtras); 	
	     
	     botonSalir.setBounds(posXExit, posYExit, iconoSalir.getIconWidth(), iconoSalir.getIconHeight());
	     botonSalir.setFont(new Font(fuenteTexto,estiloTexto,tamTextoBotones));
	     botonSalir.setVisible(true);
	     panelDuplicarTarjeta.add(botonSalir);
	     
		 lb_cabecera.setBounds(0,0,1400,83);
		 lb_cabecera.setVisible(true);
		 panelDuplicarTarjeta.add(lb_cabecera);

	     botonAtras.setVerticalTextPosition(AbstractButton.BOTTOM);//Alineamos el texto abajo
	     botonAtras.setHorizontalTextPosition(AbstractButton.CENTER);
	     botonSalir.setVerticalTextPosition(AbstractButton.BOTTOM);//Alineamos el texto abajo
	     botonSalir.setHorizontalTextPosition(AbstractButton.CENTER);
	     
		 lb_idCliente.setBounds(offsetLateralColumna01,offsetSuperiorFila01,anchuraTexto, alturaInput);
		 lb_idCliente.setFont(new Font(fuenteTexto,estiloTexto,tamTextoBotones));
		 lb_idCliente.setVisible(true);
		 panelDuplicarTarjeta.add(lb_idCliente); 
		 text_idCliente.setBounds(offsetLateralInputColumna01,offsetSuperiorFila01,anchuraInput, alturaInput);
		 text_idCliente.setFont(new Font(fuenteTexto,estiloTexto,tamTextoBotones));
		 text_idCliente.setVisible(true);
		 text_idCliente.setEditable(false);
		 text_idCliente.setForeground(Color.gray);
		 text_idCliente.setText(idCliente);
		 panelDuplicarTarjeta.add(text_idCliente);
	     
	     lb_descripcion.setBounds(offsetLateralColumna01+3*anchuraTexto,offsetSuperiorFila04,4*anchuraTexto, alturaInput);
	     lb_descripcion.setFont(new Font(fuenteTexto,estiloTexto,tamTextoBotones+6));
	     lb_descripcion.setVisible(true);
	     panelDuplicarTarjeta.add(lb_descripcion); 
	     
	     
		 lb_uidTag.setBounds(offsetLateralColumna01,offsetSuperiorFila07,anchuraTexto, alturaInput);
		 lb_uidTag.setFont(new Font(fuenteTexto,estiloTexto,tamTextoBotones));
		 lb_uidTag.setVisible(true);
		 panelDuplicarTarjeta.add(lb_uidTag); 
		 text_uidTarjeta.setBounds(offsetLateralInputColumna01,offsetSuperiorFila07,anchuraInput, alturaInput);
		 text_uidTarjeta.setFont(new Font(fuenteTexto,estiloTexto,tamTextoBotones));
		 text_uidTarjeta.setVisible(true);
		 text_uidTarjeta.setEditable(false);
		 text_uidTarjeta.setForeground(Color.gray);
		 panelDuplicarTarjeta.add(text_uidTarjeta);
	}//fin configurarInterfaz()

	
	void controlarEventos(final JPanel panelConsulta,final JPanel panel_Autenticacion){
		 botonSalir.addActionListener(new ActionListener(){
				public void actionPerformed (ActionEvent e){	  
					int n = JOptionPane.showConfirmDialog (panelDuplicarTarjeta,"¿Desea cerrar sesión?","¿Cerrar sesión?",JOptionPane.YES_NO_OPTION);
			        if(n==0){
			        	  boolean LogOut=Server.LogOut();
			        	  panelDuplicarTarjeta.setVisible(false);
			        	  panel_Autenticacion.setVisible(true);
						  System.gc();
			        }
				}
		  });
		 
		 botonAtras.addActionListener(new ActionListener(){
				public void actionPerformed (ActionEvent e){	  
					panelDuplicarTarjeta.setVisible(false);
					panelConsulta.setVisible(true);
					System.gc();
				}
		  });	
	}//fin controlarEventos()
}
