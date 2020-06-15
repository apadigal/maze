package uk.gov.dwp.maze;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.gov.dwp.maze.constant.Direction;
import uk.gov.dwp.maze.exception.EmptyFileException;
import uk.gov.dwp.maze.exception.InvalidMazeException;

import java.io.File;
import java.net.URL;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Maze {
    private static final Logger log = LoggerFactory.getLogger(Maze.class);

    private final String fileName;
    private MazeNode[][] mazeNodes;
    private MazeNode startNode;
    private MazeNode exitNode;
    private boolean isResolved;
    private int wallCount;
    private int emptySpaceCount;

    public Maze(String fileName){
        this.fileName = fileName;
        loadMaze();
    }

    @lombok.SneakyThrows
    private void loadMaze(){
        URL url = getClass().getClassLoader().getResource(fileName);
        File file = Paths.get(url.toURI()).toFile();
        Scanner scanner = new Scanner(file);
        List<String> mazeLines = new ArrayList<>();
        int mazeCols = 0;
        //Read all the lines of the file
        while(scanner.hasNextLine()){
            String nextLine = scanner.nextLine();
            if(mazeCols == 0){
                mazeCols = nextLine.length();
            }
            else if(mazeCols != nextLine.length()){
                throw new InvalidMazeException("Given Maze "+ fileName +" is not having equal columns");
            }
            mazeLines.add(nextLine);
        }
        //Validate is mazeLines  is empty ?
        if(mazeLines.isEmpty())
            throw new EmptyFileException("Maze file is empty");

        //Initialize the mazeNodes array from the mazeLines
        mazeNodes = new MazeNode[mazeLines.size()][mazeLines.get(0).length()];

        //Create mazeNodes from the mazeLines
        for(int i=0; i< mazeLines.size(); i++){
            char[] array= mazeLines.get(i).toCharArray();
            for(int y =0 ; y < array.length; y++){
                MazeNode mazeNode = new MazeNode(i, y, array[y]);
                if(mazeNode.isWall()) wallCount++;
                if(mazeNode.isPath()) emptySpaceCount++;
                if(mazeNode.isStart()) {
                    if(startNode != null)
                        throw new InvalidMazeException("Only one start position possible in a given maze...");
                    startNode = mazeNode;
                }
                if(mazeNode.isExit()) {
                    if(exitNode != null)
                        throw new InvalidMazeException("Only one exit point possible in a given maze...");
                    exitNode = mazeNode;
                }
                mazeNodes[i][y] = mazeNode;
            }
        }
        //Validate is startNode present?
        if(startNode == null)
            throw new InvalidMazeException("Starting point not available in a given maze...");

        //Validate is exitNode present?
        if(exitNode == null)
            throw new InvalidMazeException("Exit point not available in a given maze...");
    }

    public MazeNode getMazeNode(int row, int col){
        if(row >= 0 && row < mazeNodes.length  &&
                col >= 0 && col < mazeNodes[row].length) {
            return mazeNodes[row][col];
        }
        return  null;
    }

    public String getMazeNodeType(int row, int col){
        MazeNode mazeNode = getMazeNode(row, col);
        return mazeNode == null ? null :(mazeNode.isWall()? "Wall" : "Path");
    }

    public int getWallCount() {
        return wallCount;
    }

    public int getEmptySpaceCount() {
        return emptySpaceCount;
    }

    public MazeNode getStartNode() {
        return startNode;
    }

    public MazeNode getExitNode() {
        return exitNode;
    }

    public boolean isExitNode(MazeNode mazeNode){
        return exitNode != null && exitNode.equals(mazeNode);
    }

    public boolean isResolved() {
        return isResolved;
    }

    public void setResolved(boolean resolved) {
        isResolved = resolved;
    }

    public void printMaze(Direction direction, Coordinates currentCoordinates) {
        StringBuffer stringBuffer= new StringBuffer("\n");
        for(int x = 0; x< mazeNodes.length; x++)
        {
            for (int y = 0; y< mazeNodes[x].length; y++){
                if(direction != null && currentCoordinates != null && currentCoordinates.getX() == x && currentCoordinates.getY() == y){
                    switch (direction){
                        case NORTH:
                            stringBuffer.append("^");
                            break;
                        case EAST:
                            stringBuffer.append(">");
                            break;
                        case WEST:
                            stringBuffer.append("<");
                            break;
                        case SOUTH:
                            stringBuffer.append("V");
                            break;
                    }
                }
                else {
                    stringBuffer.append(mazeNodes[x][y].getMazeChar());
                }
            }
            stringBuffer.append("\n");
        }
        log.info(stringBuffer.toString());
    }

    public void printMaze() {
        printMaze(null, null);
    }
}
