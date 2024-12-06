package com.pranavbale.pageObjects;

import com.pranavbale.abstractComponents.AbstractComponent;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class ConformationPage extends AbstractComponent {

    // declare a variable
    WebDriver driver;

    // initialize the variable
    public ConformationPage(WebDriver driver) {
        super(driver);
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    // get a webElement from the browser
    @FindBy(css = "h1")
    WebElement actualConformationMessage;

    // get a conformation text
    public String checkConformation() {
        return actualConformationMessage.getText();
    }

}