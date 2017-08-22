package com.podorozhnick.moneytracker.controller;

import com.podorozhnick.moneytracker.server.JettyServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("user")
public class UserController {

    @Autowired
    private JettyServer jettyServer;

    @RequestMapping(value = "code007/stop", method = RequestMethod.GET)
    public void stopServer() {
        jettyServer.stopServer();
        System.exit(0);
    }

}
