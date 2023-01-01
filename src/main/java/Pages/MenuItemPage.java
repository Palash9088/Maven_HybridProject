package Pages;//import java.util.*;

import Base.PredefinedActions;
import Constant.ConstantPaths;
import Utils.PropertyFileReading;
import org.openqa.selenium.WebElement;

import java.io.IOException;
import java.util.List;

public class MenuItemPage extends PredefinedActions {

    static MenuItemPage menuItemPage;
    PropertyFileReading menuPropertyFile;

    private MenuItemPage() {
        try {
            menuPropertyFile = new PropertyFileReading(ConstantPaths.LOCATORPATH + "MenuItemPageProp.properties");
        } catch (IOException e) {
            System.out.println("Does not find Property File");
            throw new RuntimeException(e);
        }
    }
    public static MenuItemPage getMenuItemPage() {
        if (menuItemPage == null)
            menuItemPage = new MenuItemPage();
        return menuItemPage;
    }
    public List<String> getListOfTopCategories()
    {
        return getElementListText(menuPropertyFile.getPropertyValue("top_category"),true);
    }
    public List<String> getListOfMoreCategories()
    {
        return getElementListText(menuPropertyFile.getPropertyValue("more_category"),false);
    }
    public int getListOfTopSearches()
    {
       return getListOfWebElements(menuPropertyFile.getPropertyValue("trending_searches"),false).size();
    }
    public void hoverOnCategory(String category)
    {
        String categoryLocator = menuPropertyFile.getPropertyValue("main_category");
        categoryLocator = String.format(categoryLocator,category);
        hoverOnElement(getElement(categoryLocator,true),true);
    }
    public ProductListPage clickOnSubCategoryItem(String subItem)
    {
        String subCat_ItemLocator = menuPropertyFile.getPropertyValue("subCat_Item");
        subCat_ItemLocator = String.format(subCat_ItemLocator,subItem);
        clickOnElement(getElement(subCat_ItemLocator,true));
        return ProductListPage.getProductListPage();
    }

}
