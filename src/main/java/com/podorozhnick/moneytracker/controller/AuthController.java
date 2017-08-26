package com.podorozhnick.moneytracker.controller;

import com.podorozhnick.moneytracker.db.model.User;
import com.podorozhnick.moneytracker.security.JwtTokenUtils;
import com.podorozhnick.moneytracker.security.JwtUser;
import com.podorozhnick.moneytracker.service.UserService;
import com.podorozhnick.moneytracker.util.CookieUtils;
import jdk.nashorn.internal.parser.Token;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

import static com.podorozhnick.moneytracker.controller.ControllerAPI.*;

@Controller
@RequestMapping(AUTH_CONTROLLER)
@Slf4j
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtils jwtTokenUtils;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private UserService userService;

    @Autowired
    private CookieUtils cookieUtils;

    @PostMapping(AUTH_CONTROLLER_LOGIN)
    public ResponseEntity<?> createAuthenticationToken(@RequestBody User user, HttpServletResponse response, HttpServletRequest request) throws AuthenticationException {

        Optional<String> token = cookieUtils.getTokenFromCookies(request);
        if (!token.isPresent() || jwtTokenUtils.validateToken(token.get())) {
            final Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(user.getLogin(), user.getPassword()
                )
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);
            final JwtUser userDetails = (JwtUser) userDetailsService.loadUserByUsername(user.getLogin());
            final String newToken = jwtTokenUtils.generateToken(userDetails);
            cookieUtils.addTokenCookie(response, newToken);
            return new ResponseEntity<Object>(HttpStatus.OK);
        }
        return new ResponseEntity<Object>(HttpStatus.NOT_MODIFIED);
    }

    @GetMapping(AUTH_CONTROLLER_STATUS)
    public ResponseEntity<User> getAuthStatus(HttpServletResponse response, HttpServletRequest request) {
        Optional<String> token = cookieUtils.getTokenFromCookies(request);
        if (!token.isPresent()) {
            return new ResponseEntity<User>(HttpStatus.UNAUTHORIZED);
        }
        if (!jwtTokenUtils.validateToken(token.get())) {
            cookieUtils.addTokenCookie(response, null);
            return new ResponseEntity<User>(HttpStatus.UNAUTHORIZED);
        }
        User user = userService.getByLogin(jwtTokenUtils.getUsernameFromToken(token.get()));
        if (user == null) {
            cookieUtils.addTokenCookie(response, null);
            return new ResponseEntity<User>(HttpStatus.UNAUTHORIZED);
        }
        return new ResponseEntity<User>(user, HttpStatus.OK);
    }

    @PostMapping(AUTH_CONTROLLER_LOGOUT)
    public ResponseEntity logout(HttpServletResponse response) {
        cookieUtils.addTokenCookie(response, null);
        return new ResponseEntity(HttpStatus.OK);
    }

}
