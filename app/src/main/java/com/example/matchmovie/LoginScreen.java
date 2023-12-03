package com.example.matchmovie;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class LoginScreen extends AppCompatActivity implements View.OnTouchListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_screen);

        Button btn_visitante = (Button) findViewById(R.id.btn_visitante);
        TextView register_text = (TextView) findViewById(R.id.register_text);

        register_text.setOnTouchListener(this);

        btn_visitante.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Log.v("arroz", "clicou né");
                Intent intent = new Intent(LoginScreen.this, MovieListActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
    }
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        Intent intent = new Intent(LoginScreen.this, RegisterScreen.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        return true;
    }
}