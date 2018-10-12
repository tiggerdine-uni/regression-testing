import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;

// TODO
public class FaultMatrix {

	private static final String NAME = "smallfaultmatrix";
	public static final int[][] FAULT_MATRIX = faultMatrix();
	private static int numberOfColumns;
	private static int numberOfLines;

	public static int suiteSize() {
		return numberOfLines;
	}

	public static int numberOfFaults() {
		return numberOfColumns;
	}

	public static int[][] faultMatrix() {
		// TODO ugly but works
		try {
			BufferedReader br = new BufferedReader(new FileReader(NAME + ".txt"));
			numberOfLines = 0;
			String line;
			while ((line = br.readLine()) != null) {
				numberOfLines++;
			}
			br.close();
			br = new BufferedReader(new FileReader(NAME + ".txt"));
			br.mark(128);
			String[] splitLine = br.readLine().split(",");
			numberOfColumns = splitLine.length - 1;
			int[][] faultMatrix = new int[numberOfLines][numberOfColumns];
			br.reset();
			while ((line = br.readLine()) != null) {
				// System.out.println(line);
				splitLine = line.split(",");
				int i = Integer.parseInt(splitLine[0].substring(1, splitLine[0].length()));
				splitLine = Arrays.copyOfRange(line.split(","), 1, numberOfColumns + 1);
				// for (int i = 0; i < splitLine.length; i++) {
				// System.out.print(splitLine[i] + " ");
				// }
				// System.out.println();
				for (int j = 0; j < numberOfColumns; j++) {
					// System.out.print(Integer.parseInt(splitLine[j]));
					faultMatrix[i][j] = Integer.parseInt(splitLine[j]);
				}
				// System.out.println();
			}
			return faultMatrix;
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// System.out.println(numberOfColumns + " " + numberOfLines);
		return null;
	}

	public static void printFaultMatrix() {
		for (int i = 0; i < numberOfLines; i++) {
			System.out.print("t" + i + " ");
			for (int j = 0; j < numberOfColumns; j++) {
				System.out.print(FAULT_MATRIX[i][j] + " ");
			}
			System.out.println();
		}
	}

}
