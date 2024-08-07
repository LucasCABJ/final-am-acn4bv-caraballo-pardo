package com.davinci.sge_luju;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

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
        btnRegister.setOnClickListener(v -> {

            String inputCode = etConfirmationCode.getText().toString();

            // Validar que los campos estén completos
            if (TextUtils.isEmpty(etFirstName.getText().toString()) ||
                    TextUtils.isEmpty(etLastName.getText().toString()) ||
                    TextUtils.isEmpty(etAge.getText().toString()) ||
                    TextUtils.isEmpty(etEmail.getText().toString()) ||
                    TextUtils.isEmpty(etPassword.getText().toString()) ||
                    TextUtils.isEmpty(etConfirmPassword.getText().toString()) ||
                    TextUtils.isEmpty(etConfirmationCode.getText().toString())
            ) {
                // Mostrar mensaje de error
                Toast.makeText(RegistroActivity.this, "Todos los campos son obligatorios", Toast.LENGTH_SHORT).show();
            } else {
                // Validar que las contraseñas coincidan
                if (!etPassword.getText().toString().equals(etConfirmPassword.getText().toString())) {
                    // Mostrar mensaje de error
                    Toast.makeText(RegistroActivity.this, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show();
                } else {
                    // Validar que el código de confirmación sea correcto
                    if (!inputCode.equals(CONFIRMATION_CODE)) {
                        // Mostrar mensaje de error
                        Toast.makeText(RegistroActivity.this, "Código de confirmación incorrecto", Toast.LENGTH_SHORT).show();
                    } else {
                        // Guardar usuario en Firebase Firestore
                        crearUsuario(etFirstName.getText().toString(), etLastName.getText().toString(), etAge.getText().toString(), etEmail.getText().toString(), etPassword.getText().toString());

                        // Mostrar mensaje de éxito
                        Toast.makeText(RegistroActivity.this, "Usuario " + etFirstName.getText().toString() + " " + etLastName.getText().toString() + " registrado exitosamente", Toast.LENGTH_SHORT).show();
                        // Redirigirr a la página de inicio
                        Intent intent = new Intent(RegistroActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish(); // Finalizar la actividad actual
                    }
                }
            }
        });

        // Configuro el botón de volver a la página de inicio
        btnBackToMain.setOnClickListener(v -> {
            // Redirigir a la página de inicio
            Intent intent = new Intent(RegistroActivity.this, MainActivity.class);
            startActivity(intent);
            finish(); // Finalizar la actividad actual
        });
    }

    private void crearUsuario(String name, String lastName, String age, String email, String password) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        Map<String, Object> alumno = new HashMap<>();
        alumno.put("nombre", name);
        alumno.put("apellido", lastName);
        alumno.put("edad", age);
        alumno.put("email", email);
        alumno.put("password", password);

        db.collection("alumno").
                add(alumno)
                .addOnSuccessListener(documentReference -> handleSuccess())
                .addOnFailureListener(e -> handleFailure());
    }

    private void handleFailure() {
        Toast.makeText(RegistroActivity.this, "Error al crear el usuario", Toast.LENGTH_SHORT).show();
    }

    private void handleSuccess() {
        Toast.makeText(RegistroActivity.this, "Usuario creado exitosamente", Toast.LENGTH_SHORT).show();
        navigateToMain();
    }

    private void navigateToMain() {
        Intent intent = new Intent(RegistroActivity.this, MainActivity.class);
        startActivity(intent);
        finish(); // Finalizar la actividad actual
    }
}