package tests;

import core.BaseTest;
import org.openqa.selenium.By;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import pages.InventoryPage;
import pages.LoginPage;
import utils.Config;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class LoginLogoutTests extends BaseTest {

    final String stdUser = Config.get("username");
    final String stdPass = Config.get("password");

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
        LoginPage login = new LoginPage(driver());
        login.login(username, password);

        if (shouldPass) {
            assertTrue(new InventoryPage(driver()).isLoaded());
        } else {
            assertTrue(login.isErrorVisible());
        }
    }

    @Test
    public void logout_should_return_to_login() {
        LoginPage login = new LoginPage(driver());
        login.login(stdUser, stdPass);
        assertTrue(new InventoryPage(driver()).isLoaded());

        driver().findElement(By.id("react-burger-menu-btn")).click();
        try { Thread.sleep(500); } catch (InterruptedException ignored) {}
        driver().findElement(By.id("logout_sidebar_link")).click();

        assertEquals(driver().getCurrentUrl(), "https://www.saucedemo.com/");
        assertTrue(login.getLoginBtn().isDisplayed());
    }
}

