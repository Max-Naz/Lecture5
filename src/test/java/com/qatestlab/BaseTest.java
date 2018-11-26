package com.qatestlab;

import com.qatestlab.utils.logging.EventHandler;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class BaseTest {
    protected EventFiringWebDriver driver;
    protected GeneralActions actions;
    protected boolean isMobileTesting;

    @BeforeClass
    @Parameters({"selenium.browser", "selenium.grid"})
    public void setUp(@Optional("chrome") String browser,
                      @Optional("http://localhost:4444/wd/hub") String gridUrl) {
        // TODO create WebDriver instance according to passed parameters
        driver = new EventFiringWebDriver(getDriver(browser, gridUrl));
        driver.register(new EventHandler());

        driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
        driver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);
        // unable to maximize window in mobile mode
        if (!isMobileTesting(browser))
            driver.manage().window().maximize();

        isMobileTesting = isMobileTesting(browser);

        actions = new GeneralActions(driver);
    }


    @AfterClass
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    public  static WebDriver getDriver(String browser, String gridUrl) {
        switch (browser) {
            case "firefox":
                System.setProperty("webdriver.gecko.driver",
                        new File(BaseTest.class.getResource("/geckodriver_0_23_0.exe").getFile()).getPath());
                return new FirefoxDriver();
            case "ie":
            case "internet explorer":
                System.setProperty("webdriver.ie.driver",
                        new File(BaseTest.class.getResource("/IEDriverServer_3_14_0.exe").getFile()).getPath());
                return new InternetExplorerDriver();
            case "remote-chrome":
                ChromeOptions chromeRemoteOptions = new ChromeOptions();
                chromeRemoteOptions.addArguments("headless");
                chromeRemoteOptions.addArguments("window-size=1366x768");
                try {
                    return new RemoteWebDriver(new URL(gridUrl), chromeRemoteOptions);
                }catch (MalformedURLException e){
                    e.printStackTrace();
                }
                return null;
            case "remote-firefox":
                FirefoxOptions firefoxRemoteOptions = new FirefoxOptions();
                firefoxRemoteOptions.addArguments("--headless");
                try {
                    return new RemoteWebDriver(new URL(gridUrl), firefoxRemoteOptions);
                }catch (MalformedURLException e){
                    e.printStackTrace();
                }
            case "mobile":
                System.setProperty("webdriver.chrome.driver",
                        new File(BaseTest.class.getResource("/chromedriver_2_43.exe").getFile()).getPath());
                Map<String, String> mobileEmulation = new HashMap<>();
                mobileEmulation.put("deviceName", "Galaxy S5");

                ChromeOptions mobileChromeOptions = new ChromeOptions();
                mobileChromeOptions.setExperimentalOption("mobileEmulation", mobileEmulation);
                return new ChromeDriver(mobileChromeOptions);
            case "chrome":
            default:
                System.setProperty("webdriver.chrome.driver",
                        new File(BaseTest.class.getResource("/chromedriver_2_43.exe").getFile()).getPath());
                return new ChromeDriver();
        }
    }

    private boolean isMobileTesting(String browser) {
        switch (browser) {
            case "mobile":
                return true;
            case "firefox":
            case "ie":
            case "internet explorer":
            case "chrome":
            case "remote-firefox":
            case "remote-chrome":
            default:
                return false;
        }
    }
}
