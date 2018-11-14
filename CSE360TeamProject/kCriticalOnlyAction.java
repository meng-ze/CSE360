import javax.swing.*;
import java.awt.event.*;

public class kCriticalOnlyAction implements ActionListener {
    private GUIApp app;
    private JButton button;
    public kCriticalOnlyAction(GUIApp app, JButton host) {
        this.app = app;
        this.button = host;
    }
    public void actionPerformed(ActionEvent e) {
        if (this.app.displayingTreeName != null) {
            this.app.drawCriticalOnly = !this.app.drawCriticalOnly;
            kPathDrawer.drawDiagram(this.app.historyNetworks.get(this.app.displayingTreeName), this.app.drawCriticalOnly, this.app);

            if (this.app.drawCriticalOnly) {
                this.button.setText("All Path");
            } else {
                this.button.setText("Critical Only");
            }
        }
    }
}