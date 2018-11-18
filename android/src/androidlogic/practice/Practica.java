package androidlogic.practice;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.funnyfractions.game.R;

import androidlogic.games.archery_game.MainMenu;
import androidlogic.games.archery_game.MainMenu;
import bateria.EjecutableBateria;
import burbujas.BubblesMain;

public class Practica extends Activity {
    private ImageButton retornarHome;
    private ImageView prac;
    private Button suma, res, mul, div;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.practica);
        retornarHome = findViewById(R.id.retornarHome);
        prac = (ImageView) findViewById(R.id.pract);
        suma = (Button) findViewById(R.id.suma);
        res = (Button) findViewById(R.id.resta);
        div = (Button) findViewById(R.id.division);
        mul = (Button) findViewById(R.id.multiplicacion);
        retornarHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        validarIdioma();
        suma.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MainMenu.class);
                startActivity(intent);
            }
        });
        res.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), EjecutableBateria.class);
                startActivity(intent);
            }
        });
        mul.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), BubblesMain.class);
                startActivity(intent);
            }
        });

    }

    public void validarIdioma(){
        if(suma.getText().equals("Suma")){
            prac.setImageResource(R.drawable.pract);
        }else{
            prac.setImageResource(R.drawable.practices);
        }
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.zoom_back_in, R.anim.zoom_back_out);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        prac.setImageDrawable(null);
        retornarHome.setImageDrawable(null);
    }
}