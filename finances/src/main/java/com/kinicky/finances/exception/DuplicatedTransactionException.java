package com.kinicky.finances.exception;

public class DuplicatedTransactionException extends Exception {

    /** */
    private static final long serialVersionUID = 1L;

    public DuplicatedTransactionException(String message) {
        super(message);
    }
}
