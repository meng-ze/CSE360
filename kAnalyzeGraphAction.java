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
    public void actionPerformed(ActionEvent except) {
        // // THIS IS JUST FOR TEST PURPOSE
        // ArrayList<Node> testList = new ArrayList<Node>();
        // Node a = new Node("a", 5);
        // Node b = new Node("b", 1);
        // Node c = new Node("c", 3);
        // Node d = new Node("d", 6);
        // Node e = new Node("e", 5);
        // Node f = new Node("f", 2);
        // Node g = new Node("g", 1);
        // Node h = new Node("h", 7);
        // Node i = new Node("i", 4);
        // Node j = new Node("j", 9);

        // b.dependencies_key.add("a");
        
        // c.dependencies_key.add("d");
        // c.dependencies_key.add("b");

        // d.dependencies_key.add("a");

        // e.dependencies_key.add("d");

        // f.dependencies_key.add("b");

        // g.dependencies_key.add("h");
        // g.dependencies_key.add("e");
        // g.dependencies_key.add("c");
        // g.dependencies_key.add("f");

        // h.dependencies_key.add("d");

        // i.dependencies_key.add("");
        // j.dependencies_key.add("i");

        // testList.add(c);
        // testList.add(h);
        // testList.add(e);
        // testList.add(b);
        // testList.add(a);
        // testList.add(g);
        // testList.add(d);
        // testList.add(f);
        // // THIS IS JUST FOR TEST PURPOSE

        Tree tree = new Tree(this.app.nodeList);
        if (!this.treeConstructAux(tree)) {
            this.app.resetAllNodes();
            return;
        }

        if (tree.containsCycle()) {
            JOptionPane.showConfirmDialog(null, "Seems like your diagram contains at least one dependency cycle.\nPlease check your dependency.", "WARNING", JOptionPane.DEFAULT_OPTION);
        } else {
            tree.traverse();

            if (tree.isNotConnected()) {
                JOptionPane.showConfirmDialog(null, "Seems like part of your diagram is not connected.\nPlease check your dependency.", "WARNING", JOptionPane.DEFAULT_OPTION);
            } else {
                this.app.historyNetworks.put("Network:"+this.app.graphNumber, tree);
                tree.constructPertDiagram(true);
                tree.constructPertDiagram(false);
                this.app.graphNumber += 1;
                
                for (Node node: tree.orderList) {
                    System.out.printf("%s\t: [%d, %d, %d | %d, %d]\n", node.name, node.earlyStart, node.duration, node.earlyFinish,
                    node.lateStart, node.lateFinish);
                }
            }
        }

        this.app.resetAllNodes();

        for (String key: this.app.historyNetworks.keySet()) {
            System.out.printf("%s: %s\n", key, this.app.historyNetworks.get(key).rawData);
        }
    }
}