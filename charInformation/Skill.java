package charInformation;

import java.util.ArrayList;
import java.lang.Math;

public class Skill extends Attribute {

    /**
     * Skill's dependence of it's children basic attributes. Values must be
     * between [0,1].
     */
    private double attributeDep = 0.5;
    
    /**
     * Percentage of invested points dealt to Attributes.
     */
    //TODO This is not working well at the moment. If child's inheritance is different from the parents inheritance, the points are not calculated correctly.
    private double inheritance = 0.25;

    private ArrayList<Skill> children;

    private ArrayList<Skill> parents;

    private ArrayList<BasicAttribute> ancestors;
    
    private DispGroup group;

    public Skill(String name) {
        super(name);
        this.children = new ArrayList<Skill>();
        this.parents = new ArrayList<Skill>();
        this.ancestors = new ArrayList<BasicAttribute>();
    }

    public Skill(String name, int price) {
        super(name);
        this.children = new ArrayList<Skill>();
        this.parents = new ArrayList<Skill>();
        this.ancestors = new ArrayList<BasicAttribute>();
        this.price = price;
    }

    /**
     * Returns the number of basic attributes the skill depends on.
     * 
     * @return amount of basic attributes
     */
    public int getAncestorNumber() {
        return this.ancestors.size();
    }
    /**
     * Sets the display group for this skill. Also adds this skill to display groups skill if it
     * is not already added.
     */
    public void setDispGroup(DispGroup group) {
    	this.group = group;
    	if(group != null) {
    		if(!group.getSkills().contains(this)) {
    			group.addSkill(this);
    		}
    	}
    }
    
    public DispGroup getDispGroup() {
    	return this.group;
    }

    /**
     * Updates the value of the skill to it's right value. This is done using
     * the amount of points spent on skill.
     */
    public void updateValue() {
        if (ancestors.size() == 0) // Do something clever, otherwise the
        // program is gonna fail you
        {
            System.err
                    .print("EI TOIMI... " + name + "\n");
        }
        int base = 0;
        int roof = 0;
        for (int i = 0; i < ancestors.size(); i++) {
            base += ancestors.get(i).getValue();
        }
        roof = (int) (base / ancestors.size() + 0.5);
        base = (int) (base * attributeDep / ancestors.size() + 0.5);
        double pointsLeft = points;

        while (base < roof
                && (pointsLeft = pointsLeft - Structure.globalVariables.get("skillDep") * price) >= 0) {
            base++;
        }
        if (pointsLeft > 0) {
            value = (int) (Math
                    .log((pointsLeft * (Structure.globalVariables.get("logStr") - 1) + price)
                            / price)
                    / Math.log(Structure.globalVariables.get("logStr")) + base);
        } else
            value = base;
    }

    /**
     * Calculates the price of all the skills in the skill tree. If skill has no
     * children (it's last in the skill tree) it's original value is returned.
     * If skill has children sum of the prices of the children is returned.
     * 
     * @return skill tree's highest member's price
     */
    public double calcPrice() {
        if (children.size() == 0) {
            return price;
        } else {
            price = 0;
            for (int i = 0; i < children.size(); i++) {
                price += children.get(i).calcPrice();
            }
            return price;
        }
    }

    public void addChild(Skill child) {
        children.add(child);
    }

    public void addParent(Skill parent) {
        parents.add(parent);
    }

    public ArrayList<BasicAttribute> callAncestors() {
        if (children.size() != 0) {
            ancestors.clear();
            for (int i = 0; i < children.size(); i++) {
                ancestors.addAll(children.get(i).callAncestors());
            }
            return ancestors;
        } else
            return ancestors;
    }

    public int getNumberOfSiblings() {
        int number = 0;
        for (int i = 0; i < parents.size(); i++) {
            number += parents.get(i).children.size();
        }
        return number;
    }

    public double addPoints(double points, boolean addRemovable, boolean addPointsToAdd) {
        if(points < 0) {
            return -removePoints(points);
        } 
        double added = 0;
        if(addRemovable) {
            addRemovablePoints(this.inheritance * points);
        }
        dealPoints(points, addPointsToAdd);
        added = points;
        
        return added;
    }

    private void addRemovablePointsForParents(double points) {
        removable += points;
        for (int i = 0; i < parents.size(); i++) {
            parents.get(i)
                    .addRemovablePointsForParents(points / parents.size());
        }
    }

    private void addRemovablePoints(double points) {
        if (children.size() == 0) {
            this.addRemovablePointsForParents(points);
        } else {
            for (int i = 0; i < children.size(); i++) {
                children.get(i).addRemovablePoints(
                        children.get(i).price / price * points);
            }
        }

    }

    private void addSharePoints(double points) {
        this.points += points;
        for (int i = 0; i < children.size(); i++) {
            children.get(i).addSharePoints(
                    children.get(i).price / price * points);
        }
    }
    
    private void dealPoints(double points, boolean addPointsToAdd) {
        if (children.size() > 0) {
            for (int i = 0; i< children.size(); i++) {
                children.get(i).dealPoints(children.get(i).price/price*points, addPointsToAdd);
            }
        } else {
            addBasePoints(points, 0, addPointsToAdd);
        }
    }

    private void addBasePoints(double share, double original, boolean addPointsToAdd) { // Pretty
        // inefficient method.
        if(children.size() == 0 && addPointsToAdd) {
            addPointsToAdd(share);
        }
        double nextOrig = original;
        points += original * this.inheritance;
        nextOrig += share;

        if (parents.size() > 0) {
            addSharePoints(share * this.inheritance);
            for (int i = 0; i < parents.size(); i++) {

                parents.get(i).addBasePoints(
                        share * (1 - this.inheritance) / parents.size(),
                        nextOrig, addPointsToAdd);
            }
        } else {
            addSharePoints(share);
        }
    }

    public double removePoints(double points, boolean removePointsToAdd) {
        if (children.size() > 0) {
            double rem = 0;
            for (int i = 0; i < children.size(); i++) {
                rem += children.get(i).removePoints(
                        children.get(i).price / price * points);
            }
            return rem;
        } else if (-points > removable / this.inheritance){
            double rem = removable / this.inheritance;
            addRemovablePoints(-this.inheritance * rem);
            dealPoints(-rem, removePointsToAdd);
            return rem;
        } else {
            addRemovablePoints(this.inheritance * points);
            dealPoints(points, removePointsToAdd);
            return -points;
        }
        
    }
    
   
    
    public double pointsReqToGet(double desired, int startDepth) {
        double points = 0;
        if(children.size() > 0) {
            startDepth -= 1;
            for(int i = 0; i<children.size();i++) {
                points += children.get(i).pointsReqToGet(children.get(i).price/price*desired, startDepth);
            }
        } else {
            points = calcReqPoints(desired, startDepth);
        }
        return points;
    }
    
    private double calcReqPoints(double desired, int startDepth) {
        return desired/calcReqPointsDivisor(0, 0, startDepth);
    }
    
    private double calcReqPointsDivisor(int depth, double price, int startDepth) {
        double firstPrice = price;
        if (startDepth + depth == 0) {
            firstPrice = this.price;
        }
        double divisor = this.inheritance * Math.pow((1-this.inheritance),depth);
        if (startDepth + depth > 0) {
            divisor *= firstPrice/this.price;
        }

        if (parents.size() > 0) {
            for(int i = 0; i<parents.size(); i++) {
                divisor += parents.get(i).calcReqPointsDivisor(depth+1, firstPrice/parents.size(), startDepth); //miks siel on desired, pitäiskö olla jotain ihan muuta
            }
        } else {
            divisor = Math.pow((1-this.inheritance),depth)*firstPrice/this.price;
        }
        return divisor;
    }
    
    public double pointsReqToChange(int amount) {
        return pointsReqForValue(value+amount) - points;
    }
    
    private double pointsReqForValue(int value) {
        double points = 0;
        int base = 0;
        int roof = 0;
        for (int i = 0; i < ancestors.size(); i++) { //Kaatuu, jos ei ancestoreja ole :(
            base += ancestors.get(i).getValue();
        }
        roof = (int) (base / ancestors.size() + 0.5);
        base = (int) (base * attributeDep / ancestors.size() + 0.5);
        if(value < roof) {
            return (value - base) * Structure.globalVariables.get("skillDep")*price;
        }
        else {
            points = (roof - base) * Structure.globalVariables.get("skillDep")*price + (price*Math.pow(Structure.globalVariables.get("logStr"), value - roof) - price) / (Structure.globalVariables.get("logStr")-1);
            return points;
        }
    }
    
    void setPrice(double price) {
        this.price = price;
    }
    
    void setAttributeDep(double value) {
        attributeDep = value;
    }
    void setInheritance(double value) {
    	inheritance = value;
    }
    
    double getAttributeDep() {
        return attributeDep;
    }
    
    public ArrayList<BasicAttribute> getAncestors() {
        return ancestors;
    }
    
    public ArrayList<Skill> getParents() {
        return parents;
    }
    
    public ArrayList<Skill> getChildren() {
        return children;
    }
    
    void addAncestor(BasicAttribute ancestor) {
        this.ancestors.add(ancestor);
    }
    /**
     * NOT USED
     * @return list containing all the elders of the skill
     */
    ArrayList<Skill> getElder() {
        ArrayList<Skill> elders = new ArrayList<Skill>();
        if(parents.size() == 0) {
            elders.add(this);
            return elders;
        }
        for(int i = 0; i<parents.size();i++) {
            elders.addAll(parents.get(i).getElder());
        }
        return elders;
    }
    
    double removePoints(double points) {
        return removePoints(points, true);
    }

	public double getInheritance() {
		return this.inheritance;
	}

}
