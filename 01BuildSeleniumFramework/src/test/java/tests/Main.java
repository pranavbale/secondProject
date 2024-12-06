package tests;

import TestComponents.BaseTest;
import com.pranavbale.pageObjects.*;
import data.DataReader;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;

public class Main extends BaseTest {

    // create some global variable
    String URL = "https://rahulshettyacademy.com/client/";

    // this test is for following reason
    // 1. login the application
    // 2. add product in to the card
    // 3. move to card
    // 4. make a translation
    // 5. place an order
    // 6. order conformation
    @Test(dataProvider = "getData", groups = {"Purchase"})
    public void submitOrder(HashMap<String, String> data) throws IOException {

        // enter username and password
        String username = data.get("username");
        String password = data.get("password");
        String selectProduct = data.get("selectProduct");

        String country = "India";
        String expectedConformationMessage = "Thankyou for the order.";

        // working with launchPage
        ProductCatalogue productCatalogue = landingPage.loginApp(URL, username, password);

        // add product in to the card
//        List<WebElement> products = productCatalogue.getProductList();
//        WebElement product = productCatalogue.getProductByName(selectProduct);
        productCatalogue.addProductToCard(selectProduct);
        CartPage cartPage = productCatalogue.moveToCard();

        // verify the card product and move to payment and conformation
        boolean match = cartPage.VerifyProductDisplay(selectProduct);
        // NO Assertion should be return in to the pageObject
        // because at the time of error it hard to find error
        Assert.assertTrue(match);
        CheckoutPage checkoutPage = cartPage.goToCheckout();

        // make the payment and move to conformation page
        checkoutPage.selectCountry(country);
        ConformationPage conformationPage = checkoutPage.submit();

        // check the conformation page
        Assert.assertEquals(conformationPage.checkConformation(), expectedConformationMessage.toUpperCase());

    }


    // take a screenshot
    public void takeScreenShot(WebDriver driver) {
        // take a screenshot
        File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        LocalDateTime localDateTime = LocalDateTime.now();
        localDateTime.toString().replace(":", "-").replace(".", "-").trim();
        try {
            FileUtils.copyFile(screenshot,new File("./screenshot/" + localDateTime + ".png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    // provide data for the execution of program
    @DataProvider
    public Object[][] getData() throws IOException {
//        HashMap<String, String> data1 = new HashMap<>();
//        data1.put("username", "pranavbale99@gmail.com");
//        data1.put("password", "Pranav@123");
//        data1.put("selectProduct", "ADIDAS ORIGINAL");
//
//        HashMap<String, String> data2 = new HashMap<>();
//        data2.put("username", "pranavbale@gmail.com");
//        data2.put("password", "Pranav@1234");
//        data2.put("selectProduct", "IPHONE 13 PRO");

        var data = new DataReader().getJsonDataToMap();
        return new Object[][]{{data.get(0)}, {data.get(1)}};
    }
}