package androidlogic.login;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.funnyfractions.game.R;
import com.funnyfractions.game.tutorials.AndroidLauncher2;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.Task;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.util.Locale;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import androidlogic.home.Home;

public class MainActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {

    private EditText user, pass;
    private ImageButton US,ES;
    private Button logueo;
    private TextView mRegister, recuperar;
    private ProgressDialog progreso;
    private RequestQueue requestQueue;
    private JsonObjectRequest jsonObjectRequest;
    private SharedPreferences sharedP;
    private SharedPreferences.Editor editor;
    private GoogleSignInClient mGoogleSignInClient;
    private SignInButton signInButton;
    public static final int RC_SIGN_IN = 101;
    public static final String TAG = "MainActivity";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_layout);

        signInButton = (SignInButton) findViewById(R.id.sign_in_buttonw);
        signInButton.setSize(SignInButton.SIZE_ICON_ONLY);
        checkExistingAccount();

        // Configure sign-in to request the user's ID, email address, and basic
        // profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();


        // Build a GoogleSignInClient with the options specified by gso.
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);


        sharedP = getSharedPreferences("SHARED_PREFERENCES", Context.MODE_PRIVATE);

        editor = sharedP.edit();
        editor.putInt("currentLevel", 0);
        editor.putInt("score",0);
        editor.apply();

        //------------------------------ImageButton----------------
        US=findViewById(R.id.english);
        US.setEnabled(true);
        ES=findViewById(R.id.espanol);
        ES.setEnabled(true);
        //-----------------------------END ImageButton
        requestQueue = Volley.newRequestQueue(this);
        progreso=new ProgressDialog(this);
        //--------------------------------Text Fields--------------
        user=findViewById(R.id.txtUsuario);
        pass=findViewById(R.id.txtPassword);
        handleSSLHandshake();
        //--------------------------------END Text Fields--------------
        //--------------------------------Action Buttons----------
        mRegister =findViewById(R.id.txtRegistrarse);
        recuperar = findViewById(R.id.txtRecuperar);
        logueo=findViewById(R.id.btnIngresar);
        mRegister.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getBaseContext(),Registro.class);
                startActivity(i);
                overridePendingTransition(R.anim.right_in, R.anim.right_out);
            }
        });
        recuperar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Recupera.class);
                startActivity(intent);
                overridePendingTransition(R.anim.left_in, R.anim.left_out);
            }
        });
        logueo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                evaluarAcceso();
            }
        });
        //-------------------------------END Action Buttons----------
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signInIntent = mGoogleSignInClient.getSignInIntent();
                startActivityForResult(signInIntent, RC_SIGN_IN);
            }
        });
    }

    public void evaluarAcceso(){
        String usuario=user.getText().toString();
        String password=pass.getText().toString();
        String url="https://funnyfractios000.000webhostapp.com/consultar.php?Usuario="+usuario+"&Password="+password;
        progreso = new ProgressDialog(this);
        progreso.setMessage("Cargando...");
        progreso.show();
        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                JSONArray arrayAso;

                try {
                    arrayAso = response.getJSONArray("usuario");
                    JSONObject jsonObject = arrayAso.getJSONObject(0);
                    editarMarcosEditText(jsonObject.optString("acceso"));
                } catch (JSONException e) {
                    Toast.makeText(getBaseContext(), "Error de tipo..."+e.getMessage(), Toast.LENGTH_LONG).show();
                }
                progreso.hide();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progreso.hide();
                Toast.makeText(getBaseContext(), "No se pudo ingresar debido a " + error.toString(), Toast.LENGTH_LONG).show();
                //Log.i("ERROR!!", error.toString());
            }
        });
        jsonObjectRequest.setRetryPolicy(new RetryPolicy() {
            @Override
            public int getCurrentTimeout() {

                return ((4*DefaultRetryPolicy.DEFAULT_TIMEOUT_MS));
            }

            @Override
            public int getCurrentRetryCount() {
                return 0;
            }

            @Override
            public void retry(VolleyError error) throws VolleyError {       }
        });
        requestQueue.add(jsonObjectRequest);
    }

    public void editarMarcosEditText(String acceso){
        if(acceso.equals("errorPassword")){
            user.setError("usuario o contraseña incorrecto ");
            pass.setError("usuario o contraseña incorrecto ");
        }else{
            Intent intent=new Intent(this,Home.class);
            intent.putExtra("usuario", user.getText().toString());
            sharedP = getSharedPreferences("SHARED_PREFERENCES", Context.MODE_PRIVATE);
            editor = sharedP.edit();
            editor.putString("usuariologueado", user.getText().toString());
            editor.apply();
            startActivity(intent);
        }
    }

    public void idioma(View v){
        Intent refrescar = new Intent(this, MainActivity.class);
        Locale localizacion=null;
        Configuration config=null;
        switch (v.getId()){
            case R.id.english:
                editor.putString("idioma", "English");
                editor.apply();
                localizacion= new Locale("en","US");
                Locale.setDefault(localizacion);
                config=new Configuration();
                config.locale=localizacion;
                getBaseContext().getResources().updateConfiguration(config,getBaseContext().getResources().getDisplayMetrics());
                startActivity(refrescar);
                finish();
                break;
            case R.id.espanol:
                editor.putString("idioma", "Spanish");
                editor.apply();
                localizacion= new Locale("es","ES");
                Locale.setDefault(localizacion);
                config=new Configuration();
                config.locale=localizacion;
                getBaseContext().getResources().updateConfiguration(config,getBaseContext().getResources().getDisplayMetrics());
                startActivity(refrescar);
                finish();
                break;
        }
    }

    public void banderasVisibles(View v){
        if(US.getVisibility()==View.INVISIBLE && ES.getVisibility()==View.INVISIBLE) {
            US.setVisibility(View.VISIBLE);
            US.setEnabled(true);
            ES.setVisibility(View.VISIBLE);
            ES.setEnabled(true);
        }else{
            US.setVisibility(View.INVISIBLE);
            US.setEnabled(false);
            ES.setVisibility(View.INVISIBLE);
            ES.setEnabled(false);
        }
    }

    @Override
    public void onBackPressed() {
        final AlertDialog.Builder confirma = new AlertDialog.Builder(this);
        confirma.setMessage("Funny Fractions");
        confirma.setTitle("¿Desea salir de la aplicacion?");
        confirma.setPositiveButton("SI", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent saliendo = new Intent(Intent.ACTION_MAIN);
                saliendo.addCategory(Intent.CATEGORY_HOME);
                saliendo.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(saliendo);
            }
        });
        confirma.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        AlertDialog alertDialog = confirma.create();
        alertDialog.show();
    }

    @Override
    protected void onDestroy() {
        US.setImageDrawable(null);
        ES.setImageDrawable(null);
        super.onDestroy();

    }

    @SuppressLint("TrulyRandom")
    public static void handleSSLHandshake() {
        try {
            TrustManager[] trustAllCerts = new TrustManager[]{new X509TrustManager() {
                public X509Certificate[] getAcceptedIssuers() {
                    return new X509Certificate[0];
                }

                @Override
                public void checkClientTrusted(X509Certificate[] certs, String authType) {
                }

                @Override
                public void checkServerTrusted(X509Certificate[] certs, String authType) {
                }
            }};

            SSLContext sc = SSLContext.getInstance("SSL");
            sc.init(null, trustAllCerts, new SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
            HttpsURLConnection.setDefaultHostnameVerifier(new HostnameVerifier() {
                @Override
                public boolean verify(String arg0, SSLSession arg1) {
                    return true;
                }
            });
        } catch (Exception ignored) {
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            // Signed in successfully, show authenticated UI.
            Intent intent = new Intent(MainActivity.this, Home.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.putExtra("usuario", account.getEmail());
            sharedP = getSharedPreferences("SHARED_PREFERENCES", Context.MODE_PRIVATE);
            editor = sharedP.edit();
            editor.putString("usuariologueado", account.getEmail());
            editor.apply();
            startActivity(intent);

        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.w(TAG, "signInResult:failed code=" + e.getStatusCode());
            Toast.makeText(MainActivity.this, "Error al iniciar sesion", Toast.LENGTH_LONG).show();
        }
    }

    private void checkExistingAccount(){
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        if(account != null){
            Intent intent = new Intent(MainActivity.this, Home.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.putExtra("usuario", account.getEmail());
            sharedP = getSharedPreferences("SHARED_PREFERENCES", Context.MODE_PRIVATE);
            editor = sharedP.edit();
            editor.putString("usuariologueado", account.getEmail());
            editor.apply();
            startActivity(intent);
        }
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Toast.makeText(MainActivity.this, "Error al iniciar sesion", Toast.LENGTH_LONG).show();
    }
}
