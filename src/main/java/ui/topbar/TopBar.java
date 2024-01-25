package ui.topbar;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import ui.profile.ActivityProfilePage;
import ui.search.SearchResultPage;

import static utils.DriverUtils.createInstanceOfOpenable;
import static utils.DriverUtils.isElementAttributeHasValue;
import static utils.DriverUtils.openPageByClick;
import static utils.DriverUtils.sendKeys;
import static utils.DriverUtils.type;

public class TopBar {

    private final By inputSearch = By.xpath("//input[@placeholder='Search…']");
    private final By buttonProfile = By.xpath("//div[contains(@class,'user-card--avatar')]");

    @Step
    public SearchResultPage search(String text) {
        type(inputSearch, text);
        sendKeys(Keys.ENTER);
        return createInstanceOfOpenable(SearchResultPage.class);
    }

    @Step
    public boolean isSearchInputHasValue(String value) {
        return isElementAttributeHasValue(inputSearch, "value", value);
    }

    @Step
    public ActivityProfilePage clickProfileButton() {
        return openPageByClick(buttonProfile, ActivityProfilePage.class);
    }
}
