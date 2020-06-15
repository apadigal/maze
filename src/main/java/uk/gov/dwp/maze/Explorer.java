package uk.gov.dwp.maze;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.gov.dwp.maze.constant.Direction;
import uk.gov.dwp.maze.constant.Movement;
import uk.gov.dwp.maze.exception.InvalidMazeException;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Explorer {
    final Logger logger = LoggerFactory.getLogger(this.getClass().getName());

    private final Maze maze;
    private Coordinates currentCoordinates;
    private Direction currentDirection = Direction.NORTH;
    private boolean exitRequested;
    public Explorer(Maze maze){
        this.maze = maze;
        startJourney();
    }

    public void startJourney(){
        if(this.maze == null )
            throw new InvalidMazeException("Maze is not instantiated");

        MazeNode startNode = maze.getStartNode();
        setCurrentCoordinates(startNode.getCoordinates());
        if(startNode == null)
            throw new InvalidMazeException("Start node not found in the maze");
    }

    /**
     * This method is for solving the maze by explorer
     */
    public boolean solveMaze(){
        showHelp();
        while (!maze.isResolved()){
            try {
                int value = System.in.read();
                switch (value){
                    case 'f':
                        moveForward();
                        break;
                    case 'c':
                        whereAmI();
                        break;
                    case 'h':
                        showHelp();
                        break;
                    case 'l':
                        turnLeft();
                        break;
                    case 'r':
                        turnRight();
                        break;
                    case 'p':
                        maze.printMaze(this.getCurrentDirection(), this.getCurrentCoordinates());
                        break;
                    case 'x':
                        exitRequested = true;
                        break;
                }
            } catch (IOException e) {
                logger.error("Invalid option selected");
                showHelp();
            }
            //If user requested exit or explorer unable to move in any direction.
            if(exitRequested || !canIMoveInAnyDirection()){
                break;
            }
        }
        return maze.isResolved();
    }

    public void turnLeft(){
        Direction newDirection = getCurrentDirection().getLeft();
        logger.info("Turning Left :{}", newDirection.name());
        setCurrentDirection(newDirection);
        whereAmI();
    }

    public void turnRight(){
        Direction newDirection = getCurrentDirection().getRight();
        logger.info("Turning right :{}", newDirection.name());
        setCurrentDirection(newDirection);
        whereAmI();
    }

    private void showHelp(){
        StringBuffer stringBuffer = new StringBuffer("\n");
        stringBuffer.append("Use following options")
                    .append("\n").append("   h    help any time")
                    .append("\n").append("   f    move explorer forward.")
                    .append("\n").append("   r    turn right.")
                    .append("\n").append("   l    turn left.")
                    .append("\n").append("   l    turn left.")
                    .append("\n").append("   p    print mage with current coordinates and direction.")
                    .append("\n").append("   c    show where explorer currently located.")
                    .append("\n").append("   x    exit.");

        logger.info(stringBuffer.toString());

    }

    public String whereAmI(){
        StringBuffer stringBuffer = new StringBuffer("I am at maze node ");
        stringBuffer.append(this.getCurrentCoordinates().toString())
                .append("and facing towards ").append(this.getCurrentDirection().name());
        displayAllPossibleMovements();
        logger.info("Now At : {} and facing towards {}", this.getCurrentCoordinates(), this.getCurrentDirection().name() );
        return stringBuffer.toString();
    }

    public boolean moveForward(){
        Coordinates newCoordinates = getForwardCoordinates();
        MazeNode mazeNode = getNodeInFrontOfMe();
        if(mazeNode.isVisited() || mazeNode.isWall()) {
            if(mazeNode.isWall())
                logger.info("It's wall in front can't move forward");
            else
                logger.info("Already visited can't move forward");
            return false;
        }

        mazeNode.setVisited(true);
        mazeNode.setPathNode(true);
        //We are fine to travel this node but first check is this node is Exit?
        if(mazeNode.isExit()){
            maze.setResolved(true);
            maze.printMaze();
        }
        this.setCurrentCoordinates(newCoordinates);
        return true;
    }

    public MazeNode getNodeInFrontOfMe(){
        Coordinates newCoordinates = getForwardCoordinates();
        return maze.getMazeNode(newCoordinates.getX(), newCoordinates.getY());
    }

    public Coordinates getForwardCoordinates(){
        Movement movement = getCurrentDirection().getForward();
        return new Coordinates(this.getCurrentCoordinates().getX() + movement.getX(), this.getCurrentCoordinates().getY() + movement.getY());
    }

    public Coordinates getCurrentCoordinates() {
        return currentCoordinates;
    }

    public Direction getCurrentDirection() {
        return currentDirection;
    }

    public Map<Direction, Boolean> getAllPossibleMovements(){
        Direction direction = getCurrentDirection();
        Map<Direction, Boolean> map = new HashMap<>();
        Boolean possibleToMove;
        Movement movement;
        Coordinates coordinates;
        MazeNode mazeNode;

        for(int i=0; i< 4; i++) {
            possibleToMove = true;
            direction = direction.getLeft();
            movement = direction.getForward();
            coordinates = new Coordinates(this.getCurrentCoordinates().getX() + movement.getX(), this.getCurrentCoordinates().getY() + movement.getY());
            mazeNode = maze.getMazeNode(coordinates.getX(), coordinates.getY());
            if (mazeNode.isWall() || mazeNode.isVisited())
                possibleToMove = false;
            map.put(direction, possibleToMove);
        }

        return  map;
    }

    public void displayAllPossibleMovements(){
        Map<Direction, Boolean> map = getAllPossibleMovements();
        StringBuffer stringBuffer = new StringBuffer("\n");
        map.forEach((k,v)->stringBuffer.append(k + " direction movable? :"+ v + "\n"));

        logger.info(stringBuffer.toString());
    }
    public boolean canIMoveInAnyDirection(){
        return getAllPossibleMovements().values().contains(Boolean.TRUE);
    }

    private void setCurrentDirection(Direction currentDirection) {
        this.currentDirection = currentDirection;
    }

    private void setCurrentCoordinates(Coordinates currentCoordinates) {
        this.currentCoordinates = currentCoordinates;
    }
}
