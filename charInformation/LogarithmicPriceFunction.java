package charInformation;


public class LogarithmicPriceFunction extends PriceFunction{
	//skill_price*(1+(0.5*(skill_level).'*(1./basic_attr) + 1*log((skill_level).'*(1./basic_attr))));

	@Override
	protected double getIncreasePrice(double unitPrice,
			double basicAttributeLevel, int currentLevel) {
		return unitPrice*Math.max(0,(1+Math.log(currentLevel/basicAttributeLevel)));
	}
}

