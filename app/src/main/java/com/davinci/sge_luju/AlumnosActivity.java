package com.davinci.sge_luju;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class AlumnosActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_alumnos);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // CONTAINER DE LISTADO DE ALUMNOS
        LinearLayout alumnosContainer = this.findViewById(R.id.MainContentScrollLinearLayout);

        for (int i = 0; i < 3; i++) {

            // CREO FILA DE ALUMNO
            LinearLayout alumnoRow = new LinearLayout(this);
            alumnoRow.setOrientation(LinearLayout.HORIZONTAL);
            LinearLayout.LayoutParams alumnoRowParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            alumnoRowParams.setMargins(0, 0, 0, 30);
            alumnoRow.setLayoutParams(alumnoRowParams);

            // CREO IMAGEN DE LA FILA ALUMNO
            ImageView alumnoPicture = new ImageView(this);
            ViewGroup.LayoutParams alumnoPictureParams = new ViewGroup.LayoutParams(200, 200);
            alumnoPicture.setLayoutParams(alumnoPictureParams);
            alumnoPicture.setImageResource(R.drawable.logoescuela);
            alumnoPicture.setScaleType(ImageView.ScaleType.FIT_CENTER);

            // AGREGO IMAGEN A LA FILA
            alumnoRow.addView(alumnoPicture);

            // CREO LAYOUT VERTICAL QUE CONTIENE INFORMACIÓN
            LinearLayout alumnoRowDataLayout = new LinearLayout(this);
            alumnoRowDataLayout.setOrientation(LinearLayout.VERTICAL);
            LinearLayout.LayoutParams alumnoRowDataLayoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            alumnoRowDataLayoutParams.setMargins(15, 0, 0, 5);
            alumnoRowDataLayout.setLayoutParams(alumnoRowDataLayoutParams);

            // CREO NOMBRE DEL ALUMNO
            TextView alumnoName = new TextView(this);
            alumnoName.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            alumnoName.setText(R.string.alumno_test_name);

            // AGREGO NOMBRE A LAYOUT DE DATA DEL ALUMNO
            alumnoRowDataLayout.addView(alumnoName);

            // CREO CURSO DEL ALUMNO
            TextView alumnoGrade = new TextView(this);
            alumnoGrade.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            alumnoGrade.setText(R.string.alumno_test_curso);

            // AGREGO CURSO A LAYOUT DE DATA DEL ALUMNO
            alumnoRowDataLayout.addView(alumnoGrade);

            // CREO EDAD DEL ALUMNO
            TextView alumnoAge = new TextView(this);
            alumnoAge.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            alumnoAge.setText(R.string.alumno_test_age);

            // AGREGO EDAD A LAYOUT DE DATA DEL ALUMNO
            alumnoRowDataLayout.addView(alumnoAge);

            // AGREGO DATA A LA ROW
            alumnoRow.addView(alumnoRowDataLayout);

            // AGREGO FILA AL CONTENEDOR DE ALUMNOS
            alumnosContainer.addView(alumnoRow);


        }
    }

    public void agregarAlumnoPrueba(View view) {
        // CREO FILA DE ALUMNO
        LinearLayout alumnoRow = new LinearLayout(this);
        alumnoRow.setOrientation(LinearLayout.HORIZONTAL);
        LinearLayout.LayoutParams alumnoRowParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        alumnoRowParams.setMargins(0, 0, 0, 30);
        alumnoRow.setLayoutParams(alumnoRowParams);

        // CREO IMAGEN DE LA FILA ALUMNO
        ImageView alumnoPicture = new ImageView(this);
        ViewGroup.LayoutParams alumnoPictureParams = new ViewGroup.LayoutParams(200, 200);
        alumnoPicture.setLayoutParams(alumnoPictureParams);
        alumnoPicture.setImageResource(R.drawable.logoescuela);
        alumnoPicture.setScaleType(ImageView.ScaleType.FIT_CENTER);

        // AGREGO IMAGEN A LA FILA
        alumnoRow.addView(alumnoPicture);

        // CREO LAYOUT VERTICAL QUE CONTIENE INFORMACIÓN
        LinearLayout alumnoRowDataLayout = new LinearLayout(this);
        alumnoRowDataLayout.setOrientation(LinearLayout.VERTICAL);
        LinearLayout.LayoutParams alumnoRowDataLayoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        alumnoRowDataLayoutParams.setMargins(15, 0, 0, 5);
        alumnoRowDataLayout.setLayoutParams(alumnoRowDataLayoutParams);

        // CREO NOMBRE DEL ALUMNO
        TextView alumnoName = new TextView(this);
        alumnoName.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        alumnoName.setText(R.string.alumno_test_name);

        // AGREGO NOMBRE A LAYOUT DE DATA DEL ALUMNO
        alumnoRowDataLayout.addView(alumnoName);

        // CREO CURSO DEL ALUMNO
        TextView alumnoGrade = new TextView(this);
        alumnoGrade.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        alumnoGrade.setText(R.string.alumno_test_curso);

        // AGREGO CURSO A LAYOUT DE DATA DEL ALUMNO
        alumnoRowDataLayout.addView(alumnoGrade);

        // CREO EDAD DEL ALUMNO
        TextView alumnoAge = new TextView(this);
        alumnoAge.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        alumnoAge.setText(R.string.alumno_test_age);

        // AGREGO EDAD A LAYOUT DE DATA DEL ALUMNO
        alumnoRowDataLayout.addView(alumnoAge);

        // AGREGO DATA A LA ROW
        alumnoRow.addView(alumnoRowDataLayout);

        // AGREGO FILA AL CONTENEDOR DE ALUMNOS
        LinearLayout alumnosContainer = this.findViewById(R.id.MainContentScrollLinearLayout);
        alumnosContainer.addView(alumnoRow);
    }
}