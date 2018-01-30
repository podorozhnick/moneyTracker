package com.podorozhnick.moneytracker.service;

import com.podorozhnick.moneytracker.db.dao.CategoryDao;
import com.podorozhnick.moneytracker.db.model.Category;
import com.podorozhnick.moneytracker.db.model.User;
import com.podorozhnick.moneytracker.pojo.search.CategorySearchFilter;
import com.podorozhnick.moneytracker.pojo.search.CategorySearchParams;
import com.podorozhnick.moneytracker.pojo.search.CategorySearchResult;
import com.podorozhnick.moneytracker.security.AuthenticationFacade;
import com.podorozhnick.moneytracker.service.exception.ServiceLayerException;
import com.podorozhnick.moneytracker.service.validator.CategoryServiceValidator;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@Transactional
@AllArgsConstructor
public class CategoryService {

    private final CategoryDao categoryDao;
    private final AuthenticationFacade authenticationFacade;
    private final CategoryServiceValidator validator;

    public Category add(Category category) throws ServiceLayerException {
        validator.validateCreate(category);
        setCurrentUserIfNotExist(category);
        setParentCategoryIfExists(category);
        return categoryDao.add(category);
    }

    public Category update(Category category) throws ServiceLayerException {
        validator.validateEdit(category);
        setCurrentUserIfNotExist(category);
        setParentCategoryIfExists(category);
        return categoryDao.update(category);
    }

    private void setParentCategoryIfExists(Category category) {
        if (Objects.nonNull(category.getParent())) {
            Category parent = categoryDao.getByKey(category.getParent().getId());
            if (Objects.nonNull(parent)){
                category.setParent(parent);
            }
        }
    }

    private void setCurrentUserIfNotExist(Category category) {
        if (category.getOwner() == null) {
            Optional<User> authenticatedUser = authenticationFacade.getAuthenticatedUser();
            authenticatedUser.ifPresent(category::setOwner);
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
