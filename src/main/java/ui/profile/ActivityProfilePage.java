package ui.profile;

import core.BasePage;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

import static utils.DriverUtils.isElementContainsText;
import static utils.DriverUtils.isElementVisible;
import static utils.DriverUtils.openPageByClick;

public class ActivityProfilePage extends BasePage {

    private final By isSelected = By.xpath(
            "//a[contains(@class,'is-selected') and contains(@class,'navigation--item') and contains(text()," +
                    "'Activity')]");
    private final By buttonEditProfile = By.xpath("//a[contains(@href,'/users/edit/')]");
    private final By textDisplayName = By.xpath("//div[contains(@class,'fs-headline2')]");
    private final By textTitle = By.xpath("//div[contains(@class,'fs-title')]");
    private final By textLocation = By.xpath("//div[contains(@class,'truncate')]");

    @Step
    @Override
    public boolean isOpened() {
        return isElementVisible(isSelected);
    }

    @Step
    public EditProfilePage clickEditProfileButton() {
        return openPageByClick(buttonEditProfile, EditProfilePage.class);
    }

    @Step
    public boolean isDisplayNameContainsTo(String displayName) {
        return isElementContainsText(textDisplayName, displayName);
    }

    @Step
    public boolean isTitleContainsTo(String title) {
        return isElementContainsText(textTitle, title);
    }

    @Step
    public boolean isLocationContainsTo(String location) {
        return isElementContainsText(textLocation, location);
    }
}
