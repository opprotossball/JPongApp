package jpong.app;

import java.util.ArrayList;

import static java.lang.Math.ceil;

public class PriorityQueue {
    private ArrayList<NodeDist> queue;

    public PriorityQueue() {
        this.queue = new ArrayList<NodeDist>();
    }

    private boolean compare(NodeDist nodeDist1, NodeDist nodeDist2) {
        return nodeDist1.getDist() >= nodeDist2.getDist();
    }

    public void add(NodeDist nodeDist) {
        queue.add(nodeDist);
        int n = queue.size() - 1;
        int parent = (int) ceil((double) n / 2) - 1;
        while (parent >= 0) {
            if (compare(queue.get(parent), queue.get(n))) {
                NodeDist tmp = queue.get(n);
                queue.set(n, queue.get(parent));
                queue.set(parent, tmp);
                n = parent;
                parent = (int) ceil((double) n / 2) - 1;
            } else {
                break;
            }
        }
    }

    public NodeDist pop() {
        int elementsNumber = queue.size() - 1;
        NodeDist minNode = queue.get(0);
        queue.set(0, queue.get(elementsNumber));
        queue.remove(elementsNumber);
        elementsNumber--;
        int n = 0;
        int child = (int) ceil((double) 2 * n) + 1;
        while (child < queue.size()) {
            if (child + 1 < elementsNumber && compare(queue.get(child), queue.get(child + 1))) {
                child++;
            }
            if (compare(queue.get(child), queue.get(n))) {
                break;
            }
            NodeDist tmp = queue.get(n);
            queue.set(n, queue.get(child));
            queue.set(child, tmp);
            n = child;
            child = (int) ceil((double) 2 * n) + 1;
        }
        return minNode;
    }

    public boolean isEmpty() {
        return queue.size() == 0;
    }
}
