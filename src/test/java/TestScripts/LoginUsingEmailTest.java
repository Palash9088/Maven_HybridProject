package TestScripts;//import java.util.*;

import Constant.ConstantPaths;
import Pages.DashboardPage;
import Utils.ExcelFileReading;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class LoginUsingEmailTest extends TestBase {

    @Test(dataProvider = "loginDataProvider")
    public void loginUsingEmailTest(String email, String password, String name) {
        DashboardPage dashboardPage = getDashboardPage();
        dashboardPage.doLoginWithEmail(email, password, name);
    }

    @DataProvider(name = "loginDataProvider")
    public Object[][] dataProvider() {
        Object[][] data = ExcelFileReading.getAllRows(ConstantPaths.LOGIN_DATA,ConstantPaths.LOGIN_DATA_SHEET );
        return data;
    }


}
