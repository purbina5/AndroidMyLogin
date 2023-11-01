package com.mylogin.mylogin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class inicio extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio);
        Button btn = (Button) findViewById(R.id.mapButton);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(inicio.this,activity_main.class);
                startActivity(intent);
            }
        });
    }
    //public void irAmapadesdeInicio(View view) {
      //  Intent intent = new Intent(this,activity_main.class);
        //startActivity(intent);
    //}
}