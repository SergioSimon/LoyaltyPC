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
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import loyalty.control.Server;

public class PuntosEuros {
	 JFrame framePuntosEuros;
	 JPanel panelPuntosEuros=new JPanel();
	 
	 Icon iconoBotonCompras = new ImageIcon(getClass().getResource("/loyalty/images/iconoLoyalty.gif"));  
	 Icon imagenCabecera = new ImageIcon(getClass().getResource("/loyalty/images/cabecera.png"));
	 
	 //TODO
	 ImageIcon iconoSalir = new ImageIcon(getClass().getResource("/loyalty/images/exit.png")); 
	 ImageIcon iconoAtras = new ImageIcon(getClass().getResource("/loyalty/images/btnVolver.png"));
	 
	 JButton botonSalir=new JButton("", iconoSalir);
	 JButton botonAtras=new JButton("", iconoAtras);
	 JButton botonModificar=new JButton("Modificar");
	 
	 
	 JLabel titulo = new JLabel("CONFIGURAR PTOS - �");
	 JLabel lb_cabecera=new JLabel(imagenCabecera);
	 
	 JLabel lb_idDivisa=new JLabel("�Id. Divisa:");
	 JLabel lb_euros=new JLabel("�Euros Desc.:");
	 JLabel lb_puntos=new JLabel("�Puntos:");
	 JLabel lb_modificar=new JLabel("\"NUEVOS DATOS DE DIVISA PTOS - �\"");
	 JLabel lb_eurosfinal=new JLabel("�Euros Desc.:");
	 JLabel lb_puntosfinal=new JLabel("�Puntos:");
	 
	 JTextField text_idDivisa=new JTextField(50);
	 JTextField text_euros=new JTextField(50);
	 JTextField text_puntos=new JTextField(50);
	 JTextField text_eurosfinal=new JTextField(50);
	 JTextField text_puntosfinal=new JTextField(50);
	 
	 

	public PuntosEuros(JPanel panel_Autenticacion,JPanel panelDivisas,String idTienda,JFrame frameDivisas,int idCanjeo,
			double canjeo){
		framePuntosEuros=frameDivisas;
		panelDivisas.setVisible(false);
		configurarInterfaz(idCanjeo,canjeo);
		controlarEventos(panel_Autenticacion,panelDivisas,idCanjeo);
	}
	
	void configurarInterfaz(int idCanjeo,double canjeo){
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
		    
		 int alturaTitulo = 50;
		 int anchuraTitulo = 500;
		 int offsetLateralTitulo = 100;
		 int offsetSuperiorTitulo = 15;   
	     
	     
	     //A�adimos y configuramos el panel_modificar
		 panelPuntosEuros.setLayout(null);
		 panelPuntosEuros.setBounds(0,0,pantalla.width, pantalla.height);
		 panelPuntosEuros.setBackground(colorFondoPantalla);
		 framePuntosEuros.add(panelPuntosEuros);
		 
		 titulo.setBounds(offsetLateralTitulo, offsetSuperiorTitulo, anchuraTitulo, alturaTitulo);
		 titulo.setFont(new Font(fuenteTexto,estiloTexto,30));
		 titulo.setForeground(colorFondoPantalla);
		 titulo.setVisible(true);
		 panelPuntosEuros.add(titulo);
		 
		 botonAtras.setBounds(posXAtras,posYAtras,iconoSalir.getIconWidth(), iconoSalir.getIconHeight());
	     botonAtras.setFont(new Font(fuenteTexto,estiloTexto,tamTextoBotones));
	     botonAtras.setVisible(true);
	     panelPuntosEuros.add(botonAtras); 
	     
		 botonSalir.setBounds(posXExit, posYExit, iconoSalir.getIconWidth(), iconoSalir.getIconHeight());
	     botonSalir.setFont(new Font(fuenteTexto,estiloTexto,tamTextoBotones));
	     botonSalir.setVisible(true);
	     panelPuntosEuros.add(botonSalir);
		 
	     lb_cabecera.setBounds(0,0,1400,83);
		 lb_cabecera.setVisible(true);
		 panelPuntosEuros.add(lb_cabecera);
		 
		 lb_idDivisa.setBounds(offsetLateralColumna01,offsetSuperiorFila01,anchuraTexto, alturaInput);
		 lb_idDivisa.setFont(new Font(fuenteTexto,estiloTexto,tamTextoBotones));
		 lb_idDivisa.setVisible(true);
		 panelPuntosEuros.add(lb_idDivisa); 
		 text_idDivisa.setBounds(offsetLateralInputColumna01,offsetSuperiorFila01,anchuraInput, alturaInput);
		 text_idDivisa.setFont(new Font(fuenteTexto,estiloTexto,tamTextoBotones));
		 text_idDivisa.setVisible(true);
		 text_idDivisa.setEditable(false);
		 text_idDivisa.setText(idCanjeo+"");
		 panelPuntosEuros.add(text_idDivisa);
		 
		 lb_puntos.setBounds(offsetLateralColumna02,offsetSuperiorFila01,anchuraTexto, alturaInput);
		 lb_puntos.setFont(new Font(fuenteTexto,estiloTexto,tamTextoBotones));
		 lb_puntos.setVisible(true);
		 panelPuntosEuros.add(lb_puntos); 
		 text_puntos.setBounds(offsetLateralInputColumna02,offsetSuperiorFila01,anchuraInput, alturaInput);
		 text_puntos.setFont(new Font(fuenteTexto,estiloTexto,tamTextoBotones));
		 text_puntos.setVisible(true);
		 text_puntos.setEditable(false);
		 text_puntos.setText("1");
		 panelPuntosEuros.add(text_puntos);
		 
		 lb_euros.setBounds(offsetLateralColumna01,offsetSuperiorFila02,anchuraTexto, alturaInput);
		 lb_euros.setFont(new Font(fuenteTexto,estiloTexto,tamTextoBotones));
		 lb_euros.setVisible(true);
		 panelPuntosEuros.add(lb_euros); 
		 text_euros.setBounds(offsetLateralInputColumna01,offsetSuperiorFila02,anchuraInput, alturaInput);
		 text_euros.setFont(new Font(fuenteTexto,estiloTexto,tamTextoBotones));
		 text_euros.setVisible(true);
		 text_euros.setEditable(false);
		 text_euros.setText(canjeo+"");
		 panelPuntosEuros.add(text_euros);
		 
		 lb_modificar.setBounds(offsetLateralColumna02-anchuraTexto/2,offsetSuperiorFila05,3*anchuraTexto, alturaInput);
		 lb_modificar.setFont(new Font(fuenteTexto,estiloTexto,tamTextoBotones));
		 lb_modificar.setVisible(true);
		 panelPuntosEuros.add(lb_modificar); 
		 
		 lb_eurosfinal.setBounds(offsetLateralColumna02,offsetSuperiorFila06,anchuraTexto, alturaInput);
		 lb_eurosfinal.setFont(new Font(fuenteTexto,estiloTexto,tamTextoBotones));
		 lb_eurosfinal.setVisible(true);
		 panelPuntosEuros.add(lb_eurosfinal); 
		 text_eurosfinal.setBounds(offsetLateralInputColumna02,offsetSuperiorFila06,anchuraInput, alturaInput);
		 text_eurosfinal.setFont(new Font(fuenteTexto,estiloTexto,tamTextoBotones));
		 text_eurosfinal.setVisible(true);
		 panelPuntosEuros.add(text_eurosfinal);
		 
		 lb_puntosfinal.setBounds(offsetLateralColumna01,offsetSuperiorFila06,anchuraTexto, alturaInput);
		 lb_puntosfinal.setFont(new Font(fuenteTexto,estiloTexto,tamTextoBotones));
		 lb_puntosfinal.setVisible(true);
		 panelPuntosEuros.add(lb_puntosfinal); 
		 text_puntosfinal.setBounds(offsetLateralInputColumna01,offsetSuperiorFila06,anchuraInput, alturaInput);
		 text_puntosfinal.setFont(new Font(fuenteTexto,estiloTexto,tamTextoBotones));
		 text_puntosfinal.setVisible(true);
		 panelPuntosEuros.add(text_puntosfinal);
		 
		 botonModificar.setBounds(offsetLateralInputColumna02,offsetSuperiorFila07, iconoSalir.getIconWidth(), iconoSalir.getIconHeight());
		 botonModificar.setFont(new Font(fuenteTexto,estiloTexto,tamTextoBotones));
		 botonModificar.setVisible(true);
		 panelPuntosEuros.add(botonModificar);
	}//fin configurarInterfaz()
	
	void controlarEventos(final JPanel panel_Autenticacion,final JPanel panelDivisas,final int idCanjeo){
		 botonSalir.addActionListener(new ActionListener(){
				public void actionPerformed (ActionEvent e){	  
					int n = JOptionPane.showConfirmDialog (panelPuntosEuros,"�Desea salir del sistema?","�Salir Sistema?",JOptionPane.YES_NO_OPTION);
			        if(n==0){
			        	  boolean LogOut=Server.LogOut();
			        	  panelPuntosEuros.setVisible(false);
			        	  panel_Autenticacion.setVisible(true);
						  System.gc();
			        }
				}
		  });
		 
		 botonAtras.addActionListener(new ActionListener(){
				public void actionPerformed (ActionEvent e){	  
					panelPuntosEuros.setVisible(false);
					panelDivisas.setVisible(true);
					System.gc();
				}
		  });
		 
		 
		 botonModificar.addActionListener(new ActionListener(){
				public void actionPerformed (ActionEvent e){	
					String puntos=text_puntosfinal.getText();
					String eurosDescuentos=text_eurosfinal.getText();
					if(eurosDescuentos.compareTo("")==0 || puntos.compareTo("")==0){
						int n = JOptionPane.showConfirmDialog (panelPuntosEuros,"Error, campos de datos vac�os.","Datos nulos",JOptionPane.CLOSED_OPTION);
					}else{
						int n = JOptionPane.showConfirmDialog (panelPuntosEuros,"�Desea modificar la divisa?","Modificar divisa",JOptionPane.YES_NO_OPTION);
						if(n==0){
							boolean exito=Server.descontarEurosPuntos(idCanjeo+"",puntos,eurosDescuentos);
							if(exito){
								int j = JOptionPane.showConfirmDialog (panelPuntosEuros,"Divisa modificada con �xito.","Divisa modificada",JOptionPane.CLOSED_OPTION);
								panelPuntosEuros.setVisible(false);
								panelDivisas.setVisible(true);
							}else{
								int j = JOptionPane.showConfirmDialog (panelPuntosEuros,"Error modificando divisa.","Divisa no modificada",JOptionPane.CLOSED_OPTION);
							}
						}
					}
				}
		  });
	}//fin controlarEventos()
}
