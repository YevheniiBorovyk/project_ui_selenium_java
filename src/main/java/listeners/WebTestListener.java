package listeners;

import core.AllureAttachments;
import io.qameta.allure.Allure;
import lombok.extern.log4j.Log4j;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.logging.LogType;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.TestListenerAdapter;

import java.util.stream.Collectors;

import static core.BaseTest.getInstantiatedDriver;
import static core.driver.DriverBase.getDriver;
import static data.TestData.LOGGER_INDENT;
import static utils.CookieUtils.getCookies;
import static utils.DriverUtils.getCurrentURL;

@Log4j
public class WebTestListener extends TestListenerAdapter {

    @Override
    public void onTestSuccess(ITestResult result) {
        log.info("\n\nTEST RESULT: SUCCESS\n");
    }

    @Override
    public void onTestFailure(ITestResult result) {
        collectTestData();
        log.error("\n\nTEST RESULT: FAILED\n");
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        collectTestData();
        log.warn("SKIPPING TEST: " + result.getName() + LOGGER_INDENT);
        log.warn("\n\nTEST RESULT: SKIPPED\n");
    }

    @Override
    public void onStart(ITestContext result) {
        log.info("STARTING TEST: " + result.getName() + LOGGER_INDENT);
    }

    @Override
    public void onFinish(ITestContext result) {
        long usedMemory = (Runtime.getRuntime()
                .totalMemory() - Runtime.getRuntime()
                .freeMemory()) / (1024 * 1024);
        long freeMemory = (Runtime.getRuntime()
                .maxMemory() / (1024 * 1024)) - usedMemory;
        log.info("Used memory: " + usedMemory + " MB after finishing '" + result.getName() + "' test");
        log.info("Free memory: " + freeMemory + " MB");
        log.info("FINISHING TEST: " + result.getName() + LOGGER_INDENT);
    }

    @Override
    public void onTestStart(ITestResult result) {
        log.info("TEST METHOD NAME: " + result.getName() + LOGGER_INDENT);
    }

    @Override
    public void onConfigurationFailure(ITestResult result) {
        collectTestData();
        log.error("\n\nTEST RESULT: CONFIGURATION FAILED\n");
    }

    private void collectTestData() {
        WebDriver driver = getDriver();
        if (driver != null) {
            AllureAttachments.attachScreenshot("failureScreenshot");
            Allure.addAttachment("Current URL", getCurrentURL());
            Allure.addAttachment("Browser Cookies", getCookies().stream()
                    .map(cookie -> cookie.getName() + " : " + cookie.getValue() + "\n")
                    .collect(Collectors.joining()));
            String consoleLogs = getInstantiatedDriver().manage()
                    .logs()
                    .get(LogType.BROWSER)
                    .getAll()
                    .stream()
                    .map(logEntry -> logEntry.toJson() + "\n")
                    .collect(Collectors.joining());
            Allure.addAttachment("Browser Console Logs", consoleLogs);
        }
    }
}
