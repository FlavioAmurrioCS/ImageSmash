package com.flavioamurriocs.imagesmash;

import java.io.File;
import java.io.FileInputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * P1
 */
public class P1 {

    public static int energyAt(Grid<RGB> grid, int r, int c) {
        RGB up = grid.getWrap(r - 1, c);
        RGB down = grid.getWrap(r + 1, c);
        RGB left = grid.getWrap(r, c - 1);
        RGB right = grid.getWrap(r, c + 1);
        return up.energy(down) + left.energy(right);
    }

    public static Grid<Integer> energy(Grid<RGB> grid) {
        Grid<Integer> ret = new Grid<>(grid.height(), grid.width());
        for (int i = 0; i < ret.height(); i++)
            for (int j = 0; j < ret.width(); j++)
                ret.set(i, j, energyAt(grid, i, j));
        System.out.println(ret);
        return ret;
    }

    public static List<Point> findVerticalPath(Grid<RGB> grid) {
        Grid<Integer> energyGrid = energy(grid);
        int row = grid.height();
        int col = grid.width();
        Grid<Node> nodeGrid = getNodeGrid(energyGrid, row, col);
        for (int i = row - 2; i >= 0; i--)
            for (int j = 0; j < col; j++)
                getLowest(nodeGrid, nodeGrid.get(i, j));
        Node head = null;
        int min = Integer.MAX_VALUE;
        for (Node node : nodeGrid.values.get(0))
            if (node.cost < min) {
                head = node;
                min = node.cost;
            }
        return nodeToList(head);
    }

    private static void getLowest(Grid<Node> nodeGrid, Node node) {
        int r = node.p.x + 1;
        int c = node.p.y - 1;
        int u = node.p.y + 1;
        c = c < 0 ? 0 : c;
        u = u >= nodeGrid.width() ? nodeGrid.width() : u;
        int min = Integer.MAX_VALUE;
        for (int i = c; i < u; i++) {
            Node temp = nodeGrid.get(r, i);
            if (temp.cost < min) {
                node.bestHop = temp;
                min = temp.cost;
            }
        }
        node.cost += node.bestHop.cost;
    }

    private static Grid<Node> getNodeGrid(Grid<Integer> eGrid, int row, int col) {
        Grid<Node> nodeGrid = new Grid<>(row, col);
        for (int i = 0; i < row; i++)
            for (int j = 0; j < col; j++)
                nodeGrid.set(i, j, new Node(i, j, null, eGrid.get(i, j)));
        return nodeGrid;
    }

    // public static List<Point> findVerticalPath(Grid<RGB> grid) {
    //     Grid<Integer> eGrid = energy(grid);
    //     Grid<Node> nGrid = new Grid<>(grid.height(), grid.width());
    //     for (int i = grid.height() - 1; i >= 0; i--)
    //         for (int j = 0; j < grid.width(); j++) {
    //             Node newNode = new Node(i, j, null, eGrid.get(i, j));
    //             nGrid.set(i, j, newNode);
    //             int nextRow = i + 1;
    //             int min = Integer.MAX_VALUE;
    //             for (int k = j - 1; k < j - 1 + 3; k++) {
    //                 Node iNode = nGrid.get(nextRow, k);
    //                 int energy = iNode != null ? iNode.cost : Integer.MAX_VALUE;
    //                 newNode.bestHop = energy < min ? iNode : newNode.bestHop;
    //                 min = energy < min ? energy : min;
    //             }
    //             if (newNode.bestHop != null)
    //                 newNode.cost += newNode.bestHop.cost;
    //         }
    //     Node best = null;
    //     int min = Integer.MAX_VALUE;
    //     for (Node ptr : nGrid.values.get(0)) {
    //         if (ptr.cost < min) {
    //             best = ptr;
    //             min = ptr.cost;
    //         }
    //     }
    //     return nodeToList(best);
    // }

    private static ArrayList<Point> nodeToList(Node node) {
        ArrayList<Point> pList = new ArrayList<>();
        Node ptr = node;
        while (ptr != null) {
            pList.add(ptr.p);
            ptr = ptr.bestHop;
        }
        return pList;
    }

    public static List<Point> findHorizontalPath(Grid<RGB> grid) {
        return invertList(findVerticalPath(grid.transpose()));
    }

    private static List<Point> invertList(List<Point> pList) {
        ArrayList<Point> ret = new ArrayList<>();
        for (Point p : pList)
            ret.add(p.invert());
        return ret;
    }

    public static Grid<RGB> removeVerticalPath(Grid<RGB> grid, List<Point> path) {
        for (Point p : path)
            grid.remove(p.x, p.y);
        return grid;
    }

    public static Grid<RGB> removeHorizontalPath(Grid<RGB> grid, List<Point> path) {
        for (Point p : path)
            grid.remove(p.x, p.y);
        return grid;
    }

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

    private static PrintWriter filePrinter(String filename) {
        try {
            return new PrintWriter(new File(filename));
        } catch (Exception e) {
            System.err.println("Error writing file " + filename);
            return null;
        }
    }

    // private static File fileCleaner(String filename) {
    //     try {
    //         Scanner sc = new Scanner(new FileInputStream(new File(filename)));
    //         File out = new File("modify-" + filename);
    //         PrintWriter pw = new PrintWriter(new File(filename));
    //         while (sc.hasNextLine()) {
    //             String str = sc.nextLine();
    //             if (str.charAt(0) != '#')
    //                 pw.println(str);
    //         }
    //         pw.close();
    //         sc.close();
    //         return out;
    //     } catch (Exception e) {
    //         return new File(filename);
    //     }
    // }

}