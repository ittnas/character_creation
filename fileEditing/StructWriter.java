package fileEditing;

import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.HashMap;

import charInformation.Structure;

public class StructWriter {
    
    private String fileName;
    
    private Structure struct;
    
    private ArrayList<String> errors;
    
    private int errorCount;
    
    public StructWriter(String fileName, Structure struct) {
        this.fileName = fileName;
        this.struct = struct;
        errors = new ArrayList<String>();
    }
    
    public void writeStructure() {
        try {
            //FileWriter fstream = new FileWriter(fileName);
        	
        	FileOutputStream outStream = new FileOutputStream(fileName);
			OutputStreamWriter fstream = new OutputStreamWriter(outStream,"UTF-8");
            BufferedWriter out = new BufferedWriter(fstream);
            writeVersion(out);
            writeWriterVersion(out);
            writePersonalInformation(out);
            writeGlobalVariables(out);
            writeBasicAttributes(out);
            writeSkills(out);
            writeInventory(out);
            out.close();
        } catch (IOException e) {
            setError("Can't save open the file" + fileName + ".\n");
        }
    }
    
    private void writeWriterVersion(BufferedWriter out) throws IOException {
        out.write("#Writer version " + Structure.WRITERVERSION + "\n\n");
        
    }
    
    private void writeVersion(BufferedWriter out) throws IOException {
        out.write("#Version " + struct.getVersion() + "\n\n");
    }
    
    private void writePersonalInformation(BufferedWriter out) throws IOException {
        out.write("#Personal information {\n");
        HashMap<String, String> persInf = struct.getPersonalInformation();
        for(String variable : persInf.keySet()) {
            out.write("\t" + variable + " {" + persInf.get(variable) + "}\n");
        }
        out.write("}\n\n");
    }
    
    private void writeGlobalVariables(BufferedWriter out) throws IOException {
        out.write("#Global variables {\n");
        for(String variable : Structure.globalVariables.keySet()) {
            out.write("\t" + variable + " = " + Structure.globalVariables.get(variable) + "\n");
        }
        out.write("}\n\n");
    }
    
    private void writeBasicAttributes(BufferedWriter out) throws IOException {
        out.write("#Basic attributes {\n");
        for(String attr : struct.getBasicAttributes().keySet()) {
            out.write("\t" + attr + " ; " + struct.getBasicAttributes().get(attr).getPointsToAdd() + "\n");
        }
        out.write("}\n\n");
    }
    
    private void writeSkills(BufferedWriter out) throws IOException {
        out.write("#Skills {\n");
        out.write(struct.printSkills());
        out.write("}\n\n");
    }
    
    private void writeInventory(BufferedWriter out) throws IOException {
        out.write("#Inventory {\n");
        for(String item:struct.getInventory().getItemMap().keySet()) {
            out.write("\t" + item);
            out.write("; " + struct.getInventory().getAmount(item));
            out.write("; " + struct.getInventory().getItem(item).getPrice());
            out.write("\n");
        }
        out.write("}\n\n");
    }
    
    public String printsErrors() {
        StringBuilder errorString = new StringBuilder();
        for(int i= 0; i< errors.size(); i++) {
            errorString.append(errors.get(i));
        }
        return errorString.toString();
    }
    
    private void setError(String error) {
        errors.add(error);
        errorCount++;
    }
    
    public int getErrorCount() {
        return errorCount;
    }
    
  /*  private void writeSkillTree(BufferedWriter out, String elder int n) {
        for(int i = 0; i<n;i++)
        {
            out.write("\t");
        }
        out.write(elder + ":" + struct.getSkills().get(elder));
        
            for (int i = 0; i < skills.get(elder).getChildren().size(); i++) {
                printTree(skills.get(elder).getChildren().get(i).name, n+4);
            }
    }
    */
}

  
