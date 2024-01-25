package ui.search;

import core.BasePage;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import ui.topbar.TopBar;

import static utils.DriverUtils.isElementVisible;

public class SearchResultPage extends BasePage {

    private final By title = By.xpath("//h1[contains(@class,'headline1') and contains(text() , 'Search Results')]");

    @Step
    @Override
    public boolean isOpened() {
        return isElementVisible(title);
    }

    @Step
    public TopBar getTopBar() {
        return new TopBar();
    }
}
