package utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Optional;
import java.util.Properties;

public class PropertiesUtils {

    private final static String paramFile = "config.properties";
    private static final Logger logger = LoggerFactory.getLogger(PropertiesUtils.class);

    public static String getParameter(String propName, boolean isMandatory) {
        String system = System.getProperty(propName);
        logger.info("System Property " + propName + ": " + system);
        if (system == null) {
            Properties properties;
            try {
                properties = getPropertiesFromTestParam();
            } catch (IOException | NullPointerException e) {
                if (isMandatory) {
                    throw new RuntimeException(
                            "Cannot find properties file: " + paramFile + ". Mandatory parameter [" + propName +
                                    "]. Update configuration of the Jenkins job.", e);
                } else {
                    logger.info("Properties file not found. Default value for optional parameter [" + propName +
                            "] was set.");
                    return system;
                }
            }

            Optional<String> propFromTestParamFile = Optional.ofNullable(properties.getProperty(propName));

            if (propFromTestParamFile.isPresent()) {
                System.setProperty(propName, propFromTestParamFile.get());
                system = propFromTestParamFile.get();
            } else if (isMandatory) {
                throw new RuntimeException("Cannot find mandatory parameter [" + propName + "] in " + paramFile + ".");
            } else {
                logger.info("Optional parameter [" + propName + "] was not found in " + paramFile +
                        ". Default value was set.");
            }
        }
        return system;
    }

    public static String getParameter(String propName) {
        return getParameter(propName, true);
    }

    private static Properties getPropertiesFromTestParam() throws IOException {
        Properties properties = new Properties();
        String file = PropertiesUtils.class.getClassLoader()
                .getResource(paramFile)
                .getFile();
        properties.load(new FileInputStream(file));
        return properties;
    }

    public static String getEnv() {
        return getParameter("mvn.test.env");
    }
}
