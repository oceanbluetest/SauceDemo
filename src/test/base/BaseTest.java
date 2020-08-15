package base;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import utils.ConfigReader;

import java.util.concurrent.TimeUnit;

public class BaseTest {
    private static final ThreadLocal<WebDriver> drivers = new ThreadLocal<>();
    private static String propertyPath = "src/main/resources/config/configuration.properties";

    @BeforeMethod(alwaysRun = true)
    public void setUp(){
        initializeDriver(ConfigReader.readProperty("browser", propertyPath));
        getDriver().get(ConfigReader.readProperty("url", propertyPath));
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown(){
        getDriver().quit();
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
