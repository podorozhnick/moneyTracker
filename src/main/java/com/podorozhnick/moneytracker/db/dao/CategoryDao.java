package com.podorozhnick.moneytracker.db.dao;

import com.podorozhnick.moneytracker.db.model.Category;
import com.podorozhnick.moneytracker.pojo.search.CategorySearchFilter;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.Optional;

@Repository
public class CategoryDao extends AbstractDao<Long, Category> {

    public Category add(Category category) {
        return persist(category);
    }

    public Optional<Category> getByName(String name) {
        CriteriaBuilder builder = getCriteriaBuilder();
        CriteriaQuery<Category> criteriaQuery = builder.createQuery(Category.class);
        Root<Category> root = criteriaQuery.from(Category.class);
        criteriaQuery.where(builder.equal(root.get(Category.NAME_FIELD), name));
        return getSingleResult(criteriaQuery);
    }

    public List<Category> filter(CategorySearchFilter filter) {
        CriteriaBuilder builder = getCriteriaBuilder();
        CriteriaQuery<Category> query = builder.createQuery(Category.class);
        Root<Category> root = query.from(Category.class);
        query.select(root).distinct(true);
        query.where(DaoHelper.createCategoryFilterPredicate(builder, root, filter.getSearchParams()));
        query.orderBy(getOrder(filter.getSortFilter(), root));
        return getPagedResult(query, filter.getPageFilter());
    }

    public long count(CategorySearchFilter filter) {
        CriteriaBuilder builder = getCriteriaBuilder();
        CriteriaQuery<Long> query = builder.createQuery(Long.class);
        Root<Category> root = query.from(Category.class);
        query.where(DaoHelper.createCategoryFilterPredicate(builder, root, filter.getSearchParams()));
        return getCountByQuery(query, root);
    }
}
