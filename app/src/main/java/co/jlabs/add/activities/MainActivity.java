package co.jlabs.add.activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import co.jlabs.add.AppController;
import co.jlabs.add.R;
import co.jlabs.add.StaticCatelog;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button invent;
    private Button retail;
    private Button view_global;
    private EditText edit1;
    private EditText edit2;
    private Button signin;
    String manager_name,password;
    Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context=this;
        initView();
    }

    private void initView() {
        invent = (Button) findViewById(R.id.invent);
        retail = (Button) findViewById(R.id.retail);
        invent.setOnClickListener(this);
        retail.setOnClickListener(this);

        view_global = (Button) findViewById(R.id.view_global);

        view_global.setOnClickListener(this);
        edit1 = (EditText) findViewById(R.id.edit1);
        edit2 = (EditText) findViewById(R.id.edit2);
        signin = (Button) findViewById(R.id.signin);
        signin.setOnClickListener(this);
        try {
            if(!StaticCatelog.getStringProperty(context,"manager_id").isEmpty()){
                showContent();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.invent:
                Intent i = new Intent(this, Inventory.class);
                i.putExtra("flags","flame");
                startActivity(i);
                break;
            case R.id.retail:

                Intent in = new Intent(this, Retailer.class);
                in.putExtra("flags","flame");
                startActivity(in);

                break;
            case R.id.view_global:
                Intent intens = new Intent(this, ViewActivityGlobal.class);
                intens.putExtra("from","none");
                startActivity(intens);
                break;
            case R.id.signin:
                manager_name = edit1.getText().toString().trim();
                if (TextUtils.isEmpty(manager_name)) {
                    Toast.makeText(this, "Username", Toast.LENGTH_SHORT).show();
                    return;
                }
                password = edit2.getText().toString().trim();
                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(this, "Password", Toast.LENGTH_SHORT).show();
                    return;
                }
                View view = this.getCurrentFocus();
                if (view != null) {
                    InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                }
                managerLogin();
                break;
        }
    }

    public static final String md5(final String s) {
        final String MD5 = "MD5";
        try {
            // Create MD5 Hash
            MessageDigest digest = java.security.MessageDigest.getInstance(MD5);
            digest.update(s.getBytes());
            byte messageDigest[] = digest.digest();

            // Create Hex String
            StringBuilder hexString = new StringBuilder();
            for (byte aMessageDigest : messageDigest) {
                String h = Integer.toHexString(0xFF & aMessageDigest);
                while (h.length() < 2)
                    h = "0" + h;
                hexString.append(h);
            }
            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }

    private void managerLogin(){
        String tag_json_obj="json_obj_req";
        StaticCatelog.setStringProperty(context,"manager_name",manager_name);
        String encryptedPassword=md5(password);
        final ProgressDialog pDialog = new ProgressDialog(this);
        pDialog.setMessage("Checking credentials...");
        pDialog.show();

        JSONObject jsonObject=new JSONObject();
        try{
            jsonObject.put("username",manager_name);
            jsonObject.put("password",encryptedPassword);
            jsonObject.put("user_type","manager");
        }catch (JSONException je){
            je.printStackTrace();
        }

        JsonObjectRequest request=new JsonObjectRequest(Request.Method.POST, StaticCatelog.getLogin(), jsonObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.e("Response ",""+response);
                        pDialog.dismiss();
                        try {
                            if(response.getBoolean("success")){
                                JSONObject data=response.getJSONObject("data");
                                StaticCatelog.setStringProperty(context,"api_key",data.getString("api_key"));
                                StaticCatelog.setStringProperty(context,"address",data.getString("address"));
                                StaticCatelog.setStringProperty(context,"manager_id",data.getString("manager_id"));
                                StaticCatelog.setStringProperty(context,"name",data.getString("name"));
                                showContent();
                            }else {edit1.setError("Incorrect Input");
                                  edit2.setError("Incorrect Input");}

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

    private void showContent(){


        edit1.setVisibility(View.GONE);
        edit2.setVisibility(View.GONE);
        signin.setVisibility(View.GONE);
        invent.setVisibility(View.VISIBLE);
        retail.setVisibility(View.VISIBLE);
        view_global.setVisibility(View.VISIBLE);
    }
    @Override
    public void onBackPressed()
    {
        moveTaskToBack(true);
    }
}
