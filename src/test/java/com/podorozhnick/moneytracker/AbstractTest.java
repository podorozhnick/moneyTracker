package com.podorozhnick.moneytracker;

import lombok.extern.slf4j.Slf4j;
import org.junit.Rule;
import org.junit.rules.TestRule;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;

@Slf4j
public abstract class AbstractTest {

    @Rule
    public TestRule watcher = new TestWatcher() {
        protected void starting(Description description) {
            log.info("TEST: {} - {}()", description.getTestClass().getSimpleName(), description.getMethodName());
        }
    };

}
