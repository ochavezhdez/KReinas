package resource;

public class Table implements Comparable<Table> {

	private int[] queens;

	public Table(int[] queens) {
		this.queens = queens;
	}

	public int[] getQueens() {
		return queens;
	}

	public int getEval() {
		int eval = 0;

		for (int i = 0; i < queens.length - 1; i++) {
			int queenI = queens[i];
			for (int j = i + 1; j < queens.length; j++) {
				int queeY = queens[j];

				if (Math.abs(i - j) != Math.abs(queenI - queeY)) {
					eval+=2;
				}
			}
		}

		return eval;
	}

	@Override
	public int compareTo(Table o) {
		return o.getEval() - getEval();
	}
}
