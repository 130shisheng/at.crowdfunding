package com.atcrowdfunding.exception;

import com.sun.org.apache.xml.internal.resolver.helpers.PublicId;

/**
 * @auther:shisheng
 * @creat 2020-05-11-22:32
 */
public class LoginException extends RuntimeException {

    public LoginException() {
    }

    public LoginException(String message) {
        super(message);

    }
}
