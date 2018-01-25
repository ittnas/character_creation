package GUI;

import java.awt.BorderLayout;
import java.awt.Dimension;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.BorderFactory;
import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

import javax.swing.GroupLayout.ParallelGroup;
import javax.swing.GroupLayout.SequentialGroup;
import javax.swing.border.TitledBorder;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

import sun.security.action.GetLongAction;
import sun.security.x509.AVA;

import charInformation.Attribute;
import charInformation.BasicAttribute;
import charInformation.Skill;
import charInformation.Structure;
import fileEditing.StructReader;
import fileEditing.StructWriter;

/**
 * 
 * @author Antti Vepsäläinen
 * 
 */
public class MainWindow extends JFrame implements ActionListener,
        WindowListener {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public enum FileType {
    	PDF {
			@Override
			public String getEnding() {
				return ".pdf";
			}
			@Override
			public String getSecondaryEnding() {
				return ".tex";
			}
			@Override
			public String getLongName() {
				return "Portable Document Format (PDF)";
			}
		},RTF {
			@Override
			public String getEnding() {
				return ".rtf";
			}
			@Override
			public String getLongName() {
				return "Rich Text Format (RTF)";
			}
		}, DVI {
			@Override
			public String getEnding() {
				return ".dvi";
			}
			@Override
			public String getSecondaryEnding() {
				return ".tex";
			}
			@Override
			public String getLongName() {
				return "Device Indepent File Format (DVI)";
			}
		};
    	public abstract String getEnding();
    	public String getSecondaryEnding() {
    		return getEnding();
    	}
    	public String getLongName() {
    		return getEnding().substring(1);
    	}
	}

	private JMenuItem newFile;

    private JFileChooser fc;

    private Structure struct;

    private Properties applicationProps;

    private Properties defaultProps;

    private JTextArea statusTextArea;

    private JPanel informationPanel;

    //private ArrayList<PointsField> pointsFields;

    //private ArrayList<ValueField> valueFields;

    private int fontSize;

    private Font font;

    private Color fontColor;
    
    private Color weakColor;
    
    private Color averageColor;
    
    private Color strongColor;

    private Font titleFont;

    private JMenuItem exit;

    private String fileName;

    private String saveName = "";

    private JMenuItem saveAs;

    private JMenuItem save;

    private JMenuItem editFile;

    private JTextField pointsLeft;

    private JTextArea attributeInformation;
    
    private JTextArea attributeInformation2;

    private JPanel inventoryPanel;

    private JTextField newItemNameField;

    private JButton addItemButton;

    private JTextField newItemAmountField;
    
    private HashMap<String , ItemInfoContainer> itemContainers;

    private JTextField newItemPriceField;
    
    private Font boldFont;

    private JButton addExpButton;

    private JTextField addExpField;

    private JMenuItem rtf;

	private JTextField attrPointsLeft;

	private JMenuItem pdf;
	
	private ArrayList<AttributeContainer> attributeContainers;

	private Color mouseOverColor;

	private FileNameExtensionFilter ruleFileFilter;

	private FileNameExtensionFilter characterFileFilter;

    /**
     * 
     * @throws IOException
     */
    public MainWindow() throws IOException {
        super();
        initialize();
        createWindow();
        setVisible(true);
    }

    private void initialize() throws IOException {
        defaultProps = new Properties();
        FileInputStream in = new FileInputStream("defaultProperties.txt");
        defaultProps.load(in);
        in.close();
        applicationProps = new Properties();
        applicationProps.putAll(defaultProps);
        try {
            in = new FileInputStream("Properties.txt");
        } catch (FileNotFoundException e) {
            new FileWriter("Properties.txt");
        }
        in = new FileInputStream("Properties.txt");
        applicationProps.load(in);
        in.close();
        try {
            fontSize = Integer.parseInt(applicationProps
                    .getProperty("fontSize"));
        } catch (Exception e) {
            fontSize = 24;
        }
        String colorComponents[] = applicationProps.getProperty("fontColor")
                .split(",");
        int colorRGB[] = new int[4];
        for (int i = 0; i < colorComponents.length; i++) {
            colorRGB[i] = Integer.parseInt(colorComponents[i].trim());
        }
        fontColor = new Color(colorRGB[0], colorRGB[1], colorRGB[2],
                colorRGB[3]);
        colorComponents = applicationProps.getProperty("weakColor").split(",");
        for (int i = 0; i < colorComponents.length; i++) {
            colorRGB[i] = Integer.parseInt(colorComponents[i].trim());
        }
        weakColor = new Color(colorRGB[0], colorRGB[1], colorRGB[2],
                colorRGB[3]);
        colorComponents = applicationProps.getProperty("averageColor").split(",");
        for (int i = 0; i < colorComponents.length; i++) {
            colorRGB[i] = Integer.parseInt(colorComponents[i].trim());
        }
        averageColor= new Color(colorRGB[0], colorRGB[1], colorRGB[2],
                colorRGB[3]);
        colorComponents = applicationProps.getProperty("strongColor").split(",");
        for (int i = 0; i < colorComponents.length; i++) {
            colorRGB[i] = Integer.parseInt(colorComponents[i].trim());
        }
        strongColor = new Color(colorRGB[0], colorRGB[1], colorRGB[2],
                colorRGB[3]);
        colorComponents = applicationProps.getProperty("mouseOverColor").split(",");
        for (int i = 0; i < colorComponents.length; i++) {
            colorRGB[i] = Integer.parseInt(colorComponents[i].trim());
        }
        mouseOverColor = new Color(colorRGB[0], colorRGB[1], colorRGB[2],
                colorRGB[3]);
        try {
            String fontString = applicationProps.getProperty("font");
            font = Font.decode(fontString + "-" + fontSize);
            String newFont = font.getFontName() + "-BOLDITALIC" + "-"
                    + (int) (font.getSize() * 1.5);
            titleFont = Font.decode(newFont);
            boldFont = Font.decode(font.getFontName() + "-BOLDITALIC" + "-" + font.getSize());
        } catch (Exception e) {
            font = Font.decode("Arial-" + fontSize);
        }

    }

    private void createWindow() throws IOException {
        //valueFields = new ArrayList<ValueField>();
        //pointsFields = new ArrayList<PointsField>();
    	attributeContainers = new ArrayList<AttributeContainer>();
        itemContainers = new HashMap<String, ItemInfoContainer>();

        this.setContentPane(createContentPane());
        this.setJMenuBar(createMenuBar());
        JScrollPane sheetAreaPanel = createSheetArea();
        JSplitPane sheetSplit = new JSplitPane(JSplitPane.VERTICAL_SPLIT,createAttributeInformationWindow(),sheetAreaPanel);
        
        sheetSplit.setOpaque(false);
        JSplitPane masterSplit = new JSplitPane(JSplitPane.VERTICAL_SPLIT,sheetSplit,createStatusWindow());
        //int dividerLocation = 0;
        masterSplit.setOpaque(false);
        masterSplit.setResizeWeight(1.0);
        //masterSplit.setDividerLocation(this.getHeight() - 500);
        /*
        masterSplit.addPropertyChangeListener(JSplitPane.DIVIDER_LOCATION_PROPERTY, new PropertyChangeListener() {
			
			public void propertyChange(PropertyChangeEvent event) {
			event.getSource()
			
			}
		});
        */
        //getContentPane().add(createStatusWindow(), BorderLayout.SOUTH);

        //get+ContentPane().add(createSheetArea(), BorderLayout.CENTER);
        //getContentPane().add(createAttributeInformationWindow(), BorderLayout.NORTH);
        //setMinimumSize(new Dimension(800, 1000));
        getContentPane().add(masterSplit);
        setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        addWindowListener(this);
    }

    private JPanel createContentPane() { // DUMMY
        JPanel contentPane = new JPanel();
        contentPane.setOpaque(false);
        contentPane.setLayout(new BorderLayout());
        return contentPane;
    }

    private JMenuBar createMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        JMenu file = new JMenu("File");
        file.setMnemonic(KeyEvent.VK_F);
        menuBar.add(file);
        newFile = new JMenuItem("Create new character");
        newFile.setMnemonic(KeyEvent.VK_C);
        fc = new JFileChooser(".");
   		characterFileFilter = new FileNameExtensionFilter("Character file (CHR)","chr");
   		fc.addChoosableFileFilter(characterFileFilter);
   		ruleFileFilter = new FileNameExtensionFilter("Rule file (CH)","ch");
   		fc.addChoosableFileFilter(characterFileFilter);
        newFile.addActionListener(this);
        file.add(newFile);
        editFile = new JMenuItem("Edit existing character");
        editFile.setMnemonic(KeyEvent.VK_E);
        editFile.addActionListener(this);
        file.add(editFile);
        saveAs = new JMenuItem("Save as");
        saveAs.setMnemonic(KeyEvent.VK_V);
        saveAs.addActionListener(this);
        file.add(saveAs);
        save = new JMenuItem("Save");
        save.setMnemonic(KeyEvent.VK_S);
        save.addActionListener(this);
        file.add(save);
        JMenu exportMenu = new JMenu("Export");
        exportMenu.setMnemonic(KeyEvent.VK_X);
        file.add(exportMenu);
        rtf = new JMenuItem("Export as rtf");
        rtf.setMnemonic(KeyEvent.VK_R);
        rtf.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent arg0) {
				export(FileType.RTF);
			}
		});
        exportMenu.add(rtf);
        
        pdf = new JMenuItem("Export as pdf");
        pdf.setMnemonic(KeyEvent.VK_P);
        pdf.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent arg0) {
				export(FileType.PDF);
			}
		});
        if(!latexAvailable()) {
        	pdf.setEnabled(false);
        }
        exportMenu.add(pdf);
        exit = new JMenuItem("Exit");
        exit.setMnemonic(KeyEvent.VK_X);
        exit.addActionListener(this);
        file.add(exit);
        return menuBar;
    }

    private boolean latexAvailable() {
		// TODO Check if latex is really available... and dvipdf too.
		return true;
	}
    
    public Color getWeakColor() {
    	return this.weakColor;
    }
    
    public Color getAverageColor() {
    	return this.averageColor;
    }
    
    public Color getStrongColor() {
    	return this.strongColor;
    }
    
    public Color getMoseOverColor() {
    	return this.mouseOverColor;
    	
    }

	private JScrollPane createStatusWindow() {

        statusTextArea = new JTextArea(); // PASKAA, pitï¿½is keksiï¿½ jokin parempi
        statusTextArea.setRows(3);
        statusTextArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane();

        scrollPane.getViewport().add(statusTextArea);
        return scrollPane;
    }

    private JScrollPane createSheetArea() {
        informationPanel = new JPanel() {
            /**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			protected void paintComponent(Graphics g) {
                ImageIcon background = new ImageIcon(applicationProps
                        .getProperty("background"));
                Dimension d = getSize();
                g.drawImage(background.getImage(), 0, 0, d.width, d.height,
                        null);
                super.paintComponent(g);
            }
        };
        informationPanel.setOpaque(false);
        informationPanel.setLayout(new SheetLayout());
        JScrollPane infoPane = new JScrollPane(informationPanel);
        infoPane.getVerticalScrollBar().setUnitIncrement(30);
        return infoPane;
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == newFile) {
            newFile();
        }
        if (e.getSource() == exit) {
            windowClosing(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
        }

        if (e.getSource() == saveAs) {
            saveAs();
        }
        if (e.getSource() == save) {
            save();
        }

        if (e.getSource() == editFile) {
            editExistingCharacter();
        }

        if (e.getSource() == addItemButton) {
            addNewItem();
        }
        
        if (e.getSource() == addExpButton) {
            addExp();
        }
        
        if (e.getSource() == rtf) {
            makeRTF();
        }
    }
    
    /**
     * This method is just a stub, and not used.
     */
    /*
    private void makePDF() {
    	if(struct != null) {
    		// Create the latex file.
            FileWriter fstream;
            BufferedWriter out;
    	} else {
            statusTextArea.append("There is no character to export.\n");
        }
    }
    */
    private ProcessBuilder latexProcess(String fileName) throws IOException, InterruptedException, LatexException {
    	File testFile = new File(fileName);
    	String folder = testFile.getParent();
    	if(folder == null) {
    		folder = "./";
    	}
    	String[] command = {"latex", "--output-directory=" + folder,fileName};
    	ProcessBuilder pb = new ProcessBuilder(command);
    	final Process p = pb.start();
    	// According to stackoverflow.com/questions/5138946 there is a bug in Windows which stops the Process p from being killed.
    	// If latex fails to perform, timer will destroy the process so that it won't get stuck for eternity.
    	Timer timer = new Timer();
    	timer.schedule(new TimerTask() {
			
			@Override
			public void run() {
				p.destroy();
			}
		}, 1500);
    	int exitValue = p.waitFor();
    	timer.cancel();
    	if(exitValue != 0) {
    		throw new LatexException();
    	} else {
    		statusTextArea.append("Latex performed succesfully.\n");
    		// Next clean all the intermediate files.
    		//String fileNameWOextension = getFileNameBody(fileName,
    		String[] cleanTex = {"rm",fileName};
    		pb.command(cleanTex);
    		final Process cleanTexProcess = pb.start();
    		exitValue = cleanTexProcess.waitFor();
    		String fileNameWithoutExtension = getFileNameBody(fileName, ".tex");
    		if(exitValue != 0) {
    			statusTextArea.append("Failed to clean .tex file.\n");
    		} else {
    			String[] cleanAux = {"rm",fileNameWithoutExtension + ".aux"};
    			pb.command(cleanAux);
    			final Process cleanAuxProcess = pb.start();
    			exitValue = cleanAuxProcess.waitFor();
    			if(exitValue != 0) {
    				statusTextArea.append("Failed to clean .aux file.\n");
    			} else {
        			String[] cleanLog = {"rm",fileNameWithoutExtension + ".log"};
        			pb.command(cleanLog);
        			final Process cleanLogProcess = pb.start();
        			exitValue = cleanLogProcess.waitFor();
        			if(exitValue != 0) {
        				statusTextArea.append("Failed to clean .log file.\n");
        			}
    			}
    		}
    	}
    	return pb;
    }
    
    private String getFileNameBody(String fileName,String ending) {
		String output;
    	if(fileName.endsWith(ending)) {
			output = fileName.substring(0,fileName.lastIndexOf(ending));
		} else {
			output = fileName;
		}
    	return output;
    }
    
    private void export(FileType fileType) {
       	if(struct != null) {
            
            String fileName = exportConversion(fileType);
            if(fileName != null) {
            	fileName = getFileNameBody(fileName, fileType.getEnding()) + fileType.getSecondaryEnding();
            	try {
            		FileOutputStream outStream = new FileOutputStream(fileName);
            		OutputStreamWriter fstream = new OutputStreamWriter(outStream,"UTF-8");
                    BufferedWriter out = new BufferedWriter(fstream);
            	//FileWriter fstream = new FileWriter(fileName);
                //BufferedWriter out = new BufferedWriter(fstream);
            	// Handle different filetypes
            	
            	String text;
            	switch(fileType) {
            	case PDF:
            		text = struct.printLatex(weakColor,averageColor,strongColor);
            		out.write(text);
            		out.close();
            		try {
            		ProcessBuilder pb = latexProcess(fileName);
            		pb.command("dvipdf",getFileNameBody(fileName, ".tex"));
            		File targetFile = new File(fileName);
            		File processDirectory = new File(targetFile.getParent());
            		pb.directory(processDirectory);
            		Process p = pb.start();
            		int exitValue = p.waitFor();
            		if(exitValue != 0) {
            			statusTextArea.append("Failed to create PDF-file\n");
            		} else {
            			String[] cleanDvi = {"rm",getFileNameBody(fileName, ".tex") + ".dvi"};
            			pb.command(cleanDvi);
            			final Process cleanDviProcess = pb.start();
            			exitValue = cleanDviProcess.waitFor();
            			if(exitValue!=0) {
            				statusTextArea.append("Failed to clean .dvi file\n");
            			}
            		}
            		} catch (Exception e) {
            			statusTextArea.append("Failed to write the Latex file.\n");
            			return;
            		}
            		break;
            	case DVI:
            		text = struct.printLatex(weakColor,averageColor,strongColor);
            		out.write(text);
            		out.close();
            		try {
						latexProcess(fileName);
					} catch (Exception e) {
						statusTextArea.append("Failed to write the Latex file.\n");
					}
            		break;
            	case RTF:
            		text = struct.printRTF();
            		out.write(text);
            		out.close();
            		break;
            		default:
            			statusTextArea.append("No operation specified for selected file type.\n");
            			return;
            	}
            	statusTextArea.append("Character exported succesfully!\n");
            	} catch (IOException e) {
            		statusTextArea.append("Failed to write the character to file: " + fileName +"\n");
            		return;
            	}
            } else {
            	statusTextArea.append("Export operation aborted.\n");
            	return;
            }
    	} else {
            statusTextArea.append("There is no character to export.\n");
            return;
        }
    }
    
    /** 
     *  
     * @param fileType 
     * @return path to file where the character will be exported. Returns null if export should terminate.
     */
    
    private String exportConversion(FileType fileType) {
    	if(saveName == null) {
    		//Try saving.
    		saveAs();
    		if(saveName == null) {
    			//Saving failed. Should abort.
    			return null;
    		}
    	}
    	// SaveName exists. Can go forward.
    	String output;
 
    		// saveName is fine
    	if(saveName.endsWith(".chr")) {
    		output = saveName.substring(0,saveName.lastIndexOf(".chr")) + fileType.getEnding();
    	} else {
    		output = saveName + fileType.getEnding();
    	}
/*				
				//return output;
    		//saveName is not fine.
          	int returnVal = fc.showSaveDialog(this);
        	if(returnVal == JFileChooser.APPROVE_OPTION) {
        		File file = fc.getSelectedFile();
        		output = file.getAbsolutePath();
        	} else {
        		return null; // Saving cancelled.
        	}
        	if (output != null && !output.equals("")) {
        		if(!output.endsWith(fileType.getEnding())) {
        			output = output + fileType.getEnding();
        		} else {
        			output = output + fileType.getEnding();
        		}
        		return output; // We are done
        	} else {
       			// Attempt failed. Terminate.
    			return null;
        	}
    	}*/
    	// File name chosen. Ask to override.
    	File f = new File(output);
     	if(f.exists()) {
    		int returnVal = JOptionPane.showConfirmDialog(this, "Character with name " + output + " already exists. Override?");
    	   	if(returnVal == JOptionPane.YES_OPTION) {
    	   		return output;
    	   	} else if(returnVal == JOptionPane.NO_OPTION) {
    	   		JFileChooser fileChooser = new JFileChooser(output);
    	   		FileFilter fileFilter = new FileNameExtensionFilter(fileType.getLongName(),fileType.getEnding().substring(1));
    	   		fileChooser.addChoosableFileFilter(fileFilter);
    	   		fileChooser.setFileFilter(fileFilter);
    	   		int saveReturnVal = fileChooser.showSaveDialog(this);
    	   		if(saveReturnVal == JFileChooser.APPROVE_OPTION) {
            		File file = fileChooser.getSelectedFile();
            		output = file.getAbsolutePath();
                	if (output != null && !output.equals("")) {
                		if(!output.endsWith(fileType.getEnding())) {
                			output = output + fileType.getEnding();
                		}
                		return output; // We are done
                	} else {
               			// Attempt failed. Terminate.
            			return null;
                	}
            	} else {
            		return null; // Saving cancelled.
            	}
        	} else {
        		// Cancel option. 
        		return null;
        	}
    	} else return output;
    }
    
    /*private String exportConversion(FileType fileType) {
    	String output = (String) JOptionPane.showInputDialog(this,
    			"Enter filename for character", "Export as " + fileType.toString().toLowerCase(),
    			JOptionPane.PLAIN_MESSAGE, null, null, null);
    	output = output.trim();
    	if (output != null && !output.equals("")) {
    		if(!output.endsWith(fileType.getEnding())) {
    			output = output + fileType.getEnding();
    			return output;
    		} else {
    			if(saveName != null) {
    				if(saveName.endsWith(".chr")) {
    					output = saveName.substring(saveName.lastIndexOf(".chr")) + fileType.getEnding();
    				} else {
    					output = saveName + fileType.getEnding();
    				}
    				
    			} else {
    				output = "defaultChar" + fileType.getEnding();
    				return output;
    			}
    			output = "defaultChar" + fileType.getEnding();
    			return output;
    		}
    	} else return null;
    }*/

    private void makeRTF() {
        if (struct != null) {
            FileWriter fstream;
            BufferedWriter out;
            try {
                String outFile;
                if(saveName == null) {
                    String input = (String) JOptionPane.showInputDialog(this,
                            "Enter filename for character", "Export as rtf",
                            JOptionPane.PLAIN_MESSAGE, null, null, null);
                    if (input != null) {
                        {
                            if (input.trim().equals("")) {
                                saveName = "defaultChar.chr";
                            } else {
                                saveName = input + ".chr";
                            }
                        }
                    }
                }
                String nameParts[] = saveName.split("\\.");
                outFile = nameParts[0] + ".rtf";
                fstream = new FileWriter(outFile);
                out = new BufferedWriter(fstream);
                String text = struct.printRTF();
                out.write(text);
                statusTextArea.append("Character imported to RTF-file called " + outFile + ".\n");
                out.close();
            } catch (IOException problem) {
                statusTextArea.append("Couldn't create or open file " + saveName.split("\\.")[0] + ".rtf.");
            }
            
        } else {
            statusTextArea.append("There is no character to export.");
        }
    }

    private void addExp() {
        try{
        double exp = Double.parseDouble(addExpField.getText());
        struct.addExperience(exp);
        Structure.globalVariables.put("pointLimit", Structure.globalVariables.get("pointLimit") + exp);
        addExpField.setText("");
        //updateAllPointsFields();
        //TODO maybe it would be better to update only point fields
        updateAttributeContainers();
        } catch (NumberFormatException e) {
            
        }
    }

    private void createCharacterSheet() {
        
        informationPanel.add(createPersonalInformation(), new GridBagConstraints(0, 0, 1, 1, 0, 0,
                GridBagConstraints.NORTHWEST, GridBagConstraints.BOTH,
                new Insets(0, 0, 0, 0), 0, 0));
        informationPanel.add(createPointsLeft());
        informationPanel.add(basicAttributes(), new GridBagConstraints(0, 0, 1, 1, 0, 0,
                GridBagConstraints.NORTHWEST, GridBagConstraints.BOTH,
                new Insets(0, 0, 0, 0), 0, 0));
        JPanel[] skills = skills();
        for (int i = 0; i < skills.length; i++) {
            informationPanel.add(skills[i], new GridBagConstraints(0, 0, 1, 1, 0, 0,
                    GridBagConstraints.NORTHWEST, GridBagConstraints.BOTH,
                    new Insets(0, 0, 0, 0), 0, 0));
        }
        informationPanel.add(createInventory());
        JPanel fillPanel = new JPanel();
        fillPanel.setOpaque(false);
        informationPanel.add(fillPanel, new GridBagConstraints(0, 0, 2, 1, 1, 1,
                GridBagConstraints.NORTHWEST, GridBagConstraints.BOTH,
                new Insets(0, 0, 0, 0), 0, 0));
        informationPanel.repaint();
        informationPanel.updateUI();
    }

    private JPanel basicAttributes() {
        JPanel panel = new JPanel();
        panel.setOpaque(false);
        panel.setBorder(BorderFactory.createTitledBorder(BorderFactory
                .createLineBorder(fontColor), "Ominaisuudet",
                TitledBorder.LEFT, TitledBorder.TOP, titleFont, fontColor));

        HashMap<String, BasicAttribute> basicAttributes = struct
                .getBasicAttributes();
        int columns = basicAttributes.size() / 8 + 1;
        int rows = (struct.getBasicAttributes().size() + 1) / (columns);
        panel.setLayout(new GridLayout(rows, columns));

        ArrayList<String> nameList = new ArrayList<String>();
        nameList.addAll(basicAttributes.keySet());
        Collections.sort(nameList);
        
        for (String attr : nameList) {
        	panel.add(createAttributeContainer(basicAttributes.get(attr), 0));
        }
        return panel;
    }

    private JPanel[] skills() {
        ArrayList<Skill> elders = struct.getElders();
        JPanel[] skillTables = new JPanel[elders.size()];
        for (int i = 0; i < elders.size(); i++) {
            skillTables[i] = new JPanel();
            skillTables[i].setOpaque(false);
        }
        for (int i = 0; i < elders.size(); i++) {
            GroupLayout layout = new GroupLayout(skillTables[i]);
            skillTables[i].setLayout(layout);
            skillTables[i].setBorder(BorderFactory.createTitledBorder(
                    BorderFactory.createLineBorder(fontColor), elders.get(i)
                            .getName(), TitledBorder.LEFT, TitledBorder.TOP,
                    titleFont, fontColor));
            SequentialGroup seqGroup = layout.createSequentialGroup();
            ParallelGroup parGroup = layout.createParallelGroup();
            createSkillGroup(elders.get(i), parGroup, seqGroup, layout, 0);
            layout.setHorizontalGroup(parGroup);
            layout.setVerticalGroup(seqGroup);
        }
        return skillTables;

    }

    private void createSkillGroup(Skill elder, ParallelGroup horizontal,
            SequentialGroup vertical, GroupLayout layout, int depth) {
        if (elder.getChildren().size() == 0) {
            JPanel cont = createAttributeContainer(elder, depth);
            horizontal.addComponent(cont);
            vertical.addComponent(cont);
        } else {
            vertical = vertical.addGroup(layout.createSequentialGroup());
            JPanel cont = createAttributeContainer(elder, depth);
            horizontal.addComponent(cont);
            vertical.addComponent(cont); // sets the Gap to zero
            for (int i = 0; i < elder.getChildren().size(); i++) {
                createSkillGroup(elder.getChildren().get(i), horizontal,
                        vertical, layout, depth + 1);
            }
        }
    }
    
    
    private AttributeContainer createAttributeContainer(Attribute attribute, int indent) {
    	AttributeContainer attrCont = new AttributeContainer(attribute, indent, this);
    	attributeContainers.add(attrCont);
    	return attrCont;
    }
    
/*    private JPanel createAttributeContainer(Attribute attribute, int indent) {
    	Color weakFontColor = new Color(0,128,128);
    	Color averageFontColor = new Color(128, 0, 0);
    	Color highFontColor = new Color(0,255,0);
    	Color fontColor;
    	if (attribute.getValue() <= 4) {
    		fontColor = weakFontColor;
    	} else if (attribute.getValue() <= 7) {
    		fontColor = averageFontColor;
    	} else {
    		fontColor = highFontColor;
    	}
    	 
    	
        JPanel container = new JPanel();
        container.setOpaque(false);
        container.setLayout(new GridBagLayout());
        JLabel name = new JLabel(attribute.getName());

        name.setFont(font);
        name.setForeground(fontColor);
        JPanel indentPanel = new JPanel();
        indentPanel.setOpaque(false);
        indentPanel.setPreferredSize(new Dimension(indent * 30, fontSize));
        container.add(indentPanel);
        container.add(name, new GridBagConstraints(1, 0, 1, 1, 1.0, 1.0,
                GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0,
                        0, 0, 0), 0, 0));
        ValueField value = new ValueField(attribute);
        value.setFont(font);
        value.setForeground(fontColor);
        value.setPreferredSize(new Dimension(fontSize * 2, fontSize + 2));
        valueFields.add(value);
        container.add(value);
        JButton decreaseByOne = new JButton();
        decreaseByOne.setOpaque(false);
        decreaseByOne.addActionListener(new IncreaseListener(false, struct,
                attribute.getName(), this));
        decreaseByOne.setPreferredSize(new Dimension(fontSize, fontSize));
        JButton increaseByOne = new JButton();
        increaseByOne.setOpaque(false);
        increaseByOne.setPreferredSize(new Dimension(fontSize, fontSize));
        increaseByOne.addActionListener(new IncreaseListener(true, struct,
                attribute.getName(), this));
        ImageIcon increaseIcon = new ImageIcon("IncreaseButton.JPG");
        ImageIcon decreaseIcon = new ImageIcon("decreaseIcon.JPG");
        increaseByOne.setBorder(BorderFactory.createEmptyBorder());
        increaseByOne.setIcon(increaseIcon);
        decreaseByOne.setBorder(BorderFactory.createEmptyBorder());
        decreaseByOne.setIcon(decreaseIcon);
        container.add(increaseByOne);
        container.add(decreaseByOne);
        PointsField points = new PointsField(attribute);
        points.setForeground(fontColor);
        pointsFields.add(points);
        container.add(points);
        container.addMouseListener(new AttributeInfoListener(attribute
                .getName(), this));
        return container;
    }
    */

    private void newFile() {

        int answer;
        if (applicationProps.containsKey("defaultCharacter")) {
            Object[] options = { "Yes", "Browse", "Cancel" };
            String paneText = "Create new character using rules of ";
            String[] ruleName = applicationProps
                    .getProperty("defaultCharacter").split("\\.chr\\z");
            paneText += ruleName[0]
                    + " or click browse to choose the rules yourself.";
            answer = JOptionPane.showOptionDialog(this, paneText, "",
                    JOptionPane.YES_NO_CANCEL_OPTION,
                    JOptionPane.PLAIN_MESSAGE, null, options, options[0]);
        } else {
            Object[] options = { "Browse", "Cancel" };
            String paneText = "Click browse to choose the rules";
            answer = JOptionPane.showOptionDialog(this, paneText, "",
                    JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE,
                    null, options, options[0]);
            if (answer == JOptionPane.OK_OPTION) {
                answer = JOptionPane.NO_OPTION;
            }
        }
        if (answer == JOptionPane.CANCEL_OPTION) {
            return;
        }
        if (answer == JOptionPane.NO_OPTION) {
        	fc.setFileFilter(ruleFileFilter);
            int returnVal = fc.showOpenDialog(this);
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                File file = fc.getSelectedFile();
                fileName = file.getAbsolutePath();
                openCharacter(fileName, null);
            }
        } else if (answer == JOptionPane.YES_OPTION) {
            fileName = applicationProps.getProperty("defaultCharacter");
            openCharacter(fileName, null);

        }
    }

    private void editExistingCharacter() {
    	fc.setFileFilter(characterFileFilter);
        int returnVal = fc.showOpenDialog(this);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File file = fc.getSelectedFile();

            fileName = file.getPath();

            openCharacter(fileName, fileName);
        }
    }

    private boolean saveAs() {
    	boolean saveSuccesful = false;
        if (struct != null) {
        	fc.setFileFilter(characterFileFilter);
        	int returnVal = fc.showSaveDialog(this);
        	if(returnVal == JFileChooser.APPROVE_OPTION) {
        		File file = fc.getSelectedFile();
        		saveName = file.getAbsolutePath();
        		if(!saveName.endsWith(".chr")) {
        			saveName += ".chr";
        		}
        		StructWriter writer = new StructWriter(saveName, struct);
        		writer.writeStructure();
                if (writer.getErrorCount() != 0) {
                    statusTextArea.append(writer.printsErrors());
                } else {
                    statusTextArea.append("Character saved to file " + saveName
                            + ".\n");
                    
                }
                saveSuccesful = true;
                String titleName = saveName.substring(saveName.lastIndexOf(System.getProperty("file.separator"))+1);
                this.setTitle(titleName);
        	} else {
        		statusTextArea.append("Saving cancelled");
        		saveSuccesful = false;
        	}
        	/*
            String input = (String) JOptionPane.showInputDialog(this,
                    "Enter filename for character", "Save as",
                    JOptionPane.PLAIN_MESSAGE, null, null, null);
            if (input != null) {
            	if (input.trim().equals("")) {
            		saveName = "defaultChar.chr";
            	} else if(input.endsWith(".chr")) {
       				saveName = input;
            	} else {
            		saveName = input + ".chr";
            	}
            	this.setTitle(saveName);
            	StructWriter writer = new StructWriter(saveName, struct);
            	writer.writeStructure();
                if (writer.getErrorCount() != 0) {
                    statusTextArea.append(writer.printsErrors());
                } else {
                    statusTextArea.append("Character saved to file " + saveName
                            + ".\n");
                }

            } */
        } else {
            statusTextArea.append("No structure to save!\n");
            JOptionPane.showMessageDialog(this, "No structure to save!");
            saveSuccesful = false;
        }
        return saveSuccesful;
    }

    private boolean save() {
    	boolean saveSuccessful;
        if (saveName == null) {
            saveSuccessful = saveAs();
        } else {

            if (struct != null) {
                statusTextArea.append("Character saved to file " + saveName
                        + ".\n");
                StructWriter writer = new StructWriter(saveName, struct);
                writer.writeStructure();
                saveSuccessful = true;
            } else {
                statusTextArea.append("No structure to save!\n");
                JOptionPane.showMessageDialog(this, "No structure to save!");
                saveSuccessful = false;
            }
        }
        return saveSuccessful;
    }
    
    public Structure getStructure() {
    	return this.struct;
    }

    private JPanel createPersonalInformation() {
        JPanel container = new JPanel();
        container.setOpaque(false);
        HashMap<String, String> persMap = struct.getPersonalInformation();
        container.setLayout(new GridBagLayout());
        container.setBorder(BorderFactory.createTitledBorder(BorderFactory
                .createLineBorder(fontColor), "Hahmon tiedot", TitledBorder.LEFT,
                TitledBorder.TOP, titleFont, fontColor));

        int i = 1;
        for (String pers : persMap.keySet()) {
            JLabel label = new JLabel(pers + ": ");
            label.setFont(font);
            label.setForeground(fontColor);
            JTextArea text = new JTextArea(persMap.get(pers), 1, 5);
            text.getDocument().addDocumentListener(
                    new PersonalInformationListener(pers, struct));
            text.setOpaque(false);
            text.setFont(font);
            text.setForeground(fontColor);
            //text.setLineWrap(true); joo ei toimi
            /*
             * JScrollPane textScroll = new JScrollPane(text);
             * textScroll.setBorder(BorderFactory.createEmptyBorder());
             * textScroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.
             * HORIZONTAL_SCROLLBAR_NEVER);
             */
            container.add(label, new GridBagConstraints(0, i, 1, 1, 0, 0,
                    GridBagConstraints.NORTHWEST, GridBagConstraints.NONE,
                    new Insets(0, 10, 0, 10), 1, 0));
            /*
             * container.add(textScroll, new GridBagConstraints(1, i, 1, 1, 1.0,
             * 1.0, GridBagConstraintjepuys.WEST, GridBagConstraints.NONE, new
             * Insets(0, 10, 0, 10), 1, 0));
             */
            container.add(text, new GridBagConstraints(1, i, 1, 1, 1.0, 0,
                    GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL,
                    new Insets(0, 10, 0, 10), 75, 0));
            i++;
        }
        JPanel fill = new JPanel();
        fill.setOpaque(false);
        container.add(fill, new GridBagConstraints(0, i, 2, 1, 1.0, 1.0,
                GridBagConstraints.WEST, GridBagConstraints.BOTH,
                new Insets(0, 0, 0, 0), 0, 0));
        i++;
        return container;
    }
    
    public void updateAttributeContainers() {
    	for (int i = 0; i < attributeContainers.size(); i++) {
    		attributeContainers.get(i).updateAll();
    	}
    	
        pointsLeft
        .setText(new Integer((int) struct.getPointsLeft()).toString());
attrPointsLeft.setText(new Integer((int) struct.getAttrPointsLeft()).toString());
    }
/*
    public void updateAllValueFields() {
        for (int i = 0; i < attributeContainers.size(); i++) {
            attributeContainers.get(i).updateValue();
        }
    }
*/
    /*
    public void updateAllPointsFields() {
        for (int i = 0; i < attributeContainers.size(); i++) {
            attributeContainers.get(i).updatePoints();
        }
        pointsLeft
                .setText(new Integer((int) struct.getPointsLeft()).toString());
        attrPointsLeft.setText(new Integer((int) struct.getAttrPointsLeft()).toString());
    }
*/
    public void windowClosed(WindowEvent e) {
    }

    public void windowClosing(WindowEvent e) {
        if (struct != null) {
            int answer = JOptionPane
                    .showConfirmDialog(this,
                            "Do you want to save changes before closing?", "",
                            JOptionPane.YES_NO_CANCEL_OPTION,
                            JOptionPane.PLAIN_MESSAGE);
            if (answer == JOptionPane.YES_OPTION) {
                boolean saveSuccesful = save();
                if(saveSuccesful) {
                	dispose();
                }
            } else if (answer == JOptionPane.NO_OPTION) {
                dispose();
            }
        } else {
            dispose();
        }
    }

    private void openCharacter(String fileName, String saveName) {
        StructReader reader = new StructReader(fileName);
        if (struct != null) {
            try {
                MainWindow newWindow = new MainWindow();
                newWindow.openCharacter(fileName, saveName);
            } catch (IOException e) {

                statusTextArea.append("Creating a new character failed\n");
            }
        } else {
            struct = reader.readStructure();
            statusTextArea.append(reader.printErrors());
            //if file is not found, nothing is done
            if(struct == null) {
            	return;
            }
            struct.init();
            createCharacterSheet();
            this.saveName = saveName;
            this.fileName = fileName;
            if(saveName != null) {
            	String titleName = saveName.substring(saveName.lastIndexOf(System.getProperty("file.separator"))+1);
            	this.setTitle(titleName);
            } else {
            	this.setTitle("New character");
            }
            /*if (saveName == null) {
                saveAs();
            } else {
                save();
            }*/
        }
    }

    private JPanel createPointsLeft() {
        JPanel container = new JPanel();
        container.setOpaque(false);
        pointsLeft = new JTextField(new Integer((int) struct.getPointsLeft())
                .toString());
        pointsLeft.setOpaque(false);
        pointsLeft.setEditable(false);
        //JLabel pointsLeftLabel = new JLabel("Skill points left");
        JLabel pointsLeftLabel = new JLabel("Taitopisteitï¿½ jï¿½ljellï¿½");
        pointsLeftLabel.setFont(font);
        pointsLeftLabel.setForeground(fontColor);
        pointsLeft.setFont(font);
        pointsLeft.setForeground(fontColor);
        pointsLeft.setColumns(2);
        attrPointsLeft = new JTextField(new Integer((int) struct.getAttrPointsLeft()).toString());
        attrPointsLeft.setOpaque(false);
        attrPointsLeft.setEditable(false);
        attrPointsLeft.setFont(font);
        attrPointsLeft.setForeground(fontColor);
        attrPointsLeft.setColumns(2);
        //JLabel attrPointsLeftLabel = new JLabel("Attribute points left");
        JLabel attrPointsLeftLabel = new JLabel("Ominaisuuspisteitï¿½ jï¿½ljellï¿½");
        attrPointsLeftLabel.setFont(font);
        attrPointsLeftLabel.setForeground(fontColor);
        container.setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints(0, 0, 1, 1, 1d, 1d, GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0);
        container.add(attrPointsLeftLabel,constraints);
        constraints.gridx++;
        container.add(attrPointsLeft,constraints);
        constraints.gridx--;
        constraints.gridy++;
        container.add(pointsLeftLabel, constraints);
        constraints.gridx++;
        container.add(pointsLeft, constraints);
        addExpButton = new JButton();
        addExpButton.addActionListener(this);
        addExpButton.setPreferredSize(new Dimension(fontSize*2,(int)(fontSize*1.5)));
        ImageIcon buttonIcon = new ImageIcon("largeButton.JPG");
        addExpButton.setIcon(buttonIcon);
        addExpField = new JTextField();
        addExpField.setOpaque(false);
        addExpField.setFont(font);
        addExpField.setForeground(fontColor);
        addExpField.setPreferredSize(pointsLeft.getPreferredSize());
        JLabel addExpLabel = new JLabel("Add Experience");
        addExpLabel.setForeground(fontColor);
        addExpLabel.setFont(font);
        constraints.gridx--;
        constraints.gridy++;
        container.add(addExpLabel, constraints);
        constraints.gridx++;
        container.add(addExpField, constraints);
        constraints.gridx++;
        container.add(addExpButton, constraints);
        return container;
    }

    private JScrollPane createAttributeInformationWindow() {
        JPanel infoWindow = new JPanel();
        infoWindow.setLayout(new GridBagLayout());
        attributeInformation = new JTextArea();
        attributeInformation.setRows(3);
        attributeInformation.setEditable(false);
        JScrollPane scroll = new JScrollPane(infoWindow);
        attributeInformation2 = new JTextArea();
        attributeInformation2.setRows(3);
        attributeInformation2.setEditable(false);
        infoWindow.add(attributeInformation, new GridBagConstraints(0, 0, 1, 1, 1.0, 1.0,
                GridBagConstraints.WEST, GridBagConstraints.BOTH, new Insets(0,
                        0, 0, 0), 0, 0));
        infoWindow.add(attributeInformation2, new GridBagConstraints(1, 0, 1, 1, 1.0, 1.0,
                GridBagConstraints.WEST, GridBagConstraints.BOTH, new Insets(0,
                        0, 0, 0), 0, 0));
        return scroll;
    }

    public void updateAttributeInformationValue(String attribute) {
        Attribute attr;
        StringBuilder info = new StringBuilder();
        StringBuilder ancestorString = new StringBuilder();
        if (struct.getBasicAttributes().containsKey(attribute)) {
            attr = struct.getBasicAttributes().get(attribute);
        } else if (struct.getSkills().containsKey(attribute)) {
            attr = struct.getSkills().get(attribute);
            Skill skill = (Skill) attr;
            ArrayList<BasicAttribute> ancestors = skill.getAncestors();
            HashMap<String, Integer> ancestorMap = new HashMap<String, Integer>();
            int i;
            for (i = 0; i < ancestors.size(); i++) {
                String ancestorName = ancestors.get(i).getName();
                if (ancestorMap.containsKey(ancestorName)) {
                    ancestorMap.put(ancestorName,
                            ancestorMap.get(ancestorName) + 1);
                } else {
                    ancestorMap.put(ancestorName, 1);
                }
            }
            //ancestorString.append("Influencing attributes:\n");
            ancestorString.append("Vaikuttavat ominaisuudet:\n");
            for (String anc : ancestorMap.keySet()) {
                ancestorString.append(String.format("%s: %.0f%%\n", anc,
                        (double) ancestorMap.get(anc)*100 / i));
            }
        } else {
            return;
        }
        info.append(attribute + ":\n");
        info.append("Hinta: " + (double) ((int) (attr.getPrice() * 10)) / 10
                + "\n");
        info
                .append("Kasvata: "
                        + (Double.toString((double) (int) (struct.PointsReqToIncrease(attribute, 1) * 10 + 0.0000001) / 10))
                        + "\n");
        info.append("Vï¿½hennï¿½: "
                + (Double.toString((double) (int) (-1
                        * struct.PointsReqToIncrease(attribute, -1) * 10 + 0.0000000001) / 10)) + "\n");
        attributeInformation.setText(info.toString());
        attributeInformation2.setText(ancestorString.toString());
    }

    private JPanel createInventory() {
        JPanel inventoryContainer = new JPanel();
        inventoryContainer.setOpaque(false);
        inventoryContainer.setLayout(new GridBagLayout());
        inventoryContainer.setBorder(BorderFactory.createTitledBorder(BorderFactory
                .createLineBorder(fontColor), "Inventory", TitledBorder.LEFT,
                TitledBorder.TOP, titleFont, fontColor));
        inventoryPanel = new JPanel();
        ItemLayout layout = new ItemLayout();
        inventoryPanel.setLayout(layout);
        inventoryPanel.setOpaque(false);
        JLabel nameLabel = new JLabel("Esine");
        nameLabel.setFont(boldFont);
        nameLabel.setForeground(fontColor);
        JLabel amountLabel = new JLabel("Mï¿½ï¿½rï¿½"); 
        amountLabel.setFont(boldFont);
        amountLabel.setForeground(fontColor);
        JLabel priceLabel = new JLabel("Hinta");
        priceLabel.setFont(boldFont);
        priceLabel.setForeground(fontColor);
        inventoryPanel.add(nameLabel, new GridBagConstraints(0, 0,
                1, 1, 0, 0, GridBagConstraints.NORTHWEST,
                GridBagConstraints.HORIZONTAL, new Insets(0, 3, 0, 3), 0, 0));
        inventoryPanel.add(priceLabel, new GridBagConstraints(1, 0,
                1, 1, 0, 0, GridBagConstraints.NORTHWEST,
                GridBagConstraints.HORIZONTAL, new Insets(0, 3, 0, 3), 0, 0));
        inventoryPanel.add(amountLabel, new GridBagConstraints(2, 0,
                1, 1, 0, 0, GridBagConstraints.NORTHWEST,
                GridBagConstraints.HORIZONTAL, new Insets(0, 3, 0, 3), 0, 0));
        layout.nextRow();
        for (String item : struct.getInventory().getItemMap().keySet()) {
            createItemInfoContainer(item);
        }
        inventoryContainer.add(inventoryPanel, new GridBagConstraints(0,0,1,1, 1.0,1.0,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0,0,0,0), 0,0));
        addItemButton = new JButton();
        addItemButton.setForeground(fontColor);
        addItemButton.setFont(font);
        addItemButton.setPreferredSize(new Dimension(fontSize * 2, (int)(fontSize*1.5)));
        addItemButton.addActionListener(this);
        ImageIcon buttonIcon = new ImageIcon("largeButton.JPG");
        addItemButton.setIcon(buttonIcon);
        newItemNameField = new JTextField();
        newItemNameField.setFont(font);
        newItemNameField.setOpaque(false);
        newItemNameField.setForeground(fontColor);
        newItemNameField.setColumns(8);
        newItemAmountField = new JTextField();
        newItemAmountField.setFont(font);
        newItemAmountField.setOpaque(false);
        newItemAmountField.setForeground(fontColor);
        newItemAmountField.setColumns(2);
        newItemPriceField = new JTextField();
        newItemPriceField.setFont(font);
        newItemPriceField.setOpaque(false);
        newItemPriceField.setForeground(fontColor);
        newItemPriceField.setColumns(2);
        JPanel newItemPanel = new JPanel();
        newItemPanel.setOpaque(false);
        newItemPanel.add(newItemNameField);
        newItemPanel.add(newItemPriceField);
        newItemPanel.add(newItemAmountField);
        newItemPanel.add(addItemButton);
        inventoryContainer.add(newItemPanel, new GridBagConstraints(0,1,1,1, 1.0,1.0,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0,0,0,0), 0,0));
        return inventoryContainer;
    }

    private void addNewItem() {
        String item = newItemNameField.getText();
        if (!item.trim().equals("")) {
            newItemNameField.setText("");
            int amount;
            try {
                amount = Integer.parseInt(newItemAmountField.getText());
            } catch (NumberFormatException e) {
                amount = 1;
            }
            newItemAmountField.setText("");
            double price;
            try {
                price = Double.parseDouble(newItemPriceField.getText());
            } catch (NumberFormatException e) {
                price = 0;
            }
            newItemPriceField.setText("");
            boolean created = struct.getInventory().addItem(item, amount, price);
            if(created) {
                createItemInfoContainer(item);
            } else {
                itemContainers.get(item).changeAmount(amount);
            }
            
            inventoryPanel.updateUI();
            ItemLayout layout = (ItemLayout) inventoryPanel.getLayout();
            layout.nextRow();
        }
    }
    
    private void createItemInfoContainer(String item) {
        JTextField nameField = new JTextField();
        nameField.setOpaque(false);
        nameField.setFont(font);
        nameField.setForeground(fontColor);
        nameField.setEditable(false);
        nameField.setBorder(BorderFactory.createEmptyBorder());
        JTextField amountField = new JTextField();
        amountField.setOpaque(false);
        amountField.setFont(font);
        amountField.setForeground(fontColor);
        amountField.setEditable(false);
        amountField.setBorder(BorderFactory.createEmptyBorder());
        JTextField priceField = new JTextField();
        priceField.setOpaque(false);
        priceField.setFont(font);
        priceField.setForeground(fontColor);
        priceField.setEditable(false);
        priceField.setBorder(BorderFactory.createEmptyBorder());
        ItemInfoContainer infoCont = new ItemInfoContainer(item, struct.getInventory(), nameField, amountField, priceField);
        itemContainers.put(item, infoCont);
        inventoryPanel.add(nameField, new GridBagConstraints(0, 0,
                1, 1, 0, 0, GridBagConstraints.NORTHWEST,
                GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0));
        inventoryPanel.add(priceField, new GridBagConstraints(1, 0,
                1, 1, 0, 0, GridBagConstraints.NORTHWEST,
                GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0));
        inventoryPanel.add(amountField, new GridBagConstraints(2, 0,
                1, 1, 0, 0, GridBagConstraints.NORTHWEST,
                GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0));
        ItemLayout layout = (ItemLayout)inventoryPanel.getLayout();
        layout.nextRow();
    }
    
    public Font getFont() {
        return font;
    }
    
    public int getFontSize() {
    	return this.fontSize;
    }
    
    public Color getFontColor() {
        return fontColor;
    }

    public void windowActivated(WindowEvent arg0) {
        // TODO Auto-generated method stub

    }

    public void windowDeactivated(WindowEvent arg0) {
        // TODO Auto-generated method stub

    }

    public void windowDeiconified(WindowEvent arg0) {
        // TODO Auto-generated method stub

    }

    public void windowIconified(WindowEvent arg0) {
        // TODO Auto-generated method stub

    }

    public void windowOpened(WindowEvent arg0) {
        // TODO Auto-generated method stub

    }

}
