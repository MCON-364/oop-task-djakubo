package edu.touro.las.mcon364.taskmanager;

public class InvalidPriorityException extends RuntimeException {
    public InvalidPriorityException(Priority priority) {
        super(priority + " is not a valid priority");
    }
}

