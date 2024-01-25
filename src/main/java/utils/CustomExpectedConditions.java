package utils;

import org.openqa.selenium.By;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;

import java.util.List;

class CustomExpectedConditions {

    private CustomExpectedConditions() {
        throw new IllegalStateException("Utility class");
    }

    static ExpectedCondition<List<WebElement>> visibilityOfAllNestedElementsLocatedBy(final WebElement rootElement,
            final By childLocator) {
        return new ExpectedCondition<>() {

            @Override
            public List<WebElement> apply(WebDriver webDriver) {
                List<WebElement> allChildren = rootElement.findElements(childLocator);
                if (!allChildren.isEmpty() && allChildren.stream()
                        .allMatch(WebElement::isDisplayed)) {
                    return allChildren;
                }

                return null;
            }

            @Override
            public String toString() {
                return String.format("visibility of all child elements located by %s -> %s", rootElement, childLocator);
            }
        };
    }

    static ExpectedCondition<Boolean> click(final WebElement element) {
        return new ExpectedCondition<>() {
            String message = "";

            @Override
            public Boolean apply(WebDriver driver) {
                try {
                    element.click();
                    return true;
                } catch (ElementClickInterceptedException | StaleElementReferenceException e) {
                    message = e.getMessage();
                    return false;
                }
            }

            @Override
            public String toString() {
                return "click element: " + message;
            }
        };
    }

    static ExpectedCondition<Boolean> click(final By locator) {
        return new ExpectedCondition<>() {
            String message = "";

            @Override
            public Boolean apply(WebDriver driver) {
                try {
                    driver.findElement(locator)
                            .click();
                    return true;
                } catch (ElementClickInterceptedException | NoSuchElementException | StaleElementReferenceException e) {
                    message = e.getMessage();
                    return false;
                }
            }

            @Override
            public String toString() {
                return "click element: " + message;
            }
        };
    }

    static ExpectedCondition<String> getText(final By locator) {
        return new ExpectedCondition<>() {
            String message = "";

            @Override
            public String apply(WebDriver driver) {
                try {
                    return driver.findElement(locator)
                            .getText();
                } catch (StaleElementReferenceException e) {
                    message = e.getMessage();
                    return "";
                }
            }

            @Override
            public String toString() {
                return "get element text: " + message;
            }
        };
    }

    static ExpectedCondition<String> getText(final WebElement element) {
        return new ExpectedCondition<>() {
            String message = "";

            @Override
            public String apply(WebDriver driver) {
                try {
                    return element.getText();
                } catch (StaleElementReferenceException e) {
                    message = e.getMessage();
                    return "";
                }
            }

            @Override
            public String toString() {
                return "get element text: " + message;
            }
        };
    }
}
