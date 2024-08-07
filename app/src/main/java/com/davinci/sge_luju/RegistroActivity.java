package com.davinci.sge_luju;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
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
    private EditText etFirstName, etLastName, etAge, etEmail, etPassword, etConfirmPassword, etConfirmationCode;
    private Button btnRegister, btnBackToMain;
    private ProgressBar progressBar;

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
        etFirstName = findViewById(R.id.firstName);
        etLastName = findViewById(R.id.lastName);
        etAge = findViewById(R.id.age);
        etEmail = findViewById(R.id.email);
        etPassword = findViewById(R.id.password);
        etConfirmPassword = findViewById(R.id.confirmPassword);
        etConfirmationCode = findViewById(R.id.confirmationCode);
        btnRegister = findViewById(R.id.registerBtn);
        btnBackToMain = findViewById(R.id.backToMainBtn);
        progressBar = findViewById(R.id.progressBar);

        // Configuro el botón de registro
        btnRegister.setOnClickListener(v -> {
            handleRegistration();
        });


        // Configuro el botón de volver a la página de inicio
        btnBackToMain.setOnClickListener(v -> {
            // Redirigir a la página de inicio
            navigateToMain();
        });
    }

    private void handleRegistration() {
        if (!isFieldsFilled()) return;
        if (!isPasswordsMatch()) return;
        if (!isConfirmationCodeCorrect()) return;

        // Deshabilitar el botón de registro y mostrar el ProgressBar
        btnRegister.setEnabled(false);
        progressBar.setVisibility(View.VISIBLE);

        // Crear usuario en Firebase Firestore
        crearUsuario(
                etFirstName.getText().toString().trim(),
                etLastName.getText().toString().trim(),
                etAge.getText().toString().trim(),
                etEmail.getText().toString().trim(),
                etPassword.getText().toString().trim()
        );
    }

    private boolean isConfirmationCodeCorrect() {
        if (!etConfirmationCode.getText().toString().equals(CONFIRMATION_CODE)) {
            // Mostrar mensaje de error
            showToast("Código de confirmación incorrecto");
            return false;
        }
        return true;
    }

    private void showToast(String message) {
        Toast.makeText(RegistroActivity.this, message, Toast.LENGTH_SHORT).show();
    }

    private boolean isPasswordsMatch() {
        if (!etPassword.getText().toString().equals(etConfirmPassword.getText().toString())) {
            // Mostrar mensaje de error
            showToast("Las contraseñas no coinciden");
            return false;
        }
        return true;
    }

    private boolean isFieldsFilled() {
        boolean isAllFilled = true;

        isAllFilled &= validateField(etFirstName, "Complete su nombre");
        isAllFilled &= validateField(etLastName, "Complete su apellido");
        isAllFilled &= validateField(etAge, "Complete su edad");
        isAllFilled &= validateField(etEmail, "Complete su correo electrónico");
        isAllFilled &= validateField(etPassword, "Complete su contraseña");
        isAllFilled &= validateField(etConfirmPassword,  "Complete su contraseña");
        isAllFilled &= validateField(etConfirmationCode,"Complete su código de confirmación");

        if (!isAllFilled) {
            // Mostrar mensaje de error
            showToast("Complete todos los campos");
        }
        return isAllFilled;
    }

    private boolean validateField(EditText editText, String message) {
        if (TextUtils.isEmpty(editText.getText().toString())) {
            editText.setError(message);
            return false;
        }
        return true;
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
        btnRegister.setEnabled(true);
        progressBar.setVisibility(View.GONE);
    }

    private void handleSuccess() {
        showToast("Usuario creado exitosamente");
        navigateToMain();
    }

    private void navigateToMain() {
        Intent intent = new Intent(RegistroActivity.this, MainActivity.class);
        startActivity(intent);
        finish(); // Finalizar la actividad actual
    }
}