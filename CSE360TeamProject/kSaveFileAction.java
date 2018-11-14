import javax.swing.*;
import java.awt.event.*;

public class kSaveFileAction implements ActionListener {
    private GUIApp app;
    public kSaveFileAction(GUIApp app) {
        this.app = app;
    }
    public void actionPerformed(ActionEvent e) {
        Tree tree = this.app.historyNetworks.get(this.app.displayingTreeName);
        String networkName = tree.name;
        String dateTimeStr = tree.dateTime;
        String pathStr = kPathDrawer.getAllPossiblePathsStr(tree);
        System.out.printf("--Network Report--\n");
        System.out.printf("Name: %s\n", networkName);
        System.out.printf("Create Time: %s\n", dateTimeStr);
        System.out.printf("--Activities--\n%s", "");
        System.out.printf("\n");
        System.out.printf("--Paths--\n%s", pathStr);
    }
}