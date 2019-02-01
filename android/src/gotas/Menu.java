package gotas;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.funnyfractions.game.R;

import java.util.ArrayList;
import java.util.HashMap;

public class Menu extends Fragment implements View.OnClickListener {
    Button skip, play, settings, instrucciones;
    ImageView operacion;
    Handler handler = new Handler();
    HashMap<Integer, String> lista = new HashMap<>();
    ArrayList<Integer> imagenes = new ArrayList<>();
    private LinearLayout layout_division;
    int random = 0;
    int aux = 0;

    OnFragmentInteractionListener mListener;

    public static Menu newInstance() {

        Bundle args = new Bundle();

        Menu fragment = new Menu();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.menu, container, false);

        layout_division = view.findViewById(R.id.linearLayout_division);

        if(getArguments() != null) {
            if (getArguments().getBoolean("showOperation", false)) {
                layout_division.setVisibility(View.VISIBLE);
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent intent = new Intent(getContext(), AndroidLauncher.class);
                        intent.putExtra("operacion", ""+lista.get(aux));
                        startActivity(intent);
                    }
                },6000);
            }
        }

        random = (int)(Math.random()*10);

        //region imagenes
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
        //endregion

        //region hashmap
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
        //endregion

        skip = view.findViewById(R.id.btnskip);
        play = view.findViewById(R.id.btn_jugar);
        settings = view.findViewById(R.id.btn_settings);
        operacion = view.findViewById(R.id.imgoperacion);


        aux = imagenes.get(random);
        operacion.setImageResource(aux);
        play.setOnClickListener(this);
        skip.setOnClickListener(this);
        return view;
    }


    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }

    @Override
    public void onPause() {
        super.onPause();
        handler.removeCallbacksAndMessages(null);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_jugar:
                layout_division.setVisibility(View.VISIBLE);
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent intent = new Intent(getContext(), AndroidLauncher.class);
                        intent.putExtra("operacion", ""+lista.get(aux));
                        startActivity(intent);
                    }
                },6000);
                break;
            case R.id.btnskip:
                handler.removeCallbacksAndMessages(null);
                Intent intent = new Intent(getContext(), AndroidLauncher.class);
                intent.putExtra("operacion", ""+lista.get(aux));
                startActivity(intent);
                break;
        }
    }
}
