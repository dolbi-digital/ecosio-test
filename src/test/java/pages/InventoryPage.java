package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;
import java.util.stream.Collectors;

public class InventoryPage {
    private final WebDriver driver;
    private final WebDriverWait wait;

    @FindBy(css = ".title") private WebElement header;
    @FindBy(css = ".shopping_cart_link") private WebElement cartLink;
    @FindBy(css = ".inventory_item") private List<WebElement> items;
    @FindBy(css = ".inventory_item_name") private List<WebElement> itemNames;
    @FindBy(css = ".inventory_item_price") private List<WebElement> itemPrices;

    @FindBy(css = "#header_container select") private WebElement sortSelect;
    @FindBy(id = "react-burger-menu-btn") private WebElement menuButton;
    @FindBy(id = "logout_sidebar_link") private WebElement logoutLink;

    public InventoryPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
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

    public List<Double> getItemPrices() {
        return itemPrices.stream()
                .map(WebElement::getText)
                .map(s -> s.replace("$", ""))
                .map(Double::parseDouble)
                .collect(Collectors.toList());
    }
    public void openMenu() {
        menuButton.click();
    }

    public void clickLogout() {
        wait.until(ExpectedConditions.visibilityOf(logoutLink));
        wait.until(ExpectedConditions.elementToBeClickable(logoutLink)).click();
    }
}
