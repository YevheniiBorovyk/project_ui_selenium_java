package utils;

import io.qameta.allure.Step;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j;
import okhttp3.Credentials;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.apache.http.HttpHeaders;
import org.openqa.selenium.remote.TracedCommandExecutor;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.io.IOException;

import static core.BaseTest.getInstantiatedDriver;
import static data.TestData.RequiredDataForTest.sPassword;
import static data.TestData.RequiredDataForTest.sUser;

@Log4j
public class SystemMan {

    @Step
    public static String getClipboardValue() {
        if (null != System.getProperty("browser") && System.getProperty("browser")
                .equalsIgnoreCase("selenoid")) {
            try (Response response = new OkHttpClient().newCall(new Request.Builder().url(
                                    getRemoteWebDriverURL() + "/clipboard/" + getInstantiatedDriver().getSessionId())
                            .header("content-type", "application/json")
                            .header(HttpHeaders.AUTHORIZATION, Credentials.basic(sUser, sPassword))
                            .build())
                    .execute()) {
                return response.body()
                        .string();
            } catch (IOException e) {
                throw new RuntimeException("Can't get clipboard content from selenoid container: " + e);
            }
        } else {
            String data = "";
            try {
                data = (String) Toolkit.getDefaultToolkit()
                        .getSystemClipboard()
                        .getData(DataFlavor.stringFlavor);
            } catch (Exception e) {
                log.warn("Get value from system clipboard exception caught: " + e);
            }
            return data;
        }
    }

    @Step
    public static void setClipboardValue(String clipboardString) {
        log.info("Set to clipboard string [" + clipboardString + "]");
        StringSelection selection = new StringSelection(clipboardString);
        Clipboard clipboard = Toolkit.getDefaultToolkit()
                .getSystemClipboard();
        try {
            clipboard.setContents(selection, selection);
        } catch (IllegalStateException e) {
            log.error(e.getMessage());
        }
    }

    @Step
    public static String getOsName() {
        return System.getProperty("os.name");
    }

    @SneakyThrows
    private static String getRemoteWebDriverURL() {
        try {
            java.lang.reflect.Field executor =
                    ((TracedCommandExecutor) getInstantiatedDriver().getCommandExecutor()).getClass()
                            .getDeclaredField("delegate");
            executor.setAccessible(true);
            Object executorValue = executor.get(getInstantiatedDriver().getCommandExecutor());
            java.lang.reflect.Field remoteServer = executorValue.getClass()
                    .getDeclaredField("remoteServer");
            remoteServer.setAccessible(true);
            Object remoteServerValue = remoteServer.get(executorValue);
            return remoteServerValue.toString()
                    .replace("/wd/hub", "");
        } catch (NoSuchFieldException | IllegalAccessException e) {
            log.error("There is a mistake while getting remote webdriver URL via reflection");
            log.error(e.getMessage());
        }
        throw new ReflectiveOperationException("There is a mistake while getting remote webdriver URL via reflection");
    }
}
