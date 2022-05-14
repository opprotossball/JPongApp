package jpong.app;

public class Graph {
	private int columns;
	private int rows;
	private Node[] nodes;

	public Graph() {
	}

	public Graph(int number) {
		nodes = new Node[number];
	}

	public int[] show_connected(int number) {
		return nodes[number].show_all_node();
	}

	public void addNode(Node holder[], int columns, int rows) {
		int counter = 0;
		this.columns = columns;
		this.rows = rows;
		for (jpong.app.Node Node : holder) {
			nodes[counter++] = Node;
		}
	}

	public int show_columns() {
		return columns;
	}

	public int show_rows() {
		return rows;
	}

	public Node[] show_nodes() {
		return nodes;
	}

	public Node show_node(int number) {
		return nodes[number];
	}

}
