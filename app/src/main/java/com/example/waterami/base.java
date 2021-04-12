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
    private static final String DATABASE_NAME = "dados4.db";
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
    public void clearDatabase(String TABLE_NAME) {
        String clearDBQuery = "DELETE FROM "+TABLE_NAME;
        db.execSQL(clearDBQuery);
    }
}