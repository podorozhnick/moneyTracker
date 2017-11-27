package com.podorozhnick.moneytracker.service;

import com.podorozhnick.moneytracker.db.dao.UserDao;
import com.podorozhnick.moneytracker.db.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserService {

    @Autowired
    private UserDao userDao;

    public User getByLogin(String login) {
        return userDao.getByLogin(login);
    }

    public User getById(Long id) {
        return userDao.getByKey(id);
    }

    public User add(User user) {
        user = userDao.add(user);
        return user;
    }

    public boolean isExistByLogin(String login) {
        return userDao.isExistByLogin(login);
    }

    public boolean isExistByEmail(String email) {
        return userDao.isExistByEmail(email);
    }
}