package mx.GPS.healthec;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

import mx.GPS.healthec.modelos.UserModel;

public class DataBaseHealthec extends SQLiteOpenHelper {

    //En esta seccion se definen las costantes que se utilizaran para referirnos a la tabla y las columnas de la base de datos
    //-------------------------------------------------------------------------
    // En esta parte definimos la constantes a utilizar en la tabla usuario y sus columnas
    public static final String TABLA_USUARIO = "TABLA_USUARIO";
    public static final String COLUMNA_USUARIO_CORREO = "USUARIO_CORREO";
    public static final String COLUMNA_USUARIO_CLAVE = "USUARIO_CLAVE";
    public static final String COLUMNA_USUARIO_ID = "USUARIO_ID";
    //-----------------------------------------------------------------------
    //En esta parte definimos la constantes a utilizar en la tabla recodartorios y sus columnas
    public static final String TABLA_RECORDATORIOS = "TABLA_RECORDATORIOS";
    public static final String COLUMNA_RECORDATORIOS_CLINICA = "COLUMNA_RECORDATORIOS_CLINICA";
    public static final String COLUMNA_RECORDATORIOS_MEDICO = "COLUMNA_RECORDATORIOS_MEDICOS";
    public static final String COLUMNA_RECORDATORIOS_FECHAHORA = "COLUMNA_RECORDATORIOS_FECHAHORA";
    public static final String COLUMNA_RECORDATORIOS_ID = "COLUMNA_RECORDATORIOS_ID";
    public static final String COLUMNA_HORARIOSUEÑO_ID = "COLUMNA_HORARIOSUEÑO_ID";
    //-----------------------------------------------------------------------
    //En esta parte definimos la constantes a utilizar en la tabla horario, sueño y sus columnas
    public static final String TABLA_HORARIOSUEÑO = "TABLA_HORARIOSUEÑO ";
    public static final String COLUMNA_HORARIOSUEÑO_DIA = "COLUMNA_HORARIOSUEÑO_DIA";
    public static final String COLUMNA_HORARIOSUEÑO_HORA = "COLUMNA_HORARIOSUEÑO_HORA";
    //-----------------------------------------------------------------------
    //En esta parte definimos la constantes a utilizar en la tabla recetarios y sus columnas
    public static final String TABLA_RECETAS = "TABLA_RECETAS";
    public static final String COLUMNA_RECETAS_ID = "COLUMNA_RECETAS_ID";
    public static final String COLUMNA_RECETAS_TITULO = "COLUMNA_RECETAS_TITULO";
    public static final String COLUMNA_RECETAS_PASOS = "COLUMNA_RECETAS_PASOS";
    public static final String COLUMNA_RECETAS_INGREDIETNES = "COLUMNA_RECETAS_INGREDIETNES";
    public static final String COLUMNA_RECETAS_IMAGEN = "COLUMNA_RECETAS_IMAGEN";
    //-----------------------------------------------------------------------
    //En esta parte definimos la constantes a utilizar en la tabla consejos y sus columnas
    public static final String TABLA_CONSEJOS = "TABLA_CONSEJOS";
    public static final String COLUMNA_CONSEJOS_ID = "COLUMNA_CONSEJOS_ID";
    public static final String COLUMNA_CONSEJOS_DESCRIPCION = "COLUMNA_CONSEJOS_DESCRIPCION";
    public static final String COLUMNA_USUARIO_NOMBRE = "COLUMNA_USUARIO_NOMBRE";



    public DataBaseHealthec(@Nullable Context context) {
        super(context, "healthec.db", null, 1);
    }

    //Esto es llamado la primera vez que la base de datos es accedida. Aqui va el codigo para crear
    //una nueba db
    @Override
    public void onCreate(SQLiteDatabase db) {
        //Aqui se crean las tablas de la base de datos y se definen los tipos de datos de las columnas
        String createTableUsuario = "CREATE TABLE "+ TABLA_USUARIO + " (" + COLUMNA_USUARIO_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMNA_USUARIO_NOMBRE + " TEXT, " + COLUMNA_USUARIO_CORREO + " TEXT, " + COLUMNA_USUARIO_CLAVE + " TEXT)";
        String createTableRecordatorios =  "CREATE TABLE "+ TABLA_RECORDATORIOS + " (" + COLUMNA_RECORDATORIOS_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMNA_RECORDATORIOS_MEDICO + " TEXT, " + COLUMNA_RECORDATORIOS_FECHAHORA + " TEXT,"+COLUMNA_RECORDATORIOS_CLINICA+"TEXT)";
        String createTableRecetarios = "CREATE TABLE " + TABLA_RECETAS + " (" + COLUMNA_RECETAS_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMNA_RECETAS_TITULO + " TEXT, " + COLUMNA_RECETAS_PASOS + " TEXT, " + COLUMNA_RECETAS_INGREDIETNES + " TEXT," + COLUMNA_RECETAS_IMAGEN +"INT)";
        String createTableHorarioSueño = "CREATE TABLE " + TABLA_HORARIOSUEÑO + "( " + COLUMNA_HORARIOSUEÑO_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMNA_HORARIOSUEÑO_DIA + " TEXT, " + COLUMNA_HORARIOSUEÑO_HORA + " TEXT)";
        String createTableConsejos = "CREATE TABLE " + TABLA_CONSEJOS + " (" + COLUMNA_CONSEJOS_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMNA_CONSEJOS_DESCRIPCION + " TEXT)";
        db.execSQL(createTableUsuario);
        db.execSQL(createTableConsejos);
        db.execSQL(createTableRecetarios);
        db.execSQL(createTableRecordatorios);
        db.execSQL(createTableHorarioSueño);
    }
    //Este metodo es llamado si la version de la base de datos cambia. Previene que los usuarios que
    //tengan una version anterior de la bd creasheen cuando se hacen cambios a la db
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public boolean addOne (UserModel user ){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues cv = new ContentValues();

        cv.put(COLUMNA_USUARIO_NOMBRE, user.getNombre());
        cv.put(COLUMNA_USUARIO_CORREO, user.getEmail());
        cv.put(COLUMNA_USUARIO_CLAVE, user.getPassword());

        long insert = db.insert(TABLA_USUARIO, null , cv );
        if( insert == -1){
            return false;
        } else {
            return true;
        }

    }
    //Metodo para obtener todos los registros de una tabla en una Lista.
    public List<UserModel> getUsuarios() {

        List<UserModel> returnList = new ArrayList<>();
        //Toma la información de la db
        String queryString = "SELECT * FROM " + TABLA_USUARIO;

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(queryString, null); //el cursor es el set de resultados

        if(cursor.moveToFirst()){
            //Se hace un bucle a traves del cursor y crea un nuevo objeto de UserModel los cuales se pondran en la lista que se retorna.
            do{
                Long userID = cursor.getLong(0);
                String userName = cursor.getString(1);
                String userEmail = cursor.getString(2);
                String userPassowrd = cursor.getString(3);

                UserModel user = new UserModel(userID, userName, userEmail, userPassowrd);
                returnList.add(user);

            } while (cursor.moveToNext());
        } else {
            //Fallo, no anade nada a la lista
        }
        cursor.close();
        db.close();
        return returnList;
    }

    public boolean exists( UserModel user ){

        SQLiteDatabase db = this.getReadableDatabase();

        String[] projection = {COLUMNA_USUARIO_ID};
        String selection = COLUMNA_USUARIO_CORREO+" = ? AND "+COLUMNA_USUARIO_CLAVE+" = ?";
        String[] selectionArgs = {user.getEmail(), user.getPassword()};
        Cursor cursor = db.query(TABLA_USUARIO, projection, selection, selectionArgs, null, null, null);

        // Check if the cursor has any rows
        boolean found = cursor.moveToFirst();

        // Close the cursor and database connection
        cursor.close();
        db.close();

        // Return the boolean value
        return found;
    }

    public List<RecetasModel> getRecetas(){
        List<RecetasModel> returnList = new ArrayList<>();
        //obtiene la información de la base de datos
        String queryString = "SELECT * FROM " + TABLA_RECETAS;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(queryString, null);

        if(cursor.moveToFirst()){
            //Se hace un bucle a traves del cursor y crea un nuevo objeto de UserModel los cuales se pondran en la lista que se retorna.
            do{
                int recetaID = cursor.getInt(0);
                String recetaNombre = cursor.getString(1);
                String recetaPasos = cursor.getString(2);
                String recetaIngredientes = cursor.getString(3);
                int recetaImagen = cursor.getInt(4);

                //RecetasModel receta = new RecetasModel(recetaID, recetaNombre, recetaPasos, recetaIngredientes, recetaImagen);
                //returnList.add(receta);

            } while (cursor.moveToNext());
        } else {
            //Fallo, no anade nada a la lista
        }

        cursor.close();
        db.close();
        return returnList;
    }


}
