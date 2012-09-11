/**
* AccesoSistema.java
*/
package loyalty.main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.AbstractButton;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import loyalty.bean.*;
import loyalty.control.Server;
import loyalty.gcompras.GCompras;


/**
* Incia el sistema de login de la aplicación Loyalty
*
* @author <a href="mailto:sergio.simon@gammasolutions.es">Sergio Simón Garrido</a>
* @version 1.0
*
* @since 1.0
*/
public class AccesoSistema {
	 JFrame framePrincipal=new JFrame();
	 JDialog  dialog = new JDialog(framePrincipal,"Recordatorio",true);
	 JPanel panel_Autenticacion=new JPanel();
	 
	 Icon iconoEntrar = new ImageIcon(getClass().getResource("/loyalty/images/entrar.png")); 
	 Icon imagenCabecera = new ImageIcon(getClass().getResource("/loyalty/images/cabeceraLogin.png"));
	 //Icon imagen_fondo = new ImageIcon(getClass().getResource("/loyalty/images/LogoGamma.png"));
	 Icon imagen_logo = new ImageIcon(getClass().getResource("/loyalty/images/gamma.gif"));
	 
	 Icon imagenRecordar = new ImageIcon(getClass().getResource("/loyalty/images/recordarLogin.png"));
	 Icon btnSolicitarPwd = new ImageIcon(getClass().getResource("/loyalty/images/btnSolicitarPwd.png"));
	 
	 JButton boton_acceso=new JButton("", iconoEntrar);
	  
	 JLabel fondo_imagen=new JLabel();
	 JLabel lb_cabecera=new JLabel(imagenCabecera);	 
	 
	
	 static JLabel lb_usuario = new JLabel("·USUARIO");
	 static JLabel lb_password=new JLabel("·CONTRASEÑA");
	 static JPasswordField text_password=new JPasswordField(50);
	 static JTextField text_usuario=new JTextField(50);
	 
	 //JLabel lb_olvido=new JLabel("·¿Olvidó su contraseña?", imagenRecordar, SwingConstants.LEFT);
	 //JLabel lb_olvido=new JLabel("·¿Olvidó su contraseña?");
	 JLabel lb_mailDialog=new JLabel("·Mail registrado:");
	 JTextField text_mail=new JTextField(50);
	 
	 JButton boton_recordarMail=new JButton("", btnSolicitarPwd);
	 
	 //JButton botonOlvido=new JButton("·¿Olvidó su contraseña?", imagenRecordar);
	 JButton botonOlvido=new JButton("", imagenRecordar);
	 
	 String permiso="";
	 String idTienda="";
	 String nombreTienda="";
	 int idCanjeo=0;
	 int idCompra=0;
		  
		 
		 /**
		 * Constructor de la clase que situa todos los componentes gráficos del sistema de login en la panatalla.
		 */
		 public AccesoSistema(){ 
			  configurarInterfaz();
		      controlarEventos();
			  
			  framePrincipal.addWindowListener(new WindowAdapter(){
						public void windowClosing(WindowEvent we){
							int n = JOptionPane.showConfirmDialog (framePrincipal,"¿Desea salir del sistema?","¿Salir Sistema?",JOptionPane.YES_NO_OPTION);
					        if(n==0){
					        	  boolean LogOut=Server.LogOut();
								  System.gc();
								  System.exit(0);
					        }
					        if(n==1){
					        	  framePrincipal.setDefaultCloseOperation(0); 
					        }
						}
			  });
		 }//Fin public GUI()  
		 
		 
		 /**
		 * Configura la interfaz grafica del sistema de logeo de la aplicacion
		 */
		 void configurarInterfaz(){
			 Color colorCasillaDesactivada = new Color(0xcacaca);
			 Color colorFondoPantalla = new Color(0xe0e2e9);
		     Color colorAzulProfundo = new Color(0x3e4559);
		     String fuenteTexto = "Trebuchet";
		     int estiloTexto = Font.PLAIN;

			 Toolkit tk = Toolkit.getDefaultToolkit();
		     Dimension pantalla=tk.getScreenSize();
		      
		     int tamTextoBotones;
			 int offsetBasico;
		     int alturaTitulo;
		     int alturaInput;
		     int anchuraEtiquetas;
		     int anchuraInput;
		     
		     int anchuraPopUp;
		     int alturaPopUp;
		     int posXPopUp;
		     int posYPopUp;
		     
		     int offsetSuperiorCabecera;
		     
		     int offsetSuperiorGeneral;
		     int offsetSuperiorUsuario;
		     int offsetSuperiorUsuarioInput;
		     int offsetSuperiorPasswordInput;
		     int offsetSuperiorPassword;
		     int offsetSuperiorOlvido;
		     int offsetSuperiorBoton;
		      
		     int offsetLateralGeneral;
		     int offsetLateralPassword;
		     int offsetLateralUsuario;
		     int offsetLateralInput;
		     int offsetLateralBoton;
		      
		     int anchuraBoton = iconoEntrar.getIconWidth();
		     int alturaBoton = iconoEntrar.getIconHeight();
		      
		     offsetSuperiorCabecera = 10;
		     
		     anchuraPopUp = 500;
		     alturaPopUp = 300;
		     posXPopUp = 450;
		     posYPopUp = 200;
		     
		     
		     if(pantalla.width>1024){
		    	  tamTextoBotones = 20;
				  offsetBasico = 10;
			      alturaTitulo = 40;
			      alturaInput = 40;
			      anchuraInput = 452;
			      
			      anchuraEtiquetas = 180;
			      
			      offsetSuperiorGeneral = (imagenCabecera.getIconHeight() + offsetSuperiorCabecera + offsetBasico);
			      offsetSuperiorUsuario = offsetSuperiorGeneral;
			      offsetSuperiorUsuarioInput = offsetSuperiorUsuario + offsetBasico + alturaInput;
			      offsetSuperiorPassword = offsetSuperiorUsuarioInput + offsetBasico + alturaInput;
			      offsetSuperiorPasswordInput = offsetSuperiorPassword + offsetBasico + alturaInput;
			      offsetSuperiorOlvido = offsetSuperiorPasswordInput + offsetBasico + alturaInput;
			      offsetSuperiorBoton = offsetSuperiorPasswordInput + offsetBasico + alturaTitulo;
			      
				  offsetLateralGeneral = (imagenCabecera.getIconWidth()/2) - anchuraInput/2;
				  offsetLateralPassword = offsetLateralGeneral + offsetBasico;
			      offsetLateralUsuario = offsetLateralPassword;
			      offsetLateralInput = offsetLateralPassword;
			      offsetLateralBoton = offsetLateralPassword + anchuraInput - iconoEntrar.getIconWidth();	      
		     }else if(pantalla.width<=800){
		    	 
		    	 tamTextoBotones = 20;
				  offsetBasico = 10;
			      alturaTitulo = 40;
			      alturaInput = 40;
			      anchuraInput = 452;
			      
			      anchuraEtiquetas = 180;
			      
			      
			     offsetSuperiorGeneral = (imagenCabecera.getIconHeight() + offsetSuperiorCabecera + offsetBasico);
			     offsetSuperiorUsuario = offsetSuperiorGeneral;
			     offsetSuperiorUsuarioInput = offsetSuperiorUsuario + offsetBasico + alturaInput;
			     offsetSuperiorPassword = offsetSuperiorUsuarioInput + offsetBasico + alturaInput;
			     offsetSuperiorPasswordInput = offsetSuperiorPassword + offsetBasico + alturaInput;
			     offsetSuperiorOlvido = offsetSuperiorPasswordInput + offsetBasico + alturaInput;
			     offsetSuperiorBoton = offsetSuperiorPasswordInput + (2*offsetBasico) + alturaTitulo;
			     
			     offsetLateralGeneral = (imagenCabecera.getIconWidth()/2) - anchuraInput/2;
				  offsetLateralPassword = offsetLateralGeneral + offsetBasico;
			     offsetLateralUsuario = offsetLateralPassword;
			     offsetLateralInput = offsetLateralPassword;
			     offsetLateralBoton = offsetLateralPassword + anchuraInput - iconoEntrar.getIconWidth();
		     }else{
		    	 
		    	 tamTextoBotones = 20;
				  offsetBasico = 10;
			      alturaTitulo = 40;
			      alturaInput = 40;
			      anchuraInput = 452;
			      
			      anchuraEtiquetas = 180;
			      
			      offsetSuperiorGeneral = (imagenCabecera.getIconHeight() + offsetSuperiorCabecera + offsetBasico);
			      offsetSuperiorUsuario = offsetSuperiorGeneral;
			      offsetSuperiorUsuarioInput = offsetSuperiorUsuario + offsetBasico + alturaInput;
			      offsetSuperiorPassword = offsetSuperiorUsuarioInput + offsetBasico + alturaInput;
			      offsetSuperiorPasswordInput = offsetSuperiorPassword + offsetBasico + alturaInput;
			      offsetSuperiorOlvido = offsetSuperiorPasswordInput + offsetBasico + alturaInput;
			      offsetSuperiorBoton = offsetSuperiorPasswordInput + offsetBasico + alturaTitulo;
			      
			      offsetLateralGeneral = (imagenCabecera.getIconWidth()/2) - anchuraInput/2;
				  offsetLateralPassword = offsetLateralGeneral + offsetBasico;
			      offsetLateralUsuario = offsetLateralPassword;
			      offsetLateralInput = offsetLateralPassword;
			      offsetLateralBoton = offsetLateralPassword + anchuraInput - iconoEntrar.getIconWidth();
			 }

		     //Añadimos y configuramos el panel_modificar
			 panel_Autenticacion.setLayout(null);
			 panel_Autenticacion.setBounds(0,0,pantalla.width, pantalla.height);
			 panel_Autenticacion.setBackground(colorFondoPantalla);
			 
			 lb_cabecera.setBounds(0,offsetSuperiorCabecera,791,83);
			 lb_cabecera.setVisible(true);
			 panel_Autenticacion.add(lb_cabecera);
			 
			 //TODO Quitamos la imagen del fondo por ahora
			 //fondo_imagen.setIcon(imagen_fondo);
		 	 //fondo_imagen.setBounds(offsetLateralGeneral,offsetSuperiorGeneral,imagen_fondo.getIconWidth(), imagen_fondo.getIconHeight());
		     panel_Autenticacion.add(fondo_imagen);
			 panel_Autenticacion.setVisible(true);
			 framePrincipal.add(panel_Autenticacion);
		 	  
		 	 lb_usuario.setBounds(offsetLateralUsuario,offsetSuperiorUsuario,anchuraEtiquetas, alturaTitulo);
		     lb_usuario.setForeground(colorAzulProfundo);
		 	 lb_usuario.setFont(new Font(fuenteTexto,estiloTexto,tamTextoBotones));
		 	 lb_usuario.setVisible(true);
		 	 panel_Autenticacion.add(lb_usuario);
		 	 text_usuario.setFont(new Font(fuenteTexto,estiloTexto,tamTextoBotones));
		 	 text_usuario.setBounds(offsetLateralInput,offsetSuperiorUsuarioInput,anchuraInput, alturaInput);
		 	 text_usuario.setBackground(Color.white);
		 	 text_usuario.setForeground(Color.black);
		 	 text_usuario.setEditable(true);
		 	 text_usuario.setText(null);
		 	 text_usuario.setVisible(true);
		 	 panel_Autenticacion.add(text_usuario);
		 		
		 	 lb_password.setBounds(offsetLateralPassword,offsetSuperiorPassword,anchuraEtiquetas, alturaTitulo);
		 	 lb_password.setForeground(colorAzulProfundo);
		 	 lb_password.setFont(new Font(fuenteTexto,estiloTexto,tamTextoBotones));
		 	 panel_Autenticacion.add(lb_password);
		 	 text_password.setFont(new Font(fuenteTexto,estiloTexto,tamTextoBotones));
		 	 text_password.setBounds(offsetLateralInput,offsetSuperiorPasswordInput,anchuraInput, alturaInput);
		 	 text_password.setBackground(Color.white);
		 	 text_password.setForeground(Color.black);
		 	 text_password.setEditable(true);
		 	 text_password.setText(null);
		 	 panel_Autenticacion.add(text_password);
		 	 
		 	 /*
		 	 lb_olvido.setBounds(offsetLateralPassword,offsetSuperiorOlvido,2*anchuraEtiquetas, alturaTitulo);
		 	 lb_olvido.setForeground(colorAzulProfundo);
		 	 lb_olvido.setFont(new Font(fuenteTexto,estiloTexto,tamTextoBotones-3));
		 	 lb_olvido.setToolTipText("Pulse aquí para ingresar el mail de registro en el sistema para " +
		 	 		"proceder con el envío de los datos de acceso al mismo.");
		 	 panel_Autenticacion.add(lb_olvido);*/
		 	 
		 	 //botonOlvido.setHorizontalTextPosition(AbstractButton.RIGHT);
		 	 botonOlvido.setBounds(offsetLateralPassword,offsetSuperiorOlvido,imagenRecordar.getIconWidth(), imagenRecordar.getIconHeight());
		 	 botonOlvido.setFont(new Font(fuenteTexto,estiloTexto,tamTextoBotones));
		 	 botonOlvido.setForeground(Color.WHITE);
		 	 botonOlvido.setHorizontalAlignment(AbstractButton.CENTER);
		 	 botonOlvido.setToolTipText("Pulse aquí para ingresar el mail de registro en el sistema para " +
		 	 		"proceder con el envío de los datos de acceso al mismo.");
		 	 panel_Autenticacion.add(botonOlvido);
		 	 
		 	 boton_acceso.setBounds(offsetLateralBoton,offsetSuperiorBoton,anchuraBoton, alturaBoton);
		 	 boton_acceso.setFont(new Font(fuenteTexto,estiloTexto,tamTextoBotones));
		 	 
		 	 panel_Autenticacion.add(boton_acceso);
		 	  	 	  
			 framePrincipal.setLayout(null);
			 framePrincipal.setSize(pantalla.width,pantalla.height-43);
			 framePrincipal.setIconImage(new ImageIcon(getClass().getResource("/loyalty/images/iconoLoyalty.gif")).getImage());
			 framePrincipal.setResizable(false);
			 framePrincipal.setTitle("LOYALTY");
			 framePrincipal.getContentPane().setBackground(Color.white);
			 framePrincipal.setVisible(true); 
			 
			 dialog.setLayout(null);
			 dialog.setBounds(posXPopUp,posYPopUp,anchuraPopUp,alturaPopUp);
			 
			 lb_mailDialog.setBounds(20,60,anchuraEtiquetas,alturaInput);
			 lb_mailDialog.setForeground(colorAzulProfundo);
			 lb_mailDialog.setFont(new Font(fuenteTexto,estiloTexto,tamTextoBotones));
			 dialog.add(lb_mailDialog);
			 text_mail.setFont(new Font(fuenteTexto,estiloTexto,tamTextoBotones));
			 text_mail.setBounds(20,100,anchuraInput,alturaInput);
			 text_mail.setBackground(Color.white);
			 text_mail.setForeground(Color.black);
			 text_mail.setEditable(true);
			 text_mail.setText(null);
			 dialog.add(text_mail);
			 
			 boton_recordarMail.setBounds(anchuraPopUp/2 - btnSolicitarPwd.getIconWidth()/2,160,btnSolicitarPwd.getIconWidth(),btnSolicitarPwd.getIconHeight());
			 boton_recordarMail.setFont(new Font(fuenteTexto,estiloTexto,tamTextoBotones));
		 	 dialog.add(boton_recordarMail);
		}//fin configurarInterfaz()
		 	 
		 
		 /**
		 * Registra los eventos para los objetos del sistema de acceso.
		 */
		 void controlarEventos(){
			  //Eventos textFields
			  text_usuario.addKeyListener(new PresionarTecla1());
		      text_password.addKeyListener(new PresionarTecla1());
		      //evento boton
		      boton_acceso.addActionListener(new ActionListener(){
					@SuppressWarnings("deprecation")
					public void actionPerformed (ActionEvent e){	  
						String user=text_usuario.getText();
						user=user.trim();
						String pass=text_password.getText();
						if(user.compareTo("")!=0 && pass.compareTo("")!=0){
							
							InfoLogin login= Server.LogIn(user, pass);
							if(login!=null){
								permiso=login.getPermiso();
								idTienda=login.getIdTienda();
								nombreTienda=login.getNombreTienda();
								int compra=login.getFactorDivisaCompra();
								double canjeo=login.getFactorDivisaCanjeo();
								String URL=login.getURL();
								String email=login.getEmail();
								idCompra=login.getIDDivisaCompra();
								idCanjeo=login.getIDDivisaCanjeo();
								new Inicial(user,pass,email,permiso,idTienda,nombreTienda,compra,canjeo,URL,
										framePrincipal,panel_Autenticacion,idCompra,idCanjeo);
								text_password.setText(null);
								text_usuario.setText(null);
							}
							else{
								JOptionPane.showMessageDialog(framePrincipal,"Error de identificiación de usuario" , "Erro de Autenticación",
								        JOptionPane.ERROR_MESSAGE);
								text_password.setText(null);	  
							}
				       	}
						else{
							if(user.compareTo("")==0 || pass.compareTo("")==0){
								JOptionPane.showMessageDialog(framePrincipal,"Error, campos de datos vacíos o nulos." , "Campos vacíos",
								        JOptionPane.ERROR_MESSAGE);
							}
						}
					}
			  } );	
		      
		      botonOlvido.addMouseListener(new MouseListener(){
					public void mouseClicked(MouseEvent arg0){
						dialog.setVisible(true);
					}
					public void mouseEntered(MouseEvent arg0){
						
					}
					public void mouseExited(MouseEvent arg0){
						
					}
					public void mousePressed(MouseEvent arg0){
						//dialog
					}
					public void mouseReleased(MouseEvent arg0){
						
					}
		      });
		      
//		      lb_olvido.addMouseListener(new MouseListener(){
//					public void mouseClicked(MouseEvent arg0){
//						dialog.setVisible(true);
//					}
//					public void mouseEntered(MouseEvent arg0){
//						
//					}
//					public void mouseExited(MouseEvent arg0){
//						
//					}
//					public void mousePressed(MouseEvent arg0){
//						//dialog
//					}
//					public void mouseReleased(MouseEvent arg0){
//						
//					}
//		      });
		      
		      boton_recordarMail.addActionListener(new ActionListener(){
					public void actionPerformed (ActionEvent e){
						String mail=text_mail.getText();
						boolean exito=Server.recuperarLogeo(mail);
						if(exito){
							JOptionPane.showMessageDialog(dialog,"Datos de acceso enviados al mail indicado" , "Datos enviados",
							        JOptionPane.CLOSED_OPTION);
						}else{
							JOptionPane.showMessageDialog(dialog,"Error, el mail indicado no esta registrado en la BBDD." , "Registro incorrecto",
							        JOptionPane.CLOSED_OPTION);
						}
						text_mail.setText("");
						dialog.setVisible(false);
				        System.gc();
					}
			  });
		 }
		 
		 
		 /**
		 * Comprueba que los datos de login son los correctos y permite o deniega el acceso cuando pulsamos intro.
		 *
		 * @author <a href="mailto:sergio.simon@gammasolutions.es">Sergio Simón Garrido</a>
		 * @version 1.0
		 *
		 * @since 1.0
		 */
		 public class PresionarTecla1 extends KeyAdapter{  
			@SuppressWarnings("deprecation")
			public void keyPressed(KeyEvent ke){
			    if(ke.getKeyCode() == KeyEvent.VK_ENTER){
			       	String user=text_usuario.getText();
			       	user=user.trim();
			       	String pass=text_password.getText();
			       	if(user.compareTo("")!=0 && pass.compareTo("")!=0){
						
			       		InfoLogin login= Server.LogIn(user, pass);
						if(login!=null){
							permiso=login.getPermiso();
							idTienda=login.getIdTienda();
							nombreTienda=login.getNombreTienda();
							int compra=login.getFactorDivisaCompra();
							double canjeo=login.getFactorDivisaCanjeo();
							String URL=login.getURL();
							String email=login.getEmail();
							idCompra=login.getIDDivisaCompra();
							idCanjeo=login.getIDDivisaCanjeo();
							new Inicial(user,pass,email,permiso,idTienda,nombreTienda,compra,canjeo,URL,
									framePrincipal,panel_Autenticacion,idCompra,idCanjeo);
							text_password.setText(null);
							text_usuario.setText(null);
						}else{
							JOptionPane.showMessageDialog(framePrincipal,"Error de identificiación de usuario" , "Erro de Autenticación",
							        JOptionPane.ERROR_MESSAGE);
							text_password.setText(null); 
						}
			       	}else{
						if(user.compareTo("")==0 || pass.compareTo("")==0){
							JOptionPane.showMessageDialog(framePrincipal,"Error, campos de datos vacíos o nulos." , "Campos vacíos",
							        JOptionPane.ERROR_MESSAGE);
						}
					}
			    }
			}
		 }//Fin PresionarTecla1
}

