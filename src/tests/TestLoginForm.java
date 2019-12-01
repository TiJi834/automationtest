package tests;

import static org.junit.Assert.assertTrue;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import pageObjects.testwebsite.TestWebsiteDashboardPage;
import pageObjects.testwebsite.TestWebsiteLoginPage;
import utils.WebDriverUtils;

class TestLoginForm {

	static WebDriver driver;
	WebDriverUtils utils;

	@BeforeAll
	public static void setup() {
		// setting up configuration before tests
		System.setProperty("webdriver.chrome.driver", ".\\exe\\chromedriver.exe");
		driver = new ChromeDriver();
	}

	@Test
	public void loginToTestWebsite() {

		// 1. Go to: http://automationpractice.com/index.php?controller=authentication
		// 2. Enter incorrect email address in "Email address" field, click outside of the field and click "Sign in"
		TestWebsiteLoginPage loginPage = new TestWebsiteLoginPage(driver);
		loginPage.enterLoginEmail("test");
		assertTrue(loginPage.isElementFailingValidation("Email address"));
		loginPage.clickLogin();
		assertTrue(loginPage.isAlertMessageFound("Invalid email address."));

		// 3. Enter correct "Email address" and too short "Password", click outside of the field and click "Sign in"
		loginPage.enterLoginEmail("demo.test@mail.com");
		loginPage.enterLoginPassword("Pass");
		loginPage.clickLogin();
		assertTrue(loginPage.isAlertMessageFound("Invalid password."));

		// 4. Enter correct "Email address" and incorrect "Password", click outside of the field and click "Sign in"
		loginPage.enterLoginEmail("demo.test@mail.com");
		loginPage.enterLoginPassword("Password");
		loginPage.clickLogin();
		assertTrue(loginPage.isAlertMessageFound("Authentication failed."));

		// 5. Enter correct "Email address" and correct "Password", click outside of the field and click "Sign in"
		loginPage.enterLoginEmail("demo.test@mail.com");
		loginPage.enterLoginPassword("Password1");
		loginPage.clickLogin();

		TestWebsiteDashboardPage dashboardPage = new TestWebsiteDashboardPage(driver);
		assertTrue(dashboardPage.isInDashboard());
	}

	@AfterAll
	public static void finish() {
		// quitting driver after tests
		driver.quit();
	}

}