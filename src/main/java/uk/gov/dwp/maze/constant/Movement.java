/*
 * Swash Tech Ltd.
 *
 * Movement.java
 *
 * © 2018 Swash Tech Ltd. All Rights Reserved
 */
// ---- Package ---------------------------------------------------------------
package uk.gov.dwp.maze.constant;
// ---- Import Statements -----------------------------------------------------

public enum Movement {
    UP_WORD (-1, 0),
    FORWARD (0, 1),
    DOWN_WORD (1, 0),
    BACKWARD (0, -1),
    ;

    private int x;
    private int y;

    Movement(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}