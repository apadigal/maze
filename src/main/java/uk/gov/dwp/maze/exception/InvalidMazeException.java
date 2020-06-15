/*
 * Swash Tech Ltd.
 *
 * InvalidMazeException.java
 *
 * © 2018 Swash Tech Ltd. All Rights Reserved
 */
// ---- Package ---------------------------------------------------------------
package uk.gov.dwp.maze.exception;
// ---- Import Statements -----------------------------------------------------

public class InvalidMazeException extends RuntimeException{
    public InvalidMazeException() {
        super();
    }

    public InvalidMazeException(String message) {
        super(message);
    }

    public InvalidMazeException(String message, Throwable cause) {
        super(message, cause);
    }
}