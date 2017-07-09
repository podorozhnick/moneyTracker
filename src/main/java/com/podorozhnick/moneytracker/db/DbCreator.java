package com.podorozhnick.moneytracker.db;

import com.podorozhnick.moneytracker.db.dao.CategoryDao;
import com.podorozhnick.moneytracker.db.model.Category;
import com.podorozhnick.moneytracker.db.model.enums.CategoryType;
import com.podorozhnick.moneytracker.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class DbCreator {

    @Autowired
    private CategoryService categoryService;

    @PostConstruct
    public void init() {
        createCategories();
    }

    private void createCategories() {
        Category category = new Category();
        category.setName("TestInput").setType(CategoryType.INCOMES);
        categoryService.add(category);
        Category category1 = new Category();
        category1.setName("TestOutput").setType(CategoryType.EXPENSES);
        categoryService.add(category1);
    }


}
