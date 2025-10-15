package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class CheckoutCompletePage {
    @FindBy(css = ".complete-header") private WebElement completeHeader;

    public CheckoutCompletePage(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }

    public boolean isOrderComplete() {
        try {
            return completeHeader.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
}
