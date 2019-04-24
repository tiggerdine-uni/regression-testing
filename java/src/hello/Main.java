package hello;

public class Main {

	public static final String STRING = "Hello World!";
	public static final char[] CHARACTERS = STRING.toCharArray();
	public static final int NUMBER_OF_CHARACTERS = CHARACTERS.length;

	public static void main(String[] args) {
		long start = System.currentTimeMillis();
		GeneticAlgorithm g = new GeneticAlgorithm();
		g.run();
		long stop = System.currentTimeMillis();
		System.out.println(stop - start + " ms to find the string \"" + STRING + "\"");
	}

}
