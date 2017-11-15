package com.podorozhnick.moneytracker.db.dao;

import com.podorozhnick.moneytracker.db.model.Entry;
import com.podorozhnick.moneytracker.pojo.search.EntrySearchFilter;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

@Repository
public class EntryDao extends AbstractDao<Long, Entry> {

    public Entry add(Entry entry) {
        return persist(entry);
    }

    public List<Entry> filter(EntrySearchFilter filter) {
        CriteriaBuilder builder = getCriteriaBuilder();
        CriteriaQuery<Entry> query = builder.createQuery(Entry.class);
        Root<Entry> root = query.from(Entry.class);
        query.select(root).distinct(true);
        query.where(DaoHelper.createEntryFilterPredicate(builder, root, filter.getSearchParams()));
        query.orderBy(getOrder(filter.getSortFilter(), root));
        return getPagedResult(query, filter.getPageFilter(), createFetchGraphHint(Entry.ENTRY_CATEGORY_GRAPH));
    }

    public long count(EntrySearchFilter filter) {
        CriteriaBuilder builder = getCriteriaBuilder();
        CriteriaQuery<Long> query = builder.createQuery(Long.class);
        Root<Entry> root = query.from(Entry.class);
        query.where(DaoHelper.createEntryFilterPredicate(builder, root, filter.getSearchParams()));
        return getCountByQuery(query, root);
    }

    public List<Entry> findAllJoinCategory() {
        return findAll(createFetchGraphHint(Entry.ENTRY_CATEGORY_GRAPH));
    }


}
