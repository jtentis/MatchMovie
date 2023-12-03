package com.example.matchmovie;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.w3c.dom.Text;

public class ProfileScreen extends AppCompatActivity {

    EditText emailUsuario;
    FirebaseAuth auth;
    Button logout;
    TextView displayNome;
    FirebaseUser usuario;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_screen);

        emailUsuario = findViewById(R.id.user);
        auth = FirebaseAuth.getInstance();
        logout = findViewById(R.id.btn_logout);
        displayNome = findViewById(R.id.texto_pic);
        usuario = auth.getCurrentUser();

        if(usuario == null){
            Intent intent = new Intent(ProfileScreen.this, LoginScreen.class);
            startActivity(intent);
            finish();
        }else{
            displayNome.setText(usuario.getEmail());
            emailUsuario.setHint(usuario.getEmail());
        }

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(ProfileScreen.this, LoginScreen.class);
                startActivity(intent);
                finish();
                Toast.makeText(ProfileScreen.this, "Usuário deslogado com sucesso!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}