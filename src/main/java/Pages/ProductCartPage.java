package Pages;//import java.util.*;

import Base.PredefinedActions;
import Constant.ConstantPaths;
import Utils.PropertyFileReading;

import java.io.IOException;

public class ProductCartPage extends PredefinedActions {

    private static ProductCartPage productCartPage;
    PropertyFileReading propertyFileReading;

    private ProductCartPage() {
        try {
            propertyFileReading = new PropertyFileReading(ConstantPaths.LOCATORPATH + "ProductCartPage.properties");
        } catch (IOException ie) {
            System.out.println(ie + "file not found");
        }
    }

    public static ProductCartPage getProductCartPageObj() {
        if (productCartPage == null)
            productCartPage = new ProductCartPage();
        return productCartPage;
    }

    public String getProductName() {
        return getElementText(propertyFileReading.getPropertyValue("productTitle"), true);
    }

    public String getProductPrice() {
        return getElementText(propertyFileReading.getPropertyValue("productPrice"), true);
    }

    public int getProductFinalPrice() {
        return Integer.parseInt(getElementText(propertyFileReading.getPropertyValue("productFinalPrice"), true).split(" ")[1].replace(",", ""));
    }

}
