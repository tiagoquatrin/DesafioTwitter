/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package teste;

/**
 *
 * @author Tiago Quatrin
 */


import java.util.regex.Pattern;
import java.util.concurrent.TimeUnit;
import org.junit.*;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;

public class Teste {
  private WebDriver driver;
  private String baseUrl;
  private boolean acceptNextAlert = true;
  private StringBuffer verificationErrors = new StringBuffer();

  @Before
  public void setUp() throws Exception {
    driver = new FirefoxDriver();
    baseUrl = "https://twitter.com/";
    driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
  }

  @Test
  public void testE() throws Exception {
    driver.get(baseUrl + "/");
    driver.findElement(By.cssSelector("img.front-image")).click();
    driver.findElement(By.cssSelector("div.username.field > #signin-email")).clear();
    driver.findElement(By.cssSelector("div.username.field > #signin-email")).sendKeys("desafiopostnotwitter@gmail.com");
    driver.findElement(By.cssSelector("div.password.flex-table-form > #signin-password")).click();
    driver.findElement(By.cssSelector("div.password.flex-table-form > #signin-password")).clear();
    driver.findElement(By.cssSelector("div.password.flex-table-form > #signin-password")).sendKeys("1a2b3c");
    driver.findElement(By.xpath("//button[@type='submit']")).click();
    // ERROR: Caught exception [ERROR: Unsupported command [clickAt | document.getElementById("tweet-box-home-timeline"); | ]]
    // ERROR: Caught exception [Error: Dom locators are not implemented yet!]
    driver.findElement(By.xpath("(//button[@type='button'])[27]")).click();
    try {
      assertEquals("teste", driver.findElement(By.xpath("//div[2]/p")).getText());
    } catch (Error e) {
      verificationErrors.append(e.toString());
    }
    driver.findElement(By.xpath("(//button[@type='button'])[33]")).click();
    driver.findElement(By.cssSelector("li.js-actionDelete > button.dropdown-link")).click();
    driver.findElement(By.xpath("//div[@id='delete-tweet-dialog-dialog']/div[2]/div[4]/button[2]")).click();
    driver.findElement(By.id("user-dropdown-toggle")).click();
    driver.findElement(By.cssSelector("#signout-button > button.dropdown-link")).click();
  }

  @After
  public void tearDown() throws Exception {
    driver.quit();
    String verificationErrorString = verificationErrors.toString();
    if (!"".equals(verificationErrorString)) {
      fail(verificationErrorString);
    }
  }

  private boolean isElementPresent(By by) {
    try {
      driver.findElement(by);
      return true;
    } catch (NoSuchElementException e) {
      return false;
    }
  }

  private boolean isAlertPresent() {
    try {
      driver.switchTo().alert();
      return true;
    } catch (NoAlertPresentException e) {
      return false;
    }
  }

  private String closeAlertAndGetItsText() {
    try {
      Alert alert = driver.switchTo().alert();
      String alertText = alert.getText();
      if (acceptNextAlert) {
        alert.accept();
      } else {
        alert.dismiss();
      }
      return alertText;
    } finally {
      acceptNextAlert = true;
    }
  }
}
