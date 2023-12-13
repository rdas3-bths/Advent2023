import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Day01 {
    public static void main(String[] args) {

        File f = new File("data/Day1_Input");
        Scanner s = null;
        try {
            s = new Scanner(f);
        }
        catch (FileNotFoundException file) {
            System.out.println("File not found");
        }
        int total = 0;
        while (s.hasNextLine()) {
            String line = s.nextLine();
            line = line.strip();
            String firstDigit = "";
            String lastDigit = "";
            for (int i = 0; i < line.length(); i++) {
                char c = line.charAt(i);
                if (!getDigit(i, line).equals("") && firstDigit.equals("")) {
                    firstDigit = getDigit(i, line);
                }
            }
            for (int i = line.length()-1; i >= 0; i--) {

                char c = line.charAt(i);
                if (!getDigit(i, line).equals("") && lastDigit.equals("")) {
                    lastDigit = getDigit(i, line);

                }
            }
            System.out.println(line + ": " + firstDigit + lastDigit);
            total = total + Integer.parseInt(firstDigit + lastDigit);

        }
        System.out.println(total);
    }

    public static String getDigit(int index, String line) {
        char c = line.charAt(index);
        if (Character.isDigit(c)) return c + "";

        for (int i = 1; i <= 9; i++) {
            String number = "";
            if (i == 1) number = "one";
            if (i == 2) number = "two";
            if (i == 3) number = "three";
            if (i == 4) number = "four";
            if (i == 5) number = "five";
            if (i == 6) number = "six";
            if (i == 7) number = "seven";
            if (i == 8) number = "eight";
            if (i == 9) number = "nine";

            try {
                if (line.substring(index, index+number.length()).equals(number)) return i + "";
            }
            catch (Exception e) { }
        }
        return "";

    }
}
