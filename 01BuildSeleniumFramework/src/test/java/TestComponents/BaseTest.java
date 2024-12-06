package TestComponents;

import com.pranavbale.pageObjects.LandingPage;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Properties;

public class BaseTest {

    // initialize the WebDriver
    public WebDriver driver;
    public LandingPage landingPage;


    public WebDriver initializeDriver() throws IOException {

        // get the data form the Global.properties file
        Properties properties = new Properties();
        Path path = Paths.get("src/main/java/com/pranavbale/resources/GlobalData.properties");
        properties.load(Files.newInputStream(path));
        // check if terminal send a specific browser
        // if browser is not send by terminal then use a properties browser
        String browserName = System.getProperty("browser") != null ?
                System.getProperty("browser") :
                properties.getProperty("browser");

        // declare a driver
        if (browserName.equalsIgnoreCase("chrome"))
            driver = new ChromeDriver();
        else if (browserName.equalsIgnoreCase("Safari"))
            driver = new SafariDriver();

        // manage driver
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.manage().window().maximize();

        return driver;
    }

    // launch a browser before executing a method
    @BeforeMethod(alwaysRun = true)
    public LandingPage launchApplication() throws IOException {
        initializeDriver();
        return landingPage = new LandingPage(driver);
    }

    // close the browser after executing a method
    @AfterMethod(alwaysRun = true)
    public void closeBrowser() {
        driver.quit();
    }

    // capture a screen shot
    public String getAScreenShot(String testCaseName, WebDriver driver){
        // take a screenshot
        File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        try {
            FileUtils.copyFile(screenshot,new File("./screenshot/" + testCaseName + ".png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        //screenshot/loginErrorValidation.png
        return "../screenshot/" + testCaseName + ".png";
    }
}
