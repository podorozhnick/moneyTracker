package com.podorozhnick.moneytracker.controller;

import com.podorozhnick.moneytracker.db.model.User;
import com.podorozhnick.moneytracker.security.JwtTokenUtils;
import com.podorozhnick.moneytracker.security.JwtUser;
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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("api/auth")
@Slf4j
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtils jwtTokenUtils;

    @Autowired
    private UserDetailsService userDetailsService;

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseEntity<?> createAuthenticationToken(@RequestBody User user, HttpServletResponse response, HttpServletRequest request) throws AuthenticationException {

        // Perform the security
        final Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        user.getLogin(),
                        user.getPassword()
                )
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Reload password post-security so we can generate token
        final JwtUser userDetails = (JwtUser) userDetailsService.loadUserByUsername(user.getLogin());
        final String token = jwtTokenUtils.generateToken(userDetails);
        setUserTokenCookie(request, response, token);
        // Return the token
        return new ResponseEntity<Object>(HttpStatus.OK);
    }

    private void setUserTokenCookie(HttpServletRequest request, HttpServletResponse response, String token) {
        Cookie cookie = new Cookie("token", token);
        cookie.setDomain("http://localhost:8915");
        cookie.setPath("/");
        cookie.setMaxAge(5 * 60);
        log.debug("Cookie is %s", cookie);
        response.addCookie(cookie);
    }

}
