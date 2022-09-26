package com.test.automation.gmailPageAction;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;


import com.test.automation.testBase.TestBase;





public class LoginPage extends TestBase{
	public static final Logger log= Logger.getLogger(LoginPage.class.getName());
	
	@FindBy(name="username")
	WebElement username;
	
	@FindBy(name="password") 
	WebElement password;
	
	@FindBy(xpath="//input[@type='submit']")
	WebElement loginButton;
	
	@FindBy(xpath="//button[contains(text(),'Sign Up')]")
	WebElement signUpButton;
	
	@FindBy(xpath="//img[contains(@class,'img-responsive')]")
	WebElement cmrLogo;
	
	public LoginPage(){
		PageFactory.initElements(driver, this);
	}
	
	public String validateLoginPageTitle(){
		return driver.getTitle();
	}
	
	public boolean validateCMRLogo(){
		return cmrLogo.isDisplayed();
		
		
	}
	
	public HomePage login(String un,String pwd) throws InterruptedException{
		Thread.sleep(3000);
		username.sendKeys(un);
		log.info("The username is entered and the object is"+username.toString());
		password.sendKeys(pwd);
		log.info("The password is entered and object is"+password.toString());
		loginButton.click();
		log.info("Clicked on login button and the object is"+loginButton.toString());		
		return new HomePage();
	}

}
