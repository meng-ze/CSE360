import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import javax.swing.border.*;

public class kUndoAction implements ActionListener {
    private GUIApp app;
    public kUndoAction(GUIApp guiApp) {
        this.app = guiApp;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (this.app.nodeList.size() != 0) {
            Node removeNode = this.app.nodeList.remove(this.app.nodeList.size()-1);
            this.app.nodeMaps.remove(removeNode.name);
            this.app.updateTextRecord();
        } else {
            JOptionPane.showConfirmDialog(null, "There is no action to be undone.\n", "WARNING", JOptionPane.DEFAULT_OPTION);
        }
    }
}