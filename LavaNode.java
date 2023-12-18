public class LavaNode {
    private int row;
    private int column;

    private char symbol;

    private boolean inside;

    private String rgb;

    private boolean empty;

    public LavaNode(int row, int column, String rgb) {
        this.row = row;
        this.column = column;
        this.symbol = '#';
        this.rgb = rgb;
    }

    public LavaNode(int row, int column) {
        empty = true;
        this.row = row;
        this.column = column;
        this.symbol = '.';
        this.rgb = "";
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getColumn() {
        return column;
    }

    public void setColumn(int column) {
        this.column = column;
    }

    public char getSymbol() {
        return symbol;
    }

    public void setSymbol(char symbol) {
        this.symbol = symbol;
    }

    public boolean isInside() {
        return inside;
    }

    public void setInside(boolean inside) {
        this.inside = inside;
    }

    public String toString() {
        return row + ", " + column + ": " + symbol;
    }
}
