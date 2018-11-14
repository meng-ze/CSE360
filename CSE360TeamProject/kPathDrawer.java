import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;
import java.awt.event.*;
import java.util.*;
import java.awt.geom.AffineTransform.*;
import java.awt.geom.AffineTransform;

public class kPathDrawer {
	private static void printAllPossiblePaths(Tree tree, GUIApp app) {
		System.out.printf("print all paths\n");

        app.textArea_inputRecord.setEditable(true);
        String tmpString = "";
        System.out.printf("%d\n", tree.descendingOrderPaths.size());
		for (Path p: tree.descendingOrderPaths) {
			for (Node node: p.path) {
				tmpString += node.name;
				if (node.nextNodes.size() != 0) {
					tmpString += " -> ";
				}
			}	
			tmpString += ": ";
			tmpString += "" + p.pathLength + "\n";
		}
		System.out.printf("Set text: %s\n", tmpString);
		app.textArea_inputRecord.setText(tmpString);
		app.textArea_inputRecord.setEditable(false);

	}
	public static String getSortedActivity(Tree tree) {
		TreeMap<String, Node> treeMap = new TreeMap<String, Node>();
		treeMap.putAll(tree.nodeDict);
		String returnStr = "";
		for (Map.Entry<String, Node> entry : treeMap.entrySet()) {
			String nodeInfo = "";
			nodeInfo += entry.getKey();
			nodeInfo += ": ";
			nodeInfo += entry.getValue().duration;
			returnStr += nodeInfo + "\n";
		}
		return returnStr;
	}
	public static String getAllPossiblePathsStr(Tree tree) {
		String tmpString = "";
		for (Path p: tree.descendingOrderPaths) {
			for (Node node: p.path) {
				tmpString += node.name;
				if (node.nextNodes.size() != 0) {
					tmpString += " -> ";
				}
			}	
			tmpString += ": ";
			tmpString += "" + p.pathLength + "\n";
		}
		return tmpString;
	}
    public static void drawDiagram(Tree tree, Boolean criticalOnly, GUIApp app) {
		app.displayingTreeName = tree.name;
		
		System.out.printf("DrawDiagram\n");
		printAllPossiblePaths(tree, app);
		app.scrollPane_PathsFound.setViewportView(new JComponent() {
			void drawArrow(Graphics g, Node node1, Node node2) {
				Graphics2D overrideGraphics = (Graphics2D) g.create();

				int arrowSize = 7;
				double dx = node2.getEntryPoint().getX() - node1.getExitPoint().getX();
				double dy = node2.getEntryPoint().getY() - node1.getExitPoint().getY();
                double rotationAngle = Math.atan2(dy, dx);
                int arrowLength = (int) Math.sqrt(dx*dx+dy*dy);
                AffineTransform at = AffineTransform.getTranslateInstance(node1.getExitPoint().getX(), node1.getExitPoint().getY());
                at.concatenate(AffineTransform.getRotateInstance(rotationAngle));
                overrideGraphics.transform(at);

				if (node1.earlyFinish == node1.lateFinish && node2.earlyFinish == node2.lateFinish) {
					overrideGraphics.setColor(Color.red);
				} else {
					overrideGraphics.setColor(Color.black);
				}
				if (criticalOnly && overrideGraphics.getColor().equals(Color.black)) {

				} else {
					overrideGraphics.drawLine(0, 0, arrowLength, 0);
					overrideGraphics.fillPolygon(
						new int[] { arrowLength, arrowLength-arrowSize, arrowLength-arrowSize, arrowLength}, 
						new int[] { 0, -arrowSize, arrowSize, 0}, 3
						);
				}
			}

            void drawRec(Graphics overrideGraphics, Node node) {
				int boxWidth = node.boundingBox.width;
				int boxHeight = node.boundingBox.height;

				System.out.printf("Added %s into diagram\n", node.name);
				if (node.lateFinish == node.earlyFinish) {
					overrideGraphics.setColor(Color.red);
				} else {
					overrideGraphics.setColor(Color.blue);
				}
				if (criticalOnly && overrideGraphics.getColor().equals(Color.blue)) {
					return;
				}
				overrideGraphics.fillRect(node.boundingBox.x, node.boundingBox.y, boxWidth, boxHeight);

				int currentFontSize = overrideGraphics.getFont().getSize();
				int stringLength = currentFontSize * node.name.length();
		
				overrideGraphics.setColor(Color.white);
				overrideGraphics.drawString(node.name, node.boundingBox.x+boxWidth/2-stringLength/2,
				node.boundingBox.y+boxHeight/2+currentFontSize/2);
		
				overrideGraphics.setColor(Color.black);
				overrideGraphics.drawString(""+node.earlyStart, node.boundingBox.x, node.boundingBox.y-3);
				overrideGraphics.setColor(Color.black);
				overrideGraphics.drawString(""+node.earlyFinish, node.boundingBox.x+boxWidth-stringLength, node.boundingBox.y-3);
		
				overrideGraphics.setColor(Color.black);
				overrideGraphics.drawString(""+node.lateStart, node.boundingBox.x, node.boundingBox.y+boxHeight+currentFontSize);
				overrideGraphics.setColor(Color.black);
				overrideGraphics.drawString(""+node.lateFinish, node.boundingBox.x+boxWidth-stringLength, node.boundingBox.y+boxHeight+currentFontSize);
			}

            public void paintComponent(Graphics g) {
				for (Node node: tree.orderList) {
					drawRec(g, node);
					for (Node node2: node.nextNodes) {
						drawArrow(g, node, node2);
					}
				}
            }
        });
    }
}