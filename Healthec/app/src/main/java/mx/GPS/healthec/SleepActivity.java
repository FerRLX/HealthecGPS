package mx.GPS.healthec;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.Telephony;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.checkerframework.checker.units.qual.A;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class SleepActivity extends AppCompatActivity {
    //--------------------------------------------------------------------------------------------//
    ImageButton btn_time; // Botón para seleccionar la hora del sueño
    ToggleButton tgbtn_actividad; // Botón de alternancia para la actividad
    TextView txv_meta; // TextView para mostrar la meta de sueño
    int hourSleep, minuteSleep; // Variables para almacenar la hora y el minuto seleccionados

    String savedKey, savedPassword, savedEmail; // Variables para almacenar los datos del usuario

    FirebaseDatabase database; // Instancia de la base de datos de Firebase

    //--------------------------------------------------------------------------------------------//
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sleep);

        getSupportActionBar().hide();

        // Obtener referencias a los elementos de la interfaz de usuario
        btn_time = findViewById(R.id.btnSleepHorario);
        tgbtn_actividad = findViewById(R.id.tgbtnSleep);
        txv_meta = findViewById(R.id.txvMetaSueno);

        // Obtener los datos del usuario almacenados en SharedPreferences
        SharedPreferences prefs = getSharedPreferences("Account", MODE_PRIVATE);
        savedEmail = prefs.getString("email", null);
        savedPassword = prefs.getString("password", null);
        savedKey = prefs.getString("key", null);

        // Inicializar el gráfico de barras
        BarChart barChart = findViewById(R.id.barChart);

        // Inicializar la instancia de la base de datos de Firebase
        database = FirebaseDatabase.getInstance();

        // Obtener una referencia a la ubicación de los datos de sueño del usuario en la base de datos
        DatabaseReference timeRef = database.getReference().child("usuarios").child(savedKey).child("registroSueño");

        // Agregar un listener para detectar cambios en los datos de sueño
        timeRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Borrar los datos existentes en el gráfico de barras
                barChart.clear();

                // Recorrer los datos en Firebase y agregar las entradas al gráfico de barras
                ArrayList<BarEntry> entries = new ArrayList<>();
                int index = 0;
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Integer horas = snapshot.child("horas").getValue(Integer.class);
                    Float minutosDouble = snapshot.child("minutos").getValue(Float.class);
                    float minutos = (minutosDouble != null) ? Float.valueOf(minutosDouble) : 0;
                    entries.add(new BarEntry(index, horas));
                    entries.add(new BarEntry(index + 1, minutos));
                    index += 2;
                }


                // Crear el conjunto de datos y configurar el gráfico de barras
                BarDataSet dataSet = new BarDataSet(entries, "Tiempo");
                dataSet.setColors(ColorTemplate.COLORFUL_COLORS);
                BarData barData = new BarData(dataSet);
                barChart.setData(barData);
                barChart.invalidate();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Manejar el error de Firebase, si es necesario
            }
        });

    }
    //--------------------------------------------------------------------------------------------//
    // Método para mostrar un diálogo de selección de hora
    public void popTimePicker(View view) {
        TimePickerDialog.OnTimeSetListener onTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                hourSleep = selectedHour;
                minuteSleep = selectedMinute;

                // Mostrar la hora seleccionada en el TextView
                txv_meta.setText(String.format(Locale.getDefault(), "%02d:%02d", hourSleep, minuteSleep));
            }
        };

        int style = AlertDialog.THEME_HOLO_DARK;

        // Crear y mostrar el diálogo de selección de hora
        TimePickerDialog timePickerDialogOne = new TimePickerDialog(this, style, onTimeSetListener, hourSleep, minuteSleep, true);
        timePickerDialogOne.setTitle("Selecciona el número de horas que deseas dormir");
        timePickerDialogOne.show();

        AlertDialog.Builder builder = new AlertDialog.Builder( this );
        builder.setTitle("Importante").setMessage("Para poder hacer uso efectivo de esta\n" +
                                                  "fucnión, coloca boca abajo el celular\n" +
                                                  "para indicar que iniciaste tu periodo\n" +
                                                  "de sueño.").setIcon(R.drawable.alerta).setPositiveButton("Entendido",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        }).
                create().show();
    }
    //--------------------------------------------------------------------------------------------//
    // Método para iniciar un servicio relacionado con el sueño
    public void startService(View v) {
        long durationSeconds = ((long) hourSleep * 3600) + (long) (minuteSleep * 60);
        tgbtn_actividad.performClick();
        // Crear un Intent para iniciar el servicio y pasar datos adicionales a través de extras
        Intent serviceIntent = new Intent(this, AcelerometerService.class);
        serviceIntent.putExtra("duration", durationSeconds);
        serviceIntent.putExtra("userKey", savedKey);
        startService(serviceIntent);
    }

}
