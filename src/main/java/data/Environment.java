package data;

import lombok.AllArgsConstructor;
import lombok.Getter;

import static utils.PropertiesUtils.getParameter;

@AllArgsConstructor
@Getter
public enum Environment {
    DEV(""),
    RC(""),
    PROD("https://stackoverflow.com");

    private final String host;

    public static Environment getEnvironment() {
        return Environment.valueOf(getParameter("mvn.test.env").toUpperCase());
    }
}
