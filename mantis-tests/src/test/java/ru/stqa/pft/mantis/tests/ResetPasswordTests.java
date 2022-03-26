package ru.stqa.pft.mantis.tests;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.mantis.model.MailMessage;

import javax.mail.MessagingException;
import java.io.IOException;
import java.util.List;

import static org.testng.Assert.*;

public class ResetPasswordTests extends TestBase {

    private static final int number = (int) (Math.random() * 1000);

    public static String username = String.format("user%s", number);
    public static String userPass = String.format("password%s", number);
    public static String userNewPass = String.format("newpassword%s", number);
    public static String userEmail = String.format("email%s@email.email", number);

    public String adminLogin = "administrator";
    public String adminPassword = "root";

    @BeforeMethod
    public void startMailServer() throws MessagingException, IOException {
        app.mail().start();
        System.out.println(userEmail);
        app.registration().start(username, userEmail);
        List<MailMessage> mailMessages = app.mail().waitForMail(2, 10000);
        String confirmationLink = app.registration().findConfirmationLinkReg(mailMessages, userEmail);

        app.registration().finish(confirmationLink, userPass);
        assertTrue(app.newSession().login(username, userPass));

        app.reset().logout();
    }

    @Test
    public void testResetPassword() throws IOException, MessagingException {

        app.reset().login(adminLogin, adminPassword);
        String email = app.reset().resetPasswordUser(username);
        assertEquals(email, userEmail);

        List<MailMessage> mailMessages = app.mail().waitForMail(3, 30000);
        String confirmationLink = app.reset().findConfirmationLinkRes(mailMessages, email);

        app.registration().finish(confirmationLink, userNewPass);

        app.reset().login(username, userNewPass);
        assertTrue(app.newSession().login(username, userNewPass));
    }

    @AfterMethod(alwaysRun = true)
    public void stopMailServer() { app.mail().stop(); }
}

