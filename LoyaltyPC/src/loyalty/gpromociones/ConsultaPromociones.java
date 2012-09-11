package loyalty.gpromociones;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableRowSorter;
import loyalty.bean.Promocion;
import loyalty.control.Server;



public class ConsultaPromociones {
	
	JFrame frameConsulta;
	JPanel panelConsulta=new JPanel();
	
	ImageIcon iconoFiltrar = new ImageIcon(getClass().getResource("/loyalty/images/buscar.png"));
	ImageIcon iconoLimpiar = new ImageIcon(getClass().getResource("/loyalty/images/reiniciar.png"));
	ImageIcon iconoSalir = new ImageIcon(getClass().getResource("/loyalty/images/exit.png")); 
	ImageIcon iconoAtras = new ImageIcon(getClass().getResource("/loyalty/images/btnVolver.png"));
	Icon imagenCabecera = new ImageIcon(getClass().getResource("/loyalty/images/cabecera.png"));
	
	JButton botonAtras=new JButton("", iconoAtras);
	JButton botonSalir=new JButton("", iconoSalir);
	JButton botonFiltrar=new JButton("", iconoFiltrar);
	JButton botonLimpiar=new JButton("", iconoLimpiar);
	
	JLabel fondo_imagen=new JLabel();
	JLabel lb_cabecera=new JLabel(imagenCabecera);

	JLabel titulo = new JLabel("CONSULTA DE PROMOCIONES");
	
	JLabel lb_idTienda=new JLabel("·Id. Tienda:");
	JTextField text_idTienda=new JTextField(50);
	JLabel lb_busqueda=new JLabel("Búsqueda por:");
	JComboBox lista_buscar=new JComboBox();
	
	JLabel lb_descripcionBusqueda=new JLabel("Detalle búsqueda:");
	JTextField text_buscar=new JTextField(50);
	
	
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
	final JTable tabla_Consulta = new JTable(dtmConsulta);
    JScrollPane scrollPaneConsulta = new JScrollPane(tabla_Consulta);
    
    ArrayList<Promocion> promocion=null;
    
	String idPromocion="";
	String IdTienda="";
	String nombTienda="";
	String descripcion="";
	String fechaInicio="";
	String fechaFin="";
	int puntos=0;
	double importe=0;
	String url="";
	boolean activa=false;
	
	String campoBusqueda="";
	String textoBusqueda="";
	int columnaSeleccionada=-1;
	int filaTabla=-1;
	int columnaTabla=-1;

	public ConsultaPromociones(JPanel panelInicial,JPanel panel_Autenticacion,
			JPanel panelPromociones,JFrame framePromociones,String idTienda,String nombreTienda){
		frameConsulta=framePromociones;
		panelPromociones.setVisible(false);
		configurarInterfaz(idTienda);
		controlarEventos(panel_Autenticacion,panelPromociones,idTienda,nombreTienda);
		buscarPromociones(idTienda,nombreTienda);
	}
	
	void configurarInterfaz(String idTienda){
		Color colorFondoPantalla = new Color(0xe0e2e9);
	    Color colorAzulProfundo = new Color(0x0d0d33);
	    String fuenteTexto = "Trebuchet";
	    int estiloTexto = Font.PLAIN;
	     
	    Toolkit tk = Toolkit.getDefaultToolkit();
	    Dimension pantalla=tk.getScreenSize();
	    int tamTextoBotones = 18;
	    int offsetBasico = 10;
	    
	    int alturaTitulo = 50;
	    int anchuraTitulo = 500;
	    int offsetLateralTitulo = 100;
	    int offsetSuperiorTitulo = 15;
	    

	    int posXExit = pantalla.width - iconoSalir.getIconWidth() - offsetBasico;
	     int posYExit = (imagenCabecera.getIconHeight()/2)-(iconoSalir.getIconHeight()/2);
	     
	     int posXAtras = posXExit - iconoSalir.getIconWidth() - offsetBasico;
		 int posYAtras = posYExit;

	     int anchuraInput=300;
		 int alturaInput=35;
		 int anchuraTexto=145;
		 
		 int alturaDatos = pantalla.height/2;
		 int anchuraDatos = pantalla.width-(2*offsetBasico);
	     
		 int offsetSuperiorTabla = 80 + offsetBasico + iconoSalir.getIconHeight() + offsetBasico;
		 int offsetSuperiorFila01 = offsetSuperiorTabla + alturaDatos + offsetBasico;
		 
		 int offsetLateralColumna01 = offsetBasico;
		 int offsetLateralColumna02 = offsetLateralColumna01 + anchuraInput + anchuraTexto + (2*offsetBasico);
		 int offsetLateralColumna03 = offsetLateralColumna02 + anchuraInput + anchuraTexto + offsetBasico;
		 int offsetLateralColumna04 = offsetLateralColumna03 + iconoFiltrar.getIconWidth() + offsetBasico;	 
		 
		 int offsetLateralInputColumna01 = offsetLateralColumna01 + anchuraTexto;
		 int offsetLateralInputColumna02 = offsetLateralColumna02 + anchuraTexto;
	    //Añadimos y configuramos el panel_modificar
	    panelConsulta.setLayout(null);
	    panelConsulta.setBounds(0,0,pantalla.width, pantalla.height);
	    panelConsulta.setBackground(colorFondoPantalla);
	    frameConsulta.add(panelConsulta);
		 
		titulo.setBounds(offsetLateralTitulo, offsetSuperiorTitulo, anchuraTitulo, alturaTitulo);
		titulo.setFont(new Font(fuenteTexto,estiloTexto,30));
		titulo.setForeground(colorFondoPantalla);
		titulo.setVisible(true);
		panelConsulta.add(titulo);
		 
		botonAtras.setBounds(posXAtras,posYAtras,iconoSalir.getIconWidth(), iconoSalir.getIconHeight());
	    botonAtras.setFont(new Font(fuenteTexto,estiloTexto,tamTextoBotones));
	    botonAtras.setVisible(true);
	    panelConsulta.add(botonAtras); 	
	     
	    botonSalir.setBounds(posXExit, posYExit, iconoSalir.getIconWidth(), iconoSalir.getIconHeight());
	    botonSalir.setFont(new Font(fuenteTexto,estiloTexto,tamTextoBotones));
	    botonSalir.setVisible(true);
	    panelConsulta.add(botonSalir);
	     
		lb_cabecera.setBounds(0,0,1400,83);
		lb_cabecera.setVisible(true);
		panelConsulta.add(lb_cabecera);

	    botonAtras.setVerticalTextPosition(AbstractButton.BOTTOM);//Alineamos el texto abajo
	    botonAtras.setHorizontalTextPosition(AbstractButton.CENTER);
	    botonSalir.setVerticalTextPosition(AbstractButton.BOTTOM);//Alineamos el texto abajo
	    botonSalir.setHorizontalTextPosition(AbstractButton.CENTER);
	    
	    lb_idTienda.setBounds(offsetBasico,offsetSuperiorTabla-40,anchuraTexto, alturaInput);
	    lb_idTienda.setForeground(Color.black);
	    lb_idTienda.setFont(new Font(fuenteTexto,estiloTexto,tamTextoBotones));
	    lb_idTienda.setVisible(true);
		panelConsulta.add(lb_idTienda);
		text_idTienda.setBounds(offsetBasico+anchuraTexto,offsetSuperiorTabla-40,anchuraInput,alturaInput);
		text_idTienda.setForeground(colorAzulProfundo);
		text_idTienda.setFont(new Font(fuenteTexto,estiloTexto,tamTextoBotones));
		text_idTienda.setVisible(true);
		text_idTienda.setEditable(false);
		text_idTienda.setText(idTienda);
		panelConsulta.add(text_idTienda);
	    
	    dtmConsulta.setColumnCount(0);
	    dtmConsulta.setRowCount(0);
	    //tabla para visualizar el archivo
	    scrollPaneConsulta.setSize(1235,185);
	    scrollPaneConsulta.setBounds(offsetBasico,offsetSuperiorTabla,anchuraDatos, alturaDatos);
	    scrollPaneConsulta.setVisible(false);
	    tabla_Consulta.setPreferredScrollableViewportSize(new Dimension(50,20));
	    tabla_Consulta.setFillsViewportHeight (true);
	    tabla_Consulta.setOpaque(true);
	    tabla_Consulta.setForeground(colorAzulProfundo);
	    tabla_Consulta.getTableHeader().setDefaultRenderer(new TransparentHeader()); 
	    panelConsulta.add(scrollPaneConsulta);
	    
	    lb_busqueda.setBounds(offsetLateralColumna01,offsetSuperiorFila01,anchuraTexto, alturaInput);
		lb_busqueda.setForeground(Color.black);
		lb_busqueda.setFont(new Font(fuenteTexto,estiloTexto,tamTextoBotones));
		lb_busqueda.setVisible(true);
		panelConsulta.add(lb_busqueda);
		lista_buscar.setBounds(offsetLateralInputColumna01,offsetSuperiorFila01,anchuraInput, alturaInput);
		lista_buscar.setForeground(Color.black);
		lista_buscar.setFont(new Font(fuenteTexto,estiloTexto,tamTextoBotones));
		lista_buscar.addItem("Seleccione campo de búsqueda");
		lista_buscar.addItem("Id. Promoción");
		lista_buscar.addItem("Id. Tienda");
		lista_buscar.addItem("Descripción");
		lista_buscar.addItem("Fecha Inicio");
		lista_buscar.addItem("Fecha Fin");
		lista_buscar.addItem("Puntos Necesarios");
		lista_buscar.addItem("Importe Necesario");
		lista_buscar.addItem("URL");
		lista_buscar.addItem("Activa");
		lista_buscar.setVisible(true);
		panelConsulta.add(lista_buscar);
		
		lb_descripcionBusqueda.setBounds(offsetLateralColumna02,offsetSuperiorFila01,anchuraTexto, alturaInput);
		lb_descripcionBusqueda.setForeground(Color.black);
		lb_descripcionBusqueda.setFont(new Font(fuenteTexto,estiloTexto,tamTextoBotones));
		lb_descripcionBusqueda.setVisible(true);
		panelConsulta.add(lb_descripcionBusqueda);
		text_buscar.setBounds(offsetLateralInputColumna02,offsetSuperiorFila01,anchuraInput, alturaInput);
		text_buscar.setForeground(colorAzulProfundo);
		text_buscar.setFont(new Font(fuenteTexto,estiloTexto,tamTextoBotones));
		text_buscar.setVisible(true);
		text_buscar.setEnabled(false);
		panelConsulta.add(text_buscar);
		
		botonFiltrar.setBounds(offsetLateralColumna03,offsetSuperiorFila01, iconoSalir.getIconWidth(), alturaInput);
		botonFiltrar.setFont(new Font(fuenteTexto,estiloTexto,tamTextoBotones));
		botonFiltrar.setVisible(true);
		botonFiltrar.setEnabled(false);
	    panelConsulta.add(botonFiltrar);
	     
	    botonLimpiar.setBounds(offsetLateralColumna04,offsetSuperiorFila01, iconoSalir.getIconWidth(), alturaInput);
	    botonLimpiar.setFont(new Font(fuenteTexto,estiloTexto,tamTextoBotones));
	    botonLimpiar.setVisible(true);
	    panelConsulta.add(botonLimpiar);
	}//fin configurarInterfaz()
	
	
	void controlarEventos(final JPanel panel_Autenticacion,final JPanel panelPromociones,final String idTienda,final String nombreTienda){
		 
		botonSalir.addActionListener(new ActionListener(){
				public void actionPerformed (ActionEvent e){	  
					int n = JOptionPane.showConfirmDialog (panelConsulta,"¿Desea salir del sistema?","¿Salir Sistema?",JOptionPane.YES_NO_OPTION);
			        if(n==0){
			        	  boolean LogOut=Server.LogOut();
			        	  panelConsulta.setVisible(false);
			        	  panel_Autenticacion.setVisible(true);
						  System.gc();
			        }
				}
		  });
		 
		 botonAtras.addActionListener(new ActionListener(){
				public void actionPerformed (ActionEvent e){	  
					panelConsulta.setVisible(false);
					panelPromociones.setVisible(true);
					System.gc();
				}
		  });
		 
		 lista_buscar.addActionListener(new ActionListener(){
				public void actionPerformed (ActionEvent e){
					if(e.getSource()==lista_buscar){
						campoBusqueda=(String)lista_buscar.getSelectedItem();
						if(campoBusqueda.compareTo("Seleccione campo de búsqueda")==0){
							text_buscar.setEnabled(false);
							botonFiltrar.setEnabled(false);
						}else{
							text_buscar.setEnabled(true);
							botonFiltrar.setEnabled(true);
						   if(campoBusqueda.compareTo("Id. Promoción")==0)
							   columnaSeleccionada=0;
						   if(campoBusqueda.compareTo("Id. Tienda")==0)
							   columnaSeleccionada=1;
						   if(campoBusqueda.compareTo("Descripción")==0)
							   columnaSeleccionada=2;
						   if(campoBusqueda.compareTo("Fecha Inicio")==0)
							   columnaSeleccionada=3;
						   if(campoBusqueda.compareTo("Fecha Fin")==0)
							   columnaSeleccionada=4;
						   if(campoBusqueda.compareTo("Puntos Necesarios")==0)
							   columnaSeleccionada=5;
						   if(campoBusqueda.compareTo("Importe Necesario")==0)
							   columnaSeleccionada=6;
						   if(campoBusqueda.compareTo("URL")==0)
							   columnaSeleccionada=7;
						   if(campoBusqueda.compareTo("Activa")==0)
							   columnaSeleccionada=8;
						}
					  }
				}
		    });
		 
		 	botonFiltrar.addActionListener(new ActionListener(){
				public void actionPerformed (ActionEvent e){
					textoBusqueda="";
					textoBusqueda=text_buscar.getText();
					if(textoBusqueda.compareTo("")==0){
						   int n = JOptionPane.showConfirmDialog (panelConsulta,"Error, campo de datos vacío. Por favor, introduzca datos","Datos nulo",JOptionPane.CLOSED_OPTION);
					}else{
						   buscarDatosTabla(textoBusqueda);
						   text_buscar.setText("");
					}
				}
		    });
		 	
		 	botonLimpiar.addActionListener(new ActionListener(){
					public void actionPerformed (ActionEvent e){
						buscarPromociones(idTienda,nombreTienda);
					}
			});
		 	
		 	tabla_Consulta.addMouseListener(new MouseAdapter(){
				 public void mouseClicked(MouseEvent e){
				      filaTabla = tabla_Consulta.rowAtPoint(e.getPoint());
				      columnaTabla = tabla_Consulta.columnAtPoint(e.getPoint());
				      String idPromocion=(String) tabla_Consulta.getValueAt(filaTabla,0);
				      String idTienda=(String) tabla_Consulta.getValueAt(filaTabla,1);
				      String descripcion=(String) tabla_Consulta.getValueAt(filaTabla,2);
				      String fechaInicio=(String) tabla_Consulta.getValueAt(filaTabla,3);
				      String fechaFin=(String) tabla_Consulta.getValueAt(filaTabla,4);
				      String puntos=(String) tabla_Consulta.getValueAt(filaTabla,5);
				      String importe=(String) tabla_Consulta.getValueAt(filaTabla,6);
				      String url=(String) tabla_Consulta.getValueAt(filaTabla,7);
				      String activa=(String) tabla_Consulta.getValueAt(filaTabla,8);
				      
				      String IdInicialTienda=text_idTienda.getText();
				      if(columnaTabla==9){
				    	 if(activa.compareTo("Alta")==0){
				    		 int n=JOptionPane.showConfirmDialog(panelConsulta,"La promoción ya esta activada.","Promoción activada",JOptionPane.CLOSED_OPTION);
				    	 }else{
				    		 if(IdInicialTienda.compareTo(idTienda)!=0){
				    			 int n=JOptionPane.showConfirmDialog(panelConsulta,"No tiene permisos para esta tienda","Error de permisos",JOptionPane.CLOSED_OPTION);
				    		 }else{
				    		    new ActivarPromocion(idPromocion,idTienda,descripcion,fechaInicio,fechaFin,puntos,importe,
				    				url,activa,nombreTienda,frameConsulta,panel_Autenticacion,panelConsulta,dtmConsulta,tabla_Consulta);
				    		 }
				    	 }
				      }
				      if(columnaTabla==10){
				    	  if(activa.compareTo("Baja")==0){
					    		 int n=JOptionPane.showConfirmDialog(panelConsulta,"La promoción ya ha sido dada de baja.","Promoción desactivada",JOptionPane.CLOSED_OPTION);
					    	 }else{
					    		 if(IdInicialTienda.compareTo(idTienda)!=0){
					    			 int n=JOptionPane.showConfirmDialog(panelConsulta,"No tiene permisos para esta tienda","Error de permisos",JOptionPane.CLOSED_OPTION);
					    		 }else{
					    		    new BajaPromocion(idPromocion,idTienda,nombreTienda,panelConsulta,dtmConsulta,tabla_Consulta);
					    		 }
					    	 }
				      }
				      if(columnaTabla==11){
				    	  if(activa.compareTo("Baja")==0){
					    		 int n=JOptionPane.showConfirmDialog(panelConsulta,"Promoción desactivada. La promoción no puede ser editada.","Promoción desactivada",JOptionPane.CLOSED_OPTION);
					    	 }else{
					    		 if(IdInicialTienda.compareTo(idTienda)!=0){
					    			 int n=JOptionPane.showConfirmDialog(panelConsulta,"No tiene permisos para esta tienda","Error de permisos",JOptionPane.CLOSED_OPTION);
					    		 }else{
					    		    new EditarPromocion(idPromocion,idTienda,descripcion,fechaInicio,fechaFin,puntos,importe,
						    				 url,activa,nombreTienda,frameConsulta,panel_Autenticacion,panelConsulta,dtmConsulta,tabla_Consulta);
					    		 }
					    	 }
				      }
				 }
			});
	}//fin controlarEventos()
	
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
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
		
		promocion=Server.getPromociones("2","");//clave 2 para todas las promociones
		int tam=promocion.size();
		for(int i=0; i<tam; i++){
			idPromocion=promocion.get(i).getIdPromocion();
			IdTienda=promocion.get(i).getIdTienda();
			descripcion=promocion.get(i).getDescripcion();
			fechaInicio=promocion.get(i).getFechaInicio();
			fechaFin=promocion.get(i).getFechaFin();
			puntos=promocion.get(i).getPuntos();
			importe=promocion.get(i).getImporte();
			url=promocion.get(i).getURL();
			activa=promocion.get(i).getActiva();
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
		scrollPaneConsulta.setVisible(true);
	}//fin buscarPromociones
	
	
	
	
	/**
	* Método buscarDatosTabla que realiza el filtrado de datos segun el valor del parametro buscado
	* 
	* @param texto String con el valor del parametro buscado
	*/
    void buscarDatosTabla(String texto){
    	int cuantos=0;
    	int fil=tabla_Consulta.getRowCount();
    	String valor="";
    	CharSequence s=texto;
    	for(int i=0;i<fil;i++){
    		valor=(String) tabla_Consulta.getValueAt(i,columnaSeleccionada);
    		if(valor.compareTo(texto)==0 || valor.contains(s)==true){
    			cuantos++;
    		}
    	}
    	if(cuantos==0){
    		int k = JOptionPane.showConfirmDialog(panelConsulta,"No hay registros con los datos dados en la BBDD.","Registros nulos.",JOptionPane.CLOSED_OPTION);
    	}
    	if(cuantos!=0){
    		for(int i=0;i<fil;i++){
        		valor=(String)tabla_Consulta.getValueAt(i,columnaSeleccionada);
        		if(valor.contains(s)==false ){
        			dtmConsulta.removeRow(i);
        			fil=tabla_Consulta.getRowCount();
        			i--;
        		}
        	}
    	}
    }//fin buscarDatosTabla()
	
	
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
			 tabla_Consulta.getColumnModel().getColumn(8).setCellRenderer(modelocentrar);
			 tabla_Consulta.getColumnModel().getColumn(8).setPreferredWidth(30);
			 tabla_Consulta.getColumnModel().getColumn(9).setPreferredWidth(10);
			 tabla_Consulta.getColumnModel().getColumn(10).setPreferredWidth(10);
			 tabla_Consulta.getColumnModel().getColumn(11).setPreferredWidth(10);
	}//fin centrarDatos()
}
