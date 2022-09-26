package com.test.automation.test;

import java.io.IOException;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.test.automation.gmailPageAction.HomePage;
import com.test.automation.gmailPageAction.LoginPage;
import com.test.automation.testBase.TestBase;




public class Login extends TestBase {
	public static final Logger log= Logger.getLogger(Login.class.getName());
	LoginPage loginpage;
	HomePage homepage;
	
	@BeforeMethod
	public void setup() throws IOException{
		
		inti();
		loginpage=new LoginPage();
		
	}
	
	
	@Test(priority=1)
	public void loginPageTitleTest(){
		log.info("============================Staring loginPageTitleTest====================");
		String title=loginpage.validateLoginPageTitle();
		Assert.assertEquals(title, "#1 Free CRM for Any Business: Online Customer Relationship Software");
		log.info("============================End of loginPageTitleTest====================");
	}
	
	@Test(priority=2)
	public void cmrLogoTest(){
		log.info("============================Staring cmrLogoTest====================");
		boolean flag=loginpage.validateCMRLogo();
		Assert.assertTrue(flag);
		log.info("============================End of cmrLogoTest====================");
	}
	
	@Test(priority=3)
	public void teslogin() throws InterruptedException{
		log.info("============================Staring teslogin====================");
		homepage=loginpage.login(OR.getProperty("username"), OR.getProperty("password"));
		
		//Assert.assertEquals(dashBoard.loginMessage(), "Home");
		log.info("============================End of teslogin====================");
	}
	
	
	
	
//	@AfterClass
//	public void endtest(){
//		driver.close();
//		
//	}

}
