package tests.login;

import core.BaseTest;
import io.qameta.allure.testng.Tag;
import org.testng.annotations.Test;
import ui.login.LoginPage;

import static utils.AssertionHelper.handleError;

public class LoginPageTest extends BaseTest {

    @Test
    @Tag("smoke")
    public void login() {
        LoginPage loginPage = openLoginPage().fillEmail("eugenee.rabota@gmail.com")
                .fillPassword("Qwerty1234$");

        handleError(loginPage::clickLoginButton, "Home page is not opened");
    }
}
