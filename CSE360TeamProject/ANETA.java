//package CSE360TeamProject;
import java.awt.geom.AffineTransform.*;
import java.awt.geom.AffineTransform;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.*;
import javafx.scene.layout.Border;

import java.awt.event.*;
import java.util.HashMap;

public class ANETA extends GUIApp {
	/**
	 * Launch the application.
	 */

	// public static void main(String [] args) {
	// 	ANETA mainProgram = new ANETA();
	// 	mainProgram.setVisible(true);
	// }

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ANETA frame = new ANETA();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/*
	 * Create the frame.
	 */
	public ANETA() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1234, 511);
		mainContentPanel = new JPanel();
		mainContentPanel.setBackground(new Color(255, 255, 255));
		mainContentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(mainContentPanel);
		mainContentPanel.setLayout(null);
		
		/*
		 * LayeredPane:
		 * contains several JPanels (CreatePanel, this.pathsFoundPanel, RecordPanel, HelpPanel, this.aboutPanel)
		 */
		JLayeredPane layeredPane = new JLayeredPane();
		layeredPane.setBackground(new Color(255, 255, 255));
		layeredPane.setBorder(new LineBorder(new Color(0, 102, 102), 6));
		layeredPane.setBounds(190, 78, 1038, 405);
		mainContentPanel.add(layeredPane);
		layeredPane.setLayout(new CardLayout(0, 0));
		
		/* CreatePanel: contains input Panel and inputRecord Panel
		 * 	- input Panel: allow user to enter networkName, activityName, duration, dependency
		 *  - inputRecord Panel: show the network diagram and allow user to perform "Clean", "Undo" or "Process" actions
		 *  
		 *  To do: create storeInput function/class, clean function, undo function, drawDiagram class
		 *  To do: add listener to Enter Button, Clean Button, Undo Button, Process Button to perform the actions
		 *  To do: add listener to inputRecord textArea to show the diagram
		 *  To do: add listener to show error messages when user enters wrong data
		 */

		this.createPanel = this.addPanel(layeredPane, null, new Color(0, 102, 102), true, 0, 0, 0, 0);

		// Initialize input fields
		this.initializeActionController();

		/*
		 * this.pathsFoundPanel: show all paths found after user clicks "Process" Button
		 * 
		 * To do: create a printPath function
		 * To do: add listener to textArea_PathsFound to print all paths
		 */
		this.pathsFoundPanel.setBackground(new Color(255, 255, 255));
		layeredPane.add(this.pathsFoundPanel);
		this.pathsFoundPanel.setLayout(null);
		
		JLabel pathsFound_title = new JLabel("Paths in Network");
		pathsFound_title.setHorizontalAlignment(SwingConstants.CENTER);
		pathsFound_title.setFont(new Font("Lucida Grande", Font.PLAIN, 17));
		pathsFound_title.setBounds(449, 22, 153, 35);
		this.pathsFoundPanel.add(pathsFound_title);
		
		scrollPane_PathsFound.setBounds(21, 70, 857, 298);
		this.pathsFoundPanel.add(scrollPane_PathsFound);
		JButton modify_action_button = this.addButton("Modify Action", this.pathsFoundPanel, "/CSE360TeamProject/Icons/icons8-undo-26.png", null, 893, 80, 123, 39);
		modify_action_button.addActionListener(new kModifyNodeAction(this));

		JButton show_critical_only_button = this.addButton("Critical Only", this.pathsFoundPanel, "/CSE360TeamProject/Icons/icons8-undo-26.png", null, 893, 140, 123, 39);
		show_critical_only_button.addActionListener(new kCriticalOnlyAction(this, show_critical_only_button));
		
		JButton save_file_button = this.addButton("Save to file", this.pathsFoundPanel, "/CSE360TeamProject/Icons/icons8-undo-26.png", null, 893, 200, 123, 39);
		save_file_button.addActionListener(new kSaveFileAction(this));
		/*
		 * RecordPanel: show network diagram record
		 * 
		 * To do: add Jcheckbox to textArea_networkRecord to show record after user clicks "Process" Button
		 * To do: add listener to deleteButton to perform "Delete" action
		 */
		//JPanel RecordPanel = new JPanel();
		this.recordPanel.setBackground(new Color(255, 255, 255));
		layeredPane.add(this.recordPanel);
		this.recordPanel.setLayout(null);
		
		JLabel recordPanel_title = new JLabel("NetWork Diagram Record");
		recordPanel_title.setFont(new Font("Lucida Grande", Font.PLAIN, 15));
		recordPanel_title.setHorizontalAlignment(SwingConstants.CENTER);
		recordPanel_title.setBounds(430, 23, 198, 35);
		this.recordPanel.add(recordPanel_title);
		
		JScrollPane scrollPane_Record = new JScrollPane();
		scrollPane_Record.setBounds(6, 70, 1014, 253);
		this.recordPanel.add(scrollPane_Record);
		
		//JTextArea textArea_networkRecord = new JTextArea();
		this.model = new DefaultTableModel() {
			public boolean isCellEditable(int row, int col) {
				return false;
			}
		};
        model.addColumn("Network Name");
        model.addColumn("Activity Count");
		this.recordTable = new JTable(model);
		this.recordTable.addMouseListener(new kDoubleClickTableview(this));
		scrollPane_Record.setViewportView(recordTable);
		
		JButton deleteButton = new JButton("Delete");
		deleteButton.setIcon(new ImageIcon(ANETA.class.getResource("/CSE360TeamProject/Icons/icons8-trash-32.png")));
		deleteButton.setBounds(465, 345, 117, 42);
		this.recordPanel.add(deleteButton);
		deleteButton.addActionListener(new kDeleteAction(this));
		
		/*
		 * HelpPanel: show some tips about how to use this application
		 */
		//JPanel HelpPanel = new JPanel();
		this.helpPanel.setBackground(new Color(255, 255, 255));
		layeredPane.add(this.helpPanel);
		this.helpPanel.setLayout(null);
		
		JLabel helpPanel_title = new JLabel("Need Help ?");
		helpPanel_title.setHorizontalAlignment(SwingConstants.CENTER);
		helpPanel_title.setFont(new Font("Lucida Grande", Font.PLAIN, 20));
		helpPanel_title.setBounds(225, 13, 151, 43);
		this.helpPanel.add(helpPanel_title);
		
		SearchBar = new JTextField();
		SearchBar.setForeground(new Color(204, 204, 204));
		SearchBar.setText("Search Help");
		SearchBar.setBounds(125, 68, 284, 43);
		this.helpPanel.add(SearchBar);
		SearchBar.setColumns(15);
		
		JButton searchButton = new JButton("");
		searchButton.setIcon(new ImageIcon(ANETA.class.getResource("/CSE360TeamProject/Icons/icons8-search-30.png")));
		searchButton.setBounds(407, 68, 87, 43);
		this.helpPanel.add(searchButton);
		
		JPanel tipsPanel = new JPanel();
		tipsPanel.setBackground(new Color(255, 255, 255));
		tipsPanel.setBounds(17, 115, 1092, 531);
		this.helpPanel.add(tipsPanel);
		tipsPanel.setLayout(null);
		
		JTextField helpOption1 = this.addTextField("How to Create a new Network?", new EmptyBorder(0, 0, 0, 0), tipsPanel, 				   45, 10, 369, 26);
		JTextField helpAnswer1 = this.addTextField("Press \'Create Network\' -> Enter activity name and duration -> Click \'Enter\'", new EmptyBorder(0, 0, 0, 0), tipsPanel, 55, 40, 669, 26);
		JTextField helpOption2 = this.addTextField("Where Can I Find the Paths in Created Network?", new EmptyBorder(0, 0, 0, 0), tipsPanel, 45, 76, 323, 26);
		JTextField helpAnswer2 = this.addTextField("Press \'Record\' -> Click one history name -> Double click", new EmptyBorder(0, 0, 0, 0), tipsPanel, 55, 106, 669, 26);
		JTextField helpOption3 = this.addTextField("What will happen if I click \"Restart\"?", new EmptyBorder(0, 0, 0, 0), tipsPanel, 	   45, 142, 283, 26);
		JTextField helpAnswer3 = this.addTextField("Pressing \'Restart\' button at the left panel will triger erasing process which will remove all history data.", new EmptyBorder(0, 0, 0, 0), tipsPanel, 55, 172, 1269, 26);
		JTextField helpOption4 = this.addTextField("How do I input the 'dependencies' field in 'Create network' panel?", new EmptyBorder(0, 0, 0, 0), tipsPanel, 	   45, 208, 983, 26);
		JTextField helpAnswer4 = this.addTextField("Dependencies is separated by single comma. For example if A is depending on B and C, then we have to type 'B, C' (without single quote) in the field.", new EmptyBorder(0, 0, 0, 0), tipsPanel, 55, 238, 1069, 26);
		helpOption1.setEditable(false);
		helpAnswer1.setEditable(false);
		helpOption2.setEditable(false);
		helpAnswer2.setEditable(false);
		helpOption3.setEditable(false);
		helpAnswer3.setEditable(false);
		helpOption4.setEditable(false);
		helpAnswer4.setEditable(false);
		
		this.addLabel("", "/CSE360TeamProject/Icons/icons8-chevron-right-26.png", null, tipsPanel, null, null, null, 6, 10, 61, 26);
		this.addLabel("", "/CSE360TeamProject/Icons/icons8-chevron-right-26.png", null, tipsPanel, null, null, null, 6, 76, 61, 26);
		this.addLabel("", "/CSE360TeamProject/Icons/icons8-chevron-right-26.png", null, tipsPanel, null, null, null, 6, 142, 61, 26);
		this.addLabel("", "/CSE360TeamProject/Icons/icons8-chevron-right-26.png", null, tipsPanel, null, null, null, 6, 208, 61, 26);
		
		/*
		 * this.aboutPanel: contains two panels (ourApplication Panel and ourMembers Panel)
		 * - ourApplication: show information about this application
		 * - ourMembers Panel: show information about group members
		 */
		//JPanel this.aboutPanel = new JPanel();
		this.aboutPanel.setBackground(new Color(255, 255, 255));
		layeredPane.add(this.aboutPanel);
		this.aboutPanel.setLayout(null);
		
		JLayeredPane layeredPane_About = new JLayeredPane();
		layeredPane_About.setBorder(new EmptyBorder(0, 0, 0, 0));
		layeredPane_About.setBounds(6, 115, 614, 272);
		this.aboutPanel.add(layeredPane_About);
		layeredPane_About.setLayout(new CardLayout(0, 0));
		
		JPanel ourApplication = new JPanel();
		ourApplication.setBorder(new LineBorder(new Color(0, 102, 102), 2));
		ourApplication.setBackground(new Color(255, 255, 255));
		layeredPane_About.add(ourApplication);
		ourApplication.setLayout(null);
		
		JEditorPane About_ourApplication = new JEditorPane();
		About_ourApplication.setText("\n\nANETA (A Network diagram Analyzer) is a network analyzer which provide functionality to \nanalyze network diagram. \n\n\nThis project is aim to facilitate the procedure for “ANY” user who is required to create network based on their requirement dependency.");
		About_ourApplication.setBounds(6, 66, 602, 200);
		ourApplication.add(About_ourApplication);
		
		JLabel ApplicationName = this.addLabel("Project ANETA \n", "", null, ourApplication, null, null, null, 225, 19, 144, 58);
		ApplicationName.setFont(new Font("Lucida Grande", Font.PLAIN, 18));
		
		JPanel ourMembers = new JPanel();
		ourMembers.setBackground(new Color(255, 255, 255));
		ourMembers.setBorder(new LineBorder(new Color(0, 102, 102), 2));
		layeredPane_About.add(ourMembers);

		JEditorPane About_teamMembers = new JEditorPane();
		About_teamMembers.setText("\nCurrent team members: (Sort by last name)\n\nMeng-Ze Chen\nWilliam Kostecki\nLingzhi Zhu\n\nWithdrawn team member:\n\nYueya Ou");
		About_teamMembers.setBounds(6, 66, 602, 200);
		ourMembers.add(About_teamMembers);
		
		JLabel lblAbout = new JLabel("About");
		lblAbout.setFont(new Font("Lucida Grande", Font.PLAIN, 30));
		lblAbout.setBounds(6, 16, 115, 29);
		this.aboutPanel.add(lblAbout);
		
		this.addLabel("", "/CSE360TeamProject/Icons/icons8-monitor-40.png", null, this.aboutPanel, null, null, null, 6, 65, 47, 32);
		
		// when user clicks "Our Application", jump to ourApplication Panel
		txtOurApplication = new JTextField();
		txtOurApplication.setEditable(false);
		txtOurApplication.addMouseListener(new MouseAdapter() 
		{
			@Override
			public void mouseClicked(MouseEvent e) {
				ourApplication.setVisible(true);
				ourMembers.setVisible(false);
				txtOurApplication.setForeground(new Color(0, 102, 102));
				txtOurMembers.setForeground(new Color(0, 0, 0));
				
			}
		});
		txtOurApplication.setText("Our Application");
		txtOurApplication.setBounds(53, 71, 110, 26);
		txtOurApplication.setBorder(new EmptyBorder(0, 0, 0, 0));
		this.aboutPanel.add(txtOurApplication);
		txtOurApplication.setColumns(10);
		
		this.addLabel("", "/CSE360TeamProject/Icons/icons8-group-40.png", null, this.aboutPanel, null, null, null, 169, 65, 61, 32);
		
		// txtOurMembers = this.addTextField("Our Members", new EmptyBorder(0, 0, 0, 0), this.aboutPanel, 212, 71, 130, 26,
		// this, ourMembers, null, false, null, new Color(255, 255, 255));
		// TODO:
		// when user clicks "Our Members", jump to ourMembers Panel
		txtOurMembers = new JTextField();
		txtOurMembers.setEditable(false);
		txtOurMembers.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) 
			{
				ourApplication.setVisible(false);
				ourMembers.setVisible(true);
				txtOurMembers.setForeground(new Color(0, 102, 102));
				txtOurApplication.setForeground(new Color(0, 0, 0));
			}
		});
		txtOurMembers.setText("Our Members");
		txtOurMembers.setBounds(212, 71, 130, 26);
		txtOurMembers.setBorder(new EmptyBorder(0, 0, 0, 0));
		this.aboutPanel.add(txtOurMembers);
		txtOurMembers.setColumns(10);
		
		/*
		 * HeaderPanel: show project name "ANETA"
		 */
		JPanel HeaderPanel = new JPanel();
		HeaderPanel = this.addPanel(this.mainContentPanel, new EmptyBorder(0, 0, 0, 0), new Color(255, 255, 255), true, 0, 0, 834, 79);

		this.addLabel(" ANETA", "/CSE360TeamProject/Icons/icons8-mind-map-75.png", new Font("Apple LiGothic", Font.PLAIN, 38), 
		HeaderPanel, null, new Color(0, 102, 102), null, 6, 0, 259, 79);

		this.addMainMenuPanel();
	}
}
