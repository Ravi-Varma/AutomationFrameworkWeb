package com.test.automation.gmailPageAction;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.test.automation.testBase.TestBase;



public class HomePage extends TestBase{
	public static final Logger log= Logger.getLogger(LoginPage.class.getName());
	
	@FindBy(xpath = "//td[contains(text(),'User: Naveen K')]")
	WebElement userNameLabel;

	@FindBy(xpath = "//a[contains(text(),'Contacts')]")
	WebElement contactsLink;
	
	@FindBy(xpath = "//a[contains(text(),'New Contact')]")
	WebElement newContactLink;
	

	@FindBy(xpath = "//a[contains(text(),'Deals')]")
	WebElement dealsLink;

	@FindBy(xpath = "//a[contains(text(),'Tasks')]")
	WebElement tasksLink;
	
	@FindBy(xpath="//div[@class='noprint']/table/tbody/tr/td[4]/a")
	WebElement logoutlink;
	
	// Initializing the Page Objects:
		public HomePage() {
			PageFactory.initElements(driver, this);
		}
		public String verifyHomePageTitle(){
			return driver.getTitle();
		}
		
		
		public boolean verifyCorrectUserName(){
			return userNameLabel.isDisplayed();
		}
		
		public ContactsPage clickOnContactsLink(){
			contactsLink.click();
			return new ContactsPage();
		}
		
		public DealsPage clickOnDealsLink(){
			dealsLink.click();
			return new DealsPage();
		}
		
		public TasksPage clickOnTasksLink(){
			tasksLink.click();
			return new TasksPage();
		}
		
		public void clickOnNewContactLink(){
			Actions action = new Actions(driver);
			action.moveToElement(contactsLink).build().perform();
			newContactLink.click();
			
		}
		
		public void logout(){
			switchToFrame();
			logoutlink.click();
			
		}
		public void switchToFrame(){
			driver.switchTo().frame("mainpanel");
			log.info("switched to the iframe");
		}
		
		public void switchToDefaultContent(){
			driver.switchTo().defaultContent();
			log.info("switched to the default Content");
		}
		

}
