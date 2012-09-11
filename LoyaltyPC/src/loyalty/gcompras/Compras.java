package loyalty.gcompras;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.TextField;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import loyalty.control.Server;
import loyalty.gclientes.BajaCliente;
import loyalty.gclientes.DuplicarTarjeta;
import loyalty.gclientes.ModificarCliente;



public class Compras {
	JFrame frameCompras;
	JPanel panelCompras=new JPanel();
	
	ImageIcon iconoSalir = new ImageIcon(getClass().getResource("/loyalty/images/exit.png")); 
	ImageIcon iconoAtras = new ImageIcon(getClass().getResource("/loyalty/images/btnVolver.png"));
	
	ImageIcon iconoComprar = new ImageIcon(getClass().getResource("/loyalty/images/comprar.png"));
	
	Icon imagenCabecera = new ImageIcon(getClass().getResource("/loyalty/images/cabecera.png"));
	
	ImageIcon imagen_compra = new ImageIcon(getClass().getResource("/loyalty/images/btnRealizarCompra.png"));
	ImageIcon imagen_puntos = new ImageIcon(getClass().getResource("/loyalty/images/btnConsultaPuntos.png"));
	Icon imagenMano = new ImageIcon(getClass().getResource("/loyalty/images/manoMovil.png"));
	
	JButton botonAtras=new JButton("", iconoAtras);
	JButton botonSalir=new JButton("", iconoSalir);
	JButton botonComprar=new JButton("", iconoComprar);
	
	JLabel fondo_imagen=new JLabel();
	JLabel lb_cabecera=new JLabel(imagenCabecera);
	
	JLabel titulo = new JLabel("REALIZAR COMPRA");
	
	JLabel lb_precio=new JLabel("·Importe compra:");
	JLabel lb_concepto=new JLabel("·Concepto:");
	JLabel lb_puntosObtenidos=new JLabel("·Puntos compra:");
	JLabel lb_puntosTotales=new JLabel("·Puntos totales:");
	JLabel lb_idCliente=new JLabel("·Id. cliente:");
	JLabel lb_textoInformativo=new JLabel("\"Pase el móvil o tarjeta NFC por el lector\"");
	JLabel mano = new JLabel (imagenMano);
	
	TextField text_precio=new TextField(50);
	JTextArea areaConcepto=new JTextArea();
	TextField textPuntosObtenidos=new TextField(50);
	TextField textPuntosTotales=new TextField(50);
	TextField textIdCliente=new TextField(50);
	
	 Hilo_Compra mihilo = new Hilo_Compra();
	 String identificadorCliente="";
	 
	 
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
					if(i==8){
						 setIcon(new ImageIcon(getClass().getResource("/loyalty/images/activar_promo.png")));
				   	     this.setHorizontalAlignment(SwingConstants.CENTER);
			   	         this.setToolTipText("Activar promoción");
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
		final JTable tabla_Consulta = new JTable(dtmConsulta);
	    JScrollPane scrollPaneConsulta = new JScrollPane(tabla_Consulta);
	    
	    int columnaSeleccionada=-1;
		int filaTabla=-1;
		int columnaTabla=-1;
	    
	
	public Compras(JPanel panelInicial,JPanel panel_Autenticacion,JPanel panelGCompras,JFrame framePromociones,String idTienda){
		frameCompras=framePromociones;
		panelGCompras.setVisible(false);
		configurarInterfaz();
		controlarEventos(panel_Autenticacion,panelGCompras,idTienda);
	}

	void configurarInterfaz(){
		Color colorFondoPantalla = new Color(0xe0e2e9);
		 Color colorAzulProfundo = new Color(0x0d0d33);
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
	    
	    int alturaInput=35;
		int anchuraTexto=145;
		int anchuraInfo=500;
	    
		int offsetSuperiorFila01 = 80 + offsetBasico;
		int offsetSuperiorFila02 = offsetSuperiorFila01 + alturaInput + offsetBasico;
		int offsetSuperiorFila03 = offsetSuperiorFila02 + alturaInput + offsetBasico;
		int offsetSuperiorFila04 = offsetSuperiorFila03 + alturaInput + offsetBasico;
		int offsetSuperiorFila05 = offsetSuperiorFila04 + alturaInput + offsetBasico;
		int offsetSuperiorFila06 = offsetSuperiorFila05 + alturaInput + offsetBasico;
		
		int offsetLateralColumna01 = (pantalla.width/4) - (2*anchuraTexto) + offsetBasico;
		
		//int offsetLateralColumna01 = offsetBasico;
		int offsetLateralColumna02 = offsetLateralColumna01 + (2*anchuraTexto) + offsetBasico;
		int offsetLateralColumna03 = offsetLateralColumna02 + (2*anchuraTexto) + offsetBasico;
		int offsetLateralColumna04 = offsetLateralColumna03 + (2*anchuraTexto) + offsetBasico;
		
		int offsetLateralInputColumna01= offsetLateralColumna01 + anchuraTexto;
		int offsetLateralInputColumna02= offsetLateralColumna02 + anchuraTexto;
		int offsetLateralInputColumna03= offsetLateralColumna03 + anchuraTexto;
		int offsetLateralInputColumna04= offsetLateralColumna03 + anchuraTexto;
		
		int offsetLateralBotonCompra = offsetLateralInputColumna04 + 2*anchuraTexto - iconoSalir.getIconWidth();//Cambiar por anchura del botón cuando esté definido
	    
	    int alturaTitulo = 50;
	    int anchuraTitulo = 500;
	    int offsetLateralTitulo = 100;
	    int offsetSuperiorTitulo = 15;
		 int alturaDatos = pantalla.height/2;
		 int anchuraDatos = pantalla.width-(2*offsetBasico);
	/*
	    int offsetSuperiorFila01 = alturaBotonesMenu + offsetBasico;
	    int offsetSuperiorFila02 = offsetSuperiorFila01 + offsetBasico + alturaInput;
	    int offsetSuperiorFila03 = offsetSuperiorFila02 + offsetBasico + alturaInput;
	    int offsetLateralColumna01 = (pantalla.width/4) - (anchuraBotonesMenu);
	    int offsetLateralColumna02 = offsetLateralColumna01 + anchuraBotonesMenu + offsetBasico;
	    */
		
	    //Añadimos y configuramos el panel_modificar
	    panelCompras.setLayout(null);
	    panelCompras.setBounds(0,0,pantalla.width, pantalla.height);
	    panelCompras.setBackground(colorFondoPantalla);
		frameCompras.add(panelCompras);
		 
		titulo.setBounds(offsetLateralTitulo, offsetSuperiorTitulo, anchuraTitulo, alturaTitulo);
		titulo.setFont(new Font(fuenteTexto,estiloTexto,30));
		titulo.setForeground(colorFondoPantalla);
		titulo.setVisible(true);
		panelCompras.add(titulo);
		 
		botonAtras.setBounds(posXAtras,posYAtras,iconoSalir.getIconWidth(), iconoSalir.getIconHeight());
	    botonAtras.setFont(new Font(fuenteTexto,estiloTexto,tamTextoBotones));
	    botonAtras.setVisible(true);
	    panelCompras.add(botonAtras); 	
	     
	    botonSalir.setBounds(posXExit, posYExit, iconoSalir.getIconWidth(), iconoSalir.getIconHeight());
	    botonSalir.setFont(new Font(fuenteTexto,estiloTexto,tamTextoBotones));
	    botonSalir.setVisible(true);
	    panelCompras.add(botonSalir);
	     
		 
		lb_cabecera.setBounds(0,0,1400,83);
		lb_cabecera.setVisible(true);
		panelCompras.add(lb_cabecera);

	    botonAtras.setVerticalTextPosition(AbstractButton.BOTTOM);//Alineamos el texto abajo
	    botonAtras.setHorizontalTextPosition(AbstractButton.CENTER);
	    botonSalir.setVerticalTextPosition(AbstractButton.BOTTOM);//Alineamos el texto abajo
	    botonSalir.setHorizontalTextPosition(AbstractButton.CENTER);
	    
	    lb_precio.setBounds(offsetLateralColumna01,offsetSuperiorFila01,anchuraTexto, alturaInput);
		lb_precio.setFont(new Font(fuenteTexto,estiloTexto,tamTextoBotones));
		lb_precio.setVisible(true);
		panelCompras.add(lb_precio);
	 	text_precio.setFont(new Font(fuenteTexto,estiloTexto,tamTextoBotones));
	 	text_precio.setBounds(offsetLateralInputColumna01,offsetSuperiorFila01,anchuraTexto, alturaInput);
	 	text_precio.setEditable(true);
	 	text_precio.setText(null);
	 	text_precio.setVisible(true);
	 	panelCompras.add(text_precio);
	 	
	 	lb_concepto.setBounds(offsetLateralColumna02,offsetSuperiorFila01,anchuraTexto, alturaInput);
	 	lb_concepto.setFont(new Font(fuenteTexto,estiloTexto,tamTextoBotones));
	 	lb_concepto.setVisible(true);
		panelCompras.add(lb_concepto);
		areaConcepto.setFont(new Font(fuenteTexto,estiloTexto,tamTextoBotones));
		areaConcepto.setBounds(offsetLateralInputColumna02,offsetSuperiorFila01,2*anchuraTexto, offsetSuperiorFila02-(alturaInput+20));
		areaConcepto.setEditable(true);
		areaConcepto.setText(null);
		areaConcepto.setLineWrap(true);
		areaConcepto.setVisible(true);
		panelCompras.add(areaConcepto);
			 	
	 	lb_puntosObtenidos.setBounds(offsetLateralColumna03+anchuraTexto,offsetSuperiorFila01,anchuraTexto, alturaInput);
	 	lb_puntosObtenidos.setFont(new Font(fuenteTexto,estiloTexto,tamTextoBotones));
	 	lb_puntosObtenidos.setVisible(true);
		panelCompras.add(lb_puntosObtenidos);
		textPuntosObtenidos.setFont(new Font(fuenteTexto,estiloTexto,tamTextoBotones));
		textPuntosObtenidos.setBounds(offsetLateralInputColumna03+anchuraTexto,offsetSuperiorFila01,anchuraTexto, alturaInput);
		textPuntosObtenidos.setEditable(true);
		textPuntosObtenidos.setText(null);
		textPuntosObtenidos.setVisible(true);
		textPuntosObtenidos.setEditable(false);
		panelCompras.add(textPuntosObtenidos);
		
		lb_puntosTotales.setBounds(offsetLateralColumna03+anchuraTexto,offsetSuperiorFila02,anchuraTexto, alturaInput);
		lb_puntosTotales.setFont(new Font(fuenteTexto,estiloTexto,tamTextoBotones));
		lb_puntosTotales.setVisible(true);
		panelCompras.add(lb_puntosTotales);
		textPuntosTotales.setFont(new Font(fuenteTexto,estiloTexto,tamTextoBotones));
		textPuntosTotales.setBounds(offsetLateralInputColumna04+anchuraTexto,offsetSuperiorFila02,anchuraTexto, alturaInput);
		textPuntosTotales.setEditable(true);
		textPuntosTotales.setText(null);
		textPuntosTotales.setVisible(true);
		textPuntosTotales.setEditable(false);
		panelCompras.add(textPuntosTotales);
		
		lb_idCliente.setBounds(offsetLateralColumna01,offsetSuperiorFila02,anchuraTexto, alturaInput);
		lb_idCliente.setFont(new Font(fuenteTexto,estiloTexto,tamTextoBotones));
		lb_idCliente.setVisible(true);
		panelCompras.add(lb_idCliente);
		textIdCliente.setFont(new Font(fuenteTexto,estiloTexto,tamTextoBotones));
		textIdCliente.setBounds(offsetLateralInputColumna01,offsetSuperiorFila02,anchuraTexto, alturaInput);
		textIdCliente.setEditable(false);
		textIdCliente.setText(null);
		textIdCliente.setVisible(true);
	 	panelCompras.add(textIdCliente);
		
		lb_textoInformativo.setBounds(offsetLateralColumna03-imagenMano.getIconWidth()/3-50,offsetSuperiorFila05,anchuraInfo,alturaInput);
		lb_textoInformativo.setFont(new Font(fuenteTexto,estiloTexto,tamTextoBotones));
		lb_textoInformativo.setVisible(true);
		panelCompras.add(lb_textoInformativo);
		
		mano.setBounds(offsetLateralColumna03-imagenMano.getIconWidth()/3-50,offsetSuperiorFila06,imagenMano.getIconWidth(),imagenMano.getIconHeight());
	 	mano.setVisible(true);
	 	panelCompras.add(mano);
	 	
		botonComprar.setBounds(offsetLateralBotonCompra,offsetSuperiorFila03, iconoSalir.getIconWidth(), iconoSalir.getIconHeight());
		botonComprar.setFont(new Font(fuenteTexto,estiloTexto,tamTextoBotones));
		botonComprar.setVisible(true);
		panelCompras.add(botonComprar);
		
		dtmConsulta.setColumnCount(0);
	    dtmConsulta.setRowCount(0);
	    //tabla para visualizar el archivo
	    scrollPaneConsulta.setSize(1235,185);
	    scrollPaneConsulta.setBounds(offsetBasico,offsetSuperiorFila05,anchuraDatos, alturaDatos);
	    scrollPaneConsulta.setVisible(false);
	    tabla_Consulta.setPreferredScrollableViewportSize(new Dimension(50,20));
	    tabla_Consulta.setFillsViewportHeight (true);
	    tabla_Consulta.setOpaque(true);
	    tabla_Consulta.setForeground(colorAzulProfundo);
	    tabla_Consulta.getTableHeader().setDefaultRenderer(new TransparentHeader()); 
	    panelCompras.add(scrollPaneConsulta);
	}//fin configurarInterfaz()
	
	void controlarEventos(final JPanel panel_Autenticacion,final JPanel panelGCompras,final String idTienda){
		 botonSalir.addActionListener(new ActionListener(){
				public void actionPerformed (ActionEvent e){	  
					int n = JOptionPane.showConfirmDialog (panelCompras,"¿Desea salir del sistema?","¿Salir Sistema?",JOptionPane.YES_NO_OPTION);
			        if(n==0){
			        	  boolean LogOut=Server.LogOut();
			        	  panelCompras.setVisible(false);
			        	  panel_Autenticacion.setVisible(true);
						  System.gc();
			        }
				}
		  });
		 
		 botonAtras.addActionListener(new ActionListener(){
				public void actionPerformed (ActionEvent e){	  
			        	  panelCompras.setVisible(false);
			        	  panelGCompras.setVisible(true);
						  System.gc();
				}
		  });
		 
		 botonComprar.addActionListener(new ActionListener(){
				public void actionPerformed (ActionEvent e){	  
		        	  String importe=text_precio.getText();
		        	  double cantidad=0;
		        	  if(importe.compareTo("")!=0)
		        		  cantidad=Double.parseDouble(importe);
		        	  String concepto=areaConcepto.getText();
		        	  if(importe.compareTo("")==0 || concepto.compareTo("")==0){
		        		  int n = JOptionPane.showConfirmDialog (panelCompras,"Error, importe compra o concepto son nulos.","Campos vacíos",JOptionPane.CLOSED_OPTION);
		        	  }else{
		        		    mihilo.setTextPrecio(importe);
		        		    mihilo.setTextPuntosObtenidos(textPuntosObtenidos);
		        		    mihilo.setTextPuntosTotales(textPuntosTotales);
		        			mihilo.setPanel(panelCompras);
		        			mihilo.setConcepto(concepto);
		        			mihilo.setIdTienda(idTienda);
		        			mihilo.setTabla(tabla_Consulta);
		        			mihilo.setDefault(dtmConsulta);
		        			mihilo.setInformacion(lb_textoInformativo);
		        			mihilo.setImagen(mano);
		        			mihilo.setScroll(scrollPaneConsulta);
		        			mihilo.setUID(textIdCliente);
		        		    mihilo.start();
		        	  }
			}
		 });
		 
		 tabla_Consulta.addMouseListener(new MouseAdapter(){
			 public void mouseClicked(MouseEvent e){
			      filaTabla = tabla_Consulta.rowAtPoint(e.getPoint());
			      columnaTabla = tabla_Consulta.columnAtPoint(e.getPoint());

			      if(columnaTabla==8){
			    	 String idCliente=textIdCliente.getText();
				     String precio= text_precio.getText();
				     double importeCompra=Double.parseDouble(precio);
				     String puntos=textPuntosObtenidos.getText();
				     String puntosTotales=textPuntosTotales.getText();
				     int totalPuntos=Integer.parseInt(puntosTotales);
				     String puntosNecesarioTabla=(String) tabla_Consulta.getValueAt(filaTabla,5);
				     int totalPuntosTabla=Integer.parseInt(puntosNecesarioTabla);
				     String importeTabla=(String) tabla_Consulta.getValueAt(filaTabla,6);
				     double precioTabla=Double.parseDouble(importeTabla);
				     String idPromocion=(String) tabla_Consulta.getValueAt(filaTabla,0);
			    	 if(importeCompra<precioTabla){
			    		  int n = JOptionPane.showConfirmDialog (panelCompras,"Error, importe insuficiente para acogerse a la promoción.","Importe insuficiente",JOptionPane.CLOSED_OPTION);
			    	 }else{
			    		 int n = JOptionPane.showConfirmDialog (panelCompras,"¿Desea acogerse a la promoción?","Promoción acogida",JOptionPane.CLOSED_OPTION);
			    		 if(n==0){
			    			 boolean exito=Server.AcogerPromocion(idCliente, idPromocion);
			    			 if(exito){
			    				 int j = JOptionPane.showConfirmDialog (panelCompras,"Promoción acogida con éxito.","Promoción acogida",JOptionPane.CLOSED_OPTION);
			    				  panelCompras.setVisible(false);
			    				  panelGCompras.setVisible(true);
			    			 }else{
			    				 int j = JOptionPane.showConfirmDialog (panelCompras,"Error, imposilbe acogerse a la promoción.","Error canjeo",JOptionPane.CLOSED_OPTION);
			    			 }
			    		 }
			    	 }
			      }
			 }
		});
	}//void controlarEventos()
	
	
	
	@SuppressWarnings("serial")
	/**
	* Clase TransparentHeader para configurar las cabeceras de la tabla donde se mostraran los datos por pantalla
	*
	* @author <a href="mailto:sergio.simon@gammasolutions.es">Sergio Simón Garrido</a>
	* @version 1.0
	*
	* @since 1.0
	*/
	class TransparentHeader extends JLabel implements TableCellRenderer{  
	    	private final Border b = BorderFactory.createCompoundBorder(BorderFactory.createMatteBorder(0,0,1,0,Color.BLACK),
	    			BorderFactory.createEmptyBorder(2,2,1,2));
			@SuppressWarnings("unused")
			private final Color alphaZero = new Color(0, true);
	    	@Override 
	    	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,boolean hasFocus,
	    			int row, int column){
	    		this.setFont(new Font("Arial",Font.BOLD,11));
	    		this.setText(value!=null?value.toString():"");
	    		this.setHorizontalAlignment(JLabel.CENTER);
	    		this.setOpaque(false); 
	    		this.setBackground(Color.white);
	    		this.setForeground(Color.BLACK);
	    		this.setBorder(b);
	    		return this;
	    	}
	}//fin transparenHeader
	
	
	
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
			 tabla_Consulta.getColumnModel().getColumn(7).setPreferredWidth(130);
			 tabla_Consulta.getColumnModel().getColumn(8).setPreferredWidth(30);
	}//fin centrarDatos()
}
