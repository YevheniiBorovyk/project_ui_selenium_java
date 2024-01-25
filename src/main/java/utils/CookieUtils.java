package utils;

import data.TestData;
import io.qameta.allure.Step;
import lombok.extern.log4j.Log4j;
import org.openqa.selenium.Cookie;

import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import static core.BaseTest.getInstantiatedDriver;
import static data.Environment.DEV;
import static data.Environment.RC;
import static data.TestData.SN_TOKEN_NAME;

@Log4j
public final class CookieUtils {

    private static final String DOMAIN;

    static {
        if (DEV == TestData.RequiredDataForTest.environment) {
            DOMAIN = "";
        } else if (RC == TestData.RequiredDataForTest.environment) {
            DOMAIN = "";
        } else {
            DOMAIN = "";
        }
    }

    private CookieUtils() {
    }

    @Step
    public static Set<Cookie> getCookies() {
        return getInstantiatedDriver().manage()
                .getCookies();
    }

    @Step
    public static Cookie getCookieNamed(String name) {
        return getInstantiatedDriver().manage()
                .getCookieNamed(name);
    }

    @Step
    public static Cookie getCookie(String name) {
        Cookie cookie = getCookieNamed(name);
        return Optional.ofNullable(cookie)
                .orElseThrow(() -> new IllegalArgumentException(name + " cookie was not found"));
    }

    @Step
    public static void deleteCookie(Cookie cookieToDelete) {
        getInstantiatedDriver().manage()
                .deleteCookie(cookieToDelete);
    }

    @Step
    public static void deleteAllCookies() {
        getInstantiatedDriver().manage()
                .deleteAllCookies();
    }
}
