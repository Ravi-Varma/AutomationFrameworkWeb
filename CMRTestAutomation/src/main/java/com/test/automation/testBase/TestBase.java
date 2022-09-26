package com.test.automation.testBase;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Iterator;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import com.test.automation.customListner.Listener;




public class TestBase {
	public static final Logger log= Logger.getLogger(TestBase.class.getName());
	public static WebDriver driver;
	public Properties OR = new Properties();
	public static ExtentReports extent;
	public static ExtentTest test;
	//Listener listner;
	
	static {
		File srcDir = new File(System.getProperty("user.dir") + "/src/main/java/com/test/automation/report");
		File destDir = new File(System.getProperty("user.dir") + "/src/main/java/com/test/automation/oldreport");
		try {
			FileUtils.copyDirectory(srcDir, destDir);
			FileUtils.deleteDirectory(srcDir);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Calendar calendar = Calendar.getInstance();
		SimpleDateFormat formater = new SimpleDateFormat("dd_MM_yyyy_hh_mm_ss");
		extent = new ExtentReports(System.getProperty("user.dir") + "/src/main/java/com/test/automation/report/test" + formater.format(calendar.getTime()) + ".html", false);
			
	}
	
	public void loadData() throws IOException {
		File file = new File(System.getProperty("user.dir") + "/src/main/java/com/test/automation/config/config.properties");
		FileInputStream f = new FileInputStream(file);
		OR.load(f);

	}
	
	public void inti() throws IOException{
		loadData();
		selectBrowser(OR.getProperty("browser"));
		getUrl(OR.getProperty("url"));
		//listner =new Listener(driver);
		String log4jConfPath="log4j.properties";
		PropertyConfigurator.configure(log4jConfPath);
	}
	
	
	
	public void selectBrowser(String browser){
		if(browser.equalsIgnoreCase("firefox")){
			System.setProperty("webdriver.gecko.driver", System.getProperty("user.dir") + "/drivers/geckodriver.exe");
			log.info("Creating object of"+browser);
			driver = new FirefoxDriver();
		}else if (browser.equalsIgnoreCase("chrome")) {
			System.out.println(System.getProperty("user.dir"));
			System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") + "/drivers/chromedriver.exe");
			driver = new ChromeDriver();
		}
		
	}
	
	public void getUrl(String url){
		log.info("Navigating to "+url);
		driver.get(url);
		driver.manage().window().maximize();
		driver.manage().deleteAllCookies();
		driver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	}
	
	public void waitForElement(int timeOutInSeconds,WebElement element){
		WebDriverWait wait = new WebDriverWait(driver,timeOutInSeconds); 
		wait.until(ExpectedConditions.visibilityOf(element));
		
	}
	
	public void getScreenShot(String name) {

		Calendar calendar = Calendar.getInstance();
		SimpleDateFormat formater = new SimpleDateFormat("dd_MM_yyyy_hh_mm_ss");

		File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);

		try {
			String reportDirectory = new File(System.getProperty("user.dir")).getAbsolutePath() + "/src/main/java/com/test/automation/screenShot/";
			File destFile = new File((String) reportDirectory + name + "_" + formater.format(calendar.getTime()) + ".png");
			FileUtils.copyFile(scrFile, destFile);
			// This will help us to link the screen shot in testNG report
			Reporter.log("<a href='" + destFile.getAbsolutePath() + "'> <img src='" + destFile.getAbsolutePath() + "' height='100' width='100'/> </a>");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public Iterator<String> getAllWindows() {
		Set<String> windows = driver.getWindowHandles();
		Iterator<String> itr = windows.iterator();
		return itr;
	}
	
	public String captureScreen(String fileName) {
		if (fileName == "") {
			fileName = "blank";
		}
		File destFile = null;
		Calendar calendar = Calendar.getInstance();
		SimpleDateFormat formater = new SimpleDateFormat("dd_MM_yyyy_hh_mm_ss");

		File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);

		try {
			String reportDirectory = new File(System.getProperty("user.dir")).getAbsolutePath() + "/src/main/java/com/test/automation/screenShot/";
			destFile = new File((String) reportDirectory + fileName + "_" + formater.format(calendar.getTime()) + ".png");
			FileUtils.copyFile(scrFile, destFile);
			// This will help us to link the screen shot in testNG report
			Reporter.log("<a href='" + destFile.getAbsolutePath() + "'> <img src='" + destFile.getAbsolutePath() + "' height='100' width='100'/> </a>");
		} catch (IOException e) {
			e.printStackTrace();
		}
		return destFile.toString();
	}
	
	public void getresult(ITestResult result) {
		if (result.getStatus() == ITestResult.SUCCESS) {
			
			test.log(LogStatus.PASS, result.getName() + " test is pass");
			String screen = captureScreen("");
			test.log(LogStatus.PASS, test.addScreenCapture(screen));
		} else if (result.getStatus() == ITestResult.SKIP) {
			test.log(LogStatus.SKIP, result.getName() + " test is skipped and skip reason is:-" + result.getThrowable());
		} else if (result.getStatus() == ITestResult.FAILURE) {
			test.log(LogStatus.ERROR, result.getName() + " test is failed" + result.getThrowable());
			String screen = captureScreen("");
			test.log(LogStatus.FAIL, test.addScreenCapture(screen));
		} else if (result.getStatus() == ITestResult.STARTED) {
			test.log(LogStatus.INFO, result.getName() + " test is started");
		}
	}
	
	@AfterMethod()
	public void afterMethod(ITestResult result) {
		getresult(result);
		driver.close();
		
	}

	@BeforeMethod()
	public void beforeMethod(Method result) {
		test = extent.startTest(result.getName());
		test.log(LogStatus.INFO, result.getName() + " test Started");
	}

	@AfterClass(alwaysRun = true)
	public void endTest() {
		closeBrowser();
	}

	public void closeBrowser() {
		driver.quit();
		log.info("browser closed");
		extent.endTest(test);
		extent.flush();
	}
}
