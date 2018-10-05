import java.util.Random;

public class Chromosome {

	private static final Random random = new Random();
	private final int suiteSize = FaultMatrix.suiteSize();
	private final int tupleSize = 6;
	private final double fitness;
	private final int[] tuple;

	public Chromosome() {
		tuple = new int[tupleSize];
		tuple[0] = random.nextInt(suiteSize);
		for (int i = 1; i < tupleSize; i++) {
			boolean duplicate;
			int j;
			do {
				duplicate = false;
				j = random.nextInt(suiteSize);
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
		int[] childTuple = new int[tupleSize];
		for (int i = 0; i < crossoverPoint; i++) {
			childTuple[i] = parentTuple[i];
		}
		int j = 0;
		for (int i = crossoverPoint; i < tupleSize; i++) {
			boolean duplicate;
			do {
				duplicate = false;
				for (int k = i - 1; k >= 0; k--) {
					if (parentTuple2[j] == childTuple[k]) {
						duplicate = true;
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
		int crossoverPoint = random.nextInt(tupleSize - 1) + 1;
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
		int[] tuple = this.tuple;
		for (int i = 0; i < tupleSize - 1; i++) {
			if (random.nextDouble() < mutationRate) {
				int temp = tuple[i];
				tuple[i] = tuple[i + 1];
				tuple[i + 1] = temp;
			}
		}
		return new Chromosome(tuple);
	}

	public void printTuple() {
		for (int i = 0; i < tupleSize; i++) {
			System.out.print(tuple[i] + " ");
		}
		System.out.println();
	}

}
