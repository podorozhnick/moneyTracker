package com.podorozhnick.moneytracker.util;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Locale;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {MessagesTest.MessagesConfig.class})
public class MessagesTest {

    private static final String TEST_MESSAGE_KEY = "test.message";
    private static final String TEST_MESSAGE_VALUE = "Test message";

    @Configuration
    public static class MessagesConfig {
        @Bean
        MessageSource messageSource() {
            Locale.setDefault(Locale.ENGLISH);
            ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
            messageSource.setBasename("test.messages");
            return messageSource;
        }
        @Bean
        public Messages messages() {
            return new Messages(messageSource());
        }
    }

    @Autowired
    private Messages messages;

    @Test
    public void check() {
        assertThat(messages.get(TEST_MESSAGE_KEY)).isEqualTo(TEST_MESSAGE_VALUE);
    }
}