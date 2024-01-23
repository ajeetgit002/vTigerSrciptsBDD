package evs.vTiger.Utilities ;
import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.ElementNotInteractableException;
import org.openqa.selenium.InvalidSelectorException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Point;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.google.common.io.Files;

import io.cucumber.java.After;
import io.cucumber.java.Before;


public class webUtil {
	
	
	
	private static WebDriver driver;
	
	private static ExtentReports extreport;
	
	private static ExtentTest extTest;
	
	
	
	
	@Before
	public static void createExtentReport() {
		extreport=new ExtentReports();
		File folder = new File("Reports");
		if(folder.exists()==false) {
			folder.mkdir();
		}
		ExtentSparkReporter extSpark=new ExtentSparkReporter(System.getProperty("user.dir")+"/Reports/" +"VtigerReports.html");
		extreport.attachReporter(extSpark);
	}  
	@Before
	public   void createTestReport(String testcaseName) {
		extTest=extreport.createTest(testcaseName);
	}
@After
	public void flushReport() {
		extreport.flush();
	}
	// ***********************Browser Launch Generic method*******************
	/*
	 * This method will take screenshot of page where will it find exception
	 * 
	 * @Param -String imagetName
	 * 
	 * @Return- not return
	 */
	public String screenShot(String imageName) {
		DateFormat datef = new SimpleDateFormat("MM_dd_yyyy HH_MM_ss a");
		String dateTime = datef.format(new Date());
		TakesScreenshot tss = (TakesScreenshot) driver;
		File source = tss.getScreenshotAs(OutputType.FILE);

		File folder = new File("SnapShots");
		//
		
		if (folder.exists() == false) {
			folder.mkdir();
		}
		
		File finaldestination = new File(System.getProperty("user.dir")+"/SnapShots/" + imageName + dateTime + ".png");
		String destination=finaldestination.getAbsolutePath();
		try {
			Files.copy(source, finaldestination);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return destination;
	}
	/*
	 * This method will Launch Browser
	 * 
	 * @Param - String browserName
	 * 
	 * @Return- no return
	 */

	public void launchbrowser(String browser) {

		try {
			switch (browser.toLowerCase()) {

			case "chrome":
				System.setProperty("webdriver.chrome.driver", "Drivers\\chromedriver.exe");
				driver = new ChromeDriver();
				
				extTest.log(Status.INFO, "Browser : launched Successfully ");

				break;
			case "firefox":
				System.setProperty("webdriver.gecko.driver", "Drivers\\geckodriver.exe");
				driver = new FirefoxDriver();
				extTest.log(Status.INFO, "Browser : launched Successfully ");
				break;
			case "edge":
				System.setProperty("webdriver.edge.driver", "Drivers\\msedgedriver.exe");
				driver = new EdgeDriver();
				extTest.log(Status.INFO, "Browser : launched Successfully ");
				break;
			default:
				extTest.log(Status.INFO, "Browser : name did not match ");
			}
		} catch (Exception e) {
			extTest.log(Status.FAIL, "Browser : did not launch  ");
			extTest.addScreenCaptureFromPath(screenShot(browser));
			throw e;
		}
		// it is implicit wait  by default run with driver for all element //
		
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		
	}

	// ****************driver generic method******************

	/*
	 * This method will wait for specific element until element not be enabled 
	 * @Param= int durationForWaitInSecond
	 * @Param=By objectForLocating
	 * @Param=String elementName
	 * @return -no return
	 */
	public void exWaitElementEnabled(int durationOfSecond,By locator,String elementName) {
		WebDriverWait exwaitObj=	new WebDriverWait(driver, Duration.ofSeconds(durationOfSecond));
		exwaitObj.until(ExpectedConditions.elementToBeClickable(locator));
		extTest.log(Status.PASS, elementName+"   Element is enabled and clickabled ");
		
	}
	/*
	 * This method will wait for specific element until page  not be loaded
	 * @Param= int durationForWaitInSecond
	 * @Param=By objectForLocating
	 * @Param=String elementName
	 * @return -no return
	 */
	
	public void exWaitElementPresence(int durationOfSecond,By locator,String elementName) {
		WebDriverWait exwaitObj=	new WebDriverWait(driver, Duration.ofSeconds(durationOfSecond));
		exwaitObj.until(ExpectedConditions.presenceOfElementLocated(locator));
		extTest.log(Status.PASS, elementName+"  Element is present on html ");
		
	}
	
	
	
	/*
	 * This method will wait for specific element until Element not be visible 
	 * @Param= int durationForWaitInSecond
	 * @Param=By objectForLocating
	 * @Param=String elementName
	 * @return -no return
	 */
	
	public void exWaitElementVisibility(int durationOfSecond,By locator,String elementName) {
		WebDriverWait exwaitObj=	new WebDriverWait(driver, Duration.ofSeconds(durationOfSecond));
		exwaitObj.until(ExpectedConditions.visibilityOfElementLocated(locator));
		extTest.log(Status.PASS, elementName+"  Element is visible on page ");
		
	}
	/*
	 * This method will wait for specific element until Element text not be changed
	 * @Param= int durationForWaitInSecond
	 * @Param=By objectForLocating
	 * @Param=String elementName
	 * @Param=String textForMatch
	 * @return -no return
	 */
	
	public void exWaitElementTextChange(int durationOfSecond,By locator,String elementName,String textForMactch) {
		WebDriverWait exwaitObj=	new WebDriverWait(driver, Duration.ofSeconds(durationOfSecond));
		exwaitObj.until(ExpectedConditions.textToBePresentInElementLocated(locator,textForMactch));
		extTest.log(Status.PASS, elementName+"  Element is present on html ");
		
	}
	
	public void acceptAlertPop() {
		driver.switchTo().alert().accept();
	}
	public void dismisedAlertPop() {
		driver.switchTo().alert().dismiss();
	}
	/* This method will refresh page */

	public void refresh() {
		driver.navigate().refresh();
		extTest.log(Status.INFO, " Page refreshed ");
	}

	/*
	 * This method will Back to previous page
	 * 
	 * @return no return
	 */
	public void back() {
		driver.navigate().back();
		extTest.log(Status.INFO, " Come on previous window Successfully");
	}

	/*
	 * This method will take you to the next page
	 * 
	 * @return -no return
	 */
	public void forword() {
		driver.navigate().forward();
		extTest.log(Status.INFO, " Come on next window Successfully");
	}

	/*
	 * This method will Maximize the window
	 * 
	 * @Return-Not return
	 */
	public void maximize() {
		driver.manage().window().maximize();
		extTest.log(Status.INFO, " Full screen window Successfully");
	}

	/*
	 * This method set the size of window in desktop
	 * 
	 * @Param - Dimension object
	 * 
	 * @Return - no return
	 * 
	 */
	public void setwindowSize(Dimension object) {
		driver.manage().window().setSize(object);
		extTest.log(Status.INFO, "  Successfully set Size of Window ");
	}

	/*
	 * This method set the Position of window in desktop
	 * 
	 * @Param - Point object
	 * 
	 * @Return - no return
	 * 
	 */
	public void setWindowPosition(Point object) {

		driver.manage().window().setPosition(object);
		extTest.log(Status.INFO, " Successfully set Location of Window ");
	}

	/*
	 * This method retrieve the size of Element
	 * 
	 * @Param - By object
	 * 
	 * @Param - String ElementName
	 * 
	 * @Return - Array int
	 * 
	 */
	public int[] getElementSize(String elementName, By locator) {
		WebElement we = searchElementOnHTML(locator,elementName);
		Dimension dimension = we.getSize();
		int hieghtOfElment = dimension.getHeight();
		int widthOfElement = dimension.getWidth();
		int elementSize[] = { hieghtOfElment, widthOfElement };
extTest.log(Status.INFO, "Retrieve Size of element - ["+elementName+" ] and return the 1 : height and 2: width ");
		return elementSize;

	}
	/*
	 * This method retrieve the Location of Element
	 * 
	 * @Param - By object
	 * 
	 * @Param - String ElementName
	 * 
	 * @Return - Array int
	 * 
	 */

	public int[] getElementLocation(String elementName, By locator) {
		WebElement we = searchElementOnHTML(locator,elementName);
		Point point = we.getLocation();
		int positionOfXcoOrdinate = point.getX();
		int positionOfYcoOrdinate = point.getY();
		int elementLocation[] = { positionOfXcoOrdinate, positionOfYcoOrdinate };
		extTest.log(Status.INFO, "Retrieve location  of element - [ "+elementName+" ] and return the 1 : X cordinate and 2: Y coordinate ");
		return elementLocation;

	}

	

	/*
	 * This method will Open Url at browser
	 * 
	 * @Param-String url
	 * 
	 * @Return- not return
	 */
	public void openUrl(String url) {
		try {
			driver.get(url);
			extTest.log(Status.INFO, url+" - Opened Successfully  ");
		} catch (Exception e) {
			extTest.log(Status.FAIL, url+" - did not  Open ");
			throw e;
		}
	}

	/*
	 * This method will take focus on Frame
	 * 
	 * @Param- By object
	 * 
	 * @Param- String elementName
	 * 
	 * @Return- not return
	 */
	public void switchToFrameByWebElement(By farmelocator, String elementName) {
		WebElement we = searchElementOnHTML(farmelocator,elementName);
		driver.switchTo().frame(we);
		extTest.log(Status.INFO, elementName+" -  Focus on the current iframe  ");

	}

	/*
	 * This method will take focus on main window from frame
	 * 
	 * @Return- not return
	 */
	public void switchToDefaultContent() {
		driver.switchTo().defaultContent();
		extTest.log(Status.INFO,"  Focus on the main page from iFrame ");
	}
	/*
	 * This method will switch focus on window behalf title
	 * 
	 * @Param- String title
	 * 
	 * @Return- not return
	 */

	public void switchTowindowByTitle(String switchWindowTitle) {
		try {
		Set<String> windows = driver.getWindowHandles();

		for (String window : windows) {
			driver.switchTo().window(window);
			String title = driver.getTitle();
			if (title.equals(switchWindowTitle)) {
				extTest.log(Status.INFO,"  Focus on the New Window by title  ");
				break;
			}
		}
		}catch(Exception e) {
			extTest.log(Status.FAIL,"  Window did not handal and Focus Not change  - "+switchWindowTitle);
		}
	}

	/*
	 * This method will switch focus on window behalf contain title
	 * 
	 * @Param- String containfromTitle
	 * 
	 * @Return- not return
	 */
	public void switchTowindowByTitleContains(String switchWindowTitle) {
		try {
		Set<String> windows = driver.getWindowHandles();

		for (String window : windows) {
			driver.switchTo().window(window);
			String title = driver.getTitle();
			if (title.contains(switchWindowTitle)) {
				extTest.log(Status.INFO,"  Focus on the New Window by some contains of matching  title  ");
				break;
			}
		}
		}catch(Exception e) {
			extTest.log(Status.FAIL,"  Window did not handal and Focus Not change  - "+switchWindowTitle);
		}
		
	}
	/*
	 * This method will switch focus on window behalf URL
	 * 
	 * @Param- String URL
	 * 
	 * @Return- not return
	 */

	public void switchTowindowByURL(String switchWindowURL) {
		try {
		Set<String> windows = driver.getWindowHandles();

		for (String window : windows) {
			driver.switchTo().window(window);
			String urkValue = driver.getCurrentUrl();
			if (urkValue.equals(switchWindowURL)) {
				extTest.log(Status.INFO,"  Focus on the New Window by URL  ");
				break;
			}
		}
		}catch(Exception e) {
			extTest.log(Status.FAIL,"  Window did not handal and Focus Not change  and url is - "+switchWindowURL);
		}
		}
	
	/*
	 * This method will switch focus on window behalf URL contain
	 * 
	 * @Param- String containFromURL
	 * 
	 * @Return- not return
	 */

	public void switchTowindowByURLConatains(String switchWindowURL) {
		try {
		Set<String> windows = driver.getWindowHandles();

		for (String window : windows) {
			driver.switchTo().window(window);
			String urkValue = driver.getCurrentUrl();
			if (urkValue.contains(switchWindowURL)) {
				extTest.log(Status.INFO,"  Focus on the New Window by some contains of matching  URL - "+switchWindowURL);
				break;
			}
		}
		}catch(Exception e) {
			extTest.log(Status.FAIL,"  Window did not handal and Focus Not change  and url is -  "+switchWindowURL);
			
		}
		
	}
	/*
	 * This method will retrieve url of page
	 * 
	 * @Param- String pageName
	 * 
	 * @Return- not return
	 */

	public String getCurrentURL(String pageName) {
		String url = driver.getCurrentUrl();
		extTest.log(Status.INFO, pageName+"URL is -[  "+url+" ] Successfilly retrieve the url of the page ");

		return url;
	}
	/*
	 * This method will retrieve title of page
	 * 
	 * @Param- String pagetitle
	 * 
	 * @Return- not return
	 */

	public String getTitle(String pageName) {
		String title = driver.getTitle();
		extTest.log(Status.INFO, "Successfully return the title of page : - "+pageName);
		return title;

	}

	/*
	 * This method will checked all check boxses
	 * 
	 * @Param - By object
	 * 
	 * @Param- String elementCollectionName
	 * 
	 * @Return- not return
	 */
	public void ckeckedAllCheckBoxes(By locator, String elementCollectionName) {
		List<WebElement> listCheckedBoxes = driver.findElements(locator);
		for (int i = 0; i < listCheckedBoxes.size(); i++) {
			WebElement weCheckeBox = listCheckedBoxes.get(i);
			if (weCheckeBox.isSelected() == false) {
				weCheckeBox.click();
			}
			extTest.log(Status.INFO, "Successfully checked all check Boxes  Which is : - "+elementCollectionName);
		}
	}

	/*
	 * This method will unchecked all check boxses
	 * 
	 * @Param - By object
	 * 
	 * @Param- String elementCollectionName
	 * 
	 * @Return- not return
	 */
	public void unckeckedAllCheckBoxes(By locator, String elementCollectionName) {
		List<WebElement> listCheckedBoxes = driver.findElements(locator);
		for (int i = 0; i < listCheckedBoxes.size(); i++) {
			WebElement weCheckeBox = listCheckedBoxes.get(i);
			if (weCheckeBox.isSelected() == true) {
				weCheckeBox.click();
			}
			extTest.log(Status.INFO, "Successfully unchecked all check Boxes  Which is : "+elementCollectionName);
		}

	}

	/*
	 * This method will search element on HTML
	 * 
	 * @Param - By object
	 * 
	 * @Return- List of String innertext all element
	 */
	public List<String> searchElementsOnHTML(By locator) {

		List<WebElement> welist = null;
		ArrayList<String> al = new ArrayList<String>();
		try {
			welist = driver.findElements(locator);
			for (WebElement we : welist) {
				String innerTextValue = we.getText();
				al.add(innerTextValue);
				extTest.log(Status.INFO, "Successfully : Retrieve text of all element and Return  List of all Inner Text  ");
			}

		} catch (StaleElementReferenceException e) {
			welist = driver.findElements(locator);
			for (WebElement we : welist) {
				String innerTextValue = we.getText();
				al.add(innerTextValue);
				extTest.log(Status.INFO, "Successfully : Retrieve text of all element and Return  List of all Inner Text But In the second attempt  ");
			}
		} catch (Exception e) {
			extTest.log(Status.FAIL, "Due to Exception Not find any text ");
			
			throw e;
		}

		return al;

	}

	/*
	 * This method will search element on HTML
	 * 
	 * @Param - By object
	 * 
	 * @Return- WebElement object
	 * 
	 */
	public WebElement searchElementOnHTML(By locator,String elementName) {
		WebElement we = null;
		try {
			we = driver.findElement(locator);
			extTest.log(Status.INFO, " Element found By locator ");
		} catch (InvalidSelectorException e) {
			
			extTest.log(Status.FAIL, " Syntax of Xpath is wrong and check it care fully ");
			
			throw e;

		} catch (NoSuchElementException e) {
			try {
			we = driver.findElement(locator);
			extTest.log(Status.INFO, " Element found second attempt ");
			}catch(Exception ex) {
				extTest.log(Status.FAIL, " Due to Exception not find element ");
				extTest.addScreenCaptureFromPath(screenShot(elementName));
				throw ex;
				
			}
		}

		return we;

	}

	/*
	 * This method will close All browser
	 * 
	 */
	public void tearDown_Quit()  {
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		driver.quit();
	}
	/*
	 * This method will close current working browser
	 * 
	 */

	public void closeWindow() {
		driver.close();
	}
	// ******                      Actions class generic method                      *****/

	/*
	 * This method will mouseOver on the element After that click on element
	 * 
	 * @Param - By object
	 * @Param - String ElementName
	 * @Return- Not return AnyThings
	 * 
	 */
	public void actionClick(By locator,String elementName) {
		WebElement we = searchElementOnHTML(locator,elementName);
		Actions act = new Actions(driver);

		try {
			act.click(we).build().perform();
			extTest.log(Status.INFO, "Successfully : Maouse  over and After that click on Element : "+elementName);
		} catch (ElementNotInteractableException e) {
			jsMouseOver(locator,elementName);
			jsClickMethod(locator,elementName);
			
		} catch (StaleElementReferenceException e) {
			we = driver.findElement(locator);
			act.click(we).build().perform();
			
			extTest.log(Status.INFO, "mouse clicked on element after handling stale exception : - "+elementName);
		} catch (Exception e) {
			extTest.log(Status.FAIL, "Due to Exception Action not PerForm on Element :  - "+elementName);
			extTest.addScreenCaptureFromPath(screenShot(elementName));
			throw e;
		}
	}

	/*
	 * This method will mouseOver on the element After that send value in the Text
	 * box
	 * 
	 * @Param-String elementName
	 * 
	 * @Param - By object
	 * 
	 * @Param - String valueFor Send In textBox
	 * 
	 * @Return- Not return AnyThings
	 * 
	 */
	public void actionInputValue(String elementName, By locator, String value) {
		WebElement we = searchElementOnHTML(locator,elementName);
		Actions act = new Actions(driver);

		try {
			act.sendKeys(we, value).build().perform();
			extTest.log(Status.INFO, "Successfully mouceOver and After that enter value in the ["+elementName+" ] text Box ");
			
		} catch (ElementNotInteractableException e) {
			jsMouseOver(locator,elementName);
			jsInputValueMethod(locator, value,elementName);
			
		} catch (StaleElementReferenceException e) {
			we = driver.findElement(locator);
			act.sendKeys(we, value).build().perform();
			extTest.log(Status.INFO, "Successfully mouceOver and After that enter value in the ["+elementName+" ] text Box After Handaling Stale Exception ");
		} catch (Exception e) {
			extTest.log(Status.FAIL, "Due to Exception Action not PerForm on Element :  - "+elementName);
			extTest.addScreenCaptureFromPath(screenShot(elementName));
			throw e;
		}
	}

	/*
	 * This method will mouseOver on the element
	 * 
	 * @Param - By object
	 * @Param -String elementName
	 * @Return- Not return AnyThings
	 * 
	 */
	public void mouseOver(By locator, String elementName) {
		WebElement we = searchElementOnHTML(locator,elementName);
		Actions act = new Actions(driver);

		try {
			act.moveToElement(we).build().perform();
			extTest.log(Status.INFO, "Successfully mouceOver on the element  ["+elementName+" ] ");
			
		} catch (ElementNotInteractableException e) {
			jsMouseOver(locator,elementName);
			

		} catch (StaleElementReferenceException e) {
			we = driver.findElement(locator);
			act.moveToElement(we).build().perform();
			extTest.log(Status.INFO, "Successfully mouceOver on the element  ["+elementName+" ] After Handaling Stale Exception");
		} catch (Exception e) {
			extTest.log(Status.FAIL, "Due to Exception Action not perform ");
			extTest.addScreenCaptureFromPath(screenShot(elementName));
			throw e;
		}
	}

	/*
	 * This method will pick the element from one place and drop another place
	 * 
	 * @Param- By dragObject
	 * 
	 * @Param - By dropObject
	 * 
	 * @Return- Not return AnyThings
	 * 
	 */
	public void actionDragandDrop(By darglocator, By droplocator,String elementName) throws InterruptedException {
		WebElement we = searchElementOnHTML(darglocator,elementName);
		WebElement we1 = searchElementOnHTML(droplocator,elementName);
		Actions act = new Actions(driver);

		try {
			act.dragAndDrop(we, we1).build().perform();
			extTest.log(Status.INFO, "Successfully Drag one place and Drop another Place  ");

		} catch (ElementNotInteractableException e) {
			jsdragAndDrop(darglocator, droplocator,elementName);

		} catch (StaleElementReferenceException e) {
			we = driver.findElement(darglocator);
			we1 = driver.findElement(droplocator);

			act.dragAndDrop(we, we1).build().perform();
			extTest.log(Status.INFO, "Successfully Drag from one place and Drop another place  After handaled Stale Exceptiopn ");

		} catch (Exception e) {
			extTest.log(Status.FAIL, "Due to Exception Darg and Drop Action not perform ");
			extTest.addScreenCaptureFromPath(screenShot(elementName));
			throw e;
		}
	}

	/*
	 * This method will perform vertical scroll or up-down /down-up
	 * 
	 * @Param - By object
	 * 
	 * @Return- Not return AnyThings
	 * 
	 */
	public void actionScroll(By locator,String elementName) throws InterruptedException {
		WebElement we = searchElementOnHTML(locator,elementName);
		Actions act = new Actions(driver);
		try {
			act.scrollToElement(we).build().perform();
			extTest.log(Status.INFO, "Successfully Scroll to Element  ");

		} catch (ElementNotInteractableException e) {
			jsMouseOver(locator,elementName);
			jsScroll(locator);

		} catch (StaleElementReferenceException e) {
			we = driver.findElement(locator);
			act.scrollToElement(we).build().perform();
			extTest.log(Status.INFO, "Successfully Scroll to Element After Handling Stale Exception ");

		} catch (Exception e) {
			extTest.log(Status.FAIL, "Due to Exception Scroll Action Not Perform  ");
			extTest.addScreenCaptureFromPath(screenShot(elementName));
			throw e;
		}

	}

	/*
	 * This method will mouseOver on the element After that Double click on element
	 * 
	 * @Param - By object
	 * 
	 * @Return- Not return AnyThings
	 * 
	 */
	public void actionDoubleClick(By locator ,String elementName) {
		WebElement we = searchElementOnHTML(locator,elementName);
		Actions act = new Actions(driver);
		try {
			act.doubleClick(we).build().perform();
			extTest.log(Status.INFO, "Successfully perform double Click on  Element [  "+elementName+" ]");

		} catch (ElementNotInteractableException e) {
			jsMouseOver(locator,elementName);
			jsDoubleClick(locator,elementName);

		} catch (StaleElementReferenceException e) {
			we = driver.findElement(locator);
			act.doubleClick(we).build().perform();
			extTest.log(Status.INFO, "Successfully perform double Click on  Element [  "+elementName+" ] After Handaling Stale Exception ");
		} catch (Exception e) {
			extTest.log(Status.FAIL, "Due To Exception Double Click Action not perform on element : [ " +elementName+"]");
			extTest.addScreenCaptureFromPath(screenShot(elementName));
			throw e;
		}

	}
	// *******************Select class generic method**************

	/*
	 * this method will select option from dropdown by option visible text
	 * 
	 * @Param - By
	 * 
	 * @Param - String Visible Text
	 * 
	 * @return - no return
	 */
	public void selectByText(By locator, String textToSelect,String elementName) {
		WebElement we = searchElementOnHTML(locator,elementName);
		Select selectDD = new Select(we);
		try {
			selectDD.selectByVisibleText(textToSelect);
			extTest.log(Status.INFO, "Successfully Selected text in Drop Down Through Visisble Text  ");
		} catch (ElementNotInteractableException e) {
			jsDropdown(locator, textToSelect);

		} catch (StaleElementReferenceException e) {
			we = searchElementOnHTML(locator,elementName);
			selectDD = new Select(we);
			selectDD.selectByVisibleText(textToSelect);
			extTest.log(Status.INFO, "Successfully Selected text in Drop Down Through Visisble Text After Handaling Stale Exception ");
		} catch (Exception e) {
			extTest.log(Status.FAIL, "Due To Exception DropDown From visible text was not selected   ");
			extTest.addScreenCaptureFromPath(screenShot(elementName));
			throw e;
		}
	}

	/*
	 * this method will select option from dropdown by optioon index number and
	 * index number starts from 0
	 * 
	 * @Param - By
	 * 
	 * @Param - int indexNumber
	 * 
	 * @return - no return
	 */
	public void selectByIndex(By locator, int optionIndex,String elementName) {
		WebElement we = searchElementOnHTML(locator,elementName);
		Select selectDD = new Select(we);
		try {
			selectDD.selectByIndex(optionIndex);
			extTest.log(Status.INFO, "Successfully Selected text in Drop Down Through Index   ");
		} catch (StaleElementReferenceException e) {
			we = searchElementOnHTML(locator,elementName);
			selectDD = new Select(we);
			selectDD.selectByIndex(optionIndex);
			extTest.log(Status.INFO, "Successfully Selected text in Drop Down Through Index After Handaling Stale Exception  ");
		} catch (Exception e) {
			extTest.log(Status.FAIL, "Due To Exception DropDown From Index was not selected    ");
			extTest.addScreenCaptureFromPath(screenShot(elementName));
			throw e;
		}
	}

	/*
	 * this method will select option from dropdown by option Attribute value
	 * 
	 * @Param - By
	 * 
	 * @Param - String value Attribute value
	 * 
	 * @return - no return
	 */
	public void selectByValue(By locator, String optionValueAttribute,String elementName) {
		WebElement we = searchElementOnHTML(locator,elementName);
		Select selectDD = new Select(we);
		try {
			selectDD.selectByValue(optionValueAttribute);
			extTest.log(Status.INFO, "Successfully Selected text in Drop Down Through Value Attribute Value    ");
		} catch (ElementNotInteractableException e) {
			jsDropdown(locator, optionValueAttribute);

		} catch (StaleElementReferenceException e) {
			we = searchElementOnHTML(locator,elementName);
			selectDD = new Select(we);
			selectDD.selectByValue(optionValueAttribute);
			extTest.log(Status.INFO, "Successfully Selected text in Drop Down Through Value Attribute Value After Handaling Stale Exception   ");
		} catch (Exception e) {
			extTest.log(Status.FAIL, "Due To Exception DropDown From Value  was not selected    ");
			extTest.addScreenCaptureFromPath(screenShot(elementName));
			throw e;
		}
	}

	/*
	 * this method will select option from dropdown by Partial text
	 *
	 * @Param - By
	 * 
	 * @Param - String partialvisibletext
	 * 
	 * @Param - Element Name
	 * 
	 * @return - no return
	 */
	public void selectByTextContains(By locator, String elementName, String selectText) {
		int indexNumber = -1;
		WebElement we = searchElementOnHTML(locator,elementName);
		Select slt = new Select(we);
		List<WebElement> weListOption = null;
		try {
			weListOption = slt.getOptions();
			for (int i = 0; i <= weListOption.size() - 1; i++) {
				WebElement weOption = weListOption.get(i);
				String optionText = weOption.getText();
				if (optionText.contains(selectText) == true) {
					indexNumber = i;
				}
			}
		} catch (StaleElementReferenceException e) {
			weListOption = slt.getOptions();
			for (int i = 0; i <= weListOption.size() - 1; i++) {
				WebElement weOption = weListOption.get(i);
				String optionText = weOption.getText();
				if (optionText.contains(selectText) == true) {
					indexNumber = i;
				}
			}

		} catch (Exception e) {
			throw e;
		}
		slt.selectByIndex(indexNumber);
		extTest.log(Status.INFO, "Successfully Selected text in Drop Down Through Partial Visisble text");
	}
	/*
	 * this method will retrieve All Selected Text from Drop Down
	 * 
	 * @Param - By
	 * 
	 * @Param -
	 * 
	 * @return - List of All selected text
	 */

	public List<String> getAllSelectedOptions(By locator,String elementName) {
		WebElement we = searchElementOnHTML(locator,elementName);
		Select slt = new Select(we);
		List<String> list = new ArrayList<>();
		List<WebElement> listofSelectedOptions = null;
		try {
			listofSelectedOptions = slt.getAllSelectedOptions();
			for (WebElement weOptions : listofSelectedOptions) {
				String strSelectedoptions = weOptions.getText();
				list.add(strSelectedoptions);
				extTest.log(Status.INFO, "Successfully Retrieve the Inner Text Of All Slected Option from Drop Down    ");
			}

		} catch (Exception e) {
			extTest.log(Status.INFO, "Due To Exception not retrieve inner Text from drop down ");
			e.printStackTrace();
		}
		return list;

	}

	/*
	 * this method will retrieve Select text from drop down
	 * 
	 * @Param - By
	 * 
	 * @Param - String Element name
	 * 
	 * @return - Select Text in Drop Down
	 */
	public String getDropdownSelectedText(By locator, String elementName) {
		WebElement we = searchElementOnHTML(locator,elementName);
		Select slt = new Select(we);
		String selectedOp = null;
		try {
			WebElement selectOption = slt.getFirstSelectedOption();
			selectedOp = selectOption.getText();
			extTest.log(Status.INFO, "Successfully : get Selected option in Drop Down and return it  ");
		} catch (Exception e) {
			
			extTest.log(Status.FAIL, "Due to Excetion not get selected otption from drop down");
			extTest.addScreenCaptureFromPath(screenShot(elementName));
			e.printStackTrace();
		}
		return selectedOp;
	}

	/*
	 * this method will retrieve All options from Drop Down
	 * 
	 * @Param - By
	 * 
	 * @Param -
	 * 
	 * @return - List of All options
	 */
	public List<String> getTextAllOptionsDropdown(By locator,String elementName) {
		WebElement we = searchElementOnHTML(locator,elementName);
		Select slt = new Select(we);
		List<String> alloptions = new ArrayList<String>();
		List<WebElement> allop = slt.getOptions();
		for (int i = 0; i < allop.size() - 1; i++) {
			WebElement weAllop = allop.get(i);
			String ailoption = weAllop.getText();
			alloptions.add(ailoption);
		}
		extTest.log(Status.INFO, "Successfully get the all Option from the drop down and return text of all   ");
		
		return alloptions;

	}

	/*
	 * this method will retrieve count of All options from Drop Down
	 * 
	 * @Param - By
	 * 
	 * @Param - String element name
	 * 
	 * @return - int number
	 */
	public int getAllOptionCount(By locator, String elementName) {
		WebElement we = searchElementOnHTML(locator,elementName);
		Select slt = new Select(we);
		List<WebElement> ListAlloptions = slt.getOptions();
		int allOptions = ListAlloptions.size();
		extTest.log(Status.INFO, "Successfully get the all Option from th drop down and return All count of text  ");
		return allOptions;

	}

	// **************java script generic method********************

	/*
	 * this method will select drop Down
	 * 
	 * @Param - By
	 * 
	 * @Param - String selectText
	 * 
	 * @return - not return anything
	 */

	public void jsDropdown(By locator, String value) {
		WebElement select = driver.findElement(locator);

		((JavascriptExecutor) driver).executeScript("var select = arguments[0]; for(var i = 0;"
				+ " i < select.options.length; i++){ if(select.options[i].text == arguments[1])"
				+ "{ select.options[i].selected = true; } }", select, value);
		extTest.log(Status.INFO, "Successfully Selected text in Drop Down Through Visisble Text  ,Through java Script ");
	}

	/*
	 * this method will type value in text Box
	 * 
	 * @Param - By
	 * 
	 * @Param - String value
	 * 
	 * @return - not return anything
	 */
	public void jsInputValueMethod(By locator, String value,String elementName) {
		try {
		WebElement we = driver.findElement(locator);
		JavascriptExecutor jse = (JavascriptExecutor) driver;
		jse.executeScript("arguments[0].value='" + value + "';", we);

		extTest.log(Status.INFO, "Successfully enter value in the ["+elementName+" ] text Box  through java script ");
	}catch(Exception e) {
		extTest.log(Status.FAIL, "Value not entered  in the ["+elementName+" ] text Box  through java script ");
	extTest.addScreenCaptureFromPath(screenShot(elementName));
	}
	}

	/*
	 * this method will click on button
	 * 
	 * @Param - By
	 * 
	 * @return - not return anything
	 */
	public void jsClickMethod(By locator,String elementName) {
		WebElement we = driver.findElement(locator);
		JavascriptExecutor jse = (JavascriptExecutor) driver;
		jse.executeScript("arguments[0].click();", we);
		extTest.log(Status.INFO, "Successfully : Maouse  over and After that click on Element Through Java script : - "+elementName);

	}

	/*
	 * this method will mouse over on element
	 * 
	 * @Param - By
	 * 
	 * @return - not return anything
	 */
	public void jsMouseOver(By locator,String elementName) {
		WebElement we = driver.findElement(locator);
		JavascriptExecutor jse = (JavascriptExecutor) driver;
		jse.executeScript(
				"if(document.createEvent){var evObj = " + "document.createEvent('MouseEvents');evObj.initEvent"
						+ "('mouseover',true, false); arguments[0].dispatchEvent(evObj);} "
						+ "else if(document.createEventObject) " + "{ arguments[0].fireEvent('onmouseover');}",
				we);
		extTest.log(Status.INFO, "Successfully mouceOver on the element  ["+elementName+" ] Through javascript");
	}

	/*
	 * this method will drag and drop from one place to another place to element
	 * 
	 * @Param - By drag
	 * 
	 * @Param - By drop
	 * 
	 * @return - not return anything
	 */
	public void jsdragAndDrop(By dargLocator, By droplocator,String elementName) throws InterruptedException {
		WebElement source = searchElementOnHTML(dargLocator,elementName);
		WebElement destination = searchElementOnHTML(droplocator,elementName);
		Actions act = new Actions(driver);
		act.moveToElement(source).pause(Duration.ofSeconds(1)).clickAndHold(source).pause(Duration.ofSeconds(1))
				.moveByOffset(1, 0).moveToElement(destination).moveByOffset(1, 0).pause(Duration.ofSeconds(1)).release()
				.perform();

		Thread.sleep(3000);

		System.out.println("Drag And Drop action perfrom by js");
	}

	/*
	 * this method will scroll Vertical
	 * 
	 * @Param - By
	 * 
	 * @return - not return anything
	 */
	public void jsScroll(By locator) throws InterruptedException {
		WebElement we = driver.findElement(locator);
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", we);
		Thread.sleep(2000);
		extTest.log(Status.INFO, "Successfully Scroll through java script   ");
	}

	/*
	 * this method will perform double click on element
	 * 
	 * @Param - By
	 * 
	 * @return - not return anything
	 */
	public void jsDoubleClick(By locator,String elementName) {
		WebElement we = driver.findElement(locator);
		JavascriptExecutor executor = (JavascriptExecutor) driver;
		executor.executeScript("arguments[0].dispatchEvent(new MouseEvent('dblclick', { bubbles: true }));", we);
		extTest.log(Status.INFO, "Successfully perform double Click on  Element [  "+elementName+" ]  Through Javascript ");
	}

	// ****************WebElement generic method****************
	/*
	 * this method will clear text Box
	 * 
	 * @Param - By
	 * 
	 * @return - not return anything
	 */
	public void clear(By locator,String elementName) {

		WebElement we=searchElementOnHTML(locator,elementName);
		we.clear();
		extTest.log(Status.INFO, "Successfully will  clear  text box ");

	}

	/*
	 * this method will click on element
	 * 
	 * @Param - By
	 * 
	 * @Param - String element Name
	 * 
	 * @return - not return anything
	 */
	public void click(String elementName, By locator) {
		WebElement we = searchElementOnHTML(locator,elementName);

		try {
			we.click();
			extTest.log(Status.INFO, "Successfully clicked on the [ "+elementName+" ] element  ");
		} catch (ElementNotInteractableException e) {

			jsClickMethod(locator,elementName);
		} catch (StaleElementReferenceException e) {
			we = driver.findElement(locator);
			we.click();
			extTest.log(Status.INFO, "Successfully clicked on the [ "+elementName+" ] element After Handaling stale Exception  ");
		} catch (Exception e) {
			extTest.log(Status.FAIL, "Element could not be clicked Due to Exception ");
			extTest.addScreenCaptureFromPath(screenShot(elementName));
			throw e;
		}
	}

	/*
	 * this method will Type vale in Text Box
	 * 
	 * @Param - By
	 * 
	 * @Param - String element Name
	 * 
	 * @Param- String value for send 
	 * 
	 * @return - not return anything
	 */
	public void inputValue(String elementName, By locator, String value) {
		WebElement we = searchElementOnHTML(locator,elementName);
		try {
			clear(locator,elementName);
			we.sendKeys(value);
			extTest.log(Status.INFO, "Successfully Value - ["+ value +"] enterd in the - [ "+elementName+" ] text box  ");
		} catch (ElementNotInteractableException e) {
			jsInputValueMethod(locator, value,elementName);
		} catch (StaleElementReferenceException e) {
			we = driver.findElement(locator);
			clear(locator,elementName);
			we.sendKeys(value);
			extTest.log(Status.INFO, "Successfully Value - [ "+value+" ] entered in the - [ "+elementName+"] text box : After Handaling Stale Exception ");
		} catch (Exception e) {
			extTest.log(Status.FAIL, "Value could not be Entered in - [ "+elementName+" ] text box  Due to Exception ");
			extTest.addScreenCaptureFromPath(screenShot(elementName));
		     
			throw e;
		}
		
	}

	/*
	 * this method will retrieve inner text of element
	 * 
	 * @Param - By
	 * 
	 * @Param - String elementName
	 * 
	 * @return - String innerTextOfElement
	 */
	public String getText(String elementName, By locator) {
		WebElement we = searchElementOnHTML(locator,elementName);
		String innertext = null;
		try {
			innertext = we.getText();
			extTest.log(Status.INFO, "Successfully Retrieve inner Text - "+elementName);

		} catch (StaleElementReferenceException e) {
			innertext = we.getText();
			extTest.log(Status.INFO, "Successfully Value enterd in the  - [ "+elementName+" ] After handaling Stale Exception ");

		} catch (Exception e) {
			extTest.log(Status.FAIL, "Due To Exception Inner text not found");
			extTest.addScreenCaptureFromPath(screenShot(elementName));
			e.printStackTrace();
			
			

		}
		return innertext;

	}
	/*                   Verification method                     */

	/*
	 * this method will verify two string
	 * 
	 * @Param - String actual
	 * 
	 * @Param - String expected
	 * 
	 * @return - not return anything
	 */

	public void verifyString(String actual, String expected) {
		if (actual.equals(expected)) {
			System.out.println("PASSED : ");
			extTest.log(Status.PASS, "Actual value [ " + actual + "] And Expected value  matched  [" + expected + "  ]");
		} else {
			extTest.log(Status.FAIL, "Actual value [ " + actual + "] And Expected value nat  match [" + expected + "  ]");
		}
	}

	/* * ------ Table scenario generic method --------------- */
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	/*
	 * this method will retrieve column Number
	 * 
	 * @Param -string Table Name
	 * 
	 * @Param - By
	 * 
	 * @Param - string column Name
	 * 
	 * @return - int column Number
	 */
	
	
	
	
public int getColumnNumberByColumnName(String xpath,String columnName) {
	int columnNumber = -1;
	List<WebElement> listcolumnName = driver.findElements(By.xpath(xpath));
	int columnCount = listcolumnName.size();
	for (int i = 0; i <= columnCount - 1; i++) {
		WebElement weTableCol = listcolumnName.get(i);
		String colname = weTableCol.getText();
		if (colname.equalsIgnoreCase(columnName)) {
			columnNumber = i;
			break;
		}
	}
	return columnNumber;
	}
	


	
	public String getTableDataByUniqueData(String tableXpath, String uniqueData, String uniqueDataColumnName, String requiredDataColumnName) {
		///  get row number of account number
		/// because account name is in same row 
		int rowNumber=0;
		List<WebElement> listRows=driver.findElements(By.xpath(tableXpath+"//tr"));
		int cn1=getColumnNumberByColumnName(tableXpath,uniqueDataColumnName);
		int cn2=getColumnNumberByColumnName(tableXpath,requiredDataColumnName);
		
		for(int i=0;i<=listRows.size()-1;i++) {
			String text=driver.findElement(By.xpath(tableXpath+"//tr["+(i+1)+"]//td["+cn1+"]")).getText();
			if(text.equalsIgnoreCase(uniqueData)) {
				rowNumber=i;
			}
		}
		String requiredText=driver.findElement(By.xpath(tableXpath+"//tr["+rowNumber+"]//td["+cn2+"]")).getText();
		return requiredText;
		
	}
	
	
	
	public  boolean verifyTableDataByHeaderName(String tablexPath ,String uniqueHeaderNeme,String dataForVrfy) {
		
		List<WebElement> list=driver.findElements(By.xpath(tablexPath+"//tr[1]//td"));
				boolean found=false;

		int colCount=0;
				
				for(int i=0;i<=list.size()-1;i++) {
				WebElement we=	list.get(i);
				String headers=we.getText();
				if(uniqueHeaderNeme.equals(headers)) {
					i=i+1;
					colCount=i;
					
				}
			
				
			}
				List<WebElement> weuniqueColDataList=driver.findElements(By.xpath(tablexPath+"//td["+colCount+"]"));
				
				for(int j=0;j<=weuniqueColDataList.size()-1;j++) {
				WebElement weUniueData=	weuniqueColDataList.get(j);
				String allUniqueData=weUniueData.getText();
				if(dataForVrfy.equalsIgnoreCase(allUniqueData)) {
					found=true;
				}
				}
				
				return found;
			}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
//***********************************************Action perform by KeYboard Physically******************

	/*
	 * this method will send value from text box and click on'ENTER'key
	 * 
	 * @Param -string Element name
	 * 
	 * @Param - By
	 * 
	 * @Param - String value For send
	 * 
	 * 
	 * 
	 * @return - not return
	 */

	public void InputValue_PressEnterKeys(String elementName, By locator, String value) {
		WebElement we = searchElementOnHTML(locator,elementName);
		try {
			clear(locator,elementName);
			we.sendKeys(value);
			we.sendKeys(Keys.ENTER);
			extTest.log(Status.INFO, "Successfully Value enterd in the -[ "+elementName+" ]-text box  And click on Enter Button ");

		} catch (ElementNotInteractableException e) {
			jsInputValueMethod(locator, value,elementName);
		} catch (StaleElementReferenceException e) {
			we = driver.findElement(locator);
			clear(locator,elementName);
			we.sendKeys(value);
			we.sendKeys(Keys.ENTER);

			extTest.log(Status.INFO, "Successfully Value enterd in the -[  "+elementName+" ]text box  And click on Enter Button After Handaling Stale Exception");
		} catch (Exception e) {
			extTest.log(Status.FAIL, "Value could not be Entered in text box  and not clicked Enter button Due to Exception ");
			extTest.addScreenCaptureFromPath(screenShot(elementName));
			throw e;
		}

	}

	/*
	 * this method will click on Tab button after that click on element
	 * 
	 * @Param -string Element name
	 * 
	 * @Param - By
	 * 
	 * @return - not return
	 */

	public void pressTab_click(String elementName, By locator) {
		WebElement we = searchElementOnHTML(locator,elementName);

		try {

			we.sendKeys(Keys.TAB);
			we.click();

		} catch (ElementNotInteractableException e) {
			jsClickMethod(locator,elementName);
		} catch (StaleElementReferenceException e) {
			we = driver.findElement(locator);
			we.sendKeys(Keys.TAB);
			we.click();

		} catch (Exception e) {
			throw e;
		}

	}
	
	
	
	
	
	
	
	/*
	 * this method will retrieve total Number of row in table
	 * 
	 * @Param - By
	 * 
	 * @Param - String element name
	 * 
	 * @return - int total row number
	 */
	public int getTableRowCount(By locator, String elementCollectionName) {
		List<WebElement> listCheckedBoxes = driver.findElements(locator);
		int rowCount = listCheckedBoxes.size();
		return rowCount;
	}

	/*
	 * this method will retrieve total number of column in table
	 * 
	 * @Param - By
	 * 
	 * @Param - String element name
	 * 
	 * @return - int total column number
	 */
	public int getTableColumnCount(By Tablelocator, String elementCollectionName) {

		List<WebElement> listCheckedBoxes = driver.findElements(Tablelocator);
		int ColoumnCount = listCheckedBoxes.size();
		return ColoumnCount;
	}

	/*
	 * this method will retrieve all column text
	 * 
	 * @Param -string Table Name
	 * 
	 * @Param - By
	 * 
	 * @Param - int column number
	 * 
	 * @return - List of String text of column
	 */

	public List<String> getColumnDataByColumnNumber(String tablename, String xpath, int columnNumber) {
		List<WebElement> columnList = driver.findElements(By.xpath(xpath));
		driver.findElements(By.xpath(xpath));
		List<String> columnNamelist = new ArrayList<>();
		for (int i = 0; i < columnList.size(); i++) {
			String columnName = columnList.get(i).getText();
			columnNamelist.add(columnName);

		}
		return columnNamelist;
	}

	/*
	 * this method will retrieve
	 * 
	 * @Param -String Table Name
	 * 
	 * @Param - By
	 * 
	 * @Param - String column name
	 * 
	 * @return - List of String text of column
	 */
	public List<String> getColumnDataByName(String tableName,String xpath, String columnName) {
//		int columnNumber=-1;
//		List<WebElement> listcolumnName=	driver.findElements(By. locator(table locator+"//tr[1]//td"));
//			int columnCount=listcolumnName.size();
//			for(int i=0;i<=columnCount-1;i++) {
//				WebElement weTableCol=listcolumnName.get(i);
//			String colname=	weTableCol.getText();
//			if(colname.equalsIgnoreCase(columnName)) {
//				columnNumber=i;
//			break;
//			}
//			}
//			List<WebElement> columnList=	driver.findElements(By. locator(table locator+"//tr//td["+columnNumber+"]"));
//			List<String> columnNamelist=new ArrayList<>();
//			for(int i=0;i<columnList.size();i++) {
//				String coluName=columnList.get(i).getText();
//				columnNamelist.add(coluName);
//				
//			}
//			return columnNamelist;

		int columncount = getColumnNumberByColumnName(xpath, columnName);
		List<String> columnNamelist = getColumnDataByColumnNumber(tableName, xpath, columncount);

		return columnNamelist;
	}


	/*
	 * this method will retrieve row number
	 * 
	 * @Param -string Table Name
	 * 
	 * @Param - By
	 * 
	 * @Param - String unique Data
	 * 
	 * @Param - String unique Data in column
	 * 
	 * @return - int row number
	 */
	public int getRowNumberbyRowID(String tablelocator, String tablename, String uniqueData, String uniqueColumnName) {
		int rowNumber = -1;
		List<String> columndat = getColumnDataByName(tablename, tablelocator, uniqueColumnName);
		for (int i = 0; i <= columndat.size() - 1; i++) {
			String uniqueColumnData = columndat.get(i);
			if (uniqueColumnData.equalsIgnoreCase(uniqueData)) {

				rowNumber = i;
				break;
			}
		}
		return rowNumber;

	}

	public void getRowDataListByRowID(String tablelocator, String tablename, String uniqueData, String uniqueColumnName) {
		int rowNumber = getRowNumberbyRowID(tablelocator, tablename, uniqueData, uniqueColumnName);

	}


}
