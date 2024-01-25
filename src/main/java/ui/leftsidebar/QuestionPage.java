package ui.leftsidebar;

import core.BasePage;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

import static utils.DriverUtils.isElementVisible;

public class QuestionPage extends BasePage {

    private final By tabQuestion = By.xpath("//*[contains(@class,'iconQuestion')]/ancestor::li[@aria-current='true']");

    @Step
    @Override
    public boolean isOpened() {
        return isElementVisible(tabQuestion);
    }
}
