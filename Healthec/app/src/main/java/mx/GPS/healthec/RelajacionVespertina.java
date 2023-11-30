package mx.GPS.healthec;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class RelajacionVespertina extends AppCompatActivity {
    RecyclerView rvlista;
    List<Ejercicio> listaEjercicio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_relajacion_vespertina);


        int orientation=getResources().getConfiguration().orientation;
        if(orientation== Configuration.ORIENTATION_PORTRAIT){
            getSupportActionBar().hide();
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }else
            getSupportActionBar().show();

        //asigna referencias de vistas y se configura el layoutManager de el recycler view para utilizar un GridLayoutManager con una sola columna
        rvlista=findViewById(R.id.rvlista);
        rvlista.setLayoutManager(new GridLayoutManager(this,1));

        //Añade elementos a la lista ejercicio, la cual tiene como atributos, nombre, ejericio, descripcion
        listaEjercicio=new ArrayList<>();
        listaEjercicio.add(new Ejercicio("Cuello",R.drawable.cuello, "Estos estiramiento de cuello ayuda a aliviar la tensión y rigidez en los músculos del cuello, mejorando la flexibilidad y la circulación sanguínea."));
        listaEjercicio.add(new Ejercicio("Hombros",R.drawable.hombros,"Estos estiramiento de hombros alivia la tensión y rigidez en los músculos del hombro, mejorando la flexibilidad y reduciendo el dolor y la incomodidad."));
        listaEjercicio.add(new Ejercicio("Espalda",R.drawable.espalda,"Estos estiramiento de espalda es beneficioso para aliviar la tensión y rigidez en la parte superior de la espalda, especialmente en los músculos del trapecio y los hombros."));
        listaEjercicio.add(new Ejercicio("Piernas",R.drawable.piernas,"Estos estiramiento de piernas es útil para mejorar la flexibilidad y la movilidad de las piernas y la cadera, y también para aliviar la tensión y rigidez en los músculos de la pierna."));

        RecyclerView.Adapter adapter=new ListElement(RelajacionVespertina.this,listaEjercicio);
        rvlista.setAdapter(adapter);

    }
    @Override
    public void onBackPressed() {
        finish();
    }

}