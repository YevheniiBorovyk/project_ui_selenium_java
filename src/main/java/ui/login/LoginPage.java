package ui.login;

import core.BasePage;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import ui.leftsidebar.HomePage;

import static utils.DriverUtils.getText;
import static utils.DriverUtils.isElementVisible;
import static utils.DriverUtils.openPageByClick;
import static utils.DriverUtils.type;
import static utils.DriverUtils.typeWithCheckingText;
import static utils.DriverUtils.waitUntilElementsDisappearIfVisible;

public class LoginPage extends BasePage {

    private final By inputEmail = By.id("email");
    private final By inputPassword = By.id("password");
    private final By buttonLogin = By.id("submit-button");
    private final By errorMessage = By.xpath("//p[contains(@class,'error-details')]");

    @Step
    @Override
    public boolean isOpened() {
        return isElementVisible(inputEmail);
    }

    @Step
    public LoginPage fillEmail(String email) {
        typeWithCheckingText(inputEmail, email);
        return this;
    }

    @Step
    public LoginPage fillPassword(String password) {
        type(inputPassword, password);
        return this;
    }

    @Step
    public HomePage clickLoginButton() {
        return openPageByClick(buttonLogin, HomePage.class);
    }

    @Step
    public String getErrorMessage() {
        return getText(errorMessage);
    }
}
