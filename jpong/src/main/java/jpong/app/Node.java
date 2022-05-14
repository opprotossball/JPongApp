package jpong.app;

public class Node {
	private int[] connected;
	private double[] weights;

	public Node() {
		connected = new int[5];
		weights = new double[5];
		for (int i = 0; i < 5; i++) {
			connected[i] = -1;
			weights[i] = -1;
		}
	}

	public void add(int[] node, double[] weight) {
		System.arraycopy(node, 0, connected, 0, node.length);
		System.arraycopy(weight, 0, weights, 0, weight.length);
	}

	public int shownode(int number) {
		return connected[number];
	}
	public int[] showallnode(){
		return connected;
	}

	public double showweights(int number) {
		return this.weights[number];
	}
	public double[] showallweights(){
		return weights;
	}
}
