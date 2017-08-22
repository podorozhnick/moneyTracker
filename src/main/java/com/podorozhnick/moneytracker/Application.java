package com.podorozhnick.moneytracker;

import com.podorozhnick.moneytracker.config.AppConfig;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Application {

    public static void main(String[] args) {
        new AnnotationConfigApplicationContext(AppConfig.class);
    }

}
