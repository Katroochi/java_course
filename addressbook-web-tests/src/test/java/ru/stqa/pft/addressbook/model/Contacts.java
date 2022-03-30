package ru.stqa.pft.addressbook.model;

import com.google.common.collect.ForwardingSet;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class Contacts extends ForwardingSet<ContactData> {

    private Set<ContactData> delegate;

    public Contacts(Contacts contacts){
        this.delegate = new HashSet<>(contacts.delegate);
    }

    public Contacts(){
        this.delegate = new HashSet<>();
    }

    public Contacts(Collection<ContactData> contacts) {
        this.delegate = new HashSet<>(contacts);
    }

    @Override
    protected Set<ContactData> delegate() {
        return delegate;
    }

    public static ContactData getContactWithGroup(Contacts contacts) {
        ContactData contactWithGroup = null;
        for (ContactData contact : contacts) {
            if (contact.getGroups().size() > 0) {
                contactWithGroup = contact;
            }
        }
        System.out.println(contactWithGroup);
        return contactWithGroup;
    }

    public ContactData getInfoOfContact(ContactData contact) {
        ContactData contactForAddition = null;
        for (ContactData allContacts : delegate) {
            if (allContacts.getId() ==contact.getId() ) {
                contactForAddition = allContacts;
                break;
            }
        }
        return contactForAddition;
    }

    public ContactData getInfoOfContactByName(ContactData contact) {
        ContactData contactForAddition = null;
        for (ContactData allContacts : delegate) {
            if (allContacts.getFirstname().equals(contact.getFirstname() )) {
                contactForAddition = allContacts;
                break;
            }
        }
        return contactForAddition;
    }

    public Contacts withAdded(ContactData contact){
        Contacts contacts = new Contacts(this);
        contacts.add(contact);
        return contacts;
    }

    public Contacts whithout(ContactData contact){
        Contacts contacts = new Contacts(this);
        contacts.remove(contact);
        return contacts;
    }
}
