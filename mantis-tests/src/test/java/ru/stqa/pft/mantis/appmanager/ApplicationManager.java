package ru.stqa.pft.mantis.appmanager;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.BrowserType;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import java.util.regex.MatchResult;

public class ApplicationManager {
    private final Properties properties;
    private final String browser;
    private WebDriver driver;
    private RegistrationHelper registrationHelper;
    private FtpHelper ftp;

    public ApplicationManager(String browser) {
        this.browser = browser;
        properties = new Properties();
    }

    public void init() throws IOException {
        String target = System.getProperty("target", "local");
        properties.load(new FileReader(new File(String.format("src/test/resources/%s.properties", target))));
    }

    public HttpSession newSession(){
        return new HttpSession(this);
    }

    public void stop() {
        if(driver !=null) {
            driver.quit();
        }
    }

    public String getProperty(String key) {
        return properties.getProperty(key);
    }

    public RegistrationHelper registration() {
        if(registrationHelper == null){
            registrationHelper = new RegistrationHelper(this);
        }
        return registrationHelper;
    }

    public FtpHelper ftp(){
        if(ftp == null) {
            ftp = new FtpHelper(this);
        }
        return ftp;
    }

    public WebDriver getDriver() {
        if(driver == null){
            if (browser.equals(BrowserType.FIREFOX)) {
                driver = new FirefoxDriver();
            } else if (browser.equals(BrowserType.CHROME)) {
                driver = new ChromeDriver();
            }

            driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
            driver.get(properties.getProperty("web.baseUrl"));
        }
        return driver;
    }
}
