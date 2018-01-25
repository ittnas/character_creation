package charInformation;

public abstract class AttributeBranch {
	
	// These are the inherent attributes of the skill
	private String name; //Identifier of the attribute
	private double unitPrice; // The value of the skill. Total price is calculated by summing all values of the contributing attributes
	private PriceFunction priceFunction;
	
	// These describe the state of the attribute
	
	private double pointsSpentOnThis; // Number of points directly spent on this attribute. IT IS NOT THE TOTAL NUMBER OF POINTS.
	private double expPointsSpentOnThis; // Same as pointsSpentOnThis, but just in order to keep count on experience.
	private double removablePoints; // Number of points that can be still removed. Always remove exp first.
	
	// These are just for book keeping
	
	private double totalPrice;
	private int level;
	
	public AttributeBranch(final String name, double unitPrice, PriceFunction priceFunction) {
		this.name = name;
		this.unitPrice = unitPrice;
		this.priceFunction = priceFunction;
	}
	
	public String getName() {
		return name;
	}
	
	public double getUnitPrice() {
		return unitPrice;
	}
	
	public double getTotalPrice() {
		return totalPrice;
	}
	
	public int getLevel() {
		return level;
	}
	
	void addExpPoints(double points, boolean removable) {
		addPoints(points, removable, true);
	}
	
	public String toString() {
		return name + ": unit price = " + unitPrice + ", level = " + level + ", points = " + 
	new Double(pointsSpentOnThis+expPointsSpentOnThis) + " of which experience points " + expPointsSpentOnThis + ". Removable points = " + removablePoints;
		
	}
	
	/**
	 * PLEASE don't call this from outside, but rather distribute points... Calling this is only possible due to Java's stupid visibility keywords.   
	 * 
	 **/
	
	double addPoints(double points, boolean removable, boolean exp) {
		if(-points > removablePoints) { // Removable points can NEVER be smaller than zero.
			return addPoints(-removablePoints, removable, exp);
		} else {
			double nbrPointsAdded = 0.0;
			if(exp) {
				if(points >= 0) { //add
					nbrPointsAdded = points;
					expPointsSpentOnThis += points;
				} else { //remove
					nbrPointsAdded = Math.max(-expPointsSpentOnThis,points); // points < 0 (= removing points). Can't remove more than number of expPoints available. Using max because -10 < -6
					expPointsSpentOnThis += nbrPointsAdded;
				}
			} else {
				if(points >= 0) { //add
					nbrPointsAdded = points;
					pointsSpentOnThis += points;
				} else { //remove
					nbrPointsAdded = Math.max(-pointsSpentOnThis,points);
					pointsSpentOnThis += nbrPointsAdded;
				}
			}
			if(removable) {
				removablePoints += nbrPointsAdded;
			}
			removablePoints = Math.min(removablePoints,pointsSpentOnThis+expPointsSpentOnThis);
			return nbrPointsAdded;
		}
	}
	
	abstract public double distributePoints(double points, boolean removable, boolean exp);
	abstract double updateTotalPrice();
	abstract double updateLevel();
	
}

