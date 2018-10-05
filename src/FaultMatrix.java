import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class FaultMatrix {

	private static final String NAME = "smallfaultmatrix";
	private static final int[][] FAULT_MATRIX = faultMatrix();
	private static int numberOfColumns;
	private static int numberOfLines;

	public static int suiteSize() {
		return numberOfLines;
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
			numberOfColumns = 0;
			br.close();
			br = new BufferedReader(new FileReader(NAME + ".txt"));
			br.mark(128);
			String[] splitLine = br.readLine().split(",");
			numberOfColumns = splitLine.length - 1;
			int[][] faultMatrix = new int[numberOfLines][numberOfColumns];
			br.reset();
			while ((line = br.readLine()) != null) {
				splitLine = line.split(",");
				for (int j = 1; j < numberOfColumns; j++) {
					faultMatrix[Integer.parseInt(splitLine[0].substring(1, splitLine[0].length()))][j] = Integer
							.parseInt(splitLine[j]);
				}
			}
			return faultMatrix;
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(numberOfColumns + " " + numberOfLines);
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
