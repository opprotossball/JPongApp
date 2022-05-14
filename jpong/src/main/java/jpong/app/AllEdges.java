package jpong.app;

public class AllEdges extends Generator {
	public static final int basic_value = -1;
	public static final int maximum_number_of_elements_in_node = 5;

	public Graph generate(int minimal_weight, int maximum_weight, int columns, int rows) {
		int counter = 0;
		int counter_i_one_node;
		int Sum = columns * rows;
		Node[] graph = new Node[Sum];
		for (int i = 0; i < Sum; i++)
			graph[i] = new Node();
		for (int r = 0; r < rows; r++) {
			for (int c = 0; c < columns; c++) {
				int[] node_holder = new int[maximum_number_of_elements_in_node];
				double[] weight_holder = new double[maximum_number_of_elements_in_node];
				for (int i = 0; i < maximum_number_of_elements_in_node; i++) {
					node_holder[i] = basic_value;
					weight_holder[i] = basic_value;
				}
				counter_i_one_node = 0;
				if (r != 0) {
					node_holder[counter_i_one_node] = counter - columns;
					weight_holder[counter_i_one_node++] = draw(minimal_weight, maximum_weight);
				}
				if (c != 0) {
					node_holder[counter_i_one_node] = counter - 1;
					weight_holder[counter_i_one_node++] = draw(minimal_weight, maximum_weight);
				}
				if (c != columns - 1) {
					node_holder[counter_i_one_node] = counter + 1;
					weight_holder[counter_i_one_node++] = draw(minimal_weight, maximum_weight);
				}
				if (r != rows - 1) {
					node_holder[counter_i_one_node] = counter + columns;
					weight_holder[counter_i_one_node++] = draw(minimal_weight, maximum_weight);
				}
				graph[counter++].add(node_holder, weight_holder);
			}
		}
		Graph full_graph = new Graph(Sum);
		full_graph.addNode(graph, columns, rows);
		return full_graph;
	}
}
