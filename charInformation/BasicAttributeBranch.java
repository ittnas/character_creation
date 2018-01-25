package charInformation;

import java.util.ArrayList;

public class BasicAttributeBranch extends AttributeBranch {
	
	private ArrayList<SkillBranch> childSkills;

	public BasicAttributeBranch(String name, double unitPrice,
			PriceFunction priceFunction) {
		super(name, unitPrice, priceFunction);
		
	}

	@Override
	double updateTotalPrice() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	double updateLevel() {
		// TODO Auto-generated method stub
		return 0;
	}
	
	public void addChild(SkillBranch child) {
		childSkills.add(child);
	}

	@Override
	public double distributePoints(double points, boolean removable, boolean exp) {
		return addPoints(points, removable, exp);
	}

}
