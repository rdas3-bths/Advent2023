import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Day21 {
    public static void main(String[] args) {
        ArrayList<String> fileData = getFileData("data/Day21_Input");
        char[][] originalGrid = new char[fileData.size()][fileData.get(0).length()];
        char[][] grid = new char[fileData.size()][fileData.get(0).length()];



        for (int i = 0; i < fileData.size(); i++) {
            String line = fileData.get(i);
            for (int j = 0; j < line.length(); j++) {
                char c = line.charAt(j);
                originalGrid[i][j] = c;
            }
        }



            resetGrid(grid, originalGrid);
            int steps = 65;
            Point start = getStartingPoint(grid);
            doStep(start, grid);
            for (int s = 1; s < steps; s++) {
                ArrayList<Point> newPoints = getAllZeros(grid);
                for (int i = 0; i < newPoints.size(); i++) {
                    doStep(newPoints.get(i), grid);
                }

            }

            int count = countSpots(grid);
            System.out.println(steps + "," + count);


//        3666
//        65 [3752]
//        196 [3752, 33614]
//        327 [3752, 33614, 93252]
//        609298746763952
//
//        609298746763952

        for (char[] row : grid) {
            for (char c : row) {
                System.out.print(c + " ");
            }
            System.out.println();
        }


    }

    public static void resetGrid(char[][] grid, char[][] originalGrid) {
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                grid[i][j] = originalGrid[i][j];
            }
        }
    }

    public static int countSpots(char[][] grid) {
        int count = 0;
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                if (grid[i][j] == '0')
                    count++;
            }
        }
        return count;
    }

    public static void doStep(Point p, char[][] grid) {
        int row = p.getX();
        int col = p.getY();

        // check north
        if (row != 0) {
            if (grid[row-1][col] != '#')
                grid[row-1][col] = '0';
        }

        // check south
        if (row != grid.length-1) {
            if (grid[row+1][col] != '#')
                grid[row+1][col] = '0';
        }

        // check left
        if (col != 0)
            if (grid[row][col-1] != '#')
                grid[row][col-1] = '0';

        // check right
        if (col != grid[0].length-1)
            if (grid[row][col+1] != '#')
                grid[row][col+1] = '0';

        grid[row][col] = '.';
    }

    public static Point getStartingPoint(char[][] grid) {
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                if (grid[i][j] == 'S')
                    return new Point(i, j);
            }
        }
        return null;
    }

    public static ArrayList<Point> getAllZeros(char[][] grid) {
        ArrayList<Point> points = new ArrayList<Point>();
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                if (grid[i][j] == '0')
                    points.add(new Point(i, j));
            }
        }
        return points;
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
