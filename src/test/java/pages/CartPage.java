package pages;

import core.BaseTest;
import org.openqa.selenium.*;
import org.openqa.selenium.support.*;

import java.util.List;

public class CartPage {
    private final WebDriver driver;

    @FindBy(id = "checkout") private WebElement checkoutBtn;
    @FindBy(css = ".cart_item") private List<WebElement> cartItems;
    @FindBy(id = "remove-sauce-labs-bolt-t-shirt") private WebElement removeBtn;

    public CartPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public int getCartCount() {
        return cartItems.size();
    }

    public void removeFirstItem() {
        if (!cartItems.isEmpty()) removeBtn.click();
    }

    public void removeItem(String productName) {
        WebElement removeButton = driver.findElement(By.xpath("//div[contains(.,'"
                + productName + "')]/ancestor::div[@class='cart_item']//button"));
        removeButton.click();
    }

    public void checkout() {
        checkoutBtn.click();
    }
}

