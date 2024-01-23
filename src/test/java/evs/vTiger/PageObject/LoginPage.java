package evs.vTiger.PageObject;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

import evs.vTiger.Utilities.webUtil;

public class LoginPage {

	WebDriver ldriver;
	webUtil webutil;
	
	
	public LoginPage(WebDriver rdriver){
		ldriver=rdriver;
		PageFactory.initElements(rdriver, this);
		webutil=new webUtil();
		
	}
	
	public void enterUserName(String usernm) {
		webutil.inputValue("UserName ", By.xpath("//input[@name='user_name']"), usernm);
	}
	public void enterUserPassword(String password) {
		webutil.inputValue("Password text Box", By.xpath("//input[@name='user_password']"), password);
	}
	public void userClickOnLogin() {
		webutil.click("Login Button ", By.xpath("//input[@name='Login']"));
	}
	
	public void clickOnSales() {
		webutil.click("Sales Link", By.xpath("//a[text()='Sales']"));
	}
	public void clickOnLeads() {
		webutil.click("Leads Link", By.xpath("//td[@class='level2SelTab']//a[text()='Leads']"));
	}
	public String verifyHeader() {
		return webutil.getText("Leads details page ", By.xpath("//td[text()='Sales > ']"));
	}
	public void clickOnCreateNewLead() {
		webutil.click("Create new Leads", By.xpath("//img[@alt='Create Lead...']"));
	}
	public void clickOnSave() {
		webutil.click("Save Button", By.xpath("//div[@id='basicTab']//table[@class='dvtContentSpace']/descendant-or-self::div[2]//input[@title='Save [Alt+S]']"));
	}
}
