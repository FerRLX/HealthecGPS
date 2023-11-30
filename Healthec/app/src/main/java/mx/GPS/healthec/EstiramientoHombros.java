package mx.GPS.healthec;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class EstiramientoHombros extends AppCompatActivity {
    Button btnSiguiente;
    ImageView imgGif;
    TextView descri,contador1;
    int[] gifIds = {R.raw.pared, R.raw.cruzar, R.raw.girohombros};
    String [] descripciones={"El estiramiento de hombros con giro se realiza apoyándose en un poste, columna o barra vertical. Para hacerlo, extiende los brazos hacia adelante a la altura del hombro, agarra el poste con una mano y gira lentamente tu cuerpo hacia un lado, manteniendo el brazo estirado.","El estiramiento de hombros cruzado se realiza cruzando los brazos delante del cuerpo, agarrando los codos opuestos con las manos y elevando los hombros hacia los oídos al inhalar y luego bajándolos hacia abajo y hacia atrás al exhalar. Mantén la posición durante 15-30 segundos y luego suelta.","El estiramiento de hombros con rotación se realiza girando los hombros hacia adelante y hacia atrás para estirar los músculos de los hombros y la parte superior de la espalda"};
    int currentIndex = 0;
    CountDownTimer timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_estiramiento_hombros);

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
            startActivity(new Intent(EstiramientoHombros.this,RelajacionVespertina.class));
        }

        // Mostrar el siguiente gif y descripción
        showGif(currentIndex);

        // Reiniciar el temporizador
        timer.cancel();
        timer.start();
    }
}