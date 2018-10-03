import java.util.Random;

public class Chromosome {

	private static final FaultMatrix faultMatrix = new FaultMatrix();
	private static final Random random = new Random();
	private static final int SUITE_SIZE = FaultMatrix.suiteSize();
	private static final int TUPLE_SIZE = 6;
	private double fitness;
	private int[] tuple;

	public Chromosome() {
		tuple = new int[TUPLE_SIZE];
		tuple[0] = random.nextInt(SUITE_SIZE);
		for (int i = 1; i < TUPLE_SIZE; i++) {
			boolean duplicate;
			int j;
			do {
				duplicate = false;
				j = random.nextInt(SUITE_SIZE);
				for (int k = i - 1; k >= 0; k--) {
					if (j == tuple[k]) {
						duplicate = true;
					}
				}
			} while (duplicate);
			tuple[i] = j;
		}
		fitness = fitness();
	}

	public Chromosome(int[] tuple) {
		this.tuple = tuple;
		fitness = fitness();
	}

	private int[] chopAndChange(int[] parentTuple, int[] parentTuple2, int crossoverPoint) {
		int[] childTuple = new int[TUPLE_SIZE];
		for (int i = 0; i < crossoverPoint; i++) {
			childTuple[i] = parentTuple[i];
		}
		int j = 0;
		for (int i = crossoverPoint; i < TUPLE_SIZE; i++) {
			boolean duplicate;
			do {
				duplicate = false;
				for (int k = i - 1; k >= 0; k--) {
					if (parentTuple2[j] == childTuple[k]) {
						duplicate = true;
						// TODO this breaks the for loop doesn't it?
						break;
					}
				}
				if (!duplicate) {
					childTuple[i] = parentTuple2[j];
				}
				j++;
			} while (duplicate);
		}
		return childTuple;
	}

	public Chromosome[] crossover(Chromosome chromosome) {
		int crossoverPoint = random.nextInt(TUPLE_SIZE - 1) + 1;
		System.out.println("crossoverPoint = " + crossoverPoint);
		return new Chromosome[] { new Chromosome(chopAndChange(this.tuple, chromosome.tuple, crossoverPoint)),
				new Chromosome(chopAndChange(chromosome.tuple, this.tuple, crossoverPoint)) };
	}

	private double fitness() {
		// TODO fitness
		return random.nextInt(10) + random.nextDouble();
	}

	public double getFitness() {
		return fitness;
	}

	public Chromosome mutate(double mutationRate) {
		// TODO mutate
		return null;
	}

	public void printTuple() {
		for (int i = 0; i < TUPLE_SIZE; i++) {
			System.out.print(tuple[i] + " ");
		}
		System.out.println();
	}

}
