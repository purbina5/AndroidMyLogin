package com.paceuy.paceuy;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    private EditText emailEditText;
    private EditText passwordEditText;
    private Button loginButton;
    private Button registerButton;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();

        emailEditText = findViewById(R.id.editTextText);
        passwordEditText = findViewById(R.id.editTextTextPassword);
        loginButton = findViewById(R.id.button);
        registerButton = findViewById(R.id.btnIrASpinnerDesdeMain);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Obtener los valores de correo electrónico y contraseña
                String email = emailEditText.getText().toString();
                String password = passwordEditText.getText().toString();

                // Autenticar al usuario con Firebase Authentication
                mAuth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // El inicio de sesión fue exitoso
                                    FirebaseUser user = mAuth.getCurrentUser();
                                    // Puedes redirigir a la actividad de inicio o hacer lo que necesites aquí
                                    // Por ejemplo, redirigir a HomePageActivity
                                    Intent intent = new Intent(MainActivity.this, inicio.class);
                                    startActivity(intent);
                                } else {
                                    // El inicio de sesión falló, mostrar un mensaje de error
                                    Toast.makeText(MainActivity.this, "Inicio de sesión fallido", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Redirigir a RegistrationActivity para registrar una nueva cuenta
                Intent intent = new Intent(MainActivity.this, spinner.class);
                startActivity(intent);
            }

        });
        ImageButton facebookButton = findViewById(R.id.imageButton3);
        ImageButton twitterButton = findViewById(R.id.imageButton4);
        ImageButton instagramButton = findViewById(R.id.imageButton5);
        facebookButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openUrl("https://www.facebook.com/");
            }
        });

        twitterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openUrl("https://twitter.com/");
            }
        });

        instagramButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openUrl("https://www.instagram.com/");
            }
        });
    }

    private void openUrl(String url) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        startActivity(intent);
    }


    }





/*public void irASpinnerDesdeMain(View view) {
        Intent intent = new Intent(this,spinner.class);
        startActivity(intent);
    }
    public void iraInicioDesdeMain(View view) {
        Intent intent = new Intent(this,inicio.class);
        startActivity(intent);
    }
}*/
