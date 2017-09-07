package com.podorozhnick.moneytracker.db.dao;

import com.podorozhnick.moneytracker.db.model.Entry;
import com.podorozhnick.moneytracker.pojo.enums.SortType;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Root;
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

    public List<Entry> filter(Date from, Date to, int page, int count, String sortField, SortType sortType) {
        CriteriaBuilder builder = getCriteriaBuilder();
        EntityManager em = getEntityManager();
        CriteriaQuery<Entry> query = builder.createQuery(Entry.class);
        Root<Entry> root = query.from(Entry.class);
        query.select(root).distinct(true);
        query.where(builder.and(builder.greaterThanOrEqualTo(root.get(Entry.DATE_FIELD), from),
                builder.lessThanOrEqualTo(root.get(Entry.DATE_FIELD), to)));
        if (SortType.DESC.equals(sortType)) {
            query.orderBy(builder.desc(root.get(sortField)));
        } else {
            query.orderBy(builder.asc(root.get(sortField)));
        }
        root.fetch(Entry.CATEGORY_FIELD, JoinType.LEFT);
        TypedQuery<Entry> typedQuery = em.createQuery(query);
        typedQuery.setMaxResults(count).setFirstResult(page * count);
        return typedQuery.getResultList();
    }

    public long count(Date from, Date to) {
        CriteriaBuilder builder = getCriteriaBuilder();
        EntityManager em = getEntityManager();
        CriteriaQuery<Long> query = builder.createQuery(Long.class);
        Root<Entry> root = query.from(Entry.class);
        query.select(builder.count(root.get(Entry.ID_FIELD)));
        query.where(builder.and(builder.greaterThanOrEqualTo(root.get(Entry.DATE_FIELD), from),
                builder.lessThanOrEqualTo(root.get(Entry.DATE_FIELD), to)));
        long count = 0;
        try {
            count = em.createQuery(query).getSingleResult();
        } catch (NoResultException e) {}
        return count;
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


}
