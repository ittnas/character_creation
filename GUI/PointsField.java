package GUI;

import java.awt.Dimension;

import charInformation.Attribute;

public class PointsField extends ValueField {
    
    public PointsField(Attribute parent) {
        super(parent);
        setPreferredSize(new Dimension(24,16));
    }
    
    public void updateValue() {
        //setText(Double.toString((double)(int)(parent.getPoints()*10)/10));
        setText(new Integer((int)parent.getPoints()).toString());
        updateUI();
    }
    
}
