import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

public class FaultMatrix {

	private static final String NAME = "bigfaultmatrix";
	public static final HashMap<String, int[]> FAULT_MATRIX = faultMatrix();
	public static final String[] TESTS = FAULT_MATRIX.keySet().toArray(new String[0]);
	public static int NUMBER_OF_FAULTS;
	public static int NUMBER_OF_TESTS;

	public static HashMap<String, int[]> faultMatrix() {
		HashMap<String, int[]> faultMatrix = new HashMap<>();
		try {
			BufferedReader br = new BufferedReader(new FileReader(NAME + ".txt"));
			String s;
			while ((s = br.readLine()) != null) {
				NUMBER_OF_TESTS++;
			}
			// System.out.println("NUMBER_OF_TESTS = " + NUMBER_OF_TESTS);

			br.close();
			br = new BufferedReader(new FileReader(NAME + ".txt"));
			br.mark(128);
			String[] ss = br.readLine().split(",");
			NUMBER_OF_FAULTS = ss.length - 1;
			// System.out.println("NUMBER_OF_FAULTS = " + NUMBER_OF_FAULTS);

			br.reset();
			while ((s = br.readLine()) != null) {
				ss = s.split(",");
				int[] is = new int[NUMBER_OF_FAULTS];
				for (int i = 0; i < NUMBER_OF_FAULTS; i++) {
					is[i] = Integer.parseInt(ss[i + 1]);
				}
				// System.out.print(ss[0] + " ");
				// for (int i : is) {
				// System.out.print(i + " ");
				// }
				// System.out.println();
				faultMatrix.put(ss[0], is);
			}
			br.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return faultMatrix;
	}

}
