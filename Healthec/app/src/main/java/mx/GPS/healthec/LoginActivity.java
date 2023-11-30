package mx.GPS.healthec;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import mx.GPS.healthec.modelos.UserModel;

public class LoginActivity extends AppCompatActivity {
    //--------------------------------------------------------------------------------------------//
    //Referencia a los botones y otros controles en el layout
    Button btn_registrar, btn_ingresar;
    EditText edt_email, edt_password;
    ImageView google_login;

    FirebaseDatabase database;

    GoogleSignInOptions gso;
    GoogleSignInClient gsc;

    //--------------------------------------------------------------------------------------------//

    //METODO ONCREATE, SE EJECUTA CUNADO LOGINACTIVITY SE INICIA
    @Override
    //Llama al método onCreate() de la clase base Activity utilizando el parámetro savedInstanceState.
    //este método establece el diseño de la actividad y busca los elementos de la interfaz de
    // usuario para poder interactuar con ellos posteriormente en la aplicación.

    protected void onCreate(Bundle savedInstanceState) {
        int orientation=getResources().getConfiguration().orientation;
        if(orientation== Configuration.ORIENTATION_PORTRAIT){
            getSupportActionBar().hide();
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }else
            getSupportActionBar().show();
        super.onCreate(savedInstanceState);

        //Verifica si no hay una cuenta iniciada
        SharedPreferences prefs = getSharedPreferences("Account", MODE_PRIVATE);
        String savedEmail = prefs.getString("email", null);
        String savedPassword = prefs.getString("password", null);
        String savedKey = prefs.getString("key", null);

        if(savedEmail != null && savedPassword != null && savedKey != null){
            // los datos de inicio de sesión están guardados
            // se puede ir directamente a la actividad del menú
            Intent intent = new Intent(this, MenuActivity.class);
            startActivity(intent);
            finish(); // asegura que el usuario no pueda volver atrás a la pantalla de inicio de sesión
        }

        setContentView(R.layout.activity_login);

        //Se le asigna a las variables el elemento creado por la computadora en el layout---------//
        btn_ingresar = findViewById(R.id.btn_ingresar);
        btn_registrar = findViewById(R.id.btn_registrar);

        edt_email = findViewById(R.id.edt_email);
        edt_password = findViewById(R.id.edt_password);

        google_login = findViewById(R.id.google);

        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        gsc = GoogleSignIn.getClient(this, gso);

        //Listeners de los botones----------------------------------------------------------------//
        btn_ingresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                database = FirebaseDatabase.getInstance();
                DatabaseReference ref = database.getReference().child("usuarios");

                ref.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        UserModel usuario = new UserModel(1L, edt_email.getText().toString(),
                                edt_password.getText().toString());

                        for (DataSnapshot userSnapshot : snapshot.getChildren()) {
                            String email = userSnapshot.child("email").getValue(String.class);
                            String password = userSnapshot.child("password").getValue(String.class);
                            if (email.equals(usuario.getEmail()) && password.equals(usuario.getPassword())) {
                                // El usuario se encuentra en la base de datos
                                SharedPreferences prefs = getSharedPreferences("Account", MODE_PRIVATE);

                                SharedPreferences.Editor editor = prefs.edit();

                                editor.putString("email", email);
                                editor.putString("password", password);
                                editor.putString("key", userSnapshot.getKey());
                                editor.apply();

                                Toast.makeText(getApplicationContext(), "Bienvenido una vez más!:)", Toast.LENGTH_SHORT).show();
                                startActivity( new Intent(LoginActivity.this, MenuActivity.class));
                                finish();
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(getApplicationContext(), "El correo o la contraseña son incorrectos", Toast.LENGTH_SHORT).show();
                    }
                });
                /*//Se declara una instancia de DataBaseHealthec, que es una clase que maneja la base de datos
                // SQL utilizada por la aplicación.
                DataBaseHealthec dataBaseHealthec = new DataBaseHealthec(LoginActivity.this);


                //Se llama al método "addOne" de la instancia de DataBaseHealthec, pasando como argumento
                // la instancia de UserModel creada anteriormente. Este método intenta agregar el usuario a
                // la base de datos y devuelve un valor booleano que indica si la operación fue exitosa o no.
                boolean exist = dataBaseHealthec.exists(usuario);
                if (exist) {
                    //codigo para entrar al menu principal donde se encuentran todas las opciones
                    startActivity(new Intent(LoginActivity.this, MenuActivity.class));
                    Toast.makeText(LoginActivity.this, "Hay un error mano", Toast.LENGTH_SHORT).show();
                    finish();

                } else {
                    //codigo para representar que el usuario no existe
                    Toast.makeText(LoginActivity.this, "No existe ninguna cuenta con esos datos",
                            Toast.LENGTH_LONG).show();
                }*/
            }
        });
        //----------------------------------------------------------------------------------------//

        btn_registrar.setOnClickListener(new View.OnClickListener() {

            //METODO ONCLICK este método se encarga de crear un nuevo usuario con los valores
            // ingresados en la interfaz de usuario y agregarlo a la base de datos de la aplicación.
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, RegistroActivity.class);
                startActivity(intent);
            }
        });

        //----------------------------------------------------------------------------------------//

        google_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SignIn();
            }
        });

    }
    //--------------------------------------------------------------------------------------------//
    private void SignIn() {
        Intent intent = gsc.getSignInIntent();
        startActivityForResult(intent, 100);
    }
    //--------------------------------------------------------------------------------------------//
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 100) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                task.getResult(ApiException.class);
                HomeActivity();
            } catch (ApiException e) {
                Toast.makeText(this, "Error 1", Toast.LENGTH_SHORT).show();
            }
        }
    }
    //--------------------------------------------------------------------------------------------//
    private void HomeActivity() {
        finish();
        Intent intent = new Intent(LoginActivity.this, RegistroActivity.class);
        startActivity(intent);
    }
}