import javax.swing.*;
import java.awt.event.*;

public class kDeleteAction implements ActionListener {
    private GUIApp app;
    public kDeleteAction(GUIApp app) {
        this.app = app;
    }
    public void actionPerformed(ActionEvent e) {
        if (this.app.recordTable.getSelectedRow() < 0 || this.app.recordTable.getSelectedRow() >= this.app.recordTable.getRowCount()) {
            return;
        }
        String selected_key = (String) this.app.model.getValueAt(this.app.recordTable.getSelectedRow(), 0);
        int result = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete " + selected_key, "WARNING", JOptionPane.YES_NO_CANCEL_OPTION);
        if (result == JOptionPane.YES_OPTION) {
            this.app.model.removeRow(this.app.recordTable.getSelectedRow());
            this.app.historyNetworks.remove(selected_key);
            this.app.updateRecordsLabel();
        }
    }
}