import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.ArrayList;

public class Part1 {
    public static void main(String[] args) throws FileNotFoundException {
        int total = 0;
        File f = new File("Grid");
        Scanner s = new Scanner(f);
        ArrayList<String> lines = new ArrayList<String>();
        while (s.hasNext()) {
            String line = s.nextLine();
            lines.add(line);
        }

        char[][] grid = new char[lines.size()][lines.get(0).length()];
        for (int i = 0; i < grid.length; i++) {
            String oneLine = lines.get(i);
            for (int j = 0; j < oneLine.length(); j++) {
                char c = oneLine.charAt(j);
                grid[i][j] = c;
            }
        }

        GridEntry[][] fullGrid = makeFullGrid(grid);

        for (int row = 0; row < fullGrid.length; row++) {
            total = total + processRow(fullGrid, row);
        }

        System.out.println(total);
    }

    public static GridEntry[][] makeFullGrid(char[][] grid) {
        GridEntry[][] fullGrid = new GridEntry[grid.length][grid[0].length];

        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                boolean startNumber = false;
                boolean endNumber = false;

                if (Character.isDigit(grid[i][j])) {
                    if (j == 0)
                        startNumber = true;
                    else if (j == grid[0].length-1)
                        endNumber = true;

                    if (j >= 1) {
                        if (!Character.isDigit(grid[i][j-1]))
                            startNumber = true;
                    }

                    if (j <= grid[0].length-2) {
                        if (!Character.isDigit(grid[i][j+1]))
                            endNumber = true;
                    }


                }

                fullGrid[i][j] = new GridEntry(grid[i][j], startNumber, endNumber, i, j);
            }
        }

        return fullGrid;
    }

    public static int processRow(GridEntry[][] fullGrid, int row) {
        boolean foundNumber = false;
        String fullNumber = "";
        int startingRow = -1;
        int startingCol = -1;
        int total = 0;
        for (int col = 0; col < fullGrid[0].length; col++) {
            GridEntry e = fullGrid[row][col];
            if (e.isStartNumber()) {
                foundNumber = true;
                startingRow = row;
                startingCol = col;
            }
            if (foundNumber) {
                fullNumber = fullNumber + e.getSymbol();
                if (e.isEndNumber()) {
                    boolean isValidNumber = isAdjacent(fullGrid, fullNumber, startingRow, startingCol);
                    if (isValidNumber)
                        total = total + Integer.parseInt(fullNumber);
                    foundNumber = false;
                    fullNumber = "";
                    startingRow = -1;
                    startingCol = -1;
                }
            }
        }
        return total;
    }

    public static boolean isAdjacent(GridEntry[][] fullGrid, String fullNumber, int row, int col) {

        // check behind
        if (col != 0) {
            if (fullGrid[row][col-1].isSymbol()) return true;
        }

        // check in front
        if (col+fullNumber.length() <= fullGrid[0].length-1) {
            if (fullGrid[row][col+fullNumber.length()].isSymbol()) return true;
        }

        // check above
        if (row != 0) {
            for (int i = col-1; i <= col+fullNumber.length(); i++) {
                if (i >= 0 && i < fullGrid[0].length) {
                    //System.out.println(fullGrid[row-1][i].getSymbol() + "(" + (row-1) + "," + i + ")");
                    if (fullGrid[row-1][i].isSymbol()) return true;
                }
            }
        }

        // check below
        if (row != fullGrid.length-1) {
            for (int i = col-1; i <= col+fullNumber.length(); i++) {
                if (i >= 0 && i < fullGrid[0].length) {
                    //System.out.println(fullGrid[row+1][i].getSymbol() + "(" + (row+1) + "," + i + ")");
                    if (fullGrid[row+1][i].isSymbol()) return true;
                }
            }
        }

        return false;
    }

}