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
        String activityName  = app.txtActivityName.getText().replaceAll("\\s","");
        String dependencyStr = app.txtDependency.getText();
        String durationStr   = app.txtDuration.getText();

        ArrayList<String> dependencies = new ArrayList<String>();
        for (String str: dependencyStr.split("\\s*,\\s*")) {
            dependencies.add(str);
        }
        this.app.addNodeIntoNodeList(activityName, dependencies, durationStr);
    }
}