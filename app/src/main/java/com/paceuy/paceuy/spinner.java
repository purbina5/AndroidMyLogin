package com.paceuy.paceuy;

import androidx.annotation.NonNull;
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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class spinner extends AppCompatActivity {

    private EditText usernameEditText;
    private EditText passwordEditText;
    private Button registerButton;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spinner);

        mAuth = FirebaseAuth.getInstance();

        usernameEditText = findViewById(R.id.editTextCorreo);
        passwordEditText = findViewById(R.id.editTextContrasena);
        registerButton = findViewById(R.id.btnRegistro);

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = usernameEditText.getText().toString();
                String password = passwordEditText.getText().toString();

                mAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(spinner.this, "Registro exitoso", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(spinner.this, MainActivity.class);
                                    startActivity(intent);
                                } else {
                                    Toast.makeText(spinner.this, "Error en el registro", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
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