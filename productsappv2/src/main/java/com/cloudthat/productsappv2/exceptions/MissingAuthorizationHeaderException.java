
package com.cloudthat.productsappv2.exceptions;

public class MissingAuthorizationHeaderException extends Throwable {
    public MissingAuthorizationHeaderException(String message) {
        super(message);
    }
}
