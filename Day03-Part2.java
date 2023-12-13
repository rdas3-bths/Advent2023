import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.ArrayList;

public class Part2 {
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

        GridEntry[][] fullGrid = Part1.makeFullGrid(grid);

        for (int row = 0; row < fullGrid.length; row++) {
            for (int col = 0; col < fullGrid[0].length; col++) {
                if (fullGrid[row][col].isGear()) {
                    //System.out.println("Found gear at " + " (" + row + "," + col + ")");
                    total = total + processGear(row, col, fullGrid);
                }
            }
        }
        System.out.println(total);

    }

    public static GridEntry findStartingNumber(int r, int c, GridEntry[][] fullGrid) {
        GridEntry current = fullGrid[r][c];
        if (current.isStartNumber())
            return current;
        else  {
            while (c >= 0) {
                current = fullGrid[r][c];
                if (current.isStartNumber())
                    return current;
                c--;
            }
        }
        return null;
    }

    public static int getFullNumber(int startingRow, int startingColumn, GridEntry[][] fullGrid) {
        GridEntry current = fullGrid[startingRow][startingColumn];
        if (current.isStartNumber() && current.isEndNumber())
            return Integer.parseInt(current.getSymbol() + "");
        else {
            String full = "";
            for (int i = startingColumn; i < fullGrid[0].length; i++) {
                current = fullGrid[startingRow][i];
                full = full + current.getSymbol();
                if (current.isEndNumber())
                    return Integer.parseInt(full);
            }
        }
        return -1;
    }

    public static int processGear(int r, int c, GridEntry[][] fullGrid) {
        ArrayList<Integer> adjacentNumbers = new ArrayList<Integer>();
        // check left
        if (c != 0) {
            GridEntry left = fullGrid[r][c-1];
            if (left.isDigit()) {
                GridEntry starting = findStartingNumber(r, c-1, fullGrid);
                int full = getFullNumber(starting.getRow(), starting.getCol(), fullGrid);
                adjacentNumbers.add(full);
            }
        }

        // check right
        if (c != fullGrid[0].length-1) {
            GridEntry right = fullGrid[r][c+1];
            if (right.isDigit()) {
                GridEntry starting = findStartingNumber(r, c+1, fullGrid);
                int full = getFullNumber(starting.getRow(), starting.getCol(), fullGrid);
                adjacentNumbers.add(full);
            }
        }

        // check top
        if (r != 0) {
            GridEntry top = fullGrid[r-1][c];
            if (top.isDigit()) {
                GridEntry starting = findStartingNumber(r-1, c, fullGrid);
                int full = getFullNumber(starting.getRow(), starting.getCol(), fullGrid);
                adjacentNumbers.add(full);
            }
            else {
                //check top left and top right

                // check top left
                if (c != 0) {
                    GridEntry topLeft = fullGrid[r-1][c-1];
                    if (topLeft.isDigit()) {
                        GridEntry starting = findStartingNumber(r-1, c-1, fullGrid);
                        int full = getFullNumber(starting.getRow(), starting.getCol(), fullGrid);
                        adjacentNumbers.add(full);
                    }
                }

                // check top right
                if (c != fullGrid[0].length-1) {
                    GridEntry topRight = fullGrid[r-1][c+1];
                    if (topRight.isDigit()) {
                        GridEntry starting = findStartingNumber(r-1, c+1, fullGrid);
                        int full = getFullNumber(starting.getRow(), starting.getCol(), fullGrid);
                        adjacentNumbers.add(full);
                    }
                }

            }
        }

        // check bottom
        if (r != fullGrid.length-1) {
            GridEntry bottom = fullGrid[r+1][c];
            if (bottom.isDigit()) {
                GridEntry starting = findStartingNumber(r+1, c, fullGrid);
                int full = getFullNumber(starting.getRow(), starting.getCol(), fullGrid);
                adjacentNumbers.add(full);
            }
            else {
                // check bottom left
                if (c != 0) {
                    GridEntry bottomLeft = fullGrid[r+1][c-1];
                    if (bottomLeft.isDigit()) {
                        GridEntry starting = findStartingNumber(r+1, c-1, fullGrid);
                        int full = getFullNumber(starting.getRow(), starting.getCol(), fullGrid);
                        adjacentNumbers.add(full);
                    }
                }

                // check bottom right
                if (c != fullGrid[0].length-1) {
                    GridEntry bottomRight = fullGrid[r+1][c+1];
                    if (bottomRight.isDigit()) {
                        GridEntry starting = findStartingNumber(r+1, c+1, fullGrid);
                        int full = getFullNumber(starting.getRow(), starting.getCol(), fullGrid);
                        adjacentNumbers.add(full);
                    }
                }
            }
        }

        if (adjacentNumbers.size() == 2) {
            return adjacentNumbers.get(0) * adjacentNumbers.get(1);
        }


        return 0;

    }
}