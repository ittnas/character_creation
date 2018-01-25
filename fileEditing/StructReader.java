package fileEditing;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import charInformation.DispGroup;
import charInformation.Structure;

public class StructReader {

    private String fileName;

    private Structure struct;

    private int lineNumber = 0;

    private ArrayList<String> errorReport;

    private ArrayList<String> warningReport;

    public StructReader(String fileName) {
        this.fileName = fileName;
        this.struct = new Structure();
        errorReport = new ArrayList<String>();
        warningReport = new ArrayList<String>();
    }

    public Structure readStructure() {
        try {
            FileInputStream fstream = new FileInputStream(fileName);
            DataInputStream in = new DataInputStream(fstream);
            // Reads only UTF-8 files.
            BufferedReader br = new BufferedReader(new InputStreamReader(in,"UTF-8"));

            String line = null;
            boolean versionOK = false;
            try {
                while ((line = br.readLine()) != null) {
                    lineNumber++;
                    int index = line.indexOf("#Writer version");
                    if (index != -1) {
                        line = line.substring(index + 15);
                        line = line.trim();
                        String[] words = line.split(" ");
                        double version = Double.parseDouble(words[0]);
                        if (version == Structure.WRITERVERSION) {
                            versionOK = true;
                            break;
                        } else {
                            String error = lineNumber
                                    + ": Wrong writer version! Version of the file is "
                                    + version
                                    + " through the reader version is "
                                    + Structure.WRITERVERSION + ".";
                            setError(error);
                            versionOK = true;
                            break;
                        }
                    }
                }
                if (!versionOK) {
                    setError("Bad file, there is no version number in it!");
                }
            } catch (NumberFormatException e) {
                String error = lineNumber
                        + ": Writer version number could not be read.";
                setError(error);
            }
            while ((line = br.readLine()) != null) {
                lineNumber++;
                if (line.contains("#Global variables")) {
                    setGlobalvariables(br, line);
                } else if (line.contains("#Basic attributes")) {
                    setBasicAttributes(br, line);
                } else if (line.contains("#Skills")) {
                    setSkills(br, line);
                } else if (line.contains("#Personal information")) {
                    setPersonalInformation(br, line);
                } else if(line.contains("#Inventory")) {
                    setInventory(br, line);
                } else if(line.contains("#Version")) {
                    setVersion(br, line);
                }
            }
            in.close();
        } catch (IOException e) {
            setError("Cannot open the file or the file does not exist!");
            return null;
        }
        struct.calcPrices();
        return struct;
    }

    private void setGlobalvariables(BufferedReader br, String line)
            throws IOException {
        line = cutTheCurve(br, line);

        boolean end = false;
        do {
            int index = line.indexOf('}');
            if (index != -1) {
                line = line.substring(0, index);
                end = true;
            }
            line = line.trim();
            String[] words = line.split(" ");
            if (Structure.globalVariables.containsKey(words[0])) {
                try {
                    try {
                        double value = Double.parseDouble(words[2]);
                        Structure.globalVariables.put(words[0], value);
                    } catch (IndexOutOfBoundsException er) {
                        String warning = lineNumber
                                + ": Setting the value of global variable "
                                + words[0] + " failed due to incorrect syntax.";
                        setWarning(warning);
                    }

                } catch (NumberFormatException e) {
                    String warning = lineNumber
                            + ": The value of global variable " + words[0]
                            + " could not be read. The value was " + words[2]
                            + ".";
                    setWarning(warning);
                }
            } else if (!words[0].trim().equals("")) {
                String warning = lineNumber + ": Global variable " + words[0]
                        + " was not recognised.";
                setWarning(warning);
            }
            lineNumber++;
        } while (((line = br.readLine()) != null) && !end);
    }

    private void setBasicAttributes(BufferedReader br, String line)
            throws IOException {
        line = cutTheCurve(br, line);
        boolean end = false;
        do {
            String parts[] = line.split(";");
            line = parts[0];
            int index = line.indexOf('}');
            if (index != -1) {
                line = line.substring(0, index);
                end = true;
            }
            line = line.trim();
            if (!line.equals("")) {
                struct.addBasicAttribute(line);
            }
            if (parts.length > 1) {
                try {
                    double pointsToAdd = Double.parseDouble(parts[1]);
                    struct.getBasicAttributes().get(line).addPointsToAdd(
                            pointsToAdd);
                } catch (NumberFormatException e) {
                    String warning = lineNumber
                            + ": Points to add of the attribute " + line
                            + " could not be read. Points were " + parts[1]
                            + ".";
                    setWarning(warning);
                }
            }
            lineNumber++;
        } while (((line = br.readLine()) != null) && !end);
    }

    private void setSkills(BufferedReader br, String line) throws IOException {
        line = cutTheCurve(br, line);
        readSkills(null, br, line);
    }

    private void readSkills(String parent, BufferedReader br, String line)
            throws IOException {
        boolean end = false;
        do {

            int index = line.indexOf('}');
            if (index != -1) {
                line = line.substring(0, index);
                end = true;
            }
            line = line.trim();
            if (!line.equals("")) {
                int waweIndex = line.indexOf('{');
                if (waweIndex != -1) {
                    String parentSkill = line.substring(0, waweIndex);
                    String childSkill = line.substring(waweIndex + 1);
                    readSkills(parseSkill(parentSkill, parent), br, childSkill);
                } else {
                    parseSkill(line, parent);
                }
            }
            if (end) {
                return;
            }
            lineNumber++;
        } while (((line = br.readLine()) != null) && !end);
    }

    private String parseSkill(String line, String parent) {
        line = line.trim();
        String values[] = line.split(";");
        String parts[] = values[0].split(":");
        String name = parts[0].trim();
        if (parent != null) {
            struct.addSkill(name, parent);
        } else {
            struct.addSkill(name);
        }
        if (parts.length > 1) {
            int index = 0;
            String stats;
            if ((index = parts[1].indexOf('(')) != -1) {

                stats = parts[1].substring(0, index);
                int rightBracket = parts[1].indexOf(')');
                String attributes[] = parts[1].substring(index + 1,
                        rightBracket).split(",");

                for (int i = 0; i < attributes.length; i++) {
                    try {
                        struct.setAttribute(name, attributes[i].trim());
                    } catch (NullPointerException e) {
                        String warning = lineNumber
                                + ": You tried to set a basic attribute "
                                + attributes[i].trim()
                                + " that does not exist. The skill was " + name
                                + ".";
                        setWarning(warning);
                    }
                }

            } else {
                stats = parts[1];
            }
            stats = stats.trim();
            String statsArray[] = stats.split(",");
            if (statsArray.length > 0) {
                statsArray[0] = statsArray[0].trim();
                try {
                    if (!statsArray[0].equals("")) {
                        double price = Double.parseDouble(statsArray[0]);
                        struct.setPrice(name, price);
                    }
                } catch (NumberFormatException e) {
                    String warning = lineNumber + ": Price of the skill "
                            + name + " could not be read. Price was "
                            + statsArray[0] + ".";
                    setWarning(warning);
                }
            }
            if (statsArray.length > 1) {
                statsArray[1] = statsArray[1].trim();
                try {
                    double dep = Double.parseDouble(statsArray[1]);
                    struct.setAttributeDep(name, dep);
                } catch (NumberFormatException e) {
                    String warning = lineNumber
                            + ": Attribute dependancy of the skill " + name
                            + " could not be read. Attribute dependancy was "
                            + statsArray[1] + ".";
                    setWarning(warning);
                }
            }
            if (statsArray.length > 2) {
            	statsArray[2] = statsArray[2].trim();
            	if(!statsArray.equals("")) {
            		try {
            		double inheritance = Double.parseDouble(statsArray[2]);
            		struct.setInheritance(name,inheritance);
            		} catch (NumberFormatException e) {
            			String warning = lineNumber + ": Inheritance of the skill " + name + " could not be read. Inherintace was " + statsArray[2] + ".";
            			setWarning(warning);
            		}
            	}
            }
            if (statsArray.length > 3) {
            	statsArray[3] = statsArray[3].trim();
            	if(!statsArray.equals("")) {
            		DispGroup group = struct.addDispGroup(statsArray[3]);
            		struct.setDispGroup(name,group);
            	}
            }

        }
        if (values.length > 1) {
            values[1] = values[1].trim();
            try {
                double points = Double.parseDouble(values[1]);
                if (!struct.addPointsToAdd(name, points)) {
                    String warning = lineNumber
                            + ": Skill named "
                            + name
                            + " does not exist. Points could not be added to that skill.";
                    setWarning(warning);
                }
            } catch (NumberFormatException e) {
                String warning = lineNumber + ": Added points of the skill "
                        + name + " could not be read. Points were " + values[1]
                        + ".";
                setWarning(warning);
            }

        }
        return name;
    }

    private void setPersonalInformation(BufferedReader br, String line)
            throws IOException {
        line = cutTheCurve(br, line);
        boolean end = false;
        do {
            int index = line.indexOf('{');
            if (index != -1) {
                String parts[] = line.split("\\{");
                String name = parts[0].trim();
                StringBuilder text = new StringBuilder();
                if (parts.length > 1) {
                    line = parts[1].trim();
                } else line = "";
                boolean endInner = false;
                do {
                    int endIndex = line.indexOf('}');
                    if(endIndex != -1) {
                        line = line.substring(0, endIndex);
                        endInner = true;
                    }
                    text.append(line+ "\n");
                } while (!endInner && (line = br.readLine()) != null && lineNumber == lineNumber++);
                struct.addPersonalInformation(name, text.toString().trim());
                line = "";
            }
            int indexLast = line.indexOf('}');
            if (indexLast != -1) {
                line = line.substring(0,indexLast);
                end = true;
            }
            if (!line.trim().equals("")) {
                    struct.addPersonalInformation(line.trim(), "");
            }
            lineNumber++;
        } while (((line = br.readLine()) != null) && !end);
    }

    private void setError(String errorText) {
        errorReport.add(errorText);
    }

    private void setWarning(String warningText) {
        warningReport.add(warningText);
    }

    public String printErrors() {
        if (errorReport.size() == 0 && warningReport.size() == 0) {
            return "Reading the structure finished without warnings or errors.\n";
        }
        String report = "Reading the structure finished with "
                + errorReport.size() + " errors and " + warningReport.size()
                + " warnings:";
        for (int i = 0; i < errorReport.size(); i++) {
            report += "\n" + errorReport.get(i);
        }
        for (int i = 0; i < warningReport.size(); i++) {
            report += "\n" + warningReport.get(i);
        }
        return report;
    }

    private String cutTheCurve(BufferedReader br, String line)
            throws IOException {
        lineNumber--;
        do {
            lineNumber++;
            int index = line.indexOf('{');
            if (index != -1) {
                if (line.length() > index) {
                    line = line.substring(index + 1);
                    break;
                } else {
                    line = br.readLine();
                    lineNumber++;
                    break;
                }
            }
        } while ((line = br.readLine()) != null);
        return line;
    }
    
    private void setInventory(BufferedReader br, String line) throws IOException {
        line = cutTheCurve(br, line);
        boolean end = false;
        do {
            String parts[] = line.split(";");
            line = parts[0];
            int index = line.indexOf('}');
            if (index != -1) {
                line = line.substring(0, index);
                end = true;
            }
            line = line.trim();
            
            if (parts.length > 2) {
                try {
                    int amount = Integer.parseInt(parts[1].trim());
                    double price = Double.parseDouble(parts[2].trim());
                    struct.getInventory().addItem(line, amount, price);
                } catch (NumberFormatException e) {
                    String warning = lineNumber
                    + ": Amount of item " + line
                    + " could not be read. Amount was " + parts[1] + " and price was " + parts[2]
                    + ".";
            setWarning(warning);
        }
                
            } else if (!line.equals("")) {
                struct.getInventory().addItem(line, 0);
            }
            lineNumber++;
        } while (((line = br.readLine()) != null) && !end);
    }
    
    private void setVersion(BufferedReader br, String line) throws IOException {
       line = line.substring(line.indexOf("#Version")+8).trim();
       try {
           struct.setVersion(Double.parseDouble(line));
       } catch (NumberFormatException e) {
           String warning = lineNumber
           + ": Cannot read version number. It was " + line + ".";
   setWarning(warning);
       }
    }

}
