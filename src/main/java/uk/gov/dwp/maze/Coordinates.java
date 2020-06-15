/*
 * Swash Tech Ltd.
 *
 * Coordinate.java
 *
 * © 2018 Swash Tech Ltd. All Rights Reserved
 */
// ---- Package ---------------------------------------------------------------
package uk.gov.dwp.maze;
// ---- Import Statements -----------------------------------------------------

import uk.gov.dwp.maze.exception.InvalidMazeException;

import java.util.Objects;

public class Coordinates {
    private int x;
    private int y;

    public Coordinates(int x, int y) {
        if(x<0 || y<0)
            throw new InvalidMazeException("Invalid Coordinates : {" + x +", "+ y +"}");
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    @Override
    public String toString() {
        return "Coordinates{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Coordinates that = (Coordinates) o;
        return x == that.x &&
                y == that.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}