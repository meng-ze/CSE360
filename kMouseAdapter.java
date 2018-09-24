import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;
import java.awt.event.*;

public class kMouseAdapter extends MouseAdapter {
    private GUI targetApp;
    private JPanel targetPanel;
    private JTextField targetTextfield;
    private Boolean extraConfirmation = false;

    public kMouseAdapter(GUI targetApp, JPanel target, JTextField targetTextfield) {
        this.targetApp = targetApp;
        this.targetPanel = target;
        this.targetTextfield = targetTextfield;
    }

    public kMouseAdapter(GUI targetApp, JPanel target, JTextField targetTextfield, Boolean confirmation) {
        this.targetApp = targetApp;
        this.targetPanel = target;
        this.targetTextfield = targetTextfield;
        this.extraConfirmation = confirmation;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (this.extraConfirmation) {
            JOptionPane.showConfirmDialog(null, "Do you want to reset this application?", "WARNING", JOptionPane.YES_NO_CANCEL_OPTION);
        } else {
            this.targetApp.createPanel.setVisible(false);
            this.targetApp.pathsFoundPanel.setVisible(false);
            this.targetApp.recordPanel.setVisible(false);
            this.targetApp.helpPanel.setVisible(false);
            this.targetApp.aboutPanel.setVisible(false);
            this.targetPanel.setVisible(true);
        }

        this.targetApp.txtCreateNetwork.setForeground(new Color(0, 0, 0));
        this.targetApp.txtRecord.setForeground(new Color(0, 0, 0));
        this.targetApp.txtHelp.setForeground(new Color(0, 0, 0));
        this.targetApp.txtAbout.setForeground(new Color(0, 0, 0));
        this.targetApp.txtRestart.setForeground(new Color(0, 0, 0));
        this.targetApp.txtPathsFound.setForeground(new Color(0, 0, 0));
        this.targetTextfield.setForeground(new Color(0, 102, 102));
    }
}
		