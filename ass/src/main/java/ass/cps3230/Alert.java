package ass.cps3230;

public class Alert {

    protected Integer price;
    protected String heading;
    protected String description;
    protected Integer typeAlert;
    protected String link;
    protected String img;

    protected String user_id = "73ed1cab-f8c2-4ff8-84b6-d7f81d835829";


    public Alert(Integer _price,String _title,String _description,Integer _typeAlert,String _link,String _img){
        price = _price;
        heading = _title;
        description = _description;
        typeAlert = _typeAlert;
        link= _link;
        img =_img;
    }
}
