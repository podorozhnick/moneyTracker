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
            log.info("TEST: {} - {}(){}STARTED", description.getTestClass().getSimpleName(), description.getMethodName(), "\t");
        }

        @Override
        protected void finished(Description description) {
            log.info("TEST: {} - {}(){}FINISHED", description.getTestClass().getSimpleName(), description.getMethodName(), "\t");
        }
    };

}
