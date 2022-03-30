package ru.stqa.pft.addressbook.tests;

import org.checkerframework.checker.units.qual.C;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.*;

import java.util.Collection;
import java.util.HashSet;

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
        ContactData contact = freeContact();
        Groups groupsBefore = contact.getGroups();
        GroupData group = freeGroup(contact);

        app.goTo().homePage();
        app.contact().addToGroup(contact, group);
        Groups after = app.db().getContact(contact.getId()).getGroups();
        ContactsInGroup groupsAfter = app.db().groupsWithContact();
        assertThat(after, equalTo(groupsBefore.whithAdded(group)));
        assertThat(groupsAfter, equalTo(before.withAdded(new ContactInGroupData().withGroupId(group.getId()))));
        assertTrue(contact.getGroups().whithAdded(group).contains(group));
    }

    //ищу контакт, который не добавлен хотябы в одну группу
    //если такого нет, создаю новый
    public ContactData freeContact () {
        Contacts contacts = app.db().contacts();
        Groups groups = app.db().groups();
        ContactData freeContact = null;
        for (ContactData contact : contacts) {
            if (contact.getGroups().size() < groups.size()) {
                freeContact = contact;
            }
        }
        if(freeContact == null){
            int number = (int) (Math.random() * 1000);
            ContactData newContact = new ContactData().withFirstname("sdfsdf"+number).withLastname("dsfsdfs");
            app.goTo().homePage();
            app.contact().create(newContact);
            int id = app.db().contacts().getInfoOfContactByName(newContact).getId();
            freeContact = newContact.withId(id);;
        }
        return freeContact;
    }

    //беру группы и смотрю есть ли они в контакте
    //если у контакта нет групп, то беру любую группу
    public GroupData freeGroup (ContactData contact){
        Groups groups = app.db().groups();
        GroupData freeGroup = null;
        for(GroupData group : groups){
            if(!contact.getGroups().contains(group)){
                freeGroup = group;
            }
        }
        if(freeGroup == null){
            freeGroup = groups.iterator().next();
        }
        return freeGroup;
    }
}
