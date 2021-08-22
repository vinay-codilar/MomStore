package com.momstore;

import com.momstore.extent_reports.ExtentReport;
import com.momstore.loggers.Loggers;
import com.momstore.pageModels.HeaderModel;
import com.momstore.pageModels.ListingModel;
import com.momstore.pageModels.ProductModel;
import com.momstore.pageModels.SearchListingModel;
import com.momstore.project_setup.TestNGBase;
import com.momstore.utilities.ExcelUtils;
import com.momstore.utilities.Property;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.Objects;

public class ProductDetails extends TestNGBase {
    public WebDriver driver;
    WebDriverWait wait;

    @BeforeClass(description = "Pre Test Configurations", alwaysRun = true)
    public void preTestRuns() {
        // Initialize Driver
        driver = initializeDriver();
        wait = new WebDriverWait(driver, 30);

        // Setting the Loggers and Extent Reports
        Loggers.setLogger(UserAccounts.class.getName());
        ExtentReport.createTest("Verify Product Details");

        // Getting the Website Details
        ExcelUtils.excelConfigure(WEBSITE_SAMPLE_DATA);
        ExcelUtils.getRowData(Integer.parseInt(Objects.requireNonNull(Property.getProperty("testRow"))));
    }

    /**
     * Verifying the Product details with the listing page details
     */
    @Test(description = "Verify the Product Details in PDP", priority = 1, groups = {"productDetails.verifyDetails"})
    public void verifyProductDetails() {
        // Setting up the Extent Report
        ExtentReport.createNode("Verify Product Details");

        HeaderModel headerModel = new HeaderModel(driver);
        SearchListingModel searchListingModel = new SearchListingModel(driver);
        ListingModel listingModel = new ListingModel(driver);
        ProductModel productModel = new ProductModel(driver);

        // Search for products from Search Listing page
        headerModel.searchForProducts(searchListingModel, wait);

        searchListingModel.fetchProductDetails();

        // Clicking on the Product name
        searchListingModel.getProductItemLink().get(searchListingModel.getProductId()).click();
        Loggers.getLogger().info("Clicked on '" + searchListingModel.getSearchProductName() + "' product");
        ExtentReport.getExtentNode().pass("Clicked on '" + searchListingModel.getSearchProductName() + "' product");

        // Verifying Product name
        wait.until(ExpectedConditions.visibilityOf(productModel.getPdpProductName()));
        Assert.assertEquals(listingModel.getListProductName(), productModel.getPdpProductName().getText());
        Loggers.getLogger().info("Product name verified successfully");
        ExtentReport.getExtentNode().pass("Product name verified successfully");

        // Fetching the Final price
        productModel.setProductFinalPrice();

        // Verifying Product prices
        try {
            if (!searchListingModel.getSearchProductOldPrice().equals("") || searchListingModel.getSearchProductOldPrice() != null) {
                // Fetching the Old price
                productModel.setProductOldPrice(productModel.getPdpOldPrice().getText());

                Assert.assertEquals(searchListingModel.getSearchProductOldPrice(), productModel.getProductOldPrice());
                Assert.assertEquals(searchListingModel.getSearchProductFinalPrice(), productModel.getProductFinalPrice());
                Loggers.getLogger().info("Product old price and final price verified successfully");
                ExtentReport.getExtentNode().pass("Product old price and final price verified successfully");
            }
        } catch (Exception e) {
            Assert.assertEquals(searchListingModel.getSearchProductFinalPrice(), productModel.getProductFinalPrice());
            Loggers.getLogger().info("Product final price verified successfully");
            ExtentReport.getExtentNode().pass("Product final price verified successfully");
        }
    }


}
