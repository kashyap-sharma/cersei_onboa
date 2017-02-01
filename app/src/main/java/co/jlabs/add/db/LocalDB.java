package co.jlabs.add.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by JLabs on 08/26/16.
 */
public class LocalDB extends SQLiteOpenHelper {

    private static final int DB_Version=1;
    private static final String DB_Name="AddDB";

    Context context;

    public LocalDB(Context context){
        super(context,DB_Name,null,DB_Version);
        this.context=context;
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        String CreateInventory = "CREATE TABLE InventoryLocal ( " +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "company_name TEXT, " +
                "product_name TEXT, " +
                "barcode TEXT, " +
                "category TEXT, " +
                "product_detail TEXT, " +
                "weight TEXT, " +
                "MFG TEXT, " +
                "month TEXT, " +
                "price INTEGER);";
        db.execSQL(CreateInventory);
        String CreateRetailer = "CREATE TABLE RetailerLocal ( " +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "retailer_name TEXT, " +
                "lat TEXT, " +
                "lng TEXT, " +
                "phone TEXT, " +
                "email TEXT, " +
                "address TEXT, " +
                "operational_areas TEXT);";
        db.execSQL(CreateRetailer);

    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        db.execSQL("DROP TABLE IF EXISTS InventoryLocal");
        db.execSQL("DROP TABLE IF EXISTS RetailerLocal");
        this.onCreate(db);
    }

    /**
     * CRUD operations (create "add", read "get", update, delete)
     */


    private static final String TableInventory="InventoryLocal";
    private static final String TableRetailer="RetailerLocal";
    private static final String ID="id";
    private static final String CompanyName="company_name";
    private static final String ProductName="product_name";
    private static final String Barcode="barcode";
    private static final String Category="category";
    private static final String ProductDetail="product_detail";
    private static final String Weight="weight";
    private static final String MFG="MFG";
    private static final String Month="month";
    private static final String Price="price";
    private static final String RetailerName="retailer_name";
    private static final String Lat="lat";
    private static final String Long="lng";
    private static final String Phone="phone";
    private static final String Email="email";
    private static final String Address="address";
    private static final String OperationalAreas="operational_areas";


    public int addInventory(ClassInventory ci){
        long flag=-1;
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(CompanyName, ci.company_name);
        values.put(ProductName, ci.product_name);
        values.put(Barcode, ci.barcode);
        values.put(Category, ci.category);
        values.put(ProductDetail, ci.product_detail);
        values.put(Weight, ci.weight);
        values.put(MFG, ci.MFG);
        values.put(Month, ci.month);
        values.put(Price, ci.price);
        flag=db.insert(TableInventory, null, values);
        Log.e("Kashyap",""+flag);
        values.clear();
        db.close();
        return (int)flag;


    }

    public int addRetailer(ClassRetailer cr){
        long flag=-1;
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(RetailerName, cr.retailer);
        values.put(Lat, cr.lat);
        values.put(Long, cr.lng);
        values.put(Phone, cr.phone);
        values.put(Email, cr.email);
        values.put(Address, cr.address);
        values.put(OperationalAreas, cr.areas);
        flag=db.insert(TableRetailer, null, values);
        Log.e("Kashyap",""+flag);
        values.clear();
        db.close();
        return (int)flag;


    }

    public ArrayList<ClassInventory> getAllInventory(){
        ArrayList<ClassInventory> invent =new ArrayList<>();
        String query="SELECT * FROM "+TableInventory+" ORDER BY id";
        SQLiteDatabase db=this.getWritableDatabase();
        while (db.inTransaction()){
        }
        db.beginTransaction();
        Cursor cursor = db.rawQuery(query,null);
        db.endTransaction();
        ClassInventory ci=null;
        if(cursor.moveToFirst()){
            do{
                ci=new ClassInventory();
                ci.id=(Integer.parseInt(cursor.getString(0)));
                ci.inventoryID=(Integer.parseInt(cursor.getString(0)));
                ci.company_name=cursor.getString(1);
                ci.product_name=cursor.getString(2);
                ci.barcode=cursor.getString(3);
                ci.category=cursor.getString(4);
                ci.product_detail=cursor.getString(5);
                ci.weight=cursor.getString(6);
                ci.MFG=cursor.getString(7);
                ci.month=cursor.getString(8);
                ci.price=cursor.getString(9);
                invent.add(ci);
            }while (cursor.moveToNext());
        }
        db.close();
        return invent;
    }

    public ArrayList<ClassRetailer> getAllRetailer(){
        ArrayList<ClassRetailer> retail =new ArrayList<>();
        String query="SELECT * FROM "+TableRetailer+" ORDER BY id";
        SQLiteDatabase db=this.getWritableDatabase();
        while (db.inTransaction()){
        }
        db.beginTransaction();
        Cursor cursor = db.rawQuery(query,null);
        db.endTransaction();
        ClassRetailer cr=null;
        if(cursor.moveToFirst()){
            do{
                cr=new ClassRetailer();
                cr.id=(Integer.parseInt(cursor.getString(0)));
                cr.retailerID=(Integer.parseInt(cursor.getString(0)));
                cr.retailer=cursor.getString(1);
                cr.lat=cursor.getString(2);
                cr.lng=cursor.getString(3);
                cr.phone=cursor.getString(4);
                cr.email=cursor.getString(5);
                cr.address=cursor.getString(6);
                cr.areas=cursor.getString(7);
                retail.add(cr);
            }while (cursor.moveToNext());
        }
        db.close();
        return retail;
    }

}
