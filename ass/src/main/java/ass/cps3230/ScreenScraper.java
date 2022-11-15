package ass.cps3230;

import java.util.List;

import ass.cps3230.util.UploadAlert;
import ass.cps3230.util.Ecommerce;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class ScreenScraper {

    public WebDriver driver;

    protected RequestMaker rm;

    protected Ecommerce e_commerce;

    protected UploadAlert upAl;
    protected int price;
    protected String heading;
    protected String description;
    protected int typeAlert;
    protected String link;
    protected String img;


    public ScreenScraper(){
        rm=new RequestMaker();
    }

    public void setDriver(){
        System.setProperty("webdriver.chrome.driver","/Users/miche/WebTesting/chromedriver/chromedriver.exe");
        driver = new ChromeDriver();
    }

    public void setUploadAlert(UploadAlert upl){
        this.upAl = upl;
    }

    public void setE_commerce(Ecommerce e_commerce) {
        this.e_commerce = e_commerce;
    }

    public void getToAmazon(){

        driver.get("https://www.amazon.com/"); //get to amazon
    }

    public void closeDriver(){
        driver.quit();
    }

    public void searchProduct(String name){

        //the heading will be the name of the product itself

        if (e_commerce != null){
            heading = e_commerce.getTitle();
        }else{
            //Enter the name of the product on amazon
            WebElement searchBox = driver.findElement(By.id("twotabsearchtextbox"));
            searchBox.sendKeys(name);
            WebElement searchButton = driver.findElement(By.id("nav-search-submit-button"));
            searchButton.click();
            heading = name;
        }
    }



    public void setProductDescription(){

        //We check if the e-commerce mock service is injected. If it's the case we are not scraping the page
        if(e_commerce != null){
            description = e_commerce.getDescr();
        }
        else{
            List<WebElement> descriptions= driver.findElements(By.xpath("//span[@class='a-size-base-plus a-color-base a-text-normal']"));
            description = descriptions.get(0).getText();
        }
    }

    public void setProductPrice(){
        //all prices of the page
        if(e_commerce != null){
            price = e_commerce.getPrice();
        }
        else {
            List<WebElement> prices = driver.findElements(By.xpath("//span[@class='a-offscreen']"));
            String string_price = prices.get(0).findElement(By.xpath("//span[@class='a-price-whole']")).getText() + prices.get(0).findElement(By.xpath("//span[@class='a-price-fraction']")).getText();
            price = Integer.parseInt(string_price);
        }
    }

    public void setProductLink(){
        //link links
        if(e_commerce != null){
            link = e_commerce.getLink();
        }
        else {
            List<WebElement> links = driver.findElements(By.xpath("//a[@class='a-link-normal s-no-outline']"));
            link = links.get(0).getAttribute("href");
        }
    }

    public void setProductImg(){
        //images
        if(e_commerce != null){
            img = e_commerce.getImg();
        }
        else {
            List<WebElement> images = driver.findElements(By.xpath("//img[@class='s-image']"));
            img = images.get(0).getAttribute("src");
        }
    }

    public void setTypeAlert(){

        if(e_commerce != null){
            typeAlert = e_commerce.getType();
        }else {
            typeAlert = 6;
        }
    }

    public void getProductInfo(){
        setProductDescription();
        setProductLink();
        setProductImg();
        setTypeAlert();
        setProductPrice();
    }

    public void screenScrape(String item_name){

        if(upAl !=  null){
            upAl.uploadAlert();
        }
        else {
            getToAmazon();
            searchProduct(item_name);
            getProductInfo();
            Alert my_alert = new Alert(this.price, this.heading, this.description, this.typeAlert, this.link, this.img);
            rm.makePostRequest(my_alert);
        }
    }



    public String getHeading(){
        return heading;
    }

    public String getDescription(){
        return description;
    }

    public String getLink(){
        return link;
    }

    public String getImg(){
        return img;
    }

    public int getPrice(){
        return price;
    }

    public int getTypeAlert(){
        return typeAlert;
    }

}
