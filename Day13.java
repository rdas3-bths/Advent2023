import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Day13 {
    public static void main(String[] args) {
        ArrayList<String> fileData = getFileData("data/Day13_Input");

        ArrayList<String> gridStrings = new ArrayList<String>();
        ArrayList<int[][]> allGrids = new ArrayList<int[][]>();
        for (int i = 0; i < fileData.size(); i++) {
            String line = fileData.get(i);
            if (!line.equals(""))
                gridStrings.add(line);
            else {
                int[][] grid = convertGridStringTo2DArray(gridStrings);
                allGrids.add(grid);
                gridStrings = new ArrayList<String>();

            }
        }
        int[][] grid = convertGridStringTo2DArray(gridStrings);
        allGrids.add(grid);

        int total = 0;

        for (int i = 0; i < allGrids.size(); i++) {
            int vertical = checkVerticalReflection(allGrids.get(i));
            if (vertical != -1) {
                total += vertical;
            }

            int horizontal = checkHorizontalReflection(allGrids.get(i));

            if (horizontal != -1) {
                total += (horizontal * 100);
            }
        }

        System.out.println(total);


    }

    public static void printGrid(int[][] grid) {
        for (int[] row : grid) {
            for (int i : row) {
                System.out.print(i + " ");
            }
            System.out.println();
        }
        System.out.println();

    }

    public static int checkVerticalReflection(int [][] grid) {
        int c1 = 0;
        int c2 = 1;

        while (c2 != grid[0].length) {
            int checks = Math.min(c1, (grid[0].length-1)-c2) + 1;
            int check1 = c1;
            int check2 = c2;
            boolean check = true;
            int checkDifferences = 0;
            if (checks == 0)
                check = false;
            for (int i = 0; i < checks; i++) {
                checkDifferences += countColumnDifferences(check1, check2, grid);
                check1--;
                check2++;
            }
            if (checkDifferences == 1) {
                return (c1+1);
            }
            c1++;
            c2++;
        }
        return -1;
    }

    public static int checkHorizontalReflection(int [][] grid) {
        int r1 = 0;
        int r2 = 1;

        while (r1 != grid.length) {
            int checks = Math.min(r1, (grid.length-1)-r2) + 1;
            int check1 = r1;
            int check2 = r2;
            boolean check = true;
            int checkDifferences = 0;
            if (checks == 0)
                check = false;
            for (int i = 0; i < checks; i++) {
                checkDifferences += countRowDifferences(check1, check2, grid);
                check1--;
                check2++;
            }
            if (checkDifferences == 1) {
                return (r1+1);
            }
            r1++;
            r2++;
        }
        return -1;
    }

    public static int[][] convertGridStringTo2DArray(ArrayList<String> gridString) {
        int rows = gridString.size();
        int columns = gridString.get(0).length();
        int [][] grid = new int[rows][columns];
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                char c = gridString.get(i).charAt(j);
                if (c == '.')
                    grid[i][j] = 1;
            }
        }
        return grid;
    }

    public static boolean checkColumnsEqual(int c1, int c2, int[][] grid) {
        for (int row = 0; row < grid.length; row++) {
            if (grid[row][c1] != grid[row][c2]) {
                return false;
            }
        }
        return true;
    }

    public static boolean checkRowsEqual(int r1, int r2, int[][] grid) {
        for (int col = 0; col < grid[0].length; col++) {
            if (grid[r1][col] != grid[r2][col]) {
                return false;
            }
        }
        return true;
    }

    public static int countRowDifferences(int r1, int r2, int[][] grid) {
        int count = 0;
        for (int col = 0; col < grid[0].length; col++) {
            if (grid[r1][col] != grid[r2][col]) {
                count++;
            }
        }
        return count;
    }

    public static int countColumnDifferences(int c1, int c2, int[][] grid) {
        int count = 0;
        for (int row = 0; row < grid.length; row++) {
            if (grid[row][c1] != grid[row][c2]) {
                count++;
            }
        }
        return count;
    }

    public static ArrayList<String> getFileData(String fileName) {
        ArrayList<String> fileData = new ArrayList<String>();
        try {
            File f = new File(fileName);
            Scanner s = new Scanner(f);
            while (s.hasNextLine()) {
                String line = s.nextLine();
                fileData.add(line);
            }
            return fileData;
        } catch (FileNotFoundException e) {
            return fileData;
        }
    }
}
