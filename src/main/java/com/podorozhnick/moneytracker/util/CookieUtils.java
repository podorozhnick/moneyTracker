package com.podorozhnick.moneytracker.util;

import com.podorozhnick.moneytracker.server.StartSettings;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

@Component
@Slf4j
public class CookieUtils {

    @Value("${jwt.cookie.name}")
    private String tokenCookie;

    @Autowired
    private StartSettings settings;

    public void addCookie(HttpServletResponse response, Cookie cookie) {
        response.addCookie(cookie);
    }

    public void addTokenCookie(HttpServletResponse response, String token) {
        response.addCookie(createTokenCookie(token));
    }

    private Cookie createTokenCookie(String token) {
        Cookie cookie = new Cookie(tokenCookie, token);
        cookie.setPath("/");
        cookie.setDomain(settings.getHost());
        cookie.setMaxAge(settings.getTokenExpiration());
        log.debug("Cookie is {}", cookie);
        return cookie;
    }

}
