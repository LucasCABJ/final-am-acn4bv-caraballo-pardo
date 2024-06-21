package com.davinci.sge_luju;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.LinearLayout;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.davinci.sge_luju.ui.login.LoginActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mAuth = FirebaseAuth.getInstance();
        this.mAuth.signOut();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mAuth.getCurrentUser() != null) {
            setContentView(R.layout.activity_main);
            EdgeToEdge.enable(this);
            ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
                Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
                v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
                return insets;
            });
            ImageView mainLogOutBtn = this.findViewById(R.id.logoutBtn);
            mainLogOutBtn.setOnClickListener((v) -> {
                this.mAuth.signOut();
                this.recreate();
            });
            LinearLayout alumnosCard = this.findViewById(R.id.CardAlumnos);
            alumnosCard.setOnClickListener(this::goToAlumnosView);
        } else {
            setContentView(R.layout.activity_main_no_user);
            EdgeToEdge.enable(this);
            ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
                Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
                v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
                return insets;
            });
            Button mainLogInBtn = this.findViewById(R.id.mainLogInBtn);
            mainLogInBtn.setOnClickListener((v) -> {
                Intent nextView = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(nextView);
            });
        }
    }

    private void goToAlumnosView(View view) {
        Intent nextView = new Intent(this, AlumnosActivity.class);
        startActivity(nextView);
    }
}