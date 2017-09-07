package com.podorozhnick.moneytracker.controller;

public class ControllerAPI {

    private static final String API = "/api";
    public static final String GENERAL_REQUEST = "";
    public static final String ID_REQUEST = "/{id}";

    public static final String CATEGORIES_CONTROLLER = API + "/categories";

    public static final String ENTRIES_CONTROLLER = API + "/entries";
    public static final String ENTRIES_CONTROLLER_FILTER = "/filter";

    public static final String AUTH_CONTROLLER = API + "/auth";
    public static final String AUTH_CONTROLLER_LOGIN = "/login";
    public static final String AUTH_CONTROLLER_LOGOUT = "/logout";
    public static final String AUTH_CONTROLLER_STATUS = "/status";


}
