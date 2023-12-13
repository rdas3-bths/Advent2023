import java.util.ArrayList;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Day9 {
    public static void main(String[] args) throws FileNotFoundException {
        ArrayList<String> fileData = getFileData("Day9_Input");
        int total = 0;
        for (String line : fileData) {
            String[] splitNumbers = line.split(" ");
            int [] sequence = new int[splitNumbers.length];
            for (int i = 0; i < splitNumbers.length; i++) {
                sequence[i] = Integer.parseInt(splitNumbers[i]);
            }
            ArrayList<int[]> allSequences = buildSequences(sequence);
            int offset = -1;
            for (int i = allSequences.size()-1; i >= 0; i--) {
                int[] currentSequence = allSequences.get(i);
                if (allZeros(currentSequence)) {
                    offset = 0;
                }
                else {
                    offset = currentSequence[0] - offset;
                }
            }
            total = total + offset;
        }
        System.out.println(total);



    }

    public static ArrayList<String> getFileData(String inputFile) throws FileNotFoundException {
        ArrayList<String> fileData = new ArrayList<String>();
        File f = new File(inputFile);
        Scanner s = new Scanner(f);
        while (s.hasNext()) {
            fileData.add(s.nextLine());
        }
        return fileData;
    }

    public static ArrayList<int[]> buildSequences(int[] sequence) {
        ArrayList<int[]> sequences = new ArrayList<int[]>();
        sequences.add(sequence);
        int processSequence = 0;
        int[] nextSequence = new int[1];
        nextSequence[0] = -1;
        while (!allZeros(nextSequence)) {

            nextSequence = new int[sequences.get(processSequence).length-1];
            for (int i = 0; i < nextSequence.length; i++) {
                int[] previous = sequences.get(processSequence);
                nextSequence[i] = previous[i+1] - previous[i];

            }
            sequences.add(nextSequence);
            processSequence++;
        }
        return sequences;
    }

    public static boolean allZeros(int[] sequence) {
        for (int i : sequence) {
            if (i != 0)
                return false;
        }
        return true;
    }
}
