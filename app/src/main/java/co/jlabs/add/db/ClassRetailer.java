package co.jlabs.add.db;

/**
 * Created by JLabs on 08/26/16.
 */
public class ClassRetailer {
    int id;
    public Integer retailerID;
    public String retailer;
    public String lat;
    public String lng;
    public String phone;
    public String email;
    public String address;
    public String areas;

    public ClassRetailer(){
        retailerID=new Integer("0");
        retailer=new String();
        lat=new String();
        lng=new String();
        phone=new String();
        email=new String();
        address=new String();
        areas=new String();
    }
}
