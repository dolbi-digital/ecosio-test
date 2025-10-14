package pages;

import core.BaseTest;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.*;

public class CheckoutStepTwoPage {
    @FindBy(id = "finish") private WebElement finishBtn;

    private final WebDriver driver;

    public CheckoutStepTwoPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public void finish() {
        finishBtn.click();
    }
}

