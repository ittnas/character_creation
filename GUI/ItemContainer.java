package GUI;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JPanel;
import javax.swing.JTextField;

import charInformation.Inventory;

public class ItemContainer extends JPanel {
    
    private String name;
    
    private Inventory inv;
    
    public ItemContainer(String name, Inventory inv, MainWindow main) {
        super();
        setLayout(new GridBagLayout());
        setOpaque(false);
        this.name = name;
        this.inv = inv;
        JTextField nameField = new JTextField();
        nameField.setOpaque(false);
        nameField.setFont(main.getFont()); //h‰m‰r‰‰, kokeillaanpa
        nameField.setForeground(main.getFontColor());
        nameField.setText(name);
        nameField.setEditable(false);
        add(nameField, new GridBagConstraints(0, 0, 1, 1, 1.0, 1.0,
                GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(0,
                        0, 0, 0), 0, 0));
        JTextField amountField = new JTextField();
        amountField.setOpaque(false);
        amountField.setFont(main.getFont()); //h‰m‰r‰‰, kokeillaanpa
        amountField.setForeground(main.getFontColor());
        amountField.setText(new Integer(inv.getAmount(name)).toString());
        amountField.setColumns(2);
        amountField.setEditable(false);
        add(amountField);
        
    }
    
    public void changeAmount(int amount) {
            inv.addAmount(name, amount);
    }
    
    public void remove(){
        inv.removeItem(name);
        this.getParent().remove(this);
    }
        
    

}
