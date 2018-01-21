package com.podorozhnick.moneytracker.db;

import com.podorozhnick.moneytracker.db.model.Category;
import com.podorozhnick.moneytracker.db.model.Entry;
import com.podorozhnick.moneytracker.db.model.User;
import com.podorozhnick.moneytracker.db.model.enums.CategoryType;
import com.podorozhnick.moneytracker.service.CategoryService;
import com.podorozhnick.moneytracker.service.EntryService;
import com.podorozhnick.moneytracker.service.UserService;
import com.podorozhnick.moneytracker.service.exception.ServiceLayerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.annotation.PostConstruct;
import java.util.*;

@Component
public class DbCreator {

    private static final String ADMIN_LOGIN = "admin";
    private static final String ADMIN_PASSWORD = "admin";
    public static final String ADMIN_EMAIL = "admin@admin.admin";
    private static final Map<String, CategoryType> categoriesMap = new HashMap<>();
    private static final Random RANDOM = new Random();

    static {
        categoriesMap.put("TestInput", CategoryType.INCOMES);
        categoriesMap.put("TestOutput", CategoryType.EXPENSES);
    }

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
        User admin = createAdmin();
        List<Category> categories = createCategories(admin);
        createEntries(categories);
    }

    private User createAdmin() {
        User user = new User();
        user.setLogin(ADMIN_LOGIN).setPassword(ADMIN_PASSWORD).setEmail(ADMIN_EMAIL);
        User loaded = userService.getByLogin(ADMIN_LOGIN);
        if (loaded == null) {
            loaded = userService.add(user);
        }
        return loaded;
    }

    private void createEntries(List<Category> categories) {
        if (CollectionUtils.isEmpty(categories))
            return;
        for (Category category: categories) {
            Entry entry = new Entry().setDate(new Date()).setCategory(category)
                    .setDescription("Test expenses").setAmount(RANDOM.nextDouble() * 1000);
            entryService.add(entry);
        }
    }

    private List<Category> createCategories(User user) {
        List<Category> categoryList = new ArrayList<>();
        for (Map.Entry<String, CategoryType> entry: categoriesMap.entrySet()) {
            Optional<Category> byName = categoryService.getByName(entry.getKey());
            if (byName.isPresent()) {
                categoryList.add(byName.get());
            } else {
                Category category = new Category();
                category.setName(entry.getKey()).setType(entry.getValue())
                        .setUser(user);
                try {
                    categoryList.add(categoryService.add(category));
                } catch (ServiceLayerException e) {
                    e.printStackTrace();
                }
            }
        }
        return categoryList;
    }


}
