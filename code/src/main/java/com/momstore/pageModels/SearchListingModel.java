package com.momstore.pageModels;

import com.momstore.extent_reports.ExtentReport;
import com.momstore.loggers.Loggers;
import com.momstore.pickers.RandomPicker;
import com.momstore.utilities.ExcelUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class SearchListingModel {
    private WebDriver driver;
    private String searchProductName;
    private String searchProductFinalPrice;
    private String searchProductOldPrice;
    private int productId;

    @FindBy(css = "h1 > span")
    private WebElement searchTitle;
    @FindBy(css = ".product-items .product-item")
    private List<WebElement> productList;
    @FindBy(css = ".product-item-link")
    private List<WebElement> productItemLink;
    @FindBy(css = "[data-price-type='finalPrice'] span")
    private List<WebElement> finalPriceList;
    @FindBy(xpath = "//span[@data-price-type='oldPrice']/span")
    private List<WebElement> oldPriceList;

    /**
     * @param driver - Constructor
     */
    public SearchListingModel(WebDriver driver) {
        PageFactory.initElements(driver, this);
        this.driver = driver;
    }

    /**
     * Select the product from the Listing page
     * @param productModel - ProductModel
     */
    public void selectProduct(ProductModel productModel, WebDriverWait wait) {
        String prodName = ExcelUtils.getDataMap().get("product_name");
        Actions act = new Actions(driver);

        wait.until(ExpectedConditions.visibilityOfAllElements(getProductList()));
        List<WebElement> prodItemLink = getProductItemLink();

        // Selecting the product from Listing page
        for (WebElement prodObject : prodItemLink) {
            if (prodObject.getText().trim().equals(prodName)) {
                prodObject.click();
                break;
            }
        }
    }

    public void pickRandomProduct() {
        // Pick random product number
        productId = RandomPicker.numberPicker(productList.size());
    }

    /**
     * Fetch the Product Name, Old Price(If present) and Final Price
     */
    public void fetchProductDetails() {
        // Setting ExtentReports
        ExtentReport.createNode("Fetch product details in Listing page");

        pickRandomProduct();

        // Fetching the Product name
        searchProductName = productItemLink.get(productId).getText();
        Loggers.getLogger().info("Product '" + searchProductName + "' is picked");
        ExtentReport.getExtentNode().pass("Product '" + searchProductName + "' is picked");

        // Fetching Old Price if present
        try {
            if (oldPriceList.get(productId).isDisplayed()) {
                // Fetching the Old Price in listing page
                searchProductOldPrice = oldPriceList.get(productId).getText();
                Loggers.getLogger().info("Product Old Price: " + searchProductOldPrice);
                ExtentReport.getExtentNode().pass("Product Old Price: " + searchProductOldPrice);

                // Fetching the Final Price in listing page
                searchProductFinalPrice = finalPriceList.get(productId).getText();
                Loggers.getLogger().info("Product Final Price: " + searchProductFinalPrice);
                ExtentReport.getExtentNode().pass("Product Final Price: " + searchProductFinalPrice);
            }
        } catch (Exception e) {
            // Fetching Final Price if Old Price not present
            if (finalPriceList.get(productId).isDisplayed()) {
                searchProductFinalPrice = finalPriceList.get(productId).getText();
                Loggers.getLogger().info("Product Final Price: " + searchProductFinalPrice);
                ExtentReport.getExtentNode().pass("Product Final Price: " + searchProductFinalPrice);
            }
        }
    }

    /**
     * @return WebElement
     */
    public WebElement getSearchTitle() {
        return searchTitle;
    }

    /**
     * @return List<WebElement>
     */
    public List<WebElement> getProductList() {
        return productList;
    }

    /**
     * @return List<WebElement>
     */
    public List<WebElement> getProductItemLink() {
        return productItemLink;
    }

    /**
     * @return List<WebElement>
     */
    public List<WebElement> getFinalPriceList() {
        return finalPriceList;
    }

    /**
     * @return List<WebElement>
     */
    public List<WebElement> getOldPriceList() {
        return oldPriceList;
    }

    /**
     * @return String
     */
    public String getSearchProductName() {
        return searchProductName;
    }

    /**
     * @return String
     */
    public String getSearchProductFinalPrice() {
        return searchProductFinalPrice;
    }

    /**
     * @return String
     */
    public String getSearchProductOldPrice() {
        return searchProductOldPrice;
    }

    /**
     * @return Integer
     */
    public int getProductId() {
        return productId;
    }
}
