package mx.GPS.healthec;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Transaction;

import mx.GPS.healthec.modelos.UserModel;

public class RegistroActivity extends AppCompatActivity {
    //--------------------------------------------------------------------------------------------//
    Button btn_registroAceptar;
    EditText edt_emailRegistro, edt_passwordRegistro, edt_nombreRegistro;

    FirebaseDatabase database;

    GoogleSignInOptions gso;
    GoogleSignInClient gsc;

    //--------------------------------------------------------------------------------------------//
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);
        int orientation=getResources().getConfiguration().orientation;
        if(orientation== Configuration.ORIENTATION_PORTRAIT){
            getSupportActionBar().hide();
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }else
            getSupportActionBar().show();
        btn_registroAceptar = findViewById(R.id.btn_registroAccept);
        edt_emailRegistro = findViewById(R.id.edt_correoRegistro);
        edt_passwordRegistro = findViewById(R.id.edt_passwordRegistro);
        edt_nombreRegistro = findViewById(R.id.edt_nombreRegistro);

        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        gsc = GoogleSignIn.getClient(this, gso);

        //Bases de datos
        DataBaseHealthec dataBaseHealthec = new DataBaseHealthec(RegistroActivity.this);

        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);

        /*if( account != null ){
            UserModel usuario;
            try{
                usuario = new UserModel(-1, account.getEmail(), null, account.getDisplayName());
                Toast.makeText(RegistroActivity.this, usuario.toString(), Toast.LENGTH_SHORT).show();
            }
            catch (Exception e){
                Toast.makeText(RegistroActivity.this, "Error con la cuenta de google",
                        Toast.LENGTH_SHORT).show();
                usuario = new UserModel(-1, "error", "error", "error");
            }

            boolean success = dataBaseHealthec.addOne(usuario);

            try{
                if(success){
                    finish();
                    startActivity( new Intent(RegistroActivity.this, MenuActivity.class));
                }
            }catch (Exception e){
                Toast.makeText(RegistroActivity.this, "No se pudo registrar correctamente",
                        Toast.LENGTH_LONG).show();
            }

        }*/
//--------------------------------------------------------------------------------------------//
        //Boton que implementa el registro de un nuevo usuario, se hace uso de la base de datos en Firebase para guardar los datos
        //y se guarda localmente en el dispositivo del usuario las credenciales para hacaer referencia en otras partes de la app
        btn_registroAceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("Firebase", "Botón Aceptar presionado");
                database = FirebaseDatabase.getInstance();//Se crea la instancia de la base de datos.
                DatabaseReference ref = database.getReference().child("usuarios"); //Se crea una referencia de la tabla usuarios
                ref.runTransaction(new Transaction.Handler() {
                    @Override
                    public Transaction.Result doTransaction(MutableData mutableData) {
                        // Se obtiene el dato del ultimo id en la tabla lastId

                        //Se crea el usuario con los campos de texto del layout
                        String email = edt_emailRegistro.getText().toString();
                        String password = edt_passwordRegistro.getText().toString();
                        String nombre = edt_nombreRegistro.getText().toString();

                        if (!TextUtils.isEmpty(email) && !TextUtils.isEmpty(password) && !TextUtils.isEmpty(nombre)) {
                            UserModel usuario = new UserModel(-1L, email, password, nombre);

                            DatabaseReference userRef = ref.push(); //se utiliza el método push() para crear un nuevo nodo con una clave única dentro de la referencia ref.

                            userRef.child("name").setValue(usuario.getNombre());
                            userRef.child("email").setValue(usuario.getEmail());
                            userRef.child("password").setValue(usuario.getPassword());

                            SharedPreferences prefs = getSharedPreferences("Account", MODE_PRIVATE);

                            SharedPreferences.Editor editor = prefs.edit();

                            editor.putString("email", usuario.getEmail());
                            editor.putString("password", usuario.getPassword());
                            editor.putString("key", userRef.getKey());
                            editor.apply();

                            return Transaction.success(mutableData);
                        } else {
                            return Transaction.abort();
                        }

                    }

                    //--------------------------------------------------------------------------------------------//
                    //Cuando se complete el registro se mandará a la pantalla del menu principal, de otro modo le
                    //aparecera al usuario un mensaje alertandole que no se pudo realizar el registro
                    @Override
                    public void onComplete(DatabaseError databaseError, boolean committed, DataSnapshot dataSnapshot) {
                        if (committed) {
                            Log.d("Firebase", "Transaction completed");
                            Intent intent = new Intent(RegistroActivity.this, MenuActivity.class);
                            startActivity(intent);
                            finish();

                        } else {
                            Log.d("Firebase", "Transaction aborted");

                            Toast.makeText(RegistroActivity.this, "No se pudo registrar correctamente",
                                    Toast.LENGTH_LONG).show();
                        }
                    }


                });

               /* try{
                boolean success = dataBaseHealthec.addOne(usuario);
                usuariosRef.child("usuario1").setValue(usuario);
                    if(success){
                        finish();
                        startActivity( new Intent(RegistroActivity.this, MenuActivity.class));
                    }
                }catch (Exception e){
                    Toast.makeText(RegistroActivity.this, "No se pudo registrar correctamente",
                            Toast.LENGTH_LONG).show();
                }*/
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity( new Intent(RegistroActivity.this, LoginActivity.class));
    }
}