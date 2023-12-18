import jdk.jshell.spi.SPIResolutionException;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Day18 {
    public static void main(String[] args) throws FileNotFoundException {
        ArrayList<String> fileData = getFileData("data/Day18_Input");
        ArrayList<LavaNode> borderNodes = new ArrayList<LavaNode>();


        int row = 0;
        int column = 0;

        String cur = "";
        String next = "";
        for (int c = 0; c < fileData.size(); c++) {

            String[] data = fileData.get(c).split(" ");
            String currentDirection = data[0];
            int amount = Integer.parseInt(data[1]);
            String rgb = data[2].substring(data[2].indexOf("(")+2, data[2].length()-1);

            LavaNode node = null;
            cur = currentDirection;

            if (c != fileData.size()-1) {
                next = fileData.get(c+1).split(" ")[0];
            }

            if (borderNodes.isEmpty()) {
                node = new LavaNode(row, column, rgb);
                borderNodes.add(node);
            }

            for (int i = 0; i < amount; i++) {
                row = row + getRowOffset(currentDirection);
                column = column + getColumnOffset(currentDirection);
                node = new LavaNode(row, column, rgb);
                borderNodes.add(node);

                if (cur.equals("R") || cur.equals("L")) {
                    node.setSymbol('-');
                }


                if (cur.equals("U") || cur.equals("D"))
                    node.setSymbol('|');

                if (i == (amount-1)) {

                    System.out.println(row + " " + column);

                    if (c == fileData.size()-1){
                        cur = currentDirection;
                        next = fileData.get(0).split(" ")[0];
                    }

                    if (cur.equals("R") && next.equals("D"))
                        node.setSymbol('7');
                    if (cur.equals("U") && next.equals("L"))
                        node.setSymbol('7');

                    if (cur.equals("D") && next.equals("L"))
                        node.setSymbol('J');
                    if (cur.equals("R") && next.equals("U"))
                        node.setSymbol('J');

                    if (cur.equals("U") && next.equals("R"))
                        node.setSymbol('F');
                    if (cur.equals("L") && next.equals("D"))
                        node.setSymbol('F');

                    if (cur.equals("D") && next.equals("R"))
                        node.setSymbol('L');
                    if (cur.equals("L") && next.equals("U"))
                        node.setSymbol('L');

                }
            }
        }

        //borderNodes.remove(borderNodes.size()-1);

        int leftMostColumn = findLeftMostColumn(borderNodes);
        int rightMostColumn = findRightMostColumn(borderNodes);
        int topRow = findTopRow(borderNodes);
        int bottomRow = findBottomRow(borderNodes);
        int columns = (rightMostColumn - leftMostColumn) + 1;
        int rows = (bottomRow - topRow) + 1;

        LavaNode[][] fullGrid = new LavaNode[rows][columns];
        for (int i = 0; i < fullGrid.length; i++) {
            for (int j = 0; j < fullGrid[0].length; j++) {
                fullGrid[i][j] = new LavaNode(i, j);
            }
        }


        // every row has to be transformed by itself - topRow
        // every column has to be transformed by itself - leftMostColumn

        for (int i = 0; i < borderNodes.size(); i++) {
            int cRow = borderNodes.get(i).getRow();
            int cCol = borderNodes.get(i).getColumn();
            borderNodes.get(i).setRow(cRow - topRow);
            borderNodes.get(i).setColumn(cCol - leftMostColumn);
        }

        for (LavaNode n : borderNodes) {
            fullGrid[n.getRow()][n.getColumn()] = n;
        }


        for (int r = 0; r < fullGrid.length; r++) {
            for (int c = 0; c < fullGrid[0].length; c++) {
                if (fullGrid[r][c].getSymbol() == '.') {
                    int count = countIntersections(fullGrid, r, c);
                    if (count % 2 != 0)
                        fullGrid[r][c].setSymbol('X');
                }

            }
        }

        //printGrid(fullGrid);

        int count = 0;
        for (int r = 0; r < fullGrid.length; r++) {
            for (int c = 0; c < fullGrid[0].length; c++) {
                if (fullGrid[r][c].getSymbol() != '.')
                    count++;
            }
        }

        System.out.println(count);

    }

    public static int countIntersections(LavaNode[][] grid, int r, int c) {
        int count = 0;
        int column = c+1;
        String vertical = "|JL";
        while (column != grid[r].length) {
            if (vertical.contains(grid[r][column].getSymbol()+""))
                count++;
            column++;
        }

        return count;
    }

    public static void printGrid(LavaNode[][] grid) {
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                System.out.print(grid[i][j].getSymbol() + " ");
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

    public static int getRowOffset(String d) {
        if (d.equals("R")) return 0;

        if (d.equals("L")) return 0;

        if (d.equals("U")) return -1;

        if (d.equals("D")) return 1;

        else
            return 0;
    }

    public static int getColumnOffset(String d) {
        if (d.equals("R")) return 1;

        if (d.equals("L")) return -1;

        if (d.equals("U")) return 0;

        if (d.equals("D")) return 0;

        else
            return 0;
    }

    public static int findLeftMostColumn(ArrayList<LavaNode> nodes) {
        int m = Integer.MAX_VALUE;

        for (LavaNode n : nodes) {
            if (n.getColumn() < m)
                m = n.getColumn();
        }

        return m;
    }

    public static int findRightMostColumn(ArrayList<LavaNode> nodes) {
        int m = Integer.MIN_VALUE;

        for (LavaNode n : nodes) {
            if (n.getColumn() > m)
                m = n.getColumn();
        }

        return m;
    }

    public static int findTopRow(ArrayList<LavaNode> nodes) {
        int m = Integer.MAX_VALUE;

        for (LavaNode n : nodes) {
            if (n.getRow() < m)
                m = n.getRow();
        }

        return m;

    }

    public static int findBottomRow(ArrayList<LavaNode> nodes) {
        int m = Integer.MIN_VALUE;

        for (LavaNode n : nodes) {
            if (n.getRow() > m)
                m = n.getRow();
        }

        return m;

    }

}
