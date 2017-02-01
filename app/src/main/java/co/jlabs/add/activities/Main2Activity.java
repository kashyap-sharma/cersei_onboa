package co.jlabs.add.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;



import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import co.jlabs.add.R;
import co.jlabs.add.spinner.NiceSpinner;

public class Main2Activity extends AppCompatActivity {
    NiceSpinner niceSpinner;
    List<String> dataset;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        niceSpinner = (NiceSpinner) findViewById(R.id.nice_spinner);
         dataset = new LinkedList<>(Arrays.asList("One", "Two", "Three", "Four", "Five"));
        niceSpinner.attachDataSource(dataset);
       // Log.e("dats",""+niceSpinner.getText());
        niceSpinner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // Log.e("dats1",""+niceSpinner.getText());
            }
        });
        niceSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                 Log.e("dats2",""+dataset.get(position).toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        niceSpinner.addOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.e("dats3"+position,""+dataset.get(position).toString());
            }
        });
    }
}
