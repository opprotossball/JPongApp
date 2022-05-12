package jpong.app;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Random;

public abstract class Generator {
	public double existance_chance = 0.9;

	public double draw(double minimal_weight, double maximum_weight) {
		double random = new Random().nextDouble();
		double last = minimal_weight + (random * (maximum_weight - minimal_weight));
		return last;
	}

	public void saveGraph(String text, Graph holder) throws IOException {
		File file = new File(text);
		FileWriter filewrite = new FileWriter(file);
		PrintWriter printwrite = new PrintWriter(filewrite);
		printwrite.println(holder.show_rows() + " " + holder.show_columns());
		for (Node node : holder.show_nodes()) {
			printwrite.print("\t");
			for (int i = 0; i < 5; i++) {
				if (node.shownode(i) != -1 || node.showweights(i) != -1)
					printwrite.print(node.shownode(i) + ":" + node.showweights(i) + " ");
			}
			printwrite.print(System.getProperty("line.separator"));
		}
		printwrite.close();
	}
	public void increase_chance() {
		existance_chance = existance_chance + 0.02;
	}

	public abstract Graph generate(int minimal_weight, int maximum_weight, int columns, int rows);
}
