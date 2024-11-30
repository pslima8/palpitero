package br.com.jrsoft.palpitero.ui.telas;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import br.com.jrsoft.palpitero.R;

public class SplashScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);
        Handler handle = new Handler();
        handle.postDelayed(new Runnable() {

            @Override
            public void run() {
                //Método que será executado uma vez.. Na abertura do app.
                Intent i = new Intent(SplashScreenActivity.this, EscolhaJogoActivity.class);
                startActivity(i);

                finish();
            }
        }, 3000);

    }
}
