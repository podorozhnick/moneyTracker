package com.podorozhnick.moneytracker.service;

import com.podorozhnick.moneytracker.AbstractTest;
import com.podorozhnick.moneytracker.db.dao.EntryDao;
import com.podorozhnick.moneytracker.db.model.Entry;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import java.util.Date;

import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.*;

public class EntryServiceTest extends AbstractTest {

    @Mock
    private EntryDao entryDao;

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Test
    public void checkAdd() {
        EntryService entryService = new EntryService(entryDao, null);
        Entry entry = createDumbEntry();
        when(entryDao.add(eq(entry))).thenReturn(entry);
        entryService.add(entry);
        verify(entryDao, times(1)).add(eq(entry));
        verifyNoMoreInteractions(entryDao);

    }

    private Entry createDumbEntry() {
        return new Entry()
                .setDate(new Date())
                .setAmount(500d);
    }

}
