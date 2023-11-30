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

public class SplashRelajacion extends AppCompatActivity {
    private static int SPLASH_TIME_OUT = 6000;
    private TextView txt1;
    Button btnOmitir;
    private Handler handler, handler2;
    private Runnable runnable,runnable2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Intent mainIntent = new Intent(SplashRelajacion.this, RelajacionVespertina.class);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_relajacion);

        int orientation=getResources().getConfiguration().orientation;
        if(orientation== Configuration.ORIENTATION_PORTRAIT){
            getSupportActionBar().hide();
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }else
            getSupportActionBar().show();
        txt1 = findViewById(R.id.txt1);
        btnOmitir = findViewById(R.id.btnOmitir);
        handler = new Handler();
        handler2 = new Handler();
        runnable2 = new Runnable() {
            @Override
            public void run() {
                txt1.setVisibility(View.VISIBLE);
                startActivity(mainIntent);
            }
        };
        handler2.postDelayed(runnable2, SPLASH_TIME_OUT);

        runnable = new Runnable() {
            @Override
            public void run() {
                btnOmitir.setVisibility(View.VISIBLE);
                startAnimation();
            }
        };
        handler.postDelayed(runnable, 3000);

        btnOmitir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(mainIntent);
                handler2.removeCallbacks(runnable2);
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        handler.removeCallbacks(runnable);
        handler2.removeCallbacks(runnable2);
    }

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