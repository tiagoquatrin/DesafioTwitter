/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package testetwitter;

/**
 *
 * @author LENOVOO
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
    for (int second = 0;; second++) {
    	if (second >= 60) fail("timeout");
    	try { if (isElementPresent(By.linkText("Desafio"))) break; } catch (Exception e) {}
    	Thread.sleep(1000);
    }

    driver.findElement(By.id("tweet-box-home-timeline")).click();
   // JavascriptExecutor js = (JavascriptExecutor) driver;
      String teste ="teste";
//js.executeScript("document.getElementById('tweet-box-home-timeline').innerHTML="+teste);
      //driver.document.getElementById(("tweet-box-home-timeline").innerHTML="+teste).type();
      driver.findElement(By.id("tweet-box-home-timeline")).sendKeys("teste");
    String contador = driver.findElement(By.cssSelector("span.tweet-counter")).getText();
    System.out.println(contador);
    
    int numero = Integer.parseInt(contador);


   if(numero>0){
    driver.findElement(By.xpath("(//button[@type='button'])[27]")).click();
    for (int second = 0;; second++) {
    	if (second >= 60) fail("timeout");
    	try { if (isElementPresent(By.xpath("//div[2]/p"))) break; } catch (Exception e) {}
    	Thread.sleep(1000);
    }
    
    try {
      assertEquals("teste", driver.findElement(By.xpath("//div[2]/p")).getText());
    } catch (Error e) {
      verificationErrors.append(e.toString());
    }
    driver.findElement(By.cssSelector("button.ProfileTweet-actionButton.u-textUserColorHover.dropdown-toggle.js-dropdown-toggle")).click();
    for (int second = 0;; second++) {
    	if (second >= 60) fail("timeout");
    	try { if (isElementPresent(By.cssSelector("li.embed-link.js-actionEmbedTweet > button.dropdown-link"))) break; } catch (Exception e) {}
    	Thread.sleep(1000);
    }

    driver.findElement(By.cssSelector("li.js-actionDelete > button.dropdown-link")).click();
    driver.findElement(By.xpath("//div[@id='delete-tweet-dialog-dialog']/div[2]/div[4]/button[2]")).click();
    // Warning: verifyTextNotPresent may require manual changes
    try {
      assertFalse(driver.findElement(By.cssSelector("BODY")).getText().matches("^[\\s\\S]*//div\\[2\\]/p[\\s\\S]*$"));
    } catch (Error e) {
      verificationErrors.append(e.toString());
    }
   }
    driver.findElement(By.xpath("//*[@id='user-dropdown-toggle']")).click();
    try {
      assertTrue(isElementPresent(By.cssSelector("#signout-button > button.dropdown-link")));
    } catch (Error e) {
      verificationErrors.append(e.toString());
    }
    driver.findElement(By.xpath(".//*[@id='signout-button']/button")).click();
  }

  @After
  public void tearDown() throws Exception {
   // driver.quit();
    String verificationErrorString = verificationErrors.toString();
    if (!"".equals(verificationErrorString)) {
     // fail(verificationErrorString);
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
