package com.funnyfractions.game.tutorials;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.funnyfractions.game.R;
import com.funnyfractions.game.archery_game.ActionResolver;

import androidlogic.practice.Practica;
import de.hdodenhof.circleimageview.CircleImageView;

public class ActionResolverAndroid implements ActionResolver {


    Context context;
    SharedPreferences sharedP;

    public ActionResolverAndroid(Context androidLauncher2) {
        context = androidLauncher2;
        sharedP = androidLauncher2.getSharedPreferences("SHARED_PREFERENCES", Context.MODE_PRIVATE);

    }

    @Override
    public void showToast(CharSequence toastMessage, int toastDuration) {
        Toast.makeText(context, toastMessage, toastDuration).show();
    }

    @Override
    public void goToAndroid() {
        SharedPreferences.Editor editor2 = sharedP.edit();
        if(sharedP.getInt("currentLevel",-1) >0){
            int value = sharedP.getInt("currentLevel",-1);
            value++;
            editor2.putInt("currentLevel", value);
            editor2.apply();
        }else{
            SharedPreferences.Editor editor = sharedP.edit();
            editor.putInt("currentLevel", 1);
            editor.apply();
        }
        Intent intent = new Intent(context, AndroidLauncher2.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        context.startActivity(intent);

    }

    @Override
    public void cleanPreferences() {
        SharedPreferences.Editor editor2 = sharedP.edit();
        editor2.putInt("currentLevel", 0);
        editor2.apply();

    }

    @Override
    public void saveScore(int score) {
        SharedPreferences.Editor editor = sharedP.edit();
        int lastScore = sharedP.getInt("score",0);
        editor.putInt("score",(lastScore+score));
        editor.apply();
    }

    @Override
    public void pauseArcheryGame() {
        //Intent intent = new Intent(context, MenuArcheryActivity.class);
        //context.startActivity(intent);
    }

    @Override
    public void menu() {
        final Activity activity = (Activity) context;
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                //here it's necessary get reference to shared preferences
                final SharedPreferences.Editor editor2 = sharedP.edit();
                final AlertDialog.Builder builder = new AlertDialog.Builder(context);
                boolean soundV = true;
                // Get the layout inflater
                LayoutInflater inflater = activity.getLayoutInflater();
                View view = inflater.inflate(R.layout.menu_drop_game, null);


                TextView txv_score = view.findViewById(R.id.final_score);
                TextView txv_title = view.findViewById(R.id.titulo);
                LinearLayout ll_score = view.findViewById(R.id.ll_score);
                CircleImageView cim_continue = view.findViewById(R.id.btn_play_drops);
                CircleImageView cim_home = view.findViewById(R.id.btn_home_drops);
                final CircleImageView cim_sound = view.findViewById(R.id.btn_sound_drops);

                builder.setView(view);

                if(sharedP.getInt("currentLevel",0) == 100){
                    txv_title.setText("Fin del juego");
                    ll_score.setVisibility(View.VISIBLE);
                    cim_continue.setImageResource(R.drawable.devolver);
                    txv_score.setText(""+sharedP.getInt("score",0));
                }

                if(sharedP.getBoolean("sound", true)){
                    cim_sound.setImageResource(R.drawable.sonido);
                }else{
                    cim_sound.setImageResource(R.drawable.mute);
                }

                final AlertDialog alertDialog = builder.create();
                alertDialog.show();
                alertDialog.setCanceledOnTouchOutside(false);

                cim_continue.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(sharedP.getInt("currentLevel",0) == 100){
                            editor2.putInt("currentLevel",0);
                            editor2.putInt("score",0);
                            editor2.putBoolean("pause",false);
                            editor2.apply();

                            Intent intent = new Intent(context, AndroidLauncher2.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            context.startActivity(intent);

                        }else {
                            editor2.putBoolean("pause",false);
                            editor2.commit();
                            alertDialog.dismiss();
                        }

                    }
                });

                cim_home.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(context,Practica.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        intent.putExtra("numFragment",0);
                        activity.startActivity(intent);
                    }
                });


                cim_sound.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(sharedP.getBoolean("sound", true)){
                            cim_sound.setImageResource(R.drawable.mute);
                            editor2.putBoolean("sound",false);
                            editor2.commit();

                        }else{
                            cim_sound.setImageResource(R.drawable.sonido);
                            editor2.putBoolean("sound",true);
                            editor2.commit();
                        }
                    }
                });
            }
        });
    }

    @Override
    public void menuGotas(final CharSequence instancia, final int puntaje) {
        final Activity activity = (Activity) context;
        /*
            Here we show a menu to control the game run.
         */
        activity.runOnUiThread(new Runnable() {
            public void run() {

                final AlertDialog.Builder builder = new AlertDialog.Builder(context).setCancelable(false);
                boolean soundV;
                // Get the layout inflater
                LayoutInflater inflater = activity.getLayoutInflater();
                View view = inflater.inflate(R.layout.menu_drop_game, null);

                ImageView play = view.findViewById(R.id.btn_play_drops);
                ImageView home = view.findViewById(R.id.btn_home_drops);
                TextView titulo = view.findViewById(R.id.titulo);
                TextView puntuacion = view.findViewById(R.id.puntuacion);
                final ImageView sound = view.findViewById(R.id.btn_sound_drops);

                if(sharedP.getBoolean("sound_drop",true)){
                    sound.setImageResource(R.drawable.sonido);
                }else{
                    sound.setImageResource(R.drawable.mute);
                }

                builder.setView(view);
                builder.create();
                final AlertDialog alertDialog = builder.show();

                if (instancia.equals("pausa")) {
                    titulo.setText("PAUSA");
                    puntuacion.setText(" ");
                    play.setOnClickListener(new View.OnClickListener() {
                        SharedPreferences.Editor editor = sharedP.edit();

                        @Override
                        public void onClick(View v) {
                            alertDialog.dismiss();
                            editor.putBoolean("pause", false);
                            editor.apply();
                        }
                    });
                } else {
                    titulo.setText("FIN DE JUEGO");
                    puntuacion.setText("Puntuacion final: " + puntaje + "/ 100");
                    play.setImageResource(R.drawable.devolver);
                    play.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(context, Practica.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            intent.putExtra("numFragment", 3);
                            intent.putExtra("showOperation",true);
                            activity.startActivity(intent);
                        }
                    });
                }

                home.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(context,Practica.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        intent.putExtra("numFragment",3);
                        activity.startActivity(intent);
                    }
                });

                sound.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        SharedPreferences.Editor editor = sharedP.edit();
                        if(sharedP.getBoolean("sound_drop", true)){
                            editor.putBoolean("sound_drop", false);
                            sound.setImageResource(R.drawable.mute);
                        }
                        else {
                            editor.putBoolean("sound_drop", true);
                            sound.setImageResource(R.drawable.sonido);
                        }
                        editor.apply();
                    }
                });
            }
        });
    }
}
