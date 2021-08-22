package com.momstore;

import com.momstore.extent_reports.ExtentReport;
import com.momstore.loggers.Loggers;
import com.momstore.pageModels.AccountModel;
import com.momstore.pageModels.HeaderModel;
import com.momstore.pageModels.LoginModel;
import com.momstore.pageModels.SignupModel;
import com.momstore.project_setup.TestNGBase;
import com.momstore.utilities.ExcelUtils;
import com.momstore.utilities.Property;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class UserAccounts extends TestNGBase {
    public WebDriver driver;
    WebDriverWait wait;
    FluentWait<WebDriver> fluent;

    /**
     * Setting up Loggers and Extent reports
     */
    @BeforeClass(description = "Pre Test Configurations", alwaysRun = true)
    public void preTestRuns() {
        // Initialize Driver
        driver = initializeDriver();
        wait = new WebDriverWait(driver, 10);
        fluent = new FluentWait<>(driver).withTimeout(10, TimeUnit.SECONDS).pollingEvery(500, TimeUnit.MILLISECONDS)
                .ignoring(Exception.class);

        // Setting the Loggers and Extent Reports
        Loggers.setLogger(UserAccounts.class.getName());
        ExtentReport.createTest("User Accounts");
        ExcelUtils.getRowData(Integer.parseInt(Objects.requireNonNull(Property.getProperty("testRow"))));
    }

    /**
     * Create New Account
     */
    @Test(description = "Creating the User Account", priority = 1, groups = {"userAccounts.accountCreate"})
    public void accountCreate() {
        // Setting up the Extent Report
        ExtentReport.createNode("Create User Account");

        // PageModel object
        HeaderModel headerModel = new HeaderModel(driver);
        SignupModel signupModel = new SignupModel(driver);
        AccountModel accountModel = new AccountModel(driver);
        LoginModel loginModel = new LoginModel(driver);

        // Click on Login link
        wait.until(ExpectedConditions.elementToBeClickable(headerModel.getLoginLink()));
        headerModel.getLoginLink().click();

        // Click on Create Account link
        wait.until(ExpectedConditions.elementToBeClickable(loginModel.getCreateAccountLink()));
        loginModel.getCreateAccountLink().click();

        Assert.assertEquals(signupModel.getPageTitle().getText(), ExcelUtils.getDataMap().get("create_title"));
        Loggers.getLogger().info("Redirected to Create Account page");
        ExtentReport.getExtentNode().pass("Redirected to Create Account page");

        // Filling the form
//        signupModel.getPhoneNumber().sendKeys(ExcelUtils.getDataMap().get("mobile_number"));
//        signupModel.getEmailAddress().sendKeys(ExcelUtils.getDataMap().get("email_id"));
        signupModel.getPhoneNumber().sendKeys("282675783");
        signupModel.getEmailAddress().sendKeys("index@gmail.com");

        Select select = new Select(signupModel.getPrefixTitle());

        select.selectByValue("Mr");
        signupModel.getFirstName().sendKeys(ExcelUtils.getDataMap().get("first_name"));
        signupModel.getLastName().sendKeys(ExcelUtils.getDataMap().get("last_name"));
        signupModel.getPassword().sendKeys(ExcelUtils.getDataMap().get("password"));
        signupModel.getPasswordConfirmation().sendKeys(ExcelUtils.getDataMap().get("password"));

        signupModel.getTermsCheckbox().click();
        signupModel.getSubmit().click();

        // Fetching the User First and Last name from Excel
        String firstName = ExcelUtils.getDataMap().get("first_name");

        // Verify if the account is created / not
        wait.until(ExpectedConditions.visibilityOf(signupModel.getMessages()));
        Assert.assertEquals(accountModel.getMessages().getText(), "Thank you for registering with Mom Store.");

        wait.until(ExpectedConditions.visibilityOf(accountModel.getUserName()));
        Assert.assertEquals(accountModel.getUserName().getText(), firstName);
        Loggers.getLogger().info("User Account Created Successfully");
        ExtentReport.getExtentNode().pass("User Account Created Successfully");
    }

    /**
     * Login to User Account
     */
    @Test(description = "Logging into the User Account", priority = 2, groups = {"userAccounts.accountLogin"})
    public void accountLogin() {
        // Setting up the Extent Report
        ExtentReport.createNode("Login to Account");

        // PageModel object
        HeaderModel headerModel = new HeaderModel(driver);
        LoginModel loginModel = new LoginModel(driver);
        AccountModel accountModel = new AccountModel(driver);

        // Click on Login link
        wait.until(ExpectedConditions.elementToBeClickable(headerModel.getLoginLink()));
        headerModel.getLoginLink().click();

        // Verify the sign in page title and link
        Assert.assertTrue(driver.getCurrentUrl().contains("customer/account/login/"));
        Assert.assertEquals(loginModel.getSignInTitle().getText(), ExcelUtils.getDataMap().get("signin_title"));
        Loggers.getLogger().info("Redirected to Login page");
        ExtentReport.getExtentNode().pass("Redirected to Login page");

        // Entering the form details
        loginModel.getEmailId().sendKeys(ExcelUtils.getDataMap().get("email_id"));
        loginModel.getPassword().sendKeys(ExcelUtils.getDataMap().get("password"));
        loginModel.getSubmit().click();

        String firstName = ExcelUtils.getDataMap().get("first_name");

        // Verifying if user is logged in
//        fluent.until(ExpectedConditions.textToBePresentInElement(headerModel.getAccountIcon(), firstName));
        wait.until(ExpectedConditions.visibilityOf(accountModel.getUserName()));
        Assert.assertEquals(accountModel.getUserName().getText(), firstName);
        Loggers.getLogger().info("User logged in Successfully");
        ExtentReport.getExtentNode().pass("User logged in Successfully");
    }

    /**
     * Logout of User Account
     */
    @Test(description = "Logout of the User Account", priority = 3, groups = {"userAccounts.accountLogout"}, dependsOnMethods = {"accountLogin"})
    public void accountLogout() {
        // Setting up the Extent Report
        ExtentReport.createNode("Logout of Account");

        // PageModel object
        HeaderModel headerModel = new HeaderModel(driver);
        AccountModel accountModel = new AccountModel(driver);

        Actions act = new Actions(driver);

        // Logout from the account
        if (driver.getCurrentUrl().contains("customer/account/") || driver.getCurrentUrl().contains("/")) {
//            act.moveToElement(headerModel.getAccountIcon()).pause(500).build().perform();
//            headerModel.getLogoutLink().click();
            accountModel.getLogoutLink().click();
            Assert.assertTrue(driver.getCurrentUrl().contains("customer/account/logout"));
            Loggers.getLogger().info("User is successfully Logged Out");
            ExtentReport.getExtentNode().pass("User is successfully Logged Out");
        } else {
            Loggers.getLogger().error("User is not Logged in");
            ExtentReport.getExtentNode().fail("User is not Logged in");
        }
    }

}
