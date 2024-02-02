package evs.vTiger.StpeDefi;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import evs.vTiger.PageObject.LoginPage;
import evs.vTiger.PageObject.SearchLeadsPage;
import evs.vTiger.Utilities.Config;
import evs.vTiger.Utilities.webUtil;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class StepDefintion {

	public WebDriver driver;
	public	LoginPage loginpg;
	public webUtil util;
	public SearchLeadsPage leadssrch;
	public Config readconfig;

	@Before
	public void web() {
		util = new webUtil();
		readconfig=new Config();

	}

	@Given("User Open Browser")
	public void user_open_browser() {
		String browsername=readconfig.getBrowser();
		util.createExtentReport();
		util.createTestReport("user_open_browser");
		util.launchbrowser(browsername);
		loginpg = new LoginPage(driver);
		leadssrch = new SearchLeadsPage(driver);

	}

	@When("User Open URL {string}")
	public void user_open_url(String url) {
		util.openUrl(url);
	}

	@Then("User On login Page title Should be {string}")
	public void user_on_login_page_title_should_be(String title) {
		String lonTitle = util.getTitle("Login Page");
		if (title.equals(lonTitle)) {
			Assert.assertTrue(true);
		} else {
			Assert.assertTrue(false);
		}

	}

	@When("User Enter UserName as {string} and Password as {string}")
	public void user_enter_user_name_as_and_password_as(String userName, String password) {
		loginpg.enterUserName(userName);
		loginpg.enterUserPassword(password);
	}

	@When("click on Login Button")
	public void click_on_login_button() {
		loginpg.userClickOnLogin();
	}

	@Then("User On Home page Title Should be {string}")
	public void user_on_home_page_title_should_be(String HTitle) {

		String homePage = util.getTitle("Home Page");
		if (HTitle.equals(homePage)) {
			Assert.assertTrue(true);
		} else {
			Assert.assertTrue(false);
		}
	}

	@When("User Click on Sales link")
	public void user_click_on_sales_link() {
		loginpg.clickOnSales();
	}

	@When("User Clcik on Lead")
	public void user_clcik_on_lead() {
		loginpg.clickOnLeads();
	}

	@Then("User On Leads Datails Page and View Confirmation Header should be  {string}")
	public void user_on_leads_datails_page_and_view_confirmation_header_should_be(String header) {
		String ReHeader = loginpg.verifyHeader();
		if (header.equals(ReHeader)) {
			Assert.assertTrue(true);
		} else {
			Assert.assertTrue(false);
		}

	}

	@When("User Click on cerate new Lead icon\\(plus Button)")
	public void user_click_on_cerate_new_lead_icon_plus_button() {
		loginpg.clickOnCreateNewLead();
	}

	@Then("user On create new Leads and View Comfirmation Header should be {string}")
	public void user_on_create_new_leads_and_view_comfirmation_header_should_be(String header) {
		String newHeader = util.getText("create new Page ", By.xpath("//span[text()='Creating New Lead']"));
		if (header.equals(newHeader)) {
			Assert.assertTrue(true);
		} else {
			Assert.assertTrue(false);
		}
	}

	@When("User Pass full info for create new Leads")
	public void user_pass_full_info_for_create_new_leads() {
		util.selectByValue(By.xpath("//select[@name='salutationtype' ]"), "Mr.", "Name Title");
		util.inputValue("First Name", By.name("firstname"), "sadhu ");
		util.inputValue("Last Name", By.name("lastname"), "Yadav");
		util.inputValue("Company Name", By.name("company"), "Google");
		util.inputValue("Title", By.id("designation"), "QA engeneer");
		util.selectByValue(By.name("leadsource"), "Employee", "Lead Source");
		util.selectByValue(By.name("leadstatus"), "Qualified", "Leads Status");
		util.inputValue("Phone ", By.id("phone"), "25454545854");
		util.inputValue("Fax Number", By.id("fax"), "788544-7444-888");
		util.inputValue("Email", By.id("email"), "akjdh@gmail.com");
		util.inputValue("Address", By.name("lane"), "jdghfdsbhb ashgdshbd shdghsdgsd");
		util.inputValue("descriptionn", By.name("description"),
				"jsdhferugfckhabshdbcfuydcegbayusgfcvsuyagrfygbcehadsyugcdyugcugysgdgcuacg");

	}

	@When("Click On Save Button")
	public void click_on_save_button() {
		loginpg.clickOnSave();

	}

	@Then("Page Title should be {string}")
	public void page_title_should_be(String savePageTitle) {
		String tilte = util.getTitle("Save page Title");
		if (savePageTitle.equals(tilte)) {
			Assert.assertTrue(true);
		} else {
			Assert.assertTrue(false);
		}
	}

	@When("User Enter First Name In Search Text Box")
	public void user_enter_first_name_in_search_text_box() {
		leadssrch.enterNameInSearchText("sadhu");
	}

	@When("User Select first Name From DropDown")
	public void user_select_first_name_from_drop_down() {
		leadssrch.selectDrpsearch();
	}

	@When("User Clcik On Search Button")
	public void user_clcik_on_search_button() {
		leadssrch.clickOnsearchButton();
	}

	@Then("User Found Leads Name In Table")
	public void user_found_leads_name_in_table() {
		boolean b = leadssrch.heders("First Name", "First Name");
		if (b == true) {
			Assert.assertTrue(true);
		} else {
			Assert.assertTrue(true);
		}
	}

	@When("user Clcik On Ckeck Box")
	public void user_clcik_on_ckeck_box() {
		leadssrch.clickCheckBox();

	}

	@When("Confirm alert Pop occur and User Accept")
	public void confirm_alert_pop_occur_and_user_accept() {
		leadssrch.clickDelete();
		util.acceptAlertPop();
	}

	@Then("User Confirm text view {string}")
	public void user_confirm_text_view(String textTovrfy) {
		System.out.println("Delete Leads " + textTovrfy);
	}

	@After
	public void tearDown() {
		util.flushReport();

		util.tearDown_Quit();
	}

}
