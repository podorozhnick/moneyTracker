package com.podorozhnick.moneytracker.service;

import com.podorozhnick.moneytracker.db.dao.EntryDao;
import com.podorozhnick.moneytracker.db.model.Entry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class EntryService {
    
    @Autowired
    private EntryDao entryDao;

    public void save(Entry entry) {
        entryDao.save(entry);
    }

    public Entry add(Entry entry) {
        entry = entryDao.add(entry);
        return entry;

    }

    public List<Entry> list() {
        return entryDao.findAll();
    }

    public Entry getById(Long id) {
        return entryDao.getByKey(id);
    }
    
}
