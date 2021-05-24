package com.example.waterami;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.util.ArrayList;

public class grafico extends AppCompatActivity {
    LineChart mpLineChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grafico);

        //<<Gráfico de linhas >>
        mpLineChart=(LineChart) findViewById(R.id.line_chart);
        LineDataSet lineDataSet1 = new LineDataSet(dataValues1(), "Data");
        ArrayList<ILineDataSet> dataSets = new ArrayList<>();
        dataSets.add(lineDataSet1);


        //    mpLineChart.setBackgroundColor(Color.GREEN);  //definir cor do fundo
        mpLineChart.setNoDataText("No Data");           //mensagem no caso de não ter dados como entrada
        mpLineChart.setNoDataTextColor(Color.BLUE);
        mpLineChart.setDrawGridBackground(true);       //grelha de fundo
        mpLineChart.setDrawBorders(false);              //margens
        mpLineChart.setBorderColor(Color.RED);


        //<< Descrição do gráfico >>
        Description description = new Description();
        description.setText("Readings");
        description.setTextColor(Color.BLUE);
        description.setTextSize(20);
        mpLineChart.setDescription(description);

        lineDataSet1.setLineWidth(4);   //grossura linha1
        lineDataSet1.setColor(Color.RED);   //cor linha1
        lineDataSet1.setDrawCircles(true);   //colocar circulos nos pontos linha1
        lineDataSet1.setDrawCircleHole(true);  //buraco nos circulos

        lineDataSet1.setValueTextSize(10);   //tamanho dos numeros nos pontos



        Legend legend = mpLineChart.getLegend();  //trocar a cor das legendas
        legend.setEnabled(true);
        legend.setTextColor(Color.BLACK);  //cor
        legend.setTextSize(15);   //tamanho
        legend.setForm(Legend.LegendForm.LINE);   //formato do indicador de cor
        legend.setFormSize(20);
        legend.setXEntrySpace(15);   //espaço entre as legendas

        LineData data = new LineData(dataSets);
        mpLineChart.setData(data);
        mpLineChart.invalidate();
    }

    private ArrayList<Entry> dataValues1(){
        ArrayList<Entry> dataVals = new ArrayList<Entry>();   // pontos do gráfico
        dataVals.add(new Entry( 0 , 20));
        dataVals.add(new Entry( 1 , 24));
        dataVals.add(new Entry( 2 , 2));
        dataVals.add(new Entry( 3 , 10));
        dataVals.add(new Entry( 4 , 28));

        return dataVals;


    }






    }
