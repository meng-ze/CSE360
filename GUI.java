import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;
import java.awt.event.*;

public class GUI extends JFrame {
    protected JPanel createPanel = new JPanel();
    protected JPanel pathsFoundPanel = new JPanel();
    protected JPanel recordPanel = new JPanel();
    protected JPanel helpPanel = new JPanel();
    protected JPanel aboutPanel = new JPanel();

    protected JPanel mainContentPanel;
	protected JTextField txtCreateNetwork = new JTextField();
	protected JTextField txtHelp;
	protected JTextField txtRecord;
	protected JTextField txtAbout;
	protected JTextField txtRestart;
	protected JTextField txtPathsFound;
	protected JTextField txtNetworkName = new JTextField();
	protected JTextField txtActivityName = new JTextField();
	protected JTextField txtDuration = new JTextField();
	protected JTextField txtDependency = new JTextField();
	protected JTextField SearchBar;
	protected JTextField txtOurApplication;
	protected JTextField txtOurMembers;

	protected void addSideBarInputField(JPanel parentPanel, JTextField targetField, String name, String iconPath, int x, int y, int width, int height) {
		JLabel targetLabel = new JLabel(name);
		targetLabel.setIcon(new ImageIcon(ANETA.class.getResource(iconPath)));
		targetLabel.setBounds(x, y, width, height);
		parentPanel.add(targetLabel);
		
		targetField.setBounds(28, y+18, 152, height+10);
		parentPanel.add(targetField);
	}

	protected void addLabel(String labelText, String imagePath, Font font, JPanel parentPanel, AbstractBorder border,Color foregroundColor, Color backgroundColor, int x, int y, int width, int height) {
		JLabel tmpLabel = new JLabel(labelText);
		if (imagePath != "") {
			tmpLabel.setIcon(new ImageIcon(ANETA.class.getResource(imagePath)));
		}
		if (font != null) {
			tmpLabel.setFont(font);
		}
		if (border != null) {
			tmpLabel.setBorder(border);;
		}
		if (foregroundColor != null) {
			tmpLabel.setForeground(foregroundColor);
		}
		if (backgroundColor != null) {
			tmpLabel.setBackground(backgroundColor);
		}
		tmpLabel.setBounds(x, y, width, height);
		parentPanel.add(tmpLabel);
	}


	protected JTextField addTextField(String title, AbstractBorder border, JPanel parentPanel, int x, int y, int width, int height) {
		JTextField tmpTextField = new JTextField();
		tmpTextField.setText(title);
		tmpTextField.setBounds(x, y, width, height);
		tmpTextField.setBorder(border);
		tmpTextField.setColumns(10);
		parentPanel.add(tmpTextField);
		return tmpTextField;
	}

	protected JTextField addTextField(String title, AbstractBorder border, JPanel parentPanel, int x, int y, int width, int height,
	GUI guiApp, JPanel targetPanel, Font font, Boolean isEditable, Color foregroundColor, Color backgroundColor) {
		JTextField tmpTextField = new JTextField();
		tmpTextField.setText(title);
		tmpTextField.setBounds(x, y, width, height);
		tmpTextField.setBorder(border);
		tmpTextField.setColumns(10);

		tmpTextField.addMouseListener(new kMouseAdapter(guiApp, targetPanel, tmpTextField));
		if (font != null) {
			tmpTextField.setFont(font);
		}
		tmpTextField.setEditable(isEditable);
		if (foregroundColor != null) {
			tmpTextField.setForeground(foregroundColor);
		}
		if (backgroundColor != null) {
			tmpTextField.setBackground(backgroundColor);
		}
		parentPanel.add(tmpTextField);
		return tmpTextField;
	}
	protected JTextField addTextField(String title, AbstractBorder border, JPanel parentPanel, int x, int y, int width, int height,
	GUI guiApp, JPanel targetPanel, Font font, Boolean isEditable, Boolean extraCommand, Color foregroundColor, Color backgroundColor) {
		JTextField tmpTextField = new JTextField();
		tmpTextField.setText(title);
		tmpTextField.setBounds(x, y, width, height);
		tmpTextField.setBorder(border);
		tmpTextField.setColumns(10);

		tmpTextField.addMouseListener(new kMouseAdapter(guiApp, targetPanel, tmpTextField, true));
		if (font != null) {
			tmpTextField.setFont(font);
		}
		tmpTextField.setEditable(isEditable);
		if (foregroundColor != null) {
			tmpTextField.setForeground(foregroundColor);
		}
		if (backgroundColor != null) {
			tmpTextField.setBackground(backgroundColor);
		}
		parentPanel.add(tmpTextField);
		return tmpTextField;
	}

	protected JPanel addPanel(JComponent parentComponent, AbstractBorder borderType, Color backgroundColor, Boolean resetLayout,
	int x, int y, int width, int height) {

		JPanel tmpPanel = new JPanel();
		if (borderType != null) {
			tmpPanel.setBorder(borderType);
		}
		if (resetLayout) {
			tmpPanel.setLayout(null);
		}
		if (x != 0 || y != 0 || width != 0 || height != 0) {
			tmpPanel.setBounds(x, y, width, height);
		}
		tmpPanel.setBackground(backgroundColor);
		parentComponent.add(tmpPanel);
		return tmpPanel;
	}
}
