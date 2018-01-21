package com.podorozhnick.moneytracker.service;

import com.podorozhnick.moneytracker.db.dao.CategoryDao;
import com.podorozhnick.moneytracker.db.model.Category;
import com.podorozhnick.moneytracker.db.model.User;
import com.podorozhnick.moneytracker.pojo.search.CategorySearchFilter;
import com.podorozhnick.moneytracker.pojo.search.CategorySearchParams;
import com.podorozhnick.moneytracker.pojo.search.CategorySearchResult;
import com.podorozhnick.moneytracker.security.AuthenticationFacade;
import com.podorozhnick.moneytracker.service.exception.NoSuchParentCategoryException;
import com.podorozhnick.moneytracker.service.exception.ServiceLayerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@Transactional
public class CategoryService {

    private final CategoryDao categoryDao;

    private final AuthenticationFacade authenticationFacade;

    @Autowired
    public CategoryService(CategoryDao categoryDao, AuthenticationFacade authenticationFacade) {
        this.categoryDao = categoryDao;
        this.authenticationFacade = authenticationFacade;
    }

    public Category update(Category category) throws ServiceLayerException {
        setCurrentUserIfNotExist(category);
        setParentCategoryIfExists(category);
        return categoryDao.update(category);
    }

    public Category add(Category category) throws ServiceLayerException {
        setCurrentUserIfNotExist(category);
        setParentCategoryIfExists(category);
        return categoryDao.add(category);
    }

    private void setParentCategoryIfExists(Category category) throws NoSuchParentCategoryException {
        if (Objects.nonNull(category.getParent())) {
            Category parent = categoryDao.getByKey(category.getParent().getId());
            if (Objects.isNull(parent)){
                throw new NoSuchParentCategoryException();
            }
            category.setParent(parent);
        }
    }

    private void setCurrentUserIfNotExist(Category category) {
        if (category.getUser() == null) {
            Optional<User> authenticatedUser = authenticationFacade.getAuthenticatedUser();
            authenticatedUser.ifPresent(category::setUser);
        }
    }

    public Optional<Category> getByName(String name) {
        return categoryDao.getByName(name);
    }

    public Category delete(Category category) {
        if (Objects.nonNull(category.getParent())){
            removeParentFromChildren(category);
        }
        return categoryDao.delete(category);
    }

    private void removeParentFromChildren(Category category) {
        for (Category child : category.getChildren()) {
            child.setParent(null);
            categoryDao.update(child);
        }
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

    public CategorySearchResult filter(final CategorySearchFilter filter) {
        if (filter.getSearchParams() == null) {
            filter.setSearchParams(new CategorySearchParams());
        }
        if (filter.getSearchParams().getUserId() == null) {
            Optional<User> authenticatedUser = authenticationFacade.getAuthenticatedUser();
            authenticatedUser.ifPresent(user -> filter.getSearchParams().setUserId(user.getId()));
        }
        List<Category> categories = categoryDao.filter(filter);
        long count = categoryDao.count(filter);
        int pages = 1;
        if (filter.getPageFilter().getCount() > 0) {
            pages = (int) (count / filter.getPageFilter().getCount() + 1);
        }
        return new CategorySearchResult(categories, pages, filter.getPageFilter().getPage());
    }

}
