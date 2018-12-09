package gotas;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.funnyfractions.game.R;

import java.util.ArrayList;
import java.util.HashMap;

public class Menu extends Activity {
    Button skip;
    ImageView operacion;
    Handler handler = new Handler();
    HashMap<Integer, String> lista = new HashMap<>();
    ArrayList<Integer> imagenes = new ArrayList<>();
    int random = 0;
    int aux = 0;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu);
        random = (int)(Math.random()*10);
        //Se llena la lista con los valores de las operaciones para despues ser conparadas con el de el Hashmap
        imagenes.add(R.drawable.ope1);
        imagenes.add(R.drawable.ope5);
        imagenes.add(R.drawable.ope6);
        imagenes.add(R.drawable.ope7);
        imagenes.add(R.drawable.ope8);
        imagenes.add(R.drawable.ope9);
        imagenes.add(R.drawable.ope10);
        imagenes.add(R.drawable.ope2);
        imagenes.add(R.drawable.ope3);
        imagenes.add(R.drawable.ope4);

        //Se llena el Hashmap
        lista.put(R.drawable.ope1, "4_8");
        lista.put(R.drawable.ope5, "14_25");
        lista.put(R.drawable.ope6, "21_135");
        lista.put(R.drawable.ope7, "40_36");
        lista.put(R.drawable.ope8, "72_6");
        lista.put(R.drawable.ope9, "54_48");
        lista.put(R.drawable.ope10, "105_40");
        lista.put(R.drawable.ope2, "8_33");
        lista.put(R.drawable.ope3, "12_42" );
        lista.put(R.drawable.ope4, "48_45");
        skip = findViewById(R.id.btnskip);
        operacion = findViewById(R.id.imgoperacion);
        aux = imagenes.get(random);
        operacion.setImageResource(aux);
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(getApplicationContext(), AndroidLauncher.class);
                intent.putExtra("operacion", ""+lista.get(aux));
                startActivity(intent);
            }
        },6000);
        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handler.removeCallbacksAndMessages(null);
                Intent intent = new Intent(getApplicationContext(), AndroidLauncher.class);
                intent.putExtra("operacion", ""+lista.get(aux));
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        handler.removeCallbacksAndMessages(null);
    }

    @Override
    protected void onResume() {
        super.onResume();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(getApplicationContext(), AndroidLauncher.class);
                intent.putExtra("operacion", aux);
                startActivity(intent);
            }
        },6000);
    }
}
