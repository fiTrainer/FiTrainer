package com.fitrainer.upm.fitrainer.Sesion;

/**
 * Created by abel on 3/01/17.
 */

        import java.util.HashMap;

        import android.content.Context;
        import android.content.Intent;
        import android.content.SharedPreferences;
        import android.content.SharedPreferences.Editor;

        import com.fitrainer.upm.fitrainer.InicioSesion;
        import com.fitrainer.upm.fitrainer.MainActivity;
        import com.fitrainer.upm.fitrainer.Usuario;

public class SessionManagement {
    // Shared Preferences
    SharedPreferences pref;

    // Editor for Shared preferences
    Editor editor;

    // Context
    Context _context;

    // Shared pref mode
    int PRIVATE_MODE = 0;

    // Sharedpref file name
    private static final String PREF_NAME = "fiTrainer";

    // All Shared Preferences Keys
    private static final String IS_LOGIN = "IsLoggedIn";

    //Nombre Usuario
    public static final String KEY_NAME = "nombre";

    //Email Usuario
    public static final String KEY_EMAIL = "email";

    //Nickname
    public static final String KEY_NICKNAME = "nickname";

    //ID
    public static final String KEY_ID = "id";

    //Edad
    public static final String KEY_EDAD = "edad";

    //Peso
    public static final String KEY_PESO = "peso";

    //Altura
    public static final String KEY_ALTURA = "altura";

    //Entrenador
    public static final String KEY_ENTRENADOR = "entrenador";

    //Sexo
    public static final String KEY_SEXO = "sexo";

    //Contraseña
    public static final String KEY_CONTRASENIA = "contrasenia";

    // Constructor
    public SessionManagement(Context context){
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    /**
     * Create login session
     * */
    public void createLoginSession(Usuario usuario){
        // Storing login value as TRUE
        editor.putBoolean(IS_LOGIN, true);

        // Almacenamos el nombre en el pref
        editor.putString(KEY_NAME, usuario.getNombre());

        // Almacenamos el email en el pref
        editor.putString(KEY_EMAIL, usuario.getEmail());

        // Almacenamos el nickname en el pref
        editor.putString(KEY_NICKNAME, usuario.getNickname());

        // Almacenamos el id en el pref
        editor.putString(KEY_ID, String.valueOf(usuario.getIdUsuario()));

        // Almacenamos la edad en el pref
        editor.putString(KEY_EDAD, String.valueOf(usuario.getEdad()));

        // Almacenamos el peso en el pref
        editor.putString(KEY_PESO, Double.toString(usuario.getPeso()));

        // Almacenamos la alturaen el pref
        editor.putString(KEY_ALTURA, Double.toString(usuario.getAltura()));

        // Almacenamos si es entrenador en el pref
        editor.putString(KEY_ENTRENADOR,String.valueOf(usuario.isEsEntrenador()));

        // Almacenamos el id en el pref
        editor.putString(KEY_SEXO,String.valueOf(usuario.getSexo()));

        // Almacenamos el id en el pref
        editor.putString(KEY_CONTRASENIA,usuario.getContrasenia());


        // commit changes
        editor.commit();
    }

    /**
     * Check login method wil check user login status
     * If false it will redirect user to login page
     * Else won't do anything
     * */
    public boolean checkLogin(){
        // Check login status
        if(!this.isLoggedIn()){
            // user is not logged in redirect him to Login Activity
            Intent i = new Intent(_context, InicioSesion.class);
            // Closing all the Activities
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            // Add new Flag to start new Activity
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            // Staring Login Activity
            _context.startActivity(i);
            return true;
        }
        return false;

    }



    /**
     * Get stored session data
     * */
    public HashMap<String, String> getUserDetails(){
        HashMap<String, String> user = new HashMap<String, String>();
        // user name
        user.put(KEY_NAME, pref.getString(KEY_NAME, null));

        // user email id
        user.put(KEY_EMAIL, pref.getString(KEY_EMAIL, null));

        // user email id
        user.put(KEY_NICKNAME, pref.getString(KEY_NICKNAME, null));

        // user id id
        user.put(KEY_ID, pref.getString(KEY_ID, null));

        // user edad id
        user.put(KEY_EDAD, pref.getString(KEY_EDAD, null));

        // user peso id
        user.put(KEY_PESO, pref.getString(KEY_PESO, null));

        // user altura id
        user.put(KEY_ALTURA, pref.getString(KEY_ALTURA, null));

        // user entrenador id
        user.put(KEY_ENTRENADOR, pref.getString(KEY_ENTRENADOR, null));

        // user sexo id
        user.put(KEY_SEXO, pref.getString(KEY_SEXO, null));

        // user sexo id
        user.put(KEY_CONTRASENIA, pref.getString(KEY_CONTRASENIA, null));

        // return user
        return user;
    }

    /**
     * Clear session details
     * */
    public void logoutUser(){
        // Clearing all data from Shared Preferences
        editor.clear();
        editor.commit();

        // After logout redirect user to Loing Activity
        Intent i = new Intent(_context, MainActivity.class);
        // Closing all the Activities
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        // Add new Flag to start new Activity
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        // Staring Login Activity
        _context.startActivity(i);
    }

    /**
     * Quick check for login
     * **/
    // Get Login State
    public boolean isLoggedIn(){
        return pref.getBoolean(IS_LOGIN, false);
    }
}