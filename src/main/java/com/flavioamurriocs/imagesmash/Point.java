package com.flavioamurriocs.imagesmash;

/**
 * Point. Class that helps us store a pair. Methods are self explanatory.
 */
public class Point {

    public int x, y;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Point invert() {
        return new Point(y, x);
    }

    // Returns a string formatted like "(4,5)" (with correct x, y values, and note there are no spaces).
    public String toString() {
        return String.format("(%d,%d)", this.x, this.y);
    }

    public boolean equals(Object p) {
        if (this == p)
            return true;
        if (p instanceof Point) {
            Point np = (Point) p;
            return np.x == this.x && np.y == this.y;
        }
        return false;
    }
}