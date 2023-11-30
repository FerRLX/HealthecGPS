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

public class EstiramientosCuello extends AppCompatActivity {
    Button btnSiguiente;
    ImageView imgGif;
    TextView descri,contador1;
    int[] gifIds = {R.raw.arriba, R.raw.lados, R.raw.giro};
    String [] descripciones={"La flexión y extensión del cuello es un estiramiento en el que se mueve la cabeza hacia el pecho y luego hacia atrás para estirar los músculos del cuello y la parte superior de la espalda. Se debe realizar de manera lenta y suave para evitar lesiones.","El estiramiento consiste en inclinar suavemente la cabeza hacia un lado, llevando la oreja hacia el hombro correspondiente, manteniendo la posición durante 10-15 segundos","El estiramiento de giro de cabeza implica girar lentamente la cabeza hacia un lado y mantener la posición durante 10-15 segundos, luego repetir en el otro lado. Se debe realizar de manera suave y lenta para evitar lesiones y detenerse si hay dolor o incomodidad."};
    int currentIndex = 0;
    CountDownTimer timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_estiramientos_cuello);

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
            startActivity(new Intent(EstiramientosCuello.this,RelajacionVespertina.class));
        }

        // Mostrar el siguiente gif y descripción
        showGif(currentIndex);

        // Reiniciar el temporizador
        timer.cancel();
        timer.start();
    }
}