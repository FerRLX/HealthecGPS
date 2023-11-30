package mx.GPS.healthec;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Random;

public class MenuActivity extends AppCompatActivity {


    private ImageView perfil;

    private static final String FILE_NAME = "Recordatorios.txt";
    private TextView textView4;


    private TextView txtvFrase;

    private DatabaseReference mDataBase;

    private Random azar;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        int orientation=getResources().getConfiguration().orientation;
        if(orientation== Configuration.ORIENTATION_PORTRAIT){
            getSupportActionBar().hide();
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }else
            getSupportActionBar().show();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        textView4 = findViewById(R.id.textView4);
    }
    @Override
    protected void onResume() {
        super.onResume();
        updateTextView();
    }
    private void updateTextView() {
        // Lee el contenido del archivo de texto
        String contenidoArchivo = readTextFile();

        // Extrae el primer recordatorio del contenido del archivo
        String primerRecordatorio = "";
        String[] recordatorios = contenidoArchivo.split("\n");
        if (recordatorios.length > 0) {
            primerRecordatorio = recordatorios[0];
        }

        // Asigna el primer recordatorio al TextView o muestra un mensaje predeterminado
        if (!primerRecordatorio.isEmpty()) {
            textView4.setText(primerRecordatorio);
        } else {
            textView4.setText("No hay recordatorios disponibles");
        }
        perfil = findViewById(R.id.imgvPerfil);
        perfil.setImageResource(R.drawable.saludconsejo);
        txtvFrase = findViewById(R.id.txtvFrase);

        mDataBase = FirebaseDatabase.getInstance().getReference();

        azar = new Random();

       int fraseAzar = azar.nextInt(10) +1;



        mDataBase.child("frases").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if(snapshot.exists()){
                    // String consejo = snapshot.getValue().toString();
                    String frase = snapshot.child(fraseAzar+"").getValue().toString();
                    txtvFrase.setText(frase);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



    }

    public void btnModuloSueñoClick (View v){
        Intent intent = new Intent(MenuActivity.this, SleepActivity.class);
        startActivity(intent);
    }

    public void btnModuloRecetasClick (View v){
        startActivity(new Intent( MenuActivity.this, IngredientesActivity.class));
    }

    public void btnModuloRecordatorioClick(View v){
        startActivity(new Intent( MenuActivity.this, SplashRecordatorios.class));
    }

    public void btnModuloMeditacionClick(View v){
        startActivity(new Intent( MenuActivity.this, MeditacionActivity.class));
    }

    public void btnFraseClick (View v){
        Intent intent = new Intent(MenuActivity.this, ConsejosSaludables.class);
        startActivity(intent);
    }
    private String readTextFile() {
        StringBuilder stringBuilder = new StringBuilder();
        try {
            FileInputStream fis = openFileInput(FILE_NAME);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);
            String line;
            while ((line = br.readLine()) != null) {
                stringBuilder.append(line).append("\n");
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }

}