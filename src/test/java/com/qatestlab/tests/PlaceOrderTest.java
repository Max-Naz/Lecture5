package com.qatestlab.tests;

import com.qatestlab.BaseTest;
import org.testng.annotations.Test;

public class PlaceOrderTest extends BaseTest {
    @Test
    public void checkSiteVersion() {
        // TODO open main page and validate website version
        actions.openMainPage();
        actions.validateWebsiteVersion(isMobileTesting);
    }

    @Test
    public void createNewOrder() {
        System.out.println("createNewOrder test method.");
        // TODO implement order creation test

        // open random product
        actions.openMainPage();
        actions.openAllProductsPage();
        actions.openRandomProduct();

        // save product parameters

        // add product to Cart and validate product information in the Cart

        // proceed to order creation, fill required information

        // place new order and validate order summary

        // check updated In Stock value
    }
}
