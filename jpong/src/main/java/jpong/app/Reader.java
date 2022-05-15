package jpong.app;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Reader {

	public static final int maximum_number_of_elements_in_node = 5;
	public static final int basic_value = -1;

	public static boolean test_existance(int[] orginal, int[] correct) {
		for (int i = 0; i < maximum_number_of_elements_in_node; i++) {
			int counter = 0;
			for (int k = 0; k < maximum_number_of_elements_in_node; k++) {
				if (orginal[i] == correct[k] && orginal[i] != -1) {
					counter++;
				}
			}
			if (counter == 0 && orginal[i] != -1) {
				return false;
			}
		}
		return true;
	}
	public static boolean test_existance_for_one_element(int[] orginal, int correct) {
			int counter = 0;
			for (int k = 0; k < maximum_number_of_elements_in_node; k++) {
				if (orginal[k] == correct) {
					counter++;
				}
		}
		if (counter == 0) {
			return true;
		}
		return false;
	}

	public static Graph readGraph(String path) throws IOException {
		BufferedReader br = new BufferedReader(new FileReader(path));
		String line;
		int current_node = 0;
		line = br.readLine();
		String[] result = line.split(" ");
		if (result.length != 2) {
			br.close();
			throw new IOException("incorrect data format inside file");
		}
		try {
			int columns = Integer.parseInt(result[1]);
			int rows = Integer.parseInt(result[0]);
			int exist_right = -1;
			int exist_bottom_counter = 0;
			int[] exist_bottom = new int[columns];
			for (int i = 0; i < columns; i++) {
				exist_bottom[i] = basic_value;
			}
			AllEdges example = new AllEdges();
			Graph correct_graph = example.generate(0, 1, columns, rows);
			Node[] graph = new Node[columns * rows];
			for (int i = 0; i < columns * rows; i++)
				graph[i] = new Node();
			while ((line = br.readLine()) != null) {
				line = line.replace("\t", "");
				line = line.replace(":", "!");
				line = line.replace("  ", "!");
				line = line.replace(" ", "");
				String[] words = line.split("!");
				if (words.length < 9) {
					int node_counter = 0;
					int weight_counter = 0;
					int devider = 0;
					int[] node_holder = new int[maximum_number_of_elements_in_node];
					double[] weight_holder = new double[maximum_number_of_elements_in_node];
					for (int i = 0; i < maximum_number_of_elements_in_node; i++) {
						node_holder[i] = basic_value;
						weight_holder[i] = basic_value;
					}
					for (String word : words) {
						if (devider == 0) {
							devider = 1;
							if (Integer.parseInt(word) < 0) {
								br.close();
								throw new IOException(
										"incorrect data format inside file (value is negative in line number: "
												+ (current_node + 2) + ")");
							}
							node_holder[node_counter++] = Integer.parseInt(word);
						} else {
							devider = 0;
							if (Double.parseDouble(word) < 0) {
								br.close();
								throw new IOException(
										"incorrect data format inside file (value is negative in line number: "
												+ (current_node + 2) + ")");
							}
							weight_holder[weight_counter++] = Double.parseDouble(word);
						}
					}
					if (exist_right != basic_value) {
						if (test_existance_for_one_element(node_holder, exist_right)) {
							br.close();
							throw new IOException(
									"incorrect data format inside file (node should be connected to node number: "
											+ exist_right + " in line: " + (current_node + 2) + ")");
						}
						exist_right = basic_value;
					}
					else{
						if (test_existance_for_one_element(node_holder, current_node-1) == false && current_node != 0) {
							br.close();
							int lacking_node_left = current_node -1;
							throw new IOException(
									"incorrect data format inside file (node should be connected to node number: "
											+ current_node + " in line: " + (lacking_node_left + 2) + ")");
						}
					}
					if (exist_bottom[exist_bottom_counter] != basic_value) {
						if (test_existance_for_one_element(node_holder, exist_bottom[exist_bottom_counter])) {
							br.close();
							throw new IOException(
									"incorrect data format inside file (node should be connected to node number: "
											+ exist_bottom[exist_bottom_counter] + " in line: " + (current_node + 2)
											+ ")");
						} else {
							exist_bottom[exist_bottom_counter] = basic_value;
						}
					}
					else{
						if(test_existance_for_one_element(node_holder, current_node-columns) == false && current_node != columns-1){
							br.close();
							int lacking_node_top = current_node - columns;
							throw new IOException(
									"incorrect data format inside file (node should be connected to node number: "
											+ current_node + " in line: " + (lacking_node_top + 2)
											+ ")");
						}
					}
					for (int i = 0; i < maximum_number_of_elements_in_node; i++) {
						if (node_holder[i] == current_node + columns) {
							exist_bottom[exist_bottom_counter] = current_node;
						}
						if (node_holder[i] == current_node + 1) {
							exist_right = current_node;
						}
					}
					if (test_existance(node_holder, correct_graph.show_connected(current_node)) == false) {
						br.close();
						throw new IOException("incorrect data format inside file (wrong nodes in line number: "
								+ (current_node + 2) + ")");
					}
					graph[current_node++].add(node_holder, weight_holder);
					if (exist_bottom_counter == columns - 1) {
						exist_bottom_counter = 0;
					} else {
						exist_bottom_counter++;
					}
				} else {
					br.close();
					throw new IOException("incorrect data format inside file (too many elements in line number: "
							+ (current_node + 2) + ")");
				}
			}
			br.close();
			Graph full_graph = new Graph(columns * rows);
			full_graph.addNode(graph, columns, rows);
			return full_graph;
		} catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
			br.close();
			throw new IOException("incorrect data format inside file");
		}
	}
}