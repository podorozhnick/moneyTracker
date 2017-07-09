package com.podorozhnick.moneytracker;

import com.podorozhnick.moneytracker.config.AppConfig;
import com.podorozhnick.moneytracker.model.Category;
import com.podorozhnick.moneytracker.model.CategoryRelations;
import com.podorozhnick.moneytracker.model.enums.CategoryType;
import com.podorozhnick.moneytracker.service.CategoryRelationsService;
import com.podorozhnick.moneytracker.service.CategoryService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;

import java.util.List;

public class Test {

    public static void main(String[] args) {
        AbstractApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);

//        CategoryService service = (CategoryService) context.getBean("categoryService");
//
//        Category category = new Category();
//        category.setName("Test").setType(CategoryType.EXPENSES).setRelations(new CategoryRelations());
//
//        Category parent = new Category();
//        parent.setName("ParentCat").setType(CategoryType.EXPENSES).setRelations(new CategoryRelations());
//        parent = service.add(parent);
//
//        category.setParentCategory(parent);
//        service.add(category);
//
//        List<Category> categories = service.list();

        CategoryRelationsService service = (CategoryRelationsService) context.getBean("categoryRelationsService");
        CategoryRelations categoryRelations = service.getByCategoryId(481l);

        context.close();
    }

}
