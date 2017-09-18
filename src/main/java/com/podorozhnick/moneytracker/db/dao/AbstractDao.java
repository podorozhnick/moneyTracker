package com.podorozhnick.moneytracker.db.dao;

import com.podorozhnick.moneytracker.db.model.DbEntity;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;
import java.util.Optional;

public abstract class AbstractDao<PK extends Serializable, T extends DbEntity> {

    private final Class<T> persistentClass;

    @SuppressWarnings("unchecked")
    public AbstractDao(){
        this.persistentClass =(Class<T>) ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[1];
    }

    @Autowired
    private SessionFactory sessionFactory;

    private Session getSession(){
        return sessionFactory.getCurrentSession();
    }

    public Class<T> getPersistentClass() {
        return persistentClass;
    }

    @SuppressWarnings("unchecked")
    public T getByKey(PK key) {
        return (T) getSession().get(persistentClass, key);
    }

    T persist(T entity) {
        return (T)getSession().merge(entity);
    }

    void update(T entity) {
        getSession().update(entity);
    }

    public void delete(T entity) {
        getSession().delete(entity);
    }

    CriteriaBuilder getCriteriaBuilder(){
        return getSession().getEntityManagerFactory().getCriteriaBuilder();
    }

    EntityManager getEntityManager() {
        return getSession().getEntityManagerFactory().createEntityManager();
    }

    Optional<T> getSingleResult(CriteriaQuery<T> criteriaQuery) {
        return Optional.ofNullable(getEntityManager().createQuery(criteriaQuery).getSingleResult());
    }

    <CT> Optional<CT> getCustomSingleResult(CriteriaQuery<CT> criteriaQuery) {
        return Optional.ofNullable(getEntityManager().createQuery(criteriaQuery).getSingleResult());
    }

    List<T> getPagedResult(CriteriaQuery<T> query, int page, int size) {
        TypedQuery<T> typedQuery = getEntityManager().createQuery(query);
        typedQuery.setMaxResults(size).setFirstResult(page * size);
        return typedQuery.getResultList();
    }

    long getCountByQuery(CriteriaQuery<Long> query, Root<T> root) {
        CriteriaBuilder builder = getCriteriaBuilder();
        query.select(builder.count(root.get(T.ID_FIELD)));
        return getCustomSingleResult(query).orElse(0L);
    }

}
