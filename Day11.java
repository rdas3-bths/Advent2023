import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Day11 {
    public static void main(String[] args) {
        ArrayList<String> fileData = getFileData("data/Day11_Input");

        ArrayList<ArrayList<String>> universe = new ArrayList<ArrayList<String>>();

        for (String line : fileData) {
            ArrayList<String> row = new ArrayList<String>();
            for (int i = 0; i < line.length(); i++) {
                String c = line.charAt(i) + "";
                row.add(c);
            }
            universe.add(row);
        }

        //expandUniverse(universe); DON'T DO THIS FOR PART 2
        Universe u = createUniverse(universe);
        //printUniverse(universe);

        ArrayList<GalaxyPoint> galaxies = new ArrayList<GalaxyPoint>();
        int galaxyFound = 1;
        for (int r = 0; r < universe.size(); r++) {
            for (int c = 0; c < universe.get(0).size(); c++) {
                if (universe.get(r).get(c).equals("#")) {
                    GalaxyPoint g = new GalaxyPoint(r, c, galaxyFound);
                    galaxies.add(g);
                    galaxyFound++;
                }
            }
        }

        long total = 0;
        long distance = 0;
        for (int i = 0; i < galaxies.size(); i++) {
            for (int j = i+1; j < galaxies.size(); j++) {
                distance = GalaxyPoint.computeDistance(galaxies.get(i), galaxies.get(j), u);
                total += distance;
            }
        }
        System.out.println(total);
    }

    public static Universe createUniverse(ArrayList<ArrayList<String>>  universe) {
        ArrayList<Integer> emptyRows = new ArrayList<Integer>();
        ArrayList<Integer> emptyColumns = new ArrayList<Integer>();
        for (int i = 0; i < universe.size(); i++) {
            if (checkEmptyRow(universe, i)) {
                emptyRows.add(i);
            }
        }

        // check if a column has all dots
        for (int c = 0; c < universe.get(0).size(); c++) {
            if (checkEmptyColumn(universe, c)) {
                emptyColumns.add(c);
            }
        }
        Universe u = new Universe(emptyRows, emptyColumns);
        return u;
    }

    public static void printUniverse(ArrayList<ArrayList<String>> universe) {
        for (int i = 0; i < universe.size(); i++) {
            for (int j = 0; j < universe.get(0).size(); j++) {
                System.out.print(universe.get(i).get(j) + " ");
            }
            System.out.println();
        }
    }

    // Can't expand part 2 because it's too big ...
    /*public static void expandUniverse(ArrayList<ArrayList<String>> universe) {
        // check if row has all dots
        for (int i = 0; i < universe.size(); i++) {
            if (checkEmptyRow(universe, i)) {
                int rowLength = universe.get(i).size();
                for (int x = 0; x < 1; x++) {
                    ArrayList<String> expandRow = new ArrayList<String>();
                    for (int j = 0; j < rowLength; j++) {
                        expandRow.add(".");
                    }
                    universe.add(i, expandRow);
                    i++;
                }

            }
        }

        // check if a column has all dots
        for (int c = 0; c < universe.get(0).size(); c++) {
                if (checkEmptyColumn(universe, c)) {
                    for (int x = 0; x < 1; x++) {
                        for (int r = 0; r < universe.size(); r++) {
                            universe.get(r).add(c, ".");
                        }
                        c++;
                    }

            }
        }
    }*/

    public static boolean checkEmptyRow(ArrayList<ArrayList<String>> universe, int row) {
        ArrayList<String> checkRow = universe.get(row);

        for (String spot : checkRow) {
            if (!spot.equals("."))
                return false;
        }
        return true;
    }

    public static boolean checkEmptyColumn(ArrayList<ArrayList<String>> universe, int column) {
        for (int i = 0; i < universe.size(); i++) {
            String spot = universe.get(i).get(column);
            if (!spot.equals("."))
                return false;
        }
        return true;
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
