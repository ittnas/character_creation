package charInformation;

import java.util.ArrayList;

public class SkillBranch extends AttributeBranch {
	
	ArrayList<AttributeBranch> attributes;
	ArrayList<SkillBranch> contributingSkills;

	public SkillBranch(String name, double unitPrice,
			PriceFunction priceFunction) {
		super(name, unitPrice, priceFunction);
		attributes = new ArrayList<AttributeBranch>();
		contributingSkills = new ArrayList<SkillBranch>();
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
	
	public void addAttribute(AttributeBranch attribute) {
		attributes.add(attribute);
	}
	
	public void addParentSkill(SkillBranch skill) {
		contributingSkills.add(skill);
	}

	@Override
	public double distributePoints(double points, boolean removable, boolean exp) {
		double addedPointsTotal = 0;
		double percentageToThisSkill = getUnitPrice()/getTotalPrice();
		double addedPoints = addPoints(points*percentageToThisSkill, removable, exp);
		double pointsDistributed = addedPoints/percentageToThisSkill;
		for (SkillBranch skill : contributingSkills) {
			
		}
		return addPoints(points, removable, exp);
	}

}
