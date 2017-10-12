package com.podorozhnick.moneytracker.db.dao;

import com.podorozhnick.moneytracker.db.model.Category;
import com.podorozhnick.moneytracker.db.model.Entry;
import com.podorozhnick.moneytracker.db.model.User;
import com.podorozhnick.moneytracker.pojo.SortFilter;
import com.podorozhnick.moneytracker.pojo.enums.SortType;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.*;
import java.util.Date;
import java.util.List;

@Repository
public class EntryDao extends AbstractDao<Long, Entry> {

    public Entry add(Entry entry) {
        return persist(entry);
    }

    public List<Entry> filter(Date from, Date to, Long userId, int page, int size, SortFilter sortFilter) {
        CriteriaBuilder builder = getCriteriaBuilder();
        CriteriaQuery<Entry> query = builder.createQuery(Entry.class);
        Root<Entry> root = query.from(Entry.class);
        query.select(root).distinct(true);
        query.where(createFilterPredicate(root, from, to, userId));
        query.orderBy(getOrder(sortFilter, root));
        return getPagedResult(query, page, size, createFetchGraphHint(Entry.ENTRY_CATEGORY_GRAPH));
    }

    private <T> Order getOrder(SortFilter sortFilter, Root<T> root) {
        String sortField = sortFilter.getSortField().getField();
        Path<Object> path = root.get(sortField);
        Order order = null;
        if (sortFilter.getSortType().equals(SortType.DESC)) {
            order = getCriteriaBuilder().desc(path);
        } else {
            order = getCriteriaBuilder().asc(path);
        }
        return order;
    }

    public long count(Date from, Date to, Long userId) {
        CriteriaBuilder builder = getCriteriaBuilder();
        CriteriaQuery<Long> query = builder.createQuery(Long.class);
        Root<Entry> root = query.from(Entry.class);
        query.where(createFilterPredicate(root, from, to, userId));
        return getCountByQuery(query, root);
    }

    public List<Entry> findAllJoinCategory() {
        return findAll(createFetchGraphHint(Entry.ENTRY_CATEGORY_GRAPH));
    }

    private Predicate createFilterPredicate(Root<Entry> root, Date from, Date to, Long userId) {
        CriteriaBuilder builder = getCriteriaBuilder();
        return builder.and(builder.greaterThanOrEqualTo(root.get(Entry.DATE_FIELD), from),
                builder.lessThanOrEqualTo(root.get(Entry.DATE_FIELD), to),
                builder.equal(root.get(Entry.CATEGORY_FIELD).get(Category.USER_FIELD).get(User.ID_FIELD), userId));
    }


}
