public class GridEntry {
    private char symbol;
    private boolean startNumber;
    private boolean endNumber;
    private int row;
    private int col;

    public GridEntry(char s, boolean start, boolean end, int row, int col) {
        symbol = s;
        startNumber = start;
        endNumber = end;
        this.row = row;
        this.col = col;
    }

    public char getSymbol() {
        return symbol;
    }

    public void setSymbol(char symbol) {
        this.symbol = symbol;
    }

    public boolean isStartNumber() {
        return startNumber;
    }

    public void setStartNumber(boolean startNumber) {
        this.startNumber = startNumber;
    }

    public boolean isDigit() {
        return Character.isDigit(symbol);
    }

    public boolean isSymbol() {
        return (!Character.isDigit(symbol) && symbol != '.');
    }

    public boolean isEndNumber() {
        return endNumber;
    }

    public void setEndNumber(boolean endNumber) {
        this.endNumber = endNumber;
    }

    public String toString() {
        return symbol + " " + startNumber + " " + endNumber + " (" + row + "," + col + ")";
    }

    public boolean isGear() {
        return symbol == '*';
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }
}
