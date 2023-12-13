public class LocationEntry {
    private String location;
    private String leftLocation;
    private String rightLocation;

    public LocationEntry(String location, String leftLocation, String rightLocation) {
        this.location = location;
        this.leftLocation = leftLocation;
        this.rightLocation = rightLocation;
    }

    public String getLocation() {
        return location;
    }

    public String getLeftLocation() {
        return leftLocation;
    }

    public String getRightLocation() {
        return rightLocation;
    }

    public String toString() {
        return location + " = " + "(" + leftLocation + ", " + rightLocation + ")";
    }
}
