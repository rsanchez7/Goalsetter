package Goalsetter.config;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.concurrent.TimeUnit;

public class   Page {

  private WebDriver driver;


  public Page(WebDriver driver) {
    this.driver = driver;
    PageFactory.initElements(driver, this);
    driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
    this.waitUntilDocumentReadyStateIsComplete();

  }

  public static void sleep(int miliseconds) {
    try {
      Thread.sleep(miliseconds);
    } catch (InterruptedException ex) {
      Thread.currentThread().interrupt();
    }
  }

  private void waitUntilDocumentReadyStateIsComplete() {
    new WebDriverWait(driver, 20)
        .until((ExpectedCondition<Boolean>) wd ->
            ((JavascriptExecutor) wd).executeScript("return document.readyState")
                .equals("complete"));
  }

  public JavascriptExecutor javaScriptWebDriver() throws Exception {
    if (driver instanceof JavascriptExecutor) {
      return ((JavascriptExecutor) driver);
    } else {
      throw new IllegalStateException("Este driver no soporta JavaScript...!");
    }
  }

  public void scrollToElement(WebElement element) throws Exception {
    javaScriptWebDriver()
        .executeScript("arguments[0].scrollIntoView();", element);
  }

  public void changeToFrame(WebElement element) {
    driver.switchTo().frame(element);
  }

  public void changeToPrincipalFrame() {
    driver.switchTo().defaultContent();
  }

  public void waitForElementPresent(WebElement element, int time)
      throws Exception {
    WebDriverWait wait = new WebDriverWait(driver, time);
    wait.until(ExpectedConditions.visibilityOf(element));
  }

  public boolean elementExists(WebElement element) {
    try {
      waitForElementPresent(element, 5);
      boolean isDisplayed = element.getSize().height > 0;
      return isDisplayed;
    } catch (Exception ex) {
      return false;
    }
  }
}
