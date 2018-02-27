package com.flavioamurriocs.imagesmash;

import java.util.ArrayList;

/**
 * Grid. Class used to easily to manipulate 2d arrayList
 */
public class Grid<T> {

    public ArrayList<ArrayList<T>> values;

    //Creates a Grid of the indicated size, with null at each location.
    public Grid(int numRows, int numCols) {
        values = new ArrayList<>();
        for (int i = 0; i < numRows; i++)
            values.add(new ArrayList<>());
        for (int i = 0; i < numRows; i++)
            for (int j = 0; j < numCols; j++)
                values.get(i).add(null);
    }

    // Assumes the input is rectangular, and creates/fills the values field from its contents.
    public Grid(T[][] inGrid) {
        this(inGrid.length, inGrid[0].length);
        for (int i = 0; i < this.height(); i++)
            for (int j = 0; j < this.width(); j++)
                values.get(i).set(j, inGrid[i][j]);
    }

    // . Returns number of rows.
    public int height() {
        return values.size();
    }

    // . Returns number of columns.
    public int width() {
        return values.get(0).size();
    }

    // . Returns the item in row i and column j. This is a two-for-one indexing operation.
    public T get(int i, int j) {
        if (i < 0 || i >= this.height() || j < 0 || j >= this.width())
            return null;
        return values.get(i).get(j);
    }

    // Get method, using the a point object
    public T get(Point p) {
        return this.get(p.x, p.y);
    }

    // Special method that will wrap around if going out of bounds
    public T getWrap(int i, int j) {
        i = i < 0 ? i + this.height() : i;
        j = j < 0 ? j + this.width() : j;
        return this.get(i % this.height(), j % this.width());
    }

    // Assumes that location (i,j) exists in the Grid, and sets that location's value to the given value.
    public void set(int i, int j, T value) {
        values.get(i).set(j, value);
    }

    // This method performs two actions: first, it tries to remove the item at location (i,j). It also checks if the indicated row is now empty, and removes it when it is. Lastly, the removed value is returned.
    public T remove(int i, int j) {
        T ret = values.get(i).remove(j);
        if (values.get(i).isEmpty())
            values.remove(i);
        return ret;
    }

    // Remove method using point object
    public T remove(Point p) {
        return this.remove(p.x, p.y);
    }

    // Create a transpose copy of current grid and return it. Dont modify the original.
    public Grid<T> transpose() {
        Grid<T> ret = new Grid<>(this.width(), this.height());
        for (int i = 0; i < this.height(); i++)
            for (int j = 0; j < this.width(); j++)
                ret.set(j, i, this.get(i, j));
        return ret;
    }

    // Let's be lazy, and just return whatever values.toString() looks like.
    public String toString() {
        return this.values.toString();
        // StringBuilder sb = new StringBuilder();
        // for (ArrayList<?> list : this.values)
        //     sb.append(list + "\n");
        // return sb.toString();
    }

    // Used to view the grid in the debugger
    public String toDebugString() {
        // return this.values.toString();
        StringBuilder sb = new StringBuilder();
        for (ArrayList<?> list : this.values)
            sb.append(list + "\n");
        return sb.toString();
    }

    // Check for equallity in all aspects
    @SuppressWarnings("unchecked")
    public boolean equals(Object other) {
        try {
            if (this == other)
                return true;
            Grid<T> grid = (Grid<T>) other;
            if ((this.height() == grid.height() && this.width() == grid.width())) {
                for (int i = 0; i < this.height(); i++)
                    for (int j = 0; j < this.width(); j++) {
                        T a = this.get(i, j);
                        T b = grid.get(i, j);
                        if (!a.equals(b))
                            return false;
                    }
                return true;
            }
            return false;

        } catch (Exception e) {
            //TODO: handle exception
        }
        return true;

    }
}