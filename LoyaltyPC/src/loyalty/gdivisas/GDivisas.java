package loyalty.gdivisas;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import loyalty.control.Server;

public class GDivisas {
	 JFrame frameDivisas;
	 JPanel panelDivisas=new JPanel();
	 
	 Icon iconoBotonCompras = new ImageIcon(getClass().getResource("/loyalty/images/iconoLoyalty.gif"));  
	 Icon imagenCabecera = new ImageIcon(getClass().getResource("/loyalty/images/cabecera.png"));
	 
	 //TODO
	 ImageIcon iconoSalir = new ImageIcon(getClass().getResource("/loyalty/images/exit.png")); 
	 ImageIcon iconoAtras = new ImageIcon(getClass().getResource("/loyalty/images/btnVolver.png"));
	 
	 ImageIcon imagen_EurosPuntos = new ImageIcon(getClass().getResource("/loyalty/images/btnEurosPuntos.png"));
	 ImageIcon imagen_PuntosEuros = new ImageIcon(getClass().getResource("/loyalty/images/btnPuntosEuros.png"));
		
	 JButton botonEurosPuntos=new JButton("",imagen_EurosPuntos);
	 JButton botonPuntosEuros=new JButton("",imagen_PuntosEuros);
	 JButton botonSalir=new JButton("", iconoSalir);
	 JButton botonAtras=new JButton("", iconoAtras);
	 
	 
	 JLabel titulo = new JLabel("CONFIGURAR DIVISAS");
	 JLabel lb_cabecera=new JLabel(imagenCabecera);
	 
	 public GDivisas(JPanel panelInicial, JPanel panel_Autenticacion,JFrame frameInicial,String idTienda,
			 int idCompra,int idCanjeo,int compra, double canjeo){
		 frameDivisas=frameInicial;
		 panelInicial.setVisible(false);
		 configurarInterfaz();
		 controlarEventos(panel_Autenticacion,panelInicial,idTienda,idCompra,idCanjeo,compra,canjeo);
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
	     int offsetSuperiorFila02 = offsetSuperiorFila01 + offsetBasico + alturaBotonesMenu;
	     int offsetSuperiorFila03 = offsetSuperiorFila01 + 2*offsetBasico + 2*alturaBotonesMenu;
	     
	     int offsetLateralColumna01 = (pantalla.width/2) - (anchuraBotonesMenu);
	     int offsetLateralColumna = (pantalla.width/2) - (anchuraBotonesMenu/2);
	     
	     int offsetLateralColumna02 = offsetLateralColumna01 + anchuraBotonesMenu + offsetBasico;
	     int offsetLateralColumna03= (pantalla.width/4) - (anchuraBotonesMenu);
	     
	     int alturaTitulo = 50;
		 int anchuraTitulo = 500;
		 int offsetLateralTitulo = 100;
		 int offsetSuperiorTitulo = 15;
	     
	     
	     //Añadimos y configuramos el panel_modificar
	     panelDivisas.setLayout(null);
	     panelDivisas.setBounds(0,0,pantalla.width, pantalla.height);
	     panelDivisas.setBackground(colorFondoPantalla);
		 frameDivisas.add(panelDivisas);
		 
		 titulo.setBounds(offsetLateralTitulo, offsetSuperiorTitulo, anchuraTitulo, alturaTitulo);
		 titulo.setFont(new Font(fuenteTexto,estiloTexto,30));
		 titulo.setForeground(colorFondoPantalla);
		 titulo.setVisible(true);
		 panelDivisas.add(titulo);
		 
		 botonAtras.setBounds(posXAtras,posYAtras,iconoSalir.getIconWidth(), iconoSalir.getIconHeight());
	     botonAtras.setFont(new Font(fuenteTexto,estiloTexto,tamTextoBotones));
	     botonAtras.setVisible(true);
	     panelDivisas.add(botonAtras); 
	     
		 botonSalir.setBounds(posXExit, posYExit, iconoSalir.getIconWidth(), iconoSalir.getIconHeight());
	     botonSalir.setFont(new Font(fuenteTexto,estiloTexto,tamTextoBotones));
	     botonSalir.setVisible(true);
	     panelDivisas.add(botonSalir);
		 
	     lb_cabecera.setBounds(0,0,1400,83);
		 lb_cabecera.setVisible(true);
		 panelDivisas.add(lb_cabecera);
		 
		 botonEurosPuntos.setBounds(offsetLateralColumna01,offsetSuperiorFila01,anchuraBotonesMenu, alturaBotonesMenu);
		 botonEurosPuntos.setFont(new Font(fuenteTexto,estiloTexto,tamTextoBotones));
		 botonEurosPuntos.setVisible(true);
		 panelDivisas.add(botonEurosPuntos); 	

		 botonPuntosEuros.setBounds(offsetLateralColumna02,offsetSuperiorFila01,anchuraBotonesMenu, alturaBotonesMenu);
		 botonPuntosEuros.setFont(new Font(fuenteTexto,estiloTexto,tamTextoBotones));
		 botonPuntosEuros.setVisible(true);
	     panelDivisas.add(botonPuntosEuros);
	 }//fin configurarInterfaz()
	 
	 
	 void controlarEventos(final JPanel panel_Autenticacion,final JPanel panelInicial,final String idTienda,
			 final int idCompra,final int idCanjeo,final int compra,final double canjeo){
		 botonSalir.addActionListener(new ActionListener(){
				public void actionPerformed (ActionEvent e){	  
					int n = JOptionPane.showConfirmDialog (panelDivisas,"¿Desea salir del sistema?","¿Salir Sistema?",JOptionPane.YES_NO_OPTION);
			        if(n==0){
			        	  boolean LogOut=Server.LogOut();
			        	  panelDivisas.setVisible(false);
			        	  panel_Autenticacion.setVisible(true);
						  System.gc();
			        }
				}
		  });
		 
		 botonAtras.addActionListener(new ActionListener(){
				public void actionPerformed (ActionEvent e){	  
					panelDivisas.setVisible(false);
			        panelInicial.setVisible(true);
					System.gc();
				}
		  });
		 
		 botonEurosPuntos.addActionListener(new ActionListener(){
				public void actionPerformed (ActionEvent e){	  
					new EurosPuntos(panel_Autenticacion,panelDivisas,idTienda,frameDivisas,idCompra,compra);
				}
		  });
		 
		 botonPuntosEuros.addActionListener(new ActionListener(){
				public void actionPerformed (ActionEvent e){	  
					new PuntosEuros(panel_Autenticacion,panelDivisas,idTienda,frameDivisas,idCanjeo,canjeo);
				}
		  });
	 }//fin controlarEventos()
}
