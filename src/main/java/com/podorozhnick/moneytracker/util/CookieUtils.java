package com.podorozhnick.moneytracker.util;

import com.podorozhnick.moneytracker.server.StartSettings;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.jetty.util.ArrayUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

@Component
@Slf4j
public class CookieUtils {

    @Value("${jwt.cookie.name}")
    private String tokenCookie;

    @Autowired
    private StartSettings settings;

    public Optional<String> getTokenFromCookies(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        String token = null;
        if (cookies != null) {
            for (Cookie cookie: cookies) {
                if (tokenCookie.equals(cookie.getName())) {
                    token = cookie.getValue();
                }
            }
        }
        return Optional.ofNullable(token);
    }

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
