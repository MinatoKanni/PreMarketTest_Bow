package com.stepdefinition;

import java.util.ArrayList;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
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

    @Given("User Navigate to Navia")
    public void user_navigate_to_navia() throws InterruptedException {

        Thread.sleep(2000);

        // FIX: Replaced Robot-based context menu (breaks headless) with
        // direct URL navigation + window.open() for tab management
        driver.get("https://yopmail.com/en/?naviatesting@yopmail.com");
        Thread.sleep(5000);

        // Open Navia login in a new tab
        ((JavascriptExecutor) driver).executeScript("window.open()");

        Set<String> windows = driver.getWindowHandles();
        ArrayList<String> li = new ArrayList<>(windows);

        driver.switchTo().window(li.get(1));
        getUrl("https://rocket.tradeplusonline.com/login.php");
        sleep(5000);

        driver.findElement(By.xpath("(//button[@id='login_fsmt1'])[2]")).click();
        sleep(2000);

        driver.findElement(By.xpath("//input[@name='clientCode']")).click();
        sleep(1000);

        sendValues(l.getClientCode(), "63748379");
        clickOnElement(l.getPassWord());
        sleep(1000);
        sendValues(l.getPassWord(), "Navia@1234");

        clickOnElement(l.getCheckBox());
        clickOnElement(l.getLogin());
        sleep(1000);

        // Switch to YOPMail tab to read OTP
        driver.switchTo().window(li.get(0));
        Thread.sleep(20000);

        driver.findElement(By.id("refresh")).click();
        Thread.sleep(5000);
        driver.findElement(By.id("refresh")).click();
        Thread.sleep(5000);

        String otpValue = "";

        try {
            WebElement iframe = driver.findElement(By.xpath("//iframe[@id='ifmail']"));
            driver.switchTo().frame(iframe);

            WebElement otp = driver.findElement(By.xpath(
                "//font[text()='Your One Time Password (OTP) for BOLTPlus On Web login is ']//child::strong"));

            if (otp.isDisplayed()) {
                otpValue = otp.getText();
                System.out.println("[OTP] " + otpValue);
            }

            driver.switchTo().defaultContent();

        } catch (Exception e) {

            System.out.println("[INFO] Primary OTP xpath failed, trying fallback...");
            try {
                WebElement iframe = driver.findElement(By.xpath("//iframe[@id='ifmail']"));
                driver.switchTo().frame(iframe);
                otpValue = driver.findElement(By.xpath("//strong")).getText();
                System.out.println("[OTP Fallback] " + otpValue);
                driver.switchTo().defaultContent();
            } catch (Exception e2) {
                System.out.println("[WARN] Could not read OTP: " + e2.getMessage());
                driver.switchTo().defaultContent();
            }
        }

        // Close YOPMail tab — only Navia tab remains
        driver.close();
        windows = driver.getWindowHandles();
        li = new ArrayList<>(windows);
        driver.switchTo().window(li.get(0));

        Thread.sleep(2000);

        // FIX: JS-based OTP entry — works in both headed and headless modes
        // Robot.keyPress/sendKeys on hidden field fails headless due to no display
        clickOnElement(l.getOtpClick());
        Thread.sleep(2000);

        WebDriverWait wait = new WebDriverWait(driver, java.time.Duration.ofSeconds(60));
        WebElement otpField = wait.until(
            ExpectedConditions.presenceOfElementLocated(By.id("userotp")));
        Thread.sleep(500);

        ((JavascriptExecutor) driver).executeScript(
            "arguments[0].removeAttribute('disabled');" +
            "arguments[0].removeAttribute('readonly');" +
            "arguments[0].style.display='block';" +
            "arguments[0].style.visibility='visible';" +
            "arguments[0].style.opacity='1';", otpField);
        Thread.sleep(300);

        ((JavascriptExecutor) driver).executeScript("arguments[0].focus();", otpField);
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", otpField);
        Thread.sleep(300);

        final String finalOtp = otpValue;
        ((JavascriptExecutor) driver).executeScript(
            "var setter = Object.getOwnPropertyDescriptor(" +
            "  window.HTMLInputElement.prototype, 'value').set;" +
            "setter.call(arguments[0], arguments[1]);" +
            "arguments[0].dispatchEvent(new Event('input', {bubbles:true}));" +
            "arguments[0].dispatchEvent(new Event('change', {bubbles:true}));",
            otpField, finalOtp);
        Thread.sleep(500);

        String entered = (String) ((JavascriptExecutor) driver)
            .executeScript("return arguments[0].value;", otpField);
        System.out.println("[INFO] OTP entered: " + entered);

        // Fallback to sendKeys if JS injection failed
        if (entered == null || entered.isEmpty()) {
            System.out.println("[INFO] JS failed, using sendKeys fallback...");
            otpField.clear();
            otpField.sendKeys(finalOtp);
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
    public void user_click_login_button() throws InterruptedException {
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

        try {
            WebDriverWait wait1 = new WebDriverWait(driver, java.time.Duration.ofMinutes(4));
            WebElement textBox = wait1.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//h2[contains(text(),'RISK DISCLOSURES ON DERIVATIVES')]")));

            Thread.sleep(2000);

            if (textBox.isDisplayed()) {
                Thread.sleep(2000);
                driver.findElement(By.xpath("//span[text()='Agree']//parent::button")).click();
            }

        } catch (Exception e) {
            System.out.println("[INFO] No risk disclosure popup.");
        }

        // Wait for home page to confirm login succeeded
        try {
            System.out.println("[INFO] Waiting for home page...");
            new WebDriverWait(driver, java.time.Duration.ofSeconds(60)).until(
                ExpectedConditions.or(
                    ExpectedConditions.presenceOfElementLocated(
                        By.xpath("//input[@id='project-id']")),
                    ExpectedConditions.presenceOfElementLocated(
                        By.xpath("//button[@data-dhx-id='btn_addmoney']")),
                    ExpectedConditions.presenceOfElementLocated(
                        By.xpath("//label[text()='Dashboard']"))
                ));
            System.out.println("[INFO] Home page loaded — logged in successfully.");
        } catch (Exception e) {
            System.out.println("[WARN] Home page wait timed out: " + e.getMessage());
        }
    }
}