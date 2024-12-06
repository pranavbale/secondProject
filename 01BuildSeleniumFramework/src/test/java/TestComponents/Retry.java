package TestComponents;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

// this function is used to retry the test result
// if any test is fail then it retry again to conform
public class Retry implements IRetryAnalyzer {

    // declare same variable
    int count = 1;
    int maxValue = 2;

    @Override
    public boolean retry(ITestResult result) {

        if (count < maxValue) {
            count++;
            return true;
        }
        return false;
    }
}
