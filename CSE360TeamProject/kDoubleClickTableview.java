import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;
import java.awt.event.*;

public class kDoubleClickTableview extends MouseAdapter  {
    GUIApp app;
    public kDoubleClickTableview(GUIApp app) {
        this.app = app;
    }

    public void mousePressed(MouseEvent m) {
        Point point = m.getPoint();
        int row = this.app.recordTable.rowAtPoint(point);
        if (m.getClickCount() == 2 && this.app.recordTable.getSelectedRow() != -1) {
            String networkKey = (String)this.app.model.getValueAt(this.app.recordTable.getSelectedRow(), 0);
            this.app.drawCriticalOnly = false;
            kPathDrawer.drawDiagram(this.app.historyNetworks.get(networkKey), false, this.app);
            this.app.navigateToPathsFoundPanel();
        }
    }
}