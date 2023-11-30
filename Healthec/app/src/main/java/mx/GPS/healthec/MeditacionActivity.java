package mx.GPS.healthec;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

public class MeditacionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meditacion);

        int orientation=getResources().getConfiguration().orientation;
        if(orientation== Configuration.ORIENTATION_PORTRAIT){
            getSupportActionBar().hide();
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }else
            getSupportActionBar().show();
    }
    //-------------------------------------------------------------------------
    public void btnSueño(View v){
        // Inicia una nueva actividad en Android utilizando un objeto Intent.
        startActivity(new Intent(MeditacionActivity.this,Splash_sueno.class));
    }


    public void btnAnsiedad(View v){
        // Inicia una nueva actividad en Android utilizando un objeto Intent.
        startActivity(new Intent(MeditacionActivity.this,Splash_estres.class));
    }
    //-------------------------------------------------------------------------
    public void btnRelajacion(View v){
        // Inicia una nueva actividad en Android utilizando un objeto Intent.
        startActivity(new Intent(MeditacionActivity.this,SplashRelajacion.class));
    }
}