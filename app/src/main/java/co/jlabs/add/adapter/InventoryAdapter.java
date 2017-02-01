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

import java.util.ArrayList;
import java.util.List;

import co.jlabs.add.Image;
import co.jlabs.add.R;
import co.jlabs.add.activities.Inventory;
import co.jlabs.add.db.ClassInventory;


/**
 * Created by JLabs on 08/26/16.
 */


public class InventoryAdapter extends RecyclerView.Adapter<InventoryAdapter.MyViewHolder> implements View.OnClickListener{

    private Context mContext;
    List<ClassInventory> invents;
    private List<Image> images;
    int u=0;

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView product;
        public TextView company;
        public TextView details;
        public TextView catagory;
        public TextView price;
        public TextView mfg;
        public TextView barcode;
        public TextView month;
        public TextView addedby;
        public TextView weight;
        public TextView item_id;
        public Button edit;
        public RelativeLayout parent;
        public MyViewHolder(View view) {
            super(view);
            product = (TextView) view.findViewById(R.id.product);
            company = (TextView) view.findViewById(R.id.company);
            month = (TextView) view.findViewById(R.id.month);
            details = (TextView) view.findViewById(R.id.details);
            catagory = (TextView) view.findViewById(R.id.catagory);
            price = (TextView) view.findViewById(R.id.price);
            mfg = (TextView) view.findViewById(R.id.mfg);
            barcode = (TextView) view.findViewById(R.id.barcode);
            addedby = (TextView) view.findViewById(R.id.addedby);
            weight = (TextView) view.findViewById(R.id.weight);
            item_id = (TextView) view.findViewById(R.id.item_id);
            edit = (Button) view.findViewById(R.id.edit);
            parent = (RelativeLayout) view.findViewById(R.id.parent);
        }
    }



    public InventoryAdapter(Context context, ArrayList<ClassInventory> invent) {
        mContext = context;
        this.invents = invent;

    }
    public InventoryAdapter(Context context, List<Image> invent,int f) {
        mContext = context;
        this.images = invent;
        this.u = f;

    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_inventory, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {

        if(u==101){
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
            holder.product.setText("Product: "+image.getProduct_name());
            holder.company.setText("Company: "+image.getCompany_name());
            holder.details.setText("Details: "+image.getDetail());
            holder.catagory.setText("Category: "+image.getCategory());
            holder.price.setText("Price: "+image.getPrice());
            holder.item_id.setText("ItemID: "+image.getItem_id());
            holder.month.setText("Days: "+image.getShelfLife());
            holder.barcode.setText("Barcode: "+image.getBarcode());
            holder.weight.setText("Weight/Quantity: "+image.getWeight());
            holder.addedby.setText("Added by: "+image.getAdd_by());
            Log.e("nigga",""+image.getProduct_name());
            Log.e("nigga","dadsa");
        }else{
            ClassInventory inven = invents.get(position);
            holder.product.setText("Product: "+inven.product_name);
            holder.company.setText("Company: "+inven.company_name);
            holder.details.setText("Details: "+inven.product_detail);
            holder.catagory.setText("Category: "+inven.category);
            holder.price.setText("Price: "+inven.price);
            holder.item_id.setVisibility(View.GONE);
           // holder.mfg.setText("MFG: "+inven.MFG);
            holder.month.setText("Days: "+inven.month);
            holder.barcode.setText("Barcode: "+inven.barcode);
            holder.weight.setText("Weight: "+inven.weight);
            holder.addedby.setVisibility(View.GONE);
            Log.e("nigga",""+inven.category);
            Log.e("nigga","dadsa");
        }

    }

    @Override
    public int getItemCount(){
        if (u==101){
            return images.size();
        }else{
            return invents.size();
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
        Intent inga=new Intent(v.getContext(), Inventory.class);

        inga.putExtra("company_name",image.getCompany_name());
        inga.putExtra("company_id",image.getCompany_id());
        inga.putExtra("product_name",image.getProduct_name());
        inga.putExtra("item_id",image.getItem_id());
        inga.putExtra("detail",image.getDetail());
        inga.putExtra("barcode",image.getBarcode());
        inga.putExtra("weight",image.getWeight());
        inga.putExtra("category_id",image.getCategoryID());
        inga.putExtra("category_name",image.getCategory());
        inga.putExtra("price",image.getPrice());
        inga.putExtra("shelf_life",image.getShelfLife());
        inga.putExtra("flags","flags");
        v.getContext().startActivity(inga);

    }
}
