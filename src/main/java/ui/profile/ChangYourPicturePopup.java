package ui.profile;

import core.PopupBase;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

import static utils.DriverUtils.isElementVisible;
import static utils.DriverUtils.openPageByClick;

public class ChangYourPicturePopup extends PopupBase {

    private final By body = By.xpath("//div[contains(@class,'profile-picture-popup')]");
    private final By buttonUploadNewPicture = By.id("avatar-upload");
    private final By buttonCancel = By.id("profile-picture-cancel");

    @Step
    @Override
    public boolean isOpened() {
        return isElementVisible(body);
    }

    @Step
    public DropzonePopup clickUploadNewPictureButton() {
        return openPageByClick(buttonUploadNewPicture, DropzonePopup.class);
    }

    @Step
    public EditProfilePage clickCancelButton() {
        return openPageByClick(buttonCancel, EditProfilePage.class);
    }
}
