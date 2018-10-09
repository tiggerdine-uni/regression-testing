import java.util.Random;

public class GeneticAlgorithm {

	private static final double CROSSOVER_RATE = 0.75;
	private static final double MUTATION_RATE = 0.05;
	private static final int NUMBER_OF_ITERATIONS = 1000;
	private static final int POPULATION_SIZE = 100;
	private Chromosome[] population;
	private Random random;

	public GeneticAlgorithm() {
		population = new Chromosome[POPULATION_SIZE];
		for (int i = 0; i < POPULATION_SIZE; i++) {
			population[i] = new Chromosome();
		}
		random = new Random();
	}
	
	public void printPopulation() {
		for(int i = 0; i < POPULATION_SIZE; i++) {
			System.out.println("fitness of " + i + " = " + population[i].getFitness());
		}
	}

	private Chromosome fittestChromosome() {
		Chromosome fittestChromosome = population[0];
		for (int i = 1; i < POPULATION_SIZE; i++) {
			if (population[i].getFitness() > fittestChromosome.getFitness()) {
				fittestChromosome = population[i];
			}
		}
		return fittestChromosome;
	}

	public Chromosome run() {
		for (int i = 0; i < NUMBER_OF_ITERATIONS; i++) {
			Chromosome[] population2 = new Chromosome[POPULATION_SIZE];
			for (int j = 0; j < POPULATION_SIZE / 2; j++) {
				Chromosome[] chromosomes = selectChromosomes();
				if (random.nextDouble() < CROSSOVER_RATE) {
					chromosomes = chromosomes[0].crossover(chromosomes[1]);
				}
				population2[j * 2] = chromosomes[0].mutate(MUTATION_RATE);
				population2[j * 2 + 1] = chromosomes[1].mutate(MUTATION_RATE);
			}
			population = population2;
			System.out.println("generation " + i + " fittest " + fittestChromosome().getFitness());
		}
		return fittestChromosome();
	}

	public Chromosome selectChromosome() {
		double f = 0;
		for (int i = 0; i < POPULATION_SIZE; i++) {
			
			f += population[i].getFitness();
			// System.out.println("f += " + population[i].getFitness() + " = " + f);
		}
		double r = f * random.nextDouble();
		// System.out.println("r = " + r);
		int i = 0;
		while (r > 0) {
			if (r > population[i].getFitness()) {
				r -= population[i].getFitness();
				i++;
			} else {
				// System.out.println("RETURNING " + i);
				return population[i];
			}
		}
		return population[0];
	}

	private Chromosome[] selectChromosomes() {
		Chromosome[] chromosomes = new Chromosome[2];
		chromosomes[0] = selectChromosome();
		do {
			chromosomes[1] = selectChromosome();
		} while (chromosomes[1].equals(chromosomes[0]));
		return chromosomes;
	}

}
