import jdk.jshell.spi.SPIResolutionException;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Day18_Part2 {
    public static void main(String[] args) throws FileNotFoundException {
        ArrayList<String> fileData = getFileData("data/Day18_Input");
        ArrayList<Point> origVertices = new ArrayList<Point>();


        long row = 0;
        long column = 0;
        long perimeter = 0;
        String currentDirection = "";
        long amount = 0;

        for (int c = 0; c < fileData.size(); c++) {

            String[] data = fileData.get(c).split(" ");

            String rgb = data[2].substring(data[2].indexOf("(")+2, data[2].length()-1);
            String lastDigit = rgb.charAt(rgb.length()-1) + "";
            String hexNumber = rgb.substring(0, rgb.length()-1);
            amount = Integer.parseInt(hexNumber, 16);
            if (lastDigit.equals("0")) currentDirection = "R";
            if (lastDigit.equals("1")) currentDirection = "D";
            if (lastDigit.equals("2")) currentDirection = "L";
            if (lastDigit.equals("3")) currentDirection = "U";

            perimeter += amount;


            for (long i = 0; i < amount; i++) {
                row = row + getRowOffset(currentDirection);
                column = column + getColumnOffset(currentDirection);

                if (i == (amount-1)) {

                    Point p = new Point(row, column);
                    origVertices.add(p);

                }
            }


        }

        // for each line segment
        // add the rows, and divide by 2
        // multiply that by the width (column change)
        // thank you google for telling me how to find the area
        // of an irregular polygon
        // credit: https://www.mathsisfun.com/geometry/area-irregular-polygons.html

        long area = 0;
        for (int i = 0; i < origVertices.size(); i++) {
            Point p1 = origVertices.get(i);
            int nextIndex;
            if (i == origVertices.size()-1)
                nextIndex = 0;
            else
                nextIndex = i + 1;

            Point p2 = origVertices.get(nextIndex);

            long height = (p1.getX() + p2.getX()) / 2;
            long width = (p1.getY() - p2.getY());
            area += (height * width);
        }

        System.out.println("Perimeter: " + perimeter);
        System.out.println("Area: " + area);

        // some weird math formula to find the area of an irregular polygon INCLUDING
        // the points on the vertices
        // credit: https://en.wikipedia.org/wiki/Pick%27s_theorem
        long a = (long)(area - perimeter / 2) + 1;
        a = a + perimeter;

        System.out.println("Answer: " + a);



    }





    public static ArrayList<String> getFileData(String inputFile) throws FileNotFoundException {
        ArrayList<String> fileData = new ArrayList<String>();
        File f = new File(inputFile);
        Scanner s = new Scanner(f);
        while (s.hasNext()) {
            fileData.add(s.nextLine());
        }
        return fileData;
    }

    public static int getRowOffset(String d) {
        if (d.equals("R")) return 0;

        if (d.equals("L")) return 0;

        if (d.equals("U")) return -1;

        if (d.equals("D")) return 1;

        else
            return 0;
    }

    public static int getColumnOffset(String d) {
        if (d.equals("R")) return 1;

        if (d.equals("L")) return -1;

        if (d.equals("U")) return 0;

        if (d.equals("D")) return 0;

        else
            return 0;
    }


}
