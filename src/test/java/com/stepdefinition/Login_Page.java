package com.stepdefinition;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.baseclass.BaseClass;
import com.pom.Login_Navia_POM;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;

public class Login_Page extends BaseClass {

	Login_Navia_POM l = new Login_Navia_POM(driver);
	Actions ac = new Actions(driver);

	static Set<String> windowHandles = driver.getWindowHandles();
	static ArrayList<String> li = new ArrayList<String>(windowHandles);

	@Given("User Navigate to Navia")
	public void user_navigate_to_navia() throws InterruptedException, AWTException {
		Thread.sleep(2000);
		driver.get("https://yopmail.com/");

		String currentUrl = driver.getCurrentUrl();
		System.out.println(currentUrl);

		WebElement yopMail = driver.findElement(By.xpath("//input[@placeholder='Enter your inbox here']"));
		yopMail.sendKeys("naviatesting@yopmail.com");
		

		Thread.sleep(3000);
		driver.findElement(By.xpath("//i[@class='material-icons-outlined f36']")).click();

		WebElement createAccount = driver.findElement(By.xpath("//a[@title='YOPmail - Temporary email']"));
		Actions ac = new Actions(driver);
		Thread.sleep(2000);
		ac.contextClick(createAccount).perform();

		Robot robot = new Robot();
		robot.keyPress(KeyEvent.VK_DOWN);
		robot.keyRelease(KeyEvent.VK_DOWN);

		robot.keyPress(KeyEvent.VK_DOWN);
		robot.keyRelease(KeyEvent.VK_DOWN);

		robot.keyPress(KeyEvent.VK_ENTER);
		robot.keyRelease(KeyEvent.VK_ENTER);

		Thread.sleep(8000);

		Set<String> windowHandles = driver.getWindowHandles();
		ArrayList<String> li = new ArrayList<String>(windowHandles);
		int size = li.size();
		System.out.println(size);

		driver.switchTo().window(li.get(1));

		driver.navigate().refresh();

		getUrl("https://rocket.tradeplusonline.com/login.php");
		// getUrl("https://rocket.tradeplusonline.com/beta-v2/index.php");

		sleep(7000);

		// clickOnElement(l.getLoginWithClientCode());
		// driver.navigate().refresh();
		driver.findElement(By.xpath("(//button[@id='login_fsmt1'])[2]")).click();		
		sleep(2000);
		//
		driver.findElement(By.xpath("//input[@name='clientCode']")).click();

		sleep(1000);

		sendValues(l.getClientCode(), "63748379");

		clickOnElement(l.getPassWord());
		sleep(1000);

		sendValues(l.getPassWord(), "Navia@1234");
		

		clickOnElement(l.getCheckBox());

		clickOnElement(l.getLogin());
		sleep(1000);
		driver.switchTo().window(li.get(0));

		Thread.sleep(32000);

		WebElement refresh = driver.findElement(By.xpath("//button[@id='refresh']"));
		refresh.click();
		
		Thread.sleep(5000);
		WebElement refresh1 = driver.findElement(By.xpath("//button[@id='refresh']"));
		refresh1.click();

		

		Thread.sleep(5000);
		
		try {
			
			WebElement iframe = driver.findElement(By.xpath("//iframe[@id='ifmail']"));
			driver.switchTo().frame(iframe);
			
			WebElement otp = driver.findElement(
					By.xpath("//font[text()='Your One Time Password (OTP) for BOLTPlus On Web login is ']//child::strong"));
			
			if (otp.isDisplayed()) {
				

				String text = otp.getText();
				System.err.println(text);

				driver.switchTo().defaultContent();

				Thread.sleep(4000);

				driver.switchTo().window(li.get(1));
				clickOnElement(l.getOtpClick());
				Thread.sleep(3000);

				driver.findElement(By.xpath("//input[@id='userotp']")).sendKeys(text);

				driver.switchTo().window(li.get(0));

				driver.close();

				driver.switchTo().window(li.get(1));
				
			}
			
			
		} catch (Exception e) {
			
			Thread.sleep(1000);
			
			WebElement element = driver.findElement(By.xpath("(//div[@class='lmfd'])[2]"));
			element.click();
			
			Thread.sleep(1000);
			
			
			
			WebElement otp = driver.findElement(
					By.xpath("//font[text()='Your One Time Password (OTP) for BOLTPlus On Web login is ']//child::strong"));
			
			if (otp.isDisplayed()) {
				

				String text = otp.getText();
				System.err.println(text);

				driver.switchTo().defaultContent();

				Thread.sleep(4000);

				driver.switchTo().window(li.get(1));
				clickOnElement(l.getOtpClick());
				Thread.sleep(3000);

				driver.findElement(By.xpath("//input[@id='userotp']")).sendKeys(text);

				driver.switchTo().window(li.get(0));

				driver.close();

				driver.switchTo().window(li.get(1));
				
			}
		}

		Thread.sleep(2000);
	

	}

	@When("User Click login with client code")
	public void user_click_login_with_client_code() throws InterruptedException {
		System.out.println("User Click login with client code");
	}

	@When("User Enter Client Code")
	public void user_enter_client_code() throws InterruptedException {
		System.out.println("User Enter Client Code");

	}

	@When("User  Enter Password")
	public void user_enter_password() throws InterruptedException {

		System.out.println("User Enter Password");

	}

	@When("User Click Agree CheckBox")
	public void user_click_agree_check_box() {
		System.out.println("User Click Agree CheckBox");

	}

	@When("User Click Login button")
	public void user_click_login_button() throws InterruptedException, AWTException {

		System.out.println("User Click Login button");

	}

	@When("User Click Otp Verification and enter manualy")
	public void user_click_otp_verification_and_enter_manualy() throws InterruptedException {

		System.out.println("User Click Otp Verification and enter manualy");

	}

	@When("User Click Login Again")
	public void user_click_login_again() throws InterruptedException {

		
		setImplicitWait(400);
		
		clickOnElement(l.getLoginAfterOTP());

		System.out.println("Login Page");
//		WebElement element = driver.findElement(By.xpath("//span[text()='Agree']//parent::button"));
//		element.click();

		try {
			
			WebDriverWait wait1 = new WebDriverWait(driver, java.time.Duration.ofMinutes(4));
			WebElement textBox = wait1.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h2[contains(text(),'RISK DISCLOSURES ON DERIVATIVES'])")));

			Thread.sleep(2000);

			if (textBox.isDisplayed()) {
				Thread.sleep(2000);
				driver.findElement(By.xpath("//span[text()='Agree']//parent::button")).click();

			}

			else {
				System.out.println("Not Preset in a Page");

			}
			
		} catch (Exception e) {
			
			//driver.findElement(By.xpath("//span[text()='Agree']//parent::button")).click();
		}
			
			

		

	}
	
	
	
}
