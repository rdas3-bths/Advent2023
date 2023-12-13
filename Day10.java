import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

// odd inside
// even outside

public class Day10 {
    public static void main(String[] args) throws FileNotFoundException {
        ArrayList<String> fileData = getFileData("Day10_Input");

        String[][] grid = new String[fileData.size()][fileData.get(0).length()];
        String[][] realPath = new String[fileData.size()][fileData.get(0).length()];
        for (int r = 0; r < fileData.size(); r++) {
            String line = fileData.get(r);
            for (int c = 0; c < line.length(); c++) {
                char item = line.charAt(c);
                grid[r][c] = item + "";
                realPath[r][c] = item + "";
            }
        }

        String check = "NSEW";
        int startingRow = -1;
        int startingColumn = -1;

        for (int r = 0; r < grid.length; r++) {

            for (int c = 0; c < grid[r].length; c++) {
                if (grid[r][c].equals("S")) {
                    startingRow = r;
                    startingColumn = c;
                }
            }
        }

        boolean returned = false;
        String previousDirection = "";
        int currentRow = startingRow;
        int currentColumn = startingColumn;
        while (!returned) {
            String direction = getDirection(grid, currentRow, currentColumn, previousDirection);
            if (direction.equals("North")) currentRow -= 1;
            else if (direction.equals("East")) currentColumn += 1;
            else if (direction.equals("West")) currentColumn -= 1;
            else if (direction.equals("South")) currentRow += 1;

            if (grid[currentRow][currentColumn].equals("S")) returned = true;
            realPath[currentRow][currentColumn] = direction.charAt(0) + "";
            previousDirection = direction;
        }


        for (int i = 0; i < realPath.length; i++) {
            for (int j = 0; j < realPath[0].length; j++) {
                if (!check.contains(realPath[i][j])) {
                    realPath[i][j] = ".";
                    grid[i][j] = ".";
                }
            }
        }
        for (int i = 0; i < realPath.length; i++) {
            for (int j = 0; j < realPath[i].length; j++) {
                if (realPath[i][j].equals(".")) {
                   int numberIntersections = countIntersections(realPath, grid, i, j);
                   if (numberIntersections % 2 == 0)
                       realPath[i][j] = "0";
                   else
                       realPath[i][j] = "I";
                }

            }
        }

        getAnswer(realPath);
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

    public static void getAnswer(String[][] grid) {
        final String ANSI_RESET = "\u001B[0m";
        final String ANSI_BLUE = "\u001B[34m";
        final String ANSI_YELLOW = "\u001B[33m";

        int count = 0;
        for (String[] x : grid) {
            for (String y : x) {
                if (y.equals("I")) {
                    System.out.print(ANSI_BLUE + y + " " + ANSI_RESET);
                    count++;
                }
                else if (y.equals("0")) {
                    System.out.print(ANSI_BLUE + y + " " + ANSI_RESET);
                }
                else {
                    System.out.print(ANSI_YELLOW + y + " " + ANSI_RESET);
                }
            }
            System.out.println();
        }

        System.out.println(count);
    }

    public static boolean checkNorth(String[][] grid, int r, int c, String previous) {

        if (previous.equals("South")) return false;

        String canGoNorth = "|LJS";
        if (!canGoNorth.contains(grid[r][c])) return false;

        String validNorth = "|7FS";
        if (r == 0) return false;

        String northItem = grid[r-1][c];
        return (validNorth.contains(northItem));
    }

    public static boolean checkEast(String[][] grid, int r, int c, String previous) {

        if (previous.equals("West")) return false;

        String canGoEast = "-LFS";
        if (!canGoEast.contains(grid[r][c])) return false;

        String validEast = "-7JS";

        if (c == grid[r].length - 1) return false;
        return (validEast.contains(grid[r][c+1]));
    }

    public static boolean checkWest(String[][] grid, int r, int c, String previous) {

        if (previous.equals("East")) return false;

        String canGoWest = "-7JS";
        if (!canGoWest.contains(grid[r][c])) return false;

        String validWest = "-LFS";

        if (c == 0) return false;
        return (validWest.contains(grid[r][c-1]));
    }

    public static boolean checkSouth(String[][] grid, int r, int c, String previous) {

        if (previous.equals("North")) return false;

        String canGoSouth = "|7FS";
        if (!canGoSouth.contains(grid[r][c])) return false;

        String validSouth = "|LJS";

        if (r == grid.length-1) return false;
        return (validSouth.contains(grid[r+1][c]));
    }

    public static String getDirection(String[][] grid, int r, int c, String previous) {
        if (checkNorth(grid, r, c, previous)) return "North";

        if (checkEast(grid, r, c, previous)) return "East";

        if (checkWest(grid, r, c, previous)) return "West";

        if (checkSouth(grid, r, c, previous)) return "South";

        return "";
    }


    public static int countIntersections(String[][] grid, String[][] realGrid, int r, int c) {
        int count = 0;
        int column = c+1;
        String vertical = "|JL";
        while (column != grid[r].length) {
            if (vertical.contains(realGrid[r][column]))
                count++;
            column++;
        }

        return count;
    }
}
