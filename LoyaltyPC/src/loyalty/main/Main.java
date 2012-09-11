/**
 * Main.java
 */
package loyalty.main;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;



/**
* Incia la aplicación Loyalty PC o sistema de fidelización
*
* @author <a href="mailto:sergio.simon@gammasolutions.es">Sergio Simón Garrido</a>
* @version 1.0
*
* @since 1.0
*/
public class Main {
	    /**
	    * Llama a la clase new AccesoSistema para iniciar la aplicación.
	    *
	    * @param args Argumentos pasados por la línea de comandos, en este caso ninguno
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