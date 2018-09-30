import java.util.*;

public class Tree {
    public ArrayList<Node> orderList = new ArrayList<Node>();
    public HashMap<String, Node> nodeDict = new HashMap<String, Node>();
    public ArrayList<Node> rawData = new ArrayList<Node>();

    private int step = 0;

    public Tree(ArrayList<Node> nodes) {
        for (Node n: nodes) {
            nodeDict.put(n.name, n);
        }
        constructAscendentList();
        for (Node n: nodeDict.values()) {
            rawData.add(n);
        }
    }


    //TODO:
    // if this return true, the graphic contains dependency cycle
    // return false if dependency do not form any cycle
    public Boolean containsCycle() {
        this.resetTraverseStatus();

        for (Node node: this.rawData) {
            HashSet<String> stackingNodeKeys = new HashSet<String>();
            HashSet<String> traversedKeys = new HashSet<String>();

            if (dfs_detect_cycle(node, stackingNodeKeys, traversedKeys)) {
                return true;
            }
        }
        return false;
    }

    private Boolean dfs_detect_cycle(Node rootNode, HashSet<String> stackingNodeKeys, HashSet<String> traversedKeys) {
        if (stackingNodeKeys.contains(rootNode.name)) {
            return true;
        } else if (traversedKeys.contains(rootNode.name)) {
            return false;
        }
        for (Node node: rootNode.nextNodes) {
            stackingNodeKeys.add(rootNode.name);
            if (dfs_detect_cycle(node, stackingNodeKeys, traversedKeys)) {
                return true;
            }
        }
        stackingNodeKeys.remove(rootNode.name);
        traversedKeys.add(rootNode.name);
        return false;
    }

    public void resetTraverseStatus() {
        for (Node node: this.rawData) {
            node.traversed = false;
        }
    }

    public void traverse() {
        this.resetTraverseStatus();
        for (Node node: this.rawData) {
            if (!node.traversed) {
                this.dfs_aux(node);
            }
        }
    }
    private void dfs_aux(Node node) {
        if (!node.traversed) {
            node.traversed = true;
            for (Node next: node.nextNodes) {
                if (!next.traversed) {
                    dfs_aux(next);
                }
            }
            this.step += 1;
            node.order = this.step;
            this.orderList.add(0, node);
        }
    }

    private void constructAscendentList() {
        for (String key: this.nodeDict.keySet()) {
            for (String dep_key: nodeDict.get(key).dependencies_key) {
                (this.nodeDict.get(key)).dependencies.add(nodeDict.get(dep_key));
                nodeDict.get(dep_key).nextNodes.add(this.nodeDict.get(key));
                nodeDict.get(dep_key).nextNodes_key.add(key);
            }
        }        
    }

    public static void main(String [] args) {
        ArrayList<Node> testList = new ArrayList<Node>();
        Node a = new Node("a", 1);
        Node b = new Node("b", 1);
        Node c = new Node("c", 1);
        Node d = new Node("d", 1);
        Node e = new Node("e", 1);
        Node f = new Node("f", 1);

        a.dependencies_key.add("b");

        b.dependencies_key.add("c");
        b.dependencies_key.add("a");
        b.dependencies_key.add("d");

        c.dependencies_key.add("e");

        d.dependencies_key.add("a");
        d.dependencies_key.add("e");
        d.dependencies_key.add("f");

        e.dependencies_key.add("a");

        testList.add(a);
        testList.add(b);
        testList.add(c);
        testList.add(d);
        testList.add(e);
        testList.add(f);

        for (Node node: testList) {
            System.out.printf("%s\t: %d, %d\n", node.name, node.duration, node.dependencies.size());
        }

        System.out.printf("--------------------------------\n");

        Tree tree = new Tree(testList);
        System.out.printf("tree contains cycle? %s\n", tree.containsCycle());
        for (Node node: tree.rawData) {
            System.out.printf("%s\t: %d, %s | dep: %s\n", node.name, node.duration, node.nextNodes_key, node.dependencies_key);
        }

        /*
        System.out.printf("-----------Traversed------------\n");
        tree.traverse();
        for (Node node: tree.orderList) {
            System.out.printf("%s\t:, completed_time: %d dep: %s\n", node.name, node.order, node.dependencies_key);
        }
        */
    }
}