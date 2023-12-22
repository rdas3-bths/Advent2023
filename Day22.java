import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Scanner;
import java.util.Collections;

public class Day22 {
    public static void main(String[] args) {
        ArrayList<String> fileData = getFileData("data/Day22_Input");
        ArrayList<Brick> bricks = new ArrayList<Brick>();
        for (String line : fileData) {
            String[] startEnd = line.split("~");
            String[] startPoints = startEnd[0].split(",");
            String[] endPoints = startEnd[1].split(",");

            int xStart = Integer.parseInt(startPoints[0]);
            int xEnd = Integer.parseInt(endPoints[0]);

            int yStart = Integer.parseInt(startPoints[1]);
            int yEnd = Integer.parseInt(endPoints[1]);

            int zStart = Integer.parseInt(startPoints[2]);
            int zEnd = Integer.parseInt(endPoints[2]);

            Brick b = new Brick(xStart, xEnd, yStart, yEnd, zStart, zEnd);
            bricks.add(b);
        }

        Collections.sort(bricks);


        int highX = findHighestEndX(bricks);
        int highY = findHighestEndY(bricks);

        ArrayList<int[][]> layers = new ArrayList<int[][]>();


        // put first brick on, layer 1, nothing is there yet
        Brick first = bricks.get(0);
        int height = first.getHeight();

        // add layers needed
        for (int i = 0; i <= height; i++) {
            addLayer(highX, highY, layers);
        }

        for (int i = 0; i <= height; i++) {
            putBrick(first, layers.get(i), i);
        }

        // get next brick (start at index 1)


        // if it can, keep going down until you find lowest layer it can go on
        int nextBrick = 1;
        for (int j = nextBrick; j < bricks.size(); j++) {
            Brick b = bricks.get(j);
            int currentBrickHeight = b.getHeight();
            int topLayerIndex = layers.size()-1;
            int[][] topLayer = layers.get(topLayerIndex);

            // check top layer. can it go there?
            // if it can't, make new layer for height of brick
            if (!canBrickFit(b, topLayer)) {
                // add layers needed
                for (int i = 0; i <= currentBrickHeight; i++) {
                    addLayer(highX, highY, layers);
                }
                int start = topLayerIndex+1;
                for (int i = start; i <= start+currentBrickHeight; i++) {
                    putBrick(b, layers.get(i), i);
                }
            }
            else {

                int lowestLayerIndex = topLayerIndex;
                // if it can, keep going down until you find lowest layer it can go on
                for (int check = lowestLayerIndex; check >= 0; check--) {
                    int[][] currentLayer = layers.get(check);
                    if (canBrickFit(b, currentLayer))
                        lowestLayerIndex = check;
                    else
                        break;
                }
                //System.out.println(b + " can fit on layer " + (lowestLayerIndex+1));

                // check if we need more layers
                int h = b.getHeight();
                int layersNeeded = h - (layers.size() - lowestLayerIndex);
                for (int i = 0; i <= layersNeeded; i++) {
                    addLayer(highX, highY, layers);
                }
                int start = lowestLayerIndex;
                for (int i = start; i <= start+h; i++) {
                    putBrick(b, layers.get(i), i);
                }
            }
        }



        for (int i = 0; i < layers.size(); i++) {
            //System.out.println("Layer " + (i+1));
            //printLayer(layers.get(i));

        }

        // how do I know if I can disintegrate?
        // a brick is not supporting any other brick
        // every brick i am supporting has ANOTHER brick supporting it thats not me


        for (Brick b : bricks) {
            getBricksSupportedByThisBrick(b, layers);
        }


        ArrayList<Integer> allSupportList = new ArrayList<Integer>();
        for (Brick b : bricks) {
            for (int s : b.getBricksSupportedByThis())
                allSupportList.add(s);
        }

        ArrayList<Brick> cannotBeDestroyed = new ArrayList<Brick>();

        int count = 0;
        for (Brick b : bricks) {
            boolean destroy = true;
            for (int x : b.getBricksSupportedByThis()) {
                if (Collections.frequency(allSupportList, x) == 1)
                    destroy = false;
            }
            if (destroy) {
                count++;
            }
            else {
                cannotBeDestroyed.add(b);
            }

        }

        System.out.println("Part one: " + count);

        int partTwo = 0;
        for (Brick b : cannotBeDestroyed) {
            ArrayList<Integer> chain = new ArrayList<Integer>();
            chain.add(b.getBrickNumber());
            addChainedList(b, chain, bricks);
            partTwo += (chain.size()-1);
        }

        System.out.println("Part two: " + partTwo);


    }

    public static ArrayList<Integer> getSupportedBy(Brick b, ArrayList<Brick> bricks) {
        ArrayList<Integer> supportedBy = new ArrayList<Integer>();
        for (Brick check : bricks) {
            if (check.getBricksSupportedByThis().contains(b.getBrickNumber()))
                supportedBy.add(check.getBrickNumber());
        }
        return supportedBy;
    }

    public static void addChainedList(Brick b, ArrayList<Integer> chain, ArrayList<Brick> bricks) {

        for (int x : b.getBricksSupportedByThis()) {
            Brick br = findBrick(bricks, x);
            if (!chain.contains(x)) {
                // add if all of this supporting bricks are in chain
                ArrayList<Integer> supportingBricks = getSupportedBy(findBrick(bricks, x), bricks);
                boolean add = true;
                for (int sup : supportingBricks) {
                    if (!chain.contains(sup))
                        add = false;
                }
                if (add)
                    chain.add(x);
            }
            addChainedList(br, chain, bricks);

        }
        if (b.getBricksSupportedByThis().isEmpty()) return;
    }

    public static Brick findBrick(ArrayList<Brick> bricks, int number) {
        for (Brick b : bricks) {
            if (b.getBrickNumber() == number)
                return b;
        }
        return null;
    }

    public static void getBricksSupportedByThisBrick(Brick b, ArrayList<int[][]> layers) {
        ArrayList<Integer> supportList = new ArrayList<Integer>();
        if (b.getLayer() == layers.size()-1)
            return;
        int[][] currentLayer = layers.get(b.getLayer());
        int[][] aboveLayer = layers.get(b.getLayer()+1);

        for (int i = 0; i < currentLayer.length; i++) {
            for (int j = 0; j < currentLayer[0].length; j++) {
                if (currentLayer[i][j] == b.getBrickNumber() && aboveLayer[i][j] != 0) {
                    if (!supportList.contains(aboveLayer[i][j]))
                        supportList.add(aboveLayer[i][j]);
                }
            }
        }
        b.setBricksSupportedbyThis(supportList);
    }

    public static void putBrick(Brick b, int[][] layer, int layerNumber) {
        int number = b.getBrickNumber();
        b.setLayer(layerNumber);
        for (int x = b.getxStart(); x <= b.getxEnd(); x++) {
            for (int y = b.getyStart(); y <= b.getyEnd(); y++) {
                layer[x][y] = number;
            }
        }
    }

    public static boolean canBrickFit(Brick b, int[][] layer) {
        boolean canFit = true;
        for (int x = b.getxStart(); x <= b.getxEnd(); x++) {
            for (int y = b.getyStart(); y <= b.getyEnd(); y++) {
                if (layer[x][y] != 0)
                    canFit = false;
            }
        }
        return canFit;
    }

    public static void printLayer(int[][] layer) {
        for (int[] r : layer) {
            for (int i : r) {
                System.out.print(i + " ");
            }
            System.out.println();
        }
    }

    public static void addLayer(int highX, int highY, ArrayList<int[][]> layers) {
        int[][] layer = new int[highX+1][highY+1];
        layers.add(layer);
    }

    public static int findHighestEndX(ArrayList<Brick> bricks) {
        int high = -1;
        for (Brick b : bricks) {
            if (b.getxEnd() > high)
                high = b.getxEnd();
        }
        return high;
    }

    public static int findHighestEndY(ArrayList<Brick> bricks) {
        int high = -1;
        for (Brick b : bricks) {
            if (b.getyEnd() > high)
                high = b.getyEnd();
        }
        return high;
    }

    public static ArrayList<String> getFileData(String fileName) {
        ArrayList<String> fileData = new ArrayList<String>();
        try {
            File f = new File(fileName);
            Scanner s = new Scanner(f);
            while (s.hasNextLine()) {
                String line = s.nextLine();
                if (!line.equals(""))
                    fileData.add(line);
            }
            return fileData;
        }
        catch (FileNotFoundException e) {
            return fileData;
        }
    }
}
