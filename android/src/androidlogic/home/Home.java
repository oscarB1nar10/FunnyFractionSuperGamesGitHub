package androidlogic.home;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.funnyfractions.game.tutorials.AndroidLauncher2;
import com.funnyfractions.game.R;

import java.util.Locale;

import androidlogic.login.MainActivity;
import androidlogic.practice.Practica;
import androidlogic.tutorials.Tutorial;

public class Home extends Activity {
    private TextView logeando;
    private ImageView config, log;
    private ImageButton US,ES;
    private Button tuto, prac, evalu;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);
        config = (ImageView) findViewById(R.id.configurar);
        log = (ImageView) findViewById(R.id.logeo);
        logeando = (TextView) findViewById(R.id.txt_logeo);
        //-----------------------------ImageButton---------
        US=findViewById(R.id.englishH);
        US.setEnabled(true);
        ES=findViewById(R.id.espanolH);
        ES.setEnabled(true);
        //------------------------------END ImageButton----
        tuto = (Button) findViewById(R.id.btntuto);
        prac = (Button) findViewById(R.id.btnprac);
        evalu = (Button) findViewById(R.id.btnevalu);
        log.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        tuto.setOnClickListener(new View.OnClickListener() {
            @TargetApi(Build.VERSION_CODES.LOLLIPOP)
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Tutorial.class);
                startActivity(intent);
                overridePendingTransition(R.anim.zoom_back_in, R.anim.zoom_back_out);
            }
        });
        prac.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Practica.class);
                startActivity(intent);
                overridePendingTransition(R.anim.zoom_back_in, R.anim.zoom_back_out);
            }
        });
        evalu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*Intent intent = new Intent(getApplicationContext(), Evaluacion.class);
                startActivity(intent);
                overridePendingTransition(R.anim.zoom_back_in, R.anim.zoom_back_out);*/
            }
        });
        Bundle dato = getIntent().getExtras();
        String usuario = dato.getString("usuario");
        logeando.setText(usuario);
    }
    public void idioma(View v){
        Intent refrescar = new Intent(this, Home.class);
        refrescar.putExtra("usuario", logeando.getText().toString());
        Locale localizacion=null;
        Configuration config=null;
        switch (v.getId()){
            case R.id.englishH:
                localizacion= new Locale("en","US");
                Locale.setDefault(localizacion);
                config=new Configuration();
                config.locale=localizacion;
                getBaseContext().getResources().updateConfiguration(config,getBaseContext().getResources().getDisplayMetrics());
                startActivity(refrescar);
                finish();
                break;
            case R.id.espanolH:
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
        super.onBackPressed();
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);

        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        US.setImageDrawable(null);
        ES.setImageDrawable(null);
        config.setImageDrawable(null);
        log.setImageDrawable(null);
    }
}