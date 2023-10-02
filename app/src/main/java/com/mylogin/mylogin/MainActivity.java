package com.mylogin.mylogin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.mylogin.mylogin.spinner;
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Spinner spinner = findViewById(R.id.spinner);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this,
                R.array.spinner_options,
                android.R.layout.simple_spinner_item
        );

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = parent.getItemAtPosition(position).toString();
                handleSpinnerSelection(selectedItem);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void handleSpinnerSelection(String selectedItem) {
        switch (selectedItem) {
            case "Contact":
                // Intent contactoIntent = new Intent(this, ContactoActivity.class);
                //startActivity(contactoIntent);
                break;
            case "Volver al login":
                finish();

                break;
            case "Ayuda":
                Toast.makeText(this, "¡Bienvenido a la ayuda!", Toast.LENGTH_SHORT).show();
                break;

        }
    }
    public void irASpinnerDesdeMain(View view) {
        Intent intent = new Intent(this,spinner.class);
        startActivity(intent);
    }
    public void iraInicioDesdeMain(View view) {
        Intent intent = new Intent(this,inicio.class);
        startActivity(intent);
    }
}
