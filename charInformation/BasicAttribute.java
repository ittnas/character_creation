package charInformation;

import java.lang.Math;
import java.util.ArrayList;

public class BasicAttribute extends Attribute {

    ArrayList<Skill> children;

    public BasicAttribute(String name) {
        super(name);
        double min = Structure.globalVariables.get("minValue");
        this.value = (int) min;

        this.children = new ArrayList<Skill>();
    }

    /**
     * Updates the value of the attribute. Value is calculated using points
     * spent. If price is zero nothing is done.
     */

    void updateValue() {
        if (price == 0) {
            // Do something, this fails big time!
        } else {
            value = (int) (Math.log((points
                    * (Structure.globalVariables.get("attrLog") - 1) + price)
                    / price)
                    / Math.log(Structure.globalVariables.get("attrLog")) + Structure.globalVariables
                    .get("minValue")); // Maybe right
        }
        // value = (int)(cost*(Math.pow(progression,
        // value-minValue)-1)/(progression-1)); //WRONG
    }

    /**
     * Calculates and sets the price of the basic attribute. If attribute has no
     * parents, price is 0. To calculate the price static value skillDep is
     * used.
     * 
     * @return new price of the attribute.
     */

    public double calcPrice() // T‰‰ metodihan on jotain aivan t‰ytt‰ kuraa | ei ookaan, jee!
    {
        if (children.size() == 0) {
            price = 0;
            return price;
        }
        double price = 0;
        for (int i = 0; i < children.size(); i++) {
            Skill child = children.get(i);
            if (child.getChildren().size() == 0) {
            price += child.calcPrice()
                    / child.getAncestorNumber()
                    * (child.getAttributeDep() + (1 - child.getAttributeDep())
                            * Structure.globalVariables.get("skillDep"));
            }
        }
        this.price = price*Structure.globalVariables.get("priceMultiplier");
        return this.price;
    }

    /**
     * Adds a skill to Attributes list of children.
     * 
     * @param child
     */
    public void addChild(Skill child) {
        this.children.add(child);
    }

    private double pointsReqToGetValue(int value) {
        return (price
                * Math.pow(Structure.globalVariables.get("attrLog"), value
                        - Structure.globalVariables.get("minValue")) - price)
                / (Structure.globalVariables.get("attrLog") - 1);
    }

    public double pointsReqToChange(int amount) {
        return pointsReqToGetValue(value + amount) - points;
    }

    public double addPoints(double points, boolean addRemovable, boolean addPointsToAdd) {
        double added = 0;
        if (points < 0) {
            added = -removePoints(-points);
        } else {
            if(addRemovable) {
                removable += points;
            }
            this.points += points;
            added = points;
            if(addPointsToAdd) {
            	addPointsToAdd(added);
            }
            
        }
        return added;
    }

    public double removePoints(double points) {
        if (points <= removable) {
            removable -= points;
            this.points -= points;
            addPointsToAdd(-points);
            return points;
        } else {
            double rem = removable;
            this.points -= removable;
            removable = 0;
            return rem;
        }
    }

}
