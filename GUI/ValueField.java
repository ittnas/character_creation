package GUI;

import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.JTextField;

import charInformation.Attribute;

public class ValueField extends JTextField {
    
    protected Attribute parent;
    
    public ValueField(Attribute parent) {
        super();
        this.parent = parent;
        setEditable(false);
        setPreferredSize(new Dimension(18, 16));
        setBorder(BorderFactory.createEmptyBorder()); //Do this!
        this.setOpaque(false);
        updateValue();
    }
    
    public void updateValue() {
        setText(Integer.toString(parent.getValue()));
        updateUI();
    }
    

}
