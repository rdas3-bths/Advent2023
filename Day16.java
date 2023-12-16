import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Day16 {
    public static void main(String[] args) throws FileNotFoundException {
        ArrayList<String> fileData = getFileData("data/Day16_Input");
        String[][] mainGrid = new String[fileData.size()][fileData.get(0).length()];
        int[][] beamGrid = new int[fileData.size()][fileData.get(0).length()];

        for (int i = 0; i < fileData.size(); i++) {
            for (int j = 0; j < fileData.get(i).length(); j++) {
                mainGrid[i][j] = fileData.get(i).charAt(j) + "";
            }
        }
        System.out.println();
        ArrayList<Visit> visits = new ArrayList<Visit>();
        int max = -1;

        // do top row
        for (int i = 0; i < beamGrid[0].length; i++) {
            visits = new ArrayList<Visit>();
            beamGrid = new int[fileData.size()][fileData.get(0).length()];
            doBeam(0, i, mainGrid, beamGrid, 'd', visits);
            int count = countVisited(beamGrid);
            if (count > max) max = count;

        }

        // do bottom row
        for (int i = 0; i < beamGrid[0].length; i++) {
            visits = new ArrayList<Visit>();
            beamGrid = new int[fileData.size()][fileData.get(0).length()];
            doBeam(mainGrid.length-1, i, mainGrid, beamGrid, 'u', visits);
            int count = countVisited(beamGrid);
            if (count > max) max = count;

        }

        // do left column
        for (int i = 0; i < beamGrid.length; i++) {
            visits = new ArrayList<Visit>();
            beamGrid = new int[fileData.size()][fileData.get(0).length()];
            doBeam(i, 0, mainGrid, beamGrid, 'r', visits);
            int count = countVisited(beamGrid);
            if (count > max) max = count;
        }

        // do right column
        for (int i = 0; i < beamGrid.length; i++) {
            visits = new ArrayList<Visit>();
            beamGrid = new int[fileData.size()][fileData.get(0).length()];
            doBeam(i, mainGrid[0].length-1, mainGrid, beamGrid, 'l', visits);
            int count = countVisited(beamGrid);
            if (count > max) max = count;
        }
        System.out.println("Max: " + max);


    }


    public static int countVisited(int[][] beamGrid) {
        int count = 0;
        for (int[] row : beamGrid) {
            for (int i : row) {
                if (i == 1) count++;
            }
        }
        return count;
    }

    public static boolean hasVisited(ArrayList<Visit> visits, Visit v) {
        for (Visit other : visits) {
            if (other.getR() == v.getR() && other.getC() == v.getC() && other.getD() == v.getD()) return true;
        }
        return false;
    }

    public static void doBeam(int r, int c, String[][] mainGrid, int[][] beamGrid, char d, ArrayList<Visit> visits) {

        Visit v = new Visit(r, c, d);
        if (hasVisited(visits, v))
            return;
        visits.add(v);
        if (r < 0 || c < 0) return;
        if (c >= mainGrid[0].length) return;
        if (r >= mainGrid.length) return;

        beamGrid[r][c] = 1;

        // if you are at a dot, keep going in the correct direction
        if (mainGrid[r][c].equals(".")) {
            if (d == 'l')        doBeam(r, c-1, mainGrid, beamGrid, d, visits);
            else if (d == 'r')   doBeam(r, c+1, mainGrid, beamGrid, d, visits);
            else if (d == 'u')   doBeam(r-1, c, mainGrid, beamGrid, d, visits);
            else if (d == 'd')   doBeam(r+1, c, mainGrid, beamGrid, d, visits);
        }

        // if you are at |
        // if you are going l or r, split up and down
        // if you are going u or d, keep going in the correct direction
        else if (mainGrid[r][c].equals("|")) {
            if (d == 'u')        doBeam(r-1, c, mainGrid, beamGrid, d, visits);
            else if (d == 'd')   doBeam(r+1, c, mainGrid, beamGrid, d, visits);
            else if (d == 'l' || d == 'r') {
                doBeam(r, c, mainGrid, beamGrid, 'u', visits);
                doBeam(r, c, mainGrid, beamGrid, 'd', visits);
            }
        }

        // if you are at -
        // if you are going l or r, keep going in the same direction
        // if you are going up or d, split left and right
        else if (mainGrid[r][c].equals("-")) {
            if (d == 'l')   doBeam(r, c-1, mainGrid, beamGrid, d, visits);
            else if (d == 'r')   doBeam(r, c+1, mainGrid, beamGrid, d, visits);
            else if (d == 'u' || d == 'd') {
                doBeam(r, c, mainGrid, beamGrid, 'l', visits);
                doBeam(r, c, mainGrid, beamGrid, 'r', visits);
            }
        }

        // if you are at /
        // go to next spot
        // u --> r
        // d --> l
        // r --> u
        // l --> d
        else if (mainGrid[r][c].equals("/")) {
            if (d == 'u')   doBeam(r, c+1, mainGrid, beamGrid, 'r', visits);
            if (d == 'd')   doBeam(r, c-1, mainGrid, beamGrid, 'l', visits);
            if (d == 'r')   doBeam(r-1, c, mainGrid, beamGrid, 'u', visits);
            if (d == 'l')   doBeam(r+1, c, mainGrid, beamGrid, 'd', visits);
        }

        // if you are at \
        // go to next spot
        // u --> l
        // d --> r
        // r --> d
        // l --> u
        else if (mainGrid[r][c].equals("\\")) {
            if (d == 'u')   doBeam(r, c-1, mainGrid, beamGrid, 'l', visits);
            if (d == 'd')   doBeam(r, c+1, mainGrid, beamGrid, 'r', visits);
            if (d == 'r')   doBeam(r+1, c, mainGrid, beamGrid, 'd', visits);
            if (d == 'l')   doBeam(r-1, c, mainGrid, beamGrid, 'u', visits);
        }

    }

    public static void printBeamGrid(int[][] grid) {
        for (int[] row : grid) {
            for (int item : row) {
                System.out.print(item + " ");
            }
            System.out.println();
        }
    }

    public static void printMainGrid(String[][] grid) {
        for (String[] row : grid) {
            for (String item : row) {
                System.out.print(item + " ");
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
