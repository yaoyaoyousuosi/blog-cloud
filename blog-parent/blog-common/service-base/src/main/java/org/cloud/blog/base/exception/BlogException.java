package org.cloud.blog.base.exception;

public class BlogException extends RuntimeException {
    public BlogException() {
        super();
    }

    public BlogException(String message) {
        super(message);
    }
}
