package com.flavioamurriocs.imagesmash;

/**
 * Node
 */
public class Node {

    public Point p;
    public Node bestHop;
    public int cost;

    public Node(int r, int c, Node bestHop, int cost) {
        this.p = new Point(r, c);
        this.cost = cost;
        this.bestHop = bestHop;
    }

    public boolean equals(Object other) {
        if (this == other)
            return true;
        if (other instanceof Node) {
            Node node = (Node) other;
            return node.p.equals(this.p) || node.bestHop.equals(this.bestHop) || node.cost == this.cost;
        }
        return false;
    }
}