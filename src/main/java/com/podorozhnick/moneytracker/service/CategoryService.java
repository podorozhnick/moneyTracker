package com.podorozhnick.moneytracker.service;

import com.podorozhnick.moneytracker.db.dao.CategoryDao;
import com.podorozhnick.moneytracker.db.model.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class CategoryService {

    @Autowired
    private CategoryDao categoryDao;

    public Category update(Category category) {
        return categoryDao.update(category);
    }

    public Category add(Category category) {
        return categoryDao.add(category);
    }

    public Category delete(Category category) {
        return categoryDao.delete(category);
    }

    public List<Category> list() {
        return categoryDao.findAll();
    }

    public Category getById(Long id) {
        return categoryDao.getByKey(id);
    }

    public boolean isExistsById(Long id) {
        return categoryDao.isExistsById(id);
    }

}
