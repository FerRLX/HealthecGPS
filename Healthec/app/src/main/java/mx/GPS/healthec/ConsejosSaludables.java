package mx.GPS.healthec;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ConsejosSaludables extends AppCompatActivity {

    TextView txtvConsejo;

    ImageView imgvConsejo;

    ProgressBar prgsbImagenes;

    private DatabaseReference mDataBase;

    static int idSwitch=1;
    static int progess = 0;

    static int cont=1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consejos_saludables);

        txtvConsejo = (TextView)findViewById( R.id.txtvConsejo );
        imgvConsejo = (ImageView) findViewById(R.id.imgvConsejo);
        prgsbImagenes = findViewById(R.id.prgsbImagenes);

        mDataBase = FirebaseDatabase.getInstance().getReference();



        mDataBase.child("consejos").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if(snapshot.exists()){
                    // String consejo = snapshot.getValue().toString();
                    String consejo = snapshot.child(cont+"").getValue().toString();
                    txtvConsejo.setText("Consejo :  "+ consejo);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



    }

    public void btnSiguienteClick (View v){



        cont+=1;

        if(cont >= 12)
            cont = 1;

        mDataBase.child("consejos").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if(snapshot.exists()){
                    // String consejo = snapshot.getValue().toString();
                    String consejo = snapshot.child(cont+"").getValue().toString();
                    txtvConsejo.setText("Consejo : "+ consejo);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        progess+=10;
        idSwitch++;



        prgsbImagenes.setProgress(progess,true);

        if(progess==110){
            progess=0;
            idSwitch = 1;
            prgsbImagenes.setProgress(progess,true);
        }
        switch (idSwitch){

            case 1:  imgvConsejo.setImageResource(R.drawable.saludconsejo);
                break;
            case 2:  imgvConsejo.setImageResource(R.drawable.saludconsejo2);
                break;
            case 3:  imgvConsejo.setImageResource(R.drawable.saludconsejo3);
                break;
            case 4:  imgvConsejo.setImageResource(R.drawable.saludconsejo4);
                break;
            case 5:  imgvConsejo.setImageResource(R.drawable.saludconsejo5);
                break;
            case 6:  imgvConsejo.setImageResource(R.drawable.saludconsejo6);
                break;
            case 7:  imgvConsejo.setImageResource(R.drawable.saludconsejo7);
                break;
            case 8:  imgvConsejo.setImageResource(R.drawable.saludconsejo8);
                break;
            case 9:  imgvConsejo.setImageResource(R.drawable.saludconsejo9);
                break;
            case 10:  imgvConsejo.setImageResource(R.drawable.saludconsejo10);
                break;
            case 11:  imgvConsejo.setImageResource(R.drawable.saludconsejo11);
                break;


        }


    }

}