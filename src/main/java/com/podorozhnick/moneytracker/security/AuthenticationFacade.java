package com.podorozhnick.moneytracker.security;

import com.podorozhnick.moneytracker.db.model.User;
import com.podorozhnick.moneytracker.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class AuthenticationFacade {

    private final UserService userService;

    @Autowired
    public AuthenticationFacade(UserService userService) {
        this.userService = userService;
    }

    public Optional<User> getAuthenticatedUser() {
        Authentication authentication = getAuthentication();
        if (authentication == null) {
            return Optional.empty();
        }
        return Optional.ofNullable(userService.getByLogin(authentication.getName()));
    }

    private Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

}
