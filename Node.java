import java.awt.Rectangle;
import java.awt.geom.*;

import java.util.ArrayList;

public class Node {
    public ArrayList<Node> dependencies = new ArrayList<Node>();
    public ArrayList<String> dependencies_key = new ArrayList<String>();
    public ArrayList<Node> nextNodes = new ArrayList<Node>();
    public ArrayList<String> nextNodes_key = new ArrayList<String>();

    private Point2D.Float anchorPointEntry = new Point2D.Float(0.0f, 0.0f);
    private Point2D.Float anchorPointExit = new Point2D.Float(0.0f, 0.0f);
    private Rectangle boundingBox = new Rectangle(0, 0, 1, 1);

    public String name;
    public int duration;
    public Boolean traversed = false;
    public int order;

    public Node(String name, int duration) {
        this.name = name;
        this.duration = duration;
    }
    
    public Point2D getEntryPoint() {
        return this.anchorPointEntry;
    }

    public Point2D getExitPoint() {
        return this.anchorPointExit;
    }

    public void setBoundingBox(int x, int y, int width, int height) {
        this.boundingBox.x = x;
        this.boundingBox.y = y;
        this.boundingBox.width = width;
        this.boundingBox.height = height;

        this.anchorPointEntry.x = x;
        this.anchorPointEntry.y = y+(height/2);
        this.anchorPointExit.x = x+width;
        this.anchorPointExit.y = y+(height/2);
    }

    @Override
    public String toString() {
        return "Name: "+name+", Nexts: "+nextNodes_key+", Dependencies: "+dependencies_key;
    }
}