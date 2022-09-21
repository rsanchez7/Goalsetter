package Goalsetter.config.report;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class TestListener implements ITestListener {

  private static final Logger LOGGER = LoggerFactory.getLogger(TestListener.class.getName());

  @Override
  public void onTestStart(ITestResult result) {
    LOGGER.info("Comenzando caso de prueba : " + result.getName());
  }

  @Override
  public void onTestSuccess(ITestResult result) {
    LOGGER.info("Caso de prueba " + result.getName() + " exitoso!");
  }

  @Override
  public void onTestFailure(ITestResult result) {
    LOGGER.error("Caso de prueba " + result.getName() + " fallido");
  }

  @Override
  public void onTestSkipped(ITestResult result) {
    LOGGER.info("Caso de prueba " + result.getName() + " skippeado, verifique");
  }

  @Override
  public void onTestFailedButWithinSuccessPercentage(ITestResult result) {

  }

  @Override
  public void onStart(ITestContext context) {

  }

  @Override
  public void onFinish(ITestContext context) {

  }
}


