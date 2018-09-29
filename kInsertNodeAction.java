import java.awt.event.*;
import java.util.*;

public class kInsertNodeAction implements ActionListener {
    private GUIApp app;
    public kInsertNodeAction(GUIApp guiApp) {
        this.app = guiApp;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String networkName   = app.txtNetworkName.getText();
        String activityName  = app.txtActivityName.getText();
        String dependencyStr = app.txtDependency.getText();
        String durationStr   = app.txtDuration.getText();

        System.out.printf("%s\n", networkName);
        System.out.printf("%s\n", activityName);
        System.out.printf("%s\n", dependencyStr);
        System.out.printf("%s\n", durationStr);

        ArrayList<String> dependencies = new ArrayList<String>();
        for (String str: dependencyStr.split("\\s*,\\s*")) {
            dependencies.add(str);
        }
        this.app.addNodeIntoNodeList(activityName, dependencies, durationStr);
    }
}