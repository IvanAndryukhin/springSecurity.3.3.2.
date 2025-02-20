package net.javacode.spring_311.exception;

public class NotUniqueUserNameException extends RuntimeException {
    public NotUniqueUserNameException(String message) {
        super(message);
    }
}
