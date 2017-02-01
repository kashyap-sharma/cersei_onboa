package co.jlabs.add.Global;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;

import co.jlabs.add.AppController;
import co.jlabs.add.Image;
import co.jlabs.add.R;
import co.jlabs.add.StaticCatelog;
import co.jlabs.add.adapter.InventoryAdapter;
import co.jlabs.add.adapter.RetailerAdapter;
import co.jlabs.add.db.ClassRetailer;
import co.jlabs.add.db.LocalDB;

/**
 * Created by JLabs on 08/26/16.
 */
public class RetailerGlobalFragment extends Fragment {

    private String TAG = RetailerGlobalFragment.class.getSimpleName();
    private ArrayList<Image> images;

    private RecyclerView recyclerView;
    //    ArrayList<ClassInventory> invent;
    RetailerAdapter mAdapter;

    public RetailerGlobalFragment(){

    }
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        final View v =inflater.inflate(R.layout.ret_frag,container,false);
        initView(v);
        return v;
    }

    private void initView(View v) {
        recyclerView = (RecyclerView) v.findViewById(R.id.recyclerView);
        images = new ArrayList<>();

        int f=102;
        // invent=ldb.getAllInventory();
        mAdapter = new RetailerAdapter(getActivity().getApplicationContext(), images,f);

        RecyclerView.LayoutManager layoutManager =new GridLayoutManager(getActivity().getApplicationContext(),1);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);
        getOnboarded();

    }


    public void getOnboarded() {
        final ProgressDialog pDialog = new ProgressDialog(getContext());
        pDialog.setMessage("Please wait...");
        pDialog.show();
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("api_key", StaticCatelog.getStringProperty(getContext(), "api_key"));
            jsonObject.put("manager_id", StaticCatelog.getStringProperty(getContext(), "manager_id"));
            jsonObject.put("user_type", "manager");
        } catch (JSONException je) {
            je.printStackTrace();
        }

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, StaticCatelog.getRetailorData(), jsonObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.e("Response ", "" + response);
                        pDialog.dismiss();
                        images.clear();

                        try {
                            if (response.getBoolean("success")) {
                                JSONArray data=response.getJSONArray("data");

                                for(int i=0;i<data.length();i++){
                                    JSONObject ob=data.getJSONObject(i);
                                    Image image=new Image();
                                    image.setRetailer_id(ob.getString("retailer_id"));
                                    image.setRName(ob.getString("name"));
                                    image.setEmail(ob.getString("email"));
                                    image.setMin_order(ob.getInt("min_order"));
                                    image.setContact(ob.getString("contact"));
                                    image.setAddby2(ob.getString("add_by"));
                                    image.setAddress(ob.getString("address"));
                                    JSONObject loc=ob.getJSONObject("location");
                                    image.setLat(loc.getDouble("lat"));
                                    image.setLon(loc.getDouble("lon"));
                                    image.setAreas(ob.getJSONArray("areas"));

                                   // image.setAreas(Arrays.toString(animalsArray));
                                    images.add(image);
                                }


                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        mAdapter.notifyDataSetChanged();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        VolleyLog.e("TAG", "Error: ", error.getMessage());
                        pDialog.hide();
                    }
                });
        AppController.getInstance().addToRequestQueue(request);


    }

}
