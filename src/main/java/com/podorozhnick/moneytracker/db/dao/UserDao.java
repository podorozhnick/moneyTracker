package com.podorozhnick.moneytracker.db.dao;

import com.podorozhnick.moneytracker.db.model.User;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

@Repository
public class UserDao extends AbstractDao<Long, User> {

    public User getByLogin(String login) {
        CriteriaBuilder builder = getCriteriaBuilder();
        CriteriaQuery<User> criteriaQuery = builder.createQuery(User.class);
        Root<User> root = criteriaQuery.from(User.class);
        criteriaQuery.where(builder.equal(root.get(User.LOGIN_FIELD), login));
        return getSingleResult(criteriaQuery).orElse(null);
    }

    public User add(User user) {
        return persist(user);
    }
}
