package com.podorozhnick.moneytracker.db.dao;

import com.podorozhnick.moneytracker.db.model.Category;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import java.util.List;

@Repository
public class CategoryDao extends AbstractDao<Long, Category> {

    public Category add(Category category) {
        return persist(category);
    }

    public Category findByName(String name) {
        CriteriaBuilder builder = getCriteriaBuilder();
        EntityManager em = getEntityManager();
        return null;
    }

    public List<Category> findAll() {
        CriteriaBuilder builder = getCriteriaBuilder();
        EntityManager em = getEntityManager();
        CriteriaQuery<Category> criteriaQuery = builder.createQuery(Category.class);
        criteriaQuery.select(criteriaQuery.from(Category.class));
        return em.createQuery(criteriaQuery).getResultList();
    }

}
