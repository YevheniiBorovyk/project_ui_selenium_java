package utils;

import static utils.PropertiesUtils.getParameter;

public class MavenPropertiesUI {

    public static int getMaxRetryCount() {
        return Integer.parseInt(getParameter("maxRetryCount", false));
    }

    public static String getBrowser() {
        return getParameter("browser", false);
    }
}
