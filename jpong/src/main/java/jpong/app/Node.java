package jpong.app;

public class Node {
	public static final int maximum_number_of_elements_in_node = 5;
	public static final int basic_value = -1;
	private int[] connected;
	private double[] weights;

	public Node() {
		connected = new int[maximum_number_of_elements_in_node];
		weights = new double[maximum_number_of_elements_in_node];
		for (int i = 0; i < maximum_number_of_elements_in_node; i++) {
			connected[i] = basic_value;
			weights[i] = basic_value;
		}
	}

	public void add(int[] node, double[] weight) {
		System.arraycopy(node, 0, connected, 0, node.length);
		System.arraycopy(weight, 0, weights, 0, weight.length);
	}

	public int show_node(int number) {
		return connected[number];
	}

	public int[] show_all_node() {
		return connected;
	}

	public double show_weights(int number) {
		return weights[number];
	}

	public double[] show_all_weights() {
		return weights;
	}
}
