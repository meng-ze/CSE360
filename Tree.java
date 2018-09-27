import java.util.*;

public class Tree {
    public ArrayList<Node> orderList = new ArrayList<Node>();
    public HashMap<String, Node> nodeDict = new HashMap<String, Node>();
    public ArrayList<Node> rawData = new ArrayList<Node>();
    public Tree(ArrayList<Node> nodes) {
        for (Node n: nodes) {
            nodeDict.put(n.name, n);
        }
        constructAscendentList();
        for (Node n: nodeDict.values()) {
            rawData.add(n);
        }
    }
    private void constructAscendentList() {
        for (String key: this.nodeDict.keySet()) {
            for (String dep_key: nodeDict.get(key).dependencies_key) {
                nodeDict.get(dep_key).nextNodes.add(this.nodeDict.get(key));
            }
        }        
    }

    public Boolean containsCycle() {
        return false;
    }

    public void traverse(ArrayList<Node> nodes) {
        for (Node node: nodes) {
            if (!node.traversed) {
                this.dfs_aux(node);
            }
        }
    }
    private void dfs_aux(Node node) {
        if (!node.traversed) {
            node.traversed = true;
            for (Node next: node.nextNodes) {
                node.nextNodes.add(next);
                if (!next.traversed) {
                    dfs_aux(next);
                }
            }
            this.orderList.add(node);
        }
    }

    public static void main(String [] args) {
        ArrayList<Node> testList = new ArrayList<Node>();
        Node firstNode = new Node("firstNode", 10);
        Node secondNode = new Node("secondNode", 1);
        Node thirdNode = new Node("thirdNode", 5);
        Node forthNode = new Node("forthNode", 5);
        testList.add(firstNode);
        testList.add(secondNode);
        testList.add(thirdNode);
        testList.add(forthNode);

        forthNode.dependencies_key.add("firstNode");
        secondNode.dependencies_key.add("firstNode");
        thirdNode.dependencies_key.add("secondNode");

        for (Node node: testList) {
            System.out.printf("%s\t: %d, %d\n", node.name, node.duration, node.dependencies.size());
        }

        System.out.printf("--------------------------------\n");

        Tree tree = new Tree(testList);
        //tree.traverse(testList);
        for (Node node: tree.rawData) {
            System.out.printf("%s\t: %d, %s\n", node.name, node.duration, node.nextNodes);
        }
    }
}