package jpong.app;

public class ConnectedEdges extends RandomEdges {
	public Graph generate(int minimal_weight, int maximum_weight, int columns, int rows) {
		int sum = columns * rows;
		Graph full_graph = new Graph(sum);
		CheckConnection checker = new CheckConnection();
		full_graph = super.generate(minimal_weight, maximum_weight, columns, rows);
		if (checker.checkconnection(full_graph) != 0) {
			while (checker.checkconnection(full_graph) != 0) {
				full_graph = super.generate(minimal_weight, maximum_weight, columns, rows);
				increase_chance();
			}
		}
		return full_graph;
	}
}
