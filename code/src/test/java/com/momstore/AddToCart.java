package com.momstore;

import com.momstore.extent_reports.ExtentReport;
import com.momstore.interfaces.Constants;
import com.momstore.loggers.Loggers;
import com.momstore.pageModels.*;
import com.momstore.project_setup.TestNGBase;
import com.momstore.utilities.ExcelUtils;
import com.momstore.utilities.Property;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Objects;

public class AddToCart extends TestNGBase implements Constants {
    public WebDriver driver;
    WebDriverWait wait;
    private ArrayList<String> pdpProductOldPrice;
    private ArrayList<String> pdpProductFinalPrice;
    private ArrayList<String> pdpProductQuantity;
    private ArrayList<String> pdpProductName;
    private ArrayList<String> pdpProductSwatches;

    @BeforeClass(description = "Pre Test Configurations", alwaysRun = true)
    public void preTestRuns() {
        // Initialize Driver
        driver = initializeDriver();
        wait = new WebDriverWait(driver, 10);

        // Setting the Loggers and Extent Reports
        Loggers.setLogger(UserAccounts.class.getName());
        ExtentReport.createTest("Add To Cart & Verify");

        // Getting the Website Details
        ExcelUtils.excelConfigure(WEBSITE_SAMPLE_DATA);
        ExcelUtils.getRowData(Integer.parseInt(Objects.requireNonNull(Property.getProperty("testRow"))));
    }

    /**
     * Adding the Product to cart
     */
    @Test(description = "Add a simple product to cart", priority = 1, groups = {"addToCart.addProducts"})
    public void addToCart() {
        // Setting up the Extent Report
        ExtentReport.createNode("Add to Cart");

        HeaderModel headerModel = new HeaderModel(driver);
        ListingModel listingModel = new ListingModel(driver);
        SearchListingModel searchListingModel = new SearchListingModel(driver);
        ProductModel productModel = new ProductModel(driver);
        CartModel cartModel = new CartModel(driver);

        // Search for products from Search Listing page
        headerModel.searchForProducts(searchListingModel, wait);

        // Select the product passed
        searchListingModel.pickRandomProduct();
        searchListingModel.getProductItemLink().get(searchListingModel.getProductId()).click();

        // Fetch the PDP product details
//        productModel.fetchProductDetails(searchListingModel, wait);

        // Creating ArrayList objects
        createVariableObjects();

        // Fetching Product Details
        wait.until(ExpectedConditions.visibilityOf(productModel.getPdpProductName()));
        pdpProductName.add(productModel.getPdpProductName().getText());
        try {
            if (!searchListingModel.getSearchProductOldPrice().equals("") || searchListingModel.getSearchProductOldPrice() != null) {
                // Fetching the Old price
                pdpProductOldPrice.add(productModel.getPdpOldPrice().getText());
            }
        } catch (Exception e) {
            Loggers.getLogger().info("No Special Price");
        }
        this.pdpProductFinalPrice.add(productModel.getPdpFinalPrice().getText());
        this.pdpProductQuantity.add(productModel.getQuantity().getAttribute("value"));

        pdpProductSwatches.addAll(productModel.selectSwatchesIfConfigProduct(wait));

        // Selecting the qty to add
        wait.until(ExpectedConditions.elementToBeClickable(productModel.getQuantity()));
        productModel.getQuantity().clear();
        productModel.getQuantity().sendKeys(ExcelUtils.getDataMap().get("qty"));

        // Click on Add to Cart
        productModel.getAddToCartButton().click();

        // Verify the success message
        wait.until(ExpectedConditions.visibilityOf(productModel.getSuccessMessage()));
        Assert.assertTrue(productModel.getSuccessMessage().getText().contains("shopping bag"));
        Loggers.getLogger().info("Success message is displayed");
        ExtentReport.getExtentNode().pass("Success message is displayed");

    }

    /**
     * Verify the Products added to Cart
     */
    @Test(description = "Verify the Products added to Cart", priority = 2, groups = {"addToCart.verifyProducts"})
    public void verifyProductsInCart() {
        // Setting up the Extent Report
        ExtentReport.createNode("Verify Products");

        HeaderModel headerModel = new HeaderModel(driver);
        ListingModel listingModel = new ListingModel(driver);
        ProductModel productModel = new ProductModel(driver);
        MinicartModel minicartModel = new MinicartModel(driver);
        CartModel cartModel = new CartModel(driver);

        // Navigate to the Cart page
        wait.until(ExpectedConditions.visibilityOf(productModel.getSuccessMessage()));
        headerModel.getCartIcon().click();
        minicartModel.getViewCart().click();
        cartModel.fetchProductDetails();

        // Verifying product details
        Iterator<WebElement> cartItems = cartModel.getProductNames().iterator();
        while (cartItems.hasNext()) {
            Assert.assertEquals(pdpProductName, cartModel.getCartProductName());
            Assert.assertEquals(pdpProductFinalPrice, cartModel.getCartFinalPrice());
            Assert.assertEquals(pdpProductQuantity, cartModel.getCartProductQty());
            if (!pdpProductSwatches.get(0).equalsIgnoreCase("simple")) {
                for (int swatch = 0; swatch < cartModel.getCartSwatch().size(); swatch++) {
                    Assert.assertEquals(cartModel.getCartSwatch().get(swatch).trim(), pdpProductSwatches.get(swatch).trim());
                }
            }

            // Logging and Extent Reports
            String productName = cartItems.next().getText();
            Loggers.getLogger().info("'" + productName + "' successfully added to cart");
            ExtentReport.getExtentNode().pass("'" + productName + "' successfully added to cart");
        }
    }

    public void createVariableObjects() {
        pdpProductName = new ArrayList<>();
        pdpProductOldPrice = new ArrayList<>();
        pdpProductFinalPrice = new ArrayList<>();
        pdpProductQuantity = new ArrayList<>();
        pdpProductSwatches = new ArrayList<>();
    }

}
