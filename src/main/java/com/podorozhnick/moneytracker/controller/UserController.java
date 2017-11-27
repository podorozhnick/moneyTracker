package com.podorozhnick.moneytracker.controller;

import com.podorozhnick.moneytracker.controller.exception.BadRequestException;
import com.podorozhnick.moneytracker.controller.exception.ErrorMessage;
import com.podorozhnick.moneytracker.db.model.User;
import com.podorozhnick.moneytracker.server.JettyServer;
import com.podorozhnick.moneytracker.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static com.podorozhnick.moneytracker.controller.ControllerAPI.GENERAL_REQUEST;
import static com.podorozhnick.moneytracker.controller.ControllerAPI.USERS_CONTROLLER;

@RestController
@RequestMapping(USERS_CONTROLLER)
public class UserController {

    @Autowired
    private JettyServer jettyServer;

    @Autowired
    private UserService userService;

    @PostMapping(GENERAL_REQUEST)
    public ResponseEntity<User> addUser(@RequestBody @Valid User user) throws BadRequestException {
        if (userService.isExistByLogin(user.getLogin())) {
            throw new BadRequestException(new ErrorMessage("User with such login already exists"));
        }
        if (userService.isExistByEmail(user.getEmail())) {
            throw new BadRequestException(new ErrorMessage("User with such email already exists"));
        }
        User addedUser = userService.add(user);
        return new ResponseEntity<>(addedUser, HttpStatus.CREATED);
    }

    @RequestMapping(value = "code007/stop", method = RequestMethod.GET)
    public void stopServer() {
        jettyServer.stopServer();
        System.exit(0);
    }

}
