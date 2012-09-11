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
import com.toedter.calendar.JCalendar;
import loyalty.bean.Promocion;
import loyalty.control.Server;
import loyalty.gpromociones.ConsultaPromociones.KeyIconCellRenderer;

public class ActivarPromocion {
	
	JFrame frameActivar;
	JPanel panelActivar=new JPanel();
	
	ImageIcon iconoFiltrar = new ImageIcon(getClass().getResource("/loyalty/images/buscar.png"));
	ImageIcon iconoLimpiar = new ImageIcon(getClass().getResource("/loyalty/images/reiniciar.png"));
	ImageIcon iconoSalir = new ImageIcon(getClass().getResource("/loyalty/images/exit.png")); 
	ImageIcon iconoAtras = new ImageIcon(getClass().getResource("/loyalty/images/btnVolver.png"));
	Icon imagenCabecera = new ImageIcon(getClass().getResource("/loyalty/images/cabecera.png"));
	ImageIcon fechaDcha = new ImageIcon(getClass().getResource("/loyalty/images/meterFechaDcha.png"));
	ImageIcon fechaIzda = new ImageIcon(getClass().getResource("/loyalty/images/meterFechaIzda.png"));
	
	JButton botonAtras=new JButton("", iconoAtras);
	JButton botonSalir=new JButton("", iconoSalir);
	JButton botonActivar=new JButton("Activar Promoción");
	JButton botonFIni=new JButton("", fechaIzda);
	JButton botonFFin=new JButton("", fechaDcha);
	
	JLabel fondo_imagen=new JLabel();
	JLabel lb_cabecera=new JLabel(imagenCabecera);

	JLabel titulo = new JLabel("Activar Promoción");
	
	JLabel lb_idPromocion=new JLabel("·Id. Promoción:");
	JLabel lb_idTienda=new JLabel("·Id. Tienda:");
	JLabel lb_descripcion=new JLabel("·Descripción:");
	JLabel lb_fechaInicio=new JLabel("·Fecha Inicio:");
	JLabel lb_fechaFin=new JLabel("·Fecha Fin:");
	JLabel lb_puntos=new JLabel("·Puntos:");
	JLabel lb_importe=new JLabel("·Importe:");
	JLabel lb_url=new JLabel("·URL:");
	
	JTextField text_idPromocion=new JTextField(50);
	JTextField text_idTienda=new JTextField(50);
	JTextField text_descripcion=new JTextField(50);
	JTextField text_fechaInicio=new JTextField(50);
	JTextField text_fechaFin=new JTextField(50);
	JTextField text_puntos=new JTextField(50);
	JTextField text_importe=new JTextField(50);
	JTextField text_url=new JTextField(50);
	
	JCalendar calendario = new JCalendar();
	
	
	
	@SuppressWarnings("serial")
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
	 

	public ActivarPromocion(String idPromocion,String idTienda,String descripcion,String fechaInicio,String fechaFin,
			String puntos,String importe,String url,String activa,String nombreTienda,JFrame frameConsulta,
			JPanel panel_Autenticacion,JPanel panelConsulta,DefaultTableModel dtmConsulta,JTable tabla_Consulta){
		this.dtmConsulta=dtmConsulta;
		this.tabla_Consulta=tabla_Consulta;
		frameActivar=frameConsulta;
		panelConsulta.setVisible(false);
		configurarInterfaz(idPromocion,idTienda,descripcion,puntos,importe,url);
		controlarEventos(panel_Autenticacion,panelConsulta,idPromocion,idTienda,nombreTienda,descripcion,puntos,importe,
				url,dtmConsulta,tabla_Consulta);
	}
	
	void configurarInterfaz(String idPromocion,String idTienda,String descripcion,String puntos,String importe,String url){
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
		 int offsetSuperiorFila11 = offsetSuperiorFila10 + offsetBasico + alturaInput;
		 
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
		 int anchuraCalendario=380;
		 int alturaCalendario=170;
	    
	    //Añadimos y configuramos el panel_modificar
	    panelActivar.setLayout(null);
	    panelActivar.setBounds(0,0,pantalla.width, pantalla.height);
	    panelActivar.setBackground(colorFondoPantalla);
	    frameActivar.add(panelActivar);
		 
		titulo.setBounds(offsetLateralTitulo, offsetSuperiorTitulo, anchuraTitulo, alturaTitulo);
		titulo.setFont(new Font(fuenteTexto,estiloTexto,30));
		titulo.setForeground(colorFondoPantalla);
		titulo.setVisible(true);
		panelActivar.add(titulo);
		 
		botonAtras.setBounds(posXAtras,posYAtras,iconoSalir.getIconWidth(), iconoSalir.getIconHeight());
	    botonAtras.setFont(new Font(fuenteTexto,estiloTexto,tamTextoBotones));
	    botonAtras.setVisible(true);
	    panelActivar.add(botonAtras); 	
	     
	    botonSalir.setBounds(posXExit, posYExit, iconoSalir.getIconWidth(), iconoSalir.getIconHeight());
	    botonSalir.setFont(new Font(fuenteTexto,estiloTexto,tamTextoBotones));
	    botonSalir.setVisible(true);
	    panelActivar.add(botonSalir);
	     
		lb_cabecera.setBounds(0,0,1400,83);
		lb_cabecera.setVisible(true);
		panelActivar.add(lb_cabecera);

	    botonAtras.setVerticalTextPosition(AbstractButton.BOTTOM);//Alineamos el texto abajo
	    botonAtras.setHorizontalTextPosition(AbstractButton.CENTER);
	    botonSalir.setVerticalTextPosition(AbstractButton.BOTTOM);//Alineamos el texto abajo
	    botonSalir.setHorizontalTextPosition(AbstractButton.CENTER);
	    botonActivar.setVerticalTextPosition(AbstractButton.BOTTOM);//Alineamos el texto abajo
	    botonActivar.setHorizontalTextPosition(AbstractButton.CENTER);
	    
	    lb_idPromocion.setBounds(offsetLateralColumna01,offsetSuperiorFila01,anchuraTexto, alturaInput);
	    lb_idPromocion.setFont(new Font(fuenteTexto,estiloTexto,tamTextoBotones));
	    lb_idPromocion.setVisible(true);
		panelActivar.add(lb_idPromocion); 
		text_idPromocion.setBounds(offsetLateralInputColumna01,offsetSuperiorFila01,anchuraInput, alturaInput);
		text_idPromocion.setFont(new Font(fuenteTexto,estiloTexto,tamTextoBotones));
		text_idPromocion.setVisible(true);
		text_idPromocion.setEditable(false);
		text_idPromocion.setText(idPromocion);
		panelActivar.add(text_idPromocion);
		 
		lb_idTienda.setBounds(offsetLateralColumna02,offsetSuperiorFila01,anchuraTexto,alturaInput);
		lb_idTienda.setFont(new Font(fuenteTexto,estiloTexto,tamTextoBotones));
		lb_idTienda.setVisible(true);
		panelActivar.add(lb_idTienda); 
		text_idTienda.setBounds(offsetLateralInputColumna02,offsetSuperiorFila01,anchuraInput,alturaInput);
		text_idTienda.setFont(new Font(fuenteTexto,estiloTexto,tamTextoBotones));
		text_idTienda.setVisible(true);
		text_idTienda.setEditable(false);
		text_idTienda.setText(idTienda);
		panelActivar.add(text_idTienda);
		 
		lb_descripcion.setBounds(offsetLateralColumna01,offsetSuperiorFila02,anchuraTexto, alturaInput);
		lb_descripcion.setFont(new Font(fuenteTexto,estiloTexto,tamTextoBotones));
		lb_descripcion.setVisible(true);
		panelActivar.add(lb_descripcion); 
		text_descripcion.setBounds(offsetLateralInputColumna01,offsetSuperiorFila02,anchuraInput, alturaInput);
		text_descripcion.setFont(new Font(fuenteTexto,estiloTexto,tamTextoBotones));
		text_descripcion.setVisible(true);
		text_descripcion.setEditable(false);
		text_descripcion.setText(descripcion);
		panelActivar.add(text_descripcion);
		 
		lb_puntos.setBounds(offsetLateralColumna02,offsetSuperiorFila02,anchuraTexto, alturaInput);
		lb_puntos.setFont(new Font(fuenteTexto,estiloTexto,tamTextoBotones));
		lb_puntos.setVisible(true);
		panelActivar.add(lb_puntos); 
		text_puntos.setBounds(offsetLateralInputColumna02,offsetSuperiorFila02,anchuraInput, alturaInput);
		text_puntos.setFont(new Font(fuenteTexto,estiloTexto,tamTextoBotones));
		text_puntos.setVisible(true);
		text_puntos.setEditable(false);
		text_puntos.setText(puntos);
		panelActivar.add(text_puntos);
		 
		lb_importe.setBounds(offsetLateralColumna01,offsetSuperiorFila03,anchuraTexto, alturaInput);
		lb_importe.setFont(new Font(fuenteTexto,estiloTexto,tamTextoBotones));
		lb_importe.setVisible(true);
		panelActivar.add(lb_importe); 
		text_importe.setBounds(offsetLateralInputColumna01,offsetSuperiorFila03,anchuraInput, alturaInput);
		text_importe.setFont(new Font(fuenteTexto,estiloTexto,tamTextoBotones));
		text_importe.setVisible(true);
		text_importe.setEditable(false);
		text_importe.setText(importe);
		panelActivar.add(text_importe);
		
		lb_url.setBounds(offsetLateralColumna02,offsetSuperiorFila03,anchuraTexto, alturaInput);
		lb_url.setFont(new Font(fuenteTexto,estiloTexto,tamTextoBotones));
		lb_url.setVisible(true);
		panelActivar.add(lb_url); 
		text_url.setBounds(offsetLateralInputColumna02,offsetSuperiorFila03,anchuraInput, alturaInput);
		text_url.setFont(new Font(fuenteTexto,estiloTexto,tamTextoBotones));
		text_url.setVisible(true);
		text_url.setEditable(false);
		text_url.setText(url);
		panelActivar.add(text_url);
		 
		lb_fechaInicio.setBounds(offsetLateralColumna01,offsetSuperiorFila04,anchuraTexto, alturaInput);
		lb_fechaInicio.setFont(new Font(fuenteTexto,estiloTexto,tamTextoBotones));
		lb_fechaInicio.setVisible(true);
		panelActivar.add(lb_fechaInicio); 
		text_fechaInicio.setBounds(offsetLateralInputColumna01,offsetSuperiorFila04,anchuraInput, alturaInput);
		text_fechaInicio.setFont(new Font(fuenteTexto,estiloTexto,tamTextoBotones));
		text_fechaInicio.setVisible(true);
		text_fechaInicio.setEditable(false);
		panelActivar.add(text_fechaInicio);
		
		lb_fechaFin.setBounds(offsetLateralColumna02,offsetSuperiorFila04,anchuraTexto, alturaInput);
		lb_fechaFin.setFont(new Font(fuenteTexto,estiloTexto,tamTextoBotones));
		lb_fechaFin.setVisible(true);
		panelActivar.add(lb_fechaFin); 
		text_fechaFin.setBounds(offsetLateralInputColumna02,offsetSuperiorFila04,anchuraInput, alturaInput);
		text_fechaFin.setFont(new Font(fuenteTexto,estiloTexto,tamTextoBotones));
		text_fechaFin.setVisible(true);
		text_fechaFin.setEditable(false);
		panelActivar.add(text_fechaFin);
		
		calendario.setBounds(offsetLateralColumna02-anchuraTexto,offsetSuperiorFila05,anchuraTexto, alturaInput);
		calendario.setSize(anchuraCalendario,alturaCalendario); 
		panelActivar.add(calendario);
		
		botonFIni.setBounds(offsetLateralColumna02-2*anchuraTexto,offsetSuperiorFila05,fechaDcha.getIconWidth(),fechaDcha.getIconHeight());
		botonFIni.setFont(new Font(fuenteTexto,estiloTexto,tamTextoBotones));
		botonFIni.setVisible(true);
		panelActivar.add(botonFIni); 	
	    
	    botonFFin.setBounds(offsetLateralColumna02+2*anchuraTexto,offsetSuperiorFila05,fechaDcha.getIconWidth(),fechaDcha.getIconHeight());
	    botonFFin.setFont(new Font(fuenteTexto,estiloTexto,tamTextoBotones));
	    botonFFin.setVisible(true);
	    panelActivar.add(botonFFin); 	
	    
	    botonActivar.setBounds(offsetLateralColumna02-anchuraTexto,offsetSuperiorFila10,anchuraCalendario, alturaInput);
	    botonActivar.setFont(new Font(fuenteTexto,estiloTexto,tamTextoBotones));
	    botonActivar.setVisible(true);
	    panelActivar.add(botonActivar); 	
	}//fin configurarInterfaz()
	
	
	void controlarEventos(final JPanel panel_Autenticacion,final JPanel panelConsulta,final String idPromocion,
			final String idTienda,final String nombreTienda,final String descripcion,final String puntos,
			final String importe,final String url,final DefaultTableModel dtmConsulta,final JTable tabla_Consulta){
		botonSalir.addActionListener(new ActionListener(){
			public void actionPerformed (ActionEvent e){	  
				int n = JOptionPane.showConfirmDialog (panelActivar,"¿Desea salir del sistema?","¿Salir Sistema?",JOptionPane.YES_NO_OPTION);
		        if(n==0){
		        	  boolean LogOut=Server.LogOut();
		        	  panelActivar.setVisible(false);
		        	  panel_Autenticacion.setVisible(true);
					  System.gc();
		        }
			}
		});
	 
		botonAtras.addActionListener(new ActionListener(){
			public void actionPerformed (ActionEvent e){	  
				panelActivar.setVisible(false);
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
				int minutos = calendar.get(calendar.MINUTE);
				int segundos = calendar.get(calendar.SECOND);
				text_fechaInicio.setText(año+"-"+mes+"-"+dia+" "+hora+":"+minutos+":"+segundos);
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
		
		botonActivar.addActionListener(new ActionListener(){
			public void actionPerformed (ActionEvent e){	  
				String fechaIni=text_fechaInicio.getText();
				String fechaFin=text_fechaFin.getText();
				if(fechaIni.compareTo("")==0 || fechaFin.compareTo("")==0){
					int n = JOptionPane.showConfirmDialog (panelActivar,"Campos fecha inico o fecha fin vacíos. Por favor, selecione los valores en el calendario",
							"Fechas nulas",JOptionPane.CLOSED_OPTION);
				}else{
					activarPromocion(idPromocion,idTienda,nombreTienda,descripcion,puntos,importe,url,fechaIni,fechaFin,
							dtmConsulta,tabla_Consulta,panelConsulta);
				}
			}
		});
	}//fin controlarEventos()
	
	void activarPromocion(String idPromocion,String idTienda,String nombreTienda,String descripcion,String puntos,String importe,
			String url,String fechaIni,String fechaFin,DefaultTableModel dtmConsulta,JTable tabla_Consulta,JPanel panelConsulta){
		 boolean exito=Server.activarPromocion(idPromocion,nombreTienda,fechaIni,fechaFin);
		 if(exito){
			 int n = JOptionPane.showConfirmDialog (panelActivar,"Promoción activada con éxito.","Promoción activada",JOptionPane.CLOSED_OPTION);
			 if(n==0){
				 buscarPromociones(idTienda,nombreTienda);
				 panelActivar.setVisible(false);
				 panelConsulta.setVisible(true);
			 }
		 }else{
			 int j= JOptionPane.showConfirmDialog (panelActivar,"Error en la activación de la promoción","Error activación",JOptionPane.CLOSED_OPTION);
			 panelActivar.setVisible(false);
			 panelConsulta.setVisible(true);
		 }	
	}//fin activarPromocion()
	
	
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
			 tabla_Consulta.getColumnModel().getColumn(0).setPreferredWidth(10);
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
			 tabla_Consulta.getColumnModel().getColumn(7).setPreferredWidth(30);
			 tabla_Consulta.getColumnModel().getColumn(8).setCellRenderer(modelocentrar);
			 tabla_Consulta.getColumnModel().getColumn(8).setPreferredWidth(130);
			 tabla_Consulta.getColumnModel().getColumn(9).setPreferredWidth(10);
			 tabla_Consulta.getColumnModel().getColumn(10).setPreferredWidth(10);
			 tabla_Consulta.getColumnModel().getColumn(11).setPreferredWidth(10);
	}//fin centrarDatos()
}
