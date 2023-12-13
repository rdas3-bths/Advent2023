import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Day8 {
    public static void main(String[] args) {

        ArrayList<String> fileData = getFileData("data/Day8_Input");
        int[] directionList = new int[fileData.get(0).length()];

        for (int i = 0; i < fileData.get(0).length(); i++) {
            String d = fileData.get(0).substring(i, i+1);
            if (d.equals("L"))
                directionList[i] = 0;
            if (d.equals("R"))
                directionList[i] = 1;
        }

        ArrayList<LocationEntry> maps = new ArrayList<LocationEntry>();
        for (int i = 1; i < fileData.size(); i++) {
            String line = fileData.get(i);
            String location = line.substring(0, line.indexOf("=")-1);
            String leftLocation = line.substring(line.indexOf("(")+1, line.indexOf(","));
            String rightLocation = line.substring(line.indexOf(",")+2, line.indexOf(")"));
            LocationEntry e = new LocationEntry(location, leftLocation, rightLocation);
            maps.add(e);
        }

        ArrayList<String> startingLocations = new ArrayList<String>();
        for (LocationEntry e : maps) {
            if (e.getLocation().charAt(2) == 'A')
                startingLocations.add(e.getLocation());
        }
        long[] locationSolutions = new long[startingLocations.size()];

        for (int i = 0; i < startingLocations.size(); i++) {
            locationSolutions[i] = solveLocation(maps, directionList, startingLocations.get(i));
        }

        findLCM(locationSolutions);

    }

    public static long solveLocation(ArrayList<LocationEntry> maps, int[] directions, String start) {
        String currentLocation = new String(start);
        long count = 0;
        while (currentLocation.charAt(2) != 'Z') {
            for (int i = 0; i < directions.length; i++) {
                currentLocation = getNewLocation(maps, directions[i], currentLocation);
                count++;

            }
        }
        return count;
    }

    public static String getNewLocation(ArrayList<LocationEntry> maps, int direction, String location) {
        for (int i = 0; i < maps.size(); i++) {
            if (maps.get(i).getLocation().equals(location)) {
                if (direction == 0) return maps.get(i).getLeftLocation();
                if (direction == 1) return maps.get(i).getRightLocation();
            }
        }
        return "";
    }

    // findGCD and findLCM taken from the internets to find the Lowest Common Multiple
    // of an array of numbers (this is built-in for python!)
    public static long findGCD(long a, long b){
        //base condition
        if(b == 0)
            return a;

        return findGCD(b, a%b);
    }

    public static void findLCM(long[] array) {
        //initialize LCM and GCD with the first element
        long lcm = array[0];
        long gcd = array[0];

        //loop through the array to find GCD
        //use GCD to find the LCM
        for(int i=1; i<array.length; i++){
            gcd = findGCD(array[i], lcm);
            lcm = (lcm*array[i])/gcd;
        }

        //output the LCM
        System.out.println(lcm);
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
