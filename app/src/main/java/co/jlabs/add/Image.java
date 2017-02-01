package co.jlabs.add;

import org.json.JSONArray;

import java.io.Serializable;

/**
 * Created by JLabs on 08/30/16.
 */
public class Image implements Serializable {
    private String company_id,company_name,product_name,month,shelf_life,weight,item_id,categoryID,category,barcode,detail,add_by;
    private String retailer_id,name,email,contact,add_by2,address,locality,sublocality;
    private int price,min_order,del_c;
    private double lon,lat;
    private JSONArray areas;



    public Image() {
    }

    public String getCompany_name() {
        return company_name;
    }
    public void setCompany_name(String company_name) {
        this.company_name = company_name;
    }
    public String getCompany_id() {
        return company_id;
    }
    public void setCompany_id(String company_id) {
        this.company_id = company_id;
    }
    public String getProduct_name() {
        return product_name;
    }
    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }
    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }
    public String getShelfLife() {
        return shelf_life;
    }

    public void setShelfLife(String mfg) {
        this.shelf_life = mfg;
    }
    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }
    public String getItem_id() {
        return item_id;
    }

    public void setItem_id(String item_id) {
        this.item_id = item_id;
    }
    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getCategoryID() {
        return categoryID;
    }

    public void setCategoryID(String categoryID) {
        this.categoryID = categoryID;
    }
    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }
    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }
    public String getAdd_by() {
        return add_by;
    }

    public void setAdd_by(String add_by) {
        this.add_by = add_by;
    }


    public String getRetailer_id() {
        return retailer_id;
    }

    public void setRetailer_id(String retailer_id) {
        this.retailer_id = retailer_id;
    }
    public String getRName() {
        return name;
    }

    public void setRName(String name) {
        this.name = name;
    }
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }
    public String getAddby2() {
        return add_by2;
    }

    public void setAddby2(String add_by2) {
        this.add_by2 = add_by2;
    }
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
    public JSONArray getAreas() {
        return areas;
    }

    public void setAreas(JSONArray areas) {
        this.areas = areas;
    }

    public int getMin_order() {
        return min_order;
    }

    public void setMin_order(int min_order) {
        this.min_order = min_order;
    }
    public int getDel_c() {
        return del_c;
    }

    public void setDel_c(int del_c) {
        this.del_c = price;
    }
    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public String getLocality() {
        return locality;
    }
    public void setLocality(String locality) {
        this.locality = locality;
    }


    public String getSublocality() {
        return sublocality;
    }
    public void setSublocality(String sublocality) {
        this.sublocality = sublocality;
    }








}
