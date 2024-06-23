package com.davinci.sge_luju;

import static android.content.ContentValues.TAG;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.davinci.sge_luju.utils.NetworkUtil;
import com.google.firebase.firestore.FirebaseFirestore;

public class DetalleAlumnoActivity extends AppCompatActivity {

    private TextView textViewNombreAlumno;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_alumno);

        textViewNombreAlumno = findViewById(R.id.textViewNombreAlumno);

        // Obtener el ID del alumno desde el Intent
        String alumnoId = getIntent().getStringExtra("alumno_id");

        //  CHEQUEAR CONNECTIVITY
        if (NetworkUtil.isNetworkAvailable(this)) {
            Log.d(TAG, "Conectividad funcionando correctamente");
            // Conexion a DB FIREBASE
            FirebaseFirestore db = FirebaseFirestore.getInstance();
            cargarAlumno(db, alumnoId);
        } else {
            Log.d(TAG, "No hay conectividad");
        }
    }

    // Método para mostrar estado de carga
    private void mostrarEstadoDeCarga(boolean mostrando, String mensaje) {
        LinearLayout statusContainerView = findViewById(R.id.statusContainer);
        statusContainerView.setVisibility(mostrando ? View.VISIBLE : View.GONE);
        TextView statusText = statusContainerView.findViewById(R.id.statusText);
        statusText.setVisibility(mostrando ? View.VISIBLE : View.GONE);
        statusText.setText(mensaje);
        ProgressBar statusProgressBar = statusContainerView.findViewById(R.id.progressBar);
        statusProgressBar.setVisibility(mostrando ? View.VISIBLE : View.GONE);
    }

    private void cargarAlumno(FirebaseFirestore db, String alumnoId) {
        mostrarEstadoDeCarga(true, getString(R.string.loadingStudentData));
        db.collection("alumno")
                .whereEqualTo("id", alumnoId)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        if (task.getResult().isEmpty()) {
                            mostrarEstadoDeCarga(true, getString(R.string.alumnos_activity_no_results));
                            ProgressBar statusProgressBar = findViewById(R.id.progressBar);
                            statusProgressBar.setVisibility(View.GONE);
                        }
                    }
                })
                .addOnFailureListener(e ->
                        mostrarEstadoDeCarga(true, getString(R.string.alumnos_load_fail_error_msg)));


    }

}