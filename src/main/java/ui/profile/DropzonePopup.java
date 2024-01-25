package ui.profile;

import core.PopupBase;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

import static utils.DriverUtils.createInstanceOfOpenable;
import static utils.DriverUtils.isElementVisible;
import static utils.DriverUtils.openPageByClick;
import static utils.DriverUtils.sendFileInput;

public class DropzonePopup extends PopupBase {

    private final By body = By.xpath("//div[@class='modal-dropzone']");
    private final By inputFile = By.xpath("//input[contains(@class,'modal-input-file')]");
    private final By buttonAddPicture = By.xpath("//button[contains(@class,'modal-cta-submit')]");

    @Step
    @Override
    public boolean isOpened() {
        return isElementVisible(body);
    }

    @Step
    public DropzonePopup uploadDocuments(String filesPath) {
        sendFileInput(inputFile, filesPath);
        return createInstanceOfOpenable(DropzonePopup.class);
    }

    @Step
    public EditProfilePage clickAddPictureButton() {
        return openPageByClick(buttonAddPicture, EditProfilePage.class, AJAX_LOADER);
    }
}
