package mx.GPS.healthec;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.res.Configuration;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class ConciliacionSueno extends AppCompatActivity {

    MediaPlayer mediaPlayer;
    RecyclerView rvLista;
    List<Sonido> listaSonidos;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conciliacion_sueno);

        int orientation=getResources().getConfiguration().orientation;
        if(orientation== Configuration.ORIENTATION_PORTRAIT){
            getSupportActionBar().hide();
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }else
            getSupportActionBar().show();

        //asigna referencias de vistas y se configura el layoutManager de el recycler view para utilizar un GridLayoutManager con una sola columna
        rvLista=findViewById(R.id.rvLista);
        rvLista.setLayoutManager(new GridLayoutManager(this,1));

        //Añade elementos a la lista sonidos, la cual tiene como atributos, nombre, sonidos, descripcion
        listaSonidos=new ArrayList<>();
        listaSonidos.add(new Sonido("Sonidos de la naturaleza","sonidoUno","Los sonidos de la naturaleza, el canto de los pájaros, el sonido del mar y el viento en los árboles, pueden ayudar a reducir el estrés y promover la relajación."));
        listaSonidos.add(new Sonido("Musica suave","sonidoDos","La música suave y tranquila, como la música clásica, la música ambiental o la música de meditación, puede ayudarte a reducir la ansiedad y promover la relajación."));
        listaSonidos.add(new Sonido("ASMR","sonidoTres","Las sensaciones autónomas meridianas del cerebro (ASMR, por sus siglas en inglés) son un fenómeno que se produce cuando escuchamos sonidos suaves y repetitivos, como susurros, crujidos o golpeteos."));
        listaSonidos.add(new Sonido("Sonidos blandos","sonidoCuatro","Los sonidos blancos, como el sonido de un ventilador, de la lluvia o el sonido de la estática, pueden ayudar a bloquear otros sonidos y promover un ambiente tranquilo y relajante."));
        listaSonidos.add(new Sonido("Sonidos de instrumentos musicales","sonidoCinco","Los sonidos de instrumentos musicales como el piano, la flauta o el violín, pueden ser relajantes y promover la tranquilidad."));

        RecyclerView.Adapter adapter=new AdaptadorSonidos(ConciliacionSueno.this,listaSonidos);
        rvLista.setAdapter(adapter);
        Toast.makeText(this, "Recuerda usar audifonos para una mejor experiencia", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onBackPressed() {
        if (mediaPlayer!=null){
            mediaPlayer.stop();
        }
        finish();
    }

}