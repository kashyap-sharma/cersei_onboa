package co.jlabs.add.activities;

import android.Manifest;
import android.app.Activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;

import android.test.mock.MockPackageManager;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
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
import com.mxn.soul.flowingdrawer_core.LeftDrawerLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import co.jlabs.add.AppController;
import co.jlabs.add.GPSTracker;
import co.jlabs.add.R;
import co.jlabs.add.StaticCatelog;


public class Retailer extends Activity implements View.OnClickListener {
    private EditText input_retailer;
    private TextInputLayout input_layout_retailer;

    private TextInputLayout input_min;
    private EditText input_min_cahrge;
    private EditText input_phone;
    private TextInputLayout input_layout_phone;
    private EditText input_email;
    private TextInputLayout input_layout_email;
    private LinearLayout con, ll;
    private EditText input_address;
    private TextInputLayout input_layout_Operational;
    private static final int REQUEST_CODE_PERMISSION = 2;
    String[] mPermission = {Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.CAMERA,
            Manifest.permission.INTERNET};
    private ImageView back;
    private Button done;
    private RelativeLayout main;
    private TextView msg;
    private Button home;
    private Button view;
    private LinearLayout options;
    private LeftDrawerLayout id_drawerlayout;
    JSONArray ja;
    Context context;
    double lats = 0.1;
    double lngs = 0.1;
    int width;
    String flags = "";
      String flag = "false";
    GPSTracker gps;
    View child;
    String flg = "true";
//    private EditText input_sublocality;
//    private EditText input_arealocality;
    EditText input_locality,input_sublocality,input_arealocality;
    private Button add_new;
//    private TextInputLayout input_layout_locality;
//    private TextInputLayout input_layout_sublocality;
//    private TextInputLayout input_layout_arealocality;
        int _intMyLineCount;
    private List<EditText> editTextList = new ArrayList<EditText>();
    private List<EditText> editTextList1 = new ArrayList<EditText>();
    private List<EditText> editTextList2 = new ArrayList<EditText>();
    private List<EditText> editTextList3 = new ArrayList<EditText>();
    private List<LinearLayout> linearlayoutList=new ArrayList<LinearLayout>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retailer);
        context = this;
        flags = getIntent().getStringExtra("flags");

        initView();

        //initPermissions();


    }


    private void initView() {

        ll=(LinearLayout)findViewById(R.id.so);
        input_retailer = (EditText) findViewById(R.id.input_retailer);
        input_layout_retailer = (TextInputLayout) findViewById(R.id.input_layout_retailer);
        input_min_cahrge = (EditText) findViewById(R.id.input_min_cahrge);
        input_min = (TextInputLayout) findViewById(R.id.input_min);
        input_phone = (EditText) findViewById(R.id.input_phone);
        input_layout_phone = (TextInputLayout) findViewById(R.id.input_layout_phone);
        input_email = (EditText) findViewById(R.id.input_email);
        input_layout_email = (TextInputLayout) findViewById(R.id.input_layout_email);
        con = (LinearLayout) findViewById(R.id.con);
        Display display = ((WindowManager) getApplicationContext().getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
        width = display.getWidth();
        input_address = (EditText) findViewById(R.id.input_address);
        back = (ImageView) findViewById(R.id.back);
        back.setOnClickListener(this);
        done = (Button) findViewById(R.id.done);
        done.setOnClickListener(this);
        main = (RelativeLayout) findViewById(R.id.main);
        msg = (TextView) findViewById(R.id.msg);
        home = (Button) findViewById(R.id.home);
        home.setOnClickListener(this);
        view = (Button) findViewById(R.id.view);
        view.setOnClickListener(this);
        options = (LinearLayout) findViewById(R.id.options);
        gps = new GPSTracker(Retailer.this);
        if (gps.canGetLocation()) {

            lats = gps.getLatitude();
            lngs = gps.getLongitude();
            Log.e("m here",""+lats+lngs);
            // \n is for new line
        }else{
            gps.showSettingsAlert();
        }


        if (flags.contains("flags")) {
            input_retailer.setText(getIntent().getStringExtra("name"));
            input_phone.setText(getIntent().getStringExtra("contact"));
            input_email.setText(getIntent().getStringExtra("email"));
            input_address.setText(getIntent().getStringExtra("address"));
            input_min_cahrge.setText(""+getIntent().getIntExtra("min_ord",0));
            String jsonA = getIntent().getStringExtra("areas");
            String ares="";
            try {
                JSONArray array = new JSONArray(jsonA);
                for (int i=0;i<array.length();i++){
                    ll.addView(linearlayout(_intMyLineCount));
                    _intMyLineCount++;


                        try {
                            JSONObject jsonObject= array.getJSONObject(i);
                            editTextList.get(i).setText(jsonObject.getString("locality"));
                            editTextList1.get(i).setText(""+jsonObject.getInt("shipping_charges"));
                            editTextList2.get(i).setText(jsonObject.getString("sub-locality"));
                            JSONArray areas=jsonObject.getJSONArray("areas");
                            String[] animalsArray=new String[areas.length()];
                            for(int j0=0;j0<areas.length();j0++){
                                animalsArray[j0]=areas.getString(j0);
                            }
                            ares= Arrays.toString(animalsArray);
                            String steam= ares.substring(0,ares.length()-1);
                            Log.e("tag1",""+steam);
                            steam= steam.substring(1);
                            Log.e("tag",""+steam);

                            editTextList3.get(i).setText(steam);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                }
                //System.out.println(array.toString(2));
            } catch (JSONException e) {
                e.printStackTrace();
            }
           // input_locality.setText(getIntent().getStringExtra("areas"));
            //input_company.setText(getIntent().getStringExtra("category_name"));
            Log.e("paid", "" + getIntent().getStringExtra("areas"));

        }

//        input_sublocality = (EditText) findViewById(R.id.input_sublocality);

//        input_arealocality = (EditText) findViewById(R.id.input_arealocality);
        add_new = (Button) findViewById(R.id.add_new);
        add_new.setOnClickListener(this);

    }




    private void submit() {


        String retailer = input_retailer.getText().toString().trim();
        if (TextUtils.isEmpty(retailer)) {
            Toast.makeText(getApplicationContext(), "Retailer", Toast.LENGTH_SHORT).show();
            return;
        }


        String phone = input_phone.getText().toString().trim();
        if (TextUtils.isEmpty(phone)) {
            Toast.makeText(getApplicationContext(), "Input contact no.", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!TextUtils.isDigitsOnly(phone)) {
            Toast.makeText(getApplicationContext(), "Digits only", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.getTrimmedLength(phone) < 10) {
            Toast.makeText(getApplicationContext(), "Invalid Phone", Toast.LENGTH_SHORT).show();
            return;
        }

        String min = input_min_cahrge.getText().toString().trim();
        Log.e("sor" + TextUtils.getTrimmedLength(min), "sor");

        if ((TextUtils.isEmpty(min))) {
            Toast.makeText(getApplicationContext(), "Min Delivery", Toast.LENGTH_SHORT).show();
            return;
        }

        String address = input_address.getText().toString().trim();
        if (TextUtils.isEmpty(address)) {
            Toast.makeText(getApplicationContext(), "Address", Toast.LENGTH_SHORT).show();
            return;
        }
        else{
            flag="true";
        }


        Log.e("m here1",""+lats+lngs);
        // check if GPS enabled



        // validate
         ja=new JSONArray();

        for (int i = 0; i < editTextList.size(); i++) {
            JSONObject jo=new JSONObject();
//            System.out.println("1"+editTextList.get(i).getText().toString());
//            System.out.println("2"+editTextList1.get(i).getText().toString());
//            System.out.println("3"+editTextList2.get(i).getText().toString());
//            System.out.println("4"+editTextList3.get(i).getText().toString());
            int money=Integer.parseInt(editTextList1.get(i).getText().toString());
            String animals = editTextList3.get(i).getText().toString();
            JSONArray jsonArray = new JSONArray();
            String[] animalsArray = animals.split(",");
            for (int m = 0; m < animalsArray.length; m++) {

                Log.e("some" + m, ":" + animalsArray[m]);
                jsonArray.put(animalsArray[m]);

            }


            try {
                jo.put("locality",editTextList.get(i).getText().toString());
                jo.put("sub-locality",editTextList2.get(i).getText().toString());
                jo.put("areas", jsonArray);
                jo.put("shipping_charges",money);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            ja.put(jo);
        }

        Log.e("Custome",""+ja.toString());





        if (flags.contains("flags")) {
            sendEdited();

        } else {
            sendRetailerData();
        }


        // TODO validate success, do something


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
            jsonObject.put("retailer_id", getIntent().getStringExtra("retailer_id"));
            jsonObject.put("name", input_retailer.getText().toString().trim());
            jsonObject.put("contact", input_phone.getText().toString().trim());
            jsonObject.put("min_order", Integer.parseInt(input_min_cahrge.getText().toString().trim()));
            JSONObject location = new JSONObject();
            if (flags.equals("flags")) {

                location.put("lat", getIntent().getDoubleExtra("latt", 0.0));
                location.put("lon", getIntent().getDoubleExtra("lonn", 0.0));

            } else {
                location.put("lat", (double)lats);
                location.put("lon", (double)lngs);
            }

            jsonObject.put("location", location);
            jsonObject.put("email", input_email.getText().toString().trim());

//            String animals = input_locality.getText().toString().trim();
//            JSONArray jsonArray = new JSONArray();
//            String[] animalsArray = animals.split(",");
//            for (int i = 0; i < animalsArray.length; i++) {
//
//                Log.e("some" + i, ":" + animalsArray[i]);
//                jsonArray.put(animalsArray[i]);
//                Log.e("testhere" + i, ":" + animalsArray[i]);
//
//            }
            jsonObject.put("areas", ja);
            jsonObject.put("address", input_address.getText().toString().trim());
        } catch (JSONException je) {
            je.printStackTrace();
        }
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, StaticCatelog.sendEditedRetailerData(), jsonObject,
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
                            Log.e("error","error");
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

    public void sendRetailerData() {
        String tag_json_obj = "json_obj_req";
        final ProgressDialog pDialog = new ProgressDialog(this);
        pDialog.setMessage("Saving retailer data...");
        pDialog.show();
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("api_key", StaticCatelog.getStringProperty(context, "api_key"));
            jsonObject.put("manager_id", StaticCatelog.getStringProperty(context, "manager_id"));
            jsonObject.put("user_type", "manager");
            jsonObject.put("name", input_retailer.getText().toString().trim());
            JSONObject location = new JSONObject();
            location.put("lat", (double)lats);
            location.put("lon", (double)lngs);
            jsonObject.put("location", location);
            jsonObject.put("contact", input_phone.getText().toString().trim());
            jsonObject.put("email", input_email.getText().toString().trim());
            jsonObject.put("address", input_address.getText().toString().trim());

            jsonObject.put("areas", ja);
            jsonObject.put("min_order", Integer.parseInt(input_min_cahrge.getText().toString().trim()));


        } catch (JSONException je) {
            je.printStackTrace();
        }
        Log.e("Response321 ", "" + jsonObject.toString());

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, StaticCatelog.sendRetailerData(), jsonObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.e("Response ", "" + response);
                        pDialog.dismiss();
                        try {
                            if (response.getBoolean("success")) {
                                StaticCatelog.setStringProperty(context, "retailer_num", response.getString("data"));
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

    @Override
    protected void onResume() {
        super.onResume();

    }

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
                inten.putExtra("from", "retailer");
                startActivity(inten);
                break;
            case R.id.add_new:
                String retailer = input_retailer.getText().toString().trim();
                String phone = input_phone.getText().toString().trim();
                String min = input_min_cahrge.getText().toString().trim();
                String address = input_address.getText().toString().trim();
                if (TextUtils.isEmpty(address)||TextUtils.isEmpty(retailer)||TextUtils.isEmpty(phone)||TextUtils.isEmpty(min)) {
                    Toast.makeText(getApplicationContext(), "Input Retailer Details", Toast.LENGTH_SHORT).show();
                } else {
                    ll.addView(linearlayout(_intMyLineCount));
                    _intMyLineCount++;
                }
                break;
        }
    }

    @Override
    public void onBackPressed() {
        if (flg.equals("false")) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            // finish();
        } else {
            Intent intent = new Intent(this, ViewActivityGlobal.class);
            intent.putExtra("from", "retailer");
            startActivity(intent);
            // finish();
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
        flg = "false";
    }

    private LinearLayout linearlayout(int _intID)
    {
        LinearLayout LLMain=new LinearLayout(this);
        LLMain.setId(_intID);
        //LLMain.setBackgroundColor(getResources().getColor(android.R.color.transparent));
        LLMain.addView(hori(_intID));
        LLMain.addView(til2(_intID));
        LLMain.addView(til3(_intID));
        LLMain.setOrientation(LinearLayout.VERTICAL);
        linearlayoutList.add(LLMain);
        return LLMain;

    }


    private LinearLayout hori(int _intID) {
        LinearLayout horiz = new LinearLayout(this);
        horiz.setId(_intID);
       // horiz.setBackgroundColor(getResources().getColor(android.R.color.transparent));
        horiz.addView(til(_intID));
        horiz.addView(til1(_intID));
        horiz.setOrientation(LinearLayout.HORIZONTAL);
        horiz.setWeightSum(2);
        horiz.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT,2f));
        return horiz;
    }
    private TextInputLayout til(int _intID) {
        TextInputLayout til = new TextInputLayout(this);
        til.setId(_intID);
        final LinearLayout.LayoutParams lparams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        lparams.weight=1.0f;
        //setTheme(R.style.TextLabel);
        til.setLayoutParams(lparams);
        til.setPadding(20,0,10,0);
       // til.setBackgroundColor(getResources().getColor(android.R.color.transparent));
        til.addView(locality(_intID));
        return til;
    }
    private TextInputLayout til1(int _intID) {
        TextInputLayout til1 = new TextInputLayout(this);
        til1.setId(_intID);
        final LinearLayout.LayoutParams lparams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        lparams.weight=1.0f;
        //setTheme(R.style.TextLabel);
        til1.setLayoutParams(lparams);
        til1.setPadding(10,0,20,0);
       // til1.setBackgroundColor(getResources().getColor(android.R.color.transparent));
        til1.addView(delcharge(_intID));
        return til1;
    }



    private EditText locality(int _intID) {
        EditText locality = new EditText(this);
        locality.setId(_intID);
        locality.setHint("Locality");
        locality.setWidth(180);
        locality.setInputType(InputType.TYPE_TEXT_FLAG_CAP_WORDS);
        editTextList.add(locality);
        return locality;
    }
    private EditText delcharge(int _intID) {
        EditText delcharge = new EditText(this);
        delcharge.setId(_intID);
        delcharge.setHint("Delivery Charge");
        delcharge.setWidth(180);
        delcharge.setInputType(InputType.TYPE_CLASS_NUMBER);
        editTextList1.add(delcharge);
        return delcharge;
    }

    private TextInputLayout til2(int _intID) {
        TextInputLayout tilz = new TextInputLayout(this);
        tilz.setId(_intID);
        final TextInputLayout.LayoutParams lparams = new TextInputLayout.LayoutParams(TextInputLayout.LayoutParams.MATCH_PARENT, TextInputLayout.LayoutParams.WRAP_CONTENT);
        tilz.setLayoutParams(lparams);
       // setTheme(R.style.TextLabel);
        tilz.setPadding(20,0,20,0);

       // tilz.setBackgroundColor(getResources().getColor(android.R.color.transparent));
        tilz.addView(sublocality(_intID));
        return tilz;
    }
    private TextInputLayout til3(int _intID) {
        TextInputLayout tilz = new TextInputLayout(this);
        tilz.setId(_intID);
        final TextInputLayout.LayoutParams lparams = new TextInputLayout.LayoutParams(TextInputLayout.LayoutParams.MATCH_PARENT, TextInputLayout.LayoutParams.WRAP_CONTENT);
        tilz.setLayoutParams(lparams);
        //setTheme(R.style.TextLabel);
        tilz.setLayoutParams(lparams);
        tilz.setPadding(20,0,20,0);
       // tilz.setBackgroundColor(getResources().getColor(android.R.color.transparent));
        tilz.addView(area(_intID));
        return tilz;
    }
    private EditText sublocality(int _intID) {
        EditText sublocality = new EditText(this);
        sublocality.setId(_intID);
        sublocality.setHint("Sublocality");
        sublocality.setWidth(180);
        sublocality.setInputType(InputType.TYPE_TEXT_FLAG_CAP_WORDS);
        sublocality.setPadding(20,20,20,20);
       // sublocality.setBackgroundColor(getResources().getColor(android.R.color.transparent));
        editTextList2.add(sublocality);
        return sublocality;
    }
    private EditText area(int _intID) {
        EditText area = new EditText(this);
        area.setId(_intID);
        area.setInputType(InputType.TYPE_TEXT_FLAG_CAP_WORDS);
        area.setHint("Areas separated by comma");
        area.setWidth(180);
        area.setPadding(20,20,20,20);
        editTextList3.add(area);
        return area;
    }


}
