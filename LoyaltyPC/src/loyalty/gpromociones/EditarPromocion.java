package loyalty.gpromociones;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
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
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import loyalty.bean.Promocion;
import loyalty.control.Server;
import com.toedter.calendar.JCalendar;



public class EditarPromocion {

	JFrame frameEditar;
	JPanel panelEditar=new JPanel();
	
	ImageIcon fechaDcha = new ImageIcon(getClass().getResource("/loyalty/images/meterFechaDcha.png"));
	ImageIcon fechaIzda = new ImageIcon(getClass().getResource("/loyalty/images/meterFechaIzda.png"));
	
	ImageIcon iconoAlta = new ImageIcon(getClass().getResource("/loyalty/images/altaPromocion.png"));
	//ImageIcon imagen_consulta = new ImageIcon(getClass().getResource("/loyalty/images/btnConsultaCliente.png"));
	ImageIcon iconoSalir = new ImageIcon(getClass().getResource("/loyalty/images/exit.png")); 
	ImageIcon iconoAtras = new ImageIcon(getClass().getResource("/loyalty/images/btnVolver.png"));
	 
	ImageIcon iconoEditar = new ImageIcon(getClass().getResource("/loyalty/images/editarPromocion.png"));
	
	Icon imagenCabecera = new ImageIcon(getClass().getResource("/loyalty/images/cabecera.png"));
	
	JButton botonAtras=new JButton("", iconoAtras);
	JButton botonSalir=new JButton("", iconoSalir);
	JButton botonEditar=new JButton("", iconoEditar);
	JButton botonFIni=new JButton("", fechaIzda);
	JButton botonFFin=new JButton("", fechaDcha);
	
	JLabel fondo_imagen=new JLabel();
	JLabel lb_cabecera=new JLabel(imagenCabecera);

	JLabel titulo = new JLabel("EDITAR PROMOCIÓN");
	
	JLabel lb_idPromocion=new JLabel("·Id. promoción:");
	JLabel lb_idTienda=new JLabel("·Id. tienda:");
	JLabel lb_descripcion=new JLabel("·Descripción:");
	JLabel lb_fechaIni=new JLabel("·Fecha inicio:");
	JLabel lb_fechaFin=new JLabel("·Fecha fin:");
	JLabel lb_puntosNecesarios=new JLabel("·Puntos:");
	JLabel lb_importeNecesario=new JLabel("·Importe:");
	JLabel lb_url=new JLabel("·URL:");
	
	JCalendar calendario=new JCalendar();
	
	JTextField text_idPromocion=new JTextField(50);
	JTextField text_idTienda=new JTextField(50);
	JTextField text_descripcion=new JTextField(50);
	JTextField text_fechaIni=new JTextField(50);
	JTextField text_fechaFin=new JTextField(50);
	JTextField text_puntosNecesarios=new JTextField(50);
	JTextField text_importeNecesario=new JTextField(50);
	JTextField text_url=new JTextField(50);
	
	/**
	* Clase KeyIconCellRenderer que añade iconos al defaultablemodel para manejar varias opciones (editar, comentarios...)
	*
	* @author <a href="mailto:sergio.simon@gammasolutions.es">Sergio Simón Garrido</a>
	* @version 1.0
	*
	* @since 1.0
	*/
	class KeyIconCellRenderer extends DefaultTableCellRenderer{   

			public KeyIconCellRenderer(int i){   
				if(i==9){
					 setIcon(new ImageIcon(getClass().getResource("/loyalty/images/activar_promo.png")));
			   	     this.setHorizontalAlignment(SwingConstants.CENTER);
		   	         this.setToolTipText("Activar promoción");
				}
				if(i==10){
					   setIcon(new ImageIcon(getClass().getResource("/loyalty/images/eliminar.png")));
			   	       this.setHorizontalAlignment(SwingConstants.CENTER);
			   	       this.setToolTipText("Baja promoción");
				}
				if(i==11){
					  setIcon(new ImageIcon(getClass().getResource("/loyalty/images/editar_promo.png")));
			   	       this.setHorizontalAlignment(SwingConstants.CENTER);
			   	       this.setToolTipText("Editar promoción");
				}
		    }   
	} 
	
	//modelo de tabla para alta prestamo
	DefaultTableModel dtmConsulta= new DefaultTableModel(){
		
			private static final long serialVersionUID = 1L;

			public boolean isCellEditable(int row, int column){
				return false;
			}
	};
		
	//Tabla necesario
	JTable tabla_Consulta = new JTable(dtmConsulta);
	JScrollPane scrollPaneConsulta = new JScrollPane(tabla_Consulta);

	public EditarPromocion(String idPromocion,String idTienda,String descripcion,String fechaInicio,String fechaFin,
			String puntos,String importe,String url,String activa,String nombreTienda,JFrame frameConsulta,
			JPanel panel_Autenticacion,JPanel panelConsulta,DefaultTableModel dtmConsulta,JTable tabla_Consulta){
		this.dtmConsulta=dtmConsulta;
		this.tabla_Consulta=tabla_Consulta;
		frameEditar=frameConsulta;
		panelConsulta.setVisible(false);
		configurarInterfaz(idPromocion,idTienda,descripcion,fechaInicio,fechaFin,puntos,importe,url);
		controlarEventos(panel_Autenticacion,panelConsulta,nombreTienda,idTienda);
	}
	
	void configurarInterfaz(String idPromocion,String idTienda,String descripcion,String fechaInicio,String fechaFin,
			String puntos,String importe,String url){
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
		 int offsetSuperiorFila09 = offsetSuperiorFila08 + offsetBasico + alturaInput;
		 int offsetSuperiorFila10 = offsetSuperiorFila09 + offsetBasico + alturaInput;
		 
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
		 int anchuraCalendario=380;
		 int alturaCalendario=170;
		 
		 //Añadimos y configuramos el panelAlta
	     panelEditar.setLayout(null);
	     panelEditar.setBounds(0,0,pantalla.width, pantalla.height);
	     panelEditar.setBackground(colorFondoPantalla);
		 frameEditar.add(panelEditar);
		 
		 titulo.setBounds(offsetLateralTitulo, offsetSuperiorTitulo, anchuraTitulo, alturaTitulo);
		 titulo.setFont(new Font(fuenteTexto,estiloTexto,30));
		 titulo.setForeground(colorFondoPantalla);
		 titulo.setVisible(true);
		 panelEditar.add(titulo);
		 
		 botonAtras.setBounds(posXAtras,posYAtras,iconoSalir.getIconWidth(), iconoSalir.getIconHeight());
	     botonAtras.setFont(new Font(fuenteTexto,estiloTexto,tamTextoBotones));
	     botonAtras.setVisible(true);
	     panelEditar.add(botonAtras); 	
	     
	     botonSalir.setBounds(posXExit, posYExit, iconoSalir.getIconWidth(), iconoSalir.getIconHeight());
	     botonSalir.setFont(new Font(fuenteTexto,estiloTexto,tamTextoBotones));
	     botonSalir.setVisible(true);
	     panelEditar.add(botonSalir);
	     
		 lb_cabecera.setBounds(0,0,1400,83);
		 lb_cabecera.setVisible(true);
		 panelEditar.add(lb_cabecera);

	     botonAtras.setVerticalTextPosition(AbstractButton.BOTTOM);//Alineamos el texto abajo
	     botonAtras.setHorizontalTextPosition(AbstractButton.CENTER);
	     botonSalir.setVerticalTextPosition(AbstractButton.BOTTOM);//Alineamos el texto abajo
	     botonSalir.setHorizontalTextPosition(AbstractButton.CENTER);
	     
	     lb_idPromocion.setBounds(offsetLateralColumna01,offsetSuperiorFila01,anchuraTexto, alturaInput);
	     lb_idPromocion.setFont(new Font(fuenteTexto,estiloTexto,tamTextoBotones));
	     lb_idPromocion.setVisible(true);
	     panelEditar.add(lb_idPromocion); 
		 text_idPromocion.setBounds(offsetLateralInputColumna01,offsetSuperiorFila01,anchuraInput, alturaInput);
		 text_idPromocion.setFont(new Font(fuenteTexto,estiloTexto,tamTextoBotones));
		 text_idPromocion.setVisible(true);
		 text_idPromocion.setEditable(false);
		 text_idPromocion.setText(idPromocion);
		 panelEditar.add(text_idPromocion);
		 
		 lb_idTienda.setBounds(offsetLateralColumna02,offsetSuperiorFila01,anchuraTexto, alturaInput);
		 lb_idTienda.setFont(new Font(fuenteTexto,estiloTexto,tamTextoBotones));
		 lb_idTienda.setVisible(true);
	     panelEditar.add(lb_idTienda); 
		 text_idTienda.setBounds(offsetLateralInputColumna02,offsetSuperiorFila01,anchuraInput, alturaInput);
		 text_idTienda.setFont(new Font(fuenteTexto,estiloTexto,tamTextoBotones));
		 text_idTienda.setVisible(true);
		 text_idTienda.setEditable(false);
		 text_idTienda.setText(idTienda);
		 panelEditar.add(text_idTienda);
		 
		 lb_descripcion.setBounds(offsetLateralColumna01,offsetSuperiorFila02,anchuraTexto, alturaInput);
		 lb_descripcion.setFont(new Font(fuenteTexto,estiloTexto,tamTextoBotones));
		 lb_descripcion.setVisible(true);
		 panelEditar.add(lb_descripcion); 
		 text_descripcion.setBounds(offsetLateralInputColumna01,offsetSuperiorFila02,anchuraInput, alturaInput);
		 text_descripcion.setFont(new Font(fuenteTexto,estiloTexto,tamTextoBotones));
		 text_descripcion.setVisible(true);
		 text_descripcion.setText(descripcion);
		 panelEditar.add(text_descripcion);
		 
		 lb_url.setBounds(offsetLateralColumna02,offsetSuperiorFila02,anchuraTexto, alturaInput);
		 lb_url.setFont(new Font(fuenteTexto,estiloTexto,tamTextoBotones));
		 lb_url.setVisible(true);
		 panelEditar.add(lb_url); 
		 text_url.setBounds(offsetLateralInputColumna02,offsetSuperiorFila02,anchuraInput, alturaInput);
		 text_url.setFont(new Font(fuenteTexto,estiloTexto,tamTextoBotones));
		 text_url.setVisible(true);
		 text_url.setText(url);
		 panelEditar.add(text_url);
		 
		 lb_puntosNecesarios.setBounds(offsetLateralColumna01,offsetSuperiorFila03,anchuraTexto, alturaInput);
		 lb_puntosNecesarios.setFont(new Font(fuenteTexto,estiloTexto,tamTextoBotones));
		 lb_puntosNecesarios.setVisible(true);
		 panelEditar.add(lb_puntosNecesarios); 
		 text_puntosNecesarios.setBounds(offsetLateralInputColumna01,offsetSuperiorFila03,anchuraInput, alturaInput);
		 text_puntosNecesarios.setFont(new Font(fuenteTexto,estiloTexto,tamTextoBotones));
		 text_puntosNecesarios.setVisible(true);
		 text_puntosNecesarios.setText(puntos);
		 panelEditar.add(text_puntosNecesarios);
		 
		 lb_importeNecesario.setBounds(offsetLateralColumna02,offsetSuperiorFila03,anchuraTexto, alturaInput);
		 lb_importeNecesario.setFont(new Font(fuenteTexto,estiloTexto,tamTextoBotones));
		 lb_importeNecesario.setVisible(true);
		 panelEditar.add(lb_importeNecesario); 
		 text_importeNecesario.setBounds(offsetLateralInputColumna02,offsetSuperiorFila03,anchuraInput, alturaInput);
		 text_importeNecesario.setFont(new Font(fuenteTexto,estiloTexto,tamTextoBotones));
		 text_importeNecesario.setVisible(true);
		 text_importeNecesario.setText(importe);
		 panelEditar.add(text_importeNecesario);
		 
		 lb_fechaIni.setBounds(offsetLateralColumna01,offsetSuperiorFila04,anchuraTexto, alturaInput);
		 lb_fechaIni.setFont(new Font(fuenteTexto,estiloTexto,tamTextoBotones));
		 lb_fechaIni.setVisible(true);
		 panelEditar.add(lb_fechaIni); 
		 text_fechaIni.setBounds(offsetLateralInputColumna01,offsetSuperiorFila04,anchuraInput, alturaInput);
		 text_fechaIni.setFont(new Font(fuenteTexto,estiloTexto,tamTextoBotones));
		 text_fechaIni.setVisible(true);
		 text_fechaIni.setEditable(false);
		 text_fechaIni.setText(fechaInicio);
		 panelEditar.add(text_fechaIni);
		 
		 lb_fechaFin.setBounds(offsetLateralColumna02,offsetSuperiorFila04,anchuraTexto, alturaInput);
		 lb_fechaFin.setFont(new Font(fuenteTexto,estiloTexto,tamTextoBotones));
		 lb_fechaFin.setVisible(true);
		 panelEditar.add(lb_fechaFin); 
		 text_fechaFin.setBounds(offsetLateralInputColumna02,offsetSuperiorFila04,anchuraInput, alturaInput);
		 text_fechaFin.setFont(new Font(fuenteTexto,estiloTexto,tamTextoBotones));
		 text_fechaFin.setVisible(true);
		 text_fechaFin.setEditable(false);
		 text_fechaFin.setText(fechaFin);
		 panelEditar.add(text_fechaFin);
		 
		 calendario.setBounds(offsetLateralColumna02-anchuraTexto,offsetSuperiorFila05,anchuraTexto, alturaInput);
		 calendario.setSize(anchuraCalendario,alturaCalendario); 
		 panelEditar.add(calendario);
		 
		 botonFIni.setBounds(offsetLateralColumna02-2*anchuraTexto,offsetSuperiorFila05,fechaDcha.getIconWidth(),fechaDcha.getIconHeight());
		 botonFIni.setFont(new Font(fuenteTexto,estiloTexto,tamTextoBotones));
		 botonFIni.setVisible(true);
		 panelEditar.add(botonFIni); 	
		    
		 botonFFin.setBounds(offsetLateralColumna02+2*anchuraTexto,offsetSuperiorFila05,fechaDcha.getIconWidth(),fechaDcha.getIconHeight());
		 botonFFin.setFont(new Font(fuenteTexto,estiloTexto,tamTextoBotones));
		 botonFFin.setVisible(true);
		 panelEditar.add(botonFFin); 	

	     botonEditar.setBounds(offsetLateralInputColumna02+anchuraInput-iconoEditar.getIconWidth(),offsetSuperiorFila09,iconoEditar.getIconWidth(), iconoEditar.getIconHeight());
	     botonEditar.setFont(new Font(fuenteTexto,estiloTexto,tamTextoBotones));
	     botonEditar.setVisible(true);
	     panelEditar.add(botonEditar);
	}
	
	void controlarEventos(final JPanel panel_Autenticacion,final JPanel panelConsulta,final String nombreTienda,
			final String idTienda){
		botonSalir.addActionListener(new ActionListener(){
			public void actionPerformed (ActionEvent e){	  
				int n = JOptionPane.showConfirmDialog (panelEditar,"¿Desea cerrar sesión?","¿Cerrar sesión?",JOptionPane.YES_NO_OPTION);
		        if(n==0){
		        	  boolean LogOut=Server.LogOut();
		        	  panelEditar.setVisible(false);
		        	  panel_Autenticacion.setVisible(true);
					  System.gc();
		        }
			}
		});
	 
		botonAtras.addActionListener(new ActionListener(){
			public void actionPerformed (ActionEvent e){	  
				panelEditar.setVisible(false);
				panelConsulta.setVisible(true);
				System.gc();
			}
		});
		
		botonAtras.addActionListener(new ActionListener(){
			public void actionPerformed (ActionEvent e){	  
				panelEditar.setVisible(false);
				panelConsulta.setVisible(true);
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
					int min = calendar.get(calendar.MINUTE);
					int seg = calendar.get(calendar.SECOND);
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
					int min = calendar.get(calendar.MINUTE);
					int seg = calendar.get(calendar.SECOND);
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
					text_fechaFin.setText(año+"-"+mes+"-"+dia+" "+hora+":"+minutos+":"+segundos);
				}
			});
		
		botonEditar.addActionListener(new ActionListener(){
			public void actionPerformed (ActionEvent e){	
				String idPromo=text_idPromocion.getText();
				String descripcion=text_descripcion.getText();
				String fechaIni=text_fechaIni.getText();
				fechaIni=fechaIni.substring(0,19);
				String fechaFin=text_fechaFin.getText();
				fechaFin=fechaFin.substring(0,19);
				String puntos=text_puntosNecesarios.getText();
				String importe=text_importeNecesario.getText();
				String url=text_url.getText();
				Promocion promocion=Server.editarPromocion(idPromo,descripcion,fechaIni,fechaFin,puntos,importe,url);
				if(promocion!=null){
					 int n=JOptionPane.showConfirmDialog(panelConsulta,"Promoción editada con éxito.","Promoción editada",JOptionPane.CLOSED_OPTION);
					 buscarPromociones(idTienda,nombreTienda);
					 panelEditar.setVisible(false);
					 panelConsulta.setVisible(true);
					 panelConsulta.repaint();
					 System.gc();
				}else{
					 int n=JOptionPane.showConfirmDialog(panelConsulta,"Error editando la promoción.","Error edición de promoción",JOptionPane.CLOSED_OPTION);
				}
			}
		});
	}//fin controlarEventos()
	
	
	void buscarPromociones(String idTienda,String nombreTienda){
		dtmConsulta.setColumnCount(0);
		dtmConsulta.setRowCount(0);
		dtmConsulta.addColumn("Id. Promoción");
		dtmConsulta.addColumn("Id. Tienda");
		dtmConsulta.addColumn("Descripción");
		dtmConsulta.addColumn("Fecha Inicio");
		dtmConsulta.addColumn("Fecha Fin");
		dtmConsulta.addColumn("Puntos Necesarios");
		dtmConsulta.addColumn("Importe Necesario");
		dtmConsulta.addColumn("URL");
		dtmConsulta.addColumn("Activa");
		dtmConsulta.addColumn("");
		dtmConsulta.addColumn("");
		dtmConsulta.addColumn("");
		tabla_Consulta.setRowSorter (new TableRowSorter(dtmConsulta));
		tabla_Consulta.getTableHeader().setReorderingAllowed(false);
		
		ArrayList<Promocion> promocion=Server.getPromociones("2","");//clave 2 para todas las promociones
		int tam=promocion.size();
		for(int i=0; i<tam; i++){
			String idPromocion=promocion.get(i).getIdPromocion();
			String IdTienda=promocion.get(i).getIdTienda();
			String descripcion=promocion.get(i).getDescripcion();
			String fechaInicio=promocion.get(i).getFechaInicio();
			String fechaFin=promocion.get(i).getFechaFin();
			int puntos=promocion.get(i).getPuntos();
			double importe=promocion.get(i).getImporte();
			boolean activa=promocion.get(i).getActiva();
			String url=promocion.get(i).getURL();
			String Activo="Baja";
			if(activa){
				Activo="Alta";
			}
			Object date[]={idPromocion,IdTienda,descripcion,fechaInicio,fechaFin,puntos+"",importe+"",url,Activo};
			tabla_Consulta.getColumnModel().getColumn(9).setCellRenderer(new KeyIconCellRenderer(9));
			tabla_Consulta.getColumnModel().getColumn(10).setCellRenderer(new KeyIconCellRenderer(10));
			tabla_Consulta.getColumnModel().getColumn(11).setCellRenderer(new KeyIconCellRenderer(11));
			dtmConsulta.addRow(date);
		}
		centrarDatos(); 
	}//fin buscarPromociones
	
	
	/**
	* Método centrarDatos() que centra los datos centrados visualizados en cada celda de manera centrada
	*/
	public void centrarDatos(){
			 DefaultTableCellRenderer modelocentrar = new DefaultTableCellRenderer();
			 modelocentrar.setHorizontalAlignment(SwingConstants.CENTER);
			 tabla_Consulta.getColumnModel().getColumn(0).setCellRenderer(modelocentrar);
			 tabla_Consulta.getColumnModel().getColumn(0).setPreferredWidth(25);
			 tabla_Consulta.getColumnModel().getColumn(1).setCellRenderer(modelocentrar);
			 tabla_Consulta.getColumnModel().getColumn(1).setPreferredWidth(10);
			 tabla_Consulta.getColumnModel().getColumn(2).setCellRenderer(modelocentrar);
			 tabla_Consulta.getColumnModel().getColumn(2).setPreferredWidth(70);
			 tabla_Consulta.getColumnModel().getColumn(3).setCellRenderer(modelocentrar);
			 tabla_Consulta.getColumnModel().getColumn(3).setPreferredWidth(130);
			 tabla_Consulta.getColumnModel().getColumn(4).setCellRenderer(modelocentrar);
			 tabla_Consulta.getColumnModel().getColumn(4).setPreferredWidth(130);
			 tabla_Consulta.getColumnModel().getColumn(5).setCellRenderer(modelocentrar);
			 tabla_Consulta.getColumnModel().getColumn(5).setPreferredWidth(70);
			 tabla_Consulta.getColumnModel().getColumn(6).setCellRenderer(modelocentrar);
			 tabla_Consulta.getColumnModel().getColumn(6).setPreferredWidth(70);
			 tabla_Consulta.getColumnModel().getColumn(7).setCellRenderer(modelocentrar);
			 tabla_Consulta.getColumnModel().getColumn(7).setPreferredWidth(130);
			 tabla_Consulta.getColumnModel().getColumn(8).setCellRenderer(modelocentrar);
			 tabla_Consulta.getColumnModel().getColumn(8).setPreferredWidth(30);
			 tabla_Consulta.getColumnModel().getColumn(9).setPreferredWidth(10);
			 tabla_Consulta.getColumnModel().getColumn(10).setPreferredWidth(10);
			 tabla_Consulta.getColumnModel().getColumn(11).setPreferredWidth(10);
	}//fin centrarDatos()
}
