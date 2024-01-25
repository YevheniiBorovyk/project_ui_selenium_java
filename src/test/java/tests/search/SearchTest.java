package tests.search;

import core.BaseTest;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ui.leftsidebar.HomePage;
import ui.search.SearchResultPage;

public class SearchTest extends BaseTest {

    private HomePage homePage;

    @BeforeMethod
    public void setUp() {
        homePage = openLoginPage().fillEmail("eugenee.rabota@gmail.com")
                .fillPassword("Qwerty1234$")
                .clickLoginButton();
    }

    @Test
    public void checkSearch() {
        SearchResultPage searchResultPage = homePage.getTopBar()
                .search("tag");
        Assert.assertTrue(searchResultPage.getTopBar()
                .isSearchInputHasValue("tag"), "Search input doesn't have value");
    }
}
