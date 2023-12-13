import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Day6 {
    public static void main(String[] args) {
        // Removing because I don't feel like changing the parsing logic
        // for part 2

        /*ArrayList<String> fileData = getFileData("data/Day6_Input");
        String[] timeInfo = fileData.get(0).split(" ");
        String[] distanceInfo = fileData.get(1).split(" ");

        ArrayList<Integer> timeData = new ArrayList<Integer>();
        ArrayList<Integer> distanceData = new ArrayList<Integer>();
        for (int i = 0; i < timeInfo.length; i++) {
            try {
                int t = Integer.parseInt(timeInfo[i]);
                timeData.add(t);
            }
            catch (Exception e) {}
        }
        for (int i = 0; i < distanceInfo.length; i++) {
            try {
                int t = Integer.parseInt(distanceInfo[i]);
                distanceData.add(t);
            }
            catch (Exception e) {}
        }*/
        ArrayList<Long> timeData = new ArrayList<Long>();
        ArrayList<Long> distanceData = new ArrayList<Long>();

        // hardcoding input file data :-O
        long x = 46807866;
        long y = Long.parseLong("214117714021024");
        timeData.add(x);
        distanceData.add(y);
        long answer = 1;
        for (int i = 0; i < timeData.size(); i++) {
            long currentTime = timeData.get(i);
            long record = distanceData.get(i);
            long raceCount = 0;
            for (long j = 0; j <= currentTime; j++) {
                long travel = getDistanceTraveled(currentTime, j);
                if (travel > record) {
                    //System.out.println("Race " + i + ", Time: " + currentTime + ", Hold: " + j + ", Travel: " + travel);
                    raceCount++;
                }
            }
            System.out.println("Race can be beaten: " + raceCount + " times");
            answer = answer * raceCount;
        }
        System.out.println("Final answer: " + answer);
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

    public static long getDistanceTraveled(long time, long hold) {
        long travelTime = time - hold;
        if (travelTime <= 0) {
            return 0;
        }
        else {
            return (hold * travelTime);
        }
    }
}
