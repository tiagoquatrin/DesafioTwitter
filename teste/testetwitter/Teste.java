
package testetwitter;

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
  //Variáveis iniciais  
  private WebDriver driver;  
  private String baseUrl;
  private boolean acceptNextAlert = true;
  private StringBuffer verificationErrors = new StringBuffer();

 @Before
 public void setUp() throws Exception {
  driver = new FirefoxDriver(); //Browser
  baseUrl = "https://twitter.com/"; //Endereço base par testes
  driver.manage().window().maximize();//maximiza a tela
  driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS); //Tempo limite de espera
  }

  @Test
  public void testE() throws Exception {
   driver.get(baseUrl + "/");
   driver.findElement(By.cssSelector("img.front-image")).click(); //clique no campo usuário
   driver.findElement(By.cssSelector("div.username.field > #signin-email")).clear();//limpa o campo usuário
   driver.findElement(By.cssSelector("div.username.field > #signin-email")).sendKeys("desafiopostnotwitter@gmail.com"); //digita o usuário no respectivo campo
   driver.findElement(By.cssSelector("div.password.flex-table-form > #signin-password")).click(); //clica no campo senha
   driver.findElement(By.cssSelector("div.password.flex-table-form > #signin-password")).clear(); //limpa o campo senha
   driver.findElement(By.cssSelector("div.password.flex-table-form > #signin-password")).sendKeys("1a2b3c"); //digita a senha no respectivo campo
   driver.findElement(By.xpath("//button[@type='submit']")).click(); //clica no botão de Login
    for (int second = 0;; second++) {
    	if (second >= 60) fail("timeout");
    	try { if (isElementPresent(By.linkText("Desafio"))) break; } catch (Exception e) {} //Verifica se o nome do usuário está presente, significando que o mesmo está logado
    	Thread.sleep(1000);
    }

   driver.findElement(By.id("tweet-box-home-timeline")).click(); //clica no campo para habilitá-lo
   driver.findElement(By.id("tweet-box-home-timeline")).sendKeys("teste"); //digita a mensagem no campo
   String contador = driver.findElement(By.cssSelector("span.tweet-counter")).getText(); //Verifica o valor da contagem dos caracteres
   int numero = Integer.parseInt(contador);//Transforma a string contador em inteiro


   if(numero>0 && contador.length()<=140){ //verifica se o numero (no Twitter) de caracteres não está negativo e, se o tamanho do 'tweet' é menor ou igual a 140
       driver.findElement(By.xpath("(//button[@type='button'])[27]")).click(); //Clica no botão 'TWEETAR' 
    for (int second = 0;; second++) {
    	if (second >= 60) fail("timeout");
    	try { if (isElementPresent(By.xpath("//div[2]/p"))) break; } catch (Exception e) {}//verifica se a mensagem foi criada na timeline
    	Thread.sleep(1000);
    }
    
    try {
        assertEquals("teste", driver.findElement(By.xpath("//div[2]/p")).getText()); //verifica se o texto postado foi de acordo com o enviado
        } catch (Error e) {
        verificationErrors.append(e.toString());
     }
        driver.findElement(By.cssSelector("button.ProfileTweet-actionButton.u-textUserColorHover.dropdown-toggle.js-dropdown-toggle")).click(); //clica no botão para aparecer as opções do menu
     for (int second = 0;; second++) {
    	if (second >= 60) fail("timeout");
    	try { if (isElementPresent(By.cssSelector("li.embed-link.js-actionEmbedTweet > button.dropdown-link"))) break; } catch (Exception e) {} //verifica que as opções apareceram
    	Thread.sleep(1000);
    }

    driver.findElement(By.cssSelector("li.js-actionDelete > button.dropdown-link")).click(); //exclui o tweet -> isso possibilita repetir o teste!!
    driver.findElement(By.xpath("//div[@id='delete-tweet-dialog-dialog']/div[2]/div[4]/button[2]")).click(); //confirma a exclusão do tweet
    
    try {
      assertFalse(driver.findElement(By.cssSelector("BODY")).getText().matches("^[\\s\\S]*//div\\[2\\]/p[\\s\\S]*$"));//verifica que o tweet foi apagado
    } catch (Error e) {
      verificationErrors.append(e.toString());
    }
   }
    driver.findElement(By.xpath("//*[@id='user-dropdown-toggle']")).click(); //clica no botão 'Perfil e Configurações'
    try {
      assertTrue(isElementPresent(By.cssSelector("#signout-button > button.dropdown-link")));//Verifica que as opções do menu apareceram
    } catch (Error e) {
      verificationErrors.append(e.toString());
    }
    driver.findElement(By.xpath(".//*[@id='signout-button']/button")).click();//clica no botão de logout
  }

  @After
  //métodos de verificações e ações posteriores a execução
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
