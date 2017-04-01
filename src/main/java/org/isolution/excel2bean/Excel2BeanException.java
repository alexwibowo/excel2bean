package org.isolution.excel2bean;

public class Excel2BeanException extends RuntimeException {

    public Excel2BeanException(final String message) {
        super(message);
    }

    public Excel2BeanException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
