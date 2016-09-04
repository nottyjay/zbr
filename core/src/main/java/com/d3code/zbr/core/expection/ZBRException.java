package com.d3code.zbr.core.expection;

/**
 * Created by aaron on 16-9-4.
 */
public class ZBRException extends RuntimeException {

    public ZBRException(String message) {
        super(message);
    }

    public ZBRException(String message, Throwable cause) {
        super(message, cause);
    }

    public ZBRException(Throwable cause) {
        super(cause);
    }

}
