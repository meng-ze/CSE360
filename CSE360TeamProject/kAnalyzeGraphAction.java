import java.util.*;
import java.awt.event.*;
import javax.swing.*;
import java.time.format.DateTimeFormatter;  
import java.time.LocalDateTime;

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
    public void actionPerformed(ActionEvent except) {
        Tree tree = new Tree(this.app.nodeList);
        if (!this.treeConstructAux(tree)) {
            this.app.resetAllNodes();
            return;
        }
        this.app.resetAllNodes();

        if (tree.containsCycle()) {
            JOptionPane.showConfirmDialog(null, "Seems like your diagram contains at least one dependency cycle.\nPlease check your dependency.", "WARNING", JOptionPane.DEFAULT_OPTION);
        } else {
            tree.traverse();
            tree.findAllPossiblePaths();

            if (tree.isNotConnected()) {
                JOptionPane.showConfirmDialog(null, "Seems like part of your diagram is not connected.\nPlease check your dependency.", "WARNING", JOptionPane.DEFAULT_OPTION);
            } else {
                String historyKey = "Network"+this.app.graphNumber;
                tree.name = historyKey;
                DateTimeFormatter dateTimeFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");  
                LocalDateTime now = LocalDateTime.now();
                tree.dateTime = dateTimeFormat.format(now);
                this.app.historyNetworks.put(historyKey, tree);
                this.app.model.addRow(new Object[] { historyKey, ""+tree.nodeDict.size() });
                tree.constructPertDiagram(true);
                tree.constructPertDiagram(false);
                this.app.graphNumber += 1;
                
                for (Node node: tree.orderList) {
                    System.out.printf("%s\t: [%d, %d, %d | %d, %d | order: %d]\n", node.name, node.earlyStart, node.duration, node.earlyFinish,
                    node.lateStart, node.lateFinish, node.order);
                }
                tree.generateNodeLocation();
                this.app.drawCriticalOnly = false;
                kPathDrawer.drawDiagram(tree, false, this.app);
                this.app.navigateToPathsFoundPanel();
            }
        }

        this.app.updateRecordsLabel();

        for (String key: this.app.historyNetworks.keySet()) {
            System.out.printf("%s: %s\n", key, this.app.historyNetworks.get(key).rawData);
        }
    }
}