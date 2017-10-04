package com.podorozhnick.moneytracker.security;

import com.podorozhnick.moneytracker.db.model.User;
import com.podorozhnick.moneytracker.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationFacade {

    @Autowired
    private UserService userService;

    public User getAuthenticatedUser() {
        Authentication authentication = getAuthentication();
        return authentication != null ? userService.getByLogin(authentication.getName()) : null;
    }

    private Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

}
