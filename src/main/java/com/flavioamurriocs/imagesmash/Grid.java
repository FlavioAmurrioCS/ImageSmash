package com.flavioamurriocs.imagesmash;

import java.util.ArrayList;

/**
 * Grid
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

    public T get(Point p) {
        return this.get(p.x, p.y);
    }

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

    public T remove(Point p) {
        return this.remove(p.x, p.y);
    }

    // Creates and returns a transposed version of the Grid (the original leftmost column is the new first row; second-leftmost original column is the new second row; etc). Doesn't modify the original.
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

    // When other is also a Grid, and both grids are the same size, and every single location holds items that are .equals()-equivalent to each other, this method returns true. (false, otherwise).
    @SuppressWarnings("unchecked")
    public boolean equals(Object other) {
        if (this == other)
            return true;
        if (other instanceof Grid<?>) {
            Grid<T> grid = (Grid<T>) other;
            if (this.height() != grid.height() || this.width() != grid.width())
                return false;
            for (int i = 0; i < this.height(); i++)
                for (int j = 0; j < this.width(); j++)
                    if (!this.get(i, j).equals(grid.get(i, j)))
                        return false;
            return true;
        }
        return false;
    }
}