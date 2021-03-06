package com.centralway.contactbook.controller;

import com.centralway.contactbook.model.Contact;
import com.centralway.contactbook.model.Entry;
import com.centralway.contactbook.security.model.UserContext;
import com.centralway.contactbook.service.ContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/contacts")
public class ContactController {

    @Autowired ContactService contactService;

    @RequestMapping(method = RequestMethod.GET)
    public List<Contact> getAllContacts() {
        UserContext userContext = (UserContext) SecurityContextHolder
            .getContext()
            .getAuthentication()
            .getPrincipal();
        return contactService.getAllContacts(userContext.getUser());
    }

    @RequestMapping(method = RequestMethod.POST)
    public Contact createContact(@RequestBody Contact contact) {
        UserContext userContext = (UserContext) SecurityContextHolder
            .getContext()
            .getAuthentication()
            .getPrincipal();
        contact.setUser(userContext.getUser());
        return contactService.saveContact(contact);
    }

    @RequestMapping(value = "/{contactId}", method = RequestMethod.PUT)
    public Contact updateContact(@RequestBody Contact contact, @PathVariable Long contactId) {

        UserContext userContext = (UserContext) SecurityContextHolder
            .getContext()
            .getAuthentication()
            .getPrincipal();

        return contactService.updateContact(contact, contactId, userContext.getId());
    }

    @RequestMapping(value = "/{contactId}", method = RequestMethod.GET)
    public Contact getContact(@PathVariable Long contactId) throws IllegalAccessException {
        return contactService.getContact(getContactForId(contactId));
    }

    @RequestMapping(value = "/{contactId}", method = RequestMethod.DELETE)
    public void deleteContact(@PathVariable Long contactId) {
        contactService.deleteContact(getContactForId(contactId));
    }

    @RequestMapping(value = "/{contactId}/entries", method = RequestMethod.POST)
    public void addEntry(@PathVariable Long contactId, @RequestBody Entry entry) {
        contactService.addEntry(entry, getContactForId(contactId));
    }

    private Contact getContactForId(Long contactId) {
        Contact contact = new Contact();
        UserContext userContext = (UserContext) SecurityContextHolder
            .getContext()
            .getAuthentication()
            .getPrincipal();
        contact.setId(contactId);
        contact.setUser(userContext.getUser());
        return contact;
    }
}
