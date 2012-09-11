package loyalty.gcompras;

import gammasol.nfc2.TipoNFC;
import gammasol.nfc2.tag.Mifare1k;
import gammasol.nfc2.tag.MifareException;

import java.awt.TextField;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import loyalty.bean.Cliente;
import loyalty.bean.Promocion;
import loyalty.bean.TicketCompra;
import loyalty.control.Server;
import gammasol.nfc2.util.*;
import gammasol.nfc2.tag.*;
import gammasol.pcsc.*;
import gammasol.nfc2.*;


public class Hilo_Compra extends Thread{
	
	private static String importe;
	private static JPanel panelCompras;
	private static String concepto;
	private static String idTienda;
	private static TextField textPuntosTotales;
	private static TextField textPuntosObtenidos;
	private static JTable tabla_Consulta;
	private static DefaultTableModel dtmConsulta;
	private static JLabel lb_textoInformativo;
	private static JLabel mano;
	private static JScrollPane scrollPaneConsulta;
	private static TextField textIdCliente;
	
	static gammasol.nfc2.emu.SCard14emu scard14;

	public void setTextPrecio(String importe) {
		Hilo_Compra.importe=importe;
	}

	public void setPanel(JPanel panelCompras) {
		Hilo_Compra.panelCompras=panelCompras;
	}

	public void setConcepto(String concepto) {
		Hilo_Compra.concepto=concepto;
	}

	public void setIdTienda(String idTienda) {
		Hilo_Compra.idTienda=idTienda;
	}
	

	public void setTextPuntosObtenidos(TextField textPuntosObtenidos) {
		Hilo_Compra.textPuntosObtenidos=textPuntosObtenidos;
	}

	public void setTextPuntosTotales(TextField textPuntosTotales) {
		Hilo_Compra.textPuntosTotales=textPuntosTotales;
	}
	
	public void setTabla(JTable tabla_Consulta) {
		Hilo_Compra.tabla_Consulta=tabla_Consulta;
	}

	public void setDefault(DefaultTableModel dtmConsulta) {
		Hilo_Compra.dtmConsulta=dtmConsulta;
	}

	public void setScroll(JScrollPane scrollPaneConsulta) {
		Hilo_Compra.scrollPaneConsulta=scrollPaneConsulta;
	}
	

	public void setInformacion(JLabel lb_textoInformativo) {
		Hilo_Compra.lb_textoInformativo=lb_textoInformativo;
	}

	public void setImagen(JLabel mano) {
		Hilo_Compra.mano=mano;
	}
	
	public void setUID(TextField textIdCliente) {
		Hilo_Compra.textIdCliente=textIdCliente;
	}
	

	//Para iniciar el hilo
	public synchronized  void run (){
		ejemploTarjetasYMoviles(panelCompras,importe,concepto,idTienda,textPuntosObtenidos,textPuntosTotales,
				tabla_Consulta,dtmConsulta,scrollPaneConsulta,lb_textoInformativo,mano,textIdCliente);
		System.gc();
	}
	
	public static void ejemploTarjetasYMoviles(JPanel panelCompras,String importe,String concepto,String idTienda,
    		TextField textPuntosObtenidos,TextField textPuntosTotales,JTable tabla_Consulta,DefaultTableModel dtmConsulta,
    		JScrollPane scrollPaneConsulta,JLabel lb_textoInformativo,JLabel mano,TextField textIdCliente) {
       // while (terminar) { //modificado por mi (original (true)
            System.out.println("****************************************************************");
            switch (TipoNFC.obtener()) {
                case TipoNFC.NEXUS_ONE:
                    System.out.println("NEXUS");
                    ejemploNEXUS(panelCompras,importe,concepto,idTienda,textPuntosObtenidos,textPuntosTotales,tabla_Consulta,
                    		dtmConsulta,scrollPaneConsulta,lb_textoInformativo,mano);
                    break;
                case TipoNFC.MIFARE1K:
                    System.out.println("Mifare1k");
                    ejemploMifare(panelCompras,importe,concepto,idTienda,textPuntosObtenidos,textPuntosTotales,tabla_Consulta,
                    		dtmConsulta,scrollPaneConsulta,lb_textoInformativo,mano,textIdCliente);
                    break;
                case TipoNFC.TOPAZ:
                    System.out.println("Topaz");
                    break;
                case TipoNFC.MIFARE_ULTRALIGTH:
                	  ejemploMifare(panelCompras,importe,concepto,idTienda,textPuntosObtenidos,textPuntosTotales,tabla_Consulta,
                			  dtmConsulta,scrollPaneConsulta,lb_textoInformativo,mano,textIdCliente);
                    System.out.println("Mifare UltraLIgth");
                    break;
                case 8:
                    break;
                case 5:
                    break;
            }try{
 
                Object o = new Object();
                synchronized (o) {
                    //Tiempo de espera entre 300 y 500 ms
                    o.wait(1000);
                   // terminar=false;
                }
            } catch (Exception ex) {
            }
           // terminar=false; //modificado por mi (original no hay nada)
       // }
    }

    public static void ejemploMifare(JPanel panelCompras,String importe,String concepto,String idTienda,
    		TextField textPuntosObtenidos,TextField textPuntosTotales,JTable tabla_Consulta,DefaultTableModel dtmConsulta,
    		JScrollPane scrollPaneConsulta,JLabel lb_textoInformativo,JLabel mano,TextField textIdCliente) {
        try {
            byte[] vieja = new byte[]{(byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF};
            byte blq = 0x10;//Sector 4,bloque 0
            boolean resultado = false;//90 00 true,63 00 false
            System.out.println("Ejemplo con el bloque 0x10 (Sector 4,bloque 0)");
            Mifare1k m1 = new Mifare1k(0);
            resultado = m1.cargarClave(vieja, Mifare1k.TIPO_A);
            System.out.println("Cargar clave:....." + resultado);
            resultado = m1.autenticar(blq);
            System.out.println("Autenticacion:...." + resultado);
            resultado = m1.rTBloque((byte) 0x10);
            System.out.println("Prueba leer:......" + resultado);
            byte[] uid= m1.getUID();
            for (int i = 0; i < uid.length; i++) {
				System.out.print(uid[i]);
			}
            System.out.println(new String(uid));
            try {
				 String UID=getHexString(uid);
				 ArrayList<Cliente>cliente=Server.consultaCliente(UID);
				 System.out.println("Puntos antes de la compra:" +cliente.get(0).getPuntos());
				 
				 TicketCompra ticket=Server.insertarCompra(cliente.get(0),idTienda,UID,importe,concepto,"true"); //true=tarjetaNFC
				 ArrayList<Cliente>cliente1=Server.consultaCliente(UID);
				 String idCliente=cliente1.get(0).getID();
				 textIdCliente.setText(idCliente);
				 int puntos= ticket.getPuntosAcumulados();
				 textPuntosObtenidos.setText(puntos+"");
				 int puntosTotales=cliente1.get(0).getPuntos();
				 textPuntosTotales.setText(puntosTotales+"");
				 ArrayList<Promocion> promocion=Server.getPromociones("1",puntosTotales+"");
				 if(promocion!=null){
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
					 dtmConsulta.addColumn("");
					 int tam=promocion.size();
						for(int i=0; i<tam; i++){
							String idPromocion=promocion.get(i).getIdPromocion();
							String IdTienda=promocion.get(i).getIdTienda();
							String descripcion=promocion.get(i).getDescripcion();
							String fechaInicio=promocion.get(i).getFechaInicio();
							String fechaFin=promocion.get(i).getFechaFin();
							int points=promocion.get(i).getPuntos();
							double importeCompra=promocion.get(i).getImporte();
							String url=promocion.get(i).getURL();
							Object date[]={idPromocion,IdTienda,descripcion,fechaInicio,fechaFin,points+"",importeCompra+"",url};
							tabla_Consulta.getColumnModel().getColumn(8).setCellRenderer(new KeyIconCellRenderer(8));
							dtmConsulta.addRow(date);
						}
					 centrarDatos();
					 lb_textoInformativo.setVisible(false);
					 mano.setVisible(false);
					 scrollPaneConsulta.setVisible(true);
				 }
				 
			} catch (Exception e) {
				 //int n = JOptionPane.showConfirmDialog (panelConsulta,"Error, la etiqueta NFC no ha sido registrada.","Sin registros en BBDD",JOptionPane.CLOSED_OPTION);
				e.printStackTrace();
			}
            m1.cerrar();
        } catch (MifareException mf) {
        }
    }

    public static void ejemploNEXUS(JPanel panelCompras,String importe,String concepto,String idTienda,
    		TextField textPuntosObtenidos,TextField textPuntosTotales,JTable tabla_Consulta,DefaultTableModel dtmConsulta,
    		JScrollPane scrollPaneConsulta,JLabel lb_textoInformativo,JLabel mano) {
        scard14 = new gammasol.nfc2.emu.SCard14emu();
        scard14.preparar(0);
        int ntransmisiones = 0;
        boolean respuesta = false;
        while (true) {
            respuesta = scard14.espera();
            //si detecta la presencia del movil
            if (respuesta) {
                //procedimiento de envio
                tx(panelCompras,importe,concepto,idTienda,textPuntosObtenidos,textPuntosTotales,tabla_Consulta,dtmConsulta,
                		scrollPaneConsulta,lb_textoInformativo,mano);
                scard14.terminar();
                System.out.println("Numero de transmision:" + (ntransmisiones++) + "\n\n");
                return;
            }
        }
    }
    
    
    public static void tx(JPanel panelCompras,String importe,String concepto,String idTienda,
    		TextField textPuntosObtenidos,TextField textPuntosTotales,JTable tabla_Consulta,DefaultTableModel dtmConsulta,
    		JScrollPane scrollPaneConsulta,JLabel lb_textoInformativo,JLabel mano) {
    	 System.out.println("----Comunicacion iniciada-------------");
         //descartamos el primer APDU
         byte btRX[] = scard14.intercambio("HolaDesdePC0".getBytes());
         if (btRX != null) {
             //00 A4 04 00 07 D2 76 00 00 85 01 00
             //System.out.println(toHex(btRX));
         } else {
             System.out.println("Forzar  FIN  -------------------");
             scard14.reset14();
             return;
         }
         //--------------------------------------
         //     Comienza el envio REAL
         //--------------------------------------
         
         //Envio cadena vacía para saber imei
         btRX = scard14.intercambio("".getBytes());
         String imei = new String(btRX);
         System.out.println("Imeiiiiiiiiiiiiiiiiii: "+imei);
     
        Cliente cliente=Server.ConsultaClienteUser(imei);
		System.out.println("Puntos antes de la compra:" +cliente.getPuntos());
		TicketCompra ticket=Server.insertarCompra(cliente,idTienda,imei,importe,concepto,"false"); //false=User nfc movil
		Cliente cliente1=Server.ConsultaClienteUser(imei);
		String idCliente=cliente1.getID();
		textIdCliente.setText(idCliente);
		int puntos= ticket.getPuntosAcumulados();
		textPuntosObtenidos.setText(puntos+"");
		int puntosTotales=cliente1.getPuntos();
		textPuntosTotales.setText(puntosTotales+"");
		
		
		ArrayList<Promocion> promocion=Server.getPromociones("1",puntosTotales+"");
		if(promocion!=null){
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
			 dtmConsulta.addColumn("");
			 int tam=promocion.size();
				for(int i=0; i<tam; i++){
					String idPromocion=promocion.get(i).getIdPromocion();
					String IdTienda=promocion.get(i).getIdTienda();
					String descripcion=promocion.get(i).getDescripcion();
					String fechaInicio=promocion.get(i).getFechaInicio();
					String fechaFin=promocion.get(i).getFechaFin();
					int points=promocion.get(i).getPuntos();
					double importeCompra=promocion.get(i).getImporte();
					String url=promocion.get(i).getURL();
					Object date[]={idPromocion,IdTienda,descripcion,fechaInicio,fechaFin,points+"",importeCompra+"",url};
					tabla_Consulta.getColumnModel().getColumn(8).setCellRenderer(new KeyIconCellRenderer(8));
					dtmConsulta.addRow(date);
				}
			 centrarDatos();
			 lb_textoInformativo.setVisible(false);
			 mano.setVisible(false);
			 scrollPaneConsulta.setVisible(true);
		}
        
        
        System.out.println("----Comunicacion iniciada 2-------------");
        //descartamos el primer APDU
        btRX = scard14.intercambio("HolaDesdePC1".getBytes());
        if (btRX != null) {
            //00 A4 04 00 07 D2 76 00 00 85 01 00
            //System.out.println(toHex(btRX));
        	
        } else {
            System.out.println("Forzar  FIN  -------------------");
            scard14.reset14();
            return;
        }
        
       if(imei.compareTo("OK")!=0){

    	   btRX = scard14.intercambio("OK".getBytes());
       }
       
       if(imei.compareTo("355921043795242")==0){

    	   btRX = scard14.intercambio("OK".getBytes());
       }
       
       if(imei.compareTo("355921043795283")==0){

    	   btRX = scard14.intercambio("NOOK".getBytes());
       }
        
        
        if (btRX != null) {
            System.out.println(new String(btRX));
        } else {
            System.out.println("Forzar  FIN  -------------------");
            scard14.reset14();
            return;
        }
        btRX = scard14.intercambio("HolaDesdePC2".getBytes());
        if (btRX != null) {
            System.out.println(new String(btRX));
        }
        btRX = scard14.intercambio("HolaDesdePC3".getBytes());
        if (btRX != null) {
            System.out.println(new String(btRX));
        }
        btRX = scard14.intercambio("HolaDesdePC4".getBytes());
        if (btRX != null) {
            System.out.println(new String(btRX));
        }
        System.out.println("FIN comunicacion  -------------------");
        scard14.reset14();
    }

//    public static void tx(JPanel panelCompras,String importe,String concepto,String idTienda,
//    		TextField textPuntosObtenidos,TextField textPuntosTotales,JTable tabla_Consulta,DefaultTableModel dtmConsulta,
//    		JScrollPane scrollPaneConsulta,JLabel lb_textoInformativo,JLabel mano) {
//        System.out.println("----Comunicacion iniciada-------------");
//        //descartamos el primer APDU
//        byte btRX[] = scard14.intercambio("HolaDesdePC0".getBytes());
//        if (btRX != null) {
//            //00 A4 04 00 07 D2 76 00 00 85 01 00
//            //System.out.println(toHex(btRX));
//        } else {
//            System.out.println("Forzar  FIN  -------------------");
//            scard14.reset14();
//            return;
//        }
//        //--------------------------------------
//        //     Comienza el envio REAL
//        //--------------------------------------
//        
//        //Envio cadena vacía para saber imei
//        btRX = scard14.intercambio("".getBytes());
//        String imei = new String(btRX);
//        System.out.println("Imeiiiiiiiiiiiiiiiiii: "+imei);
//        
//        System.out.println("----Comunicacion iniciada 2-------------");
//        //descartamos el primer APDU
//        btRX = scard14.intercambio("HolaDesdePC0".getBytes());
//        if (btRX != null) {
//            //00 A4 04 00 07 D2 76 00 00 85 01 00
//            //System.out.println(toHex(btRX));
//        } else {
//            System.out.println("Forzar  FIN  -------------------");
//            scard14.reset14();
//            return;
//        }
//        
//       if(imei.compareTo("user")!=0){
//    	  
//    	   btRX = scard14.intercambio("OK".getBytes());
//       }
//       
////       if(imei.compareTo("355921043795242")==0){
////    	   
////    	   btRX = scard14.intercambio("OK".getBytes());
////       }
////       
////       if(imei.compareTo("355921043795283")==0){
////    	   
////    	   btRX = scard14.intercambio("NOOK".getBytes());
////       }
//        
//        
//        if (btRX != null) {
//            System.out.println(new String(btRX));
//        } else {
//            System.out.println("Forzar  FIN  -------------------");
//            scard14.reset14();
//            return;
//        }
//        btRX = scard14.intercambio("HolaDesdePC2".getBytes());
//        if (btRX != null) {
//            System.out.println(new String(btRX));
//        }
//        btRX = scard14.intercambio("HolaDesdePC3".getBytes());
//        if (btRX != null) {
//            System.out.println(new String(btRX));
//        }
//        btRX = scard14.intercambio("HolaDesdePC4".getBytes());
//        if (btRX != null) {
//            System.out.println(new String(btRX));
//        }
//        System.out.println("FIN comunicacion  -------------------");
//        scard14.reset14();
//    }
//    
    
    public static String getHexString(byte[] b) throws Exception {
    	  String result = "";
    	  for (int i=0; i < b.length; i++) {
    	    result +=
    	          Integer.toString( ( b[i] & 0xff ) + 0x100, 16).substring( 1 );
    	  }
    	  return result;
    	}
      
    
    /**
	* Método centrarDatos() que centra los datos centrados visualizados en cada celda de manera centrada
	*/
	public static void centrarDatos(){
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
