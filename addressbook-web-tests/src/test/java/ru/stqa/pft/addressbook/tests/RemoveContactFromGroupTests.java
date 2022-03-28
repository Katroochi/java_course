package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.GroupData;
import ru.stqa.pft.addressbook.model.Groups;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static ru.stqa.pft.addressbook.model.Contacts.getContactWithGroup;

public class RemoveContactFromGroupTests extends TestBase{

    @BeforeMethod
    public void ensurePrecondition() {
        if (app.db().groups().size() == 0) {
            app.goTo().groupPage();
            app.group().create(new GroupData()
                    .whithName("TestName1")
                    .whithHeader("TestHeader1")
                    .whithFooter("TestFooter1"));
        }
        if(getContactWithGroup(app.db().contacts()) == null || app.contact().all().size() == 0 ){
            app.goTo().homePage();
            app.contact().create(new ContactData()
                    .withFirstname("NewName")
                    .withLastname("NewLastname")
                    .withAddress("NewAddress")
                    .inGroup(app.db().groups().iterator().next()));
        }
    }

    @Test
    public void testContactRemove() {
        app.goTo().homePage();
        ContactData before = getContactWithGroup(app.db().contacts());
        Groups groupsBefore = before.getGroups();
        GroupData groupToDelete = before.getGroups().iterator().next();

        app.contact().removeFromGroup(before, groupToDelete);
        app.goTo().homePage();
        ContactData after = app.db().contacts().getInfoOfContact(before);
        Groups groupsAfter = after.getGroups();

        assertThat(groupsAfter.size(), equalTo(groupsBefore.size() - 1));
        assertThat(groupsAfter, equalTo(groupsBefore.whithout(groupToDelete)));
        }
    }
