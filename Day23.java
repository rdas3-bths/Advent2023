import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Collections;

public class Day23 {


    public static void main(String[] args) {
        ArrayList<String> fileData = getFileData("data/Day23_Input");
        ArrayList<Node> nodes = new ArrayList<Node>();
        char[][] maze = new char[fileData.size()][fileData.get(0).length()];

        for (int j = 0; j < fileData.size(); j++) {
            for (int i = 0; i < fileData.get(j).length(); i++) {
                char spot = fileData.get(j).charAt(i);
                if (spot == '^') spot = '.';
                if (spot == '<') spot = '.';
                if (spot == '>') spot = '.';
                if (spot == 'v') spot = '.';
                maze[j][i] = spot;
            }
        }

        for (int i = 0; i < maze.length; i++) {
            for (int j = 0; j < maze[0].length; j++) {
                if (maze[i][j] == '.') {
                    Point p = new Point(i, j);
                    Node n = new Node(p);
                    nodes.add(n);
                }
            }
        }

        for (int i = 0; i < nodes.size(); i++) {
            Node n = nodes.get(i);
            String directions = getDirections(n.getCurrent().getX(), n.getCurrent().getY(), "U", maze);
            Point[] neighbors = new Point[directions.length()];
            n.setNeighbors(neighbors);
            for (int j = 0; j < directions.length(); j++) {
                int nX = n.getCurrent().getX();
                int nY = n.getCurrent().getY();
                String d = directions.charAt(j) + "";
                if (d.equals("D")) nX++;
                if (d.equals("U")) nX--;
                if (d.equals("L")) nY--;
                if (d.equals("R")) nY++;
                Point p = getPoint(nodes, nX, nY);
                neighbors[j] = p;

            }
        }
        Node start = getNode(nodes, 0, 1);
        ArrayList<Node> visited = new ArrayList<Node>();
        ArrayList<Integer> solutions = new ArrayList<Integer>();
        solveGraph(start, visited, nodes, 0, solutions);
        Collections.sort(solutions);
        System.out.println(solutions);
    }

    public static Point getPoint(ArrayList<Node> nodes, int x, int y) {
        for (int i = 0; i < nodes.size(); i++) {
            Node n = nodes.get(i);
            if (n.getCurrent().getX() == x && n.getCurrent().getY() == y) {
                return n.getCurrent();
            }
        }
        return null;
    }

    public static Node getNode(ArrayList<Node> nodes, int x, int y) {
        for (int i = 0; i < nodes.size(); i++) {
            Node n = nodes.get(i);
            if (n.getCurrent().getX() == x && n.getCurrent().getY() == y) {
                return n;
            }
        }
        return null;
    }

    public static void solveGraph(Node start, ArrayList<Node> visited, ArrayList<Node> alLNodes, int steps, ArrayList<Integer> solutions) {
        ArrayList<Node> nodesChecked = new ArrayList<Node>();
        nodesChecked.add(start);
        for (Node v : visited) {
            nodesChecked.add(v);
        }


        if (start.getCurrent().getX() == 50) {
            solutions.add(steps);
            return;
        }

        Point[] neighbors = start.getNeighbors();
        for (Point p : neighbors) {
            if (getPoint(nodesChecked, p.getX(), p.getY()) == null) {
                Node next = getNode(alLNodes, p.getX(), p.getY());
                solveGraph(next, nodesChecked, alLNodes, steps+1, solutions);
            }
        }


    }

    public static void solveMaze(int r, int c, String currentDirection, char[][] maze, int steps, ArrayList<Integer> solutions) {

        maze[r][c] = 'X';
        if (r == maze[0].length-1) {
            solutions.add(steps);
            return;
        }


        String nextDirection = getDirections(r, c, currentDirection, maze);
        // for every direction in nextDirection, do this:
        for (int i = 0; i < nextDirection.length(); i++) {
            String goNext = nextDirection.charAt(i) + "";
            //System.out.println(r + " " + c + " " + goNext);
            if (goNext.equals("U")) {
                solveMaze(r-1, c, "U", copyMap(maze), steps+1, solutions);
            }

            if (goNext.equals("D")) {
                solveMaze(r+1, c, "D", copyMap(maze), steps+1, solutions);
            }

            if (goNext.equals("L")) {
                solveMaze(r, c-1, "L", copyMap(maze), steps+1, solutions);
            }

            if (goNext.equals("R")) {
                solveMaze(r, c+1, "R", copyMap(maze), steps+1, solutions);
            }
        }

    }

    public static String getDirections(int r, int c, String currentDirection, char[][] maze) {

        if (maze[r][c] == '^') return "U";
        if (maze[r][c] == 'v') return "D";
        if (maze[r][c] == '<') return "L";
        if (maze[r][c] == '>') return "R";

        String checkDirections = "";
//        if (currentDirection.equals("D")) checkDirections = "LRD";
//        if (currentDirection.equals("R")) checkDirections = "UDR";
//        if (currentDirection.equals("L")) checkDirections = "UDL";
//        if (currentDirection.equals("U")) checkDirections = "LRU";

        checkDirections = "UDLR";

        String finalDirections = "";

        for (int i = 0; i < checkDirections.length(); i++) {
            String d = checkDirections.charAt(i) + "";

            if (d.equals("D")) {
                if (checkDown(r, c, maze))
                    finalDirections += "D";
            }

            if (d.equals("U")) {
                if (checkUp(r, c, maze))
                    finalDirections += "U";
            }

            if (d.equals("L")) {
                if (checkLeft(r, c, maze))
                    finalDirections += "L";
            }

            if (d.equals("R")) {
                if (checkRight(r, c, maze))
                    finalDirections += "R";
            }

        }

        return finalDirections;
    }

    public static boolean checkDown(int r, int c, char[][] maze) {
        if (maze.length -1 == r) return false;

        if (maze[r+1][c] != '#' && maze[r+1][c] != '^' && maze[r+1][c] != 'X') return true;

        return false;
    }

    public static boolean checkUp(int r, int c, char[][] maze) {
        if (r == 0) return false;

        if (maze[r-1][c] != '#' && maze[r-1][c] != 'v' && maze[r-1][c] != 'X') return true;

        return false;
    }

    public static boolean checkLeft(int r, int c, char[][] maze) {
        if (c == 0) return false;

        if (maze[r][c-1] != '#' && maze[r][c-1] != '>' && maze[r][c-1] != 'X') return true;

        return false;
    }

    public static boolean checkRight(int r, int c, char[][] maze) {
        if (c == maze[r].length - 1) return false;

        if (maze[r][c+1] != '#' && maze[r][c+1] != '<' && maze[r][c+1] != 'X') return true;

        return false;
    }

    public static char[][] copyMap(char[][] maze) {
        char[][] copy = new char[maze.length][maze[0].length];

        for (int i = 0; i < maze.length; i++) {
            for (int j = 0; j < maze[0].length; j++) {
                copy[i][j] = maze[i][j];
            }
        }

        return copy;
    }

    public static void printMaze(char[][] maze) {

        final String ANSI_RED = "\u001B[31m";
        final String ANSI_RESET = "\u001B[0m";


        for (char[] row : maze) {
            for (char c : row) {
                if (c == 'X') {
                    System.out.print(ANSI_RED  + c + " " + ANSI_RESET);
                }
                else
                    System.out.print(c + " " );
            }
            System.out.println();
        }
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
