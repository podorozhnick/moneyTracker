package com.podorozhnick.moneytracker.dao;

import com.podorozhnick.moneytracker.model.Category;
import com.podorozhnick.moneytracker.model.CategoryRelations;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Root;

@Repository
public class CategoryRelationsDao extends AbstractDao<Long, CategoryRelations> {

    public CategoryRelations getByCategoryId(Long categoryId) {
        CriteriaBuilder builder = getCriteriaBuilder();
        EntityManager em = getEntityManager();
        CriteriaQuery<CategoryRelations> criteriaQuery = builder.createQuery(CategoryRelations.class);
        Root<CategoryRelations> root = criteriaQuery.from(CategoryRelations.class);
        Path<Category> categoryPath = root.get("category");
        Path<Long> categoryIdPath = categoryPath.get("id");
        criteriaQuery.where(builder.equal(categoryIdPath, categoryId));
        return getSingleResultOrNullFromQuery(criteriaQuery);
    }


}
