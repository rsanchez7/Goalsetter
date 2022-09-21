package Goalsetter.config.report;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.*;
import org.testng.xml.XmlSuite;

import java.io.File;
import java.io.IOException;
import java.util.*;


public class ExtentReportListener implements IReporter {


  private static final Logger LOGGER = LoggerFactory
      .getLogger(ExtentReportListener.class.getName());

  private static final String FILE_NAME = "/report.html";
  private static final String OUTPUT_FILES_FOLDER = "/target/outputfiles";
  private static final String OUTPUT_REPORTS_FOLDER = "/outputreports";
  private static final String OUTPUT_FOLDER = OUTPUT_FILES_FOLDER + OUTPUT_REPORTS_FOLDER;
  private static final String SCREENSHOT_PATH_FOLDER = "/target/outputfiles/errorscreenshotfiles/";
  private static final String SCREENSHOT_PATH = "../errorscreenshotfiles/";
  private static Map<String, String> screenshotList = new HashMap<String, String>();
  private static String baseDir = System.getProperty("user.dir");
  private ExtentReports extent;


  public static void captureScreenshot(WebDriver driver, String uniqueId) throws IOException {
    createOutputReportFolder();
    TakesScreenshot ts = (TakesScreenshot) driver;
    File source = ts.getScreenshotAs(OutputType.FILE);
    String imageFilename = uniqueId + ".png";
    String dest = String
        .format("%s%s%s", baseDir, SCREENSHOT_PATH_FOLDER,
            imageFilename);
    FileUtils.copyFile(source, new File(dest));
    screenshotList.put(uniqueId, String.format("%s%s", SCREENSHOT_PATH, imageFilename));
  }

  private static void createOutputReportFolder() {
    File dir = new File(
        String.format("%s%s%s", baseDir, OUTPUT_FILES_FOLDER, OUTPUT_REPORTS_FOLDER));
    if (!dir.exists()) {
      dir.mkdirs();
    }
  }

  @Override
  public void generateReport(List<XmlSuite> list, List<ISuite> suites, String s) {

    init();

    for (ISuite suite : suites) {
      Map<String, ISuiteResult> result = suite.getResults();

      for (ISuiteResult r : result.values()) {
        ITestContext context = r.getTestContext();

        buildTestNodes(context.getPassedTests(), Status.PASS);
        buildTestNodes(context.getFailedTests(), Status.FAIL);
        buildTestNodes(context.getSkippedTests(), Status.SKIP);

      }
    }

    for (String name : Reporter.getOutput()) {
      extent.setTestRunnerOutput(name);
    }

    extent.flush();

  }

  private void init() {
    createOutputReportFolder();

    ExtentHtmlReporter extentHtmlReporter = new ExtentHtmlReporter(
        baseDir + OUTPUT_FOLDER + FILE_NAME);

    extentHtmlReporter.config().setDocumentTitle("Reporte de Testing");
    extentHtmlReporter.config().setReportName("Reporte de ejecución de Test Automatizados");
    extentHtmlReporter.config().setTheme(Theme.STANDARD);
    extentHtmlReporter.config().setEncoding("UTF-8");

    extent = new ExtentReports();
    extent.attachReporter(extentHtmlReporter);
    extent.setReportUsesManualConfiguration(true);
  }

  private void buildTestNodes(IResultMap tests, Status status) {

    ExtentTest test;

    if (tests.size() > 0) {
      for (ITestResult result : tests.getAllResults()) {
        test = extent.createTest(result.getMethod().getMethodName());

        for (String group : result.getMethod().getGroups()) {
          test.assignCategory(group);
        }

        if (result.getThrowable() != null) {
          test.log(status, result.getThrowable());
        } else {
          test.log(status,
              String
                  .format("%s%s%s", "testCases ", StringUtils.lowerCase(status.toString()), "ed"));
        }

        String descAll = result.getMethod().getDescription();
        String description = String.format("%s%s", "Descripción: ", testDescription(descAll));

        test.getModel()
            .setDescription(description);
        test.getModel().setStartTime(getTime(result.getStartMillis()));
        test.getModel().setEndTime(getTime(result.getEndMillis()));

        if (status == Status.FAIL) {
          try {
            Object testIdObj = result.getAttribute("IdTestCase");
            if (testIdObj != null) {
              String testId = testIdObj.toString();
              test.fail("Captura de pantalla :")
                  .addScreenCaptureFromPath(screenshotList.get(testId));

            }
          } catch (IOException e) {
            LOGGER.error(e.getMessage());
          }
        }
      }
    }
  }


  private Date getTime(long millis) {
    Calendar calendar = Calendar.getInstance();
    calendar.setTimeInMillis(millis);
    return calendar.getTime();
  }


  private String testDescription(String testDescription) {

    String descriptionEmptyMessage = "El test no tiene descripción";
    String description;

    if (testDescription == null || testDescription.equals("")) {
      description = descriptionEmptyMessage;
    } else {
      description = testDescription;
    }

    return description;
  }
}
