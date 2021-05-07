package com.example.waterami;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallbackExtended;
import org.eclipse.paho.client.mqttv3.MqttMessage;

public class menu extends AppCompatActivity {
    private static final String TAG = "menu";
    private static final int ERROR_DIALOG_REQUEST = 9001;
    MqttHelper mqttHelper;
    //Mqttservice m qtt;
    base base;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        getSupportActionBar().hide();
        Button update = (Button) findViewById(R.id.update);
          mqttHelper = new MqttHelper(getApplicationContext());
          mqttHelper.connect();

        base =new base(menu.this);

        if(isServicesOK()){
            mapa();
        }
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(menu.this, tecnico.class);
                startActivity(intent);
            }
        });
    }

    private void mapa(){
        Button map = (Button) findViewById(R.id.map);
        map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mqttHelper.subscribeToTopic("home/water/out",2);
                mqttHelper.publish("home/water/in","select * from tca");
                mqttHelper.mqttAndroidClient.setCallback(new MqttCallbackExtended() {
                    @Override
                    public void connectComplete(boolean b, String s) {
                    }
                    @Override
                    public void connectionLost(Throwable throwable) {
                    }
                    @Override
                    public void messageArrived(String topic, MqttMessage mqttMessage) throws Exception {
                        base.d_tca(mqttMessage.toString());
                        Log.w("login login", mqttMessage.toString());
                        Intent intent = new Intent(menu.this, mapa.class);
                        startActivity(intent);
                    }
                    @Override
                    public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {
                    }
                });

            }
        });
    }
    public boolean isServicesOK(){
        Log.d(TAG, "isServicesOK: checking google services version");

        int available = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(menu.this);

        if(available == ConnectionResult.SUCCESS){
            //everything is fine and the user can make map requests
            Log.d(TAG, "isServicesOK: Google Play Services is working");
            return true;
        }
        else if(GoogleApiAvailability.getInstance().isUserResolvableError(available)){
            //an error occured but we can resolve it
            Log.d(TAG, "isServicesOK: an error occured but we can fix it");
            Dialog dialog = GoogleApiAvailability.getInstance().getErrorDialog(menu.this, available, ERROR_DIALOG_REQUEST);
            dialog.show();
        }else{
            Toast.makeText(this, "You can't make map requests", Toast.LENGTH_SHORT).show();
        }
        return false;
    }
}