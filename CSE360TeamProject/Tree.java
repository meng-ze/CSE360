import java.util.*;

public class Tree {
    public ArrayList<Node> orderList = new ArrayList<Node>();
    public HashMap<String, Node> nodeDict = new HashMap<String, Node>();
    public ArrayList<Node> rawData = new ArrayList<Node>();

    public HashMap<String, Node> endPoint = new HashMap<String, Node>();
    public ArrayList<Path> descendingOrderPaths = new ArrayList<Path>();

    public Tree(ArrayList<Node> nodes) {
        for (Node n: nodes) {
            Node tmpNode = new Node(n.name, n.duration);
            for (String key: n.dependencies_key) {
                if (!key.isEmpty()) {
                    tmpNode.dependencies_key.add(key);
                }
            }
            nodeDict.put(tmpNode.name, tmpNode);
        }
    }

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
            if (node.nextNodes.size() == 0) {
                if (!endPoint.containsKey(node.name)) {
                    this.endPoint.put(node.name, node);
                }
            } else {
                for (Node next: node.nextNodes) {
                    if (!next.traversed) {
                        dfs_aux(next);
                    }
                }
            }
            this.orderList.add(0, node);
        }
    }

    public Boolean constructAscendentList() {
        for (String key: this.nodeDict.keySet()) {
            for (String dep_key: this.nodeDict.get(key).dependencies_key) {
                if (this.nodeDict.containsKey(dep_key)) {
                    (this.nodeDict.get(key)).dependencies.add(this.nodeDict.get(dep_key));
                    this.nodeDict.get(dep_key).nextNodes.add(this.nodeDict.get(key));
                    this.nodeDict.get(dep_key).nextNodes_key.add(key);
                } else if (dep_key.isEmpty()) {

                } else {
                    System.out.printf("Cannot find key: %s\n", dep_key);
                    return false;
                }
            }
        }        
        return true;
    }

    public void treeConstructAux() {
        constructAscendentList();
        for (Node n: this.nodeDict.values()) {
            this.rawData.add(n);
        }
    }

    public void constructPertDiagram(Boolean isForwarding) {

        ArrayList<Node> remainQueue = new ArrayList<Node>();
        for (Node node: this.orderList) {
            if (isForwarding) {
                remainQueue.add(node);
            } else {
                remainQueue.add(0, node);
            }
        }
        HashSet<String> finishedSet = new HashSet<String>();

        while (remainQueue.size() != 0) {
            Node tmpNode = remainQueue.get(0);
            if (isForwarding) {
                if (tmpNode.dependencies_key.size() == 0) {
                    remainQueue.remove(0);
                    tmpNode.earlyStart = 0;
                    tmpNode.earlyFinish = tmpNode.duration;

                    finishedSet.add(tmpNode.name);
                } else {
                    Boolean keepSearching = true;

                    while(keepSearching) {
                        keepSearching = false;
                        System.out.printf("Processing: %s\n", remainQueue.get(0).name);
                        for (String key: remainQueue.get(0).dependencies_key) {
                            if (!finishedSet.contains(key) && !key.isEmpty()) {
                                System.out.printf("key: %s not exist in finished list\n", key);
                                remainQueue.add(remainQueue.remove(0));
                                keepSearching = true;
                                break;
                            }
                        }
                    }
                    tmpNode = remainQueue.get(0);
                    
                    int orderMax = -1;
                    int tmpMax = -1;
                    for (String key: tmpNode.dependencies_key) {
                        if (!key.isEmpty()) {
                            if (nodeDict.get(key).earlyFinish > tmpMax) {
                                tmpMax = nodeDict.get(key).earlyFinish;
                            }
                            if (nodeDict.get(key).order > orderMax) {
                                orderMax = nodeDict.get(key).order;
                            }
                        } else {
                            tmpMax = 0;
                        }
                    }
                    tmpNode.order = orderMax + 1;
                    tmpNode.earlyStart = tmpMax;
                    tmpNode.earlyFinish = tmpNode.earlyStart+tmpNode.duration;
                    remainQueue.remove(0);
                    finishedSet.add(tmpNode.name);
                }
            } else {
                if (tmpNode.nextNodes_key.size() == 0) {
                    remainQueue.remove(0);
                    tmpNode.lateFinish = tmpNode.earlyFinish;
                    tmpNode.lateStart = tmpNode.lateFinish-tmpNode.duration;

                    finishedSet.add(tmpNode.name);
                } else {
                    for (String key: tmpNode.nextNodes_key) {
                        if (!finishedSet.contains(key)) {
                            remainQueue.remove(0);
                            remainQueue.add(tmpNode);
                            break;
                        }
                    }
                    
                    int tmpMin = Integer.MAX_VALUE;
                    for (String key: tmpNode.nextNodes_key) {
                        if (nodeDict.get(key).lateStart < tmpMin) {
                            tmpMin = nodeDict.get(key).lateStart;
                        }
                    }
                    tmpNode.lateFinish = tmpMin;
                    tmpNode.lateStart  = tmpNode.lateFinish-tmpNode.duration;
                    remainQueue.remove(0);
                    finishedSet.add(tmpNode.name);
                }
            }
        }
    }

    public void findAllPossiblePaths() {
        dfs_find_path(orderList.get(0), new Path(0));
        for (Path path: this.descendingOrderPaths) {
            for (Node n: path.path) {
                System.out.printf("%s ", n.name);
            }
            System.out.printf("%d \n", path.pathLength);
        }
    }

    private void dfs_find_path(Node node, Path previousPath) {
        Path cpyPath = new Path(0);
        if (previousPath.pathLength != 0) {
            cpyPath.pathLength = previousPath.pathLength;
        }
        for (Node n: previousPath.path) {
            cpyPath.path.add(n);
        }
        cpyPath.path.add(node);
        cpyPath.pathLength += node.duration;

        if (node.name != orderList.get(orderList.size()-1).name) {
            for (Node n: node.nextNodes) {
                dfs_find_path(n, cpyPath);
            }
        } else {
            int i = 0;
            for (i=0; i<descendingOrderPaths.size(); i++) {
                if (descendingOrderPaths.get(i).pathLength < cpyPath.pathLength) {
                    break;
                }
            }
            descendingOrderPaths.add(i, cpyPath);
        }
    }

    public void generateNodeLocation() {
        HashMap<Integer, ArrayList<Node>> orderMap = new HashMap<Integer, ArrayList<Node>>();
        for (Node node: orderList) {
            node.boundingBox.x = (node.order+1)*(node.boundingBox.width+30);
            if (orderMap.containsKey(node.order)) {
                orderMap.get(node.order).add(node);
            } else {
                ArrayList<Node> tmpList = new ArrayList<Node>();
                tmpList.add(node);
                orderMap.put(node.order, tmpList);
            }
        }

        int basedSpace = 40;
        int maxheight = 300;

        for (int layerNumber: orderMap.keySet()) {
            int boxheight = orderMap.get(layerNumber).get(0).boundingBox.height;
            int numberOfNodeInLayer = orderMap.get(layerNumber).size();
            int totalHeight = boxheight*numberOfNodeInLayer+ basedSpace*(numberOfNodeInLayer-1);
            int startPoint = (maxheight-totalHeight)/2;

            int i = 0;
            for (Node node: orderMap.get(layerNumber)) {
                node.boundingBox.y = startPoint + i*(boxheight+basedSpace);
                i++;
            }
        }
    }


    public Boolean isNotConnected() {
        System.out.printf("End point count: %s\n", this.endPoint.size());
        if (this.endPoint.keySet().size() == 1) {
            return false;
        }
        return true;
    }

    public static void main(String [] args) {
        ArrayList<Node> testList = new ArrayList<Node>();
        Node a = new Node("a", 5);
        Node b = new Node("b", 1);
        Node c = new Node("c", 3);
        Node d = new Node("d", 6);
        Node e = new Node("e", 5);
        Node f = new Node("f", 2);
        Node g = new Node("g", 1);
        Node h = new Node("h", 7);
        Node i = new Node("i", 4);
        Node j = new Node("j", 9);

        b.dependencies_key.add("a");
        
        c.dependencies_key.add("d");
        c.dependencies_key.add("b");

        d.dependencies_key.add("a");

        e.dependencies_key.add("d");

        f.dependencies_key.add("b");

        g.dependencies_key.add("h");
        g.dependencies_key.add("e");
        g.dependencies_key.add("c");
        g.dependencies_key.add("f");

        h.dependencies_key.add("d");

        i.dependencies_key.add("");
        j.dependencies_key.add("i");

        testList.add(c);
        testList.add(h);
        //testList.add(j);
        testList.add(e);
        testList.add(b);
        //testList.add(i);
        testList.add(a);
        testList.add(g);
        testList.add(d);
        testList.add(f);

        for (Node node: testList) {
            System.out.printf("%s\t: %d, %d\n", node.name, node.duration, node.dependencies.size());
        }

        System.out.printf("--------------------------------\n");

        Tree tree = new Tree(testList);
        tree.treeConstructAux();
        tree.traverse();

        System.out.printf("tree contains cycle? %s\n", tree.containsCycle());
        System.out.printf("Anything not connected? %s\n", tree.isNotConnected());

        if (!tree.isNotConnected()) {
            tree.constructPertDiagram(true);
            tree.constructPertDiagram(false);
        } else {
            System.out.printf("Cannot construct pert diagram.\n");
        }

        for (Node node: tree.orderList) {
            System.out.printf("%s\t: [%d, %d, %d | %d, %d]\n", node.name, node.earlyStart, node.duration, node.earlyFinish,
            node.lateStart, node.lateFinish);
        }
    }
}