package core;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.*;
import utils.Config;

import java.io.InputStream;
import java.util.Properties;

public class BaseTest {
    public final String stdUser = Config.get("username");
    public final String stdPass = Config.get("password");
    private static final ThreadLocal<WebDriver> driverThreadLocal = new ThreadLocal<>();
    public static WebDriver driver() {
        return driverThreadLocal.get();
    }

    private static final Properties properties = new Properties();
    static {
        try (InputStream in = BaseTest.class.getClassLoader().getResourceAsStream("config.properties")) {
            if (in != null) properties.load(in);
        } catch (Exception ignored) {}
    }

    @Parameters({"browser", "headless"})
    @BeforeMethod(alwaysRun = true)
    public void setUp(@Optional("CHROME") String browserName,
                      @Optional("false") String headlessValue) {
        Browser browser = Browser.valueOf(browserName.toUpperCase());
        boolean headless = Boolean.parseBoolean(headlessValue);
        WebDriver webDriver = WebDriverFactory.create(browser, headless);
        driverThreadLocal.set(webDriver);
        webDriver.get(System.getProperty("baseUrl", properties.getProperty("baseUrl")));
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        WebDriver webDriver = driver();
        if (webDriver != null) {
            webDriver.quit();
            driverThreadLocal.remove();
        }
    }
}
