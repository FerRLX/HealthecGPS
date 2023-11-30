package mx.GPS.healthec;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class EstiramientoEspalda  extends AppCompatActivity {
    Button btnSiguiente;
    ImageView imgGif;
    TextView descri,contador1;
    int[] gifIds = {R.raw.espaldauno, R.raw.espaldados, R.raw.espaldatres};
    String [] descripciones={"Coloca tus rodillas y tus brazos en el suelo, estas deben estar aliendas con tus rodillas y tus hombros, despues desplaza hacia atras tus caderas, y vuelve a la posicion inicial","Colocate en una silla, lleva tus hombros a hacia atras, y extiende tus brazos hacia atras de la cabeza","Colocate en una silla, y sientate de lado, apoya tus manos en el respaldo de la silla y gira suavemente hacia un lado"};
    int currentIndex = 0;
    CountDownTimer timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_estiramiento_espalda);

        int orientation=getResources().getConfiguration().orientation;
        if(orientation== Configuration.ORIENTATION_PORTRAIT){
            getSupportActionBar().hide();
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }else
            getSupportActionBar().show();

        btnSiguiente=findViewById(R.id.btnSiguente);
        imgGif=findViewById(R.id.imgGif);
        descri=findViewById(R.id.txtDescrip);
        contador1=findViewById(R.id.txtContador1);

        timer = new CountDownTimer(60000, 1000) {
            public void onTick(long millisUntilFinished) {
                // Actualizar el contador de tiempo
                descri.setText("Tiempo restante: " + millisUntilFinished / 1000 + " segundos");
            }

            public void onFinish() {
                // Cambiar al siguiente gif cuando el temporizador termina
                descri.setText("El tiempo ha terminado");
            }
        }.start();
        showGif(currentIndex);

        btnSiguiente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showNextGif();
            }
        });
    }
    private void showGif(int index) {
        // Obtener el ID del gif actual
        int gifId = gifIds[index];

        // Mostrar el gif en el ImageView
        Glide.with(this).asGif().load(gifId).into(imgGif);

        // Mostrar la descripción en el TextView
        contador1.setText(descripciones[index]);
    }

    private void showNextGif() {
        currentIndex++;

        if (currentIndex >= gifIds.length) {
            // Reiniciar el índice si ya se han mostrado todos los gifs
            startActivity(new Intent(EstiramientoEspalda.this,RelajacionVespertina.class));
        }

        // Mostrar el siguiente gif y descripción
        showGif(currentIndex);

        // Reiniciar el temporizador
        timer.cancel();
        timer.start();
    }
}