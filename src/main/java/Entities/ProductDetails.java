package Entities;//import java.util.*;

public class ProductDetails {
    String productName;
    int productPrice;
    String productPriceWithCurrency;
    int productSize;

    public int getProductSize() {
        return productSize;
    }

    public void setProductSize(int productSize) {
        this.productSize = productSize;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(int productPrice) {
        this.productPrice = productPrice;
    }

    public String getProductPriceWithCurrency() {
        return productPriceWithCurrency;
    }

    public void setProductPriceWithCurrency(String productPriceWithCurrency) {
        this.productPriceWithCurrency = productPriceWithCurrency;
    }
}
