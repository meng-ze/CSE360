import java.awt.Rectangle;
import java.awt.geom.*;

import java.util.ArrayList;

public class Node {
    public ArrayList<Node> dependencies = new ArrayList<Node>();
    public ArrayList<String> dependencies_key = new ArrayList<String>();
    public ArrayList<Node> nextNodes = new ArrayList<Node>();
    public ArrayList<String> nextNodes_key = new ArrayList<String>();

    public Point2D.Float anchorPointEntry = new Point2D.Float(0.0f, 0.0f);
    public Point2D.Float anchorPointExit = new Point2D.Float(0.0f, 0.0f);
    public Rectangle boundingBox = new Rectangle(0, 0, 40, 20);

    public String name;
    public int duration;
    public Boolean traversed = false;
    public int order = 0;

    public int earlyStart = -1;
    public int earlyFinish = -1;
    public int lateStart = -1;
    public int lateFinish = -1;

    public Node(String name, int duration) {
        this.name = name;
        this.duration = duration;
    }
    
    public Point2D getEntryPoint() {
        this.anchorPointEntry.x = this.boundingBox.x;
        this.anchorPointEntry.y = this.boundingBox.y+(this.boundingBox.height/2);
        return this.anchorPointEntry;
    }

    public Point2D getExitPoint() {
        this.anchorPointExit.x = this.boundingBox.x+this.boundingBox.width;
        this.anchorPointExit.y = this.boundingBox.y+(this.boundingBox.height/2);
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