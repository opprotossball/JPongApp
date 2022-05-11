package jpong.app;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Reader {
	public static Graph readGraph(String path) throws IOException {
		BufferedReader br = new BufferedReader(new FileReader(path));
		String line;
		int graph_counter = 0;
		line = br.readLine();
		String[] result = line.split(" ");
		int columns = Integer.parseInt(result[0]);
		int rows = Integer.parseInt(result[1]);
		Node[] graph = new Node[columns * rows];
		for (int i = 0; i < columns * rows; i++)
			graph[i] = new Node();
		while ((line = br.readLine()) != null) {
			line = line.replace(":", "");
			line = line.replace("  ", " ");
			String[] words = line.split(" ");
			if (words.length < 9) {
				try {
					int[] node_holder = new int[5];
					double[] weight_holder = new double[5];
					for (int i = 0; i < 5; i++) {
						node_holder[i] = -1;
						weight_holder[i] = -1;
					}
					int node_counter = 0;
					int weight_counter = 0;
					int devider = 0;
					for (String word : words) {
						word = word.replace("\t", "");
						if (devider == 0) {
							devider = 1;
							node_holder[node_counter++] = Integer.parseInt(word);
						} else {
							devider = 0;
							weight_holder[weight_counter++] = Double.parseDouble(word);
						}
					}
					graph[graph_counter++].add(node_holder, weight_holder);
				} catch (NumberFormatException e) {
					br.close();
					throw new IOException("Line: \"" + line + "\" incorrect data format");
				}
			} else {
				br.close();
				throw new IOException("Line: \"" + line + "\" too many arguments inside line");
			}
		}
		br.close();
		Graph full_graph = new Graph(columns * rows);
		full_graph.addNode(graph, columns, rows);
		return full_graph;
	}
}
