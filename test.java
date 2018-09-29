import static java.awt.geom.AffineTransform.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import javax.swing.*;
import java.util.*;

public class test {
    public static void main(String args[]) {
        JFrame t = new JFrame();
        t.add(new JComponent() {

            private final int ARR_SIZE = 4;

            void drawArrow(Graphics g1, int x1, int y1, int x2, int y2) {
                Graphics2D g = (Graphics2D) g1.create();

                double dx = x2 - x1, dy = y2 - y1;
                double angle = Math.atan2(dy, dx);
                int len = (int) Math.sqrt(dx*dx + dy*dy);
                AffineTransform at = AffineTransform.getTranslateInstance(x1, y1);
                at.concatenate(AffineTransform.getRotateInstance(angle));
                g.transform(at);

                // Draw horizontal arrow starting in (0, 0)
                g.drawLine(0, 0, len, 0);
                g.fillPolygon(new int[] {len, len-ARR_SIZE, len-ARR_SIZE, len},
                              new int[] {0, -ARR_SIZE, ARR_SIZE, 0}, 4);
            }

            public void paintComponent(Graphics g) {
                for (int x = 15; x < 200; x += 16)
                    drawArrow(g, x, x, x, 150);
                drawArrow(g, 30, 300, 300, 190);
            }
        });

        t.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        t.setSize(400, 400);
        t.setVisible(true);

        addNodeIntoNodeList("Hi", new ArrayList<String>(), "0");
        addNodeIntoNodeList("Hi", new ArrayList<String>(), "1");
    }

	public static ArrayList<Node> nodeList = new ArrayList<Node>();
    private static HashMap<String, Node> nodeMaps = new HashMap<String, Node>();
    public static void addNodeIntoNodeList(String nodeName, ArrayList<String> dependencies, String durationStr) {
        if (nodeName == "") {
            JOptionPane.showConfirmDialog(null, "Please input node name!", "WARNING", JOptionPane.DEFAULT_OPTION);
            return;
        }
        if (!isInt(durationStr)) {
            JOptionPane.showConfirmDialog(null, "Please input Integer value in duration field!", "WARNING", JOptionPane.DEFAULT_OPTION);
            return;
		}
		if (test.nodeMaps.containsKey(nodeName)) {
            JOptionPane.showConfirmDialog(null, "Node: \"" + nodeName + "\" is already exist!\n Try using other name", "WARNING", JOptionPane.DEFAULT_OPTION);
            return;
		}
		Node newNode = new Node(nodeName, Integer.parseInt(durationStr));
		test.nodeMaps.put(nodeName, newNode);
    }

    public static Boolean isInt(String s) {
        try { 
            Integer.parseInt(s); 
        } catch (NullPointerException nulExcept) {
            return false;
        } catch (NumberFormatException formatExcept) { 
            return false; 
        }
        // only got here if we didn't return false
        return true;
    }
}

