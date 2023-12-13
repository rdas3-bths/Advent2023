import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Day5 {
    public static void main(String[] args) {



        ArrayList<String> fileData = getFileData("data/Day5_Input");

        // populate seed list
        ArrayList<Long> seedList = new ArrayList<Long>();
        String firstLine = fileData.get(0);
        String[] seeds = firstLine.substring(firstLine.indexOf(":")+2).split(" ");
        for (String s : seeds) {
            seedList.add(Long.parseLong(s.strip()));
        }


        ArrayList<Map> maps = new ArrayList<Map>();
        String currentMap = "";
        boolean processMaps = false;
        for (String line : fileData) {
            if (line.indexOf("map") != -1) {
                currentMap = line.substring(0, line.indexOf(" "));
                processMaps = true;
            }

            if (line.indexOf("map") == -1 && processMaps) {
                String[] mapLine = line.split(" ");
                long destination = Long.parseLong(mapLine[0]);
                long source = Long.parseLong(mapLine[1]);
                long range = Long.parseLong(mapLine[2]);
                Map m = new Map(source, destination, currentMap, range);
                maps.add(m);
            }

        }

       long minLocation = Long.MAX_VALUE;
       for (int i = 0; i < seedList.size()-1; i = i + 2) {
           long startSeed = seedList.get(i);
           long endSeed = seedList.get(i+1);
           System.out.println("Batch " + i + " started: ");
           for (long j = startSeed; j < (startSeed+endSeed); j++) {
               long location = getLocationFromSeed(maps, j);
               if (location < minLocation) {
                   minLocation = location;
                   System.out.println("Found new minimum: " + minLocation);
               }
           }
        }
       System.out.println("Min location is: " + minLocation);

    }

    public static long getMapDestination(ArrayList<Map> maps, String mapType, long sourceNumber) {
        for (Map m : maps) {
            if (m.getMapType().equals(mapType)) {
                long maxSource = m.getSourceNumber() + m.getRange() - 1;
                if (sourceNumber >= m.getSourceNumber() && sourceNumber <= maxSource) {
                    return sourceNumber + (m.getDestinationNumber() - m.getSourceNumber());
                }
            }
        }
        return sourceNumber;
    }

    public static long getLocationFromSeed(ArrayList<Map> maps, long seedSource) {
        long soilNumber = getMapDestination(maps, "seed-to-soil", seedSource);
        long fertNumber = getMapDestination(maps, "soil-to-fertilizer", soilNumber);
        long waterNumber = getMapDestination(maps, "fertilizer-to-water", fertNumber);
        long lightNumber = getMapDestination(maps, "water-to-light", waterNumber);
        long tempNumber = getMapDestination(maps, "light-to-temperature", lightNumber);
        long humNumber = getMapDestination(maps, "temperature-to-humidity", tempNumber);
        return getMapDestination(maps, "humidity-to-location", humNumber);

    }

    public static int findMapIndex(ArrayList<String> fileData, String mapName) {
        for (int i = 0; i < fileData.size(); i++) {
            String line = fileData.get(i);
            if (line.indexOf(mapName) != -1) return i;
        }
        return -1;
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
