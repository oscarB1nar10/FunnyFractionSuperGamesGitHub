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
    HashMap<String, Integer> lista = new HashMap<>();
    ArrayList<String> imagenes = new ArrayList<>();
    int random = 0;
    String aux = "";


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu);
        random = (int)(Math.random()*10);
        //Se llena la lista con los valores de las operaciones para despues ser conparadas con el de el Hashmap
        imagenes.add("1_2%4_4");
        imagenes.add("7_5%5_2");
        imagenes.add("7_9%15_3");
        imagenes.add("8_9%4_5");
        imagenes.add("9_2%3_8");
        imagenes.add("9_4%12_6");
        imagenes.add("15_5%8_7");
        imagenes.add("2_3%11_4");
        imagenes.add("3_6%7_4");
        imagenes.add("6_5%9_8");

        //Se llena el Hashmap
        lista.put("1_2%4_4",R.drawable.ope1);
        lista.put("7_5%5_2",R.drawable.ope5);
        lista.put("7_9%15_3",R.drawable.ope6);
        lista.put("8_9%4_5",R.drawable.ope7);
        lista.put("9_2%3_8",R.drawable.ope8);
        lista.put("9_4%12_6",R.drawable.ope9);
        lista.put("15_5%8_7",R.drawable.ope10);
        lista.put("2_3%11_4",R.drawable.ope2);
        lista.put("3_6%7_4",R.drawable.ope3);
        lista.put("6_5%9_8",R.drawable.ope4);
        skip = findViewById(R.id.btnskip);
        operacion = findViewById(R.id.imgoperacion);
        aux = imagenes.get(random);
        operacion.setImageResource(lista.get(aux));
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(getApplicationContext(), AndroidLauncher.class);
                intent.putExtra("operacion", aux);
                startActivity(intent);
            }
        },6000);
        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handler.removeCallbacksAndMessages(null);
                Intent intent = new Intent(getApplicationContext(), AndroidLauncher.class);
                intent.putExtra("operacion", aux);
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
