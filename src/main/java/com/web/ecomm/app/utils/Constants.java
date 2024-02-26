package com.web.ecomm.app.utils;

public interface Constants {

    String REQUEST_START_TIME = "REQUEST_START_TIME";
    String REQUEST_END_TIME = "REQUEST_END_TIME";
    String REQ_ID_KEY = "reqId";

    // Status
    String STATUS_SUCCESS = "SUCCESS";
    String STATUS_ERROR = "ERROR";

    // Success Code
    String SUCCESS_CODE = "200";

    // Errors Codes
    String ERR_DEFAULT = "ERR-DEF-500";
    String ERR_AUTHENTICATION = "ERR-ATH-401";
    String ERR_AUTHORIZATION = "ERR-ATR-403";
    String ERR_INVALID_DATA = "ERR-INV-100";
    String ERR_RESOURCE_NOT_FOUND = "ERR-RNF-400";
    String ERR_IO = "ERR-IO-503";
    String ERR_EXCEPTION = "ERR-EX-505";
    String ERR_BUSINESS = "ERR-BS-5001";
    String ERR_UNEXPECTED = "ERR-BS-5001";

    // Security
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String TOKEN_HEADER = "Authorization";

    // Date Formats
    public static final String DATE_FORMAT_DD_MM_YYYY_HH_MM_SS = "dd-MM-yyyy HH:mm:ss";
    String CATEGORY = "Category";
}
