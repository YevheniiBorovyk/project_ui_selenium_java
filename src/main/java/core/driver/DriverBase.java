package core.driver;

import lombok.extern.log4j.Log4j;
import org.openqa.selenium.remote.RemoteWebDriver;
import utils.CookieUtils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

@Log4j
public class DriverBase {

    private static final ThreadLocal<DriverFactory> driverThread = new ThreadLocal<>();

    public static void instantiateDriverObject() {
        log.info("Creating webDriverThreadPool for webDriver objects");
        driverThread.set(new DriverFactory());
    }

    public static RemoteWebDriver getInstantiatedDriver() {
        try {
            return driverThread.get()
                    .getInstantiatedDriver();
        } catch (NullPointerException e) {
            log.error("Instantiated driver thread is null");
        }
        return null;
    }

    public static RemoteWebDriver getDriver() {
        try {
            return driverThread.get()
                    .getDriver();
        } catch (NullPointerException e) {
            log.error("Driver thread is null");
        }
        return null;
    }

    public static void clearCookies() {
        log.info("Deleting all cookies");
        CookieUtils.deleteAllCookies();
    }

    public static void closeCurrentBrowser() {
        log.info("Closing driver object");
        driverThread.get()
                .quitDriver();
    }
}
