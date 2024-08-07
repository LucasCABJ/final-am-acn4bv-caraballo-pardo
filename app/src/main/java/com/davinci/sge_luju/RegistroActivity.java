package com.davinci.sge_luju;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class RegistroActivity extends AppCompatActivity {

    private static final String CONFIRMATION_CODE = "1234";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_registro);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.MainNav), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Obtengo las referencias a los EditTexts
        EditText etFirstName = findViewById(R.id.firstName);
        EditText etLastName = findViewById(R.id.lastName);
        EditText etAge = findViewById(R.id.age);
        EditText etEmail = findViewById(R.id.email);
        EditText etPassword = findViewById(R.id.password);
        EditText etConfirmPassword = findViewById(R.id.confirmPassword);
        EditText etConfirmationCode = findViewById(R.id.confirmationCode);
        Button btnRegister = findViewById(R.id.registerBtn);
        Button btnBackToMain = findViewById(R.id.backToMainBtn);

        // Configuro el botón de registro

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String inputCode = etConfirmationCode.getText().toString();
                if (inputCode.equals(CONFIRMATION_CODE)) {
                    // Mostrar mensaje de éxito
                    Toast.makeText(RegistroActivity.this, "Registro exitoso", Toast.LENGTH_SHORT).show();
                    // Redirigirr a la página de inicio
                    Intent intent = new Intent(RegistroActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish(); // Finalizar la actividad actual
                } else {
                    // Mostrar mensaje de error
                    Toast.makeText(RegistroActivity.this, "Código de confirmación incorrecto", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnBackToMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Redirigir a la página de inicio
                Intent intent = new Intent(RegistroActivity.this, MainActivity.class);
                startActivity(intent);
                finish(); // Finalizar la actividad actual
            }
        });
    }
}