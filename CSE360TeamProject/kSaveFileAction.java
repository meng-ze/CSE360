import java.io.*;
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
        String nodesStr = kPathDrawer.getSortedActivity(tree);
        String pathStr = kPathDrawer.getAllPossiblePathsStr(tree);
        String outputStr = "";
        outputStr += "--Network Report--\n";
        outputStr += "Name: ";
        outputStr += networkName;
        outputStr += "\n";
        outputStr += "Create Time: ";
        outputStr += dateTimeStr;
        outputStr += "\n";
        outputStr += "\n";
        outputStr += "--Activities--";
        outputStr += "\n";
        outputStr += nodesStr;
        outputStr += "\n";
        outputStr += "--Paths--";
        outputStr += "\n";
        outputStr += pathStr;
        outputStr += "\n";
        System.out.printf("%s", outputStr);
        JFileChooser fileChooser = new JFileChooser();
        if (fileChooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            try {
                BufferedWriter fileHandler = new BufferedWriter(new FileWriter(file));
                fileHandler.write(outputStr);
                fileHandler.close();
            } catch (IOException exc) {
                exc.printStackTrace();
            }
        }
    }
}