//package CSE360TeamProject;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;

import javafx.scene.layout.Border;

import java.awt.event.*;

public class ANETA extends GUIApp {
	/**
	 * Launch the application.
	 */
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
		setBounds(100, 100, 834, 511);
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
		layeredPane.setBounds(190, 78, 638, 405);
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

		/*
		 * InputsRecord Panel
		 */
		JPanel inputsRecord = this.addPanel(this.createPanel, new LineBorder(new Color(0, 102, 102), 2), null, true, 227, 6, 393, 381);
		JLabel inputRecord_title = this.addLabel("Network Diagram", "", new Font("Lucida Grande", Font.PLAIN, 16), inputsRecord, null, null, new Color(255, 255, 255), 132, 6, 138, 30);
		inputRecord_title.setHorizontalAlignment(SwingConstants.CENTER);

		JScrollPane scrollPane_inputRecord = new JScrollPane();
		scrollPane_inputRecord.setBounds(6, 39, 381, 285);
		inputsRecord.add(scrollPane_inputRecord);
		JTextArea textArea_inputRecord = new JTextArea();
		scrollPane_inputRecord.setViewportView(textArea_inputRecord);
		JButton cleanButton = new JButton("Clean");
		cleanButton.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				JOptionPane.showConfirmDialog(null, "Do you want to clean the entire network diagram?", "WARNING", JOptionPane.YES_NO_CANCEL_OPTION);
			}
		});
		cleanButton.setIcon(new ImageIcon(ANETA.class.getResource("/CSE360TeamProject/Icons/icons8-disposal-32-2.png")));
		cleanButton.setBounds(141, 336, 117, 39);
		inputsRecord.add(cleanButton);
		
		JButton processButton = new JButton("Process");
		processButton.setIcon(new ImageIcon(ANETA.class.getResource("/CSE360TeamProject/Icons/icons8-checkmark-26.png")));
		processButton.setBounds(270, 336, 117, 39);
		inputsRecord.add(processButton);
		
		JButton undoButton = new JButton("Undo");
		undoButton.setIcon(new ImageIcon(ANETA.class.getResource("/CSE360TeamProject/Icons/icons8-undo-26.png")));
		undoButton.setBounds(6, 336, 123, 39);
		inputsRecord.add(undoButton);
		
		
		/*
		 * InputPanel
		 */
		
		JPanel inputPanel = new JPanel();
		inputPanel.setBounds(0, 0, 626, 393);
		inputPanel.setBorder(new EmptyBorder(0, 0, 0, 0));
		inputPanel.setBackground(new Color(255, 255, 255));
		inputPanel.setLayout(null);
		this.createPanel.add(inputPanel);
		
		// Add middle side bar components
		addSideBarInputField(inputPanel, this.txtNetworkName, "Network Name", "/CSE360TeamProject/Icons/icons8-chevron-right-26.png", 6, 65, 132, 16);
		addSideBarInputField(inputPanel, this.txtActivityName, "Activity Name", "/CSE360TeamProject/Icons/icons8-chevron-right-26.png", 6, 120, 132, 16);
		addSideBarInputField(inputPanel, this.txtDuration, "Duration", "/CSE360TeamProject/Icons/icons8-chevron-right-26.png", 6, 176, 132, 16);
		addSideBarInputField(inputPanel, this.txtDependency, "Dependency", "/CSE360TeamProject/Icons/icons8-chevron-right-26.png", 6, 232, 132, 16);
		
		// "Enter" Button
		JButton enterButton = new JButton("Enter");
		enterButton.setBounds(41, 315, 117, 29);
		inputPanel.add(enterButton);
		
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
		pathsFound_title.setBounds(249, 22, 153, 35);
		this.pathsFoundPanel.add(pathsFound_title);
		
		JScrollPane scrollPane_PathsFound = new JScrollPane();
		scrollPane_PathsFound.setBounds(21, 70, 587, 298);
		this.pathsFoundPanel.add(scrollPane_PathsFound);
		
		JTextArea textArea_PathsFound = new JTextArea();
		textArea_PathsFound.setEditable(false);
		textArea_PathsFound.setLineWrap(true);
		scrollPane_PathsFound.setViewportView(textArea_PathsFound);
		
		/*
		 * RecordPanel: show network diagram record
		 * 
		 * To do: add Jcheckbox to textArea_networkRecord to show record after user clicks "Process" Button
		 * To do: add listener to beleteButton to perform "Delete" action
		 */
		//JPanel RecordPanel = new JPanel();
		this.recordPanel.setBackground(new Color(255, 255, 255));
		layeredPane.add(this.recordPanel);
		this.recordPanel.setLayout(null);
		
		JLabel recordPanel_title = new JLabel("NetWork Diagram Record");
		recordPanel_title.setFont(new Font("Lucida Grande", Font.PLAIN, 15));
		recordPanel_title.setHorizontalAlignment(SwingConstants.CENTER);
		recordPanel_title.setBounds(221, 23, 198, 35);
		this.recordPanel.add(recordPanel_title);
		
		JScrollPane scrollPane_Record = new JScrollPane();
		scrollPane_Record.setBounds(6, 70, 614, 253);
		this.recordPanel.add(scrollPane_Record);
		
		JTextArea textArea_networkRecord = new JTextArea();
		scrollPane_Record.setViewportView(textArea_networkRecord);
		
		JButton beleteButton = new JButton("Delete");
		beleteButton.setIcon(new ImageIcon(ANETA.class.getResource("/CSE360TeamProject/Icons/icons8-trash-32.png")));
		beleteButton.setBounds(265, 345, 117, 42);
		this.recordPanel.add(beleteButton);
		
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
		tipsPanel.setBounds(17, 145, 592, 231);
		this.helpPanel.add(tipsPanel);
		tipsPanel.setLayout(null);
		
		addTextField("How to Create a new Network?", new EmptyBorder(0, 0, 0, 0), tipsPanel, 45, 20, 369, 26);
		addTextField("Where Can I Find the Paths in Created Network?", new EmptyBorder(0, 0, 0, 0), tipsPanel, 45, 58, 323, 26);
		addTextField("What will happen if I click \"Restart\"?", new EmptyBorder(0, 0, 0, 0), tipsPanel, 45, 96, 283, 26);
		
		this.addLabel("", "/CSE360TeamProject/Icons/icons8-chevron-right-26.png", null, tipsPanel, null, null, null, 6, 20, 61, 26);
		this.addLabel("", "/CSE360TeamProject/Icons/icons8-chevron-right-26.png", null, tipsPanel, null, null, null, 6, 63, 61, 21);
		this.addLabel("", "/CSE360TeamProject/Icons/icons8-chevron-right-26.png", null, tipsPanel, null, null, null, 6, 96, 61, 26);
		
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
		About_ourApplication.setText("\n\nis a network analyzer which provide functionality to analyze network diagram. \n\n\nThis project is aim to facilitate the procedure for “ANY” user who is required to create network based on their requirement dependency.");
		About_ourApplication.setBounds(6, 66, 602, 200);
		ourApplication.add(About_ourApplication);
		
		JLabel ApplicationName = this.addLabel("Project ANETA \n", "", null, ourApplication, null, null, null, 225, 19, 144, 58);
		ApplicationName.setFont(new Font("Lucida Grande", Font.PLAIN, 18));
		
		JPanel ourMembers = new JPanel();
		ourMembers.setBackground(new Color(255, 255, 255));
		ourMembers.setBorder(new LineBorder(new Color(0, 102, 102), 2));
		layeredPane_About.add(ourMembers);
		
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
		txtOurMembers.addMouseListener(new MouseAdapter() 
		{
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

		this.addMainMenuPanel(this);
	}
}
