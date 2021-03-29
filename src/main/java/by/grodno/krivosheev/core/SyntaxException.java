package by.grodno.krivosheev.core;

/**
 * Expression's syntax is invalid
 */
public class SyntaxException extends Exception {

    public SyntaxException(String message) {
        super("Syntax exception! " + message);
    }

    public SyntaxException(String message, Throwable throwable) {
        super("Syntax exception! " + message, throwable);
    }

}
