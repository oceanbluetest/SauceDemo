package base;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import utils.ConfigReader;
import utils.ExtentReportsManager;

import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;

public class BaseTest {
    private static final ThreadLocal<WebDriver> drivers = new ThreadLocal<>();
    private static String propertyPath = "src/main/resources/config/configuration.properties";

    ExtentReports extentReports;
    ExtentTest extentTest;

    @BeforeSuite
    public void startReport(){
        extentReports = ExtentReportsManager.loadConfig();

    }

    @BeforeMethod(alwaysRun = true)
    public void setUp(Method method, ITestResult result){
        initializeDriver(ConfigReader.readProperty("browser", propertyPath));

        extentTest = extentReports.startTest((this.getClass().getSimpleName() + " : " + method.getName()), method.getName());
        extentTest.assignAuthor("Tester");

        extentTest.log(LogStatus.INFO, result.getMethod().getDescription());

        getDriver().get(ConfigReader.readProperty("url", propertyPath));
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown(){
        getDriver().quit();
    }

    @AfterSuite
    public void closeReport(){
        extentReports.flush();
        extentReports.close();
    }


    private void initializeDriver(String browser){
        WebDriver driver = null;
        switch (browser){
            case "chrome":
                WebDriverManager.chromedriver().setup();
                driver = new ChromeDriver();
                break;
            case "firefox":
                WebDriverManager.firefoxdriver().setup();
                driver = new FirefoxDriver();
                break;
            default:
                System.out.println("Invalid browser type");
        }
        drivers.set(driver);
        getDriver().manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
    }

    public WebDriver getDriver(){
        WebDriver driver = drivers.get();
        if (driver == null){
            System.out.println("driver object was null");
        }
        return driver;
    }

}
