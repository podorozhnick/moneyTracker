package com.podorozhnick.moneytracker.controller;

class ControllerAPI {

    private static final String API = "/api";
    static final String GENERAL_REQUEST = "";
    static final String ID_REQUEST = "/{id}";

    static final String CATEGORIES_CONTROLLER = API + "/categories";
    static final String CATEGORIES_CONTROLLER_FILTER = "/filter";

    static final String ENTRIES_CONTROLLER = API + "/entries";
    static final String ENTRIES_CONTROLLER_FILTER = "/filter";

    static final String AUTH_CONTROLLER = API + "/auth";
    static final String AUTH_CONTROLLER_LOGIN = "/login";
    static final String AUTH_CONTROLLER_LOGOUT = "/logout";
    static final String AUTH_CONTROLLER_STATUS = "/status";


}
