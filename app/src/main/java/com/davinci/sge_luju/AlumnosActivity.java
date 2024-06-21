package com.davinci.sge_luju;

import static android.content.ContentValues.TAG;

import static com.google.firebase.firestore.Filter.or;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;
import com.davinci.sge_luju.model.Alumno;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Firebase;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

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
        SearchView searchView = this.findViewById(R.id.searchAlumnos);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                AlumnosActivity.this.findViewById(R.id.searchAlumnos).clearFocus();
                if(query.isEmpty()) {
                    cargarAlumnos(FirebaseFirestore.getInstance());
                } else {
                    LinearLayout alumnosContainer = AlumnosActivity.this.findViewById(R.id.MainContentScrollLinearLayout);
                    alumnosContainer.removeAllViews();
                    buscarAlumnos(FirebaseFirestore.getInstance(), query);
                }
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if(newText.isEmpty()) {
                    LinearLayout alumnosContainer = AlumnosActivity.this.findViewById(R.id.MainContentScrollLinearLayout);
                    alumnosContainer.removeAllViews();
                    cargarAlumnos(FirebaseFirestore.getInstance());
                }
                return true;
            }
        });
        //  CHEQUEAR CONNECTIVITY
        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnected()) {
            Log.d(TAG, "Conectividad funcionando correctamente");
            // Conexion a DB FIREBASE
            FirebaseFirestore db = FirebaseFirestore.getInstance();
            cargarAlumnos(db);

        } else {
          Log.d(TAG, "No hay conectividad");
        }
    }

    private void cargarAlumnos(FirebaseFirestore db) {
        LinearLayout statusContainerView = AlumnosActivity.this.findViewById(R.id.statusContainer);
        statusContainerView.setVisibility(View.VISIBLE);
        TextView statusText = statusContainerView.findViewById(R.id.statusText);
        statusText.setText(R.string.alumnos_loading_text);
        ProgressBar statusProgressBar = statusContainerView.findViewById(R.id.progressBar);
        statusProgressBar.setVisibility(View.VISIBLE);
        LinearLayout alumnosContainer = this.findViewById(R.id.MainContentScrollLinearLayout);
        alumnosContainer.removeAllViews();
        db.collection("alumno")
                .orderBy("nombre")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        LinearLayout statusContainerView = AlumnosActivity.this.findViewById(R.id.statusContainer);
                        if (task.isSuccessful()) {
                            int resultados = 0;
                            statusContainerView.setVisibility(View.GONE);
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                try {
                                    resultados++;
                                    Map<String, Object> campos = document.getData();
                                    String nombre = String.valueOf(campos.get("nombre"));
                                    String apellido = String.valueOf(campos.get("apellido"));
                                    String curso = "Cursando: 1° Año";
                                    Timestamp fecNacimientoTimeStamp = (Timestamp) campos.get("fec_nacimiento");
                                    String imagenURL = (String) campos.get("imagen_url");
                                    // TODO: IMPROVE WAY OF CALCULATING AGE
                                    int age;
                                    if (fecNacimientoTimeStamp != null) {
                                        DateFormat formatter = new SimpleDateFormat("yyyyMMdd");
                                        int d1 = Integer.parseInt(formatter.format(fecNacimientoTimeStamp.toDate()));
                                        int d2 = Integer.parseInt(formatter.format(Calendar.getInstance().getTime()));
                                        age = (d2 - d1) / 10000;
                                    } else {
                                        age = 0;
                                    }
                                    Alumno nuevoAlumno = new Alumno(document.getId(), nombre, apellido, curso, imagenURL, age);
                                    crearFilaAlumno(nuevoAlumno);
                                } catch (ClassCastException npe) {
                                    Log.d(TAG, "Error al castear o obtener data de: "+document.getId());
                                }
                            }
                            if(resultados > 0) {
                                statusContainerView.setVisibility(View.GONE);
                            } else {
                                TextView statusText = statusContainerView.findViewById(R.id.statusText);
                                statusText.setText(R.string.alumnos_activity_no_results);
                                ProgressBar statusProgressBar = statusContainerView.findViewById(R.id.progressBar);
                                statusProgressBar.setVisibility(View.GONE);
                            }
                        } else {
                            TextView statusTextView = statusContainerView.findViewById(R.id.statusText);
                            statusTextView.setText(R.string.alumnos_load_fail_error_msg);
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        TextView statusTextView = AlumnosActivity.this.findViewById(R.id.statusText);
                        statusTextView.setText(R.string.alumnos_load_fail_error_msg);
                    }
                });
    }

    private void buscarAlumnos(FirebaseFirestore db, String busqueda) {
        LinearLayout statusContainerView = AlumnosActivity.this.findViewById(R.id.statusContainer);
        statusContainerView.setVisibility(View.VISIBLE);
        TextView statusText = statusContainerView.findViewById(R.id.statusText);
        statusText.setText(R.string.alumnos_activity_buscando_msg);
        ProgressBar statusProgressBar = statusContainerView.findViewById(R.id.progressBar);
        statusProgressBar.setVisibility(View.VISIBLE);
        db.collection("alumno")
                .orderBy("nombre")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        LinearLayout statusContainerView = AlumnosActivity.this.findViewById(R.id.statusContainer);
                        if (task.isSuccessful()) {
                            int resultados = 0;
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                try {
                                    Map<String, Object> campos = document.getData();
                                    String nombre = String.valueOf(campos.get("nombre"));
                                    String apellido = String.valueOf(campos.get("apellido"));
                                    String nombreCompleto = nombre + " " + apellido;
                                    if (!nombreCompleto.toLowerCase().contains(busqueda.toLowerCase())) continue;
                                    resultados++;
                                    String curso = "Cursando: 1° Año";
                                    Timestamp fecNacimientoTimeStamp = (Timestamp) campos.get("fec_nacimiento");
                                    String imagenURL = (String) campos.get("imagen_url");
                                    // TODO: IMPROVE WAY OF CALCULATING AGE
                                    int age;
                                    if (fecNacimientoTimeStamp != null) {
                                        DateFormat formatter = new SimpleDateFormat("yyyyMMdd");
                                        int d1 = Integer.parseInt(formatter.format(fecNacimientoTimeStamp.toDate()));
                                        int d2 = Integer.parseInt(formatter.format(Calendar.getInstance().getTime()));
                                        age = (d2 - d1) / 10000;
                                    } else {
                                        age = 0;
                                    }
                                    Alumno nuevoAlumno = new Alumno(document.getId(), nombre, apellido, curso, imagenURL, age);
                                    crearFilaAlumno(nuevoAlumno);
                                } catch (ClassCastException npe) {
                                    Log.d(TAG, "Error al castear o obtener data de: "+document.getId());
                                }
                            }
                            if(resultados > 0) {
                                statusContainerView.setVisibility(View.GONE);
                            } else {
                                TextView statusText = statusContainerView.findViewById(R.id.statusText);
                                statusText.setText(R.string.alumnos_activity_no_results);
                                ProgressBar statusProgressBar = statusContainerView.findViewById(R.id.progressBar);
                                statusProgressBar.setVisibility(View.GONE);
                            }
                        } else {
                            TextView statusTextView = statusContainerView.findViewById(R.id.statusText);
                            statusTextView.setText(R.string.alumnos_load_fail_error_msg);
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        TextView statusTextView = AlumnosActivity.this.findViewById(R.id.statusText);
                        statusTextView.setText(R.string.alumnos_load_fail_error_msg);
                    }
                });
    }

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
        if (alumno.getImageURL() != null) {
            try {
                Glide.with(this).load(alumno.getImageURL()).into(alumnoPicture);
            } catch (Exception e) {
                alumnoPicture.setImageResource(R.drawable.logoescuela);
            }
        } else {
            alumnoPicture.setImageResource(R.drawable.logoescuela);
        }
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
        alumnoAge.setText(String.format(getString(R.string.edad_alumno), alumno.getEdad())); // formateado según strings.xml (recomendación del linter)

        // Agrego edad a layout de data del alumno
        alumnoRowDataLayout.addView(alumnoAge);

        // Agrego data a la row
        alumnoRow.addView(alumnoRowDataLayout);

        // OnClickListener para cada alumno
        // TODO: Indent y Bundle hacía vista Alumno
        alumnoRow.setOnClickListener((v) -> {
            System.out.println(alumno.getId());
        });

        // Agrego fila al contenedor de alumnos
        LinearLayout alumnosContainer = this.findViewById(R.id.MainContentScrollLinearLayout);
        alumnosContainer.addView(alumnoRow);


    }
}