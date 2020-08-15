package tests;

import base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.LogInPage;

public class LogInPageTest extends BaseTest {
    LogInPage logInPage;

    @BeforeMethod(alwaysRun = true)
    public void setUp(){
        super.setUp();
        logInPage = new LogInPage(getDriver());
    }

    @Test
    public void verifyTitle(){
        logInPage.sleep(3000);
        Assert.assertEquals(getDriver().getTitle(), "Swag Labs");
    }

    @Test
    public void verifyLogInStandardUser(){
        logInPage.logIn("standard");
        Assert.assertEquals(getDriver().getCurrentUrl(), "https://www.saucedemo.com/inventory.html");
    }
}
