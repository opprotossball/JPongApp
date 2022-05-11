package jpong.app;

public class ConnectedEdges extends Generator {

	public Graph generate(int minimal_weight, int maximum_weight, int columns, int rows) {
		int sum = columns * rows;
		Graph full_graph = new Graph(sum);
		CheckConnection checker = new CheckConnection();
		RandomEdges test = new RandomEdges();
		full_graph = test.generate(minimal_weight, maximum_weight, columns, rows);
		if (checker.checkconnection(full_graph) != 0) {
			while (checker.checkconnection(full_graph) != 0) {
				full_graph = test.generate(minimal_weight, maximum_weight, columns, rows);
				test.increase_chance();
			}
		}
		return full_graph;
	}
}
