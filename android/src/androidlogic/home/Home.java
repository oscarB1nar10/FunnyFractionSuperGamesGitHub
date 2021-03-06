package androidlogic.home;
import android.annotation.TargetApi;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.design.internal.NavigationMenuView;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import com.funnyfractions.game.R;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;

import java.util.Locale;

import androidlogic.evaluacion.EvaluacionActivity;
import androidlogic.login.MainActivity;
import androidlogic.practice.Practica;
import androidlogic.tutorials.TutorialQueSon;
import burbujas.BubblesMain;

public class Home extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener,
                                                        NavigationView.OnNavigationItemSelectedListener {
    //widget
    private TextView logeando;
    private ImageView config, log;
    private ImageButton US,ES;
    private Button tuto, prac, evalu;
    private GoogleApiClient googleApiClient;
    private Menu nav_menu;
    //vars
    private SharedPreferences mSharedPreferences;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home2_layout);

        // Configure sign-in to request the user's ID, email address, and basic
        // profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        googleApiClient  = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API)
                .build();

        mSharedPreferences = getSharedPreferences("SHARED_PREFERENCES",MODE_PRIVATE);
        editor = mSharedPreferences.edit();
        deletePreferences();

        // setup DrawerLayout
        Toolbar toolbar = findViewById(R.id.toolbar_main);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                                            this, drawer, toolbar, R.string.navigation_drawer_open,
                                             R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView =  findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        navigationView.setItemIconTintList(null);

        NavigationMenuView navigationMenuView = (NavigationMenuView) navigationView.getChildAt(0);
        navigationMenuView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        nav_menu = navigationView.getMenu();

        View header = navigationView.getHeaderView(0);
        logeando = header.findViewById(R.id.nav_header_textView);



        config = findViewById(R.id.configurar);
        log = findViewById(R.id.logeo);
        //-----------------------------ImageButton---------
        US=findViewById(R.id.englishH);
        US.setEnabled(true);
        ES=findViewById(R.id.espanolH);
        ES.setEnabled(true);
        //------------------------------END ImageButton----
        tuto = findViewById(R.id.btntuto);
        prac = findViewById(R.id.btnprac);
        evalu = findViewById(R.id.btnevalu);

        if(mSharedPreferences.getString("idioma", "Spanish").equals("Spanish")){
            tuto.setBackgroundResource(R.drawable.tutoriales);
            prac.setBackgroundResource(R.drawable.practicas);
            evalu.setBackgroundResource(R.drawable.evaluacion);
        }
        else{
            tuto.setBackgroundResource(R.drawable.tutoriales_english);
            prac.setBackgroundResource(R.drawable.practicas_english);
            evalu.setBackgroundResource(R.drawable.evaluacion_english);
        }

        log.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Auth.GoogleSignInApi.signOut(googleApiClient).setResultCallback(new ResultCallback<Status>() {
                    @Override
                    public void onResult(@NonNull Status status) {
                        if(status.isSuccess()){
                            Intent intent = new Intent(Home.this, MainActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                        }
                    }
                });
                onBackPressed();
            }
        });
        tuto.setOnClickListener(new View.OnClickListener() {
            @TargetApi(Build.VERSION_CODES.LOLLIPOP)
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), TutorialQueSon.class);
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
                Intent intent = new Intent(getApplicationContext(), EvaluacionActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.zoom_back_in, R.anim.zoom_back_out);
            }
        });

        String usuario = "";
        Bundle dato = getIntent().getExtras();
        if(dato != null) {
            usuario = dato.getString("usuario");
            editor.putString("userName", usuario);
            editor.apply();
        }else{
            usuario = mSharedPreferences.getString("userName","carl");
        }
        logeando.setText(usuario);
    }

    private void deletePreferences() {
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putInt("currentLevel",0);
        editor.putInt("score",0);
        editor.apply();
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
    }

    private void normal_logOut(){
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

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        Intent refrescar = new Intent(this, Home.class);
        refrescar.putExtra("usuario", logeando.getText().toString());
        Locale localizacion=null;
        Configuration config=null;
        SharedPreferences.Editor editor = mSharedPreferences.edit();

        switch (item.getItemId()){
            case R.id.about:
                    showAboutAlerDialog();
                break;
            case R.id.sign_out:
                Auth.GoogleSignInApi.signOut(googleApiClient).setResultCallback(new ResultCallback<Status>() {
                    @Override
                    public void onResult(@NonNull Status status) {
                        if(status.isSuccess()){
                            Intent intent = new Intent(Home.this, MainActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                        }else{
                            normal_logOut();
                        }
                    }
                });
                break;
            case R.id.idioma:
                if(nav_menu.findItem(R.id.spanish).isVisible()){
                    nav_menu.findItem(R.id.spanish).setVisible(false);
                    nav_menu.findItem(R.id.english).setVisible(false);
                }else{
                    nav_menu.findItem(R.id.spanish).setVisible(true);
                    nav_menu.findItem(R.id.english).setVisible(true);
                }
                break;

            case R.id.spanish:
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
        }

        return false;
    }

    /**
     * This method allow us to show an AlertDialog whit the specific apk information .
     */
    private void showAboutAlerDialog(){
        final android.support.v7.app.AlertDialog.Builder builder = new
                android.support.v7.app.AlertDialog.Builder(Home.this).setCancelable(true);
        // Get the layout inflater
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.about_layout, null);


        builder.setView(view);
        builder.create();
        builder.show();
    }
}