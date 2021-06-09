package com.example.waterami;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class grafico extends AppCompatActivity {
    private static final String TAG = grafico.class.getSimpleName();
    GraphView graphView;
base base;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grafico);

        graphView = findViewById(R.id.graph);
        base=new base(grafico.this);

        long x[]=base.get_timestamp();
        double y[]=base.get_agua();
        for(int i=0;i<x.length;i++){
            Log.d(TAG, "timestamp n"+i+"  :"+y[i]);
        }



    }
}