package resource;

import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Random;
import java.util.concurrent.RecursiveAction;

public class Geneticist extends RecursiveAction {

	private static final long serialVersionUID = -9205514323062265708L;
	private Random random;
	private Table[] individual;
	private boolean solution;
	private int fitnessEvaluation;
	private long time;

	public Geneticist(int populationSize, int queensSize, int fitnessEvaluation) {
		this.fitnessEvaluation = fitnessEvaluation;
		random = new Random();

		individual = new Table[populationSize];

		for (int i = 0; i < populationSize; i++) {
			int[] queens = new int[queensSize];

			boolean[] used = new boolean[queensSize];
			for (int j = 0; j < queensSize;) {
				int poss = random.nextInt(queensSize);
				if (!used[poss]) {
					queens[j] = poss;
					used[poss] = true;
					j++;
				}
			}

			individual[i] = new Table(queens);
		}
	}

	@Override
	protected void compute() {
		time = System.currentTimeMillis();
		for (int fe = 0; !solution && fe < fitnessEvaluation; fe++) {
			Queue<Table> tables = new PriorityQueue<>();
			for (Table table : individual) {
				tables.add(table);
			}

			// Select parents
			Queue<Table> fathers = new PriorityQueue<>();
			for (int i = 0; i < 5; i++) {
				fathers.add(individual[random.nextInt(individual.length)]);
			}

			int[] fatherQueens0 = fathers.poll().getQueens();
			int[] fatherQueens1 = fathers.poll().getQueens();

			// Recombine pairs of parents
			int[] childrenQueens0 = new int[fatherQueens0.length];
			int[] childrenQueens1 = new int[fatherQueens0.length];

			int crossoverPoint = random.nextInt(fatherQueens0.length);
			boolean[] usedGenotype0 = new boolean[fatherQueens0.length];
			boolean[] usedGenotype1 = new boolean[fatherQueens0.length];
			for (int i = 0; i < crossoverPoint; i++) {
				childrenQueens0[i] = fatherQueens0[i];
				usedGenotype0[fatherQueens0[i]] = true;
				childrenQueens1[i] = fatherQueens1[i];
				usedGenotype1[fatherQueens1[i]] = true;
			}

			int childPoint0 = crossoverPoint;
			int childPoint1 = crossoverPoint;
			for (int i = crossoverPoint; i < fatherQueens0.length; i++) {
				if (!usedGenotype0[fatherQueens1[i]]) {
					childrenQueens0[childPoint0] = fatherQueens1[i];
					usedGenotype0[fatherQueens1[i]] = true;
					childPoint0++;
				}
				if (!usedGenotype1[fatherQueens0[i]]) {
					childrenQueens1[childPoint1] = fatherQueens0[i];
					usedGenotype1[fatherQueens0[i]] = true;
					childPoint1++;
				}
			}
			for (int i = 0; i < crossoverPoint; i++) {
				if (!usedGenotype0[fatherQueens1[i]]) {
					childrenQueens0[childPoint0] = fatherQueens1[i];
					usedGenotype0[fatherQueens1[i]] = true;
					childPoint0++;
				}
				if (!usedGenotype1[fatherQueens0[i]]) {
					childrenQueens1[childPoint1] = fatherQueens0[i];
					usedGenotype1[fatherQueens0[i]] = true;
					childPoint1++;
				}
			}

			// Mutate the resulting offspring
			boolean mutateChildren0 = random.nextInt(100) < 80;
			if (mutateChildren0) {
				int positionI = random.nextInt(fatherQueens0.length);
				int positionJ = random.nextInt(fatherQueens0.length);
				int queen = childrenQueens0[positionI];
				childrenQueens0[positionI] = childrenQueens0[positionJ];
				childrenQueens0[positionJ] = queen;
			}

			boolean mutateChildren1 = random.nextInt(100) < 80;
			if (mutateChildren1) {
				int positionI = random.nextInt(fatherQueens0.length);
				int positionJ = random.nextInt(fatherQueens0.length);
				int queen = childrenQueens1[positionI];
				childrenQueens1[positionI] = childrenQueens1[positionJ];
				childrenQueens1[positionJ] = queen;
			}

			// Evaluate new candidates
			Table children0 = new Table(childrenQueens0);
			Table children1 = new Table(childrenQueens1);
			tables.add(children0);
			tables.add(children1);

			// Select individuals for the next generation
			for (int i = 0; i < individual.length; i++) {
				individual[i] = tables.poll();
			}

			solution = individual[0].getEval() == fatherQueens0.length
					* (fatherQueens0.length - 1);
		}
		time = System.currentTimeMillis() - time;
	}

	public boolean isSolution() {
		return solution;
	}

	public Table getIndividual() {
		return individual[0];
	}

	public long getTime() {
		return time;
	}

	@Override
	public String toString() {
		String line = "";
		int[] queens = individual[0].getQueens();
		for (int j = 0; j < queens.length; j++) {
			for (int k = 0; k < queens.length; k++) {
				if (queens[j] == k) {
					line += "* ";
				} else {
					line += "- ";
				}
			}
			line += "\n";
		}
		line += "Solusión: " + isSolution() + "\n" + "Tiempo: " + time + "ms";
		return line;
	}
}
