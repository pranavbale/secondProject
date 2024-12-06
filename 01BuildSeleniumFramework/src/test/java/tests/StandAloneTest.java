package tests;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.time.Duration;
import java.util.List;

public class StandAloneTest {

    public static void main(String[] args) {

        // manage the driver and launch website
        ChromeDriver driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.manage().window().maximize();
        driver.get("https://rahulshettyacademy.com/client/");

        // enter username and password
        String username = "pranavbale99@gmail.com";
        String password = "Pranav@123";
        String selectProduct = "ADIDAS ORIGINAL";
        String country = "India";
        String expectedConformationMessage = "Thankyou for the order.";

        // enter username and password then login
        driver.findElement(By.id("userEmail")).sendKeys(username);
        driver.findElement(By.id("userPassword")).sendKeys(password);
        driver.findElement(By.id("login")).click();

        // add product in to the card
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".mb-3")));
        List<WebElement> products = driver.findElements(By.className("mb-3"));
        WebElement product = products.stream()
                .filter(pro -> pro.findElement(By.cssSelector("b")).getText().equals(selectProduct))
                        .findFirst().orElse(null);
        product.findElement(By.cssSelector(".card-body button:last-of-type")).click();

        // check the product and move to card
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#toast-container")));
        wait.until(ExpectedConditions.invisibilityOf(driver.findElement(By.id("toast-container"))));
        driver.findElement(By.cssSelector("[routerlink*='cart']")).click();

        // verify the card product and move to payment anc conformation
        List<WebElement> cardProducts = driver.findElements(By.xpath("//div[@class='cartSection']/h3"));
        boolean isCorrectProduct = cardProducts.stream()
                .anyMatch(prod -> prod.getText().equals(selectProduct));
        Assert.assertTrue(isCorrectProduct);
        driver.findElement(By.cssSelector("div.cf ul li.totalRow button")).click();

        // make the payment and move to conformation page
        driver.findElement(By.cssSelector("div.form-group input")).sendKeys(country);
        List<WebElement> countryList = driver.findElements(By.cssSelector("div.form-group section button"));
        WebElement selectCountry = (WebElement) countryList.stream()
                .filter(coun -> coun.findElement(By.cssSelector("span")).getText().equals(country))
                .findFirst()
                .orElse(null);
        selectCountry.click();
        driver.findElement(By.cssSelector("div.actions a")).click();

        // check the conformation page
        Assert.assertEquals(driver.findElement(By.cssSelector("h1")).getText(), expectedConformationMessage.toUpperCase());

        // quit the driver
        driver.quit();
    }
}
