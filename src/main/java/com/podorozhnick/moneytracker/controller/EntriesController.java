package com.podorozhnick.moneytracker.controller;

import com.podorozhnick.moneytracker.db.model.Entry;
import com.podorozhnick.moneytracker.service.EntryService;
import com.podorozhnick.moneytracker.util.JsonUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.podorozhnick.moneytracker.controller.ControllerAPI.ENTRIES_CONTROLLER;
import static com.podorozhnick.moneytracker.controller.ControllerAPI.GENERAL_REQUEST;
import static com.podorozhnick.moneytracker.controller.ControllerAPI.ID_REQUEST;

@Controller
@RequestMapping(ENTRIES_CONTROLLER)
public class EntriesController {

    @Autowired
    private EntryService entryService;

    @GetMapping(GENERAL_REQUEST)
    @ResponseBody
    public ResponseEntity<String> getEntryList() {
        List<Entry> entryList = entryService.list();
        if (CollectionUtils.isEmpty(entryList)) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(JsonUtils.toJson(entryList), HttpStatus.OK);
    }

    @PostMapping(GENERAL_REQUEST)
    @ResponseBody
    public ResponseEntity<String> addEntry(@RequestBody String json) {
        Entry entry = JsonUtils.fromJson(Entry.class, json);
        if (entry == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        entry = entryService.add(entry);
        return new ResponseEntity<String>(JsonUtils.toJson(entry), HttpStatus.CREATED);
    }

    @PutMapping(ID_REQUEST)
    @ResponseBody
    public ResponseEntity<String> editEntry(@PathVariable Long id, @RequestBody String json) {
        Entry loadedEntry = entryService.getById(id);
        if (loadedEntry == null) {
            return new ResponseEntity<>("Bad id", HttpStatus.BAD_REQUEST);
        }
        Entry entry = JsonUtils.fromJson(Entry.class, json);
        if (entry == null) {
            return new ResponseEntity<>("Bad json", HttpStatus.BAD_REQUEST);
        }
        BeanUtils.copyProperties(entry, loadedEntry);
        entryService.save(loadedEntry);
        return new ResponseEntity<>(JsonUtils.toJson(loadedEntry), null, HttpStatus.CREATED);

    }


}
