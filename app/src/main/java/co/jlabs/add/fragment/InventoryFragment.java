package co.jlabs.add.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import co.jlabs.add.R;
import co.jlabs.add.adapter.InventoryAdapter;
import co.jlabs.add.db.ClassInventory;
import co.jlabs.add.db.LocalDB;

/**
 * Created by JLabs on 08/26/16.
 */
public class InventoryFragment extends Fragment {

    private RecyclerView recyclerView;
    ArrayList<ClassInventory> invent;
    InventoryAdapter mAdapter;
    LocalDB ldb;
    public InventoryFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View v = inflater.inflate(R.layout.inv_frag, container, false);
        initView(v);
        return v;

    }

    private void initView(View v) {
        recyclerView = (RecyclerView) v.findViewById(R.id.recyclerView);
        ldb=new LocalDB(getActivity().getApplicationContext());

        invent=ldb.getAllInventory();
        mAdapter = new InventoryAdapter(getActivity().getApplicationContext(), invent);

        RecyclerView.LayoutManager layoutManager =new GridLayoutManager(getActivity().getApplicationContext(),1);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);

    }

}
