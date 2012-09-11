package loyalty.gcompras;

import javax.swing.ImageIcon;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;

@SuppressWarnings("serial")
public class KeyIconCellRenderer extends DefaultTableCellRenderer{   

	public KeyIconCellRenderer(int i){   
		if(i==8){
			 setIcon(new ImageIcon(getClass().getResource("/loyalty/images/acoger_promo.png")));
	   	     this.setHorizontalAlignment(SwingConstants.CENTER);
   	         this.setToolTipText("Acogerse promoción");
		}
    }   
} 
