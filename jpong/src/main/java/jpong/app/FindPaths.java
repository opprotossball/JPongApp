package jpong.app;

public class FindPaths {
    private final static int MAXCONNECTIONS = 4;
    private int start;
    private int nodes;
    private Integer[] previous;
    private Double[] dist;
    private boolean[] visited;
    Graph graph;

    public Integer[] findPath(Graph graph, int start) {
        this.graph = graph;
        this.start = start;
        this.nodes = graph.show_columns() * graph.show_rows();
        this.visited = new boolean[nodes];
        previous = new Integer[nodes];
        dist = new Double[nodes];
        for (int i = 0; i < nodes; i++) {
            previous[i] = null;
            dist[i] = Double.POSITIVE_INFINITY;
            visited[i] = false;
        }
        dist[start] = 0.0;
        PriorityQueue priorityQueue = new PriorityQueue();
        priorityQueue.add(new NodeDist(start, 0.0));
        while (!priorityQueue.isEmpty()) {
            NodeDist minNodeDist = priorityQueue.pop();
            double minDist = minNodeDist.getDist();
            int minNode = minNodeDist.getNode();
            if (visited[minNode]) {
                continue;
            }
            visited[minNode] = true;
            for (int i = 0; i < MAXCONNECTIONS; i++) {
                int nextConnected = graph.show_node(minNode).show_node(i);
                double nextWeight = graph.show_node(minNode).show_weights(i);
                if (nextConnected >= 0 && !visited[nextConnected]) {
                    double tempDist = dist[minNode] + nextWeight;
                    if (tempDist < dist[nextConnected]) {
                        dist[nextConnected] = tempDist;
                        previous[nextConnected] = minNode;
                        NodeDist newNodeDist = new NodeDist(nextConnected, tempDist);
                        priorityQueue.add(newNodeDist);
                    }
                }
            }
        }
        return previous;
    }
}
