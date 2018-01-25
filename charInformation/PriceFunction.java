package charInformation;

public abstract class PriceFunction {
	
	public double getPriceToLevel(double unitPrice, double basicAttributeLevel,
			int targetLevel) {
		double price = 0;
		for(int i = 0; i < targetLevel; i++) {
			price = price + getIncreasePrice(unitPrice, basicAttributeLevel, i);
		}
		return price;
	}
	abstract protected double getIncreasePrice(double unitPrice, double basicAttributeLevel, int currentLevel);
	
	public int getLevel(double unitPrice, double points, double basicAttributeLevel) {
		int level = 0;
		if(getPriceToLevel(unitPrice, basicAttributeLevel, level = (int)basicAttributeLevel) > points) {
			while(getPriceToLevel(unitPrice, basicAttributeLevel, --level) > points);
		} else {
			while(getPriceToLevel(unitPrice, basicAttributeLevel, ++level) <= points);
			level--;
		}
		return level;
	}
	
	/*
	public double fxpIteration(double unitPrice, double points, double basicAttributeLevel) {
		double x0 = basicAttributeLevel;
		double epsilon = 0.0001;
		double xn = Double.MAX_VALUE; 
		while(Math.abs(xn - x0) > epsilon) {
			x0 = xn;
			xn = getPriceToLevel(unitPrice, basicAttributeLevel, x0) - points;
			System.out.println(xn);
		}
		return xn;
	}
	*/
	
	public static void main(String[] args) {
		LogarithmicPriceFunction pf = new LogarithmicPriceFunction();
		//System.out.println(pf.fxpIteration(5, 100, 4));
		System.out.println(pf.getPriceToLevel(5, 6, 6));
		System.out.println(pf.getIncreasePrice(5, 7, 7));
		int level = pf.getLevel(5,8.5,6);
		System.out.println(level);
		System.out.println(pf.getPriceToLevel(5, 6, level));
	}
}


