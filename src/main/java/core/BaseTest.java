package core;

import core.driver.DriverBase;
import io.qameta.allure.Step;
import listeners.WebTestListener;
import lombok.extern.log4j.Log4j;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.IConfigurable;
import org.testng.IConfigureCallBack;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Listeners;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import ui.login.LoginPage;

import static core.driver.DriverBase.getDriver;
import static data.Environment.getEnvironment;
import static data.TestData.DEFAULT_SCREEN_SIZE;
import static data.TestData.LOGGER_INDENT;
import static data.TestData.RequiredDataForTest;
import static data.TestData.RequiredDataForTest.environment;
import static data.TestData.RequiredDataForTest.testName;
import static data.URLs.getLoginPageURL;
import static utils.DriverUtils.openPageByURL;
import static utils.MavenPropertiesUI.getMaxRetryCount;

@Listeners({
        WebTestListener.class
})
@Log4j
public abstract class BaseTest implements IConfigurable {

    public static RemoteWebDriver getInstantiatedDriver() {
        return DriverBase.getInstantiatedDriver();
    }

    @Step
    public static void deleteAllCookies() {
        DriverBase.clearCookies();
    }

    @BeforeSuite(alwaysRun = true)
    protected static void beforeSuiteSetup() {
        log.info("Before suite steps".toUpperCase() + LOGGER_INDENT);
    }

    @Override
    public void run(IConfigureCallBack callBack, ITestResult testResult) {
        int attempts = getMaxRetryCount();
        for (int attempt = 0; attempt <= attempts; attempt++) {
            callBack.runConfigurationMethod(testResult);
            if (testResult.getThrowable() == null) {
                break;
            } else {
                if (attempt == 0 && getDriver() != null) {
                    deleteAllCookies();
                }
            }
        }
    }

    @BeforeMethod(alwaysRun = true)
    @Parameters({
            "screenSize"
    })
    protected void baseBeforeMethodSetup(ITestContext testContext, @Optional(DEFAULT_SCREEN_SIZE) String screenSize) {
        log.info("Before method steps".toUpperCase() + LOGGER_INDENT);

        DriverBase.instantiateDriverObject();
        testName.set(testContext.getCurrentXmlTest()
                .getName());
        RequiredDataForTest.environment = getEnvironment();
        RequiredDataForTest.screenSize = screenSize;
        log.info("Environment: [" + environment + "]");
    }

    @Step
    protected LoginPage openLoginPage() {
        return openPageByURL(getLoginPageURL(), LoginPage.class);
    }

    @AfterSuite(alwaysRun = true)
    protected void tearDown() {
        log.info("After suite steps".toUpperCase() + LOGGER_INDENT);
    }

    @AfterMethod(alwaysRun = true)
    protected void closeDriverSession() {
        log.info("After method steps".toUpperCase() + LOGGER_INDENT);
        DriverBase.closeCurrentBrowser();
    }
}
