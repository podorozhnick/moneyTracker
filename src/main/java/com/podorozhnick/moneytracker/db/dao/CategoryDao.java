package com.podorozhnick.moneytracker.db.dao;

import com.podorozhnick.moneytracker.db.model.Category;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.Optional;

@Repository
public class CategoryDao extends AbstractDao<Long, Category> {

    public Category add(Category category) {
        return persist(category);
    }

    public Optional<Category> findByName(String name) {
        CriteriaBuilder builder = getCriteriaBuilder();
        CriteriaQuery<Category> criteriaQuery = builder.createQuery(Category.class);
        Root<Category> root = criteriaQuery.from(Category.class);
        criteriaQuery.where(builder.equal(root.get(Category.NAME_FIELD), name));
        return getSingleResult(criteriaQuery);
    }

}
