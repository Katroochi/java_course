package ru.stqa.pft.mantis.appmanager;

import org.openqa.selenium.By;

public class ResetHelper extends HelperBase{

    public ResetHelper(ApplicationManager app) {
        super(app);
    }

    public void login(String username, String password) {
        driver.get(app.getProperty("web.baseUrl") + "/login_page.php");
        type(By.id("username"), username);
        click(By.cssSelector("input[type='submit']"));
        type(By.id("password"), password);
        click(By.cssSelector("input[type='submit']"));
    }

    public void gotoManagerUsers() {
        click(By.className("fa-gears"));
        click(By.xpath("//a[contains(@href, '/manage_user_page.php')]"));
    }

    public String searchAndSelectedUser(String userName) {
        type(By.id("search"), userName);
        click(By.xpath("//form[@id='manage-user-filter']//input[@type='submit']"));
        click(By.cssSelector("td:nth-of-type(1) > a"));
        String email = driver.findElement(By.xpath("//input[@id='email-field']")).getAttribute("value");
        return email;
    }

    public void resetPassword()  {
        click(By.xpath("//form[@id='manage-user-reset-form']//input[@type='submit']"));
    }

    public void logout()  {
        click(By.className("user-info"));
        click(By.className("fa-sign-out"));
    }

    public String resetPasswordUser(String userName) {
        gotoManagerUsers();
        String userEmail = searchAndSelectedUser(userName);
        resetPassword();
        logout();
        return userEmail;
    }

    public void newPasswordForm(String confirmationLink, String username, String newPassword){
        driver.get(confirmationLink);
        type(By.id("realname"),username);
        type(By.id("password"),newPassword);
        type(By.id("password-confirm"),newPassword);
        click(By.cssSelector("button[type='submit']"));
    }

}
