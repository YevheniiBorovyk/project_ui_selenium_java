package data;

import static data.TestData.ENVIRONMENT;

public class URLs {

    private static final String PREFIX = ENVIRONMENT.getHost();
    private static final String LOGIN = "/users/login";

    public static String getLoginPageURL() {
        return PREFIX + LOGIN;
    }
}
