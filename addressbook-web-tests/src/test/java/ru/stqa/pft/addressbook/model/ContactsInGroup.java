package ru.stqa.pft.addressbook.model;

import com.google.common.collect.ForwardingSet;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class ContactsInGroup extends ForwardingSet<ContactInGroupData> {
    private Set<ContactInGroupData> delegate;

    public ContactsInGroup(Collection<ContactInGroupData> groups) {
        this.delegate = new HashSet<ContactInGroupData>(groups);
    }

    @Override
    protected Set<ContactInGroupData> delegate() {
        return delegate;
    }

    public ContactsInGroup withAdded(ContactInGroupData group) {
        ContactsInGroup groups = new ContactsInGroup(this);
        groups.add(group);
        return groups;
    }

    public ContactsInGroup without(ContactInGroupData group) {
        ContactsInGroup groups = new ContactsInGroup(this);
        groups.remove(group);
        return groups;
    }
}
