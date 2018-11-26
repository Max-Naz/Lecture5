package com.qatestlab;

import com.qatestlab.model.ProductData;
import com.qatestlab.utils.Properties;
import com.qatestlab.utils.logging.CustomReporter;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.util.Random;

public class GeneralActions {
    private WebDriver driver;
    private WebDriverWait wait;

    public GeneralActions(WebDriver driver) {
        this.driver = driver;
        wait = new WebDriverWait(driver, 30);
    }

    public void openRandomProduct() {
        // TODO implement logic to open random product before purchase
        Random random = new Random();
        Integer n = random.nextInt(6) + 1;
        String productRandomId = n.toString();

        WebElement product = driver.findElement(By.xpath("//article[@data-id-product='"+ productRandomId +"']"));
        product.findElement(By.xpath("//div/div/h1/a")).click();
        //throw new UnsupportedOperationException();
    }

    /**
     * Extracts product information from opened product details page.
     *
     * @return
     */
    public ProductData getOpenedProductInfo() {
        CustomReporter.logAction("Get information about currently opened product");
        // TODO extract data from opened page
        throw new UnsupportedOperationException();
    }

    public void openMainPage() {
        driver.get(Properties.getBaseUrl());
    }

    public void validateWebsiteVersion(boolean isMobileBrowser) {

        CustomReporter.log("Validate website version");
        if (isMobileBrowser){
            CustomReporter.log("Website Version - Mobile Version.");

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
            CustomReporter.log("Website Version - Desktop Version.");

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
        driver.findElement(By.xpath("//a[contains(text(),'Все товары')]")).click();
    }
}
