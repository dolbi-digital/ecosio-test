package pages;

import core.BaseTest;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.*;

public class CheckoutCompletePage {
    @FindBy(css = ".complete-header") private WebElement completeHeader;
    private final WebDriver driver;

    public CheckoutCompletePage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this); }

    public boolean isOrderComplete() {
        try {
            return completeHeader.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
}
