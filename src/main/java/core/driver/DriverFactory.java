package core.driver;

import core.AllureAttachments;
import io.qameta.allure.Step;
import lombok.extern.log4j.Log4j;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.awt.*;

import static data.TestData.DEFAULT_SCREEN_SIZE;
import static data.TestData.RequiredDataForTest.screenSize;
import static utils.MavenPropertiesUI.getBrowser;

@Log4j
public class DriverFactory {

    private final DriverType selectedDriverType;
    private RemoteWebDriver webDriver;

    public DriverFactory() {
        selectedDriverType = convertBrowserStringToDriverType(getBrowser());
    }

    private DriverType convertBrowserStringToDriverType(String browserName) {
        try {
            return DriverType.valueOf(browserName.toUpperCase());
        } catch (IllegalArgumentException | NullPointerException e) {
            log.error("Invalid browser specified, defaulting to CHROME...", e);
            return DriverType.CHROME;
        }
    }

    public synchronized RemoteWebDriver getInstantiatedDriver() {
        if (null == webDriver) {
            instantiateWebDriver(selectedDriverType);
        }
        return webDriver;
    }

    public synchronized RemoteWebDriver getDriver() {
        return webDriver;
    }

    public synchronized void quitDriver() {
        if (null != webDriver) {
            webDriver.quit();
            webDriver = null;
        }
    }

    @Step
    private synchronized void instantiateWebDriver(DriverType driverType) {
        log.info("Current Thread: " + Thread.currentThread()
                .getId());

        DesiredCapabilities desiredCapabilities = new DesiredCapabilities();

        webDriver = driverType.getWebDriverObject(desiredCapabilities);

        int width;
        int height;
        if (screenSize.equals(DEFAULT_SCREEN_SIZE)) {
            Toolkit toolkit = Toolkit.getDefaultToolkit();
            width = (int) toolkit.getScreenSize()
                    .getWidth();
            height = (int) toolkit.getScreenSize()
                    .getHeight();
        } else {
            width = Integer.parseInt(screenSize.split("x")[0]);
            height = Integer.parseInt(screenSize.split("x")[1]);
        }
        webDriver.manage()
                .window()
                .setSize(new Dimension(width, height));
        webDriver.manage()
                .window()
                .setPosition(new Point(0, 0));
        String dimension = width + "x" + height;
        log.info("Test Screen Size: [" + dimension + "]");
        AllureAttachments.jsonAttachment(dimension, "screenSize");
    }
}
