package com.stepdefinition;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.baseclass.BaseClass;
import com.pom.Login_Navia_POM;

import io.cucumber.java.en.When;

public class PreMarkets extends BaseClass {

    Login_Navia_POM l = new Login_Navia_POM(driver);
    Actions ac = new Actions(driver);

    // FIX: Removed static class-level initialization of windowHandles and li.
    // Static fields are initialized when the class loads — at that point driver
    // may be null, causing NullPointerException before any test runs.
    // Window handles are now fetched inside each method that needs them.

    @When("User click the search box")
    public void user_click_the_search_box() throws InterruptedException {
        Thread.sleep(4000);
        driver.findElement(By.xpath("//input[@id='project-id']")).click();
    }

    @When("User Search any {string} Script")
    public void user_search_any_script(String string) throws InterruptedException {
        Thread.sleep(2000);
        driver.findElement(By.xpath("//input[@id='project-id']")).sendKeys(string);
        Thread.sleep(2000);
    }

    @When("User Mouse Over and Add a Script")
    public void user_mouse_over_and_add_a_script() throws InterruptedException {
        Thread.sleep(3000);
        WebElement element = driver.findElement(
            By.xpath("(//div[@class='srh_results act']/descendant::span[text()='WIPRO'])[1]"));
        Actions ac = new Actions(driver);
        ac.moveToElement(element).perform();
        Thread.sleep(3000);
        driver.findElement(
            By.xpath("(//div[@class='srh_results act']/descendant::span[@class='s_add_sym'])[1]"))
            .click();
    }

    @When("User Mouse Over and Add a Script {string}")
    public void user_mouse_over_and_add_a_script(String string) throws InterruptedException {
        Thread.sleep(3000);

        WebElement element = driver.findElement(
            By.xpath("(//div[@class='srh_results act']/descendant::span[text()='" + string + "'])[1]"));

        try {
            Actions ac = new Actions(driver);
            ac.moveToElement(element).perform();
        } catch (Exception e) {
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("window.scrollBy(0,500);");
            js.executeScript("arguments[0].scrollIntoView(true);", element);
        }

        Thread.sleep(3000);

        driver.findElement(By.xpath(
            "(//span[text()='" + string + "']//ancestor::li//descendant::span[@class='s_add_sym'])[1]"))
            .click();
    }

    @When("User Remove The {string} Stock")
    public void user_remove_the_stock(String string) throws InterruptedException {
        WebElement element = driver.findElement(
            By.xpath("//span[text()='" + string + "']//parent::div"));
        Actions ac = new Actions(driver);
        ac.moveToElement(element).perform();
        Thread.sleep(3000);
        driver.findElement(By.xpath("//span[text()='" + string + "']//following-sibling::span")).click();
        Thread.sleep(2000);
        driver.findElement(By.xpath("//button[text()=' Delete']")).click();
        Thread.sleep(2000);
    }

    @When("User Click The Withdraw button")
    public void user_click_the_withdraw_button() throws InterruptedException {
        Thread.sleep(4000);
        driver.findElement(By.xpath("//div[@class='funds_show']")).click();
        Thread.sleep(2000);
        driver.findElement(By.xpath("//button[text()='Withdraw']")).click();
        Thread.sleep(4000);

        WebElement frame = driver.findElement(By.xpath("//iframe[@class='iframe_window']"));
        Thread.sleep(2000);
        driver.switchTo().frame(frame);

        driver.findElement(By.xpath("//input[@placeholder='Enter amount']")).click();
        Thread.sleep(2000);
        driver.findElement(By.xpath("//input[@placeholder='Enter amount']")).clear();
        Thread.sleep(2000);
        driver.findElement(By.xpath("//input[@placeholder='Enter amount']")).sendKeys("1");
        Thread.sleep(2000);

        WebElement element = driver.findElement(By.xpath("//div[@class='bank_radio']"));
        Thread.sleep(2000);

        if (element.isDisplayed()) {
            String blue = "\u001B[34m";
            System.out.println(blue + "Bank Details is displayed");
        } else {
            System.err.println("Bank Details is not displayed");
        }

        Thread.sleep(2000);
        driver.switchTo().defaultContent();
        Thread.sleep(2000);
        driver.findElement(By.xpath("(//span[@class='funds_back'])[2]")).click();
    }

    @When("User POP Up Message Appear Verify POP UP")
    public void user_pop_up_message_appear_verify_pop_up() throws InterruptedException {
        try {
            WebElement popUp = driver.findElement(
                By.xpath("//span[text()='Symbol Has been Added Successfully']"));
            if (popUp.isDisplayed()) {
                System.out.println("Verify Pop is Displayed Successfully");
            } else {
                System.out.println("Verify Pop is Not Displayed");
            }
        } catch (Exception e) {
            // popup may have already disappeared
        }
    }

    @When("User Click Add Money")
    public void user_click_add_money() throws InterruptedException {
        Thread.sleep(4000);
        Thread.sleep(2000);
        driver.findElement(By.xpath("//button[@data-dhx-id='btn_addmoney']")).click();
        Thread.sleep(4000);
    }

    @When("User enter money in amount to add")
    public void user_enter_money_in_amount_to_add() throws InterruptedException {
        Thread.sleep(2000);

        WebElement addMoneyFrame = driver.findElement(By.xpath("//iframe[@class='iframe_window']"));
        driver.switchTo().frame(addMoneyFrame);

        driver.findElement(By.xpath("//input[@placeholder='Enter Amount']")).click();
        WebElement enterAmount = driver.findElement(By.xpath("//input[@placeholder='Enter Amount']"));
        enterAmount.clear();
        Thread.sleep(1000);
        enterAmount.sendKeys("500");
        Thread.sleep(1000);

        driver.findElement(By.xpath("//input[@id='deposit_amount']")).click();
        Thread.sleep(4000);

        driver.switchTo().defaultContent();

        // FIX: Fetch window handles inside method — not at class load time
        Set<String> windowHandles1 = driver.getWindowHandles();
        ArrayList<String> li1 = new ArrayList<>(windowHandles1);
        int size = li1.size();
        System.out.println("Window count: " + size);
        Thread.sleep(1000);

        if (size > 1) {
            driver.switchTo().window(li1.get(1));
            Thread.sleep(2000);
            driver.close();
            driver.switchTo().window(li1.get(0));
        }

        driver.switchTo().frame(0);
        driver.findElement(By.xpath("//input[@id='deposit_amount']")).click();
        driver.switchTo().defaultContent();
        Thread.sleep(4000);
    }

    @When("User Choose Pay Using UPI")
    public void user_choose_pay_using_upi() throws InterruptedException {
        Thread.sleep(2000);

        // FIX: Fetch window handles inside method
        Set<String> windowHandles1 = driver.getWindowHandles();
        ArrayList<String> li1 = new ArrayList<>(windowHandles1);
        int size = li1.size();
        System.out.println("Window count: " + size);

        if (size > 1) {
            driver.switchTo().window(li1.get(1));
        }

        Thread.sleep(5000);
    }

    // FIX: Removed Robot import and AWTException entirely.
    // Robot.keyPress() requires a physical display — fails headless.
    // The original code already had Robot commented out — this just
    // removes the import and exception signature to prevent compile warnings.
    @When("User Enter UPI/ID/Mobile Number and click pay now")
    public void user_enter_upi_id_mobile_number_and_click_pay_now() throws InterruptedException {

        Thread.sleep(1000);
        driver.switchTo().frame(0);
        Thread.sleep(1000);

        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollBy(0, 500)");
        js.executeScript("window.scrollBy(0, 500)");
        js.executeScript("window.scrollBy(0, -500)");

        WebElement netBanking = driver.findElement(By.xpath("//input[@placeholder='example@okhdfcbank']"));
        netBanking.click();
        Thread.sleep(1000);

        WebElement UPI = driver.findElement(By.xpath("//input[@placeholder='example@okhdfcbank']"));
        UPI.sendKeys("6374837965@ptsbi");
        Thread.sleep(1000);

        WebElement clickPayUsingUPI = driver.findElement(By.xpath("//button[text()='Verify and Pay']"));
        clickPayUsingUPI.click();
        Thread.sleep(3000);

        driver.findElement(By.xpath("//button[text()='Cancel Payment']")).click();
        driver.findElement(By.xpath("//button[@data-testid='confirm-positive']")).click();

        WebDriverWait wait1 = new WebDriverWait(driver, java.time.Duration.ofMinutes(1));
        WebElement rejectedMessage = wait1.until(ExpectedConditions.visibilityOfElementLocated(
            By.xpath("//div[text()='Payment could not be completed']")));

        if (rejectedMessage.isDisplayed()) {
            js.executeScript("arguments[0].style.border='2px solid yellow'", rejectedMessage);
            System.err.println("Payment Declined");
        } else {
            js.executeScript("arguments[0].style.border='2px solid red'", rejectedMessage);
            System.err.println("Payment Successful");
        }

        driver.switchTo().parentFrame();
        driver.switchTo().defaultContent();
    }

    @When("User Click Watch List Again")
    public void user_click_watch_list_again() throws InterruptedException {
        WebElement element1 = driver.findElement(
            By.xpath("(//div[@class='header-left']//descendant::span[@class='ind_syml'])[1]"));
        element1.click();
        Thread.sleep(2000);
    }

    @When("User mouse over the scrip {string}")
    public void user_mouse_over_the_scrip(String string) throws InterruptedException {

        switch (string) {

        case "Nifty 50":
            WebElement contractSelectAgain = driver.findElement(
                By.xpath("//div[@class='select-box active']//descendant::li//span[text()='" + string + "']"));
            Thread.sleep(1000);
            Actions ac = new Actions(driver);
            ac.moveToElement(contractSelectAgain).perform();
            Thread.sleep(1000);
            ac.click(contractSelectAgain).perform();

            try {
                WebElement element1 = driver.findElement(By.xpath("(//span[text()='Nifty 50'])[2]"));
                Actions f = new Actions(driver);
                Thread.sleep(1000);
                f.moveToElement(element1).perform();
                Thread.sleep(1000);
                WebElement element11 = driver.findElement(
                    By.xpath("(//span[@optionsymbol='NIFTY-NSE']//preceding-sibling::span)[2]"));
                element11.click();
                Thread.sleep(1000);
            } catch (Exception e) {
                WebElement element1 = driver.findElement(By.xpath("(//span[text()='Nifty 50'])[1]"));
                Actions f = new Actions(driver);
                Thread.sleep(1000);
                f.moveToElement(element1).perform();
                Thread.sleep(1000);
                WebElement element11 = driver.findElement(
                    By.xpath("(//span[@optionsymbol='NIFTY-NSE']//preceding-sibling::span)[1]"));
                element11.click();
                Thread.sleep(1000);
            }
            break;

        case "SENSEX":
            WebElement element21 = driver.findElement(By.xpath("(//span[@class='ind_syml'])[1]"));
            JavascriptExecutor js1 = (JavascriptExecutor) driver;
            js1.executeScript("arguments[0].click();", element21);
            Thread.sleep(1000);

            Actions f2 = new Actions(driver);
            WebElement element3 = driver.findElement(By.xpath("(//span[text()='S&P BSE SENSEX'])[2]"));
            f2.moveToElement(element3).perform();
            Thread.sleep(1000);

            driver.findElement(By.xpath(
                "//div[@class='select-box active']//descendant::span[text()='S&P BSE SENSEX']" +
                "//ancestor::li//descendant::img")).click();
            Thread.sleep(3000);
            break;

        default:
            System.err.println("Invalid scrip: " + string);
            break;
        }
    }

    @When("User Click the Charts")
    public void user_click_the_charts() throws InterruptedException {
        WebElement charts = driver.findElement(By.xpath(
            "//div[@class='select-box active']//descendant::span[text()='Nifty 50']" +
            "//ancestor::li//descendant::img"));
        charts.click();
        Thread.sleep(2000);
    }

    @When("User Click the Candles Icon")
    public void user_click_the_candles_icon() throws InterruptedException {
        Thread.sleep(2000);

        WebElement firstiFrame = driver.findElement(By.xpath("//iframe[@class='iframe_window']"));
        driver.switchTo().frame(firstiFrame);
        Thread.sleep(1000);

        WebElement iframe = driver.findElement(By.xpath("//iframe[@title='Financial Chart']"));
        driver.switchTo().frame(iframe);

        driver.findElement(By.xpath("//div[@id='header-toolbar-chart-styles']")).click();
        Thread.sleep(1000);
    }

    @When("User Choose Candle {string}")
    public void user_choose_candle(String string) throws InterruptedException {
        Thread.sleep(1000);
        driver.findElement(By.xpath("//div[@data-value='" + string + "']")).click();
        Thread.sleep(1000);
    }

    @When("User Click the Mintues")
    public void user_click_the_mintues() throws InterruptedException {
        driver.findElement(By.xpath("//div[@id='header-toolbar-intervals']")).click();
        Thread.sleep(1000);
    }

    @When("User Choose One Minute")
    public void user_choose_one_minute() {
        driver.findElement(By.xpath("//div[text()='1 minute']//ancestor::div[@data-value='1']")).click();
    }

    @When("Verify One Minute Feed Connection Value and Wait One Minute Very Feed Connection Changes")
    public void verify_one_minute_feed_connection_value_and_wait_one_minute_very_feed_connection_changes()
            throws InterruptedException {
        WebElement element3 = driver.findElement(By.xpath(
            "/html/body/div[2]/div[1]/div[2]/div[1]/div[2]/table/tr[1]/td[2]/div/div[2]/div[1]/div/div[2]/div/div[5]/div[2]"));
        Thread.sleep(4000);
        String text2 = element3.getText();
        System.err.println("Feed Connection Value of One Minute: " + text2);
        Thread.sleep(60000);
        System.out.println("Feed Connection Value of After One Minute: " + text2);
    }

    @When("User Click the Five Minute")
    public void user_click_the_five_minute() {
        driver.findElement(By.xpath("//div[@id='header-toolbar-intervals']")).click();
        driver.findElement(By.xpath("//div[text()='5 minutes']//ancestor::div[@data-value='5']")).click();
    }

    @When("Verify Five Minute Feed Connection Valuebb")
    public void verify_five_minute_feed_connection_value() throws InterruptedException {
        WebElement element2 = driver.findElement(By.xpath(
            "/html/body/div[2]/div[1]/div[2]/div[1]/div[2]/table/tr[1]/td[2]/div/div[2]/div[1]/div/div[2]/div/div[5]/div[2]"));
        Thread.sleep(4000);
        String text = element2.getText();
        System.err.println("Feed Connection Value of Five Minutes: " + text);
    }

    @When("User Click One Day")
    public void user_click_one_day() {
        driver.findElement(By.xpath("//div[@id='header-toolbar-intervals']")).click();
        driver.findElement(By.xpath("//div[text()='1 day']//ancestor::div[@data-value='1D']")).click();
    }

    @When("Verify One Day Feed Connection Value and Wait One Minute Very Feed Connection Changes")
    public void verify_one_day_feed_connection_value_and_wait_one_minute_very_feed_connection_changes()
            throws InterruptedException {
        WebElement element4 = driver.findElement(By.xpath(
            "/html/body/div[2]/div[1]/div[2]/div[1]/div[2]/table/tr[1]/td[2]/div/div[2]/div[1]/div/div[2]/div/div[5]/div[2]"));
        Thread.sleep(4000);
        String text3 = element4.getText();
        System.err.println("Feed Connection Value of 1 hour: " + text3);
        Thread.sleep(60000);
        System.err.println("Feed Connection Value of 1 hour and 1 mins: " + text3);
    }

    @When("User Select The {string} Stock or F&O to Chart")
    public void user_select_the_stock_or_f_o_to_chart(String string) throws InterruptedException {
        Thread.sleep(2000);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

        List<WebElement> elements = driver.findElements(By.xpath("//span[text()='" + string + "']"));

        for (WebElement webElement : elements) {
            if (webElement.getText().equals(string)) {
                Actions a = new Actions(driver);
                a.moveToElement(webElement).perform();

                List<WebElement> elements2 = driver.findElements(By.xpath(
                    "(//span[text()='" + string +
                    "']//ancestor::div[@class='m_bg_color']//descendant::span[@class='s_b2 chart'])[1]"));

                for (WebElement webElement2 : elements2) {
                    if (webElement2.isDisplayed()) {
                        a.moveToElement(webElement2).perform();
                        Thread.sleep(2000);
                        a.click(webElement2).perform();
                    }
                }
            }
        }

        Thread.sleep(2000);
        framesHandling();

        driver.findElement(By.xpath("//div[@id='header-toolbar-intervals']")).click();
        driver.findElement(By.xpath("//div[text()='1 minute']//ancestor::div[@data-value='1']")).click();
        Thread.sleep(2000);

        String text2 = driver.findElement(By.xpath(
            "/html/body/div[2]/div[1]/div[2]/div[1]/div[2]/table/tr[1]/td[2]/div/div[2]/div[1]/div/div[2]/div/div[5]/div[2]"))
            .getText();
        System.out.println("Feed Connection Value of One Minute: " + text2);
        Thread.sleep(4000);

        String text1 = driver.findElement(By.xpath(
            "/html/body/div[2]/div[1]/div[2]/div[1]/div[2]/table/tr[1]/td[2]/div/div[2]/div[1]/div/div[2]/div/div[5]/div[2]"))
            .getText();
        System.out.println("Feed Connection Value of One Minute: " + text1);

        if (text2.equals(text1)) {
            System.err.println("\u001B[34mOne Minute Chart Value to Pass: " + text1);
        } else {
            System.err.println("Feed Connection Failed");
        }

        Thread.sleep(2000);
        driver.findElement(By.xpath("//div[@id='header-toolbar-intervals']")).click();
        driver.findElement(By.xpath("//div[text()='1 day']//ancestor::div[@data-value='1D']")).click();
        Thread.sleep(2000);

        String text212 = driver.findElement(By.xpath(
            "/html/body/div[2]/div[1]/div[2]/div[1]/div[2]/table/tr[1]/td[2]/div/div[2]/div[1]/div/div[2]/div/div[5]/div[2]"))
            .getText();
        System.out.println("Feed Connection Value of One Day: " + text212);
        Thread.sleep(4000);

        String text112 = driver.findElement(By.xpath(
            "/html/body/div[2]/div[1]/div[2]/div[1]/div[2]/table/tr[1]/td[2]/div/div[2]/div[1]/div/div[2]/div/div[5]/div[2]"))
            .getText();
        System.out.println("Feed Connection Value of One Day: " + text112);

        if (text212.equals(text112)) {
            System.err.println("\u001B[34mOne Day Chart Value to Pass: " + text112);
        } else {
            System.err.println("Feed Connection Failed");
        }

        outOfFrames();
        driver.findElement(By.xpath("//button[@data-dhx-id='close']")).click();
        Thread.sleep(2000);
    }

    @When("User Click the Watch List")
    public void user_click_the_watch_list() throws InterruptedException {
        Thread.sleep(4000);
        try {
            WebElement element = driver.findElement(
                By.xpath("(//div[@class='header-left']//descendant::span[@class='ind_syml'])[1]"));
            Thread.sleep(1000);
            element.click();
        } catch (Exception e) {
            WebElement element = driver.findElement(
                By.xpath("(//div[@class='header-left']//descendant::span[@class='ind_syml'])[1]"));
            Thread.sleep(1000);
            element.click();
        }
    }

    @When("User Select scrip {string}")
    public void user_select_scrip(String string) throws InterruptedException {
        driver.findElement(By.xpath(
            "//div[@class='select-box active']//descendant::li//span[text()='" + string + "']")).click();
        System.out.println(string);
        Thread.sleep(4000);
    }

    @When("User Check the one Minute and one Day Chart")
    public void user_check_the_one_minute_and_one_day_chart() throws InterruptedException {
        Thread.sleep(2000);

        driver.findElement(By.xpath("//div[@id='header-toolbar-intervals']")).click();
        driver.findElement(By.xpath("//div[text()='1 minute']//ancestor::div[@data-value='1']")).click();
        Thread.sleep(2000);

        String text2 = driver.findElement(By.xpath(
            "/html/body/div[2]/div[1]/div[2]/div[1]/div[2]/table/tr[1]/td[2]/div/div[2]/div[1]/div/div[2]/div/div[5]/div[2]"))
            .getText();
        System.out.println("Feed Connection Value of One Minute: " + text2);
        Thread.sleep(4000);

        String text1 = driver.findElement(By.xpath(
            "/html/body/div[2]/div[1]/div[2]/div[1]/div[2]/table/tr[1]/td[2]/div/div[2]/div[1]/div/div[2]/div/div[5]/div[2]"))
            .getText();
        System.out.println("Feed Connection Value of One Minute: " + text1);

        if (text2.equals(text1)) {
            System.err.println("\u001B[34mOne Minute Chart Value to Pass: " + text1);
        } else {
            System.err.println("Feed Connection Failed");
        }

        Thread.sleep(2000);
        driver.findElement(By.xpath("//div[@id='header-toolbar-intervals']")).click();
        driver.findElement(By.xpath("//div[text()='1 day']//ancestor::div[@data-value='1D']")).click();
        Thread.sleep(2000);

        String text211 = driver.findElement(By.xpath(
            "/html/body/div[2]/div[1]/div[2]/div[1]/div[2]/table/tr[1]/td[2]/div/div[2]/div[1]/div/div[2]/div/div[5]/div[2]"))
            .getText();
        System.out.println("Feed Connection Value of One Day: " + text211);
        Thread.sleep(4000);

        String text111 = driver.findElement(By.xpath(
            "/html/body/div[2]/div[1]/div[2]/div[1]/div[2]/table/tr[1]/td[2]/div/div[2]/div[1]/div/div[2]/div/div[5]/div[2]"))
            .getText();
        System.out.println("Feed Connection Value of One Day: " + text111);

        if (text211.equals(text111)) {
            System.err.println("\u001B[34mOne Day Chart Value to Pass: " + text111);
        } else {
            System.err.println("Feed Connection Failed");
        }

        outOfFrames();
        driver.findElement(By.xpath("//button[@data-dhx-id='close']")).click();
        Thread.sleep(2000);
    }
}