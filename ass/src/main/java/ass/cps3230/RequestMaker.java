package ass.cps3230;

import ass.cps3230.util.UploadAlert;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import java.io.IOException;

public class RequestMaker {

    public UploadAlert upAl;

    public CloseableHttpClient httpClient;

    public RequestMaker(){
        httpClient = HttpClients.createDefault();
    }

    public void sendPost(Alert my_alert) throws Exception {

        HttpPost post = new HttpPost("https://api.marketalertum.com/Alert");
        post.setHeader("Accept", "application/json");
        post.setHeader("Content-type", "application/json");

        JSONObject data = new JSONObject();

        data.put("alertType",my_alert.typeAlert);
        data.put("heading",my_alert.heading);
        data.put("description",my_alert.description);
        data.put("url",my_alert.link);
        data.put("imageUrl",my_alert.img);
        data.put("postedBy",my_alert.user_id);
        data.put("priceInCents",my_alert.price);

        String body = data.toString();

        StringEntity strEnt = new StringEntity(body, ContentType.APPLICATION_JSON);
        post.setEntity(strEnt);


        try (CloseableHttpClient httpClient = HttpClients.createDefault();
             CloseableHttpResponse response = httpClient.execute(post)) {

            System.out.println(EntityUtils.toString(response.getEntity()));
        }
    }

    public void makePostRequest(Alert my_alert){
        RequestMaker obj = new RequestMaker();

        try {
            System.out.println("Send Http POST request");
            obj.sendPost(my_alert);
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            try {
                obj.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void close() throws IOException {
        httpClient.close();
    }

    public void setUploadAlert(UploadAlert upl){
        this.upAl = upl;
    }

    public void deleteReq(){

        if(upAl != null){
            upAl.deleteAllAlerts();
        }else {
            CloseableHttpClient client = HttpClients.createDefault();
            HttpDelete httpDelete = new HttpDelete("https://api.marketalertum.com/Alert?userId=73ed1cab-f8c2-4ff8-84b6-d7f81d835829");
            try {
                String responseBody = client.execute(httpDelete).toString();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            try {
                client.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

}
