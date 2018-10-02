import java.util.*;
import java.awt.event.*;
import javax.swing.*;

public class kAnalyzeGraphAction implements ActionListener {
    private GUIApp app;
    public kAnalyzeGraphAction(GUIApp guiApp) {
        this.app = guiApp;
    }

    private Boolean treeConstructAux(Tree tree) {
        if (!tree.constructAscendentList()) {
            JOptionPane.showConfirmDialog(null, "Error!\nSome Dependency is missing in this path", "WARNING", JOptionPane.DEFAULT_OPTION);
            return false;
        }
        for (Node n: tree.nodeDict.values()) {
            tree.rawData.add(n);
        }
        return true;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Tree tree = new Tree(this.app.nodeList);
        if (!this.treeConstructAux(tree)) {
            this.app.resetAllNodes();
            return;
        }
        if (tree.containsCycle()) {
            JOptionPane.showConfirmDialog(null, "Seems like your diagram contains at least one dependency cycle.\nPlease check your dependency.", "WARNING", JOptionPane.DEFAULT_OPTION);
        } else {
            tree.traverse();

            this.app.historyNetworks.put("Network:"+this.app.graphNumber, tree);
            this.app.graphNumber += 1;
        }

        this.app.resetAllNodes();

        for (String key: this.app.historyNetworks.keySet()) {
            System.out.printf("%s: %s\n", key, this.app.historyNetworks.get(key).rawData);
        }
    }
}