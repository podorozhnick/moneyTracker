package com.podorozhnick.moneytracker.db.dao;

import com.podorozhnick.moneytracker.db.model.Entry;
import com.podorozhnick.moneytracker.pojo.SortFilter;
import com.podorozhnick.moneytracker.pojo.enums.SortType;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.criteria.*;
import java.util.Date;
import java.util.List;

@Repository
public class EntryDao extends AbstractDao<Long, Entry> {

    public Entry add(Entry entry) {
        return persist(entry);
    }

    public void save(Entry entry) {
        update(entry);
    }

    public List<Entry> filter(Date from, Date to, int page, int size, SortFilter sortFilter) {
        CriteriaBuilder builder = getCriteriaBuilder();
        CriteriaQuery<Entry> query = builder.createQuery(Entry.class);
        Root<Entry> root = query.from(Entry.class);
        query.select(root).distinct(true);
        query.where(createFilterPredicate(root, from, to));
        query.orderBy(getOrder(sortFilter, root));
        root.fetch(Entry.CATEGORY_FIELD, JoinType.LEFT);
        return getPagedResult(query, page, size);
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

    public long count(Date from, Date to) {
        CriteriaBuilder builder = getCriteriaBuilder();
        CriteriaQuery<Long> query = builder.createQuery(Long.class);
        Root<Entry> root = query.from(Entry.class);
        query.where(createFilterPredicate(root, from, to));
        return getCountByQuery(query, root);
    }

    public List<Entry> findAll() {
        CriteriaBuilder builder = getCriteriaBuilder();
        EntityManager em = getEntityManager();
        CriteriaQuery<Entry> criteriaQuery = builder.createQuery(Entry.class);
        Root<Entry> root = criteriaQuery.from(Entry.class);
        criteriaQuery.select(root).distinct(true);
        root.fetch(Entry.CATEGORY_FIELD, JoinType.LEFT);
        return em.createQuery(criteriaQuery).getResultList();
    }

    private Predicate createFilterPredicate(Root<Entry> root, Date from, Date to) {
        CriteriaBuilder builder = getCriteriaBuilder();
        return builder.and(builder.greaterThanOrEqualTo(root.get(Entry.DATE_FIELD), from),
                builder.lessThanOrEqualTo(root.get(Entry.DATE_FIELD), to));
    }


}
