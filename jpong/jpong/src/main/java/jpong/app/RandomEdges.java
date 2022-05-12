package jpong.app;

public class RandomEdges extends Generator {

	public Graph generate(int minimal_weight, int maximum_weight, int columns, int rows) {
		int counter = 0;
		int counter_i_one_node;
		int delete_right = 0;
		int Sum = columns * rows;
		int delete_bottom_counter = 0;
		int[] delete_bottom = new int[columns];
		for (int i = 0; i < columns; i++)
			delete_bottom[i] = -1;
		Node[] graph = new Node[Sum];
		for (int i = 0; i < Sum; i++)
			graph[i] = new Node();

		for (int r = 0; r < rows; r++) {
			for (int c = 0; c < columns; c++) {
				int[] node_holder = new int[5];
				double[] weight_holder = new double[5];
				for (int i = 0; i < 5; i++) {
					node_holder[i] = -1;
					weight_holder[i] = -1;
				}
				counter_i_one_node = 0;
				if (r != 0) {
					if (delete_bottom[delete_bottom_counter] == -1) {
						node_holder[counter_i_one_node] = counter - columns;
						weight_holder[counter_i_one_node++] = draw(minimal_weight, maximum_weight);
					} else {
						delete_bottom[delete_bottom_counter] = -1;
					}
				}
				if (c != 0 && delete_right == 0) {
					node_holder[counter_i_one_node] = counter - 1;
					weight_holder[counter_i_one_node++] = draw(minimal_weight, maximum_weight);
				} else {
					delete_right = 0;
				}
				if (c != columns - 1) {
					if (draw(0.0, 1.0) >= 1 - existance_chance) {
						node_holder[counter_i_one_node] = counter + 1;
						weight_holder[counter_i_one_node++] = draw(minimal_weight, maximum_weight);
					} else {
						delete_right++;
					}
				}
				if (r != rows - 1) {
					if (draw(0.0, 1.0) >= 1 - existance_chance) {
						node_holder[counter_i_one_node] = counter + columns;
						weight_holder[counter_i_one_node++] = draw(minimal_weight, maximum_weight);
					} else {
						delete_bottom[delete_bottom_counter] = 0;
					}
				}
				graph[counter++].add(node_holder, weight_holder);
				delete_bottom_counter++;
			}
			delete_bottom_counter = 0;
		}
		Graph full_graph = new Graph(Sum);
		full_graph.addNode(graph, columns, rows);
		return full_graph;
	}
}