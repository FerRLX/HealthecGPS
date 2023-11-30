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

public class EstiramientoPiernas  extends AppCompatActivity {
    Button btnSiguiente;
    ImageView imgGif;
    TextView descri,contador1;
    int[] gifIds = {R.raw.piernasuno, R.raw.piernasdos, R.raw.piernatres};
    String [] descripciones={"Para hacerlo, siéntate en el suelo, cruza una pierna sobre la otra y gira lentamente el torso hacia la pierna cruzada. Mantén la posición durante 15-30 segundos y luego repite en el otro lado. Recuerda no forzar la posición y detenerte si sientes dolor.","Párate frente a una silla y coloca un pie en el asiento de la misma. Inclínate hacia adelante y agarra la punta del pie con las manos. Mantén la posición durante 10-15 segundos y luego cambia de pierna. Recuerda no forzar demasiado el estiramiento y detenerte si sientes dolor.","Para hacer el estiramiento de cuádriceps, párate derecho y levanta un pie hacia atrás, sosteniéndolo con la mano. Tira del pie hacia tu trasero tanto como puedas y mantén la posición durante 10-15 segundos. Repite con la otra pierna. Recuerda no forzar demasiado el estiramiento y detenerte si sientes dolor."};
    int currentIndex = 0;
    CountDownTimer timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_estiramiento_piernas);

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
            startActivity(new Intent(EstiramientoPiernas.this,RelajacionVespertina.class));
        }

        // Mostrar el siguiente gif y descripción
        showGif(currentIndex);

        // Reiniciar el temporizador
        timer.cancel();
        timer.start();
    }
}