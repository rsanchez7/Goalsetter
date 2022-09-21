package Goalsetter.login;


import Goalsetter.pages.LoginPage;
import Goalsetter.pages.PageGeneral;
import org.testng.Assert;
import org.testng.annotations.Test;


public class TestLogin extends PageGeneral {
  @Test(description = "Test de login  Correctamente", testName = "Test login Correcto", groups = {
          TestGroups.LOGIN})
  public void login() throws Exception {
    config();
    LoginPage login = new LoginPage(driver);
    Assert.assertTrue(login.login(),
            "El usurio  no se pudo loguear correctamente...");
    System.out.println("Usuario Logueado");
    sleep(3000);
    login.logout();
    closeBrowser();
  }

  @Test(description = "Test de logout", testName = "Test de logout", groups = {
          TestGroups.LOGIN})
  public void logout() throws Exception {
    config();
    LoginPage page = new LoginPage(driver);
    page.loginManual("standard_user", "secret_sauce");
    sleep(3000);
    page.logout();
    Assert.assertTrue(true);
    closeBrowser();
  }

  @Test(description = "Test de login con usuario incorrecto", testName = "usuario incorrecto", groups = {
      TestGroups.LOGIN})
  public void userLoked() throws Exception {
      config();
    LoginPage page = new LoginPage(driver);
    Assert.assertTrue(page.userLoked("locked_out_user", "secret_sauce"),
        "No se pudo validar el test de usuer incorrecto");
    System.out.println("Usuario bloqueado");
    closeBrowser();
  }

  @Test(description = "Test de login con password incorrecto", testName = "password incorrecto", groups = {
      TestGroups.LOGIN})
  public void password_incorrecto() throws Exception {
      config();
    LoginPage page = new LoginPage(driver);
    Assert.assertTrue(page.userAndPasswordIncorrecto("performance_glitch_user", "ksksksks"),
        "No se pudo validar el test de password incorrecto");
    System.out.println("Contraseña incorrecta");
    closeBrowser();
  }
}


