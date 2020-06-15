/*
 * Swash Tech Ltd.
 *
 * EmptyFileException.java
 *
 * © 2018 Swash Tech Ltd. All Rights Reserved
 */
// ---- Package ---------------------------------------------------------------
package uk.gov.dwp.maze.exception;
// ---- Import Statements -----------------------------------------------------

public class EmptyFileException extends RuntimeException{
    public EmptyFileException(String message, Throwable cause) {
        super(message, cause);
    }

    public EmptyFileException(String message) {
        super(message);
    }
}