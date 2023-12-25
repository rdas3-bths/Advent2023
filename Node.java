import java.util.Arrays;


public class Node {
    private Point current;
    private Point[] neighbors;

    public Node(Point c) {
        current = c;
    }

    public Point getCurrent() {
        return current;
    }

    public void setCurrent(Point current) {
        this.current = current;
    }

    public Point[] getNeighbors() {
        return neighbors;
    }

    public void setNeighbors(Point[] neighbors) {
        this.neighbors = neighbors;
    }

    public String toString() {
        return current.toString() + " " + Arrays.toString(neighbors);
    }
}
