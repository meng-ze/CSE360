import javax.swing.*;
import java.awt.event.*;

public class kWarningPopup implements ActionListener {
    private String warningMessage;
    public kWarningPopup(String warningMessage) {
        this.warningMessage = warningMessage;
    }
    public void actionPerformed(ActionEvent e) {
        JOptionPane.showConfirmDialog(null, this.warningMessage, "WARNING", JOptionPane.YES_NO_CANCEL_OPTION);
    }
}