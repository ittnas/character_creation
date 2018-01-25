package GUI;

import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

public class ItemLayout extends GridBagLayout {
    
    private int position = 0;

    public ItemLayout() {
        super();
    }
    
    public void addLayoutComponent(Component comp, Object constraints) {
        GridBagConstraints newConstraints;
        if (constraints != null) {
            newConstraints = (GridBagConstraints)constraints;
            newConstraints.gridy = position;
        } else {
            newConstraints = new GridBagConstraints(0, position,
                    1, 1, 0, 0, GridBagConstraints.NORTHWEST,
                    GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0);
        }
        super.addLayoutComponent(comp, newConstraints);
    }
    
    public void nextRow() {
        position++;
    }
    
    public void prevRow() {
        position--;
    }
}
