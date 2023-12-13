import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Collections;

public class Day07 {
    public static void main(String[] args) {
        ArrayList<String> fileData = getFileData("data/Day7_Input");
        ArrayList<CamelCardHand> cards = new ArrayList<CamelCardHand>();
        for (String line : fileData) {
            String[] splitLine = line.split(" ");
            CamelCardHand cc = new CamelCardHand(splitLine[0], Integer.parseInt(splitLine[1]));
            cards.add(cc);
        }

        Collections.sort(cards);

        for (CamelCardHand cc : cards) {
            System.out.println(cc.getCards() + " " + cc.getHandType());
        }

        int total = 0;
        for (int i = 0; i < cards.size(); i++) {
            int rank = i + 1;
            total = total + (rank * cards.get(i).getBid());
        }
        System.out.println(total);
    }

    public static ArrayList<String> getFileData(String fileName) {
        ArrayList<String> fileData = new ArrayList<String>();
        try {
            File f = new File(fileName);
            Scanner s = new Scanner(f);
            while (s.hasNextLine()) {
                String line = s.nextLine();
                if (!line.equals(""))
                    fileData.add(line);
            }
            return fileData;
        }
        catch (FileNotFoundException e) {
            return fileData;
        }
    }
}
