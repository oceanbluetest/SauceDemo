package tests;

import base.BaseTest;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.HomePage;
import pages.LogInPage;

import java.lang.reflect.Method;

public class HomePageTest extends BaseTest {
    LogInPage logInPage;
    HomePage homePage;

    @BeforeMethod(alwaysRun = true)
    public void localSetUp(Method method, ITestResult result){
        setUp(method, result);
        logInPage = new LogInPage(getDriver());
        homePage = new HomePage(getDriver());
    }

    @Test
    public void validatePageHeader(){
        logInPage.logIn("standard");
        Assert.assertEquals(homePage.headerText.getText(), "Products");
    }

}
