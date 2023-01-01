package Pages;//import java.util.*;

import Base.PredefinedActions;
import Constant.ConstantPaths;
import Utils.PropertyFileReading;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebElement;

import java.io.IOException;
import java.util.Iterator;
import java.util.Set;

public class ProductListPage extends PredefinedActions {
    static ProductListPage productListPage;
    PropertyFileReading productPropertyFile;

    Logger log = Logger.getLogger(getClass());

    private ProductListPage() {
        try {
            productPropertyFile = new PropertyFileReading(ConstantPaths.LOCATORPATH + "ProductPageProp.properties");
        } catch (IOException e) {
            log.warn("Does not find Property File");
            throw new RuntimeException(e);
        }
    }

    public static ProductListPage getProductListPage() {
        if (productListPage == null)
            productListPage = new ProductListPage();
        return productListPage;
    }

    public void searchByBrand(String brandName) {
        clickOnElement(getElement(productPropertyFile.getPropertyValue("searchBox"), true));
        WebElement element = getElement(productPropertyFile.getPropertyValue("clickAndSendKeysBox"), true);
        clickOnElement(element);
        enterText(element, brandName);
        clickOnElement(String.format(productPropertyFile.getPropertyValue("selectCheckBox"), brandName), true);
        clickOnElement(productPropertyFile.getPropertyValue("applyBtn"), true);
    }

    public boolean isBrandSelected(String brandName) {
        WebElement element = getElement(String.format(productPropertyFile.getPropertyValue("brandCheck"), brandName), true);
        String attrValue = getAttribute(element, "class");
        return attrValue.contains("active-filter");
    }

    public void searchByRating(String rating) {
        clickOnElement(String.format(productPropertyFile.getPropertyValue("rating_Filter"), rating), true);
    }

    public boolean isRatingSelected(String rating) {
        WebElement element = getElement(String.format(productPropertyFile.getPropertyValue("ratingCheck"), rating), true);
        String attrValue = getAttribute(element, "class");
        return attrValue.contains("active-filter");

    }

    public void searchByColor(String color) {
        String element = String.format(productPropertyFile.getPropertyValue("color_Filter"), color);
        clickOnElement(String.format(productPropertyFile.getPropertyValue("color_Filter"), color), true);


    }

    public ProductDetailsPage selectFirstProduct() {
        String parentWin = getMainWindowHandleId();
        clickOnElement(productPropertyFile.getPropertyValue("first_Product"), true);
        Set<String> windowHandlesId = getAllWindowHandlesId();
        Iterator<String> iterator = windowHandlesId.iterator();
        parentWin = iterator.next();
        String childWin = iterator.next();
        switchToSpecificWindow(childWin);

        return ProductDetailsPage.getProductDetailsPage();
    }
}
