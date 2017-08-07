package com.podorozhnick.moneytracker.security;

import com.podorozhnick.moneytracker.db.model.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Arrays;
import java.util.List;

public final class JwtUserFactory {

    private JwtUserFactory() {
    }

    public static JwtUser create(User user) {
        return new JwtUser(
                user.getLogin(),
                user.getName(),
                user.getSurname(),
                user.getPassword(),
                user.getEmail(),
                mapToGrantedAuthorities(),
                true,
                "ADMIN"
        );
    }

    private static List<GrantedAuthority> mapToGrantedAuthorities() {
        return Arrays.asList(new SimpleGrantedAuthority("ROLE_ADMIN"));
    }

}
