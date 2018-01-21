package com.podorozhnick.moneytracker.db.dao;

import com.podorozhnick.moneytracker.db.model.Category;
import com.podorozhnick.moneytracker.db.model.DbEntity;
import com.podorozhnick.moneytracker.db.model.Entry;
import com.podorozhnick.moneytracker.db.model.User;
import com.podorozhnick.moneytracker.pojo.search.CategorySearchParams;
import com.podorozhnick.moneytracker.pojo.search.EntrySearchParams;
import com.podorozhnick.moneytracker.pojo.search.PageFilter;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

public class DaoHelper {

    static <T extends Entry> Predicate createEntryFilterPredicate(CriteriaBuilder builder, Root<T> root,
                                                                  EntrySearchParams searchParams) {
        List<Predicate> predicates = new ArrayList<>();
        if (searchParams.getUserId() != null) {
            predicates.add(builder.equal(root.get(Entry.CATEGORY_FIELD).get(Category.USER_FIELD).get(User.ID_FIELD), searchParams.getUserId()));
        }
        if (searchParams.getFrom() != null) {
            predicates.add(builder.greaterThanOrEqualTo(root.get(Entry.DATE_FIELD), searchParams.getFrom()));
        }
        if (searchParams.getTo() != null) {
            predicates.add(builder.lessThanOrEqualTo(root.get(Entry.DATE_FIELD), searchParams.getTo()));
        }
        return builder.and(predicates.toArray(new Predicate[predicates.size()]));
    }

    static <T extends Category> Predicate createCategoryFilterPredicate(CriteriaBuilder builder, Root<T> root,
                                                                        CategorySearchParams searchParams) {
        List<Predicate> predicates = new ArrayList<>();
        if (searchParams.getUserId() != null) {
            predicates.add(builder.equal(root.get(Category.USER_FIELD).get(User.ID_FIELD), searchParams.getUserId()));
        }
        if (searchParams.getType() != null) {
            predicates.add(builder.equal(root.get(Category.TYPE_FIELD), searchParams.getType()));
        }
        if (searchParams.getRelation() != null) {
            predicates.add(builder.equal(root.get(Category.RELATION_FIELD), searchParams.getRelation()));
        }
        return builder.and(predicates.toArray(new Predicate[predicates.size()]));
    }

    static <T extends DbEntity> void pageQuery(TypedQuery<T> query, PageFilter pageFilter) {
        assert pageFilter != null;
        assert pageFilter.getCount() != null;
        assert pageFilter.getCount() >= -1;
        if (pageFilter.getCount() == -1) {
            return;
        }
        assert pageFilter.getPage() > 0;
        query.setFirstResult((pageFilter.getPage() - 1) * pageFilter.getCount());
        query.setMaxResults(pageFilter.getCount());
    }

}
