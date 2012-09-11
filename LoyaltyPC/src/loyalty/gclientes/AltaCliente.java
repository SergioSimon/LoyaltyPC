package loyalty.gclientes;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import loyalty.control.Server;

public class AltaCliente {
	JFrame frameAlta;
	JPanel panelAlta=new JPanel();
	
	ImageIcon iconoAlta = new ImageIcon(getClass().getResource("/loyalty/images/alta.png"));
	ImageIcon iconoSalir = new ImageIcon(getClass().getResource("/loyalty/images/exit.png")); 
	ImageIcon iconoAtras = new ImageIcon(getClass().getResource("/loyalty/images/btnVolver.png"));
	 
	Icon imagenCabecera = new ImageIcon(getClass().getResource("/loyalty/images/cabecera.png"));
	
	JButton botonAtras=new JButton("", iconoAtras);
	JButton botonSalir=new JButton("", iconoSalir);
	JButton botonAlta=new JButton("", iconoAlta);
	
	JLabel fondo_imagen=new JLabel();
	JLabel lb_cabecera=new JLabel(imagenCabecera);
	
	JLabel lb_nombre=new JLabel("·NOMBRE:");
	JLabel lb_apellidos=new JLabel("·APELLIDOS:");
	JLabel lb_dni=new JLabel("·D.N.I.:");
	JLabel lb_email=new JLabel("·E-MAIL:");
	JLabel lb_user=new JLabel("·USUARIO:");
	JLabel lb_pass=new JLabel("·CONTRASEÑA:");
	JLabel lb_puntos=new JLabel("·PUNTOS:");
	JLabel lb_fechaAlta=new JLabel("·FECHA ALTA:");
	JLabel lb_imeiUID=new JLabel("·UID TARJETA:");
	JLabel lb_mensaje=new JLabel("\"ACERQUE LA TARJETA NFC AL LECTOR\"");

	JLabel titulo = new JLabel("ALTA DE CLIENTES");
	
	
	JTextField text_nombre=new JTextField(50);
	JTextField text_apellidos=new JTextField(50);
	JTextField text_dni=new JTextField(50);
	JTextField text_email=new JTextField(50);
	JTextField text_user=new JTextField(50);
	JPasswordField text_pass=new JPasswordField(50);
	JTextField text_puntos=new JTextField(50);
	JTextField text_fechaAlta=new JTextField(50);
	JTextField text_imeiUID=new JTextField(50);
	
	String nombreCliente="";
	String apesCliente="";
	String dniCliente="";
	String  emailCliente="";
	String usuario="";
	String contraseña="";
	String puntos="";
	String UID_tag="";

	public AltaCliente(JPanel panelGClientes,JPanel panel_Autenticacion,JFrame frame_GClientes){
		frameAlta=frame_GClientes;
		panelGClientes.setVisible(false);
		configurarInterfaz();
		controlarEventos(panelGClientes,panel_Autenticacion);
	}
	
	void configurarInterfaz(){
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
		 int offsetSuperiorFila08 = offsetSuperiorFila07 + offsetBasico + alturaInput;
		 
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
	     panelAlta.setLayout(null);
	     panelAlta.setBounds(0,0,pantalla.width, pantalla.height);
	     panelAlta.setBackground(colorFondoPantalla);
		 frameAlta.add(panelAlta);
		 
		 titulo.setBounds(offsetLateralTitulo, offsetSuperiorTitulo, anchuraTitulo, alturaTitulo);
		 titulo.setFont(new Font(fuenteTexto,estiloTexto,30));
		 titulo.setForeground(colorFondoPantalla);
		 titulo.setVisible(true);
		 panelAlta.add(titulo);
		 
		 botonAtras.setBounds(posXAtras,posYAtras,iconoSalir.getIconWidth(), iconoSalir.getIconHeight());
	     botonAtras.setFont(new Font(fuenteTexto,estiloTexto,tamTextoBotones));
	     botonAtras.setVisible(true);
	     panelAlta.add(botonAtras); 	
	     
	     botonSalir.setBounds(posXExit, posYExit, iconoSalir.getIconWidth(), iconoSalir.getIconHeight());
	     botonSalir.setFont(new Font(fuenteTexto,estiloTexto,tamTextoBotones));
	     botonSalir.setVisible(true);
	     panelAlta.add(botonSalir);
	     
		 lb_cabecera.setBounds(0,0,1400,83);
		 lb_cabecera.setVisible(true);
		 panelAlta.add(lb_cabecera);

	     botonAtras.setVerticalTextPosition(AbstractButton.BOTTOM);//Alineamos el texto abajo
	     botonAtras.setHorizontalTextPosition(AbstractButton.CENTER);
	     botonSalir.setVerticalTextPosition(AbstractButton.BOTTOM);//Alineamos el texto abajo
	     botonSalir.setHorizontalTextPosition(AbstractButton.CENTER);
	     
	     lb_nombre.setBounds(offsetLateralColumna01,offsetSuperiorFila01,anchuraTexto, alturaInput);
	     lb_nombre.setFont(new Font(fuenteTexto,estiloTexto,tamTextoBotones));
	     lb_nombre.setVisible(true);
		 panelAlta.add(lb_nombre); 
		 text_nombre.setBounds(offsetLateralInputColumna01,offsetSuperiorFila01,anchuraInput, alturaInput);
		 text_nombre.setFont(new Font(fuenteTexto,estiloTexto,tamTextoBotones));
		 text_nombre.setVisible(true);
		 panelAlta.add(text_nombre);
		 
		 lb_apellidos.setBounds(offsetLateralColumna02,offsetSuperiorFila01,anchuraTexto, alturaInput);
	     lb_apellidos.setFont(new Font(fuenteTexto,estiloTexto,tamTextoBotones));
	     lb_apellidos.setVisible(true);
		 panelAlta.add(lb_apellidos); 
		 text_apellidos.setBounds(offsetLateralInputColumna02,offsetSuperiorFila01,anchuraInput, alturaInput);
		 text_apellidos.setFont(new Font(fuenteTexto,estiloTexto,tamTextoBotones));
		 text_apellidos.setVisible(true);
		 panelAlta.add(text_apellidos);
		 
		 lb_dni.setBounds(offsetLateralColumna01,offsetSuperiorFila02,anchuraTexto, alturaInput);
		 lb_dni.setFont(new Font(fuenteTexto,estiloTexto,tamTextoBotones));
		 lb_dni.setVisible(true);
		 panelAlta.add(lb_dni); 
		 text_dni.setBounds(offsetLateralInputColumna01,offsetSuperiorFila02,anchuraInput, alturaInput);
		 text_dni.setFont(new Font(fuenteTexto,estiloTexto,tamTextoBotones));
		 text_dni.setVisible(true);
		 panelAlta.add(text_dni);
		 
		 lb_email.setBounds(offsetLateralColumna02,offsetSuperiorFila02,anchuraTexto, alturaInput);
		 lb_email.setFont(new Font(fuenteTexto,estiloTexto,tamTextoBotones));
		 lb_email.setVisible(true);
		 panelAlta.add(lb_email); 
		 text_email.setBounds(offsetLateralInputColumna02,offsetSuperiorFila02,anchuraInput, alturaInput);
		 text_email.setFont(new Font(fuenteTexto,estiloTexto,tamTextoBotones));
		 text_email.setVisible(true);
		 panelAlta.add(text_email);
		 
		 lb_user.setBounds(offsetLateralColumna01,offsetSuperiorFila03,anchuraTexto, alturaInput);
		 lb_user.setFont(new Font(fuenteTexto,estiloTexto,tamTextoBotones));
		 lb_user.setVisible(true);
		 panelAlta.add(lb_user); 
		 text_user.setBounds(offsetLateralInputColumna01,offsetSuperiorFila03,anchuraInput, alturaInput);
		 text_user.setFont(new Font(fuenteTexto,estiloTexto,tamTextoBotones));
		 text_user.setVisible(true);
		 panelAlta.add(text_user);
		 
		 lb_pass.setBounds(offsetLateralColumna02,offsetSuperiorFila03,anchuraTexto, alturaInput);
		 lb_pass.setFont(new Font(fuenteTexto,estiloTexto,tamTextoBotones));
		 lb_pass.setVisible(true);
		 panelAlta.add(lb_pass); 
		 text_pass.setBounds(offsetLateralInputColumna02,offsetSuperiorFila03,anchuraInput, alturaInput);
		 text_pass.setFont(new Font(fuenteTexto,estiloTexto,tamTextoBotones));
		 text_pass.setVisible(true);
		 panelAlta.add(text_pass);
		 
		 lb_puntos.setBounds(offsetLateralColumna01,offsetSuperiorFila04,anchuraTexto, alturaInput);
		 lb_puntos.setFont(new Font(fuenteTexto,estiloTexto,tamTextoBotones));
		 lb_puntos.setVisible(true);
		 panelAlta.add(lb_puntos); 
		 text_puntos.setBounds(offsetLateralInputColumna01,offsetSuperiorFila04,anchuraInput, alturaInput);
		 text_puntos.setFont(new Font(fuenteTexto,estiloTexto,tamTextoBotones));
		 text_puntos.setVisible(true);
		 text_puntos.setText("0");
		 text_puntos.setEditable(false);
		 panelAlta.add(text_puntos);
		 
		 lb_fechaAlta.setBounds(offsetLateralColumna02,offsetSuperiorFila04,anchuraTexto, alturaInput);
		 lb_fechaAlta.setFont(new Font(fuenteTexto,estiloTexto,tamTextoBotones));
		 lb_fechaAlta.setVisible(true);
		 panelAlta.add(lb_fechaAlta); 
		 text_fechaAlta.setBounds(offsetLateralInputColumna02,offsetSuperiorFila04,anchuraInput, alturaInput);
		 text_fechaAlta.setFont(new Font(fuenteTexto,estiloTexto,tamTextoBotones));
		 text_fechaAlta.setVisible(true);
		 text_fechaAlta.setEditable(false);
		 panelAlta.add(text_fechaAlta);
		 
		 lb_imeiUID.setBounds(offsetLateralColumna01,offsetSuperiorFila05,anchuraTexto, alturaInput);
		 lb_imeiUID.setFont(new Font(fuenteTexto,estiloTexto,tamTextoBotones));
		 lb_imeiUID.setVisible(true);
		 panelAlta.add(lb_imeiUID); 
		 text_imeiUID.setBounds(offsetLateralInputColumna01,offsetSuperiorFila05,anchuraInput, alturaInput);
		 text_imeiUID.setFont(new Font(fuenteTexto,estiloTexto,tamTextoBotones));
		 text_imeiUID.setVisible(true);
		 text_imeiUID.setBackground(Color.LIGHT_GRAY);
		 text_imeiUID.setEditable(false);
		 panelAlta.add(text_imeiUID);

	     botonAlta.setBounds(posXBotonAlta,offsetSuperiorFila05,iconoAlta.getIconWidth(), iconoAlta.getIconHeight());
	     botonAlta.setFont(new Font(fuenteTexto,estiloTexto,tamTextoBotones));
	     botonAlta.setVisible(true);
	     panelAlta.add(botonAlta);
	     
	     lb_mensaje.setBounds(offsetLateralColumna02-50,offsetSuperiorFila08,4*anchuraTexto, alturaInput);
	     lb_mensaje.setFont(new Font(fuenteTexto,estiloTexto,tamTextoBotones+4));
		 lb_mensaje.setVisible(false);
		 panelAlta.add(lb_mensaje); 
	}//fin configurarInterfaz()
	
	
	void controlarEventos(final JPanel panelGClientes,final JPanel panel_Autenticacion){
		 botonSalir.addActionListener(new ActionListener(){
				public void actionPerformed (ActionEvent e){	  
					int n = JOptionPane.showConfirmDialog (panelAlta,"¿Desea cerrar sesión?","¿Cerrar sesión?",JOptionPane.YES_NO_OPTION);
			        if(n==0){
			        	  boolean LogOut=Server.LogOut();
			        	  panelAlta.setVisible(false);
			        	  panel_Autenticacion.setVisible(true);
						  System.gc();
			        }
				}
		  });
		 
		 botonAtras.addActionListener(new ActionListener(){
				public void actionPerformed (ActionEvent e){	  
					panelAlta.setVisible(false);
					panelGClientes.setVisible(true);
					System.gc();
				}
		  });	
		 
		 botonAlta.addActionListener(new ActionListener(){
				public void actionPerformed (ActionEvent e){	  
					Calendar calendario = new GregorianCalendar();
					int anio=calendario.get(calendario.YEAR);
					int mes=calendario.get(calendario.MONTH)+1;
					int dia=calendario.get(calendario.DAY_OF_MONTH);
					int hora=calendario.get(calendario.HOUR_OF_DAY);
					int min=calendario.get(calendario.MINUTE);
					int seg=calendario.get(calendario.SECOND);
					String horas="";
					String minutos="";
					String segundos="";
					if(hora<10)
						horas="0"+hora;
					else
						horas=hora+"";
					if(min<10)
						minutos="0"+min;
					else
						minutos=min+"";
					if(seg<10)
						segundos="0"+seg;
					else
						segundos=seg+"";
					String fecha=anio+"-"+mes+"-"+dia+" "+horas+":"+minutos+":"+segundos;
					text_fechaAlta.setText(fecha);
					registrarCliente();
				}
		  });	
	}//fin controlarEventos()
	
	void registrarCliente(){
		nombreCliente=text_nombre.getText();
		apesCliente=text_apellidos.getText();
		dniCliente=text_dni.getText();
		emailCliente=text_email.getText();
		usuario=text_user.getText();
		contraseña=text_pass.getText();
		puntos=text_puntos.getText();
		UID_tag=text_imeiUID.getText();
		if(nombreCliente.compareTo("")==0 || nombreCliente==null || apesCliente.compareTo("")==0 || apesCliente==null ||
				emailCliente.compareTo("")==0 || emailCliente==null || usuario.compareTo("")==0 || usuario==null ||
				contraseña.compareTo("")==0 || contraseña==null || puntos.compareTo("")==0 || puntos==null){
			
			int j=JOptionPane.showConfirmDialog (panelAlta,"Error, campos vacios. Rellene todos los campos","Campos Vacíos",JOptionPane.CLOSED_OPTION);
		}else{
			 lb_mensaje.setVisible(true);
			 Hilo_RegistrarTarjeta mihilo = new Hilo_RegistrarTarjeta();
			 mihilo.setTextField(text_imeiUID);
			 mihilo.setTextnombre(text_nombre);
			 mihilo.setTextapes(text_apellidos);
			 mihilo.setTextdni(text_dni);
			 mihilo.setTextemail(text_email);
			 mihilo.setTextuser(text_user);
			 mihilo.setTextpas(text_pass);
			 mihilo.setTextpuntos(text_puntos);
			 mihilo.setTextfecha(text_fechaAlta);
			 mihilo.setPanel(panelAlta);
			 mihilo.setLabel(lb_mensaje);
		     mihilo.start();
		}
		
	}
}
