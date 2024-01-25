package utils;

import core.BaseTest;
import core.Openable;
import io.qameta.allure.Step;
import lombok.extern.log4j.Log4j;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.WebDriverWait;

import static java.time.Duration.ofMillis;
import static java.time.Duration.ofSeconds;
import static org.openqa.selenium.support.ui.ExpectedConditions.attributeToBe;
import static org.openqa.selenium.support.ui.ExpectedConditions.elementToBeClickable;
import static org.openqa.selenium.support.ui.ExpectedConditions.invisibilityOf;
import static org.openqa.selenium.support.ui.ExpectedConditions.invisibilityOfElementLocated;
import static org.openqa.selenium.support.ui.ExpectedConditions.or;
import static org.openqa.selenium.support.ui.ExpectedConditions.presenceOfElementLocated;
import static org.openqa.selenium.support.ui.ExpectedConditions.textToBePresentInElementLocated;
import static org.openqa.selenium.support.ui.ExpectedConditions.textToBePresentInElementValue;
import static org.openqa.selenium.support.ui.ExpectedConditions.visibilityOfElementLocated;
import static utils.Check.checkTrue;

@Log4j
public class DriverUtils {

    private static final long DEFAULT_WAIT = 15L;

    private DriverUtils() {
    }

    private static WebDriver getDriver() {
        return BaseTest.getInstantiatedDriver();
    }

    private static WebDriverWait getDefaultWait() {
        return new WebDriverWait(getDriver(), ofSeconds(DEFAULT_WAIT));
    }

    private static WebDriverWait getCustomWait(long waitInSeconds) {
        return getCustomWait(waitInSeconds, 500L);
    }

    private static WebDriverWait getCustomWait(long waitInSeconds, long pollingWaitInMillis) {
        return new WebDriverWait(getDriver(), ofSeconds(waitInSeconds), ofMillis(pollingWaitInMillis));
    }

    private static Actions getActions() {
        return new Actions(getDriver());
    }

    @Step
    public static <T extends Openable> T createInstanceOfOpenable(Class<T> expectedPage) {
        log.info("Creating instance of Openable [" + expectedPage.getSimpleName() + "]");
        try {
            T instance = expectedPage.getConstructor()
                    .newInstance();
            checkTrue(instance.isOpened(), instance.getClass()
                    .getSimpleName() + " is not opened");
            return instance;
        } catch (ReflectiveOperationException e) {
            throw new UnsupportedOperationException("Cannot create instance of " + expectedPage.getSimpleName(), e);
        }
    }

    @Step
    public static <T extends Openable> T createInstanceOfOpenable(Class<T> expectedPage, By... spinnersLocatorsToWait) {
        log.info("Creating instance of Openable [" + expectedPage.getSimpleName() + "]");
        try {
            T instance = expectedPage.getConstructor()
                    .newInstance();
            waitUntilElementsDisappearIfVisible(spinnersLocatorsToWait);
            checkTrue(instance.isOpened(), instance.getClass()
                    .getSimpleName() + " is not opened");
            return instance;
        } catch (ReflectiveOperationException e) {
            throw new UnsupportedOperationException("Cannot create instance of " + expectedPage.getSimpleName(), e);
        }
    }

    @Step
    public static WebElement findElement(By locator) {
        return getDefaultWait().until(visibilityOfElementLocated(locator));
    }

    @Step
    public static WebElement findElementInDOM(By locator) {
        return getDefaultWait().until(presenceOfElementLocated(locator));
    }

    @Step
    public static boolean isElementVisible(By locator) {
        try {
            findElement(locator);
            return true;
        } catch (TimeoutException e) {
            log.error(e.getMessage());
            return false;
        }
    }

    @Step
    public static boolean isElementVisible(By locator, long timeOutInSeconds, long pollingWaitInMillis) {
        try {
            getCustomWait(timeOutInSeconds, pollingWaitInMillis).until(visibilityOfElementLocated(locator));
            return true;
        } catch (TimeoutException e) {
            log.error(e.getMessage());
            return false;
        }
    }

    @Step
    public static boolean isElementNotVisible(WebElement element) {
        try {
            getDefaultWait().until(invisibilityOf(element));
            return true;
        } catch (TimeoutException e) {
            log.error(e.getMessage());
            return false;
        }
    }

    @Step
    public static boolean isElementNotVisible(By locator, long timeOutInSeconds, long pollingWaitInMillis) {
        try {
            getCustomWait(timeOutInSeconds, pollingWaitInMillis).until(invisibilityOfElementLocated(locator));
            return true;
        } catch (TimeoutException e) {
            log.error(e.getMessage());
            return false;
        }
    }

    @Step
    public static void getURL(String url) {
        log.info("Getting URL [" + url + "]");
        getDriver().get(url);
    }

    @Step
    public static String getCurrentURL() {
        String currentURL = getDriver().getCurrentUrl();
        log.info("Getting current URL [" + currentURL + "]");
        return currentURL;
    }

    @Step
    public static void click(WebElement element) {
        getDefaultWait().until(elementToBeClickable(element));
        getDefaultWait().until(CustomExpectedConditions.click(element));
    }

    @Step
    public static void click(By locator) {
        getDefaultWait().until(elementToBeClickable(locator));
        getDefaultWait().until(CustomExpectedConditions.click(locator));
    }

    @Step
    public static void type(By locator, String text) {
        findElement(locator).sendKeys(text);
    }

    @Step
    public static void typeWithCheckingText(By locator, String text) {
        findElement(locator).clear();
        findElement(locator).sendKeys(text);
        getDefaultWait().until(
                or(textToBePresentInElementValue(locator, text), textToBePresentInElementLocated(locator, text)));
    }

    @Step
    public static void slowTypeWithCheckingText(By locator, String text) {
        findElement(locator).clear();
        for (char c : text.toCharArray()) {
            findElement(locator).sendKeys(Character.toString(c));
        }
        getDefaultWait().until(
                or(textToBePresentInElementValue(locator, text), textToBePresentInElementLocated(locator, text)));
    }

    @Step
    public static void sendKeys(Keys key) {
        getActions().sendKeys(key)
                .perform();
    }

    @Step
    public static String getText(By locator) {
        return getDefaultWait().until(CustomExpectedConditions.getText(locator));
    }

    @Step
    public static void sendFileInput(By locator, String filePath) {
        findElementInDOM(locator).sendKeys(filePath);
    }

    @Step
    public static boolean isElementContainsText(By locator, String text) {
        try {
            getDefaultWait().until(textToBePresentInElementLocated(locator, text));
            return true;
        } catch (TimeoutException e) {
            return false;
        }
    }

    @Step
    public static boolean isElementAttributeHasValue(By locator, String attribute, String value) {
        try {
            getDefaultWait().ignoring(StaleElementReferenceException.class)
                    .until(attributeToBe(locator, attribute, value));
            return true;
        } catch (TimeoutException e) {
            log.error(e.getMessage());
            return false;
        }
    }

    @Step
    public static <T extends Openable> T openPageByURL(String url, Class<T> expectedPage) {
        log.info("Go to: " + expectedPage.getSimpleName());
        getURL(url);
        return createInstanceOfOpenable(expectedPage);
    }

    @Step
    public static <T extends Openable> T openPageByClick(By locator, Class<T> expectedPage) {
        log.info("Go to: " + expectedPage.getSimpleName());
        click(locator);
        return createInstanceOfOpenable(expectedPage);
    }

    @Step
    public static <T extends Openable> T openPageByClick(By locator, Class<T> expectedPage,
            By... spinnersLocatorsToWait) {
        log.info("Go to: " + expectedPage.getSimpleName());
        click(locator);
        return createInstanceOfOpenable(expectedPage, spinnersLocatorsToWait);
    }

    @Step
    public static void waitUntilElementsDisappearIfVisible(By... locators) {
        waitUntilElementsDisappearIfVisible(2L, locators);
    }

    @Step
    public static void scrollTo(By locator) {
        executeJSScript("arguments[0].scrollIntoView({block: 'center'});", findElement(locator));
    }

    @Step
    public static Object executeJSScript(String script, Object... args) {
        return ((JavascriptExecutor) getDriver()).executeScript(script, args);
    }

    @Step
    public static void waitUntilElementsDisappearIfVisible(long waitForVisibilityTimeoutInSeconds, By... locators) {
        long time = 120L;
        log.info("Waiting [" + time + "] sec until element disappears");
        for (By locator : locators) {
            if (isElementVisible(locator, waitForVisibilityTimeoutInSeconds, 250L)) {
                isElementNotVisible(locator, time, 500L);
            }
        }
    }

    @Step
    public static void waitUntilPageLoaded() {
        getCustomWait(120, 50).withMessage("Page is not loaded: " + getCurrentURL())
                .until(d -> ((JavascriptExecutor) d).executeScript("return document.readyState")
                        .toString()
                        .equalsIgnoreCase("complete"));
    }

}
