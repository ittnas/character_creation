package charInformation;

import java.util.HashMap;

public class StructureBranch {
	
	public StructureBranch(double attributePointLimit, double skillPointLimit, PriceFunction skillPriceFunction, PriceFunction attributePriceFunction) {
		this.attributePointLimit = attributePointLimit;
		this.skillPointLimit = skillPointLimit;
		this.skillPriceFunction = skillPriceFunction;
		this.attributePriceFunction = attributePriceFunction;
		
		basicAttributes = new HashMap<String, BasicAttributeBranch>();
		skills = new HashMap<String, SkillBranch>();
	}
	
	private double attributePointLimit = 100;
	private double skillPointLimit = 5;
	private double expPointLimit = 5;
	
	private double attributePointsSpent = 0;
	private double skillPointsSpent = 0;
	private double expPointsSpent = 0;
	
	private PriceFunction skillPriceFunction = null;
	private PriceFunction attributePriceFunction = null;
	
    private HashMap<String, BasicAttributeBranch> basicAttributes;
    private HashMap<String, SkillBranch> skills;
	
	/**
	 * This method is used to add points to a basic attribute or a skill.
	 *  Points are only removed if there is enough removable points spent on the Skill. 
	 **/
	
	public void addPoints(double nbrPoints, String attributeName,boolean removable) {
		if(basicAttributes.containsKey(attributeName)) {
			// Found from Basic attributes. Add points to that one
			double pointsThatCanBeAdded;
			if(nbrPoints >= 0) { //Adding points
				pointsThatCanBeAdded = Math.max(0,attributePointLimit - (attributePointsSpent + nbrPoints));
			} else { // Removing points
				pointsThatCanBeAdded = nbrPoints;
			}
			double pointsThatWereAdded = basicAttributes.get(attributeName).distributePoints(pointsThatCanBeAdded, removable,false);
			attributePointsSpent += pointsThatWereAdded;
		} else if(skills.containsKey(attributeName)) {
			// Found from Skills. Adding points.
			// First try to add expPoints:
			double expPointsThatCanBeAdded;
			if(nbrPoints >= 0) { //Adding points
				expPointsThatCanBeAdded = Math.min(nbrPoints, Math.max(0, expPointLimit - expPointsSpent));
			} else { // Removing points
				expPointsThatCanBeAdded = nbrPoints;
			}
			double expPointsThatWereAdded = skills.get(attributeName).distributePoints(expPointsThatCanBeAdded, removable,true);
			expPointsSpent += expPointsThatWereAdded;
			double nbrPointsAfterExp = nbrPoints - expPointsThatWereAdded;
			
			double pointsThatCanBeAdded;
			if(nbrPointsAfterExp >= 0) { //Adding points
				pointsThatCanBeAdded = Math.min(nbrPointsAfterExp, Math.max(0,skillPointLimit - skillPointsSpent));
			} else { // Removing points
				pointsThatCanBeAdded = nbrPointsAfterExp;
			}
			double skillPointsThatWereAdded = skills.get(attributeName).distributePoints(pointsThatCanBeAdded,removable,false);
			skillPointsSpent += skillPointsThatWereAdded;
		}
	}
	
	/**
	 * This method is used to add points to a basic attribute or a skill.
	 * Points are only removed if there is enough removable points spent on the Skill.
	 * Points added vai this method can be later removed. 
	 **/
	
	public void addPoints(double nbrPoints, String attributeName) {
		addPoints(nbrPoints,attributeName,true);
	}
	/**
	 * Adds a new skill with given name.
	 * @param name of the new skill
	 * @param unitPrice price of the skill
	 * @return boolean indicating whether the skill was added successfully or not.
	 */
	
	public boolean addSkill(String name, double unitPrice) {
		if(skills.containsKey(name)) {
			return false;
		}
		else {
			skills.put(name, new SkillBranch(name, unitPrice, this.skillPriceFunction));
			return true;
		}

	}
	public SkillBranch getSkill(String name) {
		return skills.get(name);
	}
	public static void main(String args[]) {
		StructureBranch sb = new StructureBranch(5, 5, new LogarithmicPriceFunction(), new LogarithmicPriceFunction());
		sb.addSkill("Hyökkäys", 5);
		System.out.println("Added a new skill:");
		System.out.println(sb.getSkill("Hyökkäys"));
	
		sb.addPoints(15,"Hyökkäys",true);
		System.out.println("Added points:");
		System.out.println(sb.getSkill("Hyökkäys"));
		sb.addPoints(-18, "Hyökkäys",false);
		System.out.println("Removed some points:");
		System.out.println(sb.getSkill("Hyökkäys"));
	}
}


	
	/*
    public double addPoints(double points, String attribute,
            boolean addRemovable, boolean addPointsToAdd) {
        double added = 0;
        double pointsUsed;
        double pointLimit;
        //Pretty clumsy. For checks if the attribute is basic attribute and chooses appropriate points-value 
        if(basicAttributes.containsKey(attribute)) {
        	pointsUsed = this.attrPointsUsed;
        	pointLimit = globalVariables.get("attrPointLimit");
        } else {
        	pointsUsed = this.pointsUsed;
        	pointLimit = globalVariables.get("pointLimit");
        }
        Attribute target;
        if (skills.containsKey(attribute)) {
            target = skills.get(attribute);
        } else if (basicAttributes.containsKey(attribute)) {
            target = basicAttributes.get(attribute);
        } else {
            return 0;
        }
        if (pointsUsed + points > pointLimit
                && (points > 0)) {
            if (pointsUsed > pointLimit) {
                return 0;
            } else {
                added = target.addPoints(pointLimit
                        - pointsUsed, addRemovable, addPointsToAdd);
            }
        } else {
            added = target.addPoints(points, addRemovable, addPointsToAdd);
        }
        //TODO Temporary, updates all values, though only tree's
        // values need to be updated
        updateAllValues();
        pointsUsed += added;
        // Updates the value back
        if(basicAttributes.containsKey(attribute)) {
        	this.attrPointsUsed = pointsUsed;
        } else {
        	this.pointsUsed = pointsUsed;
        }
        return added;
    }

	
/*
    public static HashMap<String, Double> globalVariables = new HashMap<String, Double>();

    //private HashMap<String, BasicAttribute> basicAttributes;

    //private HashMap<String, Skill> skills;

    public static final double WRITERVERSION = 1.02;

    private double pointsUsed = 0;
    
    private double attrPointsUsed = 0; // Separate attribute and skill points.

    private HashMap<String, String> personalInformation;

    private Inventory inventory;
    
    private double experience;
    
    private double version;
    
    private HashMap<String, DispGroup> dispGroups;

    public StructureBranch() {
        personalInformation = new HashMap<String, String>();
        basicAttributes = new HashMap<String, BasicAttribute>();
        dispGroups = new HashMap<String, DispGroup>();
        skills = new HashMap<String, Skill>();
        inventory = new Inventory();
        
        //I don't think that's a very good way to use static variables...
        globalVariables.put("minValue", 3d);  //Minimum value of basic attribute
        globalVariables.put("skillDep", 0.5); //How much increasing skill costs when lower than roof and higher than base
        globalVariables.put("logStr", 1.12); //The logaritmic base used in calculating skill prices
        globalVariables.put("attrLog", 1.08); // The logaritmic base used in calculating basic attribute prices
        globalVariables.put("inheritance", 0.25); //Multiplier of points added directly to the specific skill
        globalVariables.put("pointLimit", 250d); //Starting points of character
        globalVariables.put("attrPointLimit", 100d); // Starting basic attribute points of character
        globalVariables.put("priceMultiplier", 0.8); //Price of basic attributes is multiplied by this
    }

    public void addBasicAttribute(String name) {
        basicAttributes.put(name, new BasicAttribute(name));
    }
    
    public DispGroup addDispGroup(String dispString) {
    	if(!dispGroups.containsKey(dispString)) {
    		DispGroup group = new DispGroup(dispString);
    		dispGroups.put(dispString, group);
    	}
    	return dispGroups.get(dispString);
    }

    public void addSkill(String name, String parent) {
        addSkill(name);
        setChild(name, parent);
    }

    public boolean addSkill(String name, int price, String parent) {
        boolean priceModified = addSkill(name, price);
        setChild(name, parent);
        return priceModified;
    }

    public boolean addSkill(String name, int price) {
        boolean priceModified = false;
        if (!skills.containsKey(name)) {
            skills.put(name, new Skill(name, price));
            priceModified = false;
        } else {
            if (skills.get(name).price != price) {
                skills.get(name).setPrice(price);
                priceModified = true;
            }
        }
        return priceModified;
    }

    @Deprecated
    public void removeBasicAttribute(String name) {
        basicAttributes.remove(name);
    }

    public void addSkill(String name) {
        if (!skills.containsKey(name)) {
            Skill child = new Skill(name);
            skills.put(name, child);
        }

    }

    public void setChild(String child, String parent) {
        skills.get(child).getParents().add(skills.get(parent));
        skills.get(parent).getChildren().add(skills.get(child));
    }

    public void setAttribute(String skill, String basicAttribute) {
        BasicAttribute attribute = basicAttributes.get(basicAttribute);
        basicAttributes.get(basicAttribute).children.add(skills.get(skill));
        skills.get(skill).addAncestor(attribute);
    }

    public void updateAllValues() {
        for (String attr : basicAttributes.keySet()) {
            basicAttributes.get(attr).updateValue();
        }
        for (String skill : skills.keySet()) {
            skills.get(skill).updateValue();
        }
    }

    public void calcPrices() {
        for (String attr : basicAttributes.keySet()) {
            basicAttributes.get(attr).calcPrice();
        }
        for (String skill : skills.keySet()) {
            if (skills.get(skill).getParents().size() == 0) {
                skills.get(skill).calcPrice();
            }
        }
    }

    public void setPrice(String skill, double price) {
        skills.get(skill).setPrice(price);
    }

    public void callAllAncestors() {
        for (String skill : skills.keySet()) {
            if (skills.get(skill).getParents().size() == 0) {
                skills.get(skill).callAncestors();

            }
        }
    }

    public double addPoints(double points, String attribute) {
        return addPoints(points,attribute, true, true);
    }
    
    */
    /**
     * Adds the given amount of points to the ability specified by attribute.
     * @param points how many points should be added
     * @param attribute attribute name (must be unique)
     * @param addRemovable tells if the points can be removed later
     * @param addPointsToAdd - no idea what's that
     * @return how many points was added
     */
/*
    public double addPoints(double points, String attribute,
            boolean addRemovable, boolean addPointsToAdd) {
        double added = 0;
        double pointsUsed;
        double pointLimit;
        //Pretty clumsy. For checks if the attribute is basic attribute and chooses appropriate points-value 
        if(basicAttributes.containsKey(attribute)) {
        	pointsUsed = this.attrPointsUsed;
        	pointLimit = globalVariables.get("attrPointLimit");
        } else {
        	pointsUsed = this.pointsUsed;
        	pointLimit = globalVariables.get("pointLimit");
        }
        Attribute target;
        if (skills.containsKey(attribute)) {
            target = skills.get(attribute);
        } else if (basicAttributes.containsKey(attribute)) {
            target = basicAttributes.get(attribute);
        } else {
            return 0;
        }
        if (pointsUsed + points > pointLimit
                && (points > 0)) {
            if (pointsUsed > pointLimit) {
                return 0;
            } else {
                added = target.addPoints(pointLimit
                        - pointsUsed, addRemovable, addPointsToAdd);
            }
        } else {
            added = target.addPoints(points, addRemovable, addPointsToAdd);
        }
        //TODO Temporary, updates all values, though only tree's
        // values need to be updated
        updateAllValues();
        pointsUsed += added;
        // Updates the value back
        if(basicAttributes.containsKey(attribute)) {
        	this.attrPointsUsed = pointsUsed;
        } else {
        	this.pointsUsed = pointsUsed;
        }
        return added;
    }

    public double removePoints(double points, String attribute) {
        double removed = 0;
        if (skills.containsKey(attribute)) {
            removed = skills.get(attribute).removePoints(points);

            return removed;
        } else if (basicAttributes.containsKey(attribute)) {
            removed = basicAttributes.get(attribute).removePoints(points);
        } else {
            return 0;
        }
        updateAllValues();
        return removed;
    }

    @Deprecated
    public void printTree(String elder, int n) {

        for (int i = 0; i < n; i++) {
            System.out.print(" ");
        }
        System.out.printf("%s %d (Points %.2f, Removable %.2f, Price %.0f)\n",
                elder, skills.get(elder).value, skills.get(elder).points,
                skills.get(elder).removable, skills.get(elder).getPrice());

        for (int i = 0; i < skills.get(elder).getChildren().size(); i++) {
            printTree(skills.get(elder).getChildren().get(i).name, n + 4);
        }
    }

    /**
     * Prints all the skills, their attributeDep, inheritance, and points to add. This can be
     * used as input for StructWriter.
     * 
     * @return Skills in a form of tree
     */
/*
    public String printSkills() {
        HashMap<String, Skill> elders = new HashMap<String, Skill>();
        for (String skill : skills.keySet()) {
            if (skills.get(skill).getParents().size() == 0) {
                elders.put(skills.get(skill).getName(), elders.get(skill));
            }
        }
        String output = "";
        for (String skill : elders.keySet()) {
            output += printSkillTree(skill, 0);
        }
        return output;
    }

    /**
     * Helper for {@link #printSkills()}. Constructs the structure of the
     * string.
     * 
     * @param elder
     *            Skill from which the tree is build
     * @param n
     *            depth of recursion
     * @return skill tree of a one elder
     */
/*
    private String printSkillTree(String elder, int n) {
        StringBuilder output = new StringBuilder("");
        for (int i = 0; i < n + 1; i++) {
            output.append('\t');
        }
        output.append(skills.get(elder).getName() + ": "
                + skills.get(elder).getPrice() + ", "
                + skills.get(elder).getAttributeDep() + ", " + skills.get(elder).getInheritance() + ", ");
        if(skills.get(elder).getDispGroup() != null) {
        	output.append(skills.get(elder).getDispGroup().getName());
        }        
        output.append(" (");
        for (int i = 0; i < skills.get(elder).getAncestors().size(); i++) {
            if (i != 0) {
                output.append(", ");
            }
            output.append(skills.get(elder).getAncestors().get(i).getName());
        }
        output.append(") ; " + skills.get(elder).getPointsToAdd());
        if (skills.get(elder).getChildren().size() > 0) {
            output.append(" {");
        }
        output.append("\n");

        for (int i = 0; i < skills.get(elder).getChildren().size(); i++) {
            output.append(printSkillTree(
                    skills.get(elder).getChildren().get(i).name, n + 1));
        }
        if (skills.get(elder).getChildren().size() != 0) {
            for (int i = 0; i < n + 1; i++) {
                output.append("\t");
            }
            output.append("}\n");
        }
        String out = output.toString();
        return out;
    }

    @Deprecated
    public void printAttributes() {
        System.out.print("\nBasic Attributes:");
        for (String attr : basicAttributes.keySet()) {
            System.out.printf("\n%s %d (Points %.2f, Price %.0f)", attr,
                    basicAttributes.get(attr).value,
                    basicAttributes.get(attr).points, basicAttributes.get(attr)
                            .getPrice());
        }
    }

    /**
     * Returns amount of points required to increase given attribute by given
     * value. for negative amounts returns points required to decrease skill by
     * amount +1.
     * 
     * @param attribute
     *            target attribute
     * @param amount
     *            amount of change
     * @return points required for desired increase or decrease
     */
/*
    public double PointsReqToIncrease(String attribute, int amount) {
        if (skills.containsKey(attribute)) {
            return skills.get(attribute).pointsReqToGet(
                    skills.get(attribute).pointsReqToChange(amount), 0) + 0.00001;
        } else if (basicAttributes.containsKey(attribute)) {
            return basicAttributes.get(attribute).pointsReqToChange(amount)
                    + +0.00001;
        } else {
            System.err
                    .printf(
                            "You tried to find out how much points it requires to increse %s by %d.\nHowever, such attribute does not exist",
                            attribute, amount);
            return 0;
        }
    }

    public void setAttributeDep(String skill, double value) {
        skills.get(skill).setAttributeDep(value);
    }

    @Deprecated
    public void testReq(String skill, double des) {
        System.out.printf("%s\n", skills.get(skill).pointsReqToGet(des, 0));
    }

    @Deprecated
    public void testAdd(String skill) {
        System.out.printf("%f\n", skills.get(skill).pointsReqToChange(1));
    }

    /**
     * Sets the amount of points which have to be added to the attribute when
     * editing character.
     * 
     * @param attribute
     *            target attribute
     * @param points
     *            to added
     * @return true if given attribute exists, else returns false
     */
/*
    public boolean addPointsToAdd(String attribute, double points) {
        if (skills.containsKey(attribute)) {
            skills.get(attribute).addPointsToAdd(points);
            return true;
        } else if (basicAttributes.containsKey(attribute)) {
            basicAttributes.get(attribute).addPointsToAdd(points);
            return true;
        } else {
            return false;
        }
    }

    /**
     * Returns HashMap containing basic attiributes
     * 
     * @return basic attributes
     */
/*
    public HashMap<String, BasicAttribute> getBasicAttributes() {
        return this.basicAttributes;
    }

    /**
     * Gets skills.
     * 
     * @return skills
     */
/*
    public HashMap<String, Skill> getSkills() {
        return this.skills;
    }

    public ArrayList<Skill> getElders() {
        ArrayList<Skill> elders = new ArrayList<Skill>();
        for (String attr : skills.keySet()) {
            if (skills.get(attr).getParents().size() == 0) {
                elders.add(skills.get(attr));
            }
        }
        return elders;
    }

    public void init() {
        calcPrices();
        callAllAncestors();
        setPointsToAdd();
        updateAllValues();
    }

    public void setPointsToAdd() {
        for (String attr : basicAttributes.keySet()) {
            addPoints(basicAttributes.get(attr).getPointsToAdd(), attr,false,false);
        }
        for (String attr : skills.keySet()) {
            addPoints(skills.get(attr).getPointsToAdd(), attr,false,false);
        }
    }

    public void addPersonalInformation(String key, String value) {
        personalInformation.put(key, value);
    }

    public HashMap<String, String> getPersonalInformation() {
        return personalInformation;
    }

    public double getPointsLeft() {
        return globalVariables.get("pointLimit") - pointsUsed;
    }

    public Inventory getInventory() {
        return inventory;
    }
    
    public double getExperience() {
        return experience;
    }
    
    public void addExperience(double exp) {
        experience+=exp;
    }
    
    public void setVersion(double version) {
        this.version = version;
    }
    
    public double getVersion() {
        return version;
    }
    
    public String printPaperSheet() {
        
        return "";
    }
    
    public String printPersonalInformationPaper() {
        StringBuilder str = new StringBuilder();
        str.append("Hahmon tiedot\n");
        for(String pers: personalInformation.keySet()) {
            String[] info = personalInformation.get(pers).split("\\n");
            info[0] = pers + ": " + info[0];
            for(int i = 0; i<info.length; i++) {
                if(info[i].length() > 45) {
                    if(info.length-1 == i) {
                        String end = info[i].substring(45);
                        String begin = info[i].substring(0,45);
                        str.append(begin + "\n" + end + "\n");
                    } else {
                        String end = info[i].substring(45);
                        String begin = info[i].substring(0,45);
                        str.append(begin + "\n");
                        info[i+1] = end + info[i+1];
                    }
                } else {
                    str.append(info[i] + "\n");
                }
            }
        }
        return str.toString();
    }
    
    public String printPersonalInformationRTF() {
        int lettersInRow = 30;
        StringBuilder str = new StringBuilder();
        str.append("{");
        str.append("\\fs40 \\b Hahmon tiedot \\b0 \\par\\fs30 ");
        for(String pers: personalInformation.keySet()) {
            String[] info = personalInformation.get(pers).split("\\n");
            info[0] = "\\b " + pers + " " + "\\b0" + info[0];
            for(int i = 0; i<info.length; i++) {
                int partLenght = 0;
                if (i == 0) {
                    partLenght = info[0].length()-5;
                } else {
                    partLenght = info[i].length();
                }
                if(partLenght > lettersInRow) {
                    if(info.length-1 == i) {
                        String end = info[i].substring(lettersInRow);
                        String begin = info[i].substring(0,lettersInRow);
                        str.append(begin + "\\par " + end + "\\par ");
                    } else {
                        String end = info[i].substring(lettersInRow);
                        String begin = info[i].substring(0,lettersInRow);
                        str.append(begin + "\\par ");
                        info[i+1] = end + info[i+1];
                    }
                } else {
                    str.append(info[i] + "\\par ");
                }
            }
        }
        str.append("}");
        return str.toString();
    }
    
    public String printBasicAttributesRTF() {
        StringBuilder str = new StringBuilder();
        str.append("{");
        str.append("\\fs40\\b Perusominaisuudet \\b0 \\par\n");
        str.append("\\fs30");
        for(String attr: basicAttributes.keySet()) {
            str.append(attr + " " + "\\b " + basicAttributes.get(attr).getValue() + "\\b0 \\par ");
        }
        str.append("}");
        return str.toString();
    }
    
    public String printSkillGroupRTF(Skill elder, int n) {
        StringBuilder output = new StringBuilder("");
        output.append("\\fs30 ");
        for (int i = 0; i < n; i++) {
            output.append("   ");
        }
        String name = elder.getName();
        /*for(int i = name.length() + 3*n; i< 23; i++) {
            name += " ";
        }*/
    /*
        name += " ";
        output.append(name);
        output.append("\\b " + elder.getValue() + "\\b0 ");
        output.append("\\par\n");

        for (int i = 0; i < elder.getChildren().size(); i++) {
            output.append(printSkillGroupRTF(
                    elder.getChildren().get(i), n + 1));
        }
        
        
        return output.toString();
    }
    
    private String printInventoryRTF() {
        StringBuilder output = new StringBuilder("\\fs40 Varusteet\\par\\fs30\n");
        for(String item: inventory.getItemMap().keySet()) {    
            Item itm = inventory.getItem(item);
            output.append(itm.getName() + " " + itm.getPrice() + " hr  " + inventory.getAmount(item) + " kpl\\par\n");
        } 
        return output.toString();
    }
    
    public String printRTF() {
        StringBuilder str = new StringBuilder();
        //Beginning of the document
        str.append("{\\rtf1\\ansi{\\fonttbl\\f0\\fswiss Helvetica;}\n");
        str.append("\\fs50 Hahmolomake \\fs20 \\par\\par\n");
        str.append("\\intbl \\cellx5000 \\cellx10000");
        //Here goes the code
        str.append(printPersonalInformationRTF() + "\\cell " + printBasicAttributesRTF() + "\\cell\n");
        str.append("{\\trowd\\intbl\\cellx5000\\cellx10000\\row}\n");
        ArrayList<Skill> elders = getElders();
        for(int i = 0; i < elders.size(); i++) {
            if(i % 2 == 0) {
                 //Ennen ja j�lkeen, nyt ei ole n�in :(
                str.append("\\trowd\\intbl\\cellx5000\\cellx10000\n");
                str.append("{");
            }
            str.append(printSkillGroupRTF(elders.get(i), 0) + "\\cell ");
            if(i % 2 != 0 || i == elders.size()-1) {
                str.append("}\n");
                str.append("{\\trowd\\intbl\\cellx5000\\cellx10000\\row}\n");

            }
        }
        str.append("\\trowd\\intbl\\cellx5000\\cellx10000\n");
        str.append("{");
        str.append(printInventoryRTF() + "\\cell\\cell");
        str.append("}\n");
        str.append("{\\trowd\\intbl\\cellx5000\\cellx10000\\row}\n");
        //End of the document
        str.append("}");
        return str.toString();
    }

	public double getAttrPointsLeft() {
		return globalVariables.get("attrPointLimit") - attrPointsUsed;
	}

	public void setDispGroup(String name, DispGroup group) {
		if(skills.containsKey(name)) {
			skills.get(name).setDispGroup(group);
		}
		
	}

	public String printLatex(Color weakColor, Color averageColor, Color strongColor) {
		StringBuilder builder = new StringBuilder();
		builder.append("\\documentclass[a4paper,14pt]{article}\r\n");
		builder.append("\\usepackage[T1]{fontenc}\r\n");
		builder.append("\\usepackage[finnish]{babel}\r\n");
		builder.append("\\usepackage[utf8]{inputenc}\r\n");
		builder.append("\\usepackage[dvips]{graphicx}\r\n");
		builder.append("\\usepackage[cm]{fullpage}\r\n");
		builder.append("\\usepackage{multicol}\r\n");
		builder.append("\\usepackage{color}");
		builder.append("\\usepackage[usenames,dvipsnames,svgnames,table]{xcolor}");
		//builder.append("\\usetikzlibrary{calc}\r\n");
		//builder.append("\\usetikzlibrary{decorations.pathmorphing}\r\n");

		builder.append("\\begin{document}\r\n");
		
		builder.append("\\definecolor{weakColor}{RGB}{" + weakColor.getRed() + "," + weakColor.getGreen() + "," + weakColor.getBlue() + "}");
		builder.append("\\definecolor{averageColor}{RGB}{" + averageColor.getRed() + "," + averageColor.getGreen() + "," + averageColor.getBlue() + "}");
		builder.append("\\definecolor{strongColor}{RGB}{" + strongColor.getRed() + "," + strongColor.getGreen() + "," + strongColor.getBlue() + "}");
		
		//builder.append("\\begin{tikzpicture}[overlay,remember,picture]\r\n");
		//builder.append("\\draw[line width = 1mm,decorate,decoration={snake}]($ (current page.north west) + (1cm,-1cm) $)" +
		//"rectangle ($ (current page.south east) + (-1cm,1cm) $);\r\n");
		//builder.append("\\draw [line width=1mm,decorate,decoration={zigzag}]" + 
        //"($ (current page.north west) + (2cm,-2cm) $)" +
        //"rectangle ($ (current page.south east) + (-2cm,2cm) $);\r\n");
		//builder.append("\\end{tikzpicture}\r\n");
		
		
		//Write personal information
		
		
		
		builder.append("\\section*{Hahmon tiedot}\r\n");
		
		builder.append("\\begin{description}\r\n");
		for(String key : personalInformation.keySet()) {
			builder.append("\\item[" + key + "] " + personalInformation.get(key) + "\r\n");
		}
		builder.append("\\end{description}\r\n");
		builder.append("\\pagebreak");
		builder.append("\\section*{Ominaisuudet}\r\n");
		builder.append("\\begin{multicols}{2}\r\n");
		
		builder.append("\\begin{description}\r\n");
		for(BasicAttribute attribute : basicAttributes.values()) {
			String fontColor;
			if(attribute.getValue() <=4 ) {
				fontColor = "weakColor";
			} else if(attribute.getValue() <=6){
				fontColor = "averageColor";
			} else {
				fontColor = "strongColor";
			}
			builder.append("\\item[" + "\\color{" + fontColor + "}" + attribute.value + "]" + "\\color{" + fontColor + "}" + attribute.name + " " + String.format("%.0f",attribute.points) + "\r\n");
		}
		builder.append("\\end{description}\r\n");
		builder.append("\\end{multicols}\r\n");
		int column = 0;
		int nbrColumns = 3;
		/*
		for(DispGroup dispGroup : dispGroups.values()) {
			if(column % nbrColumns == 0) {
				builder.append("\\begin{multicols}{" + nbrColumns + "}\r\n");
				//builder.append("\\sloppy\r\n");
			}
			builder.append("\\section*{" + dispGroup.getName() + "}\r\n");
			
			builder.append("\\begin{description}\r\n");
			for(Skill skill : dispGroup.getSkills()) {
				builder.append("\\item[" + skill.value + "]" + skill.name + " " + String.format("%.0f", skill.points) + "\r\n");
			}
			builder.append("\\end{description}\r\n");
			builder.append("\\columnbreak\r\n");
			if(column % nbrColumns == nbrColumns - 1 || column == dispGroups.values().size() - 1) {
				builder.append("\\end{multicols}\r\n");	
			}
			column++;
		}
		*/
    /*
		ArrayList<Skill> elders = getElders();
		//builder.append("\\newcommand*\\nobreaklist{\\@beginparpenalty=\\@M}\r\n");
		for(Skill elder : elders) {
			//if(column % nbrColumns == 0) {
			if(column == 0) {
				builder.append("\\begin{multicols}{" + nbrColumns + "}\r\n");
				//builder.append("\\setcounter{collectmore}{-1}");
				builder.append("\\raggedcolumns\r\n");
			}
			builder.append("\\section*{" + elder.getName() + "}\r\n");
			builder.append("\\begin{description}\r\n");
			//builder.append("\\begin{enumerate}\r\n");
			builder.append(buildNestedListLatex(elder,0));
			builder.append("\\end{description}\r\n");
			//builder.append("\\pagebreak\r\n");
			if(column == elders.size() -1) {
			//if(column % nbrColumns == nbrColumns - 1 || column == elders.size() - 1) {
				builder.append("\\end{multicols}\r\n");	
			}
			column++;
		}

		builder.append("\\end{document}\r\n");
		return builder.toString();
	}
	
	public String buildNestedListLatex(Skill elder, int depth) {

		String fontColor;
		if(elder.getValue() <=4 ) {
			fontColor = "weakColor";
		} else if(elder.getValue() <=6){
			fontColor = "averageColor";
		} else {
			fontColor = "strongColor";
		}
		StringBuilder builder = new StringBuilder();
		if(depth > 0) {
			builder.append("\\item[" + "\\color{" + fontColor + "}" + elder.value + "]" + "\\color{" + fontColor + "}"+ elder.name + " " + String.format("%.0f", elder.points) + "\r\n");
		if(elder.getChildren().size() != 0) {
		builder.append("\\begin{description}\r\n");
		}
		}
		for(Skill skill : elder.getChildren()) {
			builder.append(buildNestedListLatex(skill,depth+1));
		}
		if(elder.getChildren().size() != 0 && depth > 0) {
		builder.append("\\end{description}\r\n");
		}
		return builder.toString();
	}

	public void setInheritance(String skill, double value) {
		skills.get(skill).setInheritance(value);
		
	}
}
    */