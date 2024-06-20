package com.davinci.sge_luju;

import static android.content.ContentValues.TAG;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.davinci.sge_luju.model.Alumno;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
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

        //  CHEQUEAR CONNECTIVITY
        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnected()) {
            Log.d(TAG, "Conectividad funcionando correctamente");
            // Conexion a DB FIREBASE
            FirebaseFirestore db = FirebaseFirestore.getInstance();

            db.collection("alumno")
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            TextView statusTextView = AlumnosActivity.this.findViewById(R.id.statusText);
                            if (task.isSuccessful()) {
                                statusTextView.setVisibility(View.GONE);
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    try {
                                        Map<String, Object> campos = document.getData();
                                        System.out.println(campos);
                                        String nombre = String.valueOf(campos.get("nombre"));
                                        String apellido = String.valueOf(campos.get("apellido"));
                                        String curso = "Cursando: 1° Año";
                                        Timestamp fecNacimientoTimeStamp = (Timestamp) campos.get("fec_nacimiento");
                                        // TODO: IMPROVE WAY OF CALCULATING AGE
                                        DateFormat formatter = new SimpleDateFormat("yyyyMMdd");
                                        int d1 = Integer.parseInt(formatter.format(fecNacimientoTimeStamp.toDate()));
                                        int d2 = Integer.parseInt(formatter.format(Calendar.getInstance().getTime()));
                                        int age = (d2 - d1) / 10000;
                                        Alumno nuevoAlumno = new Alumno(nombre, apellido,curso, "prueba", age);
                                        crearFilaAlumno(nuevoAlumno);
                                    } catch (ClassCastException cce) {
                                        Log.d(TAG, "Error al castear o obtener data de: "+document.getId());
                                    }
                                }
                            } else {
                                statusTextView.setText("Ha ocurrido un error al cargar la información, intente de nuevo en unos minutos. En caso de continuar fallando, contacta con un administrador");
                            }
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            TextView statusTextView = AlumnosActivity.this.findViewById(R.id.statusText);
                            statusTextView.setText("Ha ocurrido un error al cargar la información, intente de nuevo en unos minutos. En caso de continuar fallando, contacta con un administrador");
                        }
                    });
        } else {
          Log.d(TAG, "No hay conectividad");
        }

        // CONTAINER DE LISTADO DE ALUMNOS
        LinearLayout alumnosContainer = this.findViewById(R.id.MainContentScrollLinearLayout);
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
        alumnoAge.setText(String.format(getString(R.string.edad_alumno), alumno.getEdad())); // formateado según strings.xml (recomendación del linter)

        // Agrego edad a layout de data del alumno
        alumnoRowDataLayout.addView(alumnoAge);

        // Agrego data a la row
        alumnoRow.addView(alumnoRowDataLayout);

        // Agrego fila al contenedor de alumnos
        LinearLayout alumnosContainer = this.findViewById(R.id.MainContentScrollLinearLayout);
        alumnosContainer.addView(alumnoRow);

    }
}