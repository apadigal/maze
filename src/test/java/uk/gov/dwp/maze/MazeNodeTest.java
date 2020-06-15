package uk.gov.dwp.maze;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import uk.gov.dwp.maze.exception.InvalidMazeException;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Test Maze Node")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
class MazeNodeTest {

    @Mock
    MazeNode mockedMazeNode;

    MazeNode wallMazeNode;
    MazeNode pathMazeNode;
    MazeNode startMazeNode;
    MazeNode endMazeNode;

    @BeforeEach
    void setUp() {
        wallMazeNode = new MazeNode(1, 2, 'X');
        pathMazeNode = new MazeNode(1, 2, ' ');
        startMazeNode = new MazeNode(1, 2, 'S');
        endMazeNode = new MazeNode(1, 2, 'F');
    }

    @Nested
    @DisplayName("Maze test with mocked object")
    class MazeNodeMockTests {
        @Test
        void isWall() {
            Mockito.when(mockedMazeNode.isPathNode()).thenReturn(true);
            assert (mockedMazeNode.isPathNode());
        }

        @Test
        void isPath() {
            Mockito.when(mockedMazeNode.isPath()).thenReturn(true);
            assert (mockedMazeNode.isPath());
        }

        @Test
        void isExit() {
            Mockito.when(mockedMazeNode.isExit()).thenReturn(true);
            assert (mockedMazeNode.isExit());
        }

        @Test
        void isStart() {
            Mockito.when(mockedMazeNode.isStart()).thenReturn(true);
            assert (mockedMazeNode.isStart());
        }

        @Test
        void isVisited() {
            Mockito.when(mockedMazeNode.isVisited()).thenReturn(true);
            assert (mockedMazeNode.isVisited());
        }

        @Test
        void getCoordinates() {
            Mockito.when(mockedMazeNode.getCoordinates()).thenReturn(new Coordinates(1, 2));
            assertNotNull(mockedMazeNode.getCoordinates());
            assertEquals(1, mockedMazeNode.getCoordinates().getX());
            assertEquals(2, mockedMazeNode.getCoordinates().getY());
        }

        @Test
        void getMazeChar() {
            Mockito.when(mockedMazeNode.getMazeChar()).thenReturn('X');
            assertNotNull(mockedMazeNode.getMazeChar());
            assertEquals('X', mockedMazeNode.getMazeChar());
        }

        @Test
        void isPathNode() {
            Mockito.when(mockedMazeNode.isPathNode()).thenReturn(true);
            assert (mockedMazeNode.isPathNode());
        }
    }

    @Nested
    @DisplayName("Maze Node Exceptions tests")
    class MazeNodeExceptionsTests{
        @Test
        @DisplayName("Test to handle invalid maze character")
        void invalidMazeNodeCharacter() {
            InvalidMazeException invalidMazeException = assertThrows(InvalidMazeException.class, () -> {
                new MazeNode(1, 2, 'R');
            });

            assertEquals("Invalid maze character found!!. Valid characters are (X, S, F and space)",
                    invalidMazeException.getMessage());
        }

        @Test
        @DisplayName("Test to handle invalid maze coordinates")
        void invalidMazeNodeCoordinates() {
            InvalidMazeException invalidMazeException = assertThrows(InvalidMazeException.class, () -> {
                new MazeNode(-1, 2, 'R');
            });

            assertEquals("Invalid Coordinates : {-1, 2}",
                    invalidMazeException.getMessage());
        }
    }

    @Nested
    @DisplayName("MazeNode functional tests")
    class MazeNodeFunctionalTests {
        @Test
        @DisplayName("Wall node test")
        void isWall() {
            assert(wallMazeNode.isWall());
            assert(!pathMazeNode.isWall());
            assert(!startMazeNode.isWall());
            assert(!endMazeNode.isWall());
        }

        @Test
        @DisplayName("Path node test")
        void isPath() {
            assert(!wallMazeNode.isPath());
            assert(pathMazeNode.isPath());
            assert(!startMazeNode.isPath());
            assert(!endMazeNode.isPath());
        }

        @Test
        @DisplayName("Exit node test")
        void isExit() {
            assert(!wallMazeNode.isExit());
            assert(!pathMazeNode.isExit());
            assert(!startMazeNode.isExit());
            assert(endMazeNode.isExit());
        }

        @Test
        @DisplayName("Start node test")
        void isStart() {
            assert(!wallMazeNode.isStart());
            assert(!pathMazeNode.isStart());
            assert(startMazeNode.isStart());
            assert(!endMazeNode.isStart());
        }

        @Test
        @DisplayName("Visited node test")
        void isVisited() {
            wallMazeNode.setVisited(true);
            pathMazeNode.setVisited(true);
            startMazeNode.setVisited(true);
            endMazeNode.setVisited(true);
            assert(!wallMazeNode.isVisited());
            assert(pathMazeNode.isVisited());
            assert(startMazeNode.isVisited());
            assert(endMazeNode.isVisited());
        }

        @Test
        @DisplayName("Maze node char")
        void getMazeChar() {
            assertEquals('X', wallMazeNode.getMazeChar());
            assertEquals(' ', pathMazeNode.getMazeChar());
            assertEquals('S', startMazeNode.getMazeChar());
            assertEquals('F', endMazeNode.getMazeChar());
        }

        @Test
        @DisplayName("Final Path Node test")
        void isPathNode() {
            wallMazeNode.setPathNode(true);
            pathMazeNode.setPathNode(true);
            startMazeNode.setPathNode(true);
            endMazeNode.setPathNode(true);
            assert(!wallMazeNode.isPathNode());
            assert(pathMazeNode.isPathNode());
            assert(startMazeNode.isPathNode());
            assert(endMazeNode.isPathNode());
        }
    }
}