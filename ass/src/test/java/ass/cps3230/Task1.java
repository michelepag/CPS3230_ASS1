package ass.cps3230;

import ass.cps3230.spies.UploadAlertSpy;
import ass.cps3230.util.Ecommerce;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class Task1 {
    ScreenScraper ss = new ScreenScraper();
    RequestMaker rm= new RequestMaker();


    @Test
    public void testSetDescription(){

        //setup
        Ecommerce mock_e_commerce = Mockito.mock(Ecommerce.class);
        Mockito.when(mock_e_commerce.getDescr()).thenReturn("awesome laptop");
        ss.setE_commerce(mock_e_commerce);

        //Exercise
        ss.setProductDescription();

        //verify
        Assertions.assertEquals("awesome laptop",ss.getDescription());
    }

    @Test
    public void testSetImg(){
        //setup
        Ecommerce mock_e_commerce = Mockito.mock(Ecommerce.class);
        Mockito.when(mock_e_commerce.getImg()).thenReturn("https://m.media-amazon.com/images/I/71rmymgVWVL._AC_SL1500_.jpg");
        ss.setE_commerce(mock_e_commerce);

        //Exercise
        ss.setProductImg();

        //verify
        Assertions.assertEquals("https://m.media-amazon.com/images/I/71rmymgVWVL._AC_SL1500_.jpg",ss.getImg());
    }


    @Test
    public void testSetLink(){
        //setup
        Ecommerce mock_e_commerce = Mockito.mock(Ecommerce.class);
        Mockito.when(mock_e_commerce.getLink()).thenReturn("https://www.amazon.com/HP-Dual-Core-Processor-Graphics-Ethernet/dp/B09SZPSTN1/ref=sr_1_1_sspa?keywords=hp+laptop&qid=1668069582&sr=8-1-spons&psc=1");
        ss.setE_commerce(mock_e_commerce);

        //Exercise
        ss.setProductLink();

        Assertions.assertEquals("https://www.amazon.com/HP-Dual-Core-Processor-Graphics-Ethernet/dp/B09SZPSTN1/ref=sr_1_1_sspa?keywords=hp+laptop&qid=1668069582&sr=8-1-spons&psc=1",ss.getLink());
    }


    @Test
    public void testSetPrice(){
        //setup
        Ecommerce mock_e_commerce = Mockito.mock(Ecommerce.class);
        Mockito.when(mock_e_commerce.getPrice()).thenReturn(49999);
        ss.setE_commerce(mock_e_commerce);

        //Exercise
        ss.setProductPrice();

        //verify
        Assertions.assertEquals(49999,ss.getPrice());
    }

    @Test
    public void testSetTypeAlert(){
        // setup
        Ecommerce mock_e_commerce = Mockito.mock(Ecommerce.class);
        Mockito.when(mock_e_commerce.getType()).thenReturn(5);
        ss.setE_commerce(mock_e_commerce);

        //Exercise
        ss.setTypeAlert();

        //verify
        Assertions.assertEquals(5,ss.getTypeAlert());
    }

    @Test
    public void testSetTitle(){
        //setup
        Ecommerce mock_e_commerce = Mockito.mock(Ecommerce.class);
        Mockito.when(mock_e_commerce.getTitle()).thenReturn("hp laptop");
        ss.setE_commerce(mock_e_commerce);

        //Exercise
        ss.searchProduct("hp laptop");
        //verify
        Assertions.assertEquals("hp laptop",ss.getHeading());
    }

    @Test
    public void numberOfUploads(){
        //set up
        UploadAlertSpy uplAlSpy = new UploadAlertSpy();
        ss.setUploadAlert(uplAlSpy);

        //Exercise
        ss.screenScrape("hp laptop");
        ss.screenScrape("lenovo laptop");
        ss.screenScrape("acer laptop");

        //verify
        Assertions.assertEquals(3,uplAlSpy.numOfUploads);
    }

    @Test
    public void deleteUploads(){
        //set up
        UploadAlertSpy uplAlSpy = new UploadAlertSpy();
        ss.setUploadAlert(uplAlSpy);
        rm.setUploadAlert(uplAlSpy);

        //Exercise
        ss.screenScrape("hp laptop");
        ss.screenScrape("lenovo laptop");
        ss.screenScrape("acer laptop");
        rm.deleteReq();

        //verify
        Assertions.assertEquals(0,uplAlSpy.numOfUploads);
    }


}
