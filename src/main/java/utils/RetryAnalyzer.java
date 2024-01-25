package utils;

import lombok.extern.log4j.Log4j;
import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

import static utils.MavenPropertiesUI.getMaxRetryCount;

@Log4j
public class RetryAnalyzer implements IRetryAnalyzer {

    private final int maxRetryCount = getMaxRetryCount();
    private int retryCount = 0;

    private String getResultStatusName(int status) {
        String resultName;
        switch (status) {
            case 1:
                resultName = "SUCCESS";
                break;
            case 2:
                resultName = "FAILURE";
                break;
            case 3:
                resultName = "SKIP";
                break;
            default:
                resultName = "undefined";
                break;
        }
        return resultName;
    }

    @Override
    public boolean retry(ITestResult result) {
        if (!result.isSuccess()) {
            if (retryCount < maxRetryCount) {
                log.info("Retrying test " + result.getName() + " with status '" +
                        getResultStatusName(result.getStatus()) + "' for the " + (retryCount + 1) + " time(s).");
                result.setStatus(ITestResult.SKIP);
                retryCount++;
                return true;
            } else {
                result.setStatus(ITestResult.FAILURE);
            }
        } else {
            result.setStatus(ITestResult.SUCCESS);
        }
        return false;
    }
}
