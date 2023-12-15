public class Lens {
    private String label;
    private String operator;
    private int focalLength;
    private int box;

    public Lens(String lensData) {
        int equalIndex = lensData.indexOf("=");
        int minusIndex = lensData.indexOf("-");
        if (equalIndex != -1)
            parseEqualsLens(lensData, equalIndex);
        else
            parseMinusLens(lensData, minusIndex);

    }

    public void parseMinusLens(String lensData, int minusIndex) {
        label = lensData.substring(0, minusIndex);
        operator = lensData.charAt(minusIndex) + "";
        focalLength = -1;
        box = getHashValue(label);
    }

    public void parseEqualsLens(String lensData, int equalIndex) {
        label = lensData.substring(0, equalIndex);
        operator = lensData.charAt(equalIndex) + "";
        focalLength = Integer.parseInt(lensData.substring(equalIndex+1));
        box = getHashValue(label);
    }

    public String toString() {
        if (focalLength == -1)
            return label + operator;
        else
            return label + operator + focalLength;
    }

    public int getHashValue(String data) {
        int hashValue = 0;
        for (int i = 0; i < data.length(); i++) {
            char c = data.charAt(i);
            int asciiValue = (int)c;
            hashValue += asciiValue;
            hashValue *= 17;
            hashValue %= 256;
        }
        return hashValue;
    }

    public void updateFocalLength(int focalLength) {
        this.focalLength = focalLength;
    }

    public String getLabel() {
        return label;
    }

    public String getOperator() {
        return operator;
    }

    public int getFocalLength() {
        return focalLength;
    }

    public int getBox() {
        return box;
    }
}
