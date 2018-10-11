import javax.swing.*;
import java.awt.event.*;

public class kCleanPopup implements ActionListener {
    private String warningMessage;
    private GUIApp app;
    public kCleanPopup(String warningMessage, GUIApp app) {
        this.warningMessage = warningMessage;
        this.app = app;
    }
    public void actionPerformed(ActionEvent e) {
        int result = JOptionPane.showConfirmDialog(null, this.warningMessage, "WARNING", JOptionPane.YES_NO_CANCEL_OPTION);
        if (result == JOptionPane.YES_OPTION) {
            this.app.resetAllNodes();
        }
    }
}