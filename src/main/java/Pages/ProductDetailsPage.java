package Pages;//import java.util.*;

import Base.PredefinedActions;
import Constant.ConstantPaths;
import Entities.ProductDetails;
import Utils.PropertyFileReading;
import org.openqa.selenium.WebElement;

import java.io.IOException;
import java.util.List;

public class ProductDetailsPage extends PredefinedActions {

    static ProductDetailsPage productDetailsPage;
    static PropertyFileReading productDetailsProp;

    private ProductDetailsPage() {
        try {
            productDetailsProp = new PropertyFileReading(ConstantPaths.LOCATORPATH + "productDetailsProp.properties");
        } catch (IOException e) {
            System.out.println("Does not find Property File");
            throw new RuntimeException(e);
        }
    }

    public static ProductDetailsPage getProductDetailsPage() {
        if (productDetailsPage == null)
            productDetailsPage = new ProductDetailsPage();
        return productDetailsPage;
    }

    public List<String> getAllAvailableSizes() {
        return getElementListText(productDetailsProp.getPropertyValue("available_Sizes"), true);
    }

    public void selectSize(int sizeNumber) {
        String element = String.format(productDetailsProp.getPropertyValue("select_Size"), sizeNumber);
        clickOnElement(element, true);
    }

    public boolean isProductSizeSelected() {
        WebElement element = getElement(productDetailsProp.getPropertyValue("product_Selected"), true);
        String elementAttr = getAttribute(element, "class");
        return elementAttr.contains("attr-selected");
    }

    private int getPrice(String str) {
        try {
            String[] prices = str.split(" ");
            for (String word : prices) {
                return Integer.parseInt(word.replace(",", "").replace(".", ""));
            }
        } catch (NumberFormatException ne) {
            System.out.println(ne);
        }
        return 0;
    }

    public ProductDetails getSelectedProductDetails() {
        ProductDetails productDetails = new ProductDetails();
        productDetails.setProductName(getElementText(productDetailsProp.getPropertyValue("productName"), true));
        productDetails.setProductPriceWithCurrency(getElementText(productDetailsProp.getPropertyValue("productPriceRs"), true));
        productDetails.setProductPrice(getPrice(getElementText(productDetailsProp.getPropertyValue("ProductPrice"), true)));
        productDetails.setProductSize(9);
        return productDetails;
    }

    public ProductCartPage addToCart() {
        clickOnElement(getElement(productDetailsProp.getPropertyValue("addToCartBtn"), true));
        return ProductCartPage.getProductCartPageObj();
    }
}