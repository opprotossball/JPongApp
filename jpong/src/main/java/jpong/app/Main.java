package jpong.app;

import java.io.IOException;

public class Main {
	public static void main(String args[]) throws IOException {
		int kolumny = 8;
		int min = 0;
		int max = 1;
		int wiersze = 8;
		int wszystkie = kolumny * wiersze;
		Graph full_graph = new Graph(wszystkie);
		ConnectedEdges nowy = new ConnectedEdges();
		full_graph = nowy.generate(min, max, kolumny, wiersze);
		CheckConnection newer = new CheckConnection();
		int spojny = newer.checkconnection(full_graph);
		if (spojny == 0) {
			System.out.println("graf jest spojny");
		} else {
			System.out.println("graf jest nie spojny");
		}
		nowy.saveGraph("text.txt", full_graph);
		FindPaths findPaths = new FindPaths();
		Integer[] previous = findPaths.findPath(full_graph, 0);
		for (Integer prev : previous){
			System.out.print(prev + " ");
		}
	}
}