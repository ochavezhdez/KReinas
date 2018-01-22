package gui;

import java.util.Scanner;
import java.util.concurrent.ForkJoinPool;

import resource.Iterator;

public class MainKQueen {

	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		System.out.println("Ejercicio de las K reinas.");
		System.out.println("Introdusca la cantidad de reinas a calcular.");
		int queensSize = scanner.nextInt();
		System.out
				.println("Introduzca la cantidad de individuos en la población.");
		int populationSize = scanner.nextInt();
		System.out
				.println("Introduzca la cantidad de evaluaciones a realizar.");
		int fitnessEvaluation = scanner.nextInt();
		System.out.println("Introduzca la cantidad de iteraciones.");
		int iterations = scanner.nextInt();
		scanner.close();

		System.out.println("Calculando, por favor espere");
		Iterator iterator = new Iterator(queensSize, populationSize,
				fitnessEvaluation, iterations);
		ForkJoinPool pool = new ForkJoinPool();
		pool.invoke(iterator);
		System.out.println("Finalizado");
	}
}
