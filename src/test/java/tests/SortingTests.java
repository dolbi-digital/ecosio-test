package tests;

import core.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.InventoryPage;
import pages.LoginPage;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class SortingTests extends BaseTest {

    @Test
    public void sort_by_name_desc() {
        new LoginPage(driver()).login(stdUser, stdPass);
        InventoryPage inventoryPage = new InventoryPage(driver());
        Assert.assertTrue(inventoryPage.isLoaded());

        inventoryPage.sortBy("Name (Z to A)");

        List<String> actual = inventoryPage.getItemNames();
        List<String> expected = actual.stream()
                .sorted(Comparator.reverseOrder())
                .collect(Collectors.toList());

        Assert.assertEquals(actual, expected);
    }

    @Test
    public void sort_by_price_desc() {
        new LoginPage(driver()).login(stdUser, stdPass);
        InventoryPage inventoryPage = new InventoryPage(driver());
        Assert.assertTrue(inventoryPage.isLoaded());
        inventoryPage.sortBy("Price (high to low)");
        List<Double> actual = inventoryPage.getItemPrices();
        List<Double> expected = actual.stream()
                .sorted(Comparator.reverseOrder())
                .collect(Collectors.toList());
        Assert.assertEquals(actual, expected);
    }
}
