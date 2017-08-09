package com.podorozhnick.moneytracker.server;

import lombok.Getter;
import org.springframework.stereotype.Component;

@Component
@Getter
public class AppSettings {

    private long tokenExpiration = 84600;

}
