package co.jlabs.add.activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.Selection;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.zxing.ResultPoint;
import com.journeyapps.barcodescanner.BarcodeCallback;
import com.journeyapps.barcodescanner.BarcodeResult;
import com.journeyapps.barcodescanner.CaptureManager;
import com.journeyapps.barcodescanner.CompoundBarcodeView;
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


import co.jlabs.add.AppController;
import co.jlabs.add.InstantAutoComplete;
import co.jlabs.add.R;
import co.jlabs.add.StaticCatelog;
import co.jlabs.add.db.ClassInventory;
import co.jlabs.add.db.LocalDB;
import co.jlabs.add.kmpautotextview.KMPAutoComplTextView;


public class Inventory extends AppCompatActivity implements View.OnClickListener
        , CompoundBarcodeView.TorchListener, DatePickerDialog.OnDateSetListener {

    private CaptureManager capture;
    private CompoundBarcodeView barcodeScannerView;
    private Button switchFlashlightButton;
    private KMPAutoComplTextView input_company;
    private KMPAutoComplTextView input_category;
    private KMPAutoComplTextView  input_scategory;
    private TextInputLayout input_layout_company;
    private EditText input_product;
    private TextInputLayout input_layout_pname;
    private LinearLayout co;
    private RelativeLayout cont;
    private SearchableSpinner conta;
    private EditText input_details;
    private TextInputLayout input_proc;
    private EditText input_weight;
    private TextInputLayout input_layout_weight;
    private LinearLayout contas;
    private EditText input_mfg;
    private TextInputLayout input_layout_mfg;
    private EditText input_price;
    private TextInputLayout input_layout_price;
    private LinearLayout contass;
    private TextView barcode;


    private CompoundBarcodeView barcodeView;
    private ImageView back;
    private Button done ,edit_barcode;
    String bar = "blank";
    String catag = "blank";
    private RelativeLayout main;
    private TextView msg;
    private LinearLayout options;
    private Button home;
    private Button view;
    Context context;
    private EditText input_month;
    private Button datepicker;
    String date="2001-01-01";
    String[] companies;
    String flags="";
    String cat_id="";
    String companyid="";
    String flag="true";
    String flg="true";
    List<String> responseList = new ArrayList<String>();
    List<String> company_idList = new ArrayList<String>();
    List<String> categoryList = new ArrayList<String>();
    List<String> scategoryList = new ArrayList<String>();
    List<String> sidcategoryList = new ArrayList<String>();
    List<JSONArray> subcategoryList = new ArrayList<JSONArray>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventory);
        context = this;
        flags=getIntent().getStringExtra("flags");
        input_company = (KMPAutoComplTextView ) findViewById(R.id.input_company);
        input_category = (KMPAutoComplTextView) findViewById(R.id.input_category);
        input_scategory = (KMPAutoComplTextView ) findViewById(R.id.input_scategory);
        getCompanyNames();
        getCategories();
        initView();
        // barcodeScannerView = (CompoundBarcodeView)findViewById(R.id.zxing_barcode_scanner);
        //  barcodeScannerView.setTorchListener(this);
        //  switchFlashlightButton = (Button)findViewById(R.id.switch_flashlight);
        //capture.initializeFromIntent(getIntent(), savedInstanceState);
        //capture.decode();
        barcodeView = (CompoundBarcodeView) findViewById(R.id.zxing_barcode_scanner);
        barcodeView.decodeContinuous(callback);


    }

    private void initView() {


        input_layout_company = (TextInputLayout) findViewById(R.id.input_layout_company);
        input_product = (EditText) findViewById(R.id.input_product);
        input_layout_pname = (TextInputLayout) findViewById(R.id.input_layout_pname);
        co = (LinearLayout) findViewById(R.id.co);
        cont = (RelativeLayout) findViewById(R.id.cont);
        conta = (SearchableSpinner) findViewById(R.id.conta);
        input_details = (EditText) findViewById(R.id.input_details);
        input_proc = (TextInputLayout) findViewById(R.id.input_proc);
        input_weight = (EditText) findViewById(R.id.input_weight);
        input_layout_weight = (TextInputLayout) findViewById(R.id.input_layout_weight);
        contas = (LinearLayout) findViewById(R.id.contas);
        input_mfg = (EditText) findViewById(R.id.input_mfg);
        input_layout_mfg = (TextInputLayout) findViewById(R.id.input_layout_mfg);
        input_price = (EditText) findViewById(R.id.input_price);
        input_layout_price = (TextInputLayout) findViewById(R.id.input_layout_price);
        contass = (LinearLayout) findViewById(R.id.contass);
        barcode = (TextView) findViewById(R.id.barcode);


//        if (!hasFlash()) {
//            switchFlashlightButton.setVisibility(View.GONE);
//        }


        conta.setTitle("Category");
        conta.setPositiveButton("OK");
        back = (ImageView) findViewById(R.id.back);
        back.setOnClickListener(this);
        done = (Button) findViewById(R.id.done);
        done.setEnabled(false);
        done.setOnClickListener(this);
        main = (RelativeLayout) findViewById(R.id.main);
        msg = (TextView) findViewById(R.id.msg);
        options = (LinearLayout) findViewById(R.id.options);
        home = (Button) findViewById(R.id.home);
        home.setOnClickListener(this);
        view = (Button) findViewById(R.id.view);
        view.setOnClickListener(this);
        input_month = (EditText) findViewById(R.id.input_month);
        datepicker = (Button) findViewById(R.id.datepicker);
        edit_barcode = (Button) findViewById(R.id.edit_barcode);
        datepicker.setOnClickListener(this);
        edit_barcode.setOnClickListener(this);
//        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, responseList);
//        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, responseList);
        try {
            Log.e("asasa","here");
            Log.e("asasa",""+responseList.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        // input_company.setDatas(responseList);
       // input_category.setDatas(categoryList);

        input_price.setText("₹");
        if(flags.contains("flags")){
            barcode.setVisibility(View.VISIBLE);
            edit_barcode.setVisibility(View.VISIBLE);
            cont.setVisibility(View.GONE);
            input_product.setText(getIntent().getStringExtra("product_name"));
            input_details.setText(getIntent().getStringExtra("detail"));
            barcode.setText(getIntent().getStringExtra("barcode"));
            input_weight.setText(getIntent().getStringExtra("weight"));
            //input_company.setText(getIntent().getStringExtra("category_name"));
            Log.e("paid",""+getIntent().getIntExtra("price",0));
            input_price.setText("₹"+getIntent().getIntExtra("price",0));
            input_month.setText(getIntent().getStringExtra("shelf_life"));
        }
        Selection.setSelection(input_price.getText(), input_price.getText().length());


        input_price.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // TODO Auto-generated method stub

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
                // TODO Auto-generated method stub

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(!s.toString().contains("₹")){
                    input_price.setText("₹");
                    Selection.setSelection(input_price.getText(), input_price.getText().length());

                }

            }
        });
        input_category.setOnPopupItemClickListener(new KMPAutoComplTextView.OnPopupItemClickListener() {
            @Override
            public void onPopupItemClick(CharSequence charSequence) {
                //input_scategory.setEnabled(true);
                Log.e("logan","123");
                done.setEnabled(true);
                submits();
            }
        });


    }

    private void submits() {
        // validate
        String company = input_company.getText().toString().trim();
        if (TextUtils.isEmpty(company)) {
            Toast.makeText(this, "Company empty", Toast.LENGTH_SHORT).show();
            input_category.setText("");
            done.setEnabled(false);
            return;
        }
        if(!responseList.contains(company)){
            Toast.makeText(this, "Company not listed", Toast.LENGTH_SHORT).show();
            input_category.setText("");
            done.setEnabled(false);
            return;
        }

        companyid=company_idList.get(responseList.indexOf(company));
        try {
            catag = conta.getSelectedItem().toString();
        } catch (Exception e) {
            e.printStackTrace();
        }

//        if (catag.equals("blank")) {
//            Toast.makeText(this, "Category", Toast.LENGTH_SHORT).show();
//            return;
//        }

//        Log.e("catag", catag);

        if (flags.equals("flags")&&flag.equals("false")) {
            if (bar.equals("blank")) {
                input_category.setText("");
                done.setEnabled(false);
                Toast.makeText(this, "Barcode", Toast.LENGTH_SHORT).show();
                return;
            }
        }else if(flag.equals("true")&&!flags.equals("flags")){
            if (bar.equals("blank")) {
                input_category.setText("");
                done.setEnabled(false);
                Toast.makeText(this, "Barcode", Toast.LENGTH_SHORT).show();
                return;
            }
        }


        String product = input_product.getText().toString().trim();
        if (TextUtils.isEmpty(product)) {
            input_category.setText("");
            done.setEnabled(false);
            Toast.makeText(this, "Product", Toast.LENGTH_SHORT).show();
            return;
        }
        String category = input_category.getText().toString().trim();
        if (TextUtils.isEmpty(category)) {
            input_category.setText("");
            done.setEnabled(false);
            Toast.makeText(this, "Category empty", Toast.LENGTH_SHORT).show();
            return;
        }


        //trace
        if(!categoryList.contains(category)){
            input_category.setText("");
            done.setEnabled(false);
            Toast.makeText(this, "Category not listed.", Toast.LENGTH_SHORT).show();
            return;
        }

                    //input_scategory.setEnabled(true);
                    Log.e("logan","123");
                try {
                    ProgressDialog pd = new ProgressDialog(Inventory.this);
                    pd.setMessage("loading");
                    pd.show();
                    JSONArray dats=subcategoryList.get(categoryList.indexOf(input_category.getText().toString().trim()));
                    Log.e("soq",":"+dats.toString());

                    for(int m=0;m<dats.length();m++){
                        JSONObject ob=dats.getJSONObject(m);
                        String subcat_name=ob.getString("subcat_name");
                        String cat_id=ob.getString("cat_id");

                        if(!scategoryList.contains(subcat_name)){
                            scategoryList.add(subcat_name);
                        }
                        if(!sidcategoryList.contains(cat_id)){
                            sidcategoryList.add(cat_id);
                        }
                    }
                   // ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_dropdown_item_1line, scategoryList);
                    input_scategory.setDatas(scategoryList);
                    pd.dismiss();
                    //input_scategory.setThreshold(1);

                } catch (Exception e) {
                    e.printStackTrace();
                }



        // TODO validate success, do something


    }

    private void submit() {
        String company = input_company.getText().toString().trim();
        if (TextUtils.isEmpty(company)) {
            Toast.makeText(this, "Company empty", Toast.LENGTH_SHORT).show();
            input_category.setText("");
            return;
        }
        if(!responseList.contains(company)){
            Toast.makeText(this, "Company not listed", Toast.LENGTH_SHORT).show();
            input_category.setText("");
            return;
        }


        try {
            catag = conta.getSelectedItem().toString();
        } catch (Exception e) {
            e.printStackTrace();
        }

//        if (catag.equals("blank")) {
//            Toast.makeText(this, "Category", Toast.LENGTH_SHORT).show();
//            return;
//        }

//        Log.e("catag", catag);

        if (flags.equals("flags")&&flag.equals("false")) {
            if (bar.equals("blank")) {
                input_category.setText("");
                Toast.makeText(this, "Barcode", Toast.LENGTH_SHORT).show();
                return;
            }
        }else if(flag.equals("true")&&!flags.equals("flags")){
            if (bar.equals("blank")) {
                input_category.setText("");
                Toast.makeText(this, "Barcode", Toast.LENGTH_SHORT).show();
                return;
            }
        }


        String product = input_product.getText().toString().trim();
        if (TextUtils.isEmpty(product)) {
            input_category.setText("");
            Toast.makeText(this, "Product", Toast.LENGTH_SHORT).show();
            return;
        }
        String category = input_category.getText().toString().trim();
        if (TextUtils.isEmpty(category)) {
            input_category.setText("");
            Toast.makeText(this, "Category empty", Toast.LENGTH_SHORT).show();
            return;
        }else{
            Log.e("ssss","ssss");
        }


        //trace
        if(!categoryList.contains(category)){
            input_category.setText("");
            Toast.makeText(this, "Category not listed.", Toast.LENGTH_SHORT).show();
            return;
        }
        String scategory = input_scategory.getText().toString().trim();
        if(!scategoryList.contains(scategory)){
            Toast.makeText(getApplicationContext(), "SubCategory not listed.", Toast.LENGTH_SHORT).show();
            return;
        }
        cat_id=sidcategoryList.get(scategoryList.indexOf(scategory));
        Log.e("cat_ids",cat_id);
        if (TextUtils.isEmpty(input_scategory.getText().toString().trim())) {
            Toast.makeText(getApplicationContext(), "SubCategory empty", Toast.LENGTH_SHORT).show();
            return;
        }







        String details = input_details.getText().toString().trim();
        if (TextUtils.isEmpty(details)) {
            Toast.makeText(this, "Product Details", Toast.LENGTH_SHORT).show();
            return;
        }

        String weight = input_weight.getText().toString().trim();
        if (TextUtils.isEmpty(weight)) {
            Toast.makeText(this, "Weight", Toast.LENGTH_SHORT).show();
            return;
        }



        String month = input_month.getText().toString().trim();
        Log.e("month",month);
        if (TextUtils.isEmpty(month)) {
            Toast.makeText(this, "Days", Toast.LENGTH_SHORT).show();
            return;
        }  if (!TextUtils.isDigitsOnly(month)) {
            Toast.makeText(this, "Days", Toast.LENGTH_SHORT).show();
            return;
        }

//        String mfg = input_mfg.getText().toString().trim();
//        if (TextUtils.isEmpty(mfg)) {
//            Toast.makeText(this, "MFG dd/mm/yy", Toast.LENGTH_SHORT).show();
//            return;
//        }

//        if (TextUtils.isEmpty(date)) {
//            Toast.makeText(this, "MFG", Toast.LENGTH_SHORT).show();
//            return;
//        }
        String price = input_price.getText().toString().trim().substring(1);
        Log.e("month",price);
        if (TextUtils.isEmpty(price)) {
            Toast.makeText(this, "Price", Toast.LENGTH_SHORT).show();
            return;
        }

//
//        ClassInventory inv = new ClassInventory();
//        inv.company_name = company;
//        inv.product_name = product;
//        inv.barcode = bar;
//        inv.category = category;
//        inv.product_detail = details;
//        inv.weight = weight;
//        inv.MFG = date;
//        inv.month = month;
//        inv.price = price;
//
//        LocalDB db = new LocalDB(this);
//        db.addInventory(inv);

        if(flags.contains("flags")){
            sendEdited();

        }else{
            sendInventoryData();
        }


    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e("here", "here");

        //capture.onDestroy();
    }


    private boolean hasFlash() {
        return getApplicationContext().getPackageManager()
                .hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH);
    }

    public void switchFlashlight(View view) {
        if (getString(R.string.turn_on_flashlight).equals(switchFlashlightButton.getText())) {
            barcodeScannerView.setTorchOn();
        } else {
            barcodeScannerView.setTorchOff();
        }
    }

    @Override
    public void onTorchOn() {
        switchFlashlightButton.setText(R.string.turn_off_flashlight);
    }

    @Override
    public void onTorchOff() {
        switchFlashlightButton.setText(R.string.turn_on_flashlight);
    }


    @Override
    protected void onResume() {
        super.onResume();
//        DatePickerDialog dpd = (DatePickerDialog) getFragmentManager().findFragmentByTag("Datepickerdialog");
//        if(dpd != null) dpd.setOnDateSetListener(this);
        barcodeView.resume();
    }

    @Override
    protected void onPause() {
        super.onPause();

        barcodeView.pause();
    }

    public void triggerScan(View view) {
        barcodeView.decodeSingle(callback);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return barcodeView.onKeyDown(keyCode, event) || super.onKeyDown(keyCode, event);
    }

    private BarcodeCallback callback = new BarcodeCallback() {
        @Override
        public void barcodeResult(BarcodeResult result) {

            Log.e("", "hey baby" + result);
            bar = result.toString();
            barcode.setText("Barcode: " + result);
            cont.setVisibility(View.GONE);
            barcode.setVisibility(View.VISIBLE);
            edit_barcode.setVisibility(View.GONE);
        }

        @Override
        public void possibleResultPoints(List<ResultPoint> resultPoints) {
        }
    };

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.done:
                submit();
                break;
            case R.id.back:
                Intent i = new Intent(this, MainActivity.class);
                startActivity(i);
                break;
            case R.id.home:
                Intent inte = new Intent(this, MainActivity.class);
                startActivity(inte);
                break;
            case R.id.view:
                Intent inten = new Intent(this, ViewActivityGlobal.class);
                inten.putExtra("from","inventory");
                startActivity(inten);
                break;
            case R.id.edit_barcode:
                cont.setVisibility(View.VISIBLE);
                barcode.setVisibility(View.GONE);
                edit_barcode.setVisibility(View.GONE);
                flag="false";
                break;
        }
    }

    public void showContent() {

        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
        main.setVisibility(View.GONE);
        msg.setVisibility(View.VISIBLE);
        options.setVisibility(View.VISIBLE);
        flg="false";
    }

    public void sendInventoryData() {
        String tag_json_obj = "json_obj_req";
        final ProgressDialog pDialog = new ProgressDialog(this);
        pDialog.setMessage("Saving inventory data...");
        pDialog.show();
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("api_key", StaticCatelog.getStringProperty(context, "api_key"));
            jsonObject.put("manager_id", StaticCatelog.getStringProperty(context, "manager_id"));
            jsonObject.put("user_type", "manager");
            jsonObject.put("company_id", companyid);
            jsonObject.put("company_name", input_company.getText().toString().trim());
            jsonObject.put("product_name", input_product.getText().toString().trim());
            jsonObject.put("barcode", bar);
            jsonObject.put("detail", input_details.getText().toString().trim());
            jsonObject.put("weight", input_weight.getText().toString().trim());
            jsonObject.put("price", (input_price.getText().toString().trim()).substring(1));
            //jsonObject.put("category_name", input_category.getText().toString().trim());
           // jsonObject.put("category_name", input_category.getText().toString().trim());
            jsonObject.put("category_id", cat_id);
            jsonObject.put("shelf_life", input_month.getText().toString().trim());
            Log.e("cat_idds",""+jsonObject.toString());

        } catch (JSONException je) {
            je.printStackTrace();
        }

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, StaticCatelog.sendInventoryData()+"_new", jsonObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.e("Response ", "" + response);
                        pDialog.dismiss();
                        try {
                            if (response.getBoolean("success")) {
                                StaticCatelog.setStringProperty(context, "inventory_num", response.getString("data"));
                                showContent();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        VolleyLog.e("TAG", "Error: ", error.getMessage());
                        pDialog.hide();
                    }
                });
        AppController.getInstance().addToRequestQueue(request, tag_json_obj);


    }



    public void sendEdited() {
        String tag_json_obj = "json_obj_req";
        final ProgressDialog pDialog = new ProgressDialog(this);
        pDialog.setMessage("Saving inventory data...");
        pDialog.show();
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("api_key", StaticCatelog.getStringProperty(context, "api_key"));
            jsonObject.put("manager_id", StaticCatelog.getStringProperty(context, "manager_id"));
            jsonObject.put("user_type", "manager");
            jsonObject.put("item_id", getIntent().getStringExtra("item_id"));
            jsonObject.put("company_id", companyid);
            jsonObject.put("company_name", input_company.getText().toString().trim());
            jsonObject.put("product_name", input_product.getText().toString().trim());
            if(flag.equals("true")){
                jsonObject.put("barcode", getIntent().getStringExtra("barcode"));
            }else{
                jsonObject.put("barcode", bar);
            }
            //jsonObject.put("category_name", input_category.getText().toString().trim());
            jsonObject.put("category_id", cat_id);
            jsonObject.put("detail", input_details.getText().toString().trim());
            jsonObject.put("weight", input_weight.getText().toString().trim());
            jsonObject.put("price", (input_price.getText().toString().trim()).substring(1));
            jsonObject.put("shelf_life", input_month.getText().toString().trim());
        } catch (JSONException je) {
            je.printStackTrace();
        }
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, StaticCatelog.sendEditedInventoryData(), jsonObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.e("Response ", "" + response);
                        pDialog.dismiss();
                        try {
                            if (response.getBoolean("success")) {
                                StaticCatelog.setStringProperty(context, "inventory_num", response.getString("data"));
                                showContent();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        VolleyLog.e("TAG", "Error: ", error.getMessage());
                        pDialog.hide();
                    }
                });
        AppController.getInstance().addToRequestQueue(request, tag_json_obj);
    }


    public void getCompanyNames() {
        String tag_json_obj = "json_obj_req";
        final ProgressDialog pDialog = new ProgressDialog(this);
        pDialog.setMessage("Please wait...");
        pDialog.show();
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("api_key", StaticCatelog.getStringProperty(context, "api_key"));
            jsonObject.put("manager_id", StaticCatelog.getStringProperty(context, "manager_id"));
            jsonObject.put("user_type", "manager");
        } catch (JSONException je) {
            je.printStackTrace();
        }
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, StaticCatelog.getCompanyName(), jsonObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.e("Response ", "" + response);
                        pDialog.dismiss();

                        try {
                            if (response.getBoolean("success")) {
                                JSONArray data=response.getJSONArray("data");
                                companies=new String [data.length()];
                                for(int i=0;i<data.length();i++){
                                    JSONObject ob=data.getJSONObject(i);
                                    String company_name=ob.getString("company_name");
                                    String company_id=ob.getString("company_id");
                                    if(!responseList.contains(company_name)){
                                        responseList.add(company_name);
                                        company_idList.add(company_id);

                                    }

                                }
                                input_company.setDatas(responseList);

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        VolleyLog.e("TAG", "Error: ", error.getMessage());
//                        pDialog.hide();
                    }
                });
        AppController.getInstance().addToRequestQueue(request, tag_json_obj);


    }
    public void getCategories() {
        String tag_json_obj = "json_obj_req";
        final ProgressDialog pDialog = new ProgressDialog(this);
        pDialog.setMessage("Please wait...");
        pDialog.show();
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("api_key", StaticCatelog.getStringProperty(context, "api_key"));
            jsonObject.put("manager_id", StaticCatelog.getStringProperty(context, "manager_id"));
            jsonObject.put("user_type", "manager");
        } catch (JSONException je) {
            je.printStackTrace();
        }
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, StaticCatelog.getCategory()+"_new", jsonObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.e("Response ", "" + response);
                        pDialog.dismiss();

                        try {
                            if (response.getBoolean("success")) {
                                JSONArray data=response.getJSONArray("data");
                                companies=new String [data.length()];

                                for(int i=0;i<data.length();i++){
                                    JSONObject ob=data.getJSONObject(i);
                                    String category_name=ob.getString("category");
                                    JSONArray subcategory=ob.getJSONArray("subcategory");
                                    if(!categoryList.contains(category_name)){
                                        categoryList.add(category_name);
                                        subcategoryList.add(subcategory);
                                    }
                                    Log.e("some data",""+companies[i]);
                                    Log.e("some data1",""+ob.getString("category"));
                                }

                                 input_category.setDatas(categoryList);

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        VolleyLog.e("TAG", "Error: ", error.getMessage());
//                        pDialog.hide();
                    }
                });
        AppController.getInstance().addToRequestQueue(request, tag_json_obj);


    }

//
//    private void submit() {
//        // validate
//        String month = input_month.getText().toString().trim();
//        if (TextUtils.isEmpty(month)) {
//            Toast.makeText(this, "Months", Toast.LENGTH_SHORT).show();
//            return;
//        }
//
//        // TODO validate success, do something
//
//
//    }

    public void showDatePicker(){
        Calendar now = Calendar.getInstance();
        DatePickerDialog dpd = DatePickerDialog.newInstance(
                Inventory.this,
                now.get(Calendar.YEAR),
                now.get(Calendar.MONTH),
                now.get(Calendar.DAY_OF_MONTH)
        );
        dpd.setTitle("MFG");
        dpd.showYearPickerFirst(true);
        dpd.show(getFragmentManager(), "Datepickerdialog");
    }
    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        //date = +dayOfMonth+"/"+(++monthOfYear)+"/"+year;
        date = "2001-01-01";
        datepicker.setText(date);
    }



    @Override
    public void onBackPressed()
    {
        if(flg.equals("false")){
            Intent intent =new Intent(this,MainActivity.class);
            startActivity(intent);
            finish();
        }else{
            Intent intent =new Intent(this,ViewActivityGlobal.class);
            intent.putExtra("from","inventory");
            startActivity(intent);
            finish();
        }


    }
}
