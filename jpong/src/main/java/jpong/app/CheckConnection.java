package jpong.app;

import java.util.LinkedList;

public class CheckConnection extends Graph {
	private int[] previous;
	final int START = 0;

	public int checkconnection(Graph graph) {
		int top_of_queue = 0;
		int result_counter = 0;
		int columns = graph.show_columns();
		int rows = graph.show_rows();
		int sum = rows * columns;
		previous = new int[columns * rows];
		for (int i = 0; i < sum; i++) {
			previous[i] = -1;
		}
		LinkedList<Integer> queue = new LinkedList<Integer>();
		queue.add(START);
		previous[START] = 0;
		while (queue.size() != 0) {
			top_of_queue = queue.pollLast();
			if (queue.isEmpty() == false) {
				queue.removeFirst();
			}
			Node node = graph.show_node(top_of_queue);
			int element_counter = 0;
			while (node.shownode(element_counter) != -1) {
				if (previous[node.shownode(element_counter)] == -1) {
					queue.addLast(node.shownode(element_counter));
					previous[node.shownode(element_counter)] = top_of_queue;
				}
				element_counter++;
			}
		}
		for (int i = 0; i < sum; i++) {
			if (previous[i] == -1) {
				result_counter++;
			}
		}
		if (result_counter != 0) {
			return 1;
		}
		return 0;
	}
}
