package com.podorozhnick.moneytracker.service;

import com.podorozhnick.moneytracker.db.dao.EntryDao;
import com.podorozhnick.moneytracker.db.model.Entry;
import com.podorozhnick.moneytracker.db.model.User;
import com.podorozhnick.moneytracker.pojo.EntrySearchFilter;
import com.podorozhnick.moneytracker.pojo.EntrySearchResult;
import com.podorozhnick.moneytracker.security.AuthenticationFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class EntryService {
    
    private final EntryDao entryDao;

    private final AuthenticationFacade authenticationFacade;

    @Autowired
    public EntryService(EntryDao entryDao, AuthenticationFacade authenticationFacade) {
        this.entryDao = entryDao;
        this.authenticationFacade = authenticationFacade;
    }

    public void update(Entry entry) {
        entryDao.update(entry);
    }

    public Entry delete(Entry entry) {
        return entryDao.delete(entry);
    }

    public Entry add(Entry entry) {
        return entryDao.add(entry);
    }

    public List<Entry> listJoinCategory() {
        return entryDao.findAllJoinCategory();
    }

    public Entry getById(Long id) {
        return entryDao.getByKey(id);
    }

    public EntrySearchResult filter(EntrySearchFilter filter) {
        if (filter.getUserId() == null) {
            Optional<User> authenticatedUser = authenticationFacade.getAuthenticatedUser();
            authenticatedUser.ifPresent(user -> filter.setUserId(user.getId()));
        }
        List<Entry> entries = entryDao.filter(filter.getFrom(), filter.getTo(), filter.getUserId(),
                filter.getPage(), filter.getCount(), filter.getSortFilter());
        long count = entryDao.count(filter.getFrom(), filter.getTo(), filter.getUserId());
        int pages = (int) (count / filter.getCount());
        return new EntrySearchResult(entries, pages, filter.getPage());

    }
    
}
