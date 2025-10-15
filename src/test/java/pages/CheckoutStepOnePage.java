package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

    public class CheckoutStepOnePage {
        @FindBy(id = "first-name") private WebElement firstName;
        @FindBy(id = "last-name") private WebElement lastName;
        @FindBy(id = "postal-code") private WebElement postalCode;
        @FindBy(id = "continue") private WebElement continueBtn;

        public CheckoutStepOnePage(WebDriver driver) {
            PageFactory.initElements(driver, this);
        }

        public void fillAndContinue(String first, String last, String zip) {
            firstName.sendKeys(first);
            lastName.sendKeys(last);
            postalCode.sendKeys(zip);
            continueBtn.click();
        }
    }
