package ass.cps3230;

public class Main {


    public static void main(String[] args) {

        ScreenScraper ss = new ScreenScraper();
        ss.setDriver();

        ss.screenScrape("lenovo laptop");
        ss.screenScrape("hp laptop");
        ss.screenScrape("asus laptop");
        ss.screenScrape("acer laptop");
        ss.screenScrape("dell laptop");

        ss.closeDriver();

    }
}