package com.flavioamurriocs.imagesmash;

import java.io.File;
import java.io.FileInputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * P1. Class with statics methods performs image smashing.
 */
public class P1 {
 
    // Calculate energy at a specific point in the grid. getWrap() function helps wrap around 
    public static int energyAt(Grid<RGB> grid, int r, int c) {
        RGB up = grid.getWrap(r - 1, c);
        RGB down = grid.getWrap(r + 1, c);
        RGB left = grid.getWrap(r, c - 1);
        RGB right = grid.getWrap(r, c + 1);
        return up.energy(down) + left.energy(right);
    }

    // Creates energy grid using the energyAt() at everysingle point in the grid. return eGrid.
    public static Grid<Integer> energy(Grid<RGB> grid) {
        Grid<Integer> ret = new Grid<>(grid.height(), grid.width());
        for (int i = 0; i < ret.height(); i++)
            for (int j = 0; j < ret.width(); j++)
                ret.set(i, j, energyAt(grid, i, j));
        // System.out.println(ret);
        return ret;
    }

    // create energy grid and generate node grid from it.
    // starting from the 2nd to last row, iterate up
    // pick the bottom point that has the lowest value and point the node to it
    // once done iter, iter thru the top row and find the lowest value
    // navigate the choosen node and create list to return
    public static List<Point> findVerticalPath(Grid<RGB> grid) {
        Grid<Integer> energyGrid = energy(grid);
        int row = grid.height();
        int col = grid.width();
        Grid<Node> nodeGrid = getNodeGrid(energyGrid, row, col);
        for (int i = row - 2; i >= 0; i--)
            for (int j = 0; j < col; j++)
                getLowest(nodeGrid, nodeGrid.get(i, j));
        Node head = nodeGrid.get(0, 0);
        int min = Integer.MAX_VALUE;
        for (Node node : nodeGrid.values.get(0))
            if (node.cost < min) {
                head = node;
                min = node.cost;
            }
        return nodeToList(head);
    }

    // converts energyGrid to nodeGrid with the energy value being the same and besthop=null
    private static Grid<Node> getNodeGrid(Grid<Integer> eGrid, int row, int col) {
        Grid<Node> nodeGrid = new Grid<>(row, col);
        for (int i = 0; i < row; i++)
            for (int j = 0; j < col; j++)
                nodeGrid.set(i, j, new Node(i, j, null, eGrid.get(i, j)));
        return nodeGrid;
    }

    // identif which points to traverse, watching for edge cases
    // iterate thru those, find the lowest and make the node point ot it and update value
    private static void getLowest(Grid<Node> nodeGrid, Node node) {
        int nextRow = node.p.x + 1;
        int bottomLeft = node.p.y - 1;
        int bottomRight = node.p.y + 1;
        bottomLeft = bottomLeft < 0 ? 0 : bottomLeft;
        bottomRight = bottomRight > nodeGrid.width()-1 ? nodeGrid.width()-1 : bottomRight;
        int min = Integer.MAX_VALUE;
        for (int i = bottomLeft; i <= bottomRight; i++) {
            Node temp = nodeGrid.get(nextRow, i);
            if (temp.cost < min) {
                node.bestHop = temp;
                min = temp.cost;
            }
        }
        node.cost += node.bestHop.cost;
    }

    // traverses node and creates a List<Points>
    private static ArrayList<Point> nodeToList(Node node) {
        ArrayList<Point> pList = new ArrayList<>();
        Node ptr = node;
        while (ptr != null) {
            pList.add(ptr.p);
            ptr = ptr.bestHop;
        }
        return pList;
    }

    // transpose the grid and then find the vertical of it
    // transpose point by inverting the points
    public static List<Point> findHorizontalPath(Grid<RGB> grid) {
        return invertList(findVerticalPath(grid.transpose()));
    }

    // invert all the point in the list and return the new list.
    private static List<Point> invertList(List<Point> pList) {
        ArrayList<Point> ret = new ArrayList<>();
        for (Point p : pList)
            ret.add(p.invert());
        return ret;
    }

    // traverse List and remove all the points from the grid in place, return the grid
    public static Grid<RGB> removeVerticalPath(Grid<RGB> grid, List<Point> path) {
        for (Point p : path)
            grid.remove(p.x, p.y);
        return grid;
    }

    // in order to ab=void grid fragmentation due to the fact that the grid does not
    // check if a jagged horizontal is remove, we transpose the grid before removing
    // since we are transposing the grid, we have to invert the path
    // and finally to have the grid "change in place", we reasign the 2d arraylist
    // in the grid object. not elegant but it works!
    public static Grid<RGB> removeHorizontalPath(Grid<RGB> grid, List<Point> path) {
        Grid<RGB> trans = grid.transpose();
        grid.values = removeVerticalPath(trans, invertList(path)).transpose().values;
        return grid;
    }

    // Discard the first line because we will always use P3
    // use the next to value to initialize the grid
    // Discard the fourth value(this is the color range)
    // use nested for loop to scan all values
    // this does not check if the  ppm file is corrupted
    public static Grid<RGB> ppm2grid(String filename) {
        Scanner sc = fileReader(filename);
        sc.next(); // Discard the header tag
        int width = sc.nextInt();
        int height = sc.nextInt();
        sc.nextInt(); // Discard range
        Grid<RGB> grid = new Grid<>(height, width);
        for (int i = 0; i < height; i++)
            for (int j = 0; j < width; j++) {
                int r = sc.nextInt();
                int g = sc.nextInt();
                int b = sc.nextInt();
                grid.set(i, j, new RGB(r, g, b));
            }
        return grid;
    }

    // Add P3 as header an then the dimmentions as well as the color range(255)
    // use nested for loop and use toFileString() to print out the rgb values.
    public static void grid2ppm(Grid<RGB> grid, String filename) {
        PrintWriter pw = filePrinter(filename);
        pw.println("P3");
        pw.println(grid.width() + " " + grid.height());
        pw.println("255");
        for (int i = 0; i < grid.height(); i++)
            for (int j = 0; j < grid.width(); j++)
                pw.println(grid.get(i, j).toFileString());
        pw.close();
    }

    // Helper to avoid making the above functions too long. Returns scanner from filename
    private static Scanner fileReader(String filename) {
        try {
            File file = new File(filename);
            FileInputStream fis = new FileInputStream(file);
            return new Scanner(fis);
        } catch (Exception e) {
            System.err.println("Error opening " + filename);
            return null;
        }
    }

    // Helper function that returns printWriter from filename.
    private static PrintWriter filePrinter(String filename) {
        try {
            return new PrintWriter(new File(filename));
        } catch (Exception e) {
            System.err.println("Error writing file " + filename);
            return null;
        }
    }

}