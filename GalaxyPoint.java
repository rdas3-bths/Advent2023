public class GalaxyPoint {
    private int row;
    private int col;
    private int galaxyNumber;
    private static final int EXPAND = 1;

    public GalaxyPoint(int row, int col, int galaxyNumber) {
        this.row = row;
        this.col = col;
        this.galaxyNumber = galaxyNumber;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public int getGalaxyNumber() {
        return galaxyNumber;
    }

    public static long computeDistance(GalaxyPoint g1, GalaxyPoint g2, Universe u) {
        long rowDiff = Math.abs(g1.getRow() - g2.getRow());
        long colDiff = Math.abs(g1.getCol() - g2.getCol());
        long minRow = Math.min(g1.getRow(), g2.getRow());
        long minCol = Math.min(g1.getCol(), g2.getCol());
        long y = minRow + rowDiff;
        for (long i = minRow; i < y; i++) {
            if (u.noGalaxyInRow(i)) {
                rowDiff += EXPAND;
            }

        }
        y = (minCol+colDiff);
        for (long i = minCol; i < y; i++) {
            if (u.noGalaxyInColumn(i)) {
                colDiff += EXPAND;
            }

        }
        long total = rowDiff + colDiff;
        return total;
    }

    public String toString() {
        return "Galaxy number: " + galaxyNumber + ": (" + row + "," + col + ")";
    }
}
