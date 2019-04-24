import java.util.Random;

public class Chromosome {

	private static final Random random = new Random();
	private final int size = 10;
	private final double fitness;
	private final String[] tuple;

	public Chromosome() {
		tuple = new String[size];
		for (int i = 0; i < size; i++) {
			boolean duplicate;
			String j;
			do {
				duplicate = false;
				j = FaultMatrix.TESTS[random.nextInt(FaultMatrix.NUMBER_OF_TESTS)];
				for (int k = i - 1; k >= 0; k--) {
					if (j.equals(tuple[k])) {
						duplicate = true;
					}
				}
			} while (duplicate);
			tuple[i] = j;
		}
		fitness = fitness();
	}

	public Chromosome(String[] tuple) {
		this.tuple = tuple;
		fitness = fitness();
	}

	private String[] chopAndChange(String[] parentTuple, String[] parentTuple2, int crossoverPoint) {
		String[] childTuple = new String[size];
		for (int i = 0; i < crossoverPoint; i++) {
			childTuple[i] = parentTuple[i];
		}
		int j = 0;
		for (int i = crossoverPoint; i < size; i++) {
			boolean duplicate;
			do {
				duplicate = false;
				for (int k = i - 1; k >= 0; k--) {
					if (parentTuple2[j].equals(childTuple[k])) {
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
		int crossoverPoint = random.nextInt(size - 1) + 1;
		return new Chromosome[] { new Chromosome(chopAndChange(this.tuple, chromosome.tuple, crossoverPoint)),
				new Chromosome(chopAndChange(chromosome.tuple, this.tuple, crossoverPoint)) };
	}

	private double fitness() {
		double count;
		double fitness = 0;
		boolean[] found = new boolean[FaultMatrix.NUMBER_OF_FAULTS];
		for (int i = 0; i < size; i++) {
			count = 0;
			for (int j = 0; j < FaultMatrix.NUMBER_OF_FAULTS; j++) {
				if (FaultMatrix.FAULT_MATRIX.get(tuple[i])[j] == 1 && !found[j]) {
					count++;
					found[j] = true;
				}
			}
			fitness += count / (i + 1);
		}
		fitness /= FaultMatrix.NUMBER_OF_FAULTS;
		return fitness;
	}

	public double getFitness() {
		return fitness;
	}

	public Chromosome mutate(double mutationRate) {
		String[] tuple = this.tuple;
		for (int i = 0; i < size - 1; i++) {
			if (random.nextDouble() < mutationRate) {
				String temp = tuple[i];
				tuple[i] = tuple[i + 1];
				tuple[i + 1] = temp;
			}
		}
		return new Chromosome(tuple);
	}

	public void print() {
		for (int i = 0; i < size; i++) {
			System.out.print(tuple[i] + " ");
		}
		System.out.println("f = " + fitness);
	}

}
