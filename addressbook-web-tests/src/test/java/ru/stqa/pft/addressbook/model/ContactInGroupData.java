package ru.stqa.pft.addressbook.model;

import org.hibernate.annotations.Type;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "address_in_groups")
public class ContactInGroupData {

    @Id
    @Column(name = "group_id")
    private int groupId;
    @Column(name = "id")
    private int contactId;

    public int getContactId() {
        return contactId;
    }

    public int getGroupId() {
        return groupId;
    }

    @Override
    public String toString() {
        return "ContactInGroupData{" +
                "groupId=" + groupId +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ContactInGroupData that = (ContactInGroupData) o;

        return groupId == that.groupId;
    }

    @Override
    public int hashCode() {
        return groupId;
    }

    public ContactInGroupData withGroupId(int id) {
        this.groupId = id;
        return this;
    }

    public ContactInGroupData withContactId(int id) {
        this.contactId = id;
        return this;
    }
}
