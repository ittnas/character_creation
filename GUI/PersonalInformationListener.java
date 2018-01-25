package GUI;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;

import charInformation.Structure;

public class PersonalInformationListener implements DocumentListener {
    
    private String name;
    
    private Structure struct;
    
    public PersonalInformationListener(String name, Structure struct) {
        this.name = name;
        this.struct = struct;
    }

    public void changedUpdate(DocumentEvent e) {
        
    }

    public void insertUpdate(DocumentEvent e) {
        try {
            struct.addPersonalInformation(name, e.getDocument().getText(0, e.getDocument().getLength()));
        } catch (BadLocationException problem) {
            // TODO Auto-generated catch block
            problem.printStackTrace();
        }
        
    }

    public void removeUpdate(DocumentEvent e) {
        try {
            struct.addPersonalInformation(name, e.getDocument().getText(0, e.getDocument().getLength()));
        } catch (BadLocationException problem) {
            // TODO Auto-generated catch block
            problem.printStackTrace();
        }
        
    }

}
