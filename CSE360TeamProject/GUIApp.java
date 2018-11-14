import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.table.*;
import java.awt.geom.AffineTransform.*;
import java.awt.geom.AffineTransform;

public class GUIApp extends GUI {
	private JLabel numberRecord;
	public int graphNumber = 1;

	protected JTextField txtNetworkName = new JTextField();
	protected JTextField txtActivityName = new JTextField();
	protected JTextField txtDuration = new JTextField();
	protected JTextField txtDependency = new JTextField();
	protected JTextArea textArea_inputRecord;
	protected JScrollPane scrollPane_PathsFound = new JScrollPane();

	public HashMap<String, Tree> historyNetworks = new HashMap<String, Tree>();

	public ArrayList<Node> nodeList = new ArrayList<Node>();
	public HashMap<String, Node> nodeMaps = new HashMap<String, Node>();
	protected DefaultTableModel model;
	protected JTable recordTable;
	public String displayingTreeName;
	protected Boolean drawCriticalOnly = false;

	public void resetAllNodes() {
		System.out.printf("Reset All Nodes\n");
		this.nodeList.clear();
		this.nodeMaps.clear();
		this.updateTextRecord();
		this.txtNetworkName.setText("Network " + this.graphNumber);
	}

	public void navigateToPathsFoundPanel() {
		System.out.printf("Fake mouse click\n");
		MouseEvent tmpMouseEvent = new MouseEvent(this.txtPathsFound, this.txtPathsFound.CENTER , 0, 0, 0, 0, 1, false);
		MouseListener ms = this.txtPathsFound.getMouseListeners()[this.txtPathsFound.getMouseListeners().length-1];
		ms.mouseClicked(tmpMouseEvent);
	}

	public void updateRecordsLabel() {
		System.out.printf("Path Found: %s\n", historyNetworks.size());
		this.numberRecord.setText(""+historyNetworks.size());
	}

	public void updateTextRecord() {
		this.textArea_inputRecord.setEditable(true);
		this.textArea_inputRecord.setText("");
		String tmpString = "";
		for (Node node: this.nodeList) {
			tmpString += "Activity name:\t";
			tmpString += node.name;
			tmpString += "\tDuration:\t";
			tmpString += node.duration;
			tmpString += "\tDependencies:\t";
			tmpString += node.dependencies_key;
			tmpString += "\n";
		}
		this.textArea_inputRecord.setText(tmpString);
		this.textArea_inputRecord.setEditable(false);
	}
    public void addNodeIntoNodeList(String nodeName, ArrayList<String> dependencies, String durationStr) {
        if (nodeName.isEmpty() || nodeName == null) {
            JOptionPane.showConfirmDialog(null, "Please input node name!", "WARNING", JOptionPane.DEFAULT_OPTION);
            return;
        }
        if (!GUIApp.isInt(durationStr)) {
			JOptionPane.showConfirmDialog(null, "Please input Integer value in duration field!", "WARNING", JOptionPane.DEFAULT_OPTION);
			this.txtDuration.setText("");
            return;
		}
		if (this.nodeMaps.containsKey(nodeName)) {
            JOptionPane.showConfirmDialog(null, "Node: \"" + nodeName + "\" is already exist!\n Try using other name", "WARNING", JOptionPane.DEFAULT_OPTION);
			this.txtActivityName.setText("");
            return;
		}
		Node newNode = new Node(nodeName, Integer.parseInt(durationStr));
		for (String key: dependencies) {
			newNode.dependencies_key.add(key);
		}
		this.nodeMaps.put(nodeName, newNode);
		this.nodeList.add(newNode);
		this.resetInputFields();
		this.updateTextRecord();
	}

	private void clearTextField() {
		this.txtActivityName.setText("");
		this.txtDuration.setText("");
		this.txtDependency.setText("");
	}

	protected void initializeActionController() {
		JPanel inputsRecord = this.addPanel(this.createPanel, new LineBorder(new Color(0, 102, 102), 2), null, true, 227, 6, 793, 381);
		JLabel inputRecord_title = this.addLabel("Network Diagram", "", new Font("Lucida Grande", Font.PLAIN, 16), inputsRecord, null, new Color(255, 255, 255), null, 335, 6, 138, 30);
		inputRecord_title.setHorizontalAlignment(SwingConstants.CENTER);

		JScrollPane scrollPane_inputRecord = new JScrollPane();
		scrollPane_inputRecord.setBounds(6, 39, 781, 285);
		inputsRecord.add(scrollPane_inputRecord);
		textArea_inputRecord = new JTextArea();
		scrollPane_inputRecord.setViewportView(textArea_inputRecord);

		JButton cleanButton = this.addButton("Clean", inputsRecord, "/CSE360TeamProject/Icons/icons8-disposal-32-2.png", null, 141, 336, 117, 39); 
		cleanButton.addActionListener(new kCleanPopup("Do you want to clean the entire network diagram?", this));

		JButton undoButton = this.addButton("Undo", inputsRecord, "/CSE360TeamProject/Icons/icons8-undo-26.png", null, 6, 336, 123, 39);
		undoButton.addActionListener(new kUndoAction(this));
		JButton analyzeButton = this.addButton("Process", inputsRecord, "/CSE360TeamProject/Icons/icons8-checkmark-26.png", null, 270, 336, 117, 39);
		analyzeButton.addActionListener(new kAnalyzeGraphAction(this));
		
		/*
		 * InputPanel
		 */
		JPanel inputPanel = this.addPanel(this.createPanel, null, new Color(255, 255, 255), true, 0, 0, 1026, 393);
		
		// Add middle side bar components
		this.addSideBarInputField(inputPanel, this.txtNetworkName, "Network Name", "/CSE360TeamProject/Icons/icons8-chevron-right-26.png", 6, 65, 132, 16);
		this.addSideBarInputField(inputPanel, this.txtActivityName, "Activity Name", "/CSE360TeamProject/Icons/icons8-chevron-right-26.png", 6, 120, 132, 16);
		this.addSideBarInputField(inputPanel, this.txtDuration, "Duration", "/CSE360TeamProject/Icons/icons8-chevron-right-26.png", 6, 176, 132, 16);
		this.addSideBarInputField(inputPanel, this.txtDependency, "Dependency", "/CSE360TeamProject/Icons/icons8-chevron-right-26.png", 6, 232, 132, 16);
		this.txtNetworkName.setText("Network " + this.graphNumber);
		this.txtNetworkName.setEditable(false);
		
		
		// "Enter" Button
		JButton enterButton = this.addButton("Enter", inputPanel, "", null, 41, 315, 117, 29);
		enterButton.addActionListener(new kInsertNodeAction(this));
	}

    protected void addMainMenuPanel() {
		JPanel mainMenuPanel = this.addPanel(mainContentPanel, new EmptyBorder(0, 0, 0, 0), new Color(255, 255, 255), true, 0, 78, 177, 411);
	
		txtCreateNetwork = this.addTextField("Create Network", new EmptyBorder(0, 0, 0, 0), mainMenuPanel, 66, 76, 120, 37, 
		this, this.createPanel, new Font("Lucida Grande", Font.PLAIN, 13), false, 
		new Color(0, 102, 102), new Color(255, 255, 255));

		this.addLabel("", "/CSE360TeamProject/Icons/icons8-create-36.png", null, mainMenuPanel, null, null, null, 24, 71, 90, 37);
		this.addLabel("", "/CSE360TeamProject/Icons/icons8-save-close-35.png", null, mainMenuPanel, null, null, null, 22, 164, 61, 40);
		
		this.numberRecord = this.addLabel("0", "", null, mainMenuPanel, null, null, null, 96, 158, 48, 26);
		this.numberRecord.setHorizontalAlignment(SwingConstants.CENTER);
		
		this.addLabel("", "/CSE360TeamProject/Icons/icons8-hard-to-find-40.png", null, mainMenuPanel, null, null, null, 19, 118, 61, 37);
		txtPathsFound = this.addTextField("Paths Found", new EmptyBorder(0, 0, 0, 0), mainMenuPanel, 67, 125, 130, 26,
		this, this.pathsFoundPanel, null, false, null, new Color(255, 255, 255));

		this.addLabel("", "/CSE360TeamProject/Icons/icons8-filled-circle-27.png", null, mainMenuPanel, null, null, null, 107, 153, 66, 37);
		txtRecord = this.addTextField("Record", new EmptyBorder(0, 0, 0, 0), mainMenuPanel, 67, 167, 48, 37,
		this, this.recordPanel, null, false, null, new Color(255, 255, 255));
	
		this.addLabel("", "/CSE360TeamProject/Icons/icons8-idea-40-2.png", null, mainMenuPanel, null, null, null, 19, 264, 90, 40);
		txtHelp = this.addTextField("Help", new EmptyBorder(0, 0, 0, 0), mainMenuPanel, 67, 267, 82, 37,
		this, this.helpPanel, null, false, null, new Color(255, 255, 255));
	
		this.addLabel("", "/CSE360TeamProject/Icons/icons8-question-mark-35-2.png", null, mainMenuPanel, null, null, null, 19, 319, 61, 40);
		txtAbout = this.addTextField("About", new EmptyBorder(0, 0, 0, 0), mainMenuPanel, 67, 328, 130, 26,
		this, this.aboutPanel, null, false, null, new Color(255, 255, 255));
	
		this.addLabel("", "/CSE360TeamProject/Icons/icons8-synchronize-40-2.png", null, mainMenuPanel, null, null, null, 19, 216, 61, 37);
		txtRestart = this.addTextField("Restart", new EmptyBorder(0, 0, 0, 0), mainMenuPanel, 66, 223, 130, 26,
		this, this.aboutPanel, null, false, true, null, new Color(255, 255, 255));
	}

	public static Boolean isInt(String s) {
        try { 
            Integer.parseInt(s); 
        } catch (NullPointerException nulExcept) {
            return false;
        } catch (NumberFormatException formatExcept) { 
            return false; 
        }
        // only got here if we didn't return false
		return true;
	}
	
	private void resetInputFields() {
		this.txtActivityName.setText("");
		this.txtDuration.setText("");
		this.txtDependency.setText("");
	}
}