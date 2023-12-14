import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Day14 {
    public static void main(String[] args) throws FileNotFoundException {
        ArrayList<String> fileData = getFileData("data/Day14_Input");
        String[][] grid = new String[fileData.size()][fileData.get(0).length()];
        for (int i = 0; i < fileData.size(); i++) {
            String line = fileData.get(i);
            for (int j = 0; j < line.length(); j++) {
                grid[i][j] = line.charAt(j) + "";
            }
        }

        int cycles = 200;
        for (int i = 0; i < cycles; i++) {
            doCycle(grid);
            int total = 0;
            for (int r = 0; r < grid.length; r++) {
                total += calculateLoadInRow(grid, r);
            }
            System.out.println("Doing cycle " + (i+1) + ": " + total);
        }



    }

    public static void doCycle(String[][] grid) {
        tiltNorth(grid);
        tiltWest(grid);
        tiltSouth(grid);
        tiltEast(grid);
    }

    public static void tiltSouth(String[][] grid) {
        for (int c = 0; c < grid[0].length; c++) {
            int rockToMove = findRockToMoveSouth(c, grid);
            while (rockToMove != -1) {
                moveDownOneRow(rockToMove, c, grid);
                rockToMove = findRockToMoveSouth(c, grid);
            }
        }
    }

    public static void tiltNorth(String[][] grid) {
        for (int c = 0; c < grid[0].length; c++) {
            int rockToMove = findRockToMoveNorth(c, grid);
            while (rockToMove != -1) {
                moveUpOneRow(rockToMove, c, grid);
                rockToMove = findRockToMoveNorth(c, grid);
            }
        }
    }

    public static void tiltWest(String[][] grid) {
        for (int r = 0; r < grid.length; r++) {
            int rockToMove = findRockToMoveWest(r, grid);
            while (rockToMove != -1) {
                moveLeftOneColumn(r, rockToMove, grid);
                rockToMove = findRockToMoveWest(r, grid);
            }
        }
    }

    public static void tiltEast(String[][] grid) {
        for (int r = 0; r < grid.length; r++) {
            int rockToMove = findRockToMoveEast(r, grid);
            while (rockToMove != -1) {
                moveRightOneColumn(r, rockToMove, grid);
                rockToMove = findRockToMoveEast(r, grid);
            }
        }
    }

    public static int calculateLoadInRow(String[][] grid, int row) {
        int count = 0;
        int loadPerRock = grid.length - row;
        for (int c = 0; c < grid[0].length; c++) {
            if (grid[row][c].equals("O"))
                count++;
        }
        return (count * loadPerRock);
    }

    public static int findRockToMoveNorth(int col, String[][] grid) {
        for (int i = 1; i < grid.length; i++) {
            String current = grid[i][col];
            String above = grid[i-1][col];
            if (current.equals("O") && above.equals("."))
                return i;
        }
        return -1;
    }

    public static int findRockToMoveSouth(int col, String[][] grid) {
        for (int i = grid.length - 2; i >= 0; i--) {
            String current = grid[i][col];
            String below = grid[i+1][col];
            if (current.equals("O") && below.equals("."))
                return i;
        }
        return -1;
    }

    public static int findRockToMoveWest(int row, String[][] grid) {
        for (int i = 1; i < grid[0].length; i++) {
            String current = grid[row][i];
            String left = grid[row][i-1];
            if (current.equals("O") && left.equals("."))
                return i;
        }
        return -1;
    }

    public static int findRockToMoveEast(int row, String[][] grid) {
        for (int i = 0; i < grid[0].length-1; i++) {
            String current = grid[row][i];
            String right = grid[row][i+1];
            if (current.equals("O") && right.equals("."))
                return i;
        }
        return -1;
    }

    public static void moveDownOneRow(int row, int col, String[][] grid) {
        if (row != grid.length-1) {
            String current = grid[row][col];
            String below = grid[row+1][col];
            grid[row+1][col] = current;
            grid[row][col] = below;
        }
    }



    public static void moveUpOneRow(int row, int col, String[][] grid) {
        if (row != 0) {
            String current = grid[row][col];
            String above = grid[row-1][col];
            grid[row-1][col] = current;
            grid[row][col] = above;
        }
    }

    public static void moveLeftOneColumn(int row, int col, String[][] grid) {
        if (col != 0) {
            String current = grid[row][col];
            String left = grid[row][col-1];
            grid[row][col-1] = current;
            grid[row][col] = left;
        }
    }

    public static void moveRightOneColumn(int row, int col, String[][] grid) {
        if (col != grid[0].length-1) {
            String current = grid[row][col];
            String right = grid[row][col+1];
            grid[row][col+1] = current;
            grid[row][col] = right;
        }
    }

    public static void printGrid(String[][] grid) {
        for (String[] row : grid) {
            for (String c : row) {
                System.out.print(c + " ");
            }
            System.out.println();
        }
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

}
