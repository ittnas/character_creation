package GUI;

import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

public class SheetLayout extends GridBagLayout {

    private int position = 0;

    public SheetLayout() {
        super();
    }

    public void addLayoutComponent(Component comp, Object constraints) {
        GridBagConstraints newConstraints;
        if (constraints != null) {
            newConstraints = (GridBagConstraints) constraints;
            if (newConstraints.gridwidth == 2) {
                position++;
                newConstraints.gridx = 0;
                newConstraints.gridy = position / 2;

            } else {
                newConstraints.gridx = position % 2;
                newConstraints.gridy = position / 2;
                newConstraints.weightx = 1.0;
            }
        } else {
            newConstraints = new GridBagConstraints(position % 2, position / 2,
                    1, 1, 1, 0, GridBagConstraints.NORTHWEST,
                    GridBagConstraints.HORIZONTAL, new Insets(5, 5, 5, 5), 0, 0);
        }
        position++;
        super.addLayoutComponent(comp, newConstraints);
    }
}
