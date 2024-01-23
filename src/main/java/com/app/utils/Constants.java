package com.app.utils;

public interface Constants {

    String REQUEST_START_TIME = "REQUEST_START_TIME";
    String REQUEST_END_TIME = "REQUEST_END_TIME";
    String REQ_ID_KEY = "reqId";

    // Errors
    String ERR_DEFAULT = "ERR-DEF-500";
    String ERR_AUTH = "ERR-AUTH-401";
    String ERR_INVALID_DATA = "ERR-INV-100";
    String ERR_RESOURCE_NOT_FOUND = "ERR-RNF-400";

    // Security
    public static final String SECRET = "SECRET_KEY";
    public static final long ACCESS_TOKEN_VALIDITY = 60 * 60 * 1000;
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String TOKEN_HEADER = "Authorization";
    public static final String SIGN_UP_URL = "/";
}
