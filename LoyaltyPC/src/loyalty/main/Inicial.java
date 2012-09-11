/**
 * Inicial.java
 */
package loyalty.main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URLConnection;
import java.net.URL;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import loyalty.control.Server;
import loyalty.gclientes.GClientes;
import loyalty.gcompras.GCompras;
import loyalty.gdivisas.GDivisas;
import loyalty.gpromociones.GPromociones;


/**
* Incia el menú principal de la aplicación Loyalty PC
*
* @author <a href="mailto:sergio.simon@gammasolutions.es">Sergio Simón Garrido</a>
* @version 1.0
*
* @since 1.0
*/
public class Inicial {
	
	JFrame frameInicial;
	 JPanel panelInicial=new JPanel();
	 JDialog  dialog = new JDialog(frameInicial,"Contacta",true);
	 
	 Icon iconoBotonCompras = new ImageIcon(getClass().getResource("/loyalty/images/iconoLoyalty.gif"));  
	 Icon imagenCabecera = new ImageIcon(getClass().getResource("/loyalty/images/cabecera.png"));
	 
	 //TODO
	 ImageIcon iconoSalir = new ImageIcon(getClass().getResource("/loyalty/images/exit.png"));
	 ImageIcon imagenBotonEnviar = new ImageIcon(getClass().getResource("/loyalty/images/enviarIncidencia.png"));
	 
	 ImageIcon imagen_compras = new ImageIcon(getClass().getResource("/loyalty/images/btnCompras.png"));
	 ImageIcon imagen_promociones = new ImageIcon(getClass().getResource("/loyalty/images/btnPromos.png"));
	 ImageIcon imagen_clientes = new ImageIcon(getClass().getResource("/loyalty/images/btnClientes.png"));
	 ImageIcon imagen_contactar = new ImageIcon(getClass().getResource("/loyalty/images/btnContactar.png"));
	 ImageIcon imagen_divisas = new ImageIcon(getClass().getResource("/loyalty/images/btnGestionDivisas.png"));
		
	 JButton botonCompras=new JButton("",imagen_compras);
	 JButton botonPromociones=new JButton("",imagen_promociones);
	 JButton botonClientes=new JButton("",imagen_clientes);
	 JButton botonContacta=new JButton("",imagen_contactar); 
	 JButton botonDivisas=new JButton("",imagen_divisas);
	 JButton botonSalir=new JButton("", iconoSalir);
	 JButton botonEnviarIncidencia=new JButton("", imagenBotonEnviar);
	 
	 
	 JLabel titulo = new JLabel("LOYALTY:");
	 JLabel tienda = new JLabel("");
		
	 JLabel fondo_imagen=new JLabel();
	 JLabel lb_cabecera=new JLabel(imagenCabecera);
	 JLabel imagenCorporativa=new JLabel();
	 ImageIcon imagen_corporativa = null;
			
	 
	 JTextArea areaFoto=new JTextArea();
	 
	 String User="";
	 String Pass="";
	 String permiso="";
	 String idTienda="";
	 String nombreTienda="";
	 int compra=0;
	 double canjeo=0;
	 String URL="";
	 String email="";
	 int idCompra;
	 int idCanjeo;
	 
	 
	 JLabel lb_emailDialog=new JLabel("·Email:");
	 JLabel lb_asuntoDialog=new JLabel("·Asunto:");
	 JTextField text_emailDialog=new JTextField(50);
	 JTextArea areaDialog=new JTextArea();

	 /**
	 * Constructor de la clase Inicial que genera el menu principal con todas su opciones
	 */
	 public Inicial(String User,String pass,String email,String permiso,String idTienda,String nombreTienda,int compra,double canjeo,
			 String URL,JFrame framePrincipal,JPanel panel_Autenticacion,int idCompra,int idCanjeo){
		 tienda.setText(nombreTienda);
		 this.User=User;
		 this.Pass=pass;
		 this.permiso=permiso;
		 this.idTienda=idTienda;
		 this.nombreTienda=nombreTienda;
		 this.compra=compra;
		 this.canjeo=canjeo;
		 this.URL=URL;
		 this.email=email;
		 this.idCompra=idCompra;
		 this.idCanjeo=idCanjeo;
		 frameInicial=framePrincipal;
		 panel_Autenticacion.setVisible(false);
		 descargarImagen();
		 configurarInterfaz();
		 controlarEventos(panel_Autenticacion,idCompra,idCanjeo);
	 }
	 
	 /**
	  * Descarga la imagen o logo corporativo del local donde el usuario de la aplicación se ha logeado
	  */
	 public void descargarImagen(){
		 
		 try {
				// Url con la foto
				URL url = new URL(URL);
				// establecemos conexion
				URLConnection urlCon = url.openConnection();

				// Sacamos por pantalla el tipo de fichero
				System.out.println(urlCon.getContentType());

				// Se obtiene el inputStream de la foto web y se abre el fichero
				// local.
				InputStream is = urlCon.getInputStream();
				FileOutputStream fos = new FileOutputStream("C:/Users/Sergio/Desktop/foto.jpg");

				// Lectura de la foto de la web y escritura en fichero local
				byte[] array = new byte[1000]; // buffer temporal de lectura.
				int leido = is.read(array);
				while (leido > 0) {
					fos.write(array, 0, leido);
					leido = is.read(array);
				}
				
				// cierre de conexion y fichero.
				is.close();
				fos.close();
				this.imagen_corporativa= new ImageIcon("C:/Users/Sergio/Desktop/foto.jpg");
			} catch (Exception e) {
				e.printStackTrace();
			}
	 }//fin descargarImagen()
	

	 
	 /**
	  * Configura la interfaz gráfica del menú principal de la aplicación Loyalty
	  */
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
		 int anchuraTitulo = 300;
		 int offsetLateralTitulo = 100;
		 int offsetSuperiorTitulo = 15;
	     
		 int anchuraDialogo=500;
		 int alturaDialogo=550;
		 int posXDialogo=(pantalla.width/2)-(anchuraDialogo/2);
		 int posYDialogo=(pantalla.height/2)-(alturaDialogo/2);
		 
	     
	     //Añadimos y configuramos el panel_modificar
	     panelInicial.setLayout(null);
	     panelInicial.setBounds(0,0,pantalla.width, pantalla.height);
	     panelInicial.setBackground(colorFondoPantalla);
		 frameInicial.add(panelInicial);
		 
		 titulo.setBounds(offsetLateralTitulo, offsetSuperiorTitulo, anchuraTitulo, alturaTitulo);
		 titulo.setFont(new Font(fuenteTexto,estiloTexto,30));
		 titulo.setForeground(colorFondoPantalla);
		 titulo.setVisible(true);
		 panelInicial.add(titulo);
		 //tienda.setBounds(offsetLateralTitulo+anchuraTitulo, offsetSuperiorTitulo, anchuraTitulo, alturaTitulo);
		 tienda.setBounds(anchuraTitulo, offsetSuperiorTitulo, anchuraTitulo, alturaTitulo);
		 
		 tienda.setFont(new Font(fuenteTexto,estiloTexto,30));
		 tienda.setForeground(colorFondoPantalla);
		 tienda.setVisible(true);
		 panelInicial.add(tienda);
	     
		 botonSalir.setBounds(posXExit, posYExit, iconoSalir.getIconWidth(), iconoSalir.getIconHeight());
	     botonSalir.setFont(new Font(fuenteTexto,estiloTexto,tamTextoBotones));
	     botonSalir.setVisible(true);
	     panelInicial.add(botonSalir);
		 
	     lb_cabecera.setBounds(0,0,1400,83);
		 lb_cabecera.setVisible(true);
		 panelInicial.add(lb_cabecera);
		  
	  	 imagenCorporativa.setIcon(imagen_corporativa);
	  	 imagenCorporativa.setBounds(offsetLateralColumna03,offsetSuperiorFila03,anchuraBotonesMenu-100, alturaBotonesMenu);
	  	 imagenCorporativa.setFont(new Font(fuenteTexto,estiloTexto,tamTextoBotones));
	  	 imagenCorporativa.setVisible(true);
	  	 panelInicial.add(imagenCorporativa);
	     
	     botonCompras.setBounds(offsetLateralColumna01,offsetSuperiorFila01,anchuraBotonesMenu, alturaBotonesMenu);
	  	 botonCompras.setFont(new Font(fuenteTexto,estiloTexto,tamTextoBotones));
	  	 botonCompras.setVisible(true);
	  	 panelInicial.add(botonCompras);

	     botonClientes.setBounds(offsetLateralColumna02,offsetSuperiorFila01,anchuraBotonesMenu, alturaBotonesMenu);
	     botonClientes.setFont(new Font(fuenteTexto,estiloTexto,tamTextoBotones));
	     botonClientes.setVisible(true);
	     panelInicial.add(botonClientes); 	

	     botonPromociones.setBounds(offsetLateralColumna01,offsetSuperiorFila02,anchuraBotonesMenu, alturaBotonesMenu);
	     botonPromociones.setFont(new Font(fuenteTexto,estiloTexto,tamTextoBotones));
	     botonPromociones.setVisible(true);
	     panelInicial.add(botonPromociones); 	
	     
	     botonDivisas.setBounds(offsetLateralColumna02,offsetSuperiorFila02,anchuraBotonesMenu, alturaBotonesMenu);
	     botonDivisas.setFont(new Font(fuenteTexto,estiloTexto,tamTextoBotones));
	     botonDivisas.setVisible(true);
	     panelInicial.add(botonDivisas); 
	     
	     botonContacta.setBounds(offsetLateralColumna02-anchuraBotonesMenu/2,offsetSuperiorFila03,anchuraBotonesMenu, alturaBotonesMenu);
	     botonContacta.setFont(new Font(fuenteTexto,estiloTexto,tamTextoBotones));
	     botonContacta.setVisible(true);
	     panelInicial.add(botonContacta); 
	     
	     dialog.setLayout(null);
		 dialog.setBounds(posXDialogo,posYDialogo,anchuraDialogo,alturaDialogo);
		 dialog.setIconImage(new ImageIcon(getClass().getResource("/loyalty/images/iconoLoyalty.gif")).getImage());
		 
		 lb_emailDialog.setBounds(20,60,300,30);
		 lb_emailDialog.setFont(new Font(fuenteTexto,estiloTexto,tamTextoBotones));
		 dialog.add(lb_emailDialog);
		 text_emailDialog.setFont(new Font(fuenteTexto,estiloTexto,tamTextoBotones));
		 text_emailDialog.setBounds(30,100,400,40);
		 text_emailDialog.setBackground(Color.white);
		 text_emailDialog.setForeground(Color.black);
		 text_emailDialog.setEditable(true);
		 text_emailDialog.setText(null);
		 dialog.add(text_emailDialog);
		 
		 lb_asuntoDialog.setBounds(20,160,300,30);
		 lb_asuntoDialog.setFont(new Font(fuenteTexto,estiloTexto,tamTextoBotones));
		 dialog.add(lb_asuntoDialog);
		 areaDialog.setFont(new Font(fuenteTexto,estiloTexto,tamTextoBotones));
		 areaDialog.setBounds(30,200,400,150);
		 areaDialog.setBackground(Color.white);
		 areaDialog.setForeground(Color.black);
		 areaDialog.setEditable(true);
		 areaDialog.setLineWrap(true);
		 areaDialog.setText(null);
		 dialog.add(areaDialog);
		 
		 botonEnviarIncidencia.setBounds(430-imagenBotonEnviar.getIconWidth(),380,imagenBotonEnviar.getIconWidth(),imagenBotonEnviar.getIconHeight());
		 botonEnviarIncidencia.setFont(new Font(fuenteTexto,estiloTexto,tamTextoBotones));
	 	 dialog.add(botonEnviarIncidencia);
	 }//fin configurarInterfaz()
	 
	 /**
	  * Cotrola los eventos de los objeto para la clase Inicial.java
	  * @param panel_Autenticacion JPanel para volver al panel de logeo si el usuario decide deslogearse
	  * @param idCompra int con el identificador del cambio de divisas acumular puntos
	  * @param idCanjeo int con el identificador del cambio de divisas para descontar euros
	  */
	 void controlarEventos(final JPanel panel_Autenticacion,final int idCompra,final int idCanjeo){
		 botonSalir.addActionListener(new ActionListener(){
				public void actionPerformed (ActionEvent e){	  
					int n = JOptionPane.showConfirmDialog (panelInicial,"¿Desea cerrar la sesión?","¿Cerrar sesión?",JOptionPane.YES_NO_OPTION);
			        if(n==0){
			        	  boolean LogOut=Server.LogOut();
			        	  panelInicial.setVisible(false);
			        	  panel_Autenticacion.setVisible(true);
			        	  imagenCorporativa.removeAll();
			        	  imagenCorporativa.setIcon(null);
			        	  imagenCorporativa.setText("");
						  System.gc();
			        }
				}
		  });	
		 
		 botonCompras.addActionListener(new ActionListener(){
				public void actionPerformed (ActionEvent e){	  
					new GCompras(panelInicial, panel_Autenticacion,frameInicial,idTienda);
				}
		  });
		 
		 botonClientes.addActionListener(new ActionListener(){
				public void actionPerformed (ActionEvent e){	  
					new GClientes(panelInicial, panel_Autenticacion,frameInicial,User,Pass,permiso,idTienda,nombreTienda,
							compra,canjeo,URL);
				}
		  });	
		 
		 botonPromociones.addActionListener(new ActionListener(){
				public void actionPerformed (ActionEvent e){	  
					new GPromociones(panelInicial, panel_Autenticacion,frameInicial,User,Pass,permiso,idTienda,nombreTienda,
							compra,canjeo,URL);
				}
		  });
		 
		 botonContacta.addActionListener(new ActionListener(){
				public void actionPerformed (ActionEvent e){	  
					dialog.setVisible(true);
				}
		  });
		 
		 botonDivisas.addActionListener(new ActionListener(){
				public void actionPerformed (ActionEvent e){	  
					new GDivisas(panelInicial,panel_Autenticacion,frameInicial,idTienda,idCompra,idCanjeo,compra,canjeo);
				}
		  });
		 
		 botonEnviarIncidencia.addActionListener(new ActionListener(){
				public void actionPerformed (ActionEvent e){	  
					String mail=text_emailDialog.getText();
					String asunto=areaDialog.getText();
					boolean exito=Server.contactarAdministracion(mail,"1",asunto);
					if(exito){
						JOptionPane.showMessageDialog(dialog,"Incidencia enviada correctamente." , "Incidencia enviada",
						        JOptionPane.CLOSED_OPTION);
					}else{
						JOptionPane.showMessageDialog(dialog,"Error enviando incidencia." , "Error incidencia",
						        JOptionPane.CLOSED_OPTION);
					}
					text_emailDialog.setText("");
					dialog.setVisible(false);
			        System.gc();
				}
		  });
	 }//fin controlarEventos()
}
