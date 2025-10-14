package pages;

import core.BaseTest;
import core.Browser;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.*;

public class LoginPage {
    @FindBy(id = "user-name")
    private WebElement username;
    @FindBy(id = "password")
    private WebElement password;

    @FindBy(id = "login-button")
    private WebElement loginBtn;
    @FindBy(css = "h3[data-test='error']")
    private WebElement errorBox;

    private final WebDriver driver;

    public LoginPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public void login(String user, String pass) {
        username.sendKeys(user);
        password.sendKeys(pass);
        loginBtn.click();
    }

    public boolean isErrorVisible() {
        try {
            return errorBox.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public WebElement getLoginBtn() {
        return loginBtn;
    }
}
