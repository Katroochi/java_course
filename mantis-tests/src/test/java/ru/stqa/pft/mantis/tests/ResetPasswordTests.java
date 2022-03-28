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

    public static String userName = String.format("user%s", number);
    public static String userPass = String.format("password%s", number);
    public static String userNewPass = String.format("newpassword%s", number);
    public static String userEmail = String.format("email%s@email.email", number);;

    public String adminLogin = "administrator";
    public String adminPassword = "root";

    @BeforeMethod
    public void startMailServer() throws MessagingException, IOException {
        app.mail().start();
        System.out.println(userEmail);
        app.registration().start(userName, userEmail);
        List<MailMessage> mailMessages = app.mail().waitForMail(2, 10000);
        String confirmationLink = app.registration().findConfirmationLink(mailMessages, userEmail);

        app.registration().finish(confirmationLink, userName, userPass);
        assertTrue(app.newSession().login(userName, userPass));
    }

    @Test
    public void testResetPassword() throws IOException, MessagingException {

        app.reset().login(adminLogin, adminPassword);
        String email = app.reset().resetPasswordUser(userName);
        assertEquals(email, userEmail);

        List<MailMessage> mailMessages = app.mail().waitForMail(3, 30000);
        String confirmationLink = app.registration().findConfirmationLinkRS(mailMessages);

        app.reset().newPasswordForm(confirmationLink, userName, userNewPass);

        app.reset().login(userName, userNewPass);
        assertTrue(app.newSession().login(userName, userNewPass));
    }

    @AfterMethod(alwaysRun = true)
    public void stopMailServer() { app.mail().stop(); }
}

