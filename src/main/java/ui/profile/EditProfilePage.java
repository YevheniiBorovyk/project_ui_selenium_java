package ui.profile;

import core.BasePage;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

import static utils.DriverUtils.isElementVisible;
import static utils.DriverUtils.openPageByClick;
import static utils.DriverUtils.slowTypeWithCheckingText;
import static utils.DriverUtils.type;

public class EditProfilePage extends BasePage {

    private final By isSelected = By.xpath("//a[contains(@class,'is-selected') and contains(.,'Edit profile')]");
    private final By buttonChangePicture = By.id("change-picture");
    private final By inputDisplayName = By.id("displayName");
    private final By inputLocation = By.id("location");
    private final By inputTitle = By.id("Title");
    private final By buttonSaveProfile = By.xpath("//button[contains(@class,'save-button')]");

    @Step
    @Override
    public boolean isOpened() {
        return isElementVisible(isSelected);
    }

    @Step
    public ChangYourPicturePopup clickChangePictureButton() {
        return openPageByClick(buttonChangePicture, ChangYourPicturePopup.class);
    }

    @Step
    public EditProfilePage fillDisplayName(String displayName) {
        type(inputDisplayName, displayName);
        return this;
    }

    @Step
    public EditProfilePage fillLocation(String location) {
        slowTypeWithCheckingText(inputLocation, location);
        return this;
    }

    @Step
    public EditProfilePage fillTitle(String title) {
        slowTypeWithCheckingText(inputTitle, title);
        return this;
    }

    @Step
    public ActivityProfilePage clickSaveProfileButton() {
        return openPageByClick(buttonSaveProfile, ActivityProfilePage.class);
    }
}
