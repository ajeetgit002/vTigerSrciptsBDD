package evs.vTiger.PageObject;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.PageFactory;

import evs.vTiger.Utilities.webUtil;

public class SearchLeadsPage {

	WebDriver ldriver;
	webUtil webutil;
	
	
	public SearchLeadsPage(WebDriver rdriver){
		ldriver=rdriver;
		PageFactory.initElements(rdriver, this);
		webutil=new webUtil();
		
	}
	
	public void enterNameInSearchText(String leadsName) {
		webutil.inputValue("search Text box", By.xpath("//input[@name='search_text']"), leadsName);
	}
	public void selectDrpsearch() {
		webutil.selectByValue(By.name("search_field"), "firstname", "specific Name For Search");
	}
	public void clickOnsearchButton() {
		webutil.click("Search Button ", By.xpath("//input[@name='submit']"));
	}
	public  boolean heders(String uniqueAccNameGetData,String dataForVrfy) {
		WebDriver driver=new EdgeDriver();
		List<WebElement> list=driver.findElements(By.xpath("//table[@class='lvt small']//tr[1]//td"));
				boolean found=false;

		int colCount=0;
				
				for(int i=0;i<=list.size()-1;i++) {
				WebElement we=	list.get(i);
				String headers=we.getText();
				if(uniqueAccNameGetData.equals(headers)) {
					i=i+1;
					colCount=i;
					
				}
				
				
			}
				List<WebElement> weuniqueColDataList=driver.findElements(By.xpath("//table[@class='lvt small']//td["+colCount+"]"));
				
				for(int j=0;j<=weuniqueColDataList.size()-1;j++) {
				WebElement weUniueData=	weuniqueColDataList.get(j);
				String allUniqueData=weUniueData.getText();
				if(dataForVrfy.equalsIgnoreCase(allUniqueData)) {
					found=true;
				}
				}
				driver.quit();
				return found;
			}
	
	public void clickCheckBox() {
		
		webutil.click("Check Box", By.xpath("//input[@id='17']"));
	}
	public void clickDelete() {
		webutil.click("Delete Button", By.xpath("//input[@value='Delete']"));
	}
	
}
