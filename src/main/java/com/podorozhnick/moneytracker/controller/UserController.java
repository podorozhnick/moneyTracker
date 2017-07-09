package com.podorozhnick.moneytracker.controller;

import com.podorozhnick.moneytracker.server.JettyServer;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("user")
public class UserController {

    @RequestMapping(value = "code007/stop", method = RequestMethod.GET)
    public void stopServer() {
        JettyServer.getInstance().stopServer();
        System.exit(0);
    }

}
