import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;
import java.awt.event.*;
import java.util.*;
import java.awt.geom.AffineTransform.*;
import java.awt.geom.AffineTransform;

public class kPathDrawer {
    public static void drawDiagram(Tree tree, GUIApp app) {
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
                overrideGraphics.drawLine(0, 0, arrowLength, 0);
                overrideGraphics.fillPolygon(
					new int[] { arrowLength, arrowLength-arrowSize, arrowLength-arrowSize, arrowLength}, 
					new int[] { 0, -arrowSize, arrowSize, 0}, 3);
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