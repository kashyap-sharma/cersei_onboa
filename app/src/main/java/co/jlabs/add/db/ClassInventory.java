package co.jlabs.add.db;

/**
 * Created by JLabs on 08/26/16.
 */
public class ClassInventory {
    int id;
    public Integer inventoryID;
    public String company_name;
    public String product_name;
    public String barcode;
    public String category;
    public String product_detail;
    public String weight;
    public String MFG;
    public String month;
    public String price;

   public ClassInventory(){
        inventoryID=new Integer("0");
        company_name=new String();
        product_name=new String();
        barcode=new String();
        category=new String();
        product_detail=new String();
        weight=new String();
        month=new String();
        MFG=new String();
        price=new String();
    }

}
