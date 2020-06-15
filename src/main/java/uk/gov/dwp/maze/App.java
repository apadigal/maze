/*
 * Swash Tech Ltd.
 *
 * App.java
 *
 * © 2018 Swash Tech Ltd. All Rights Reserved
 */
// ---- Package ---------------------------------------------------------------
package uk.gov.dwp.maze;
// ---- Import Statements -----------------------------------------------------

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class App {
    private static final Logger log = LoggerFactory.getLogger(App.class);
    public static void main(String[] args) {

        Maze maze = new Maze("Maze.txt");
        maze.printMaze();
        //Using interactive method to solve the maze...
        Explorer explorer = new Explorer(maze);
        if(explorer.solveMaze()){
            log.info("Maze resolved successfully...");
        }else {
            maze.printMaze();
            log.error("Maze is not solved !!!!!");
        };

        //Using Auto Explorer to solve the maze.
        log.info("Trying to solve maze using auto explorer (This is basically DFS (Depth First Search) approach).");
        maze = new Maze("Maze2.txt");
        ExplorerAuto explorerAuto = new ExplorerAuto(maze);
        explorerAuto.solveMaze();

        if(maze.isResolved()){
            maze.printMaze();
            log.info("Auto explorer resolved maze successfully...");
        }
        else {
            log.error("This maze is not solvable!!!!");
        }
    }
}