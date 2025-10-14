package pages;

import core.BaseTest;
import core.Browser;
import org.openqa.selenium.*;
import org.openqa.selenium.support.*;
import org.openqa.selenium.support.ui.Select;

import java.util.List;
import java.util.stream.Collectors;

public class InventoryPage {
    private final WebDriver driver;

    @FindBy(css = ".title")
    private WebElement header;
    @FindBy(css = ".shopping_cart_link")
    private WebElement cartLink;
    @FindBy(css = ".inventory_item")
    private List<WebElement> items;
    @FindBy(css = ".inventory_item_name")
    private List<WebElement> itemNames;
    @FindBy(css = "#header_container select")
    private WebElement sortSelect;

    public InventoryPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public boolean isLoaded() {
        return header.getText().equalsIgnoreCase("Products");
    }

    public void addProductToCart(String productName) {
        WebElement addButton = driver.findElement(By.xpath("//div[contains(.,'"
                + productName + "')]/ancestor::div[@class='inventory_item']//button"));
        addButton.click();
    }

    public void goToCart() {
        cartLink.click();
    }

    public void sortBy(String visibleText) {
        new Select(sortSelect).selectByVisibleText(visibleText);
    }

    public List<String> getItemNames() {
        return itemNames.stream().map(WebElement::getText).collect(Collectors.toList());
    }
}
