/*
 * Swash Tech Ltd.
 *
 * MazeNode.java
 *
 * © 2018 Swash Tech Ltd. All Rights Reserved
 */
// ---- Package ---------------------------------------------------------------
package uk.gov.dwp.maze;
// ---- Import Statements -----------------------------------------------------

import uk.gov.dwp.maze.exception.InvalidMazeException;

import java.util.Objects;

public class MazeNode {
    private Coordinates coordinates;
    private boolean wall;
    private boolean path;
    private boolean exit;
    private boolean start;
    private boolean visited;
    private boolean pathNode;
    private char mazeChar;

    public MazeNode(int positionX, int positionY, char c) {
        this.coordinates = new Coordinates(positionX, positionY);
        this.mazeChar = c;
        switch (c){
            case 'X':
                wall = true;
                break;
            case 'S':
                start = true;
                break;
            case 'F':
                exit = true;
                break;
            case ' ':
                path = true;
                break;
            default:
                throw new InvalidMazeException("Invalid maze character found!!. Valid characters are (X, S, F and space)");
        }
    }

    public boolean isWall() {
        return wall;
    }

    public boolean isPath() {
        return path;
    }

    public boolean isExit() {
        return exit;
    }

    public boolean isStart() {
        return start;
    }

    public boolean isVisited() {
        return visited;
    }

    public void setVisited(boolean visited) {
        this.visited = visited;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public char getMazeChar() {
        return isPathNode() ? '.' : mazeChar;
    }

    public boolean isPathNode() {
        return pathNode;
    }

    public void setPathNode(boolean pathNode) {
        this.pathNode = pathNode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MazeNode mazeNode = (MazeNode) o;
        return Objects.equals(coordinates, mazeNode.coordinates);
    }

    @Override
    public int hashCode() {
        return Objects.hash(coordinates);
    }
}