package tests;

import core.BaseTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import pages.InventoryPage;
import pages.LoginPage;
import utils.Config;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class LoginLogoutTests extends BaseTest {

    @DataProvider(name = "users")
    public Object[][] users() {
        return new Object[][]{
                { stdUser,                      stdPass,        true },
                { stdUser,                      "wrong_pass",   false },
                { "locked_out_user",            stdPass,        false },
                { "problem_user",               stdPass,        true },
                { "performance_glitch_user",    stdPass,        true },
                { "error_user",                 stdPass,        true },
                { "visual_user",                stdPass,        true },
        };
    }

    @Test(dataProvider = "users")
    public void loginParameterized(String username, String password, boolean shouldPass) {
        LoginPage loginPage = new LoginPage(driver());
        loginPage.login(username, password);

        if (shouldPass) {
            assertTrue(new InventoryPage(driver()).isLoaded());
        } else {
            assertTrue(loginPage.isErrorVisible());
        }
    }

    @Test
    public void logout_should_return_to_login() {
        LoginPage loginPage = new LoginPage(driver());
        loginPage.login(stdUser, stdPass);
        InventoryPage inventoryPage = new InventoryPage(driver());
        assertTrue(inventoryPage.isLoaded());
        inventoryPage.openMenu();
        inventoryPage.clickLogout();
        assertEquals(driver().getCurrentUrl(), Config.get("baseUrl"));
        assertTrue(loginPage.getLoginBtn().isDisplayed());
    }
}
