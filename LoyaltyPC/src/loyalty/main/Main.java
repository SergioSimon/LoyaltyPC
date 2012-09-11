/**
 * Main.java
 */
package loyalty.main;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;



/**
* Incia la aplicaci�n Loyalty PC o sistema de fidelizaci�n
*
* @author <a href="mailto:sergio.simon@gammasolutions.es">Sergio Sim�n Garrido</a>
* @version 1.0
*
* @since 1.0
*/
public class Main {
	    /**
	    * Llama a la clase new AccesoSistema para iniciar la aplicaci�n.
	    *
	    * @param args Argumentos pasados por la l�nea de comandos, en este caso ninguno
	    */
		public static void main(String[] args){
			 try{
					UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
				}catch(ClassNotFoundException e){
					// TODO Auto-generated catch block
					e.printStackTrace();
				}catch(InstantiationException e){
					// TODO Auto-generated catch block
					e.printStackTrace();
				}catch(IllegalAccessException e){
					// TODO Auto-generated catch block
					e.printStackTrace();
				}catch(UnsupportedLookAndFeelException e){
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				new AccesoSistema();
		}
}