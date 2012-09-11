package loyalty.gpromociones;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.swing.AbstractButton;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import loyalty.control.Server;

import com.toedter.calendar.JCalendar;

public class AltaPromocion {
	
	JFrame frameAlta;
	JPanel panelAlta=new JPanel();
	
	ImageIcon fechaDcha = new ImageIcon(getClass().getResource("/loyalty/images/meterFechaDcha.png"));
	ImageIcon fechaIzda = new ImageIcon(getClass().getResource("/loyalty/images/meterFechaIzda.png"));
	
	ImageIcon iconoAlta = new ImageIcon(getClass().getResource("/loyalty/images/altaPromocion.png"));
	//ImageIcon imagen_consulta = new ImageIcon(getClass().getResource("/loyalty/images/btnConsultaCliente.png"));
	ImageIcon iconoSalir = new ImageIcon(getClass().getResource("/loyalty/images/exit.png")); 
	ImageIcon iconoAtras = new ImageIcon(getClass().getResource("/loyalty/images/btnVolver.png"));
	 
	//TODO Iconos fecha inicio y fecha fin
	
	Icon imagenCabecera = new ImageIcon(getClass().getResource("/loyalty/images/cabecera.png"));
	
	JButton botonAtras=new JButton("", iconoAtras);
	JButton botonSalir=new JButton("", iconoSalir);
	JButton botonAlta=new JButton("", iconoAlta);
	JButton botonFIni=new JButton("", fechaIzda);
	JButton botonFFin=new JButton("", fechaDcha);
	
	JLabel fondo_imagen=new JLabel();
	JLabel lb_cabecera=new JLabel(imagenCabecera);

	JLabel titulo = new JLabel("ALTA DE PROMOCIONES");
	
	JLabel lb_idTienda=new JLabel("ID.TIENDA:");
	JLabel lb_descripcion=new JLabel("DESCRIPCIÓN:");
	JLabel lb_fechaIni=new JLabel("FECHA INICIO:");
	JLabel lb_fechaFin=new JLabel("FECHA FIN:");
	JLabel lb_puntosNecesarios=new JLabel("PUNTOS:");
	JLabel lb_importeNecesario=new JLabel("IMPORTE:");
	JLabel lb_url=new JLabel("URL:");
	
	JCalendar calendario=new JCalendar();
	
	JTextField text_idTienda=new JTextField(50);
	JTextArea areaComentario=new JTextArea();
	JTextField text_fechaIni=new JTextField(50);
	JTextField text_fechaFin=new JTextField(50);
	JTextField text_puntosNecesarios=new JTextField(50);
	JTextField text_importeNecesario=new JTextField(50);
	JTextField text_url=new JTextField(50);
	

	public AltaPromocion(JPanel panelInicial,JPanel panel_Autenticacion,JPanel panelPromociones,JFrame framePromociones,
			String idTienda,String nombreTienda){
		frameAlta=framePromociones;
		panelPromociones.setVisible(false);
		configurarInterfaz(idTienda);
		controlarEventos(panelPromociones,panel_Autenticacion);
	}
	
	void configurarInterfaz(String idTienda){
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
	    
	    int anchuraInput=400;
		int alturaInput=35;
		int alturaAreaComentario=80;
		int anchuraTexto=140;
		int anchuraCalendario = 380;
		int alturaCalendario =170;
		
		int anchuraFechas=150;
	    
	    int alturaTitulo = 50;
	    int anchuraTitulo = 500;
	    int offsetLateralTitulo = 100;
	    int offsetSuperiorTitulo = 15;
	    
		int offsetSuperiorFila1 = 80 + offsetBasico;
		int offsetSuperiorFila2 = offsetSuperiorFila1 + offsetBasico + alturaInput;
		int offsetSuperiorFila3 = offsetSuperiorFila2 + offsetBasico + alturaInput;
		int offsetSuperiorFila4 = offsetSuperiorFila3 + offsetBasico + alturaInput;
		int offsetSuperiorFila5 = offsetSuperiorFila4 + offsetBasico + alturaInput;
		int offsetSuperiorFila6 = offsetSuperiorFila5 + offsetBasico + alturaInput;
		int offsetSuperiorFila7 = offsetSuperiorFila6 + offsetBasico + alturaInput;
		int offsetSuperiorFila8 = offsetSuperiorFila7 + offsetBasico + alturaInput;
		int offsetSuperiorFila9 = offsetSuperiorFila8 + offsetBasico + alturaInput;
		int offsetSuperiorFila10 = offsetSuperiorFila9 + offsetBasico + alturaInput;
		int offsetSuperiorFila11 = offsetSuperiorFila10 + offsetBasico + alturaInput;
		int offsetSuperiorFila12 = offsetSuperiorFila11 + offsetBasico + alturaInput;
		int offsetSuperiorFila13 = offsetSuperiorFila12 + offsetBasico + alturaInput;
		int offsetSuperiorFila14 = offsetSuperiorFila13 + offsetBasico + alturaInput;
		
	    //int offsetLateralColumna1 = offsetBasico;
		int offsetLateralColumna1 = pantalla.width/3 - anchuraInput;
		
		int offsetLateralColumna2 = offsetLateralColumna1 + anchuraInput + (2*offsetBasico);
		int offsetLateralInputColumna1 = offsetLateralColumna1 + anchuraTexto;
		int offsetLateralInputColumna2 = offsetLateralColumna2 + anchuraTexto;
	     
	    int offsetLateralCalendario = offsetLateralColumna2 - (anchuraCalendario/2);
		int offsetLateralFlechaIzda = offsetLateralCalendario - fechaIzda.getIconWidth();
		int offsetLateralFlechaDcha = offsetLateralCalendario + anchuraCalendario;
	    
		int offsetLateralAlta = offsetLateralColumna2 - (iconoAlta.getIconWidth()/2);
	    //Añadimos y configuramos el panel_modificar
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
	    botonAlta.setVerticalTextPosition(AbstractButton.BOTTOM);//Alineamos el texto abajo
	    botonAlta.setHorizontalTextPosition(AbstractButton.CENTER);
	    
	    lb_idTienda.setBounds(offsetLateralColumna1,offsetSuperiorFila1,anchuraTexto, alturaInput);
	    lb_idTienda.setFont(new Font(fuenteTexto,estiloTexto,tamTextoBotones));
	    lb_idTienda.setVisible(true);
	    lb_idTienda.setBackground(Color.white);
	    panelAlta.add(lb_idTienda); 
	    text_idTienda.setBounds(offsetLateralColumna1,offsetSuperiorFila2,anchuraInput, alturaInput);
	    text_idTienda.setFont(new Font(fuenteTexto,estiloTexto,tamTextoBotones));
	    text_idTienda.setVisible(true);
	    text_idTienda.setEditable(false);
	    text_idTienda.setText(idTienda);
	    panelAlta.add(text_idTienda);
	    
	    lb_descripcion.setBounds(offsetLateralColumna2,offsetSuperiorFila1,anchuraTexto, alturaInput);
	    lb_descripcion.setFont(new Font(fuenteTexto,estiloTexto,tamTextoBotones));
	    lb_descripcion.setVisible(true);
	    lb_descripcion.setBackground(Color.white);
	    panelAlta.add(lb_descripcion); 
	    areaComentario.setBounds(offsetLateralColumna2,offsetSuperiorFila2,anchuraInput,alturaAreaComentario);
	    areaComentario.setFont(new Font(fuenteTexto,estiloTexto,tamTextoBotones));
	    areaComentario.setVisible(true);
	    panelAlta.add(areaComentario);
	    
	    lb_fechaIni.setBounds(offsetLateralColumna1,offsetSuperiorFila4,anchuraTexto, alturaInput);
	    lb_fechaIni.setFont(new Font(fuenteTexto,estiloTexto,tamTextoBotones));
	    lb_fechaIni.setVisible(true);
	    lb_fechaIni.setBackground(Color.white);
	    panelAlta.add(lb_fechaIni); 
	    text_fechaIni.setBounds(offsetLateralColumna1,offsetSuperiorFila5,anchuraInput,alturaInput);
	    text_fechaIni.setFont(new Font(fuenteTexto,estiloTexto,tamTextoBotones));
	    text_fechaIni.setVisible(true);
	    text_fechaIni.setEditable(false);
	    panelAlta.add(text_fechaIni);
	    
	    lb_fechaFin.setBounds(offsetLateralColumna2,offsetSuperiorFila4,anchuraTexto, alturaInput);
	    lb_fechaFin.setFont(new Font(fuenteTexto,estiloTexto,tamTextoBotones));
	    lb_fechaFin.setVisible(true);
	    lb_fechaFin.setBackground(Color.white);
	    panelAlta.add(lb_fechaFin); 
	    text_fechaFin.setBounds(offsetLateralColumna2,offsetSuperiorFila5,anchuraInput,alturaInput);
	    text_fechaFin.setFont(new Font(fuenteTexto,estiloTexto,tamTextoBotones));
	    text_fechaFin.setVisible(true);
	    text_fechaFin.setEditable(false);
	    panelAlta.add(text_fechaFin);
	    
	    calendario.setBounds(offsetLateralCalendario,offsetSuperiorFila6,anchuraCalendario, alturaCalendario);
		calendario.setSize(anchuraCalendario,alturaCalendario); 
		panelAlta.add(calendario);
		
		botonFIni.setBounds(offsetLateralFlechaIzda,offsetSuperiorFila6,fechaDcha.getIconWidth(),fechaDcha.getIconHeight());
		botonFIni.setFont(new Font(fuenteTexto,estiloTexto,tamTextoBotones));
		botonFIni.setVisible(true);
	    panelAlta.add(botonFIni); 	
	    
	    botonFFin.setBounds(offsetLateralFlechaDcha,offsetSuperiorFila6,fechaDcha.getIconWidth(),fechaDcha.getIconHeight());
	    botonFFin.setFont(new Font(fuenteTexto,estiloTexto,tamTextoBotones));
	    botonFFin.setVisible(true);
	    panelAlta.add(botonFFin); 	
		
		lb_puntosNecesarios.setBounds(offsetLateralColumna1,offsetSuperiorFila10,anchuraTexto, alturaInput);
		lb_puntosNecesarios.setFont(new Font(fuenteTexto,estiloTexto,tamTextoBotones));
		lb_puntosNecesarios.setVisible(true);
		lb_puntosNecesarios.setBackground(Color.white);
		panelAlta.add(lb_puntosNecesarios); 
		text_puntosNecesarios.setBounds(offsetLateralColumna1,offsetSuperiorFila11,anchuraInput,alturaInput);
		text_puntosNecesarios.setFont(new Font(fuenteTexto,estiloTexto,tamTextoBotones));
		text_puntosNecesarios.setVisible(true);
		panelAlta.add(text_puntosNecesarios);
		
		lb_importeNecesario.setBounds(offsetLateralColumna2,offsetSuperiorFila10,anchuraTexto, alturaInput);
		lb_importeNecesario.setFont(new Font(fuenteTexto,estiloTexto,tamTextoBotones));
		lb_importeNecesario.setVisible(true);
		lb_importeNecesario.setBackground(Color.white);
		panelAlta.add(lb_importeNecesario); 
		text_importeNecesario.setBounds(offsetLateralColumna2,offsetSuperiorFila11,anchuraInput,alturaInput);
		text_importeNecesario.setFont(new Font(fuenteTexto,estiloTexto,tamTextoBotones));
		text_importeNecesario.setVisible(true);
		panelAlta.add(text_importeNecesario);
		
		lb_url.setBounds(offsetLateralColumna1,offsetSuperiorFila12,anchuraTexto, alturaInput);
		lb_url.setFont(new Font(fuenteTexto,estiloTexto,tamTextoBotones));
		lb_url.setVisible(true);
		lb_url.setBackground(Color.white);
		panelAlta.add(lb_url); 
		text_url.setBounds(offsetLateralColumna1,offsetSuperiorFila13,anchuraInput,alturaInput);
		text_url.setFont(new Font(fuenteTexto,estiloTexto,tamTextoBotones));
		text_url.setVisible(true);
		panelAlta.add(text_url);

	    botonAlta.setBounds(offsetLateralColumna2+anchuraInput-iconoAlta.getIconWidth(),offsetSuperiorFila13,iconoAlta.getIconWidth(),iconoAlta.getIconHeight());
	    botonAlta.setFont(new Font(fuenteTexto,estiloTexto,tamTextoBotones));
	    botonAlta.setVisible(true);
	    panelAlta.add(botonAlta);
	}//fin configurarInterfaz()
	
	
	void controlarEventos(final JPanel panelPromociones,final JPanel panel_Autenticacion){
		 botonSalir.addActionListener(new ActionListener(){
				public void actionPerformed (ActionEvent e){	  
					int n = JOptionPane.showConfirmDialog (panelAlta,"¿Desea salir del sistema?","¿Salir Sistema?",JOptionPane.YES_NO_OPTION);
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
					panelPromociones.setVisible(true);
					System.gc();
				}
		  });
		 
		 botonFIni.addActionListener(new ActionListener(){
				public void actionPerformed (ActionEvent e){	  
					String año = Integer.toString(calendario.getCalendar().get(java.util.Calendar.YEAR));
					int mes1 =(calendario.getCalendar().get(java.util.Calendar.MONTH) + 1);
					String mes="";
					if(mes1<10)
						mes="0"+mes1;
					if(mes1>=10)
						mes=mes1+"";
					int dia1 =(calendario.getCalendar().get(java.util.Calendar.DATE));
					String dia="";
					if(dia1<10)
						dia="0"+dia1;
					if(dia1>=10)
						dia=dia1+"";
					Calendar calendar = new GregorianCalendar();
					int hora =calendar.get(calendar.HOUR_OF_DAY);
					int minutos = calendar.get(calendar.MINUTE);
					int segundos = calendar.get(calendar.SECOND);
					text_fechaIni.setText(año+"-"+mes+"-"+dia+" "+hora+":"+minutos+":"+segundos);
				}
			});
			
			botonFFin.addActionListener(new ActionListener(){
				public void actionPerformed (ActionEvent e){	  
					String año = Integer.toString(calendario.getCalendar().get(java.util.Calendar.YEAR));
					int mes1 =(calendario.getCalendar().get(java.util.Calendar.MONTH) + 1);
					String mes="";
					if(mes1<10)
						mes="0"+mes1;
					if(mes1>=10)
						mes=mes1+"";
					int dia1 =(calendario.getCalendar().get(java.util.Calendar.DATE));
					String dia="";
					if(dia1<10)
						dia="0"+dia1;
					if(dia1>=10)
						dia=dia1+"";
					Calendar calendar = new GregorianCalendar();
					int hora =calendar.get(calendar.HOUR_OF_DAY);
					int minutos = calendar.get(calendar.MINUTE);
					int segundos = calendar.get(calendar.SECOND);
					text_fechaFin.setText(año+"-"+mes+"-"+dia+" "+hora+":"+minutos+":"+segundos);
				}
			});
			
			botonAlta.addActionListener(new ActionListener(){
				public void actionPerformed (ActionEvent e){	  
					String comentario=areaComentario.getText();
					String fechaIni=text_fechaIni.getText();
					String fechaFin=text_fechaFin.getText();
					String puntos =text_puntosNecesarios.getText();
					String importe=text_importeNecesario.getText();
					String url=text_url.getText();
					if(comentario.compareTo("")==0 || fechaIni.compareTo("")==0 || fechaFin.compareTo("")==0 || 
							puntos.compareTo("")==0 || importe.compareTo("")==0){
						 int n = JOptionPane.showConfirmDialog (panelAlta,"Campos vacíos. Por favor, complete todos los campos","Campos vacíos",JOptionPane.CLOSED_OPTION);
					}else{
						boolean exito=Server.altaPromocion(comentario,fechaIni,fechaFin,puntos,importe,url);
						if(exito){
							 int n = JOptionPane.showConfirmDialog (panelAlta,"Promoción dada de alta correctamente.","Alta promoción",JOptionPane.CLOSED_OPTION);
						}else{
							 int n = JOptionPane.showConfirmDialog (panelAlta,"Error en alta promoción.","Error alta promoción",JOptionPane.CLOSED_OPTION);
						}
						areaComentario.setText("");
						text_fechaIni.setText("");
						text_fechaFin.setText("");
						text_puntosNecesarios.setText("");
						text_importeNecesario.setText("");
						text_url.setText("");
					}
				}
		   });
	}//fin controlarEventos()
}
