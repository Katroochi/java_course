package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;
import ru.stqa.pft.addressbook.model.GroupData;
import ru.stqa.pft.addressbook.model.Groups;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactAdditionToGroupTests extends TestBase {

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
    public void testContactAddAndRemoveFromGroup(){
        app.goTo().homePage();
        Contacts contacts = app.db().contacts();
        Groups groups = app.db().groups();
        ContactData addContact = contacts.iterator().next();
        GroupData group = groups.iterator().next();
        app.contact().addContactInGroup(addContact, group);
        System.out.println(addContact.getGroups());
        assertThat(String.valueOf(addContact.getGroups().contains(group)), true);
        app.contact().goToContactGroups(group);
        app.contact().clickSelectContactById(addContact.getId());
        app.contact().submitRemoveContactFromGroup();
        System.out.println(addContact.getGroups().whithout(group));
        assertThat(String.valueOf(addContact.getGroups().whithout(group).isEmpty()), true);
    }
}