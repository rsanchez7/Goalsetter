package Goalsetter.config;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.ITestResult;
import Goalsetter.config.report.ExtentReportListener;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TestBrowser {

  private static final String URL = "https://www.saucedemo.com/";
  protected static WebDriver driver;

  public TestBrowser() {
    super();
  }

  public void config() {

    System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") + "\\src\\main\\resources\\drivers\\chromedriver.exe");
    ChromeOptions options = new ChromeOptions();
    //options.addArguments("--headless");
    options.addArguments("start-maximized");
    driver = new ChromeDriver(options);
    driver.get(URL);
    driver.manage().window().maximize();
    sleep(4000);
  }

  protected static void sleep(int miliseconds) {
    try {
      Thread.sleep(miliseconds);
    } catch (InterruptedException ex) {
      Thread.currentThread().interrupt();
    }
  }


  //@BeforeMethod(alwaysRun = true)
  public void init() throws Exception {
    System.setProperty("webdriver.chrome.driver",
        System.getProperty("user.dir") + "\\src\\main\\resources\\drivers\\chromedriver.exe");

    ChromeOptions options = new ChromeOptions();
    //options.addArguments("--headless");
    options.addArguments("window-size=1200x600");
    driver = new ChromeDriver(options);
    driver.get(URL);
    driver.manage().window().maximize();
    sleep(4000);
  }


  //@AfterMethod(alwaysRun = true)
  public void afterMethod(ITestResult result) {
    if (result.getStatus() == ITestResult.FAILURE) {
      SimpleDateFormat dateFormat = new SimpleDateFormat(
          "yyyy_dd_MM_HH_mm_ss_SS");
      String attributeValue = result.getMethod().getMethodName() + dateFormat.format(new Date());
      result.setAttribute("IdTestCase", attributeValue);
      try {
        ExtentReportListener.captureScreenshot(driver, attributeValue);
      } catch (Exception ex) {
        ex.printStackTrace();
      }
    }
    closeBrowser();
  }

  protected void closeBrowser() {
    driver.quit();
  }

}


