package androidlogic.login;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Registro extends Activity{
    private TextInputLayout vect[];
    private int validacampo[];
    private EditText vEditText[];
    private TextView ingDatos;
    private Button btnregistrar;
    private ProgressDialog progreso;
    private RequestQueue requestQueue;
    private JsonObjectRequest jsonObjectRequest;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registro);
        vect = new TextInputLayout[8];
        vEditText= new EditText[8];
        añadirEditTextA_Vector();
        ingDatos=findViewById(R.id.ingresaDatos);
        vect[0] = findViewById(R.id.tilnom);
        vect[1] = findViewById(R.id.tilapell);
        vect[2] = findViewById(R.id.tilusu);
        vect[3] = findViewById(R.id.tilPassword);
        vect[4] = findViewById(R.id.tilRepetirPaswword);
        vect[5] = findViewById(R.id.tilpre1);
        vect[6] = findViewById(R.id.tilpre2);
        vect[7] = findViewById(R.id.tilpre3);
        validacampo = new int[8];
        for(int i = 0 ; i < 8; i++){
            validacampo[i] = 0;
        }
        btnregistrar = (Button) findViewById(R.id.btnRegistrarR);
        requestQueue = Volley.newRequestQueue(this);
        btnregistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int aux = CamposVacios();
                if(aux > 0){
                    for (int h = 0; h < 8; h++){
                        if(validacampo[h] == 0){
                            vect[h].getEditText().setError("Campo obligatorio");
                        }
                    }
                }
                else {
                    vect[3].getEditText().getText().toString().trim();
                    vect[4].getEditText().getText().toString().trim();
                    if(!vect[3].getEditText().getText().toString().equals(vect[4].getEditText().getText().toString())){
                        vect[4].getEditText().setError("Password no coincide");
                    }
                    else {
                        cargarWebService();
                    }
                }
            }
        });
    }

    /*
        *anñadirEditTextA_Vector(): Funcion que permite agregar en un vector "vEditText" elemnetos de tipo EditText
        * para posteriormente agregarle a cada elamento dentro del vector un vigilante de tipo "FocusChangeListener" que permite determinar
        * si un elemento EditText gana o pierde el foco.

     */

    private void añadirEditTextA_Vector(){
        vEditText[0] =  findViewById(R.id.txtNombres);
        vEditText[1] =  findViewById(R.id.txtApellidos);
        vEditText[2] =  findViewById(R.id.txtUsuario);
        vEditText[3] =  findViewById(R.id.txtPassword);
        vEditText[4] =  findViewById(R.id.txtPassword2);
        vEditText[5] =  findViewById(R.id.txtPregunta1);
        vEditText[6] =  findViewById(R.id.txtPregunta2);
        vEditText[7] =  findViewById(R.id.txtPregunta3);

            for(int i=0; i<8; i++){
                final int I=i;//Declaracion de variable final "I" necesaria para que dentro de la clase anonima se pueda reconocer la variable
                vEditText[i].setOnFocusChangeListener(new View.OnFocusChangeListener() {
                    @Override
                    public void onFocusChange(View view, boolean hasFocus) {
                        if (!hasFocus) {//variable que indica si el EditText gano o perdio el foco
                            vEditText[I].setInputType(InputType.TYPE_NULL);//se indica que el metodo de entrada sera nulo (ocultar teclado)
                            ingDatos.setVisibility(View.INVISIBLE);
                        }else{
                            vEditText[I].setInputType(InputType.TYPE_CLASS_TEXT);//se indica que el metodo de entrada sera texto.
                        }
                    }
                });
            }
    }

    private void cargarWebService() {
        progreso = new ProgressDialog(this);
        String url = "https://funnyfractios000.000webhostapp.com/guardar.php?Nombre="+vect[0].getEditText().getText().toString()+
                "&Apellido="+vect[1].getEditText().getText().toString()+"&Usuario="+vect[2].getEditText().getText().toString()+
                "&Password="+vect[3].getEditText().getText().toString()+"&Pregunta1="+vect[5].getEditText().getText().toString()+
                "&Pregunta2="+vect[6].getEditText().getText().toString()+"&Pregunta3="+vect[7].getEditText().getText().toString();
        url = url.replace(" ", "%20");
        progreso.setMessage("Cargando...");
        progreso.show();
        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                @Override
            public void onResponse(JSONObject response) {
                    try {
                        JSONArray jsonArray = response.getJSONArray("usuario");
                        JSONObject jsonObject = jsonArray.getJSONObject(0);
                        if(jsonObject.optString("usu").equals("EXISTE")){
                            progreso.hide();
                            vect[2].getEditText().setError("Usuario ya existe");
                        }
                        else{
                            progreso.hide();
                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                            startActivity(intent);
                            overridePendingTransition(R.anim.left_in, R.anim.left_out);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                 }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    progreso.hide();
                    Toast.makeText(getBaseContext(), "No se pudo registrar debido a " + error.toString(), Toast.LENGTH_LONG).show();
                }
            });
            jsonObjectRequest.setRetryPolicy(new RetryPolicy() {
                @Override
                public int getCurrentTimeout() {
                    return (DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 4);
                }

                @Override
                public int getCurrentRetryCount() {
                    return DefaultRetryPolicy.DEFAULT_MAX_RETRIES;
                }

                @Override
                public void retry(VolleyError error) throws VolleyError {
                }
            });
            requestQueue.add(jsonObjectRequest);
    }

    public int CamposVacios(){
        int cantcamp = 0;
        for(int j = 0; j < 8; j++){
            if(vect[j].getEditText().getText().toString().isEmpty() == true){
                cantcamp++;
            }
            else {
                validacampo[j] = 1;
            }
        }
        return cantcamp;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.left_in, R.anim.left_out);
    }
}