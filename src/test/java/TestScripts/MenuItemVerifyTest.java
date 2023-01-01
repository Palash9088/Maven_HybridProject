package TestScripts;//import java.util.*;

import Entities.ProductDetails;
import Pages.*;
import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MenuItemVerifyTest extends TestBase {
    static Logger log = Logger.getLogger(MenuItemVerifyTest.class);
    MenuItemPage menuItemPage;
    String expectedUsername;
    ProductListPage productListPage;

    //@BeforeClass
    public void login() {
        expectedUsername = "Kailash Soni";
        DashboardPage dashboardPage = getDashboardPage();
        ArrayList<String> credentialList;
        credentialList = readProperty();
        dashboardPage.loginSnapdeal(credentialList.get(0), credentialList.get(1));
        String actualUsername = dashboardPage.getSigninUsername();
        Assert.assertEquals(actualUsername, expectedUsername);
    }

    @Test(enabled = false)
    public void topCategoryVerify() {
        menuItemPage = getMenuItemPage();
        List<String> expectedTopCategory = new ArrayList<>(Arrays.asList("Men's Fashion", "Women's Fashion", "Home & Kitchen", "Toys, Kids' Fashion & more", "Beauty, Health & Daily Needs"));
        log.info("Expected Top Category " + expectedTopCategory);
        List<String> actualTopCategoryList = menuItemPage.getListOfTopCategories();

        log.info("Actual Top Category " + actualTopCategoryList);
        Assert.assertEquals(actualTopCategoryList, expectedTopCategory);
        log.info("Verification Passed");
    }

    @Test(enabled = false)
    public void moreCategoryVerify() {
        menuItemPage = getMenuItemPage();
        List<String> expectedMoreCategory = new ArrayList<>(Arrays.asList("Automotives", "Mobile & Accessories", "Electronics", "Sports, Fitness & Outdoor", "Computers & Gaming", "Books, Media & Music", "Hobbies"));
        log.info("Expected More Category " + expectedMoreCategory);
        List<String> actualMoreCategories = menuItemPage.getListOfMoreCategories();
        log.info("Actual More Category " + actualMoreCategories);
        Assert.assertEquals(actualMoreCategories, expectedMoreCategory);
        log.info("Verification Passed");
    }

    @Test(enabled = false)
    public void trendingSearchesVerify() {
        menuItemPage = getMenuItemPage();
        int expectedSIzeOfTrending = 5;
        log.info("Expected Size of Trending List " + expectedSIzeOfTrending);
        int actualSize = menuItemPage.getListOfTopSearches();
        log.info("Actual Size of Trending List " + actualSize);
        Assert.assertEquals(actualSize, expectedSIzeOfTrending);
        log.info("Verification Passed");
    }

    @Test()
    public void productVerify() throws InterruptedException {
        menuItemPage = getMenuItemPage();
        productListPage = getProductListPage();
        Thread.sleep(3000);
        menuItemPage.hoverOnCategory("Men's Fashion");

        ProductListPage productListPage = menuItemPage.clickOnSubCategoryItem("Sports Shoes");

        productListPage.searchByBrand("Action");
        boolean isBrandSelected = productListPage.isBrandSelected("Action");
        Assert.assertTrue(isBrandSelected);

        Thread.sleep(2000);
        productListPage.searchByRating("4.0");
        boolean isRatingSelected = productListPage.isRatingSelected("4.0");
        Assert.assertTrue(isRatingSelected);
        Thread.sleep(2000);
        productListPage.searchByColor("Black");

        Thread.sleep(1000);
        ProductDetailsPage productDetailsPage = productListPage.selectFirstProduct();
        List<String> availableSizes = productDetailsPage.getAllAvailableSizes();
        productDetailsPage.selectSize(9);
        Assert.assertTrue(productDetailsPage.isProductSizeSelected());
        ProductDetails productDetails = ProductDetailsPage.getProductDetailsPage().getSelectedProductDetails();
        ProductCartPage productCartPage = productDetailsPage.addToCart();

        String ProductCartPageProductName = productCartPage.getProductName();
        String ProductCartPageProductPrice = productCartPage.getProductPrice();
        int ProductCartPageFinalPrice = productCartPage.getProductFinalPrice();

        /*System.out.println(ProductCartPageProductName);
        System.out.println(ProductCartPageProductPrice);
        System.out.println(ProductCartPageFinalPrice);

        System.out.println(productDetails.getProductName());
        System.out.println(productDetails.getProductPriceWithCurrency());
        System.out.println(productDetails.getProductPrice());*/

        SoftAssert softAssert = new SoftAssert();

        softAssert.assertEquals(ProductCartPageProductName,productDetails.getProductName());
        softAssert.assertEquals(ProductCartPageProductPrice,productDetails.getProductPriceWithCurrency());
        softAssert.assertEquals(ProductCartPageFinalPrice,productDetails.getProductPrice());

        softAssert.assertAll();
    }
}
