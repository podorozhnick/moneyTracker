package com.podorozhnick.moneytracker.db;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.podorozhnick.moneytracker.AbstractTest;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {DbUnitConfig.class})
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class,
        DbUnitTestExecutionListener.class, TransactionalTestExecutionListener.class })
public abstract class DbUnitDaoTest extends AbstractTest {
}
