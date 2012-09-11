package loyalty.gcompras;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableRowSorter;
import loyalty.control.Server;
import loyalty.gclientes.Hilo_RegistrarTarjeta;


public class ConsultaPuntos {
	JFrame frameConsulta;
	JPanel panelConsulta=new JPanel();
	
	ImageIcon iconoSalir = new ImageIcon(getClass().getResource("/loyalty/images/exit.png")); 
	ImageIcon iconoAtras = new ImageIcon(getClass().getResource("/loyalty/images/btnVolver.png"));
	Icon imagenCabecera = new ImageIcon(getClass().getResource("/loyalty/images/cabecera.png"));
	Icon imagenMano = new ImageIcon(getClass().getResource("/loyalty/images/manoTarjeta.png"));
	
	JButton botonAtras=new JButton("", iconoAtras);
	JButton botonSalir=new JButton("", iconoSalir);

	JLabel titulo = new JLabel("CONSULTA DE PUNTOS");
	
	JLabel lb_informacion=new JLabel("\"PASE LA TARJETA NFC POR EL LECTOR\"");
	JLabel lb_imeiUID=new JLabel("·Imei/UID:");
	JLabel lb_puntos=new JLabel("·Puntos:");
	
	JTextField text_imeiUID=new JTextField(50);
	JTextField text_puntos=new JTextField(50);
	
	JLabel fondo_imagen=new JLabel();
	JLabel lb_cabecera=new JLabel(imagenCabecera);
	JLabel mano = new JLabel (imagenMano);
	
	
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
		           setIcon(new ImageIcon(getClass().getResource("/obp/images/comentario.png")));
		   	       this.setHorizontalAlignment(SwingConstants.CENTER);
		   	       this.setToolTipText("Comentario revisión");
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
    
    Hilo_ConsultarPromos mihilo = new Hilo_ConsultarPromos();

	public ConsultaPuntos(JPanel panelInicial,JPanel panel_Autenticacion,JPanel panelGCompras,JFrame frame_GCompras,
			String idTienda){
		frameConsulta=frame_GCompras;
		panelGCompras.setVisible(false);
		configurarInterfaz();
		controlarEventos(panel_Autenticacion,panelGCompras);
		buscarPromociones();
		mihilo.setTextField(text_imeiUID);
		mihilo.setTextnombre(text_puntos);
		mihilo.setPanel(panelConsulta);
		mihilo.setDtm(dtmConsulta);
		mihilo.setTabla(tabla_Consulta);
		mihilo.setScroll(scrollPaneConsulta);
		mihilo.setIdTienda(idTienda);
		mihilo.setMano(mano);
		mihilo.setTerminar(false);
	    mihilo.start();
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
		int anchuraTexto=100;
		int anchuraInput=170;
		int anchuraInfo=500;
		
		int alturaDatos = pantalla.height/2;
		int anchuraDatos = pantalla.width-(2*offsetBasico);
	    
		int offsetSuperiorFila01 = 80 + offsetBasico;
		int offsetSuperiorFila02 = offsetSuperiorFila01 + offsetBasico + alturaInput;
		int offsetSuperiorFila03 = offsetSuperiorFila02 + offsetBasico + alturaInput;
		int offsetSuperiorTabla = offsetSuperiorFila02 + offsetBasico + alturaInput;
		
		int offsetLateralColumna01 = offsetBasico;
		int offsetLateralColumna02 = offsetLateralColumna01 + offsetBasico + anchuraTexto + anchuraInput;
		int offsetLateralColumna03 = offsetLateralColumna02 + offsetBasico + anchuraTexto + anchuraInput;
		
		int offsetLateralInputColumna01= offsetLateralColumna01 + anchuraTexto;
		int offsetLateralInputColumna02= offsetLateralColumna02 + anchuraTexto;
	    
	    int alturaTitulo = 50;
	    int anchuraTitulo = 500;
	    int offsetLateralTitulo = 100;
	    int offsetSuperiorTitulo = 15;
	    /*
		int offsetSuperiorFila01 = alturaBotonesMenu + offsetBasico;
	    int offsetSuperiorFila02 = offsetSuperiorFila01 + offsetBasico + alturaInput;
	    int offsetSuperiorFila03 = offsetSuperiorFila02 + offsetBasico + alturaInput;
	    int offsetLateralColumna01 = (pantalla.width/4) - (anchuraBotonesMenu);
	    int offsetLateralColumna02 = offsetLateralColumna01 + anchuraBotonesMenu + offsetBasico;
	    */
		
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
	    
	    lb_imeiUID.setBounds(offsetLateralColumna01,offsetSuperiorFila01,anchuraTexto, alturaInput);
	    lb_imeiUID.setFont(new Font(fuenteTexto,estiloTexto,tamTextoBotones));
	    lb_imeiUID.setVisible(true);
	    panelConsulta.add(lb_imeiUID);
	 	text_imeiUID.setFont(new Font(fuenteTexto,estiloTexto,tamTextoBotones));
	 	text_imeiUID.setBounds(offsetLateralInputColumna01,offsetSuperiorFila01,anchuraInput, alturaInput);
	 	text_imeiUID.setEditable(true);
	 	text_imeiUID.setText(null);
	 	text_imeiUID.setVisible(true);
	 	text_imeiUID.setEditable(false);
	 	panelConsulta.add(text_imeiUID);
	 	
	 	lb_informacion.setBounds(pantalla.width/2 - anchuraInfo/2+40,offsetSuperiorFila02,anchuraInfo,alturaInput);
	 	lb_informacion.setFont(new Font(fuenteTexto,estiloTexto,tamTextoBotones));
	 	lb_informacion.setVisible(true);
		panelConsulta.add(lb_informacion);
		
		mano.setBounds(offsetLateralColumna03-imagenMano.getIconWidth()/5,offsetSuperiorFila03,imagenMano.getIconWidth(),imagenMano.getIconHeight());
	 	mano.setVisible(true);
	 	panelConsulta.add(mano);
		
	 	lb_puntos.setBounds(offsetLateralColumna02,offsetSuperiorFila01,anchuraTexto, alturaInput);
	 	lb_puntos.setFont(new Font(fuenteTexto,estiloTexto,tamTextoBotones));
	 	lb_puntos.setVisible(true);
	 	panelConsulta.add(lb_puntos);
		text_puntos.setFont(new Font(fuenteTexto,estiloTexto,tamTextoBotones));
		text_puntos.setBounds(offsetLateralInputColumna02,offsetSuperiorFila01,anchuraInput, alturaInput);
		text_puntos.setEditable(true);
		text_puntos.setText(null);
		text_puntos.setVisible(true);
		text_puntos.setEditable(false);
		panelConsulta.add(text_puntos);
		
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
	}//fin configurarInterfaz()
	
	void controlarEventos(final JPanel panel_Autenticacion,final JPanel panelGCompras){
		 botonSalir.addActionListener(new ActionListener(){
				public void actionPerformed (ActionEvent e){	  
					int n = JOptionPane.showConfirmDialog (panelConsulta,"¿Desea salir del sistema?","¿Salir Sistema?",JOptionPane.YES_NO_OPTION);
			        if(n==0){
			        	  mihilo.setTerminar(true);
			        	  boolean LogOut=Server.LogOut();
			        	  panelConsulta.setVisible(false);
			        	  panel_Autenticacion.setVisible(true);
						  System.gc();
			        }
				}
		  });
		 
		 botonAtras.addActionListener(new ActionListener(){
				public void actionPerformed (ActionEvent e){	
					mihilo.setTerminar(true);
					panelConsulta.setVisible(false);
			        panelGCompras.setVisible(true);
			        System.gc();
				}
		  });
	}//fin controlarEventos()
	
	
	@SuppressWarnings({"rawtypes","unchecked"})
	void buscarPromociones(){
		dtmConsulta.setColumnCount(0);
		dtmConsulta.setRowCount(0);
		dtmConsulta.addColumn("Id. Promoción");
		dtmConsulta.addColumn("Id. Tienda");
		dtmConsulta.addColumn("Descripción");
		dtmConsulta.addColumn("Fecha Inicio");
		dtmConsulta.addColumn("Fecha Fin");
		dtmConsulta.addColumn("Puntos Necesarios");
		dtmConsulta.addColumn("Importe");
		tabla_Consulta.setRowSorter (new TableRowSorter(dtmConsulta));
		tabla_Consulta.getTableHeader().setReorderingAllowed(false);
		centrarDatos(); 
		//scrollPaneConsulta.setVisible(true);
	}
	
	
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
	}//fin centrarDatos()
}
