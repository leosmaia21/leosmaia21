package com.example.waterami;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class base extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "dados5.db";
    private static final String TAG = base.class.getSimpleName();
    Cursor c = null;
    SQLiteDatabase db;
    public  boolean tcax;
    public base(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        db = this.getWritableDatabase();
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE "+ tabelas.tca.TABLE_NAME+"("+ tabelas.tca.COLUMN_NAME_ID+" integer not null unique primary key, "+ tabelas.tca.COLUMN_NAME_LAT+" FLOAT NOT NULL,"+ tabelas.tca.COLUMN_NAME_LON+" FLOAT NOT NULL);");
        db.execSQL("CREATE TABLE "+ tabelas.pgc.TABLE_NAME+"("+ tabelas.pgc.COLUMN_NAME_ID+" integer not null unique primary key, "+ tabelas.pgc.COLUMN_NAME_LAT+" FLOAT NOT NULL,"+ tabelas.pgc.COLUMN_NAME_LON+" FLOAT NOT NULL);");
        db.execSQL("create table "+tabelas.agua.TABLE_NAME+"("+tabelas.agua.ID_TCA+" integer not null unique primary key, "+tabelas.agua.TIMESTAMP+ "text not null unique, "+ tabelas.agua.MEDIDA+" float not null);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
    public void d_tca(String dados) throws IOException, JSONException {
        try {
            JSONArray jsondata = new JSONArray(dados);
            for (int i = 0; i < jsondata.length(); i++) {
                int id;
                double lat;
                double lon;

                JSONObject dataObject = jsondata.getJSONObject(i);

                ContentValues valores = new ContentValues();
                id = dataObject.getInt("id");
                lat = dataObject.getDouble("lat");
                lon = dataObject.getDouble("lon");

                valores.put(tabelas.tca.COLUMN_NAME_ID, id);
                valores.put(tabelas.tca.COLUMN_NAME_LAT, lat);
                valores.put(tabelas.tca.COLUMN_NAME_LON, lon);
                db.insert(tabelas.tca.TABLE_NAME, null, valores);
            }
            Log.d(TAG, "tca localizacoes inseridos na base de dados");
           tcax=true;
        } catch (JSONException e) {
            Log.e(TAG, e.getMessage(), e);
            e.printStackTrace();
        }
    }
        public void d_pgc(String dados) throws IOException, JSONException {
            try{
                JSONArray jsondata= new JSONArray(dados);
                for(int i=0;i<jsondata.length();i++){
                    int id;
                    double lat;
                    double lon;

                    JSONObject dataObject=jsondata.getJSONObject(i);

                    ContentValues valores=new ContentValues();
                    id=dataObject.getInt("id");
                    lat= dataObject.getDouble("lat");
                    lon=dataObject.getDouble("lon");

                    valores.put(tabelas.pgc.COLUMN_NAME_ID,id);
                    valores.put(tabelas.pgc.COLUMN_NAME_LAT,lat);
                    valores.put(tabelas.pgc.COLUMN_NAME_LON,lon);


                    db.insert(tabelas.pgc.TABLE_NAME,null,valores);

                }
                Log.d(TAG, "pgc localizacoes inseridos na base de dados");
            } catch (JSONException e){
                Log.e(TAG, e.getMessage(),e);
                e.printStackTrace();
            }
    }
    public int get_id_tca(float lat,float lon){
        Cursor crs = db.rawQuery("select * from tca where lat="+lat+" and lon="+lon,null);
        crs.moveToFirst();
        int x = crs.getInt(crs.getColumnIndex("id"));
       return x;
    }
    public void get_agua(String dados){
        try{
            JSONArray jsondata= new JSONArray(dados);
            for(int i=0;i<jsondata.length();i++){
                int id;
                String timestamp;
                double medida;

                JSONObject dataObject=jsondata.getJSONObject(i);

                ContentValues valores=new ContentValues();
                id=dataObject.getInt("id");
                timestamp= dataObject.getString("timestamp");
                medida=dataObject.getDouble("medida");

                valores.put(tabelas.pgc.COLUMN_NAME_ID,id);
                valores.put(tabelas.pgc.COLUMN_NAME_LAT,timestamp);
                valores.put(tabelas.pgc.COLUMN_NAME_LON,medida);


                db.insert(tabelas.agua.TABLE_NAME,null,valores);

            }
            Log.d(TAG, "valores das medidas do tca inseridos na base de dados");
        } catch (JSONException e){
            Log.e(TAG, e.getMessage(),e);
            e.printStackTrace();
        }



    }
    public LatLng[] get_tca(){
        Cursor crs = db.rawQuery("SELECT * FROM "+tabelas.tca.TABLE_NAME, null);

        LatLng[] str= new LatLng[crs.getCount()];
        crs.moveToFirst();

        for(int i=0;i<str.length;i++)
        {
            str[i]=new LatLng(crs.getFloat(crs.getColumnIndex("lat")),crs.getFloat(crs.getColumnIndex("lon"))) ;
            crs.moveToNext();
        }
        crs.moveToLast();

        return str;

    }
    public LatLng[] get_pgc(){
        Cursor crs = db.rawQuery("SELECT * FROM pgc ", null);
        LatLng[] str= new LatLng[crs.getCount()];
        crs.moveToFirst();

        for(int i=0;i<str.length;i++)
        {
            str[i]=new LatLng(crs.getFloat(crs.getColumnIndex("lat")),crs.getFloat(crs.getColumnIndex("lon"))) ;
            crs.moveToNext();
        }

        return str;

    }
    public int[][] get_timestamp() {
        Cursor crs = db.rawQuery("SELECT * FROM " + tabelas.agua.TABLE_NAME, null);
        int[][] str = new int[crs.getCount()][crs.getCount()];
        crs.moveToFirst();
        String x, sdia, smes;
        int dia, mes;
        for (int i = 0; i < str.length; i++) {
            x = crs.getString(crs.getColumnIndex("timestamp"));
            sdia = x.substring(8, 10);
            smes = x.substring(5, 7);
            dia = Integer.parseInt(sdia);
            mes = Integer.parseInt(smes);
            str[i][0] = dia;
            str[i][1] = mes;
            // Log.d(TAG, "get_dia:"+ str[i][0]);
            crs.moveToNext();
        }
        return str;
    }
        public double[] get_data(String a){
            Cursor crs = db.rawQuery("SELECT * FROM agua", null);
            double[] str= new double[crs.getCount()];
            crs.moveToFirst();
            for(int i=0;i<str.length;i++)
            {
                str[i] = crs.getDouble(crs.getColumnIndex(a));
                //  Log.d(TAG, "get_data:"+str[i]);
                crs.moveToNext();
            }
            return str;

        }
    public void clearDatabase(String a) {
        String clearDBQuery = "DELETE FROM "+a;
        db.execSQL(clearDBQuery);
    }
}