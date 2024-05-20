package com.ssafy.enjoytrip.global.exception;

public class NotFoundArticleException extends Exception {
    public NotFoundArticleException() {
        super();
    }

    public NotFoundArticleException(final String message) {
        super(message);
    }
}
