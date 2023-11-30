package mx.GPS.healthec;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.SearchView;


import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

public class RecordatoriosActivity extends AppCompatActivity implements AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener {
    private Button miInfoBtn, citasBtn, medicamentosBtn;
    private TextView titleTxt;
    private ListView list;
    private TextView agregaractividad;
    private EditText actividad;
    private List<Modelo>mLista= new ArrayList<>();
    Modelo modelo;
    ListAdapter mAdapter;
    String nomarchivo;



    private static final String[] MEDICAMENTOS = {
            "Ibuprofeno - se usa para aliviar el dolor, la fiebre y la inflamación.\n",
            "Paracetamol - se usa para aliviar el dolor y la fiebre.\n",
            "Aspirina - se usa para aliviar el dolor, la fiebre y la inflamación. También se puede usar para prevenir ataques cardíacos y accidentes cerebrovasculares.\n",
            "Amoxicilina - se usa para tratar infecciones bacterianas, como la neumonía, la faringitis estreptocócica y las infecciones del oído, la nariz y la garganta.\n",
            "Omeprazol - se usa para tratar el reflujo gastroesofágico (ERGE), úlceras gástricas y duodenales, y otros trastornos gastrointestinales.\n",
            "Diazepam - se usa para tratar la ansiedad, el insomnio, los espasmos musculares y las convulsiones.\n",
            "Clonazepam - se usa para tratar la epilepsia y los trastornos de ansiedad, como el trastorno de pánico.\n",
            "Ketorolaco - se usa para aliviar el dolor moderado a intenso, como el dolor de cabeza, el dolor menstrual y el dolor después de una cirugía o lesión.\n",
            "Atorvastatina - se usa para reducir el colesterol en la sangre.\n",
            "Metformina - se usa para tratar la diabetes tipo 2.\n",
            "Metoprolol - se usa para tratar la presión arterial alta y la insuficiencia cardíaca.\n",
            "Sertralina - se usa para tratar la depresión y los trastornos de ansiedad.\n",
            "Alprazolam - se usa para tratar la ansiedad y los trastornos de pánico.\n",
            "Losartan - se usa para tratar la presión arterial alta y la insuficiencia cardíaca.\n",
            "Ondansetrón - se usa para prevenir las náuseas y los vómitos después de la quimioterapia o la cirugía.\n",
            "Furosemida - se usa para tratar la retención de líquidos en pacientes con insuficiencia cardíaca, cirrosis hepática y otros trastornos médicos.\n",
            "Lorazepam - se usa para tratar la ansiedad y los trastornos de pánico.\n",
            "Tramadol - se usa para aliviar el dolor moderado a intenso.\n"
    };

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recordatorios);
        agregaractividad= findViewById(R.id.agregaract);
        actividad=findViewById(R.id.act);
        list = findViewById(R.id.lista);
        list.setOnItemClickListener(this);
        list.setOnItemLongClickListener(this);

        recuperar();

        int orientation=getResources().getConfiguration().orientation;
        if(orientation== Configuration.ORIENTATION_PORTRAIT){
            getSupportActionBar().hide();
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }else
            getSupportActionBar().show();


        mAdapter = new  ListAdapter(RecordatoriosActivity.this, R.layout.item_row,mLista);
        list.setAdapter(mAdapter);


        agregaractividad.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                String actividadText = actividad.getText().toString().trim();

                if (TextUtils.isEmpty(actividadText)) {
                    Toast.makeText(RecordatoriosActivity.this, "Debes ingresar un recordatorio válido", Toast.LENGTH_SHORT).show();
                } else {
                    mLista.add(new Modelo(list.getCount() + 1 + "", actividadText));
                    mAdapter = new ListAdapter(RecordatoriosActivity.this, R.layout.item_row, mLista);
                    list.setAdapter(mAdapter);
                    nomarchivo = "Recordatorios.txt";

                    grabar();
                    actividad.setText("");
                }
            }
        });

        // Enlazar vistas con variables
        medicamentosBtn = findViewById(R.id.btnMedicamentos);
        titleTxt = findViewById(R.id.textView2);


        // Definir listener para el botón "Medicamentos"
        Button btnMedicamentos = findViewById(R.id.btnMedicamentos);

        btnMedicamentos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mostrarDialogoMedicamentos();
            }
        });
    }

    private void mostrarDialogoMedicamentos() {
        AlertDialog.Builder builder = new AlertDialog.Builder(RecordatoriosActivity.this);
        builder.setTitle("Selecciona un medicamento");

        final ListView listView = new ListView(RecordatoriosActivity.this);
        builder.setView(listView);

        final ArrayAdapter<String> adapter = new ArrayAdapter<>(RecordatoriosActivity.this, android.R.layout.simple_list_item_1, MEDICAMENTOS);
        listView.setAdapter(adapter);

        final DialogInterface.OnClickListener clickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String selectedMedicamento = adapter.getItem(which);
                Toast.makeText(RecordatoriosActivity.this, "Has seleccionado: " + selectedMedicamento, Toast.LENGTH_SHORT).show();
            }
        };

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                clickListener.onClick(null, position);
            }
        });

        final SearchView searchView = new SearchView(RecordatoriosActivity.this);
        builder.setCustomTitle(searchView);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return true;
            }
        });

        builder.setPositiveButton("Cancelar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        builder.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                // Restaurar la lista completa de medicamentos cuando se cancela la búsqueda
                adapter.getFilter().filter("");
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }




    private void recuperar(){
        boolean enco = false;
        String nomarchivo="Recordatorios.txt";

        String[] archivos = fileList();
        for(int f = 0; f<archivos.length;f++)
            if (nomarchivo.equals(archivos[f]))
                enco=true;
        if(enco==true){
            try{
                InputStreamReader archivo = new InputStreamReader(openFileInput(nomarchivo));
                BufferedReader br = new BufferedReader(archivo);
                String linea = br.readLine();

                while (linea != null){
                    mAdapter=new ListAdapter(RecordatoriosActivity.this,R.layout.item_row,mLista);
                    list.setAdapter(mAdapter);

                    mLista.add(new Modelo(list.getCount()+1+"",linea));
                    mAdapter=new ListAdapter(RecordatoriosActivity.this,R.layout.item_row,mLista);
                    list.setAdapter(mAdapter);

                    linea=br.readLine();
                }
                br.close();
                archivo.close();
            }catch (IOException e){
            }
        }else
        {
            Toast.makeText(this,"No hay recordatorios", Toast.LENGTH_SHORT).show();
        }
    }


    private void grabar() {
        try {
            OutputStreamWriter archivo = new OutputStreamWriter(openFileOutput(nomarchivo, Activity.MODE_APPEND));  // Utiliza MODE_APPEND para agregar contenido al final del archivo
            archivo.write(actividad.getText().toString());
            archivo.write("\n");  // Agrega una nueva línea después de cada elemento para separarlos
            archivo.flush();
            archivo.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Toast mensaje = Toast.makeText(this, "Los Datos Fueron Grabados", Toast.LENGTH_SHORT);
        mensaje.show();
    }


    public void grabarEliminar() {
        if (nomarchivo == null) {
            nomarchivo = "Recordatorios.txt"; // Valor predeterminado
        }

        try {
            OutputStreamWriter archivo = new OutputStreamWriter(openFileOutput(nomarchivo, Activity.MODE_PRIVATE));
            for (Modelo modelo : mLista) {
                archivo.write(modelo.getNombreactividad());
                archivo.write("\n");  // Agrega una nueva línea después de cada elemento para separarlos
            }
            archivo.flush();
            archivo.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        String nombreActividad = mAdapter.getItem(position).getNombreactividad();
        Intent splashIntent = new Intent(this, SplashRecuerda.class);
        splashIntent.putExtra("nombre", nombreActividad);
        startActivityForResult(splashIntent, 1);
    }


    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Elige la función deseada: ")
                .setItems(new CharSequence[]{"EDITAR", "ELIMINAR"}, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                editarElementoPorLongClick(position);
                                break;
                            case 1:
                                eliminarElementoPorLongClick(position);
                                break;
                        }
                    }
                })
                .setNegativeButton("Cancelar", null)
                .create()
                .show();
        return true;
    }


    protected void onActivityResult(int requestCode, int resultCode,@Nullable Intent data)
    {
        super.onActivityResult(requestCode,resultCode,data);

        if(requestCode==1 && resultCode==RESULT_OK)
        {

        }
    }
    private void eliminarElementoPorLongClick(int posicion) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Eliminar elemento")
                .setMessage("¿Deseas eliminar este elemento?")
                .setPositiveButton("Eliminar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mLista.remove(posicion);
                        mAdapter.notifyDataSetChanged();
                        Toast.makeText(RecordatoriosActivity.this, "Elemento eliminado", Toast.LENGTH_SHORT).show();
                        grabarEliminar();
                    }
                })
                .setNegativeButton("Cancelar", null)
                .create()
                .show();
    }

    private void editarElementoPorLongClick(int posicion) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Editar elemento");

        // Crear un nuevo EditText para ingresar el nuevo nombre de la actividad
        final EditText editText = new EditText(this);
        editText.setText(mLista.get(posicion).getNombreactividad());
        builder.setView(editText);

        builder.setPositiveButton("Guardar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String nuevoNombre = editText.getText().toString();
                mLista.get(posicion).setNombreactividad(nuevoNombre);
                mAdapter.notifyDataSetChanged();
                Toast.makeText(RecordatoriosActivity.this, "Elemento editado", Toast.LENGTH_SHORT).show();
                grabarEliminar();
            }
        });

        builder.setNegativeButton("Cancelar", null);

        builder.create().show();
    }



}



