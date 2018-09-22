import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;
import java.awt.event.*;

public class kMouseAdapter extends MouseAdapter {
    private ANETA targetApp;
    private JPanel targetPanel;
    public kMouseAdapter(ANETA targetApp, JPanel target) {
        this.targetApp = targetApp;
        this.targetPanel = target;
    }

    @Override
    public void mouseClicked(MouseEvent e) {

        this.targetApp.createPanel.setVisible(false);
        this.targetApp.pathsFoundPanel.setVisible(false);
        this.targetApp.recordPanel.setVisible(false);
        this.targetApp.helpPanel.setVisible(false);
        this.targetApp.aboutPanel.setVisible(false);
        this.targetPanel.setVisible(true);

        this.targetApp.txtCreateNetwork.setForeground(new Color(0, 0, 0));
        this.targetApp.txtRecord.setForeground(new Color(0, 0, 0));
        this.targetApp.txtHelp.setForeground(new Color(0, 0, 0));
        this.targetApp.txtAbout.setForeground(new Color(0, 0, 0));
        this.targetApp.txtRestart.setForeground(new Color(0, 0, 0));
        this.targetApp.txtPathsFound.setForeground(new Color(0, 102, 102));
    }
}
		