package ru.stqa.pft.addressbook.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Select;

public class NavigationHelper extends HelperBase{

    public NavigationHelper(WebDriver driver) {
        super(driver);
    }

    public void groupPage() {
      click(By.linkText("groups"));
    }

    public void groupContacts(int groupId) {
        new Select(driver.findElement(By.name("group"))).selectByValue(Integer.toString(groupId));
    }

    public void homePage() {
        click(By.linkText("home"));
    }

    public void homePage(String groupId) {
        new Select(driver.findElement(By.name("group"))).selectByValue(groupId);
    }

    public void logout() {
        click(By.linkText("Logout"));
    }

}
