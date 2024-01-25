package utils;

import io.qameta.allure.Step;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.log4j.Logger;

public class StringMan {

    private static final Logger log = Logger.getLogger(StringMan.class);

    public StringMan() {
    }

    @Step
    public static String getRandomString(int length) {
        String generatedRandomText = RandomStringUtils.random(length, true, true);
        log.info("Generated random string: [" + generatedRandomText + "]");
        return generatedRandomText;
    }
}
