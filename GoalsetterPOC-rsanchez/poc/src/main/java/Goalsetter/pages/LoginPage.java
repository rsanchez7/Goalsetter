package Goalsetter.pages;

import Goalsetter.config.Page;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
public class LoginPage extends Page {
  private final String USER_INCORRECTO_MSG = "Epic sadface: Username and password do not match any user in this service";
  private final String USUARIO_LOCKED = "Epic sadface: Sorry, this user has been locked out.";
  private WebDriver driver;

  //LOCALIZADORES
  private final String USER = "standard_user";
  private final String PASS = "secret_sauce";
  @FindBy(xpath = "//*[@id=\"user-name\"]")
  private WebElement email;
  @FindBy(xpath = "//*[@id=\"password\"]")
  private WebElement password;
  @FindBy(xpath = "//*[@id=\"login-button\"]")
  private WebElement ingresar;
  @FindBy(xpath = "//*[@id=\"login_button_container\"]/div/form/div[3]/h3")
  private WebElement error;
  @FindBy(xpath = "//*[@id=\"header_container\"]/div[1]/div[2]/div")
  private WebElement Bienvenido;
  @FindBy(xpath = "//*[@id=\"react-burger-menu-btn\"]")
  private WebElement configBtn;
  @FindBy(xpath = "//*[@id=\"logout_sidebar_link\"]")
  private WebElement logoutBtn;

  //ACTIONS
  public LoginPage(WebDriver driver) {
    super(driver);
  }
  public WebElement getBienvenido() {
    return Bienvenido;
  }
  public boolean loginManual(String user, String pass) throws Exception {
    email.sendKeys(user);
    password.sendKeys(pass);
    sleep(3000);
    ingresar.click();
    try {
      waitForElementPresent(Bienvenido, 10);
      sleep(3000);
      return Bienvenido.isDisplayed();
    } catch (Exception e) {
      return false;
    }
  }

  public boolean login() throws Exception {
    email.sendKeys(USER);
    password.sendKeys(PASS);
    sleep(3000);
    ingresar.click();
    try {
      //waitForElementPresent(Bienvenido, 10);
      sleep(3000);
      return Bienvenido.isDisplayed();
    } catch (Exception e) {
      return false;
    }
  }

  public boolean userAndPasswordIncorrecto(String user, String pass) throws Exception {
    if (!loginManual(user, pass)) {
      return error.getText().equals(USER_INCORRECTO_MSG);
    }
    throw new Exception(
        "El usurio " + user + " se pudo loguear, no se pudo validar el usuario incorrecto");
  }

  public boolean userLoked(String user, String pass) throws Exception {
    if (!loginManual(user, pass)) {
      return error.getText().equals(USUARIO_LOCKED);
    }
    throw new Exception(
            "El usurio " + user + " se pudo loguear, no se pudo validar el usuario incorrecto");
  }

  public void logout() throws InterruptedException {
    configBtn.click();
    sleep(5000);
    if (logoutBtn.getText().equals("LOGOUT")) {
      sleep(3000);
      logoutBtn.click();
      System.out.println("Usuario Deslogueado");
    }
  }


}


	
