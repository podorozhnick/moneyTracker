package com.podorozhnick.moneytracker.service;

import com.podorozhnick.moneytracker.db.dao.CategoryDao;
import com.podorozhnick.moneytracker.db.model.Category;
import com.podorozhnick.moneytracker.security.AuthenticationFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CategoryService {

    @Autowired
    private CategoryDao categoryDao;

    @Autowired
    private AuthenticationFacade authenticationFacade;

    public Category update(Category category) {
        return categoryDao.update(category);
    }

    public Category add(Category category) {
        setCurrentUserIfNotExist(category);
        return categoryDao.add(category);
    }

    private void setCurrentUserIfNotExist(Category category) {
        if (category.getUser() == null) {
            category.setUser(authenticationFacade.getAuthenticatedUser());
        }
    }

    public Optional<Category> getByName(String name) {
        return categoryDao.getByName(name);
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
