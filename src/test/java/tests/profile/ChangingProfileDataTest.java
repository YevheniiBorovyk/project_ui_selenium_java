package tests.profile;

import core.BaseTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import ui.leftsidebar.HomePage;
import ui.profile.ActivityProfilePage;
import ui.profile.EditProfilePage;
import utils.StringMan;

import static data.TestData.FilesToUpload.PNG_FILE;

public class ChangingProfileDataTest extends BaseTest {

    private HomePage homePage;

    @BeforeMethod
    public void setUp() {
        homePage = openLoginPage().fillEmail("eugenee.rabota@gmail.com")
                .fillPassword("Qwerty1234$")
                .clickLoginButton();
    }

    @Test
    public void checkChangingProfileData() {
        String location = StringMan.getRandomString(5);
        String title = StringMan.getRandomString(5);
        EditProfilePage editProfilePage = homePage.getTopBar()
                .clickProfileButton()
                .clickEditProfileButton()
                .fillLocation(location)
                .fillTitle(title)
                .clickChangePictureButton()
                .clickUploadNewPictureButton()
                .uploadDocuments(PNG_FILE)
                .clickAddPictureButton();
        ActivityProfilePage activityProfilePage = editProfilePage.clickSaveProfileButton();
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertTrue(activityProfilePage.isTitleContainsTo(title), "Title is not contains " + title);
        softAssert.assertTrue(activityProfilePage.isLocationContainsTo(location),
                "Location is not contains " + location);
        softAssert.assertAll();
    }
}
