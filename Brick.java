public class Brick implements Comparable {
    private int xStart;
    private int xEnd;

    private int yStart;
    private int yEnd;

    private int zStart;
    private int zEnd;

    private int height;

    private static int bricksCreated = 0;

    private int brickNumber;

    public Brick(int xStart, int xEnd, int yStart, int yEnd, int zStart, int zEnd) {
        this.xStart = xStart;
        this.xEnd = xEnd;
        this.yStart = yStart;
        this.yEnd = yEnd;
        this.zStart = zStart;
        this.zEnd = zEnd;
        this.height = zEnd - zStart;
        bricksCreated++;
        this.brickNumber = bricksCreated;
    }

    public String toString() {
        return brickNumber + " : " + "x: " + xStart + "->" + xEnd + ", y: " + yStart + "->" + yEnd + ", z: " + zStart + "->" + zEnd;
    }

    public int getxStart() {
        return xStart;
    }

    public int getxEnd() {
        return xEnd;
    }

    public int getyStart() {
        return yStart;
    }

    public int getyEnd() {
        return yEnd;
    }

    public int getzStart() {
        return zStart;
    }

    public int getzEnd() {
        return zEnd;
    }

    public int getHeight() {
        return height;
    }

    public int getBrickNumber() {
        return brickNumber;
    }

    public void setzStart(int zStart) {
        this.zStart = zStart;
    }

    public void setzEnd(int zEnd) {
        this.zEnd = zEnd;
    }

    public int compareTo(Object o) {
        Brick b = (Brick)o;
        return this.zStart - b.zStart;
    }
}
