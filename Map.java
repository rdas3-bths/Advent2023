public class Map {
    private long sourceNumber;
    private long destinationNumber;
    private String mapType;
    private long range;

    public Map(long sourceNumber, long destinationNumber, String mapType, long range) {
        this.sourceNumber = sourceNumber;
        this.destinationNumber = destinationNumber;
        this.mapType = mapType;
        this.range = range;
    }

    public long getSourceNumber() {
        return sourceNumber;
    }

    public void setSourceNumber(long sourceNumber) {
        this.sourceNumber = sourceNumber;
    }

    public long getDestinationNumber() {
        return destinationNumber;
    }

    public void setDestinationNumber(long destinationNumber) {
        this.destinationNumber = destinationNumber;
    }

    public String getMapType() {
        return mapType;
    }

    public void setMapType(String mapType) {
        this.mapType = mapType;
    }

    public long getRange() {
        return range;
    }

    public void setRange(long range) {
        this.range = range;
    }

    public String toString() {
        return "Map type: " + mapType + " Source: " + sourceNumber + ", Destination: " + destinationNumber +
                " Range: " + range;
    }
}
