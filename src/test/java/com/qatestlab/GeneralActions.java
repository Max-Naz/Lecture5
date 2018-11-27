package com.qatestlab;

import com.qatestlab.model.ProductData;
import com.qatestlab.utils.Properties;
import com.qatestlab.utils.logging.CustomReporter;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.util.List;
import java.util.Random;

public class GeneralActions {
    private WebDriver driver;
    private WebDriverWait wait;
    private ProductData productData;

    public GeneralActions(WebDriver driver) {
        this.driver = driver;
        wait = new WebDriverWait(driver, 30);
    }

    public void openMainPage() {
        CustomReporter.logAction("Open main page.");

        driver.get(Properties.getBaseUrl());
    }

    public void validateWebsiteVersion(boolean isMobileBrowser) {
        CustomReporter.logAction("Validate website version.");

        if (isMobileBrowser){
            CustomReporter.log("Website Version = Mobile Version.");

            WebElement logoContainer = driver.findElement(By.id("_mobile_logo"));
            WebElement contactLinkContainer = driver.findElement(By.id("_mobile_contact_link"));
            WebElement languageSelectorContainer = driver.findElement(By.id("_mobile_language_selector"));
            WebElement userInfoContainer = driver.findElement(By.id("_mobile_user_info"));
            WebElement cartContainer = driver.findElement(By.id("_mobile_cart"));

            Assert.assertTrue(
                    logoContainer.findElement(By.tagName("a")).isEnabled());
            Assert.assertTrue(
                    contactLinkContainer.findElement(By.id("contact-link")).isEnabled());
            Assert.assertTrue(
                    languageSelectorContainer.findElement(By.className("language-selector-wrapper")).isEnabled());
            Assert.assertTrue(
                    userInfoContainer.findElement(By.className("user-info")).isEnabled());
            Assert.assertTrue(
                    cartContainer.findElement(By.className("header")).isEnabled());

            CustomReporter.log("Mobile Version is valid.");

        }else {
            CustomReporter.log("Website Version = Desktop Version.");

            WebElement logoContainer = driver.findElement(By.id("_desktop_logo"));
            WebElement contactLinkContainer = driver.findElement(By.id("_desktop_contact_link"));
            WebElement languageSelectorContainer = driver.findElement(By.id("_desktop_language_selector"));
            WebElement userInfoContainer = driver.findElement(By.id("_desktop_user_info"));
            WebElement cartContainer = driver.findElement(By.id("_desktop_cart"));

            Assert.assertTrue(
                    logoContainer.findElement(By.tagName("a")).isEnabled());
            Assert.assertTrue(
                    contactLinkContainer.findElement(By.id("contact-link")).isEnabled());
            Assert.assertTrue(
                    languageSelectorContainer.findElement(By.className("language-selector-wrapper")).isEnabled());
            Assert.assertTrue(
                    userInfoContainer.findElement(By.className("user-info")).isEnabled());
            Assert.assertTrue(
                    cartContainer.findElement(By.className("header")).isEnabled());

            CustomReporter.log("Desktop Version is valid.");
        }
    }

    public void openAllProductsPage() {
        CustomReporter.logAction("Open all products.");

        driver.findElement(By.xpath("//a[contains(text(),'Все товары')]")).click();
    }

    public void openRandomProduct() {
        CustomReporter.logAction("Open random product.");

        Random random = new Random();
        Integer n = random.nextInt(6) + 1;
        String productRandomId = n.toString();

        WebElement productContainer = driver.findElement(By.xpath("//article[@data-id-product='"+ productRandomId +"']"));
        productContainer.findElement(By.xpath("//div/div/h1/a")).click();
    }

    public ProductData getOpenedProductInfo() {
        CustomReporter.logAction("Get information about currently opened product");

        String openProductName = driver.findElement(By.xpath("//h1[@itemprop='name']")).getText();
        String openProductPrice = driver.findElement(By.xpath("//span[@itemprop='price']")).getText();
        String openProductQty = driver.findElement(By.xpath("//span[contains(text(),'Товары')]")).getAttribute("textContent");

        String productPriceReplaced = openProductPrice.replaceAll(" ₴", "");
        String productPriceStr = productPriceReplaced.replaceAll(",", ".");
        float productPriceFloat = Float.parseFloat(productPriceStr);

        String productQtyStr = openProductQty.replaceAll(" Товары", "");
        int productQtyInt = Integer.parseInt(productQtyStr);

        CustomReporter.log("Information about product is saved." +
                "Product name = '" + openProductName +
                "', Product price = '" + productPriceFloat +
                "', Product quantity = '" + productQtyInt + "'.");

        return new ProductData(openProductName, productPriceFloat, productQtyInt);
    }

    public void saveProductInfo() {
        CustomReporter.logAction("Save Product Info.");

        productData = getOpenedProductInfo();
    }

    public void addProductToCart() {
        CustomReporter.logAction("addProductToCart.");

        driver.findElement(By.xpath("//button[@data-button-action = 'add-to-cart']")).click();
    }

    public void proceedToCart() {
        CustomReporter.logAction("proceedToCart.");

        driver.findElement(By.xpath("//div[@class='cart-content']//a[contains(text(),'перейти к оформлению')]")).click();
    }

    public void validateProductDataInCart() throws InterruptedException {
        CustomReporter.logAction("validateProductDataInCart.");
        Thread.sleep(3000);
        CustomReporter.log("Validate products quantity in the cart.");
        String productQtyInCart = driver.findElement(By.xpath("//div[@id='cart-subtotal-products']/span[@class='label js-subtotal']")).getText();
        Assert.assertEquals(productQtyInCart, "1 шт.");

        CustomReporter.log("Validate product name in the cart.");
        WebElement productNameInCart= driver.findElement(By.xpath("//a[@class='label']"));
        Assert.assertTrue(productNameInCart.getText() == productData.getName());

        CustomReporter.log("Validate product name in the cart.");
        String productPriceInCart = driver.findElement(By.xpath("//span[@class='product-price']//strong")).getText();
        String productPriceReplaced = productPriceInCart.replaceAll(" ₴", "");
        String productPriceStr = productPriceReplaced.replaceAll(",", ".");
        float productPriceInCartFloat = Float.parseFloat(productPriceStr);
        Assert.assertTrue(productPriceInCartFloat == productData.getPrice());
    }

    public void proceedToOrderCreation() {
        driver.findElement(By.xpath("//a[contains(text(),'Оформление заказа')]")).click();
    }


}
