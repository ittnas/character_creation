package charInformation;

import java.util.HashMap;

public class Inventory {
    
    private HashMap<Item, Integer> items;
    
    private HashMap<String, Item> itemNames;
    
    public Inventory() {
        items = new HashMap<Item, Integer>();
        itemNames = new HashMap<String, Item>();
    }
    
    public boolean addItem(String name, double price) {
        return addItem(name, 1, price);
    }
    
    public boolean addItem(String name, int amount, double price) {
        if (itemNames.containsKey(name)) {
            Item item = itemNames.get(name);
            items.put(item, items.get(item) + amount);
            return false;
        } else {
            Item item = new Item(name, price);
            itemNames.put(name, item);
            items.put(item, amount);
            return true;
        }
    }
    
    public Item getItem(String name) {
        if(itemNames.containsKey(name)) {
            return itemNames.get(name);
        } else {return null;}
        
    }
    
    public int getAmount(String name) {
        if(itemNames.containsKey(name)) {
            return items.get(itemNames.get(name));
        } else return 0;
    }
    
    public HashMap<String, Item> getItemMap() {
        return itemNames;
    }
    
    public int addAmount(String item, int amount) {
        int origAmount = items.get(itemNames.get(item));
        if (origAmount + amount > 0) {
            items.put(itemNames.get(item), origAmount + amount);
            return origAmount + amount;
        } else {
            items.put(itemNames.get(item), 0);
            return 0;
        }
    }
    
    public void removeItem(String item) {
        Item removable = itemNames.get(item);
        items.remove(removable);
        itemNames.remove(item);
    }
}
