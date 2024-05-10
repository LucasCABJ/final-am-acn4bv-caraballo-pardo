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

import com.davinci.sge_luju.model.Alumno;

import java.time.LocalDate;

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

    // Creo un array para almacenar 10 alumnos
    Alumno[] alumnos = new Alumno[3];

    // Creamos manualmente instancias de Alumno y las agregamos al array
    alumnos[0] = new Alumno("Juan", "Perez", "juan@example.com");
    alumnos[1] = new Alumno("María", "González", "maria@example.com");
    alumnos[2] = new Alumno("Carlos", "Rodríguez", "carlos@example.com");
    /*alumnos[3] = new Alumno("Laura", "Martínez", "laura@example.com");
    alumnos[4] = new Alumno("Pedro", "Sánchez", "pedro@example.com");
    alumnos[5] = new Alumno("Ana", "López", "ana@example.com");
    alumnos[6] = new Alumno("Sofía", "Fernández", "sofia@example.com");
    alumnos[7] = new Alumno("Diego", "Díaz", "diego@example.com");
    alumnos[8] = new Alumno("Elena", "Torres", "elena@example.com");
    alumnos[9] = new Alumno("Pablo", "Ruiz", "pablo@example.com");*/


    // CONTAINER DE LISTADO DE ALUMNOS
    LinearLayout alumnosContainer = this.findViewById(R.id.MainContentScrollLinearLayout);

    for (int i = 0; i < alumnos.length; i++) {
      Alumno alumno = alumnos[i];
      crearFilaAlumno(alumno);
    }
  }
  // Fin onCreate

  private void crearFilaAlumno(Alumno alumno) {
    // Creo fila de alumno
    LinearLayout alumnoRow = new LinearLayout(this);
    alumnoRow.setOrientation(LinearLayout.HORIZONTAL);
    LinearLayout.LayoutParams alumnoRowParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
    alumnoRowParams.setMargins(0, 0, 0, 30);
    alumnoRow.setLayoutParams(alumnoRowParams);

    // Creo imagen de la fila alumno
    ImageView alumnoPicture = new ImageView(this);
    ViewGroup.LayoutParams alumnoPictureParams = new ViewGroup.LayoutParams(200, 200);
    alumnoPicture.setLayoutParams(alumnoPictureParams);
    alumnoPicture.setImageResource(R.drawable.logoescuela); // setImageResource() espera recibir un int ??
    alumnoPicture.setScaleType(ImageView.ScaleType.FIT_CENTER);

    // Agrego imagen a la fila
    alumnoRow.addView(alumnoPicture);

    // Creo layout vertical que contiene información
    LinearLayout alumnoRowDataLayout = new LinearLayout(this);
    alumnoRowDataLayout.setOrientation(LinearLayout.VERTICAL);
    LinearLayout.LayoutParams alumnoRowDataLayoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
    alumnoRowDataLayoutParams.setMargins(15, 0, 0, 5);
    alumnoRowDataLayout.setLayoutParams(alumnoRowDataLayoutParams);

    // Creo nombre del alumno
    TextView alumnoName = new TextView(this);
    alumnoName.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
    alumnoName.setText(alumno.getFullName());

    // Agrego nombre a layout de data del alumno
    alumnoRowDataLayout.addView(alumnoName);

    // Creo curso del alumno
    TextView alumnoGrade = new TextView(this);
    alumnoGrade.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
    alumnoGrade.setText(alumno.getCurso());

    // Agrego curso a layout de data del alumno
    alumnoRowDataLayout.addView(alumnoGrade);

    // Creo edad del alumno
    TextView alumnoAge = new TextView(this);
    alumnoAge.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
    alumnoAge.setText(alumno.calcularEdad());

    // Agrego edad a layout de data del alumno
    alumnoRowDataLayout.addView(alumnoAge);

    // Agrego data a la row
    alumnoRow.addView(alumnoRowDataLayout);

    // Agrego fila al contenedor de alumnos
    LinearLayout alumnosContainer = this.findViewById(R.id.MainContentScrollLinearLayout);
    alumnosContainer.addView(alumnoRow);

  }

  public void agregarAlumnoPrueba(View view) {
    Alumno alumno = new Alumno("Prueba", "Prueba", "prueba@example.com");
    crearFilaAlumno(alumno);
  }
}