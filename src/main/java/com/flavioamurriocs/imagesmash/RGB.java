package com.flavioamurriocs.imagesmash;

/**
 * RGB. Conatiner of the red grenn blue vaues for each pixel. Most methods are self explanatory
 */
public class RGB {

    // color components. Assumed values are between 0-255 inclusive.
    public int r, g, b;

    public RGB(int r, int g, int b) {
        this.r = r;
        this.g = g;
        this.b = b;
    }

    public RGB() {
        this(1, 1, 1);
    }

    public boolean equals(Object other) {
        if (this == other)
            return true;
        if (other instanceof RGB) {
            RGB c = (RGB) other;
            return c.r == this.r && c.g == this.g && c.b == this.b;
        }
        return false;
    }

    public String toString() {
        return String.format("(%s,%s,%s)", r, g, b);
    }

    // use to output the rgb when writing to file
    public String toFileString() {
        return String.format("%s %s %s", r, g, b);
    }

    // Helper method that calculates the energy with respect to another rgb.
    public int energy(RGB rgb) {
        int rs = (this.r - rgb.r) * (this.r - rgb.r);
        int gs = (this.g - rgb.g) * (this.g - rgb.g);
        int bs = (this.b - rgb.b) * (this.b - rgb.b);
        return rs + gs + bs;
    }

}