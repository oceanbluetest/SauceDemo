package tests;

import base.BaseTest;
import com.relevantcodes.extentreports.LogStatus;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.LogInPage;

import java.lang.reflect.Method;

public class LogInPageTest extends BaseTest {
    LogInPage logInPage;

    @BeforeMethod(alwaysRun = true)
    public void setUp(Method method, ITestResult result){
        super.setUp(method, result);
        logInPage = new LogInPage(getDriver());
    }

    @Test
    public void verifyTitle(){
        logInPage.sleep(3000);
        Assert.assertEquals(getDriver().getTitle(), "Swag Labs");
    }

    @Test(description = "Testing standard user")
    public void verifyLogInStandardUser(){
        logInPage.logIn("standard");
        Assert.assertEquals(getDriver().getCurrentUrl(), "https://www.saucedemo.com/inventory.html");
        extentTest.log(LogStatus.INFO,"current URL: " + getDriver().getCurrentUrl());
        extentTest.log(LogStatus.INFO, "expected URL: https://www.saucedemo.com/inventory.html");
        screenshot.takeScreenshotAndLog();
    }

    @Test(description = "Testing Locked User role")
    public void verifyLockedUser(){
        logInPage.logIn("locked");
        Assert.assertEquals(logInPage.errorMsg.getText(), "Epic sadface: Sorry, this user has been locked out.");
        screenshot.takeScreenshotAndLog();
    }
}
