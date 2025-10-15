package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LoginPage {
    @FindBy(id = "user-name")
    private WebElement username;
    @FindBy(id = "password")
    private WebElement password;

    @FindBy(id = "login-button")
    private WebElement loginBtn;
    @FindBy(css = "h3[data-test='error']")
    private WebElement errorBox;

    public LoginPage(WebDriver driver) {
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
