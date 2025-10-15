package core;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.*;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.*;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.safari.SafariOptions;
import java.nio.file.Paths;

public class WebDriverFactory {

    public static WebDriver create(Browser browser, boolean headless) {
        switch (browser) {
            case FIREFOX:
                WebDriverManager.firefoxdriver().setup();
                FirefoxOptions firefoxOptions = new FirefoxOptions();
                if (headless) firefoxOptions.addArguments("--headless");
                return new FirefoxDriver(firefoxOptions);

            case EDGE:
                //WebDriverManager.edgedriver().setup();

                System.setProperty("webdriver.edge.driver",
                        Paths.get("drivers", "msedgedriver").toAbsolutePath().toString());
                EdgeOptions edgeOptions = new EdgeOptions();
                if (headless) edgeOptions.addArguments("--headless=new", "--disable-gpu");
                edgeOptions.addArguments("--inprivate", "--start-maximized");
                return new EdgeDriver(edgeOptions);

            case SAFARI:
                SafariOptions safariOptions = new SafariOptions();
                return new SafariDriver(safariOptions);

            case CHROME:
            default:
                WebDriverManager.chromedriver().setup();
                ChromeOptions chromeOptions = new ChromeOptions();
                if (headless) chromeOptions.addArguments("--headless=new", "--disable-gpu");
                chromeOptions.addArguments("--incognito", "--no-first-run",
                        "--no-default-browser-check", "--start-maximized");
                return new ChromeDriver(chromeOptions);
        }
    }
}
