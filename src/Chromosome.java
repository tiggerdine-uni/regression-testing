import java.util.Random;

public class Chromosome {

	private static final Random random = new Random();
	private double fitness;

	public Chromosome() {
		fitness = fitness();
	}

	private double fitness() {
		// TODO fitness
		return random.nextInt(10) + random.nextDouble();
	}

	public Chromosome[] crossover(Chromosome chromosome) {
		// TODO crossover
		return null;
	}

	public Chromosome mutate(double mutationRate) {
		// TODO mutate
		return null;
	}

	public double getFitness() {
		return fitness;
	}

}
