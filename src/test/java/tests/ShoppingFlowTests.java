package tests;

import core.BaseTest;
import org.testng.annotations.Test;
import pages.*;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class ShoppingFlowTests extends BaseTest {
    final String backpack = "Backpack";
    final String bikelight = "Bike Light";

    @Test
    public void add_item_and_checkout() {
        new LoginPage(driver()).login(stdUser, stdPass);
        InventoryPage inventoryPage = new InventoryPage(driver());
        assertTrue(inventoryPage.isLoaded());
        inventoryPage.addProductToCart(backpack);
        inventoryPage.addProductToCart(bikelight);
        inventoryPage.addProductToCart("Fleece Jacket");
        inventoryPage.goToCart();

        CartPage cartPage = new CartPage(driver());
        assertEquals(cartPage.getCartCount(), 3);
        cartPage.checkout();

        new CheckoutStepOnePage(driver()).fillAndContinue("Dima", "Dolbilov", "12345");
        new CheckoutStepTwoPage(driver()).finish();

        assertTrue(new CheckoutCompletePage(driver()).isOrderComplete());
    }

    @Test
    public void remove_item_from_cart() {
        new LoginPage(driver()).login(stdUser, stdPass);
        InventoryPage inv = new InventoryPage(driver());
        inv.addProductToCart(backpack);
        inv.addProductToCart(bikelight);
        inv.goToCart();

        CartPage cart = new CartPage(driver());
        int before = cart.getCartCount();
        cart.removeItem(bikelight);
        cart.removeItem(backpack);
        int after = cart.getCartCount();
        assertEquals(before, 2);
        assertEquals(after, 0);
    }
}
