package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.*;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.testng.Assert.assertTrue;

public class ContactAddToGroup extends TestBase{

    @BeforeMethod
    public void ensurePreconditions() {
        if (app.db().contacts().size() == 0) {
            app.goTo().homePage();
            app.contact().create(new ContactData().withFirstname("Test1343").withLastname("Testing1433"));
        }
        if (app.db().groups().size() == 0) {
            app.goTo().groupPage();
            app.group().create(new GroupData().whithName("test2"));
        }
    }

    @Test
    public void testAddContactToGroup() {
        ContactsInGroup before = app.db().groupsWithContact();
        ContactData contact = selectedContact();
        Groups groupsBefore = contact.getGroups();
        Groups groups = app.db().groups();
        GroupData group = groups.iterator().next();

        app.goTo().homePage();
        app.contact().addToGroup(contact, group);
        Groups after = app.db().getContact(contact.getId()).getGroups();
        ContactsInGroup groupsAfter = app.db().groupsWithContact();
        assertThat(after, equalTo(groupsBefore.whithAdded(group)));
        assertThat(groupsAfter, equalTo(before.withAdded(new ContactInGroupData().withGroupId(group.getId()))));
        assertTrue(contact.getGroups().whithAdded(group).contains(group));
    }

    public ContactData selectedContact() {
        Contacts contacts = app.db().contacts();
        Groups groups = app.db().groups();
        for (ContactData contact : contacts) {
            if (contact.getGroups().size() < groups.size()) {
                return contact;
            }
        }
        return contacts.iterator().next();
    }

}
