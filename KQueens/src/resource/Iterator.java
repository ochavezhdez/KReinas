package resource;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.RecursiveAction;

public class Iterator extends RecursiveAction {

	private static final long serialVersionUID = 223351043383599362L;
	private List<Geneticist> geneticists;

	public Iterator(int queensSize, int populationSize, int fitnessEvaluation,
			int iterations) {
		geneticists = new LinkedList<>();
		for (int i = 0; i < iterations; i++) {
			Geneticist geneticist = new Geneticist(populationSize, queensSize,
					fitnessEvaluation);
			geneticists.add(geneticist);
		}
	}

	@Override
	protected void compute() {
		invokeAll(geneticists);
		for (int i = 0; i < geneticists.size(); i++) {
			Geneticist geneticist = geneticists.get(i);
			geneticist.join();
			System.out.println("Iterasión: " + i);
			System.out.println(geneticist.toString() + "\n");
		}

		double suma = 0;
		for (Geneticist geneticist : geneticists) {
			suma += geneticist.getTime();
		}

		double promedio = suma / geneticists.size();
		System.out.println("Promedio: " + promedio + "ms");

		double sumaD = 0;
		for (Geneticist geneticist : geneticists) {
			sumaD += Math.pow(geneticist.getTime() - promedio, 2);
		}

		double desviacion = Math.sqrt(sumaD / (geneticists.size() - 1));

		System.out.println("Desviación estándar: " + desviacion + "ms");
	}
}
