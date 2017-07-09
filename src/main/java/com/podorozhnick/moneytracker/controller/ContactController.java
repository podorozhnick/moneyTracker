package com.podorozhnick.moneytracker.controller;

import com.podorozhnick.moneytracker.model.Contact;
import com.podorozhnick.moneytracker.service.ContactService;
import com.podorozhnick.moneytracker.util.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("contacts")
public class ContactController {

    @Autowired
    private ContactService contactService;

    @RequestMapping(value = "add", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<String> addContact(@RequestBody String jsonContact) {
        Contact contact = JsonUtils.fromJson(Contact.class, jsonContact);
        if (contact == null) {
            return new ResponseEntity<>("Bad Json", null, HttpStatus.BAD_REQUEST);
        }
        contact.setAdded(new Date());
        contact = contactService.save(contact);
        return new ResponseEntity<>(JsonUtils.toJson(contact), null, HttpStatus.CREATED);
    }

    @RequestMapping(value = "", method = RequestMethod.GET, produces = "text/json;charset=UTF-8")
    @ResponseBody
    public ResponseEntity<String> getContactsList() {
        List<Contact> contactList = contactService.findAll();
        if (contactList == null || contactList.size() == 0) {
            return new ResponseEntity<>(null, null, HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(JsonUtils.toJson(contactList), null, HttpStatus.OK);
        }
    }

    @RequestMapping(value = "/order/{name}", method = RequestMethod.GET, produces = "text/json;charset=UTF-8")
    @ResponseBody
    public ResponseEntity<String> getOrderedContactsList(@PathVariable String name) {
        if (name == null) {
            return new ResponseEntity<>("Bad order attribute", null, HttpStatus.BAD_REQUEST);
        }
        List<Contact> contactList = new ArrayList<>();
        try {
            contactList = contactService.orderedList(name);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>("Bad order attribute", null, HttpStatus.BAD_REQUEST);
        }
        if (contactList == null || contactList.size() == 0) {
            return new ResponseEntity<>(null, null, HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(JsonUtils.toJson(contactList), null, HttpStatus.OK);
        }
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<String> removeContact(@PathVariable Long id) {
        if (id == null) {
            return new ResponseEntity<>("Bad id", null, HttpStatus.BAD_REQUEST);
        }
        boolean removed = contactService.delete(id);
        if (!removed) {
            return new ResponseEntity<>("Bad id", null, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(null, null, HttpStatus.OK);

    }

}
