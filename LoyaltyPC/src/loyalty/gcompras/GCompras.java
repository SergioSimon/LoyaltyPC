package loyalty.gcompras;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import loyalty.control.Server;

public class GCompras {
	
	JFrame frame_GCompras;
	JPanel panelGCompras=new JPanel();
	
	ImageIcon iconoSalir = new ImageIcon(getClass().getResource("/loyalty/images/exit.png")); 
	ImageIcon iconoAtras = new ImageIcon(getClass().getResource("/loyalty/images/btnVolver.png"));
	Icon imagenCabecera = new ImageIcon(getClass().getResource("/loyalty/images/cabecera.png"));
	
	ImageIcon imagen_compra = new ImageIcon(getClass().getResource("/loyalty/images/btnRealizarCompra.png"));
	ImageIcon imagen_puntos = new ImageIcon(getClass().getResource("/loyalty/images/btnConsultaPuntos.png"));
	
	JButton botonAtras=new JButton("", iconoAtras);
	JButton botonSalir=new JButton("", iconoSalir);
	JButton botonCompra=new JButton("", imagen_compra);
	JButton botonConsultaPuntos=new JButton("", imagen_puntos);

	JLabel titulo = new JLabel("GESTIÓN DE COMPRAS");
	
	JLabel fondo_imagen=new JLabel();
	JLabel lb_cabecera=new JLabel(imagenCabecera);
	
	public GCompras(JPanel panelInicial, JPanel panel_Autenticacion,JFrame frameInicial,String idTienda){
		frame_GCompras=frameInicial;
		panelInicial.setVisible(false);
		configurarInterfaz();
		controlarEventos(panel_Autenticacion,panelInicial,idTienda);
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
	    panelGCompras.setLayout(null);
	    panelGCompras.setBounds(0,0,pantalla.width, pantalla.height);
	    panelGCompras.setBackground(colorFondoPantalla);
		frame_GCompras.add(panelGCompras);
		 
		titulo.setBounds(offsetLateralTitulo, offsetSuperiorTitulo, anchuraTitulo, alturaTitulo);
		titulo.setFont(new Font(fuenteTexto,estiloTexto,30));
		titulo.setForeground(colorFondoPantalla);
		titulo.setVisible(true);
		panelGCompras.add(titulo);

		botonAtras.setBounds(posXAtras,posYAtras,iconoSalir.getIconWidth(), iconoSalir.getIconHeight());
	    botonAtras.setFont(new Font(fuenteTexto,estiloTexto,tamTextoBotones));
	    botonAtras.setVisible(true);
	    panelGCompras.add(botonAtras); 	
	     
	    botonSalir.setBounds(posXExit, posYExit, iconoSalir.getIconWidth(), iconoSalir.getIconHeight());
	    botonSalir.setFont(new Font(fuenteTexto,estiloTexto,tamTextoBotones));
	    botonSalir.setVisible(true);
	    panelGCompras.add(botonSalir);
	    
		lb_cabecera.setBounds(0,0,1400,83);
		lb_cabecera.setVisible(true);
		panelGCompras.add(lb_cabecera);


	    botonConsultaPuntos.setBounds(offsetLateralColumna01,offsetSuperiorFila01,anchuraBotonesMenu, alturaBotonesMenu);
	    botonConsultaPuntos.setFont(new Font(fuenteTexto,estiloTexto,tamTextoBotones));
	    botonConsultaPuntos.setVisible(true);
	    panelGCompras.add(botonConsultaPuntos); 	

	    botonCompra.setBounds(offsetLateralColumna02,offsetSuperiorFila01,anchuraBotonesMenu, alturaBotonesMenu);
		botonCompra.setFont(new Font(fuenteTexto,estiloTexto,tamTextoBotones));
		botonCompra.setVisible(true);
		panelGCompras.add(botonCompra);	
	}
	
	
	void controlarEventos(final JPanel panel_Autenticacion,final JPanel panelInicial,final String idTienda){
		 botonSalir.addActionListener(new ActionListener(){
				public void actionPerformed (ActionEvent e){	  
					int n = JOptionPane.showConfirmDialog (panelGCompras,"¿Desea salir del sistema?","¿Salir Sistema?",JOptionPane.YES_NO_OPTION);
			        if(n==0){
			        	  boolean LogOut=Server.LogOut();
			        	  panelGCompras.setVisible(false);
			        	  panel_Autenticacion.setVisible(true);
						  System.gc();
			        }
				}
		  });
		 
		 botonAtras.addActionListener(new ActionListener(){
				public void actionPerformed (ActionEvent e){	  
			        	  panelGCompras.setVisible(false);
			        	  panelInicial.setVisible(true);
						  System.gc();
				}
		  });
		 
		 botonCompra.addActionListener(new ActionListener(){
				public void actionPerformed (ActionEvent e){	  
			        	new Compras(panelInicial,panel_Autenticacion,panelGCompras,frame_GCompras,idTienda);
				}
		  });
		 
		 botonConsultaPuntos.addActionListener(new ActionListener(){
				public void actionPerformed (ActionEvent e){	  
		        	new ConsultaPuntos(panelInicial,panel_Autenticacion,panelGCompras,frame_GCompras,idTienda);
			}
	  });
	}
}
