import java.util.ArrayList;

public class Box {
    private int boxNumber;
    private ArrayList<Lens> lens;

    public Box(int boxNumber) {
        this.boxNumber = boxNumber;
        lens = new ArrayList<Lens>();
    }

    public void processLens(Lens l) {
        if (l.getOperator().equals("-")) {
            for (int i = 0; i < lens.size(); i++) {
                if (lens.get(i).getLabel().equals(l.getLabel())) {
                    lens.remove(i);
                    i--;
                }
            }
        }

        if (l.getOperator().equals("=")) {
            boolean found = false;
            for (int i = 0; i < lens.size(); i++) {
                if (lens.get(i).getLabel().equals(l.getLabel())) {
                    found = true;
                    lens.get(i).updateFocalLength(l.getFocalLength());
                }
            }
            if (found == false) {
                lens.add(l);
            }
        }
    }

    public String toString() {
        if (lens.size() != 0)
            return "Box: " + boxNumber + ": " + lens;
        return "";
    }

    public int getFocalPower() {
        int total = 0;
        for (int i = 0; i < lens.size(); i++) {
            int b = boxNumber+1;
            int slot = i + 1;
            int f = lens.get(i).getFocalLength();
            int focalPower = b * slot * f;
            total += focalPower;
        }
        return total;
    }
}
