package charInformation;

public abstract class Attribute {
    /*
     * Price of the attribute;
     */
    protected double price = 0;

    /*
     * Value of the attribute.
     */
    protected int value;

    /*
     * Character creation points spent on the attribute.
     */
    protected double points;

    /*
     * Experience spent on the attribute. Exp is included into points.
     */
    protected double exp;

    /*
     * Name of the attribute
     */
    protected String name;
    
    /*
     * Points that can be removed
     */
    protected double removable;
    
    protected double pointsToAdd;

    public Attribute(final String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public int getValue() {
        return value;
    }
    
    public double getPrice() {
        return this.price;
    }
    
    public void addPointsToAdd(double points) {
        pointsToAdd += points;
    }
    
    public void setPointsToAdd(double points) {
        pointsToAdd = points;
    }
    
    public double getPointsToAdd() {
        return pointsToAdd;
    }
    
    public double getPoints() {
        return points;
    }
    
    public String toString() {
    	return this.name;
    }

    /**
     * Adds given amount of points to the attribute. Also updates the value.
     */
    /*
    public void addPoints(double points)
    {
        this.points += points;
        updateValue();
    }
    */
    abstract void updateValue();
    abstract double calcPrice();
    public abstract double pointsReqToChange(int amount);
    abstract double removePoints(double points);
    abstract double addPoints(double points, boolean addRemovable, boolean addPointsToAdd);

}
