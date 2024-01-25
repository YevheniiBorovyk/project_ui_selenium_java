package ui.leftsidebar;

import core.BasePage;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import ui.topbar.TopBar;

import static utils.DriverUtils.isElementVisible;

public class HomePage extends BasePage {

    private final By tabHome = By.xpath("//*[contains(@class,'iconHome')]//ancestor::li[@aria-current='true']");

    @Step
    @Override
    public boolean isOpened() {
        return isElementVisible(tabHome);
    }

    @Step
    public TopBar getTopBar(){
        return new TopBar();
    }
}
