package com.zx.crm.exception;

public class UpdateUserPwdException extends Exception {
    public UpdateUserPwdException() {
        super();
    }

    public UpdateUserPwdException(String message) {
        super(message);
    }

    public UpdateUserPwdException(String message, Throwable cause) {
        super(message, cause);
    }

    public UpdateUserPwdException(Throwable cause) {
        super(cause);
    }

    protected UpdateUserPwdException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
