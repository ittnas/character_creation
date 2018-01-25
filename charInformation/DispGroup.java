package charInformation;

import java.util.ArrayList;

public class DispGroup {
	private String name;
	private ArrayList<Skill> skills;
	
	public DispGroup(String name) {
		this.name = name;
		skills = new ArrayList<Skill>();
	}
	
	public void addSkill(Skill skill) {
		if(skill != null) {
			this.skills.add(skill);
			skill.setDispGroup(this);
		}
	}
	
	public void removeSkill(Skill skill) {
		this.skills.remove(skill);
	}
	public String getName() {
		return name;
	}
	
	public String toString() {
		return name;
	}
	
	public ArrayList<Skill> getSkills() {
		return skills;
	}
}
