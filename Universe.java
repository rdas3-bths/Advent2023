import java.util.Arrays;
import java.util.ArrayList;

public class Universe {
    private ArrayList<Integer> emptyRows;
    private ArrayList<Integer> emptyColumns;

    public Universe(ArrayList<Integer> emptyRows, ArrayList<Integer> emptyColumns) {
        this.emptyRows = emptyRows;
        this.emptyColumns = emptyColumns;
    }

    public ArrayList<Integer> getEmptyRows() {
        return emptyRows;
    }

    public ArrayList<Integer> getEmptyColumns() {
        return emptyColumns;
    }

    public String toString() {
        return "Empty Rows: " + emptyRows + "\nEmpty Columns: " + emptyColumns;
    }

    public boolean noGalaxyInRow(long row) {
        for (int r : emptyRows) {
            if (row == r) return true;
        }
        return false;
    }

    public boolean noGalaxyInColumn(long col) {
        for (int c : emptyColumns) {
            if (col == c) return true;
        }
        return false;
    }
}
