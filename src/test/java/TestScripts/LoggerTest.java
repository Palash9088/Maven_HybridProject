package TestScripts;//import java.util.*;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.testng.annotations.Test;

import java.text.SimpleDateFormat;
import java.util.Date;

public class LoggerTest {

    Logger log = Logger.getLogger(getClass());
    @Test
    public void m1() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd_MM_yyyy_HH_mm");
        System.setProperty("current.date.time",sdf.format(new Date()));

        PropertyConfigurator.configure("src//main//resources//Configs//log4j.properties");
        log.trace("hi");
        log.debug("hi");
        log.info("hi");
        log.warn("hi");
        log.error("hi");
        log.fatal("hi");
        log.info("");
    }
}
