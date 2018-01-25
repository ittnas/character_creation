package GUI;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class AttributeInfoListener extends MouseAdapter {
    
    private String attribute;
    
    private MainWindow main;
    
    public AttributeInfoListener(String attribute, MainWindow mainWindow) {
        this.attribute = attribute;
        main = mainWindow;
        
    }
    
    public void mouseEntered(MouseEvent e) {
        main.updateAttributeInformationValue(attribute);
    }
}
