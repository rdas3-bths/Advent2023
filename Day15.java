import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Day15 {
    public static void main(String[] args) throws FileNotFoundException {
        // sequence of letters represents the label
        // the hash of the sequence is the box to put it in
        // if sequence has -, find box, and remove that label
        // if sequence has =, find box:
              // if box has that label, replace it with new one
              // if box doesn't have the label, add it to the end of the list

        ArrayList<String> fileData = getFileData("data/Day15_Input");
        String fullLine = fileData.get(0);
        String[] dataSet = fullLine.split(",");
        int total = 0;
        ArrayList<Lens> allLens = new ArrayList<Lens>();
        ArrayList<Box> allBoxes = new ArrayList<Box>();

        for (int i = 0; i < 256; i++) {
            Box b = new Box(i);
            allBoxes.add(b);
        }


        for (String data : dataSet) {
            Lens l = new Lens(data);
            allLens.add(l);
        }

        for (Lens l : allLens) {
            int boxNumber = l.getBox();
            allBoxes.get(boxNumber).processLens(l);
        }

        int answer = 0;
        for (Box b : allBoxes) {
            answer += b.getFocalPower();
        }
        System.out.println(answer);

    }

    public static void printAllBoxes(ArrayList<Box> boxes) {
        for (Box b : boxes) {
            System.out.println(b);
        }
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
}
