package com.pranavbale.pageObjects;

import com.pranavbale.abstractComponents.AbstractComponent;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class CheckoutPage extends AbstractComponent {

    // declare a variable
    WebDriver driver;

    // initialise the variable
    public CheckoutPage(WebDriver driver) {
        super(driver);
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    // get webElement from the browser
    @FindBy(css = "div.form-group input")
    WebElement inputCountry;

    @FindBy(css = "div.form-group section button")
    List<WebElement> countryList;

    @FindBy(css = "div.actions a")
    WebElement submit;

    // declare some variable
    By ByCountriesName = By.cssSelector("span");

    // select country from the list
    public void selectCountry(String country) {
        inputCountry.sendKeys(country);
        countryList.stream()
                .filter(coun -> coun.findElement(ByCountriesName).getText().equals(country))
                .findFirst()
                .orElse(null)
                .click();
    }

    // conform the order
    // move to conformation page
    public ConformationPage submit() {
        submit.click();
        return new ConformationPage(driver);
    }
}
