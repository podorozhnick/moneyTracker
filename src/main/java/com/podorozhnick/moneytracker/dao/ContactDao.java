package com.podorozhnick.moneytracker.dao;

import com.podorozhnick.moneytracker.model.Contact;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

@Repository
public class ContactDao extends AbstractDao<Long, Contact> {

    public Contact findById(long id) {
        return getByKey(id);
    }

    public Contact save(Contact contact) {
        return persist(contact);
    }

    public List<Contact> findAll() {
        CriteriaBuilder builder = getCriteriaBuilder();
        EntityManager em = getEntityManager();
        CriteriaQuery<Contact> criteriaQuery = builder.createQuery(Contact.class);
        criteriaQuery.select(criteriaQuery.from(Contact.class));
        return em.createQuery(criteriaQuery).getResultList();
    }

    public List<Contact> orderedList(String name) throws IllegalArgumentException {
        CriteriaBuilder builder = getCriteriaBuilder();
        EntityManager em = getEntityManager();
        CriteriaQuery<Contact> criteriaQuery = builder.createQuery(Contact.class);
        Root<Contact> contactRoot = criteriaQuery.from(Contact.class);
        criteriaQuery.select(contactRoot).orderBy(builder.asc(contactRoot.get(name)));
        return em.createQuery(criteriaQuery).getResultList();
    }

    public boolean delete(long id) {
        Contact contact = findById(id);
        if (contact != null) {
            delete(contact);
            return true;
        }
        return false;
    }

}
