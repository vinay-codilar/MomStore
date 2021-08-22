package com.momstore.pageModels;

import com.momstore.project_setup.TestNGBase;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.concurrent.TimeUnit;

public class LoginModel extends TestNGBase {

    @FindBy(css = "fieldset.login .field.note")
    private WebElement signInTitle;
    @FindBy(css = ".actions-toolbar div[class='primary'] a")
    private WebElement createAccountLink;
    @FindBy(id = "email")
    private WebElement emailId;
    @FindBy(id = "pass")
    private WebElement password;
    @FindBy(id = "send2")
    private WebElement submit;
    @FindBy(css = ".action.remind")
    private WebElement forgotPassword;
    @FindBy(css = ".greet.welcome .logged-in")
    private WebElement userName;

    /**
     * @param driver - Webdriver element
     */
    public LoginModel(WebDriver driver) {
        PageFactory.initElements(driver, this);
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
    }

    /**
     * @return WebElement - Sign in Title
     */
    public WebElement getSignInTitle() {
        return signInTitle;
    }

    /**
     * @return WebElement - Create Account Link
     */
    public WebElement getCreateAccountLink() {
        return createAccountLink;
    }

    /**
     * @return WebElement - Email Field
     */
    public WebElement getEmailId() {
        return emailId;
    }

    /**
     * @return WebElement - Password field
     */
    public WebElement getPassword() {
        return password;
    }

    /**
     * @return WebElement - Submit button
     */
    public WebElement getSubmit() {
        return submit;
    }

    /**
     * @return WebElement - Forgot Password
     */
    public WebElement getForgotPassword() {
        return forgotPassword;
    }

    /**
     * @return WebElement - Username field
     */
    public WebElement getUserName() {
        return userName;
    }

}
