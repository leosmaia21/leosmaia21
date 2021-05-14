package com.example.waterami;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.InputType;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class tecnico extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tecnico);
        getSupportActionBar().hide();
        MqttHelper mqttHelper = new MqttHelper(getApplicationContext());
        mqttHelper.connect();


        TextView multiplicador = findViewById(R.id.multiplicador_textbox);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        multiplicador.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                closekeyboard();
                PopupMenu menu = new PopupMenu(tecnico.this, multiplicador);
                menu.getMenu().add("1 L");
                menu.getMenu().add("10 L");
                menu.getMenu().add("100 L");
                menu.getMenu().add("1000 L");
                menu.show();
                menu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {
                        //item.getTitle(),
                       multiplicador.setText(item.getTitle());
                        return true;
                    }
                });
                menu.show();
            }
        });
    }
    private  void closekeyboard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

}

