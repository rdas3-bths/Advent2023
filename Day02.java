import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.ArrayList;

public class Day2 {
    public static void main(String[] args) throws FileNotFoundException {
        int gameTotal = 0;
        File f = new File("Games");
        Scanner s = new Scanner(f);
        while (s.hasNext()) {
            String line = s.nextLine();
            int gameID = getGameID(line);
            String gameData = getGameData(line);
            String[] roundData = getRoundData(gameData);
            for (int i = 0; i < roundData.length; i++) {
                roundData[i] = roundData[i].strip();
            }
            ArrayList<String> cubes = new ArrayList<String>();
            for (String round : roundData) {
                String[] d = round.split(",");
                for (String data : d) {
                    cubes.add(data.strip());
                }
            }
            int red = highestCount(cubes, "red");
            int green = highestCount(cubes, "green");
            int blue = highestCount(cubes, "blue");

            int roundPower = red * green * blue;
            gameTotal = gameTotal + roundPower;

        }
        System.out.println(gameTotal);
    }

    public static int getGameID(String line) {
        int colon = line.indexOf(":");

        return Integer.parseInt(line.substring(5, colon));
    }

    public static String getGameData(String line) {
        int colon = line.indexOf(":");
        return line.substring(colon+1).strip();
    }

    public static String[] getRoundData(String gameData) {
        return gameData.split(";");
    }

    public static int highestCount(ArrayList<String> cubes, String color) {
        int highestCount = -1;
        for (String data : cubes) {
            if (data.contains(color)) {
                int startColor = data.indexOf(color);
                String amount = data.substring(0, startColor).strip();
                int count = Integer.parseInt(amount);
                if (count > highestCount) {
                    highestCount = count;
                }
            }
        }
        return highestCount;
    }

}