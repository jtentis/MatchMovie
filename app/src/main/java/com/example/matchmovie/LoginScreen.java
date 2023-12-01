package com.example.matchmovie;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class LoginScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_screen);

        Button btn_visitante = (Button) findViewById(R.id.btn_visitante);

        btn_visitante.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Log.v("arroz", "clicou né");
                Intent intent = new Intent(LoginScreen.this, MovieListActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}