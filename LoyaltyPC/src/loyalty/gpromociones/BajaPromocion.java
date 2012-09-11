package loyalty.gpromociones;

import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import loyalty.bean.Promocion;
import loyalty.control.Server;


public class BajaPromocion {
	
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

	public BajaPromocion(String idPromocion,String idTienda,String nombreTienda,
			JPanel panelConsulta,DefaultTableModel dtmConsulta,JTable tabla_Consulta){
		this.dtmConsulta=dtmConsulta;
		this.tabla_Consulta=tabla_Consulta;
		int n=JOptionPane.showConfirmDialog(panelConsulta,"¿Desea dar de baja la promoción seleccionada?","Baja promoción",JOptionPane.OK_OPTION);
		if(n==0){
			desactivarPromocion(idPromocion,panelConsulta,idTienda,nombreTienda);
		}
	}
	
	void desactivarPromocion(String idPromocion,JPanel panelConsulta,String idTienda,String nombreTienda){
		boolean exito=Server.bajaPromocion(idPromocion);
		if(exito){
			int n=JOptionPane.showConfirmDialog(panelConsulta,"Baja realizada con éxito.","Baja promoción",JOptionPane.CLOSED_OPTION);
			 buscarPromociones(idTienda,nombreTienda);
			 panelConsulta.repaint();
		}else{
			int n=JOptionPane.showConfirmDialog(panelConsulta,"Imposible realizar la baja.","Error baja promoción",JOptionPane.CLOSED_OPTION);
		}
	}
	
	
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
