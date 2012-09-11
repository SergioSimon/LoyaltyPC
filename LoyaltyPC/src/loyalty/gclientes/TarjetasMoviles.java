package loyalty.gclientes;
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import loyalty.control.Server;

import gammasol.nfc2.util.*;
import gammasol.nfc2.tag.*;
import gammasol.pcsc.*;
import gammasol.nfc2.*;

/**
 * Clase de ejemplo de convivencia de tarjetas y moviles
 * El metodo principal es ejemploTarjetasYMoviles();
 * Desde este ,enfuncion del tipo de elemento se llama alos diferentes metodos
 *
 * @author Fermin Gallego < fermin.gallego.sac_A_gmail.com >
 */
public class TarjetasMoviles {

    static gammasol.nfc2.emu.SCard14emu scard14;
    
    //public static boolean terminar=true;//modoficiaco por mi (original no hay nada)

    public static void ejemploTarjetasYMoviles(JTextField text_uidTarjeta,JPanel panelConsulta,JPanel panelDuplicarTarjeta,
    		String idCliente) {
       // while (terminar) { //modificado por mi (original (true)
            System.out.println("****************************************************************");
            switch (TipoNFC.obtener()) {
                case TipoNFC.NEXUS_ONE:
                    System.out.println("NEXUS");
                    ejemploNEXUS(text_uidTarjeta);
                    break;
                case TipoNFC.MIFARE1K:
                    System.out.println("Mifare1k");
                    ejemploMifare(text_uidTarjeta,panelConsulta,panelDuplicarTarjeta,idCliente);
                    break;
                case TipoNFC.TOPAZ:
                    System.out.println("Topaz");
                    break;
                case TipoNFC.MIFARE_ULTRALIGTH:
                	  ejemploMifare(text_uidTarjeta,panelConsulta,panelDuplicarTarjeta,idCliente);
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

    public static void ejemploMifare(JTextField text_uidTarjeta,JPanel panelConsulta,JPanel panelDuplicarTarjeta,
    		String idCliente) {
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
				text_uidTarjeta.setText(UID);
				int j=JOptionPane.showConfirmDialog (panelConsulta,"¿Desea duplicar la tarjeta?","Duplicar Tarjeta",JOptionPane.OK_OPTION);
				if(j==0){
					boolean duplicado=Server.duplicarTarjeta(idCliente,UID);
					if(duplicado){
						JOptionPane.showConfirmDialog (panelConsulta,"Tarjeta duplicada con éxito.","Tarjeta Duplicada",JOptionPane.CLOSED_OPTION);
						panelDuplicarTarjeta.setVisible(false);
						panelConsulta.setVisible(true);
					}else{
						JOptionPane.showConfirmDialog (panelConsulta,"Error, la tarjeta ya ha sido desactivada.","Error Tarjeta Duplicada",JOptionPane.CLOSED_OPTION);
						panelDuplicarTarjeta.setVisible(false);
						panelConsulta.setVisible(true);
					}
					
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            m1.cerrar();
        } catch (MifareException mf) {
        }
    }

    public static void ejemploNEXUS(JTextField text_uidTarjeta) {
        scard14 = new gammasol.nfc2.emu.SCard14emu();
        scard14.preparar(0);
        int ntransmisiones = 0;
        boolean respuesta = false;
        while (true) {
            respuesta = scard14.espera();
            //si detecta la presencia del movil
            if (respuesta) {
                //procedimiento de envio
                tx(text_uidTarjeta);
                scard14.terminar();
                System.out.println("Numero de transmision:" + (ntransmisiones++) + "\n\n");
                return;
            }
        }
    }

    public static void tx(JTextField text_uidTarjeta) {
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
        
        System.out.println("----Comunicacion iniciada 2-------------");
        //descartamos el primer APDU
        btRX = scard14.intercambio("HolaDesdePC0".getBytes());
        if (btRX != null) {
            //00 A4 04 00 07 D2 76 00 00 85 01 00
            //System.out.println(toHex(btRX));
        } else {
            System.out.println("Forzar  FIN  -------------------");
            scard14.reset14();
            return;
        }
        
       if(imei.compareTo("354494040334002")==0){
    	   text_uidTarjeta.setText(imei);
    	   btRX = scard14.intercambio("OK".getBytes());
       }
       
       if(imei.compareTo("355921043795242")==0){
    	   text_uidTarjeta.setText(imei);
    	   btRX = scard14.intercambio("OK".getBytes());
       }
       
       if(imei.compareTo("355921043795283")==0){
    	   text_uidTarjeta.setText(imei);
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
    
    
    public static String getHexString(byte[] b) throws Exception {
    	  String result = "";
    	  for (int i=0; i < b.length; i++) {
    	    result +=
    	          Integer.toString( ( b[i] & 0xff ) + 0x100, 16).substring( 1 );
    	  }
    	  return result;
    	}

}