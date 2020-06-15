package uk.gov.dwp.maze;

import uk.gov.dwp.maze.constant.Movement;
import uk.gov.dwp.maze.exception.InvalidMazeException;

public class ExplorerAuto {

    private final Maze maze;

    public ExplorerAuto(Maze maze) {
        this.maze = maze;
    }

    public void solveMaze() {
        if (this.maze == null)
            throw new InvalidMazeException("Maze is not instantiated");

        MazeNode startNode = maze.getStartNode();
        if (startNode == null)
            throw new InvalidMazeException("Start node not found in the maze");
        traverseMaze(startNode);
    }

    private boolean traverseMaze(MazeNode mazeNode) {
        //First validate is the maze node is wall or previously visited?
        if (mazeNode.isWall() || mazeNode.isVisited())
            return false;

        mazeNode.setVisited(true);
        //We are fine to travel this node but first check is this node is Exit?
        if (mazeNode.isExit()) {
            mazeNode.setPathNode(true);
            maze.setResolved(true);
            return true;
        }

        //Plan is to move Up, Right, Down, Left in sequence and decide where to move
        for (Movement movement : Movement.values()) {
            MazeNode nextMazeNode = maze.getMazeNode(mazeNode.getCoordinates().getX() + movement.getX(), mazeNode.getCoordinates().getY() + movement.getY());
            if (nextMazeNode == null)
                continue;
            if (traverseMaze(nextMazeNode)) {
                mazeNode.setPathNode(true);
                return true;
            }
        }
        return false;
    }
}
