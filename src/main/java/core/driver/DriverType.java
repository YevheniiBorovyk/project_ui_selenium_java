package core.driver;

import io.github.bonigarcia.wdm.WebDriverManager;
import lombok.extern.log4j.Log4j;
import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.UnexpectedAlertBehaviour;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.util.HashMap;
import java.util.logging.Level;

@Log4j
public enum DriverType implements DriverSetup {
    CHROME {
        public RemoteWebDriver getWebDriverObject(DesiredCapabilities capabilities) {
            WebDriverManager.chromedriver()
                    .setup();

            HashMap<String, Object> chromePreferences = new HashMap<>();
            chromePreferences.put("profile.default_content_settings.popups", 0);
            chromePreferences.put("profile.default_content_setting_values.automatic_downloads", 1);
            chromePreferences.put("profile.default_content_setting_values.notifications", 2); // disable notifications
            chromePreferences.put("profile.password_manager_enabled", false); // no asking to save ui.login details
            chromePreferences.put("plugin.state.flash", 0);

            ChromeOptions options = new ChromeOptions();
            options.addArguments("--test-type");
            options.addArguments("disable-infobars");
            options.addArguments("--disable-notifications");
            options.addArguments("--disable-site-isolation-trials");
            options.addArguments("--disable-popup-blocking");
            options.addArguments("--remote-allow-origins=*");
            options.addArguments("--disable-plugins");
            options.addArguments("--start-maximized");
            options.addArguments("--user-agent=" + new UserAgentInfo() + " hello:)");
            options.setPageLoadStrategy(PageLoadStrategy.NONE);
            LoggingPreferences logPrefs = new LoggingPreferences();
            logPrefs.enable(LogType.BROWSER, Level.ALL);
            logPrefs.enable(LogType.PERFORMANCE, Level.ALL);
            options.setCapability("goog:loggingPrefs", logPrefs);

            DesiredCapabilities desiredCapabilities = defaultCapabilities(capabilities, options);
            options.merge(desiredCapabilities);
            options.addArguments("--no-default-browser-check"); // no asking to make chrome default
            options.setExperimentalOption("prefs", chromePreferences);
            log.info("Instantiating Chrome WebDriver object");
            return new ChromeDriver(options);
        }

        private DesiredCapabilities defaultCapabilities(DesiredCapabilities desiredCapabilities,
                ChromeOptions options) {
            desiredCapabilities.setCapability(CapabilityType.UNHANDLED_PROMPT_BEHAVIOUR,
                    UnexpectedAlertBehaviour.ACCEPT);
            desiredCapabilities.setCapability(CapabilityType.ACCEPT_INSECURE_CERTS, true);
            desiredCapabilities.setCapability(ChromeOptions.CAPABILITY, options);
            return desiredCapabilities;
        }
    },
    FIREFOX {
        public RemoteWebDriver getWebDriverObject(DesiredCapabilities capabilities) {
            WebDriverManager.firefoxdriver()
                    .setup();
            FirefoxOptions firefoxOptions = new FirefoxOptions();
            firefoxOptions.setPageLoadStrategy(PageLoadStrategy.NORMAL);
            return new FirefoxDriver(firefoxOptions);
        }
    }
}
