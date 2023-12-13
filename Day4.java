import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.ArrayList;

public class Day4 {
    public static void main(String[] args) {
        ArrayList<String> fileData = getFileData("data/Day4_Input");
        int total = 0;
        ArrayList<Card> cards = new ArrayList<Card>();
        for (String line : fileData) {
            int cardNumber = getCardNumber(line);
            int value = getCardValue(getListOfWinningNumbers(line), getListOfPickedNumbers(line));
            Card c = new Card(cardNumber, value);
            cards.add(c);
        }
        for (int i = 0; i < cards.size(); i++) {
            Card currentCard = cards.get(i);
            int winnings = currentCard.getTotalWinning();
            int copies = currentCard.getCopies();
            for (int x = 0; x < copies; x++) {
                for (int j = 0; j < winnings; j++) {
                    int indexToCopy = j+i+1;
                    if (indexToCopy < cards.size()) {
                        cards.get(indexToCopy).addCopy();
                    }
                }
            }

        }

        int totalCopies = 0;
        for (Card c : cards) {
            totalCopies = totalCopies + c.getCopies();
        }

        System.out.println(totalCopies);
    }

    public static ArrayList<String> getFileData(String fileName) {
        ArrayList<String> fileData = new ArrayList<String>();
        try {
            File f = new File(fileName);
            Scanner s = new Scanner(f);
            while (s.hasNextLine()) {
                fileData.add(s.nextLine());
            }
            return fileData;
        }
        catch (FileNotFoundException e) {
            return fileData;
        }
    }

    public static int getCardNumber(String line) {
        int colon = line.indexOf(":");
        return Integer.parseInt(line.substring(5, colon).strip());
    }

    public static ArrayList<Integer> getListOfWinningNumbers(String line) {
        int colon = line.indexOf(":");
        int bar = line.indexOf("|");
        String numberString = line.substring(colon+1, bar);
        String[] numbers = numberString.split(" ");
        return parseNumbers(numbers);

    }

    public static ArrayList<Integer> getListOfPickedNumbers(String line) {
        int bar = line.indexOf("|");
        String numberString = line.substring(bar+1);
        String[] numbers = numberString.split(" ");
        return parseNumbers(numbers);
    }

    public static ArrayList<Integer> parseNumbers(String[] numbers) {
        ArrayList<Integer> finalNumbers = new ArrayList<Integer>();
        for (String n : numbers) {
            try {
                int number = Integer.parseInt(n);
                finalNumbers.add(number);
            }
            catch (Exception e) {}
        }
        return finalNumbers;
    }

    public static int getCardValue(ArrayList<Integer> winningNumbers, ArrayList<Integer> pickedNumbers) {
        int count = 0;
        for (Integer picked : pickedNumbers) {
            for (Integer winning : winningNumbers) {
                if (picked == winning) {
                    count++;
                }
            }
        }
        return count;
    }
}
