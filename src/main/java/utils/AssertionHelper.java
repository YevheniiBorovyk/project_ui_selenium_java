package utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.testng.asserts.SoftAssert;

import static org.testng.Assert.fail;

@Log4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AssertionHelper {

    public static void handleError(Runnable method) {
        try {
            method.run();
        } catch (RuntimeException e) {
            log.error(e.getMessage());
            fail(e.getMessage());
        }
    }

    public static void handleError(Runnable method, String message) {
        try {
            method.run();
        } catch (RuntimeException e) {
            log.error(e.getMessage());
            fail(message + "\n Exception: " + e.getMessage());
        }
    }

    public static void handleSoftError(Runnable method, SoftAssert softAssert) {
        try {
            method.run();
        } catch (RuntimeException e) {
            softAssert.fail(e.getMessage());
        }
    }

    public static void handleSoftError(Runnable method, SoftAssert softAssert, String message) {
        try {
            method.run();
        } catch (RuntimeException e) {
            softAssert.fail(message + "\n Exception: " + e.getMessage());
        }
    }

}
