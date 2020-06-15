package uk.gov.dwp.maze;

import org.junit.jupiter.api.*;
import org.mockito.Mockito;
import org.mockito.Spy;
import uk.gov.dwp.maze.constant.Direction;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ExplorerTest {

    @Spy
    Maze maze;

    Explorer explorer;

    @BeforeAll
    public void init(){
        maze = new Maze("Maze.txt");
        explorer = Mockito.spy(new Explorer(maze));

    }

    @Test
    @Order(1)
    public void explorer_initialised(){
        assertNotNull(explorer);
    }

    @Test
    @Order(2)
    public void solveMaze(){
        Mockito.doReturn(true).when(explorer).solveMaze();
        assert(explorer.solveMaze());
    }


    @Test
    @Order(3)
    public void turnLeft(){
        Direction direction = explorer.getCurrentDirection();
        explorer.turnLeft();
        Direction newDirection = explorer.getCurrentDirection();

        assertEquals(direction.getLeft(), newDirection);
    }

    @Test
    @Order(4)
    public void turnRight(){
        Direction direction = explorer.getCurrentDirection();
        explorer.turnRight();
        Direction newDirection = explorer.getCurrentDirection();

        assertEquals(direction.getRight(), newDirection);
    }

    @Test
    @Order(5)
    public void whereAmI(){
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
    public void getNodeInFrontOfMe(){
        assertNotNull(explorer.getNodeInFrontOfMe());
    }

    @Test
    @Order(7)
    public void moveForward(){
        assertNotNull(explorer.moveForward());
    }

    @Test
    @Order(8)
    public void getAllPossibleMovements(){
        Map map = explorer.getAllPossibleMovements();
        assertNotNull(map);
    }


}