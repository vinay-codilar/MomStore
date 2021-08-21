package com.momstore.pageModels;

import com.momstore.project_setup.TestNGBase;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.concurrent.TimeUnit;

public class LoginModel extends TestNGBase {

<<<<<<< Updated upstream:MagentoCode/src/main/java/com/magento/pageModels/LoginModel.java
    @FindBy(xpath = "(//ul[@class='header links']/li/a)[1]")
    private WebElement login_link;
    @FindBy(xpath = "//div/input[@id='email']")
    private WebElement email_id;
    @FindBy(xpath = "(//div/input[@id='pass'])[1]")
=======
    @FindBy(css = "fieldset.login .field.note")
    private WebElement signInTitle;
    @FindBy(id = "email")
    private WebElement emailId;
    @FindBy(id = "pass")
>>>>>>> Stashed changes:MagentoCode/src/main/java/com/momstore/pageModels/LoginModel.java
    private WebElement password;
    @FindBy(id = "send2")
    private WebElement submit;
    @FindBy(css = ".action.remind")
    private WebElement forgotPassword;
    @FindBy(css = ".greet.welcome .logged-in")
    private WebElement user_name;

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
<<<<<<< Updated upstream:MagentoCode/src/main/java/com/magento/pageModels/LoginModel.java
    public WebElement getLogin_link() {
        return login_link;
=======
    public WebElement getSignInTitle() {
        return signInTitle;
>>>>>>> Stashed changes:MagentoCode/src/main/java/com/momstore/pageModels/LoginModel.java
    }

    /**
     * @return WebElement - Email Field
     */
    public WebElement getEmail_id() {
        return email_id;
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
    public WebElement getUser_name() {
        return user_name;
    }

}
