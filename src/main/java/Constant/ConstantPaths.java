package Constant;//import java.util.*;

import java.io.File;

public class ConstantPaths {
    //WebDrivers Paths
    public final static String CHROMEDRIVER_WIN = "src/main/resources/Windows/chromedriver.exe";
    public final static String FIREFOXDRIVER_WIN = "src/main/resources/Windows/geckodriver.exe";
    public final static String EDGEDRIVER_WIN = "src/main/resources/Windows/msedgedriver.exe";

    //Screenshot Path
    public final static String SCREENSHOT_PATH = "src/test/resources/screenshots";
    public final static String SCREENSHOT_EXTENSION = ".png";

    //DataFile Path
    public final static String LOGIN_DATA = "src/test/resources/TestData/Demo2.xlsx";
    public final static String LOGIN_DATA_SHEET = "LoginData";

    //Locator Paths
    public final static String LOCATORPATH = "src"+File.separator+"main"+ File.separator+"resources"+File.separator+"Configs"+File.separator;

    public final static String LOG4J_PROP_PATH = "src//main//resources//Configs//log4j.properties";
}
