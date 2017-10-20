package com.podorozhnick.moneytracker.db.dao;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.podorozhnick.moneytracker.AbstractTest;
import com.podorozhnick.moneytracker.db.DbUnitConfig;
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
