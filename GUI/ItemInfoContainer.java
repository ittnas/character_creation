package GUI;

import javax.swing.JTextField;

import charInformation.Inventory;

public class ItemInfoContainer {
    
    private String name;
    
    private Inventory inv;
    
    private JTextField nameField;
    
    private JTextField amountField;
    
    private JTextField priceField;
    
    public ItemInfoContainer(String name, Inventory inv, JTextField nameField, JTextField amountField, JTextField priceField) {
        this.name = name;
        this.inv = inv;
        this.nameField = nameField;
        this.amountField = amountField;
        this.priceField = priceField;
        this.priceField.setText(new Double(inv.getItem(name).getPrice()).toString());
        this.nameField.setText(name);
        this.amountField.setText(new Integer(inv.getAmount(name)).toString());
        }
    
    public void changeAmount(int amount) {
            amountField.setText(new Integer(inv.addAmount(name, amount)).toString());
    }
    
    public void remove() {
        inv.removeItem(name);
    }

}
