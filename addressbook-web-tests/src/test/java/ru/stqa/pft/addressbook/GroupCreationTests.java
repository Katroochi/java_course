package ru.stqa.pft.addressbook;

import org.testng.annotations.*;

public class GroupCreationTests extends TestBase{

  @Test
  public void testGroupCreation(){
    gotoGroupPage();
    initGroupCreation();
    fillGroupForm(new GroupData("group2", "test group", "test group"));
    submitGroupCreation();
    returnToGroupPage();
    logout();
  }

}
