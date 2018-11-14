import javax.swing.*;
import java.awt.event.*;

public class kModifyNodeAction implements ActionListener {
    private GUIApp app;
    public kModifyNodeAction(GUIApp app) {
        this.app = app;
    }

    private JTextField node_name_filed = new JTextField();
    private JTextField duration_field = new JTextField();
    private Object[] modify_node_event = {
        "Activity Name:", node_name_filed,
        "New Duration:", duration_field
    };
    public void actionPerformed(ActionEvent e) {
        int result = JOptionPane.showConfirmDialog(null, modify_node_event, "Modifying Node", JOptionPane.YES_NO_CANCEL_OPTION);
        String node_name = node_name_filed.getText();
        String new_duration = duration_field.getText();
    
        Tree oldTree = this.app.historyNetworks.get(this.app.displayingTreeName);
        if (result == JOptionPane.YES_OPTION) {
            for (int i=0; i<oldTree.rawData.size(); ++i) {
                if (oldTree.rawData.get(i).name.equals(node_name)) {
                    Node tmp_node = oldTree.rawData.get(i);
                    if (!GUIApp.isInt(new_duration)) {
                        JOptionPane.showConfirmDialog(null, "Please input Integer value in duration field!", "WARNING", JOptionPane.DEFAULT_OPTION);
                        return;
                    }
                    tmp_node.duration = Integer.parseInt(new_duration);
                    Tree newTree = new Tree(oldTree.rawData);
                    newTree.treeConstructAux();
                    newTree.traverse();
                    newTree.findAllPossiblePaths();
                    newTree.constructPertDiagram(true);
                    newTree.constructPertDiagram(false);
                    newTree.generateNodeLocation();
                    kPathDrawer.drawDiagram(newTree, this.app);
                    this.app.historyNetworks.put(this.app.displayingTreeName, newTree);
                    return;
                }
            }
            String message = "Node: " + node_name + " does not exist!\n Please check your activity name";
            JOptionPane.showConfirmDialog(null, message, "WARNING", JOptionPane.OK_CANCEL_OPTION);
        }
    }
}