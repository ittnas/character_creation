package GUI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;

import charInformation.Structure;

public class IncreaseListener implements ActionListener {
    
    boolean increase;
    Structure struct;
    String attribute;
    JPanel container;
    MainWindow window;
    
    public IncreaseListener(boolean increase, Structure struct, String attribute, MainWindow window) {
        this.increase = increase;
        this.struct = struct;
        this.attribute = attribute;
        this.window = window;
    }

    public void actionPerformed(ActionEvent e) {
        if(increase) {
            struct.addPoints(struct.PointsReqToIncrease(attribute, 1),attribute);
        } else {
            struct.addPoints(struct.PointsReqToIncrease(attribute, -1), attribute);
        }
        //window.updateAllValueFields();
        //window.updateAllPointsFields();
        window.updateAttributeContainers();
        window.updateAttributeInformationValue(attribute);
    }
}
