package loyalty.gclientes;

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
import loyalty.bean.Cliente;
import loyalty.control.Server;



public class ConsultaClientes {
	
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

	JLabel titulo = new JLabel("CONSULTA DE CLIENTES");
	
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
				if(i==10){
		           setIcon(new ImageIcon(getClass().getResource("/loyalty/images/editar.png")));
		   	       this.setHorizontalAlignment(SwingConstants.CENTER);
		   	       this.setToolTipText("Editar Cliente");
				}
				if(i==11){
			           setIcon(new ImageIcon(getClass().getResource("/loyalty/images/eliminar.png")));
			   	       this.setHorizontalAlignment(SwingConstants.CENTER);
			   	       this.setToolTipText("Baja Cliente");
				}
				if(i==12){
			           setIcon(new ImageIcon(getClass().getResource("/loyalty/images/duplicar.png")));
			   	       this.setHorizontalAlignment(SwingConstants.CENTER);
			   	       this.setToolTipText("Duplicar Tarjeta");
				}
				if(i==13){
			           setIcon(new ImageIcon(getClass().getResource("/loyalty/images/registrarMovil.png")));
			   	       this.setHorizontalAlignment(SwingConstants.CENTER);
			   	       this.setToolTipText("Registrar Móvil");
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
    
    
    String User="";
	String Pass="";
	String permiso="";
	String idTienda="";
	String nombreTienda="";
	int compra=0;
	double canjeo=0;
	String URL="";
	
	ArrayList<Cliente> cliente=null;
	String IdCliente="";
	String NombreCliente="";
	String ApellidosCliente="";
	String DNICliente="";
	String EmailCliente="";
	String Usuario="";
	String Password="";
	int Puntos;
	String FechaAlta="";
	
	String campoBusqueda="";
	String textoBusqueda="";
	int columnaSeleccionada=-1;
	int filaTabla=-1;
	int columnaTabla=-1;
	
	public ConsultaClientes(JPanel panelGClientes,JPanel panel_Autenticacion,JFrame frame_GClientes,String User,String Pass,
			String permiso,String idTienda,String nombreTienda,int compra,double canjeo,String URL){
		 this.User=User;
		 this.Pass=Pass;
		 this.permiso=permiso;
		 this.idTienda=idTienda;
		 this.nombreTienda=nombreTienda;
		 this.compra=compra;
		 this.canjeo=canjeo;
		 this.URL=URL;
		 frameConsulta=frame_GClientes;
		 panelGClientes.setVisible(false);
		 configurarInterfaz();
		 buscarClientes();
		 controlarEventos(panelGClientes,panel_Autenticacion);
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
		    
		 int alturaTitulo = 50;
		 int anchuraTitulo = 500;
		 int offsetLateralTitulo = 100;
		 int offsetSuperiorTitulo = 15;  

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
	     
	     dtmConsulta.setColumnCount(0);
		 dtmConsulta.setRowCount(0);
		 //tabla para visualizar el archivo
		 scrollPaneConsulta.setSize(1235,185);
		 scrollPaneConsulta.setBounds(offsetLateralColumna01,offsetSuperiorTabla,anchuraDatos, alturaDatos);
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
		 lista_buscar.addItem("Id. Cliente");
		 lista_buscar.addItem("Nombre");
		 lista_buscar.addItem("Apellidos");
		 lista_buscar.addItem("DNI");
		 lista_buscar.addItem("Email");
		 lista_buscar.addItem("Usuario");
		 lista_buscar.addItem("Contraseña");
		 lista_buscar.addItem("Puntos iniciales");
		 lista_buscar.addItem("Fecha alta");
		 lista_buscar.addItem("Alta/Baja");
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
	
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	void buscarClientes(){
		dtmConsulta.setColumnCount(0);
		dtmConsulta.setRowCount(0);
		dtmConsulta.addColumn("Id. Cliente");
		dtmConsulta.addColumn("Nombre");
		dtmConsulta.addColumn("Apellidos");
		dtmConsulta.addColumn("DNI");
		dtmConsulta.addColumn("Email");
		dtmConsulta.addColumn("Usuario");
		dtmConsulta.addColumn("Contraseña");
		dtmConsulta.addColumn("Puntos");
		dtmConsulta.addColumn("Fecha alta");
		dtmConsulta.addColumn("Alta/Baja");
		dtmConsulta.addColumn("");
		dtmConsulta.addColumn("");
		dtmConsulta.addColumn("");
		dtmConsulta.addColumn("");
		tabla_Consulta.setRowSorter (new TableRowSorter(dtmConsulta));
		tabla_Consulta.getTableHeader().setReorderingAllowed(false);
		cliente=Server.consultaCliente("");
		int tam=cliente.size();
		for(int i=0; i<tam; i++){
		   IdCliente=cliente.get(i).getID();
		   NombreCliente=cliente.get(i).getNombre();
		   ApellidosCliente=cliente.get(i).getApellidos();
		   DNICliente=cliente.get(i).getDNI();
		   EmailCliente=cliente.get(i).getEmail();
		   Usuario=cliente.get(i).getUser();
		   Password=cliente.get(i).getPass();
		   Puntos=cliente.get(i).getPuntos();
		   FechaAlta=cliente.get(i).getFechaAlta();
		   boolean activo=cliente.get(i).getActivo();
		   String estado="";
		   if(activo)
			   estado="Alta";
		   else
			   estado="Baja";
		   Object date[]={IdCliente,NombreCliente,ApellidosCliente,DNICliente,EmailCliente,Usuario,Password,Puntos+"",FechaAlta,estado};
		   tabla_Consulta.getColumnModel().getColumn(10).setCellRenderer(new KeyIconCellRenderer(10));
		   tabla_Consulta.getColumnModel().getColumn(11).setCellRenderer(new KeyIconCellRenderer(11));
		   tabla_Consulta.getColumnModel().getColumn(12).setCellRenderer(new KeyIconCellRenderer(12));
		   tabla_Consulta.getColumnModel().getColumn(13).setCellRenderer(new KeyIconCellRenderer(13));
		   dtmConsulta.addRow(date);
		}
		centrarDatos(); 
		scrollPaneConsulta.setVisible(true);
	}//fin buscarDatos

	
	void controlarEventos(final JPanel panelGClientes,final JPanel panel_Autenticacion){
		botonSalir.addActionListener(new ActionListener(){
			public void actionPerformed (ActionEvent e){	  
				int n = JOptionPane.showConfirmDialog (panelConsulta,"¿Desea cerrar sesión?","¿Cerrar sesión?",JOptionPane.YES_NO_OPTION);
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
				panelGClientes.setVisible(true);
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
					}
					else{
						text_buscar.setEnabled(true);
						botonFiltrar.setEnabled(true);
					   if(campoBusqueda.compareTo("Id. Cliente")==0)
						   columnaSeleccionada=0;
					   if(campoBusqueda.compareTo("Nombre")==0)
						   columnaSeleccionada=1;
					   if(campoBusqueda.compareTo("Apellidos")==0)
						   columnaSeleccionada=2;
					   if(campoBusqueda.compareTo("DNI")==0)
						   columnaSeleccionada=3;
					   if(campoBusqueda.compareTo("Email")==0)
						   columnaSeleccionada=4;
					   if(campoBusqueda.compareTo("Usuario")==0)
						   columnaSeleccionada=5;
					   if(campoBusqueda.compareTo("Contraseña")==0)
						   columnaSeleccionada=6;
					   if(campoBusqueda.compareTo("Puntos iniciales")==0)
						   columnaSeleccionada=7;
					   if(campoBusqueda.compareTo("Fecha alta")==0)
						   columnaSeleccionada=8;
					   if(campoBusqueda.compareTo("Alta/Baja")==0)
						   columnaSeleccionada=9;
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
				buscarClientes();
			}
	    });
	    
	    tabla_Consulta.addMouseListener(new MouseAdapter(){
			 public void mouseClicked(MouseEvent e){
			      filaTabla = tabla_Consulta.rowAtPoint(e.getPoint());
			      columnaTabla = tabla_Consulta.columnAtPoint(e.getPoint());
			      String idCliente=(String) tabla_Consulta.getValueAt(filaTabla,0);
			      String nombreCliente=(String) tabla_Consulta.getValueAt(filaTabla,1);
			      String apellidosCliente=(String) tabla_Consulta.getValueAt(filaTabla,2);
			      String DNI=(String) tabla_Consulta.getValueAt(filaTabla,3);
			      String email=(String) tabla_Consulta.getValueAt(filaTabla,4);
			      String usuario=(String) tabla_Consulta.getValueAt(filaTabla,5);
			      String contraseña=(String) tabla_Consulta.getValueAt(filaTabla,6);
			      String puntos=(String) tabla_Consulta.getValueAt(filaTabla,7);
			      String fechaAlta=(String) tabla_Consulta.getValueAt(filaTabla,8);
			      fechaAlta=fechaAlta.substring(0,19);
			      String estado=(String) tabla_Consulta.getValueAt(filaTabla,9);
			      if(columnaTabla==10){
			    	  if(estado.compareTo("Baja")==0){
			    		  int n = JOptionPane.showConfirmDialog (panelConsulta,"Cliente dado de baja. No se pueden editar sus datos","Error editar cliente",JOptionPane.CLOSED_OPTION);
			    	  }else{
			    		  new ModificarCliente(idCliente,nombreCliente,apellidosCliente,DNI,email,usuario,contraseña,
			    			  puntos,fechaAlta,frameConsulta,panelConsulta,panel_Autenticacion,dtmConsulta,tabla_Consulta);
			    	  }
			      }
			      if(columnaTabla==11){
			    	  if(estado.compareTo("Baja")==0){
			    		  int n = JOptionPane.showConfirmDialog (panelConsulta,"Error. El cliente ya esta dado de baja","Cliente en baja",JOptionPane.CLOSED_OPTION);
			    	  }else{
			    		  new BajaCliente(idCliente,nombreCliente,apellidosCliente,DNI,email,usuario,contraseña,
				    			  puntos,fechaAlta,frameConsulta,panelConsulta,panel_Autenticacion,dtmConsulta,tabla_Consulta);
			    	  } 
			      }
			      if(columnaTabla==12){
			    	  if(estado.compareTo("Baja")==0){
			    		  int n = JOptionPane.showConfirmDialog (panelConsulta,"Error. Imposibilidad de realizar duplicado de tarjeta a un cliente dado de baja.","Error duplicado tarjeta",JOptionPane.CLOSED_OPTION);
			    	  }else{
			    		  new DuplicarTarjeta(idCliente,frameConsulta,panelConsulta,panel_Autenticacion,dtmConsulta,tabla_Consulta);
			    	  }
			      }
			      if(columnaTabla==13){
			    	  int n = JOptionPane.showConfirmDialog (panelConsulta,"Funcionalidad aún no disponible.","Registrar móvil",JOptionPane.CLOSED_OPTION);
			    	 // new RegistrarMovil(idCliente,frameConsulta,panelConsulta,panel_Autenticacion,dtmConsulta,tabla_Consulta);
			      }
			 }
		});
	}//fin controlarEventos()
	
	
	
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
    		System.out.println("Secuencia: "+s);
    		if(valor.compareTo(texto)==0 || valor.contains(s)==true){
    			cuantos++;
    			System.out.println("Cuantos: "+cuantos);
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
			 tabla_Consulta.getColumnModel().getColumn(1).setPreferredWidth(35);
			 tabla_Consulta.getColumnModel().getColumn(2).setCellRenderer(modelocentrar);
			 tabla_Consulta.getColumnModel().getColumn(2).setPreferredWidth(70);
			 tabla_Consulta.getColumnModel().getColumn(3).setCellRenderer(modelocentrar);
			 tabla_Consulta.getColumnModel().getColumn(3).setPreferredWidth(35);
			 tabla_Consulta.getColumnModel().getColumn(4).setCellRenderer(modelocentrar);
			 tabla_Consulta.getColumnModel().getColumn(4).setPreferredWidth(100);
			 tabla_Consulta.getColumnModel().getColumn(5).setCellRenderer(modelocentrar);
			 tabla_Consulta.getColumnModel().getColumn(5).setPreferredWidth(50);
			 tabla_Consulta.getColumnModel().getColumn(6).setCellRenderer(modelocentrar);
			 tabla_Consulta.getColumnModel().getColumn(6).setPreferredWidth(50);
			 tabla_Consulta.getColumnModel().getColumn(7).setCellRenderer(modelocentrar);
			 tabla_Consulta.getColumnModel().getColumn(7).setPreferredWidth(50);
			 tabla_Consulta.getColumnModel().getColumn(8).setCellRenderer(modelocentrar);
			 tabla_Consulta.getColumnModel().getColumn(8).setPreferredWidth(100);
			 tabla_Consulta.getColumnModel().getColumn(9).setCellRenderer(modelocentrar);
			 tabla_Consulta.getColumnModel().getColumn(9).setPreferredWidth(10);
			 tabla_Consulta.getColumnModel().getColumn(10).setPreferredWidth(10);
			 tabla_Consulta.getColumnModel().getColumn(11).setPreferredWidth(10);
			 tabla_Consulta.getColumnModel().getColumn(12).setPreferredWidth(10);
			 tabla_Consulta.getColumnModel().getColumn(13).setPreferredWidth(10);
	}//fin centrarDatos()
}
