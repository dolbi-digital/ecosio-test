package pages;

import core.BaseTest;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.*;

    public class CheckoutStepOnePage {
        @FindBy(id = "first-name") private WebElement firstName;
        @FindBy(id = "last-name") private WebElement lastName;
        @FindBy(id = "postal-code") private WebElement postalCode;
        @FindBy(id = "continue") private WebElement continueBtn;

        private final WebDriver driver;

        public CheckoutStepOnePage(WebDriver driver) {
            this.driver = driver;
            PageFactory.initElements(driver, this);
        }

        public void fillAndContinue(String first, String last, String zip) {
            firstName.sendKeys(first);
            lastName.sendKeys(last);
            postalCode.sendKeys(zip);
            continueBtn.click();
        }
    }

