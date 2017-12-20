package edu.swarthmore.cs.cs71.shelved.model.simple;

import edu.swarthmore.cs.cs71.shelved.model.api.User;

import java.util.HashSet;
import java.util.Set;

public class ContactList {
    private Set<User> contactList = new HashSet<>();

    public ContactList() {
    }

    public void addContact(User user) {
        this.contactList.add(user);
    }

    public void removeContact(User user) {
        this.contactList.remove(user);
    }

    public Set<User> getContactList() {
        return contactList;
    }
}
