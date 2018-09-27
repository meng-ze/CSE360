import java.util.ArrayList;

public class Node {
    public ArrayList<Node> dependencies = new ArrayList<Node>();
    public ArrayList<String> dependencies_key = new ArrayList<String>();
    public ArrayList<Node> nextNodes = new ArrayList<Node>();
    public String name;
    public int duration;
    public Boolean traversed = false;
    public int order;

    public Node(String name, int duration) {
        this.name = name;
        this.duration = duration;
    }

    @Override
    public String toString() {
        return "Name: " + name + ", Nexts: " + nextNodes;
    }
}