package com.davinci.sge_luju;

import static android.content.ContentValues.TAG;

import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
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
    private void mostrarEstadoDeCarga(boolean mostrando, String mensaje) {
        LinearLayout statusContainerView = findViewById(R.id.statusContainer);
        statusContainerView.setVisibility(mostrando ? View.VISIBLE : View.GONE);
        TextView statusText = statusContainerView.findViewById(R.id.statusText);
        statusText.setVisibility(mostrando ? View.VISIBLE : View.GONE);
        statusText.setText(mensaje);
        ProgressBar statusProgressBar = statusContainerView.findViewById(R.id.progressBar);
        statusProgressBar.setVisibility(mostrando ? View.VISIBLE : View.GONE);
    }

    private void cargarAlumno(@NonNull Alumno alumno) {
        this.textViewNombreAlumno.setText(alumno.getFullName());

        String imageUrl = alumno.getImageURL();
        ImageView imageView = new ImageView(this);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        layoutParams.setMargins(
                20,
                20,
                20,
                20);
        imageView.setLayoutParams(layoutParams);
        imageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);

        LinearLayout mainContentLayout = findViewById(R.id.MainContentScrollLinearLayout);
        mainContentLayout.addView(imageView);

        cargarImagenAlumno(imageView, imageUrl);

        // Crear y añadir los TextView
        // Nombre
        addTextViewToLayout(mainContentLayout, alumno.getNombre());
        // Apellido
        addTextViewToLayout(mainContentLayout, alumno.getApellido());
        // Curso
        addTextViewToLayout(mainContentLayout, alumno.getCurso());
        // Edad
        addTextViewToLayout(mainContentLayout, getString(R.string.edad_alumno, alumno.getEdad()));

    }

    /*private void cargarImagenAlumno(ImageView imageView, String imageUrl) {
        if (imageUrl != null && !imageUrl.isEmpty()) {
            try {
                Glide.with(this).load(imageUrl).into(imageView);
            } catch (Exception e) {
                imageView.setImageResource(R.drawable.logoescuela); // Imagen predeterminada en caso de error
            }
        } else {
            imageView.setImageResource(R.drawable.logoescuela); // Imagen predeterminada si no hay URL válida
        }
    }*/

    // Método auxiliar para crear y añadir TextView
    private void addTextViewToLayout(LinearLayout layout, String text) {
        TextView textView = new TextView(this);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        textView.setLayoutParams(params);
        textView.setText(text);
        textView.setTextSize(32); // Doble del tamaño por defecto (16sp)
        textView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER); // Centrar horizontalmente
        params.gravity = Gravity.CENTER_HORIZONTAL; // Centrar horizontalmente dentro del layout
        layout.addView(textView);
    }

    private void cargarImagenAlumno(ImageView imageView, String imageUrl) {
        if (imageUrl != null && !imageUrl.isEmpty()) {
            try {
                int borderColor = getResources().getColor(R.color.border_color); // Define el color del borde en tu archivo de recursos
                int borderWidth = getResources().getDimensionPixelSize(R.dimen.border_width); // Define el ancho del borde en tu archivo de recursos

                Glide.with(this)
                        .load(imageUrl)
                        .apply(RequestOptions.bitmapTransform(new CircleCrop())) // Para hacer la imagen circular
                        .into(imageView);
            } catch (Exception e) {
                imageView.setImageResource(R.drawable.logoescuela); // Imagen predeterminada en caso de error
            }
        } else {
            imageView.setImageResource(R.drawable.logoescuela); // Imagen predeterminada si no hay URL válida
        }
    }

}