package TestComponents;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.pranavbale.resources.ExtentsReportNG;
import com.sun.net.httpserver.Authenticator;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.io.File;

public class Listeners extends BaseTest implements ITestListener {

    // create some variable
    ExtentReports reports = new ExtentsReportNG().getReportNG();
    ExtentTest test;

    // for making  a object a thread safe
    ThreadLocal<ExtentTest> threadLocal = new ThreadLocal<>();

    // the function is executed for every test execution stat
    @Override
    public void onTestStart(ITestResult result) {

        // create a extent report for each test
        test = reports.createTest(result.getMethod().getMethodName());
        // make a thread safe
        // add a new thread object to the threadLocal
        // it assign unique thread ID
        // the id store into the map
        threadLocal.set(test);
    }

    // for every test success the below method executed
    @Override
    public void onTestSuccess(ITestResult result) {

        // log a test report
        threadLocal.get().log(Status.PASS, "Test Pass");
    }

    // for every test failure the below method executed
    @Override
    public void onTestFailure(ITestResult result) {

        // log a test report
        // thread local check which thread id send a test
        // on that demand thread get fail
        // .getThrowable() is send a exception
        threadLocal.get().fail(result.getThrowable());

        // for each test there is different instance of driver
        // for gating a instance of driver user a result
        try {
            driver = (WebDriver) result                 // get a driver from the result
                    .getTestClass()                     // go to the test class of xml file
                    .getRealClass()                     // go to the real class
                    .getField("driver")           // get the driver filed
                    .get(result.getInstance());         // get a instance of driver
        } catch (IllegalAccessException | NoSuchFieldException e) {
            throw new RuntimeException(e);
        }
        String testName = result.getMethod().getMethodName();

        // take a screenShot and attached to the test
        // take screenshot
        String ssPath = getAScreenShot(testName, driver);

        File ssFile = new File(ssPath);
        if (!ssFile.exists()) {
            System.err.println("Screenshot not found at path: " + ssPath);
        }
        System.out.println(ssPath);
        // attached to the test
        threadLocal.get().addScreenCaptureFromPath(ssPath, testName);
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        ITestListener.super.onTestSkipped(result);
    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
        ITestListener.super.onTestFailedButWithinSuccessPercentage(result);
    }

    @Override
    public void onTestFailedWithTimeout(ITestResult result) {
        ITestListener.super.onTestFailedWithTimeout(result);
    }

    @Override
    public void onStart(ITestContext context) {
        ITestListener.super.onStart(context);
    }

    // execute at last of the execution
    @Override
    public void onFinish(ITestContext context) {

        // flash the extent report
        reports.flush();
    }

    @Override
    public boolean isEnabled() {
        return ITestListener.super.isEnabled();
    }
}
