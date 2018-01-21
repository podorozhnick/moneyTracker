package com.podorozhnick.moneytracker.controller;

import com.podorozhnick.moneytracker.controller.exception.BadRequestException;
import com.podorozhnick.moneytracker.controller.exception.ErrorMessage;
import com.podorozhnick.moneytracker.controller.exception.NoContentException;
import com.podorozhnick.moneytracker.controller.exception.RestException;
import com.podorozhnick.moneytracker.db.model.Entry;
import com.podorozhnick.moneytracker.pojo.search.EntrySearchFilter;
import com.podorozhnick.moneytracker.pojo.search.EntrySearchResult;
import com.podorozhnick.moneytracker.service.EntryService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static com.podorozhnick.moneytracker.controller.ControllerAPI.*;

@Controller
@RequestMapping(ENTRIES_CONTROLLER)
public class EntryController {

    @Autowired
    private EntryService entryService;

    @GetMapping(GENERAL_REQUEST)
    public ResponseEntity<List<Entry>> getEntryList() throws RestException {
        List<Entry> entryList = entryService.listJoinCategory();
        if (CollectionUtils.isEmpty(entryList)) {
            throw new NoContentException();
        }
        return new ResponseEntity<>(entryList, HttpStatus.OK);
    }

    @PostMapping(ENTRIES_CONTROLLER_FILTER)
    public ResponseEntity<EntrySearchResult> filterEntries(@RequestBody @Valid EntrySearchFilter entrySearchFilter) {
        EntrySearchResult result = entryService.filter(entrySearchFilter);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping(GENERAL_REQUEST)
    public ResponseEntity<Entry> addEntry(@RequestBody @Valid Entry entry) {
        entry = entryService.add(entry);
        return new ResponseEntity<>(entry, HttpStatus.CREATED);
    }

    @PutMapping(ID_REQUEST)
    public ResponseEntity<Entry> editEntry(@PathVariable Long id, @RequestBody Entry entry) throws RestException {
        Entry loadedEntry = entryService.getById(id);
        if (loadedEntry == null) {
            throw new BadRequestException(new ErrorMessage("Bad id"));
        }
        BeanUtils.copyProperties(entry, loadedEntry);
        loadedEntry = entryService.update(loadedEntry);
        return new ResponseEntity<>(loadedEntry, HttpStatus.CREATED);
    }

    @DeleteMapping(ID_REQUEST)
    public ResponseEntity<Entry> deleteEntry(@PathVariable Long id) throws RestException {
        Entry entry = entryService.getById(id);
        if (entry == null) {
            throw new BadRequestException(new ErrorMessage("Bad id"));
        }
        entry = entryService.delete(entry);
        return new ResponseEntity<>(entry, HttpStatus.OK);
    }


}
