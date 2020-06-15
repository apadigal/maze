/*
 * Swash Tech Ltd.
 *
 * Direction.java
 *
 * © 2018 Swash Tech Ltd. All Rights Reserved
 */
// ---- Package ---------------------------------------------------------------
package uk.gov.dwp.maze.constant;
// ---- Import Statements -----------------------------------------------------

public enum  Direction {
/*
    NORTH(Movement.UP_WORD, Movement.BACKWARD, Movement.FORWARD),
    SOUTH(Movement.DOWN_WORD, Movement.FORWARD, Movement.BACKWARD),
    EAST(Movement.FORWARD, Movement.UP_WORD, Movement.DOWN_WORD),
    WEST(Movement.BACKWARD, Movement.DOWN_WORD, Movement.UP_WORD),;
*/
    NORTH(Movement.UP_WORD),
    SOUTH(Movement.DOWN_WORD),
    EAST(Movement.FORWARD),
    WEST(Movement.BACKWARD),;

    private final Movement forward;
    private Direction left, right;

    Direction(Movement forward) {
        this.forward = forward;
    }

    public Movement getForward() {
        return forward;
    }

    public Direction getLeft() {
        switch (this.name()){
            case "NORTH":
                return WEST;
            case "WEST":
                return SOUTH;
            case "SOUTH":
                return  EAST;
            case "EAST":
                return NORTH;
        }
        return null;
    }

    public Direction getRight() {
        switch (this.name()){
            case "NORTH":
                return EAST;
            case "WEST":
                return NORTH;
            case "SOUTH":
                return  WEST;
            case "EAST":
                return SOUTH;
        }
        return null;
    }
}