package tests;

import core.BaseTest;
import core.Browser;
import org.testng.annotations.Test;
import pages.*;

import static core.BaseTest.driver;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class ShoppingFlowTests extends BaseTest {

    @Test
    public void add_item_and_checkout() throws InterruptedException {
        new LoginPage(driver()).login("standard_user", "secret_sauce");
        assertTrue(new InventoryPage(driver()).isLoaded());
        InventoryPage inv = new InventoryPage(driver());
        inv.addProductToCart("Backpack");
        inv.addProductToCart("Bike Light");
        inv.addProductToCart("Fleece Jacket");
        inv.goToCart();

        CartPage cart = new CartPage(driver());
        assertEquals(cart.getCartCount(), 3);
        cart.checkout();

        new CheckoutStepOnePage(driver()).fillAndContinue("Dima", "Dolbilov", "12345");
        new CheckoutStepTwoPage(driver()).finish();

        assertTrue(new CheckoutCompletePage(driver()).isOrderComplete());
    }

    @Test
    public void remove_item_from_cart() throws InterruptedException {
        LoginPage login = new LoginPage(driver());
        login.login("standard_user", "secret_sauce");
        InventoryPage inv = new InventoryPage(driver());
        inv.addProductToCart("Backpack");
        inv.addProductToCart("Bike Light");
        inv.goToCart();

        CartPage cart = new CartPage(driver());
        int before = cart.getCartCount();
        cart.removeItem("Bike Light");
        cart.removeItem("Backpack");
        int after = cart.getCartCount();
        assertEquals(before, 2);
        assertEquals(after, 0);
    }
}
