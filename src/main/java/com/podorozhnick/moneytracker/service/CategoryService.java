package com.podorozhnick.moneytracker.service;

import com.podorozhnick.moneytracker.dao.CategoryDao;
import com.podorozhnick.moneytracker.dao.CategoryRelationsDao;
import com.podorozhnick.moneytracker.model.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class CategoryService {

    @Autowired
    private CategoryDao categoryDao;

    @Autowired
    private CategoryRelationsDao categoryRelationsDao;

    public void save(Category category) {
        categoryDao.save(category);
//        updateParents(category);
//        updateChildren(category);
    }

    public Category add(Category category) {
        category = categoryDao.add(category);
//        updateParents(category);
//        updateChildren(category);
        return category;

    }

    public List<Category> list() {
        return categoryDao.findAll();
    }

    public Category getById(Long id) {
        return categoryDao.getByKey(id);
    }

//    private void updateChildren(Category newChildren) {
//        if (newChildren.getParentCategory() != null) {
//            Category parent = newChildren.getParentCategory();
//            List<Category> children = new ArrayList<>(newChildren.getRelations().getChildren());
//            children.add(newChildren);
//            parent.getRelations().setCategory(parent);
//            parent.getRelations().getChildren().addAll(children);
//            categoryDao.save(parent);
//        }
//    }

//    private void updateParents(Category category) {
//        if (category.getParentCategory() != null) {
//            List<Category> parents = new ArrayList<>(category.getParentCategory().getRelations().getParents());
//            parents.add(category.getParentCategory());
//            category.getRelations().setCategory(category);
//            category.getRelations().setParents(parents);
//            categoryDao.update(category);
//        }
//    }

}
