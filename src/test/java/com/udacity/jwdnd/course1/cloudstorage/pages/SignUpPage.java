package com.udacity.jwdnd.course1.cloudstorage.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class SignUpPage {

    @FindBy(id="inputFirstName")
    private WebElement inputFirstName;

    @FindBy(id="inputLastName")
    private WebElement inputLastName;

    @FindBy(id="inputUsername")
    private WebElement inputUsername;

    @FindBy(id="inputPassword")
    private WebElement inputPassword;

    @FindBy(id="submit-button")
    private WebElement submitButton;

    public SignUpPage(WebDriver driver){
        PageFactory.initElements(driver,this);
    }

    public void signup(String firstName, String lassName, String userName, String password){
        inputFirstName.sendKeys(firstName);
        inputLastName.sendKeys(lassName);
        inputUsername.sendKeys(userName);
        inputPassword.sendKeys(password);
        submitButton.click();
    }
}
