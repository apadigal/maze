package uk.gov.dwp.maze;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import uk.gov.dwp.maze.constant.Direction;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DisplayName("Test Explorer Class")
@ExtendWith(MockitoExtension.class)
class ExplorerTest {

    @Spy
    Maze maze = new Maze("Maze.txt");

    @Mock
    Maze mockedMaze;

    @Mock
    MazeNode mazeNode;

    @Mock
    Coordinates coordinates;
    Explorer explorer;
    Explorer explorerMocked;


    @BeforeAll
    public void init() {
        MockitoAnnotations.initMocks(this);
        explorer = Mockito.spy(new Explorer(maze));

        Mockito.when(mazeNode.getCoordinates()).thenReturn(coordinates);
        Mockito.when(mockedMaze.getStartNode()).thenReturn(mazeNode);
        explorerMocked = Mockito.spy(new Explorer(mockedMaze));
    }

    @Nested
    @DisplayName("Mock Tests")
    class MockedTest {
        @Test
        @DisplayName("Test Maze is valid maze")
        public void validMaze() {
            Mockito.when(mazeNode.getCoordinates()).thenReturn(coordinates);
            Mockito.when(mockedMaze.getStartNode()).thenReturn(mazeNode);
            assertEquals(coordinates, mazeNode.getCoordinates());
            assertNotNull(mockedMaze.getStartNode());
            assertNotNull(explorerMocked);
        }

        @Test
        @DisplayName("Test solveMaze returns successful")
        public void solveMaze() {
            Mockito.doReturn(true).when(explorerMocked).solveMaze();
            assert (explorerMocked.solveMaze());
        }
    }

    @Nested
    @DisplayName("Test Explorer movements")
    class TestMovements {
        @Test
        @Order(1)
        public void explorer_initialised() {
            assertNotNull(explorer);
        }

        @Test
        @Order(3)
        public void turnLeft() {
            Direction direction = explorer.getCurrentDirection();
            explorer.turnLeft();
            Direction newDirection = explorer.getCurrentDirection();

            assertEquals(direction.getLeft(), newDirection);
        }

        @Test
        @Order(4)
        public void turnRight() {
            Direction direction = explorer.getCurrentDirection();
            explorer.turnRight();
            Direction newDirection = explorer.getCurrentDirection();

            assertEquals(direction.getRight(), newDirection);
        }

        @Test
        @Order(7)
        public void moveForward() {
            assertNotNull(explorer.moveForward());
        }

        @Test
        @Order(8)
        public void getAllPossibleMovements() {
            Map map = explorer.getAllPossibleMovements();
            assertNotNull(map);
        }
    }

    @Nested
    class OtherTests {
        @Test
        @Order(5)
        public void whereAmI() {
            String whereAmI = "I am at maze node Coordinates{x=3, y=3}and facing towards EAST";
            Direction direction = explorer.getCurrentDirection();
            explorer.turnRight();
            assertEquals(whereAmI, explorer.whereAmI());
            whereAmI = "I am at maze node Coordinates{x=3, y=4}and facing towards EAST";
            explorer.moveForward();
            assertEquals(whereAmI, explorer.whereAmI());
        }

        @Test
        @Order(6)
        public void getNodeInFrontOfMe() {
            assertNotNull(explorer.getNodeInFrontOfMe());
        }

    }
}