package Pages;//import java.util.*;

import Base.PredefinedActions;
import Utils.PropertyFileReading;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebElement;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
public class DashboardPage extends PredefinedActions {
    private static DashboardPage dashboardPage;
    //PropertyFileReading propOperation;
    PropertyFileReading dashboardPageProp;
    String parentWindow;

    static Logger log = Logger.getLogger(DashboardPage.class);
    private DashboardPage() {
     /*   try {
            propOperation = new PropertyFileReading("src/main/resources/CredentialsProp.properties");
        } catch (IOException e) {
            throw new RuntimeException("Credentials property file not found");
            //Custom Exception
        }*/
        try {
            dashboardPageProp = new PropertyFileReading("src/main/resources/Configs/DashboardPage.properties");
        } catch (IOException e) {
            throw new RuntimeException("Dashboard property file not found");
        }
    }


    synchronized static public DashboardPage getDashboardPage() {
        if (dashboardPage == null)
            dashboardPage = new DashboardPage();
        return dashboardPage;
    }

    private void hoverOnSignIn() {
        WebElement target = getElement(dashboardPageProp.getPropertyValue("signInMenu"), true);
        hoverOnElement(target, true);
        log.debug("User able to Hover on Signup Button");
    }

    private void clickOnLoginButton() {
        //getElement("xpath", "//a[text()='login']", true).click();
        clickOnElement(dashboardPageProp.getPropertyValue("loginBtn"), true);
        log.debug("User able to Click on Login");
    }

    private void switchToFrame() {
        switchToFrameByElement(getElement(dashboardPageProp.getPropertyValue("frameSwitch"), true));
        log.debug("User Switched to Frame");
    }

    private void loginUsingGmail(String gUserName, String gPassword) {
        WebElement element = getElement(dashboardPageProp.getPropertyValue("gLoginBtn"), true);
        // javaScriptClick(element);
        clickOnElement(element);
        log.debug("User clicked on google login Button");
        Set<String> windowHandles = getAllWindowHandlesId();
        Iterator<String> iterator = windowHandles.iterator();
        parentWindow = iterator.next();
        String childWindow = iterator.next();
        switchToSpecificWindow(childWindow);

        enterText(dashboardPageProp.getPropertyValue("gEmail"), true, gUserName);
        //  getElement("xpath", "//input[@aria-label='Email or phone']", true).sendKeys("kailashsoni007test@gmail.com");
        log.debug("User Entered Email");

        getElement(dashboardPageProp.getPropertyValue("nextBtn"), true).click();
        log.debug("User clicked on Next");

        WebElement password = getElement(dashboardPageProp.getPropertyValue("gPass"), true);
        enterText(password, gPassword);
        log.debug("User Entered Password");

        //getElement("xpath", "//span[@class='VfPpkd-vQzf8d'][text()='Next']", true).click();
        clickOnElement(dashboardPageProp.getPropertyValue("nextBtnPass"), true);
        log.debug("User clicked on next Button");
    }

    private void switchToParentWindow() {
        switchToSpecificWindow(parentWindow);
        log.debug("User switched to Parent window");
    }

    public void loginSnapdeal(String gUserName, String gPassword) {
        hoverOnSignIn();
        clickOnLoginButton();
        switchToFrame();
        loginUsingGmail(gUserName, gPassword);
        switchToParentWindow();

    }

    public List<String> getHoverOptions() {
        hoverOnSignIn();
        List<String> elementListText = getElementListText(dashboardPageProp.getPropertyValue("beforeLoginList"), true);
        return elementListText;
    }

    public String getSigninUsername() {
        String signInName = "Sign In";
        while (signInName.equals("Sign In")) {
            signInName = getElement(dashboardPageProp.getPropertyValue("userName"), true).getText();
        }
        return signInName;
        // return getElement("xpath", "//span[contains(@class,'accountUserName')]", true).getText();
    }

    public void doLoginWithEmail(String email, String password, String name) {
        hoverOnSignIn();
        clickOnLoginButton();
        switchToFrame();
        enterText(getElement(dashboardPageProp.getPropertyValue("loginText"), true), email);
        clickOnElement(getElement(dashboardPageProp.getPropertyValue("continueButton"), true));
        EmailPage emailPage = EmailPage.getPageObject();
        String otp = emailPage.getOtp(email, password);
        switchToFrame();
        enterText(getElement(dashboardPageProp.getPropertyValue("otpInputValue"), true), otp);
        clickOnElement(getElement(dashboardPageProp.getPropertyValue("otpLoginBtn"), true));

    }
}
