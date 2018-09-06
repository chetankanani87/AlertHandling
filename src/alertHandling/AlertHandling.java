package alertHandling;

import java.io.IOException;

//import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;

import lib.BrowserDriverUtility;
import lib.EmailWithAttachmentUtility;
import lib.ExtentReportUtility;
import lib.ScreenshotUtility;

public class AlertHandling {
	WebDriver dr;
	ExtentReports report;
	ExtentTest logger;
	WebElement ele;
	String path1, path2, path3, path4;

	@BeforeTest
	public void InvokeBrowser() {
		try {
			dr = BrowserDriverUtility.InvokeBrowser("webdriver.chrome.driver",
					"C:\\Chetan\\SeleniumSuite\\WebDrivers\\chromedriver.exe",
					"http://www.ksrtc.in");
			report = ExtentReportUtility.InvokeExtentReport();
			logger = report.createTest("File Upload Test");
			path1 = ScreenshotUtility.CaptureScreenshot(dr, "1_MainPage");
			logger.pass("Main Page - Screenshot taken.", MediaEntityBuilder.createScreenCaptureFromPath(path1).build());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void IFrameHandle() {
		try {
			dr.findElement(By.id("searchBtn")).click();
			Thread.sleep(2000);
			
			//Alert alert = dr.switchTo().alert();
			//alert.accept(); 
			
			String actualMsg = dr.switchTo().alert().getText();
			System.out.println("Text in the alert box is: " + actualMsg);
			
			String expectedMsg = "Please select start place.";
			Assert.assertEquals(actualMsg, expectedMsg);
			
			dr.switchTo().alert().accept();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@AfterTest
	public void tearDown() {
		try {
			EmailWithAttachmentUtility.SendEmail("Test Case for Alerts Handling is Passed - File is uploaded successfully...!!!",
					"Congratulations...Bro!!!", path1, "Screenshot of Main page which is working fine...!!!");
			report.flush();
			Thread.sleep(1000);
			dr.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
