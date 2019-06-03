package org.monkey.ebill.exception;

public class EBillException extends Exception {
    public EBillException() {
        super();
    }

    public EBillException(String message) {
        super(message);
    }

    public EBillException(String message, Throwable cause) {
        super(message, cause);
    }

    public EBillException(Throwable cause) {
        super(cause);
    }

    protected EBillException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
