package GUI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import charInformation.Attribute;
import charInformation.Structure;



public class AttributeContainer extends JPanel implements MouseListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Attribute attribute;
	private JLabel name;
	private ValueField value;
	private PointsField points;
	private MainWindow main;

	
	public AttributeContainer(Attribute attribute, int indent,MainWindow main) {
		super();
		this.attribute = attribute;
		this.main = main;
		this.setOpaque(false);
		this.setLayout(new GridBagLayout());
		name = new JLabel(attribute.getName());
		Font font = main.getFont();
		int fontSize = main.getFontSize();
		Structure struct = main.getStructure();

		Color fontColor = getCorrectColor();
		
		name.setFont(main.getFont());
		name.setForeground(fontColor);
		JPanel indentPanel = new JPanel();
		indentPanel.setOpaque(false);
		indentPanel.setPreferredSize(new Dimension(indent * 30, fontSize));
		this.add(indentPanel);
		this.add(name, new GridBagConstraints(1, 0, 1, 1, 1.0, 1.0,
            GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0,
                    0, 0, 0), 0, 0));
		value = new ValueField(attribute);
		value.setFont(font);
		value.setForeground(fontColor);
		value.setPreferredSize(new Dimension(fontSize * 2, fontSize + 2));
    //valueFields.add(value);
		this.add(value);
		JButton decreaseByOne = new JButton();
		decreaseByOne.setOpaque(false);
		decreaseByOne.addActionListener(new IncreaseListener(false, struct,
            attribute.getName(), main));
		decreaseByOne.setPreferredSize(new Dimension(fontSize, fontSize));
		JButton increaseByOne = new JButton();
		increaseByOne.setOpaque(false);
		increaseByOne.setPreferredSize(new Dimension(fontSize, fontSize));
		increaseByOne.addActionListener(new IncreaseListener(true, struct,
            attribute.getName(), main));
		ImageIcon increaseIcon = new ImageIcon("IncreaseButton.JPG");
		ImageIcon decreaseIcon = new ImageIcon("decreaseIcon.JPG");
		increaseByOne.setBorder(BorderFactory.createEmptyBorder());
		increaseByOne.setIcon(increaseIcon);
		decreaseByOne.setBorder(BorderFactory.createEmptyBorder());
		decreaseByOne.setIcon(decreaseIcon);
		this.add(increaseByOne);
		this.add(decreaseByOne);
		points = new PointsField(attribute);
		points.setForeground(fontColor);
		//pointsFields.add(points);
		this.add(points);
		this.addMouseListener(new AttributeInfoListener(attribute
            .getName(), main));
		this.addMouseListener(this);
	}
	
	public void updateValue() {
		this.value.setText(new Integer(this.attribute.getValue()).toString());
	}
	
	public void updateColor() {
		Color newColor = getCorrectColor();
		updateColor(newColor);
	}
	
	public void updateColor(Color color) {
		this.name.setForeground(color);
		this.value.setForeground(color);
		this.points.setForeground(color);
	}
	
	private Color getCorrectColor() {
		Color fontColor;
		Color weakColor = main.getWeakColor();
		Color averageColor = main.getAverageColor();
		Color strongColor = main.getStrongColor();
		if(attribute.getValue() <=4 ) {
			fontColor = weakColor;
		} else if(attribute.getValue() <=6){
			fontColor = averageColor;
		} else {
			fontColor = strongColor;
		}
		return fontColor;
	}

	public void updatePoints() {
		//this.points.setText(new Integer((int)this.attribute.getPoints()).toString());
		this.points.updateValue();
	}

	public void updateAll() {
		updateValue();
		updatePoints();
		updateColor();
	}

	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	public void mouseEntered(MouseEvent e) {
		updateColor(main.getMoseOverColor());
	}

	public void mouseExited(MouseEvent e) {
		updateColor();
		
	}

	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

}
