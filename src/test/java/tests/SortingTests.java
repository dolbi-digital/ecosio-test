package tests;

import core.BaseTest;
import core.Browser;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.InventoryPage;
import pages.LoginPage;

import java.util.*;

import static core.BaseTest.driver;

public class SortingTests extends BaseTest {

    @Test
    public void sort_by_name_desc() {
        new LoginPage(driver()).login("standard_user", "secret_sauce");
        InventoryPage inv = new InventoryPage(driver());
        Assert.assertTrue(inv.isLoaded());

        List<String> before = inv.getItemNames();
        inv.sortBy("Name (Z to A)");
        List<String> after = inv.getItemNames();

        List<String> sorted = new ArrayList<>(before);
        sorted.sort(Comparator.reverseOrder());
        Assert.assertEquals(after, sorted, "Items should be sorted Z→A");
    }
}

