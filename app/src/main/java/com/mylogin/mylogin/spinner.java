package com.mylogin.mylogin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class spinner extends AppCompatActivity {

    private EditText editTextCorreo;
    private EditText editTextContrasena;
    private EditText editTextConfirmarContrasena;
    private DatePicker datePickerFechaNacimiento;
    private Button btnRegistro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spinner);


        editTextCorreo = findViewById(R.id.editTextCorreo);
        editTextContrasena = findViewById(R.id.editTextContrasena);
        editTextConfirmarContrasena = findViewById(R.id.editTextConfirmarContrasena);
        datePickerFechaNacimiento = findViewById(R.id.datePickerFechaNacimiento);
        btnRegistro = findViewById(R.id.btnRegistro);


        btnRegistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registrarUsuario();
            }
        });
    }

    private void registrarUsuario() {

        String correo = editTextCorreo.getText().toString();
        String contrasena = editTextContrasena.getText().toString();
        String confirmarContrasena = editTextConfirmarContrasena.getText().toString();



        if (contrasena.equals(confirmarContrasena)) {

            Toast.makeText(this, "Usuario registrado con éxito", Toast.LENGTH_SHORT).show();
        } else {

            Toast.makeText(this, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show();
        }
    }
    private void handleSpinnerSelection(String selectedItem) {
        switch (selectedItem) {
            case "Contact":
                 // Intent contactoIntent = new Intent(this, ContactoActivity.class);
                //startActivity(contactoIntent);
                break;
            case "Volver al login":

                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                finish();
                break;

            case "Ayuda":
                Toast.makeText(this, "¡Bienvenido a la ayuda!", Toast.LENGTH_SHORT).show();
                break;

        }
    }

}