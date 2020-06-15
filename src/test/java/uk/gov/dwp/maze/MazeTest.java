package uk.gov.dwp.maze;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import uk.gov.dwp.maze.exception.InvalidMazeException;

import java.util.Arrays;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;


@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class MazeTest {

    Maze maze;

    @BeforeAll
    public void init() {
        maze = new Maze("Maze.txt");
    }

    @Test
    @Order(1)
    public void loadMaze(){
//        maze.loadMaze();
        assertNotNull(maze);
    }

    @Test
    @Order(2)
    public void loadMaze_ExceptionUponInvalidMazeCharacter() {
        InvalidMazeException invalidMazeException = assertThrows(InvalidMazeException.class, () -> {
            new Maze("Maze4InvalidCharException.txt");
        });

        assertEquals("Invalid maze character found!!. Valid characters are (X, S, F and space)",
                invalidMazeException.getMessage());

    }

    @Test
    @Order(3)
    public void loadMaze_ExceptionUponInvalidNoOfRows() {
        InvalidMazeException invalidMazeException = assertThrows(InvalidMazeException.class, () -> {
            new Maze("Maze4InvalidRowsException.txt");
        });

        assertEquals("Given Maze Maze4InvalidRowsException.txt is not having equal columns",
                invalidMazeException.getMessage());

    }

    @Test
    @Order(4)
    public void loadMaze_ExceptionUponNoValidStartPosition() {
        InvalidMazeException invalidMazeException = assertThrows(InvalidMazeException.class, () -> {
            new Maze("Maze4InvalidStartPositionException.txt");
        });

        assertEquals("Starting point not available in a given maze...",
                invalidMazeException.getMessage());

    }

    @Test
    @Order(5)
    public void loadMaze_ExceptionUponMultipleStartPositions() {
        InvalidMazeException invalidMazeException = assertThrows(InvalidMazeException.class, () -> {
            new Maze("Maze4MultipleStartPositionException.txt");
        });

        assertEquals("Only one start position possible in a given maze...",
                invalidMazeException.getMessage());

    }

    @Test
    @Order(6)
    public void loadMaze_ExceptionUponNoValidExit() {
        InvalidMazeException invalidMazeException = assertThrows(InvalidMazeException.class, () -> {
            new Maze("Maze4InvalidExitException.txt");
        });

        assertEquals("Exit point not available in a given maze...",
                invalidMazeException.getMessage());

    }

    @Test
    @Order(7)
    public void loadMaze_ExceptionUponMultipleExitPoints() {
        InvalidMazeException invalidMazeException = assertThrows(InvalidMazeException.class, () -> {
            new Maze("Maze4MultipleExitsException.txt");
        });

        assertEquals("Only one exit point possible in a given maze...",
                invalidMazeException.getMessage());

    }


    @ParameterizedTest
    @MethodSource("getXandYCoordinates")
    @Order(8)
    public void getMazeNode_AreAlwaysNonNull(int x, int y){
        assertNotNull(maze.getMazeNode(x, y));
    }

    @ParameterizedTest
    @MethodSource("getXandYCoordinatesForWalls")
    @Order(9)
    public void getMazeNode_AreAlwaysWall(int x, int y){
        assert(maze.getMazeNode(x, y).isWall());
    }

    @ParameterizedTest
    @MethodSource("getXandYCoordinatesForWalls")
    @Order(10)
    public void getMazeNodeType_AreAlwaysWall(int x, int y){
        assert("Wall".equals(maze.getMazeNodeType(x,y)));
    }

    @ParameterizedTest
    @MethodSource("getXandYCoordinatesForPaths")
    @Order(11)
    public void getMazeNode_AreAlwaysPath(int x, int y){
        assert(maze.getMazeNode(x, y).isPath());
    }

    @ParameterizedTest
    @MethodSource("getXandYCoordinatesForPaths")
    @Order(12)
    public void getMazeNodeType_AreAlwaysPath(int x, int y){
        assert("Path".equals(maze.getMazeNodeType(x, y)));
    }

    @Test
    @Order(13)
    public void getEmptySpaceCount_ShouldReturnNonZeroUponSuccessfulMazeLoad(){
        assert(maze.getEmptySpaceCount() > 0);
    }

    @Test
    @Order(14)
    public void getWallCount_ShouldReturnNonZeroUponSuccessfulMazeLoad(){
        assert(maze.getWallCount() > 0);
    }

    @ParameterizedTest
    @MethodSource("getXandYCoordinatesForPathsOrWalls")
    @Order(15)
    public void getMazeNode_AreAlwaysPathOrWall(int x, int y){
        MazeNode mazeNode = maze.getMazeNode(x, y);
        assert(mazeNode.isPath() || mazeNode.isWall());
    }

    @ParameterizedTest
    @MethodSource("getXandYCoordinatesForPathsOrWalls")
    @Order(16)
    public void getMazeNodeType_AreAlwaysPathOrWall(int x, int y){
        String mazeNodeType = maze.getMazeNodeType(x, y);
        assert(Arrays.asList("Path", "Wall").contains(mazeNodeType));
    }

    // static argument generators
    static Stream<Arguments> getXandYCoordinates(){
        return Stream.of(Arguments.of(1, 3),
                Arguments.of(2, 3),
                Arguments.of(3,0));
    }

    static Stream<Arguments> getXandYCoordinatesForWalls(){
        return Stream.of(Arguments.of( 0, 0),
                Arguments.of(1, 0),
                Arguments.of(3,0));
    }

    static Stream<Arguments> getXandYCoordinatesForPaths(){
        return Stream.of(Arguments.of( 1, 1),
                Arguments.of(1, 2),
                Arguments.of(1,3));
    }

    static Stream<Arguments> getXandYCoordinatesForPathsOrWalls(){
//        Maze maze = getValidMaze();
//        return Stream.of( Arguments.of(maze, 1, 1),
//                Arguments.of(maze, 3, 2),
//                Arguments.of(maze, 12,3),
//                Arguments.of(maze, 1,3),
//                Arguments.of(maze, 7,4));
        return Stream.of( Arguments.of(1, 1),
                Arguments.of( 3, 2),
                Arguments.of(12,3),
                Arguments.of(1,3),
                Arguments.of(7,4));
    }

    static Maze getValidMaze(){
        Maze maze = new Maze("Maze234.txt");
        return  maze;
    }
}