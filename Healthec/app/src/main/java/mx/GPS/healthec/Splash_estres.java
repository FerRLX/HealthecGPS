package mx.GPS.healthec;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;

public class Splash_estres extends AppCompatActivity {
    private static int SPLASH_TIME_OUT = 12000;
    private TextView txt1;
    private TextView txt2;
    private static final int DELAY = 6000; // 5 segundos
    Button btnOmitir;
    private Handler handler;
    private Runnable runnable;
    private Runnable runnable2;
    //-------------------------------------------------------------------------
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Intent mainIntent = new Intent(Splash_estres.this, AlivioEstres.class);
        int orientation = getResources().getConfiguration().orientation;
        if (orientation == Configuration.ORIENTATION_PORTRAIT) {
            getSupportActionBar().hide();
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        } else
            getSupportActionBar().show();
        super.onCreate(savedInstanceState);
        //asigna referencias de vistas
        setContentView(R.layout.activity_splash_estres);
        txt1 = findViewById(R.id.txt1);
        txt2 = findViewById(R.id.txt2);
        btnOmitir = findViewById(R.id.btnOmitir);
        //establece la visibilidad de los textView
        txt1.setVisibility(View.VISIBLE);
        txt2.setVisibility(View.GONE);

        //-------------------------------------------------------------------------
        //en esta parte establece que a los 5 segundos de iniciar la actividad los text view cambiaran su visibilidad
        handler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {
                txt1.setVisibility(View.GONE);
                txt2.setVisibility(View.VISIBLE);
            }
        };
        handler.postDelayed(runnable, DELAY);
        //-------------------------------------------------------------------------
        //establece que al terminar el tiempo del splash se inicia una actividad que es MainIntent
        runnable2 = new Runnable() {
            @Override
            public void run() {
                startActivity(mainIntent);
                finish();
            }
        };
        handler.postDelayed(runnable2, SPLASH_TIME_OUT);
        //-------------------------------------------------------------------------
       //Establece que el boton aparecera a los 3 segundos de iniciar la actividad
        runnable = new Runnable() {
            @Override
            public void run() {
                btnOmitir.setVisibility(View.VISIBLE);
                startAnimation();
            }
        };
        handler.postDelayed(runnable, 3000);
        //-------------------------------------------------------------------------
        btnOmitir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(mainIntent);
                handler.removeCallbacks(runnable2);
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        handler.removeCallbacks(runnable);
        handler.removeCallbacks(runnable2);
    }

    //-------------------------------------------------------------------------
    private void startAnimation() {
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.slide);
        btnOmitir.startAnimation(animation);

        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                // La animación ha comenzado
                animation.start();
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                // La animación ha terminado
                animation.cancel();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
                // La animación se repite
                animation.reset();
            }
        });
    }
}