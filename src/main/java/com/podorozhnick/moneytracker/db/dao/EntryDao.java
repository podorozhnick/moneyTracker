package com.podorozhnick.moneytracker.db.dao;

import com.podorozhnick.moneytracker.db.model.Entry;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Root;
import java.util.List;

@Repository
public class EntryDao extends AbstractDao<Long, Entry> {

    public Entry add(Entry entry) {
        return persist(entry);
    }

    public void save(Entry entry) {
        update(entry);
    }

    public List<Entry> findAll() {
        CriteriaBuilder builder = getCriteriaBuilder();
        EntityManager em = getEntityManager();
        CriteriaQuery<Entry> criteriaQuery = builder.createQuery(Entry.class);
        Root<Entry> root = criteriaQuery.from(Entry.class);
        criteriaQuery.select(root);
        criteriaQuery.select(root).distinct(true);
        root.fetch("category", JoinType.LEFT);
        return em.createQuery(criteriaQuery).getResultList();
    }


}
