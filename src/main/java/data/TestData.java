package data;

import java.io.File;

import static utils.PropertiesUtils.getEnv;

public class TestData {

    public static final String SN_TOKEN_NAME = "SN_TOKEN";
    public static final Environment ENVIRONMENT = getEnvironment();
    public static final String DEFAULT_SCREEN_SIZE = "autoScreenSizeRecognition";

    public static final String LOGGER_INDENT = "\n===============================================";

    private static Environment getEnvironment() {
        String envParameter = getEnv();
        try {
            return Environment.valueOf(envParameter.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new ExceptionInInitializerError("Not supported environment format: [" + envParameter + "].\n" +
                    "Please use one of these: prod / rc / dev");
        }
    }

    public static class FilesToUpload {

        public static final String PNG_FILE = new File("src/test/resources/files/cat.jpeg").getAbsolutePath();

        private FilesToUpload() {
        }
    }

    public static class RequiredDataForTest {

        public static Environment environment;
        public static String screenSize;
        public static String selenoidURL;
        public static String sUser = System.getProperty("sUser");
        public static String sPassword = System.getProperty("sPassword");
        public static ThreadLocal<String> testName = new ThreadLocal<>();
        public static ThreadLocal<String> sBrowser = new ThreadLocal<>();
        public static ThreadLocal<String> sBrowserVersion = new ThreadLocal<>();
        public static ThreadLocal<String> enableVideo = new ThreadLocal<>();
        public static ThreadLocal<String> sVideoName = new ThreadLocal<>();
    }
}
