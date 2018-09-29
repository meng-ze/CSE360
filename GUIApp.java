import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;
import java.awt.event.*;

public class GUIApp extends GUI {
	private JLabel numberRecord;

    protected void addMainMenuPanel(GUI guiProgram) {
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
}