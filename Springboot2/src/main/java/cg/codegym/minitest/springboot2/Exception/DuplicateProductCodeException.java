package cg.codegym.minitest.springboot2.Exception;

public class DuplicateProductCodeException extends RuntimeException {
    public DuplicateProductCodeException(String message) {
        super(message);
    }
}
