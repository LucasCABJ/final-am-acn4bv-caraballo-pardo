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

import com.davinci.sge_luju.model.Alumno;
import com.davinci.sge_luju.utils.NetworkUtil;
import com.google.firebase.firestore.FirebaseFirestore;

public class DetalleAlumnoActivity extends AppCompatActivity {

    TextView textViewNombreAlumno;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_alumno);

        this.textViewNombreAlumno = findViewById(R.id.textViewNombreAlumno);
        // Obtener el ID del alumno desde el Intent
        Alumno alumno = (Alumno) getIntent().getSerializableExtra("alumno");
        cargarAlumno(alumno);
    }

    // Método para mostrar estado de carga
    /*private void mostrarEstadoDeCarga(boolean mostrando, String mensaje) {
        LinearLayout statusContainerView = findViewById(R.id.statusContainer);
        statusContainerView.setVisibility(mostrando ? View.VISIBLE : View.GONE);
        TextView statusText = statusContainerView.findViewById(R.id.statusText);
        statusText.setVisibility(mostrando ? View.VISIBLE : View.GONE);
        statusText.setText(mensaje);
        ProgressBar statusProgressBar = statusContainerView.findViewById(R.id.progressBar);
        statusProgressBar.setVisibility(mostrando ? View.VISIBLE : View.GONE);
    }*/

    private void cargarAlumno(Alumno alumno) {
        this.textViewNombreAlumno.setText(alumno.getNombre());
    }

}