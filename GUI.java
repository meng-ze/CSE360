import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;
import java.awt.event.*;

public class GUI extends JFrame {
    protected JPanel createPanel = new JPanel();
    protected JPanel pathsFoundPanel = new JPanel();
    protected JPanel recordPanel = new JPanel();
    protected JPanel helpPanel = new JPanel();
    protected JPanel aboutPanel = new JPanel();

    protected JPanel mainContentPanel;
	protected JTextField txtCreateNetwork = new JTextField();
	protected JTextField txtHelp;
	protected JTextField txtRecord;
	protected JTextField txtAbout;
	protected JTextField txtRestart;
	protected JTextField txtPathsFound;
	protected JTextField txtNetworkName = new JTextField();
	protected JTextField txtActivityName = new JTextField();
	protected JTextField txtDuration = new JTextField();
	protected JTextField txtDependency = new JTextField();
	protected JTextField SearchBar;
	protected JTextField txtOurApplication;
	protected JTextField txtOurMembers;
	protected JTextField txt_ToCreateNetwork;
	protected JTextField txt_ToFindPathsList;
	protected JTextField txt_ClickRestartButton;
}
