package com.podorozhnick.moneytracker.db.dao;

import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.podorozhnick.moneytracker.db.model.Category;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.junit.Assert.*;

public class CategoryDaoTest extends DbUnitDaoTest {

    private static final String TEST_EXPENSES = "TestExpenses";

    @Autowired
    private CategoryDao categoryDao;

    @Test
    @Transactional
    @DatabaseSetup("category/CategoryData.xml")
    public void getByNameTest() {
        Optional<Category> byName = categoryDao.getByName(TEST_EXPENSES);
        assertNotNull(byName);
        assertTrue(byName.isPresent());
        assertEquals(TEST_EXPENSES, byName.get().getName());
    }

}
