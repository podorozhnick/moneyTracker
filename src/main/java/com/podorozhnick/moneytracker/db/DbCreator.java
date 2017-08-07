package com.podorozhnick.moneytracker.db;

import com.podorozhnick.moneytracker.db.model.Category;
import com.podorozhnick.moneytracker.db.model.Entry;
import com.podorozhnick.moneytracker.db.model.User;
import com.podorozhnick.moneytracker.db.model.enums.CategoryType;
import com.podorozhnick.moneytracker.service.CategoryService;
import com.podorozhnick.moneytracker.service.EntryService;
import com.podorozhnick.moneytracker.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class DbCreator {

    private final CategoryService categoryService;

    private final EntryService entryService;

    private final UserService userService;

    @Autowired
    public DbCreator(CategoryService categoryService, EntryService entryService, UserService userService) {
        this.categoryService = categoryService;
        this.entryService = entryService;
        this.userService = userService;
    }

    @PostConstruct
    public void init() {
        createUsers();
        List<Category> categories = createCategories();
        createEntries(categories);
    }

    private void createUsers() {
        User user = new User();
        user.setLogin("admin").setPassword("admin");
        if (userService.getByLogin("admin") == null) {
            userService.add(user);
        }
    }

    private void createEntries(List<Category> categories) {
        if (CollectionUtils.isEmpty(categories))
            return;
        for (Category category: categories) {
            Entry entry = new Entry();
            entry.setDate(new Date());
            entry.setCategory(category);
            entry.setDescription("Big expenses");
            entryService.add(entry);
        }
    }

    private List<Category> createCategories() {
        List<Category> categoryList = new ArrayList<>();
        Category category = new Category();
        category.setName("TestInput").setType(CategoryType.INCOMES);
        categoryList.add(categoryService.add(category));
        Category category1 = new Category();
        category1.setName("TestOutput").setType(CategoryType.EXPENSES);
        categoryList.add(categoryService.add(category1));
        return categoryList;
    }


}
