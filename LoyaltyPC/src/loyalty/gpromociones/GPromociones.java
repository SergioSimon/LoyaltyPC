package loyalty.gpromociones;

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

import loyalty.control.Server;

public class GPromociones {
	JFrame framePromociones;
	JPanel panelPromociones=new JPanel();
	
	ImageIcon iconoSalir = new ImageIcon(getClass().getResource("/loyalty/images/exit.png")); 
	ImageIcon iconoAtras = new ImageIcon(getClass().getResource("/loyalty/images/btnVolver.png"));
	Icon imagenCabecera = new ImageIcon(getClass().getResource("/loyalty/images/cabecera.png"));
	
	ImageIcon imagen_alta = new ImageIcon(getClass().getResource("/loyalty/images/btnAltaPromo.png"));
	ImageIcon imagen_consulta = new ImageIcon(getClass().getResource("/loyalty/images/btnConsultaPromo.png"));
	
	JButton botonAtras=new JButton("", iconoAtras);
	JButton botonSalir=new JButton("", iconoSalir);
	JButton botonAlta=new JButton("", imagen_alta);
	JButton botonConsulta=new JButton("", imagen_consulta);

	JLabel titulo = new JLabel("GESTIÓN DE PROMOCIONES");
	
	JLabel fondo_imagen=new JLabel();
	JLabel lb_cabecera=new JLabel(imagenCabecera);
	
	String idTienda="";
	String nombreTienda="";

	public GPromociones(JPanel panelInicial,JPanel panel_Autenticacion,JFrame frameInicial,String User,String Pass,
			String permiso,String idTienda,String nombreTienda,int compra,double canjeo,String URL){
		this.idTienda=idTienda;
		this.nombreTienda=nombreTienda;
		framePromociones=frameInicial;
		panelInicial.setVisible(false);
		configurarInterfaz();
		controlarEventos(panel_Autenticacion,panelInicial,idTienda,nombreTienda);
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

		 int anchuraBotonesMenu = 330;
	     int alturaBotonesMenu = 150;
	     
	     int offsetSuperiorFila01 = alturaBotonesMenu + offsetBasico;
	     int offsetLateralColumna01 = (pantalla.width/2) - (anchuraBotonesMenu);
	     int offsetLateralColumna02 = offsetLateralColumna01 + anchuraBotonesMenu + offsetBasico;
		    
		 int alturaTitulo = 50;
		 int anchuraTitulo = 500;
		 int offsetLateralTitulo = 100;
		 int offsetSuperiorTitulo = 15;
		    
	     
	     //Añadimos y configuramos el panel_modificar
	     panelPromociones.setLayout(null);
	     panelPromociones.setBounds(0,0,pantalla.width, pantalla.height);
	     panelPromociones.setBackground(colorFondoPantalla);
	     framePromociones.add(panelPromociones);
		 
	     titulo.setBounds(offsetLateralTitulo, offsetSuperiorTitulo, anchuraTitulo, alturaTitulo);
	     titulo.setFont(new Font(fuenteTexto,estiloTexto,30));
	     titulo.setForeground(colorFondoPantalla);
	     titulo.setVisible(true);
	     panelPromociones.add(titulo);
	     
	     botonAtras.setBounds(posXAtras,posYAtras,iconoSalir.getIconWidth(), iconoSalir.getIconHeight());
	     botonAtras.setFont(new Font(fuenteTexto,estiloTexto,tamTextoBotones));
	     botonAtras.setVisible(true);
	     panelPromociones.add(botonAtras); 	
	     
	     botonSalir.setBounds(posXExit, posYExit, iconoSalir.getIconWidth(), iconoSalir.getIconHeight());
	     botonSalir.setFont(new Font(fuenteTexto,estiloTexto,tamTextoBotones));
	     botonSalir.setVisible(true);
	     panelPromociones.add(botonSalir);
	     

		 lb_cabecera.setBounds(0,0,1400,83);
		 lb_cabecera.setVisible(true);
		 panelPromociones.add(lb_cabecera);
		 
		 
	     botonAtras.setVerticalTextPosition(AbstractButton.BOTTOM);//Alineamos el texto abajo
	     botonAtras.setHorizontalTextPosition(AbstractButton.CENTER);
	     botonSalir.setVerticalTextPosition(AbstractButton.BOTTOM);//Alineamos el texto abajo
	     botonSalir.setHorizontalTextPosition(AbstractButton.CENTER);
	     
	     botonAlta.setBounds(offsetLateralColumna01,offsetSuperiorFila01,anchuraBotonesMenu, alturaBotonesMenu);
	     botonAlta.setFont(new Font(fuenteTexto,estiloTexto,tamTextoBotones));
	     botonAlta.setVisible(true);
	     panelPromociones.add(botonAlta); 	

	     botonConsulta.setBounds(offsetLateralColumna02,offsetSuperiorFila01,anchuraBotonesMenu, alturaBotonesMenu);
	     botonConsulta.setFont(new Font(fuenteTexto,estiloTexto,tamTextoBotones));
	     botonConsulta.setVisible(true);
	     panelPromociones.add(botonConsulta);
	}
	
	
	void controlarEventos(final JPanel panel_Autenticacion,final JPanel panelInicial,final String idTienda,final String nombreTienda){
		 botonSalir.addActionListener(new ActionListener(){
				public void actionPerformed (ActionEvent e){	  
					int n = JOptionPane.showConfirmDialog (panelPromociones,"¿Desea salir del sistema?","¿Salir Sistema?",JOptionPane.YES_NO_OPTION);
			        if(n==0){
			        	  boolean LogOut=Server.LogOut();
			        	  panelPromociones.setVisible(false);
			        	  panel_Autenticacion.setVisible(true);
						  System.gc();
			        }
				}
		  });
		 
		 botonAtras.addActionListener(new ActionListener(){
				public void actionPerformed (ActionEvent e){	  
					panelPromociones.setVisible(false);
			        panelInicial.setVisible(true);
					System.gc();
				}
		  });
		 
		 botonAlta.addActionListener(new ActionListener(){
				public void actionPerformed (ActionEvent e){	  
					new AltaPromocion(panelInicial,panel_Autenticacion,panelPromociones,framePromociones,idTienda,nombreTienda);
				}
		  });
		 
		 botonConsulta.addActionListener(new ActionListener(){
				public void actionPerformed (ActionEvent e){	  
					new ConsultaPromociones(panelInicial,panel_Autenticacion,panelPromociones,framePromociones,idTienda,nombreTienda);
				}
		  });
	}
}
