package com.springboot.passwordless.util;

public class ServiceConstants {
    public static final String TOKEN_PARAM_KEY = "token";

    public static enum PASSWORDLESS_ERROR_CODE {
        UNKNOWN_CREDENTIAL,
        MISSING_TOKEN
    }
}
