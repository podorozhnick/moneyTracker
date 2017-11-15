package com.podorozhnick.moneytracker.db.dao;

import com.podorozhnick.moneytracker.db.model.DbEntity;
import com.podorozhnick.moneytracker.pojo.search.PageFilter;
import com.podorozhnick.moneytracker.pojo.search.SortFilter;
import com.podorozhnick.moneytracker.pojo.search.enums.SortType;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.annotations.QueryHints;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.*;

public abstract class AbstractDao<PK extends Serializable, T extends DbEntity> {

    private final Class<T> persistentClass;

    @SuppressWarnings("unchecked")
    AbstractDao(){
        this.persistentClass =(Class<T>) ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[1];
    }

    @Autowired
    private SessionFactory sessionFactory;

    private Session getSession(){
        return sessionFactory.getCurrentSession();
    }

    protected Class<T> getPersistentClass() {
        return persistentClass;
    }

    @SuppressWarnings("unchecked")
    public T getByKey(PK key) {
        return (T) getSession().get(persistentClass, key);
    }

    @SuppressWarnings("unchecked")
    T persist(T entity) {
        return (T)getSession().merge(entity);
    }

    public T update(T entity) {
        getSession().update(entity);
        return entity;
    }

    public T delete(T entity) {
        getSession().delete(entity);
        return entity;
    }

    CriteriaBuilder getCriteriaBuilder(){
        return getSession().getEntityManagerFactory().getCriteriaBuilder();
    }

    EntityManager getEntityManager() {
        return getSession().getEntityManagerFactory().createEntityManager();
    }

    public List<T> findAll() {
        return findAll(Collections.emptyMap());
    }

    List<T> findAll(Map<String, Object> hints) {
        CriteriaBuilder builder = getCriteriaBuilder();
        CriteriaQuery<T> query = builder.createQuery(getPersistentClass());
        query.from(getPersistentClass());
        return createQuery(query, hints).getResultList();
    }

    <CT> Optional<CT> getSingleResult(CriteriaQuery<CT> criteriaQuery) {
        try {
            return Optional.of(createQuery(criteriaQuery).getSingleResult());
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }
    List<T> getPagedResult(CriteriaQuery<T> query, PageFilter pageFilter, Map<String, Object> hints) {
        TypedQuery<T> typedQuery = createQuery(query, hints);
        DaoHelper.pageQuery(typedQuery, pageFilter);
        return typedQuery.getResultList();
    }

    List<T> getPagedResult(CriteriaQuery<T> query, PageFilter pageFilter) {
        return getPagedResult(query, pageFilter, Collections.emptyMap());
    }

    long getCountByQuery(CriteriaQuery<Long> query, Root<T> root) {
        CriteriaBuilder builder = getCriteriaBuilder();
        query.select(builder.count(root.get(T.ID_FIELD)));
        return getSingleResult(query).orElse(0L);
    }

    public boolean isExistsById(Long id) {
        CriteriaBuilder builder = getCriteriaBuilder();
        CriteriaQuery<Long> query = builder.createQuery(Long.class);
        Root<T> root = query.from(getPersistentClass());
        return getCountByQuery(query, root) > 0;
    }

    <CT> TypedQuery<CT> createQuery(CriteriaQuery<CT> query) {
        return createQuery(query, Collections.emptyMap());
    }

    <CT> TypedQuery<CT> createQuery(CriteriaQuery<CT> query, Map<String, Object> hints) {
        TypedQuery<CT> typedQuery = getEntityManager().createQuery(query);
        for (Map.Entry<String, Object> entry: hints.entrySet()) {
            typedQuery.setHint(entry.getKey(), entry.getValue());
        }
        return typedQuery;
    }

    Map<String, Object> createFetchGraphHint(String fetchGraphName) {
        Map<String, Object> hints = new HashMap<>();
        hints.put(QueryHints.LOADGRAPH, getSession().getEntityGraph(fetchGraphName));
        return hints;
    }

    Order getOrder(SortFilter sortFilter, Root<T> root) {
        String sortField = sortFilter.getSortField().getFieldName();
        Path<Object> path = root.get(sortField);
        Order order = null;
        if (sortFilter.getSortType().equals(SortType.DESC)) {
            order = getCriteriaBuilder().desc(path);
        } else {
            order = getCriteriaBuilder().asc(path);
        }
        return order;
    }

}
