package com.flavioamurriocs.imagesmash;

import java.util.List;

// This class is the runner for testing the image smashing

public class App {

    public static final String FOLDER = "./image_folder/";

    // Pass argument. The args0 is the source jpg in the image_folder. 
    // Args1 is how many verticals to remove
    // Args2 is how many horizontals to remove
    public static void main(String[] args) {
        String src = args[0];
        int vertical = Integer.parseInt(args[1]);
        int horizontal = Integer.parseInt(args[2]);

        System.out.println(
                String.format("Remove %s vertical and %s horizontal pixels from %s", vertical, horizontal, src));
        String originaljpg = FOLDER + src;
        String orignalppm = FOLDER + src + ".ppm";
        String modifyppm = FOLDER + src + "_" + vertical + "_" + horizontal + ".ppm";
        String modifyjpg = FOLDER + src + "_" + vertical + "_" + horizontal + ".jpg";

        try {
            Runtime.getRuntime().exec(String.format("magick %s -compress none %s", originaljpg, orignalppm));
            Thread.sleep(2000);
            Grid<RGB> grid = P1.ppm2grid(orignalppm);
            grid = removeVertical(grid, vertical);
            grid = removeHorizontal(grid, horizontal);
            P1.grid2ppm(grid, modifyppm);
            Runtime.getRuntime().exec(String.format("magick %s %s", modifyppm, modifyjpg));
        } catch (Exception e) {
            System.out.println(e);
        }

    }

    // Remove verticals
    private static Grid<RGB> removeVertical(Grid<RGB> grid, int count) {
        for (int i = 1; i <= count; i++) {
            List<Point> path = P1.findVerticalPath(grid);
            grid.values = P1.removeVerticalPath(grid, path).values;
            System.out.print("\rRemoving " + (i) + "th Vertical Line...");
        }
        System.out.println();
        return grid;
    }

    // Remove horizontals
    private static Grid<RGB> removeHorizontal(Grid<RGB> grid, int count) {
        for (int i = 1; i <= count; i++) {
            List<Point> path = P1.findHorizontalPath(grid);
            grid.values = P1.removeHorizontalPath(grid, path).values;
            System.out.print("\rRemoving " + (i) + "th Horizontal Line...");
        }
        System.out.println();
        return grid;
    }
}
