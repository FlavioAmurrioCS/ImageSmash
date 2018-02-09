package com.flavioamurriocs.imagesmash;

import java.util.List;

/**
 * Hello world!
 *
 */
public class App {

    public static final String FOLDER = "./image_folder/";
    public static final String ORIGINAL = FOLDER + "original.ppm";
    public static final String MODIFY = FOLDER + "modify.ppm";
    public static void main(String[] args) {
        Grid<RGB> grid = P1.ppm2grid(ORIGINAL);
        // Grid<RGB> grid = new Grid<>(1, 1);
        grid.set(0, 0, new RGB(255, 255, 255));
        P1.grid2ppm(grid, MODIFY);

        System.out.println("Hellow Worlds!!");
        System.out.println("Hellow Worlds!!");
        System.out.println("Hellow Worlds!!");

        // Grid<RGB> grid = P1.ppm2grid(".temp_file.ppm");
        // List<Point> list = P1.findVerticalPath(grid);
        // System.out.println("Size: " + list.size());

        // P1.grid2ppm(grid, "out.ppm");
    }
}
