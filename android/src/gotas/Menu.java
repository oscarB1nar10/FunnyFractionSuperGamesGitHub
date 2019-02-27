package gotas;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
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
    ImageView operacion, logomenu;
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
        logomenu = view.findViewById(R.id.logomenu);
        logomenu.setBackgroundResource(R.drawable.logonicolas);
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
        instrucciones = view.findViewById(R.id.btn_instrucciones);
        operacion = view.findViewById(R.id.imgoperacion);


        aux = imagenes.get(random);
        operacion.setImageResource(aux);
        play.setOnClickListener(this);
        skip.setOnClickListener(this);
        instrucciones.setOnClickListener(this);
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

    public void instrucciones(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater layout = this.getLayoutInflater();
        View view = layout.inflate(R.layout.instrucciones, null);
        LinearLayout layoutdialog = view.findViewById(R.id.linearDialog);
        ArrayList<ImageView> imagen = new ArrayList<>();

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        ImageView imageView = new ImageView(getActivity());
        ImageView imageView2 = new ImageView(getActivity());
        ImageView imageView3 = new ImageView(getActivity());
        ImageView imageView4 = new ImageView(getActivity());
        ImageView imageView5 = new ImageView(getActivity());
        ImageView imageView6 = new ImageView(getActivity());

        imageView.setImageResource(R.drawable.gotas_ins1);
        imageView.setLayoutParams(params);
        imageView.setAdjustViewBounds(true);

        imageView2.setImageResource(R.drawable.gotas_ins2);
        imageView2.setLayoutParams(params);
        imageView2.setAdjustViewBounds(true);

        imageView3.setImageResource(R.drawable.gotas_ins3);
        imageView3.setLayoutParams(params);
        imageView3.setAdjustViewBounds(true);

        imageView4.setImageResource(R.drawable.gotas_ins4);
        imageView4.setLayoutParams(params);
        imageView4.setAdjustViewBounds(true);

        imageView5.setImageResource(R.drawable.gotas_ins5);
        imageView5.setLayoutParams(params);
        imageView5.setAdjustViewBounds(true);

        imageView6.setImageResource(R.drawable.gotas_ins6);
        imageView6.setLayoutParams(params);
        imageView6.setAdjustViewBounds(true);


        imagen.add(imageView);
        imagen.add(imageView2);
        imagen.add(imageView3);
        imagen.add(imageView4);
        imagen.add(imageView5);
        imagen.add(imageView6);

        for(ImageView item : imagen) {
            layoutdialog.addView(item);
        }
        builder.setView(view);
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_jugar:
                layout_division.setVisibility(View.VISIBLE);
                layout_division.setBackgroundResource(R.drawable.fondomenugotas);
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
            case R.id.btn_instrucciones:
                instrucciones();
                break;
        }
    }
}
