package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

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

    public void removeItem(String productName) {
        WebElement removeButton = driver.findElement(By.xpath("//div[contains(.,'"
                + productName + "')]/ancestor::div[@class='cart_item']//button"));
        removeButton.click();
    }

    public void checkout() {
        checkoutBtn.click();
    }
}
