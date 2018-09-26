package androidlogic.login;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

public class Recupera extends Activity {
    private TextInputLayout tilusu, tilpre1, tilpre2, tilpre3;
    private EditText [] vEdit;
    private Button recupera;
    private ProgressDialog progreso;
    private JsonObjectRequest jsonObjectRequest;
    private RequestQueue requestQueue;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recupera);
        tilusu = findViewById(R.id.tilrecusu);
        tilpre1 = findViewById(R.id.tilrecupre1);
        tilpre2 = findViewById(R.id.tilrecupre2);
        tilpre3 = findViewById(R.id.tilrecupre3);
        vEdit=new EditText[4];
        vEdit[0]=findViewById(R.id.txtrecusu);
        vEdit[1]=findViewById(R.id.txtrecupre1);
        vEdit[2]=findViewById(R.id.txtrecupre2);
        vEdit[3]=findViewById(R.id.txtrecupre3);
        cambioDeFocoEditText();
        progreso=new ProgressDialog(this);
        recupera = findViewById(R.id.btnRecupera);
        requestQueue = Volley.newRequestQueue(this);
        recupera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(tilusu.getEditText().getText().toString().isEmpty() == true && tilpre1.getEditText().getText().toString().isEmpty() == true && tilpre2.getEditText().getText().toString().isEmpty() == true && tilpre3.getEditText().getText().toString().isEmpty() == true ){
                    tilusu.getEditText().setError("Campo obligatorio");
                    tilpre1.getEditText().setError("Campo obligatorio");
                    tilpre2.getEditText().setError("Campo obligatorio");
                    tilpre3.getEditText().setError("Campo obligatorio");
                }
                else {
                    RecuperarPassword();
                }
            }
        });
    }

    private void cambioDeFocoEditText(){
        for(int i=0; i<4; i++){
            final int I=i;//Declaracion de variable final "I" necesaria para que dentro de la clase anonima se pueda reconocer la variable
            vEdit[i].setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View view, boolean hasFocus) {
                    if (!hasFocus) {//variable que indica si el EditText gano o perdio el foco
                        vEdit[I].setInputType(InputType.TYPE_NULL);//se indica que el metodo de entrada sera nulo (ocultar teclado)
                    }else{
                        vEdit[I].setInputType(InputType.TYPE_CLASS_TEXT);//se indica que el metodo de entrada sera texto.
                    }
                }
            });
        }
    }

    public void RecuperarPassword(){
        String usuario = tilusu.getEditText().getText().toString();
        String pregunta1 = tilpre1.getEditText().getText().toString();
        String pregunta2 = tilpre2.getEditText().getText().toString();
        String pregunta3 = tilpre3.getEditText().getText().toString();
        String url="https://funnyfractios000.000webhostapp.com/recuperar.php?Usuario="+usuario+"&Pregunta1="+pregunta1+"&Pregunta2="+pregunta2+"&Pregunta3="+pregunta3;
        progreso = new ProgressDialog(this);
        progreso.setMessage("Validando espera un momento...");
        progreso.show();
        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                JSONArray arrayAso = null;
                try {
                    arrayAso=response.getJSONArray("clave");
                    JSONObject jsonObject = arrayAso.getJSONObject(0);
                    editarMarcosEditText(jsonObject.optString("acceso"),jsonObject.optString("clave"));
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
            }
        });
        jsonObjectRequest.setRetryPolicy(new RetryPolicy() {
            @Override
            public int getCurrentTimeout() {
                return (DefaultRetryPolicy.DEFAULT_TIMEOUT_MS*4);
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

    public void editarMarcosEditText(String acceso, String clave) {
        if(acceso.equals("UsuarioNoExiste")) {
            tilusu.getEditText().setError("Usuario no registrado");
        }
        else{
            if (acceso.equals("errorValidando")) {
                tilpre1.getEditText().setError("Datos incorrectos");
                tilpre2.getEditText().setError("Datos incorrectos");
                tilpre3.getEditText().setError("Datos incorrectos");
            } else {
                AlertDialog.Builder confirma = new AlertDialog.Builder(this);
                confirma.setIcon(R.drawable.a0_0);
                confirma.setTitle("Funny Fractions");
                confirma.setMessage("Su contraseña es: " + clave);
                confirma.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                AlertDialog alertDialog = confirma.create();
                alertDialog.show();
            }
        }
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.left_in, R.anim.left_out);
    }
}
