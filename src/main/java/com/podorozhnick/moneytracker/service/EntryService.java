package com.podorozhnick.moneytracker.service;

import com.podorozhnick.moneytracker.db.dao.EntryDao;
import com.podorozhnick.moneytracker.db.model.Entry;
import com.podorozhnick.moneytracker.db.model.User;
import com.podorozhnick.moneytracker.pojo.search.EntrySearchFilter;
import com.podorozhnick.moneytracker.pojo.search.EntrySearchParams;
import com.podorozhnick.moneytracker.pojo.search.EntrySearchResult;
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

    public Entry update(Entry entry) {
        return entryDao.update(entry);
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

    public EntrySearchResult filter(final EntrySearchFilter filter) {
        if (filter.getSearchParams() == null) {
            filter.setSearchParams(new EntrySearchParams());
        }
        if (filter.getSearchParams().getUserId() == null) {
            Optional<User> authenticatedUser = authenticationFacade.getAuthenticatedUser();
            authenticatedUser.ifPresent(user -> filter.getSearchParams().setUserId(user.getId()));
        }
        List<Entry> entries = entryDao.filter(filter);
        long count = entryDao.count(filter);
        int pages = 1;
        if (filter.getPageFilter().getCount() > 0) {
            pages = (int) (count / filter.getPageFilter().getCount() + 1);
        }
        return new EntrySearchResult(entries, pages, filter.getPageFilter().getPage());

    }
    
}
