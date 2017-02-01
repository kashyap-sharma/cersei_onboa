package co.jlabs.add.adapter;


import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import co.jlabs.add.Image;
import co.jlabs.add.R;
import co.jlabs.add.activities.Inventory;
import co.jlabs.add.activities.Retailer;
import co.jlabs.add.db.ClassRetailer;


/**
 * Created by JLabs on 08/26/16.
 */


public class RetailerAdapter extends RecyclerView.Adapter<RetailerAdapter.MyViewHolder> implements View.OnClickListener{

    private Context mContext;
    List<ClassRetailer> retails;
    private List<Image> images;
    int u=0;


    public class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView retailer;
        public TextView contact;
        public TextView lat_long;
        public TextView email;
        public TextView address;
        public LinearLayout ll;
        public TextView areas;
        public TextView addedby;
        public TextView retailerID;
        public TextView min_order;
        public Button edit;
        public RelativeLayout parent;

        public MyViewHolder(View view) {
            super(view);
            retailer = (TextView) view.findViewById(R.id.retailer);
            contact = (TextView) view.findViewById(R.id.contact);
            lat_long = (TextView) view.findViewById(R.id.lat_long);
            min_order = (TextView) view.findViewById(R.id.min_order);
            email = (TextView) view.findViewById(R.id.email);
            address = (TextView) view.findViewById(R.id.address);
            ll = (LinearLayout) view.findViewById(R.id.ll);
            areas = (TextView) view.findViewById(R.id.areas);
            addedby = (TextView) view.findViewById(R.id.addedby);
            retailerID = (TextView) view.findViewById(R.id.reatilerID);
            edit = (Button) view.findViewById(R.id.edit);
            parent = (RelativeLayout) view.findViewById(R.id.parent);
        }
    }


    public RetailerAdapter(Context context, ArrayList<ClassRetailer> retail) {
        mContext = context;
        this.retails = retail;
    }
    public RetailerAdapter(Context context, List<Image> invent, int f) {
        mContext = context;
        this.images = invent;
        this.u = f;

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_retailer, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {

        if(u==102){
            holder.parent.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    holder.edit.setVisibility(View.VISIBLE);
                    Log.e("Mhere","mhere");
                    return false;
                }
            });
            holder.edit.setOnClickListener(this);
            holder.edit.setTag(position);
            Image image = images.get(position);
           // holder.product.setText("Product: "+image.getProduct_name());
            holder.retailer.setText("Retailer: " +image.getRName());
            holder.contact.setText("Contact: " +image.getContact());
            holder.lat_long.setText("GPS: " + image.getLat()+", "+image.getLon());
            holder.email.setText("Email: " + image.getEmail());
            holder.min_order.setText("Min Order: ₹" + image.getMin_order());
            holder.address.setText("Address: " + image.getAddress());
            String locality="Not Available";
            String sublocality="Not Available";
            String ares="";
            int shipping_charges;
            JSONArray ja=image.getAreas();
            ArrayList<String> scripts = new ArrayList<String>();
            for (int i=0;i<ja.length();i++){
                try {
                    JSONObject jsonObject= ja.getJSONObject(i);
                    locality=jsonObject.getString("locality");
                    shipping_charges=jsonObject.getInt("shipping_charges");
                    sublocality=jsonObject.getString("sub-locality");
                    JSONArray areas=jsonObject.getJSONArray("areas");
                    String[] animalsArray=new String[areas.length()];
                    for(int j=0;j<areas.length();j++){
                        animalsArray[j]=areas.getString(j);
                    }
                    ares= Arrays.toString(animalsArray);
                    TextView tv= new TextView(mContext);


                   tv.setText(i+1+": Locality: "+locality+"\n Sublocality: "+sublocality+"\n Areas: "+ares+"\n Delivery Charge: ₹"+shipping_charges);
                    holder.ll.addView(tv);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            holder.areas.setText(scripts.toString());


            holder.retailerID.setText("RetailerID: " + image.getRetailer_id());
            holder.addedby.setText("Added by: " + image.getAddby2());
            Log.e("nigga",""+image.getAddby2());
            Log.e("nigga","sdsds");
        }
        else{
            ClassRetailer ret = retails.get(position);
            holder.retailer.setText("Retailer: " + ret.retailer);
            holder.contact.setText("Contact: " + ret.phone);
            holder.lat_long.setText("GPS: " + ret.lat+", "+ret.lng);
            holder.email.setText("Email: " + ret.email);
            holder.address.setText("Address: " + ret.address);
            holder.areas.setText("Areas: " + ret.areas);
            holder.retailerID.setVisibility(View.GONE);
            holder.addedby.setVisibility(View.GONE);
            Log.e("nigga", "" + ret.retailer);
            Log.e("nigga", "dsdsds");
        }


    }

    @Override
    public int getItemCount() {
        if (u==102){
            return images.size();
        }else{
            return retails.size();
        }
    }


    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public void onClick(View v) {
        Image image = images.get(Integer.parseInt(v.getTag().toString()));
        Log.e("sometag",""+image.getWeight());
        Intent inga=new Intent(v.getContext(), Retailer.class);
        inga.putExtra("retailer_id",image.getRetailer_id());
        inga.putExtra("name",image.getRName());
        inga.putExtra("contact",image.getContact());
        inga.putExtra("email",image.getEmail());
        inga.putExtra("min_ord",image.getMin_order());
        inga.putExtra("address",image.getAddress());
        inga.putExtra("latt",image.getLat());
        inga.putExtra("lonn",image.getLon());
        inga.putExtra("areas",image.getAreas().toString());
//        String steam= image.getAreas().substring(0,image.getAreas().length()-1);
//        Log.e("tag1",""+steam);
//        steam= steam.substring(1);
//        Log.e("tag",""+steam);
//        inga.putExtra("areas",steam);

        inga.putExtra("flags","flags");
        v.getContext().startActivity(inga);

    }
}
