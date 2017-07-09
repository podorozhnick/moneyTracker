package com.podorozhnick.moneytracker.service;

import com.podorozhnick.moneytracker.dao.ContactDao;
import com.podorozhnick.moneytracker.model.Contact;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ContactService {

    @Autowired
    private ContactDao contactDao;

    public Contact findById(long id) {
        return contactDao.findById(id);
    }

    public Contact save(Contact contact) {
        return contactDao.save(contact);
    }

    public boolean delete(long id) {
        return contactDao.delete(id);
    }

    public List<Contact> findAll() {
        return contactDao.findAll();
    }

    public List<Contact> orderedList(String name) throws IllegalArgumentException {
        return contactDao.orderedList(name);
    }

}