package ass.cps3230;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.List;


public class Task2 {

    WebDriver driver;
    String id="73ed1cab-f8c2-4ff8-84b6-d7f81d835829";
    String invalid_id="73ed1cab-f8c2";

    ScreenScraper ss;

    RequestMaker rm = new RequestMaker();

    //helper function to check correct links
    public boolean CheckLink(List<WebElement> list) {
        for (WebElement e : list) {
            if (e.getAttribute("href").length() == 0) {
                return false;
            }
        }
        return  true;
    }

    //helper function to check correct heading
    public boolean CheckDescr(List<WebElement> list) {
        for (WebElement e : list) {
            if (e.getText().length() == 0) {
                return false;
            }
        }
        return  true;
    }

//-------------------- TEST 1 --------------------------------
    @Given("I am a user of marketalertum")
    public void iAmAUserOfMarketalertum() {
        System.setProperty("webdriver.chrome.driver","/Users/miche/WebTesting/chromedriver/chromedriver.exe");
        driver = new ChromeDriver();
        driver.get("https://www.marketalertum.com/");
    }

    @When("I login using valid credentials")
    public void iLoginUsingValidCredentials() {
        driver.findElement(By.xpath("//a[@href='/Alerts/Login']")).click();

        WebElement input_text = driver.findElement(By.id("UserId"));
        input_text.sendKeys(id);

        input_text.sendKeys(Keys.ENTER);
    }

    @Then("I should see my alerts")
    public void iShouldSeeMyAlerts() {
        //I check for the specific title of My alerts Page
        WebElement title = driver.findElement(By.xpath("/html/body/div/main/h1"));
        String text_title = title.getText();

        Assert.assertEquals("Latest alerts for Michele Pagano Mariano",text_title);
        driver.close();
    }

    //------------------------------TEST 2 ----------------
    @When("I login using invalid credentials")
    public void iLoginUsingInvalidCredentials() {
        driver.findElement(By.xpath("//a[@href='/Alerts/Login']")).click();

        //An invalid ID is entered
        WebElement input_text = driver.findElement(By.id("UserId"));
        input_text.sendKeys(invalid_id);

        input_text.sendKeys(Keys.ENTER);
    }

    @Then("I should see the login screen again")
    public void iShouldSeeTheLoginScreenAgain() {
        //We expect to remain on the same page
        String url = driver.getCurrentUrl();
        Assert.assertEquals("https://www.marketalertum.com/Alerts/Login",url);
        driver.close();
    }

    //---------------------------------- TEST 3 ----------------------
    @Given("I am an administrator of the website and I upload 3 alerts")
    public void iAmAnAdministratorOfTheWebsiteAndIUploadAlerts() {
        rm.deleteReq();    //clear MyAlerts section

        ss = new ScreenScraper();
        ss.setDriver();
        ss.screenScrape("hp laptop");
        ss.screenScrape("lenovo laptop");
        ss.screenScrape("acer laptop");
        ss.closeDriver();
    }

    @When("I view a list of alerts")
    public void iViewAListOfAlerts() {
        System.setProperty("webdriver.chrome.driver","/Users/miche/WebTesting/chromedriver/chromedriver.exe");
        driver = new ChromeDriver();
        driver.get("https://www.marketalertum.com/");

        //login
        driver.findElement(By.xpath("//a[@href='/Alerts/Login']")).click();
        WebElement input_text = driver.findElement(By.id("UserId"));
        input_text.sendKeys(id);
        input_text.sendKeys(Keys.ENTER);
    }

    @Then("each alert should contain an icon")
    public void eachAlertShouldContainAnIcon() {
        //check icons, all icons have width 100
        List<WebElement> icons = driver.findElements(By.xpath("//img[@width='100']"));
        Assertions.assertEquals(3,icons.size());
    }

    @And("each alert should contain a heading")
    public void eachAlertShouldContainAHeading() {
        //We check how many h4 element are in the page
        List<WebElement> headings = driver.findElements(By.xpath("//h4"));
        Assertions.assertEquals(3,headings.size());
    }

    @And("each alert should contain a description")
    public void eachAlertShouldContainADescription() {
        //The xpath is retrieved inspecting the page
        List<WebElement> descr = driver.findElements(By.xpath("//tbody/tr[3]/td"));

        //checking for valid description, which means that they shouldn't be empty strings
        Assertions.assertTrue(CheckDescr(descr));
    }

    @And("each alert should contain an image")
    public void eachAlertShouldContainAnImage() {
        //images of the product have width 200
        List<WebElement> imgs = driver.findElements(By.xpath("//img[@width='200']"));
        Assertions.assertEquals(3,imgs.size());
    }

    @And("each alert should contain a price")
    public void eachAlertShouldContainAPrice() {
        //price is under the <b> tag
        List<WebElement> prices = driver.findElements(By.xpath("//b"));
        Assertions.assertEquals(3,prices.size());
    }

    @And("each alert should contain a link to the original product website")
    public void eachAlertShouldContainALinkToTheOriginalProductWebsite() {
        List<WebElement> links = driver.findElements(By.xpath("//a[text()='Visit item']"));

        //checking for valid links, which means that they shouldn't be empty strings
        Assertions.assertTrue(CheckLink(links));
        driver.close();
    }
//------------------------TEST 4 ---------------------------
    @Given("I am an administrator of the website and I upload more than 5 alerts")
    public void iAmAnAdministratorOfTheWebsiteAndIUploadMoreThanAlerts() {
        rm.deleteReq();
        ss = new ScreenScraper();
        ss.setDriver();
        ss.screenScrape("hp laptop");
        ss.screenScrape("lenovo laptop");
        ss.screenScrape("acer laptop");
        ss.screenScrape("hp laptop");
        ss.screenScrape("hp laptop");
        ss.screenScrape("hp laptop");
        ss.screenScrape("hp laptop");

        ss.closeDriver();
    }

    @Then("I should see 5 alerts")
    public void iShouldSeeAlerts() {
        List<WebElement> alerts = driver.findElements(By.xpath("//table[@border='1']"));
        Assert.assertEquals(5, alerts.size());
        driver.close();
    }

//------------------------- TEST 5 -----------------------------------
    @Given("I am an administrator of the website and I upload an alert of type {int}")
    public void iAmAnAdministratorOfTheWebsiteAndIUploadAnAlertOfTypeAlertType(int arg0) {
        rm.deleteReq();
        Alert my_alert = new Alert(49999,"hp laptop","16gb ram",arg0,"lll","aaa");
        rm.makePostRequest(my_alert);
    }

    @Then("I should see 1 alerts")
    public void iShouldSeeAlertsOnce(){
        List<WebElement> alerts = driver.findElements(By.xpath("//table[@border='1']"));
        Assert.assertEquals(1, alerts.size());
    }

    @And("the icon displayed should be {string}")
    public void theIconDisplayedShouldBeIconFileName(String arg0) {
        List<WebElement> icons = driver.findElements(By.xpath("//img[@width='100']"));
        String src=icons.get(0).getAttribute("src");
        Assert.assertEquals(arg0,src);
        driver.close();
    }

}
