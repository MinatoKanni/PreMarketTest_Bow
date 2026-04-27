package com.baseclass;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;

import io.github.bonigarcia.wdm.WebDriverManager;

public class BaseClass {

    public static WebDriver driver;
    public static Actions a;

    // 🔥 BROWSER LAUNCH (FIXED FOR JENKINS + LOCAL)
    public static WebDriver launchBrowser(String browser) {

        if (browser.equalsIgnoreCase("Chrome")) {

            ChromeOptions options = new ChromeOptions();

            // ✅ Headless control (default = false)
            String headless = System.getProperty("headless", "false");

            if ("true".equalsIgnoreCase(headless)) {
                System.out.println("[INFO] Running in HEADLESS mode.");

                options.addArguments("--headless=new");
                options.addArguments("--no-sandbox");
                options.addArguments("--disable-dev-shm-usage");
                options.addArguments("--disable-gpu");
                options.addArguments("--window-size=1920,1080");

            } else {
                System.out.println("[INFO] Running in HEADED mode.");
            }

            // Common options
            options.addArguments("--disable-notifications");
            options.addArguments("--remote-allow-origins=*");

            // 🔥 IMPORTANT FIX → Clear old driver cache + auto-match Chrome version
            WebDriverManager.chromedriver()
                    .clearDriverCache()
                    .setup();

            driver = new ChromeDriver(options);
        }

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.manage().window().maximize();

        return driver;
    }

    // 🔹 Navigate URL
    public static void getUrl(String url) {
        driver.get(url);
    }

    // 🔹 Click
    public static void clickOnElement(WebElement element) {
        element.click();
    }

    // 🔹 Send values
    public static void sendValues(WebElement element, String value) {
        element.clear();
        element.sendKeys(value);
    }

    // 🔹 Quit browser
    public static void quitBrowser() {
        if (driver != null) {
            driver.quit();
        }
    }

    // 🔹 Screenshot (AFTER SCENARIO)
    public static void takeScreenshot(String name) {

        try {
            TakesScreenshot ts = (TakesScreenshot) driver;
            File src = ts.getScreenshotAs(OutputType.FILE);

            String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss_SSS").format(new Date());
            String safeName = name.replaceAll("[^a-zA-Z0-9_\\-]", "_");

            File dest = new File("target/screenshots/" + safeName + "_" + timestamp + ".png");

            Files.createDirectories(dest.getParentFile().toPath());
            Files.copy(src.toPath(), dest.toPath(), StandardCopyOption.REPLACE_EXISTING);

            System.out.println("[SCREENSHOT] Saved: " + dest.getPath());

        } catch (IOException e) {
            System.err.println("[SCREENSHOT ERROR] Failed for: " + name);
            e.printStackTrace();
        }
    }

    // 🔹 Frame Handling (FIXED WITH WAIT)
    public static void framesHandling() {

        try {
            Thread.sleep(1000);

            WebElement frame1 = driver.findElement(By.xpath("//iframe[contains(@class,'iframe')]"));
            driver.switchTo().frame(frame1);

            WebElement frame2 = driver.findElement(By.xpath("//iframe[contains(@title,'Chart')]"));
            driver.switchTo().frame(frame2);

        } catch (Exception e) {
            System.out.println("Frame switching failed: " + e.getMessage());
        }
    }

    public static void outOfFrames() {
        driver.switchTo().defaultContent();
    }

    // 🔹 Sleep utility
    public static void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    // 🔹 Implicit wait
    public static void setImplicitWait(long seconds) {
        driver.manage().timeouts().implicitlyWait(seconds, TimeUnit.SECONDS);
    }

    // 🔹 Page load wait (VERY IMPORTANT FOR JENKINS)
    public static void waitForPageLoad() {
        new org.openqa.selenium.support.ui.WebDriverWait(driver, Duration.ofSeconds(30))
                .until(d -> ((JavascriptExecutor) d)
                        .executeScript("return document.readyState").equals("complete"));
    }

    // 🔹 Safe click (JS fallback)
    public static void safeClick(WebElement element) {
        try {
            element.click();
        } catch (Exception e) {
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
        }
    }
}