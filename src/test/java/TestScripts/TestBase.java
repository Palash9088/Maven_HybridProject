package TestScripts;//import java.util.*;

import Base.PredefinedActions;
import Constant.ConstantPaths;
import Pages.*;
import Utils.PropertyFileReading;
import org.apache.log4j.PropertyConfigurator;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class TestBase {
    private DashboardPage dashboardPage;
    private MenuItemPage menuItemPage;
    private ProductListPage productListPage;
    private ProductDetailsPage productDetailsPage;
    private ProductCartPage productCartPage;

    @BeforeClass
    public void beforeClassExecution() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd_MM_yyyy_HH_mm");
        System.setProperty("current.date.time", sdf.format(new Date()));
        PropertyConfigurator.configure(ConstantPaths.LOG4J_PROP_PATH);
    }

    @BeforeClass
    void setDriver() {
        PredefinedActions.openBrowser("https://www.snapdeal.com/", "chrome");

    }

    DashboardPage getDashboardPage() {
        if (dashboardPage == null)
            dashboardPage = DashboardPage.getDashboardPage();
        return dashboardPage;
    }

    MenuItemPage getMenuItemPage() {
        if (menuItemPage == null)
            menuItemPage = MenuItemPage.getMenuItemPage();
        return menuItemPage;
    }

    ProductListPage getProductListPage() {
        if (productListPage == null)
            productListPage = ProductListPage.getProductListPage();
        return productListPage;
    }

    ProductDetailsPage getProductDetailsPageObj()
    {
        if(productDetailsPage == null)
            productDetailsPage = ProductDetailsPage.getProductDetailsPage();
        return productDetailsPage;
    }
    ProductCartPage getProductCartPageObj(){
        if(productCartPage == null)
            productCartPage = ProductCartPage.getProductCartPageObj();
        return productCartPage;
    }

    public ArrayList<String> readProperty() {
        PropertyFileReading prop;
        ArrayList<String> credentialList = new ArrayList<>();
        try {
            prop = new PropertyFileReading("src/main/resources/Configs/CredentialsProp.properties");
            credentialList.add(prop.getPropertyValue("gUserName"));
            credentialList.add(prop.getPropertyValue("gPassword"));
        } catch (IOException e) {
            throw new RuntimeException("Not valid file");
        }
        return credentialList;
    }

    @AfterMethod(enabled = false)
    void tearDown(ITestResult result) {
        if (result.getStatus() == ITestResult.FAILURE)
            PredefinedActions.takeScreenshot(result.getName());
        PredefinedActions.quitBrowser();
    }
}
