package com.funnyfractions.game.tutorials;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.funnyfractions.game.R;
import com.funnyfractions.game.archery_game.ActionResolver;

import androidlogic.games.archery_game.MenuArcheryActivity;
import androidlogic.practice.Practica;

public class ActionResolverAndroid implements ActionResolver {



    Context context;
    SharedPreferences sharedP;

    public ActionResolverAndroid(Context androidLauncher2) {
        context = androidLauncher2;
        sharedP = androidLauncher2.getSharedPreferences("SHARED_PREFERENCES",Context.MODE_PRIVATE);

    }

    @Override
    public void showToast(CharSequence toastMessage, int toastDuration) {
        Toast.makeText(context,toastMessage,toastDuration).show();
    }

    @Override
    public void showAlertBox(String alertBoxTitle, String alertBoxMessage, String alertBoxButtonText) {

    }

    @Override
    public void openUri(String uri) {

    }

    @Override
    public void goToAndroid() {
        SharedPreferences.Editor editor2 = sharedP.edit();
        SharedPreferences prefs = context.getSharedPreferences("SHARED_PREFERENCES", Context.MODE_PRIVATE);

        if(prefs.getInt("currentLevel",-1) >0){
            int value = prefs.getInt("currentLevel",-1);
            value++;
            editor2.putInt("currentLevel", value);
            editor2.apply();
        }else{
            SharedPreferences.Editor editor = sharedP.edit();
            editor.putInt("currentLevel", 1);
            editor.commit();
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
        Intent intent = new Intent(context, MenuArcheryActivity.class);
        //intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        //intent.putExtra("usuario", "MiUsuario");
        context.startActivity(intent);
    }

    @Override
    public void menu() {
         createMenu();

    }

    public void createMenu (){

        final Activity activity = (Activity) context;

        /*
            Here we show a menu to control the game run.
         */
        activity.runOnUiThread(new Runnable() {
            public void run() {
                //here it's necessary get reference to shared preferences
                SharedPreferences prefs = context.getSharedPreferences("SHARED_PREFERENCES", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor2 = sharedP.edit();
                final AlertDialog.Builder builder = new AlertDialog.Builder(context);
                 boolean soundV = true;
                // Get the layout inflater
                 LayoutInflater inflater = activity.getLayoutInflater();
                View view = inflater.inflate(R.layout.pause_menu_archery_game, null);

                ImageButton restar = view.findViewById(R.id.btn_restar);
                ImageButton play = view.findViewById(R.id.btn_play);
                ImageView stars = view.findViewById(R.id.imgv_stars);
                ImageButton home = view.findViewById(R.id.btn_home);
                final ImageButton sound = view.findViewById(R.id.btn_sound);


                //check whether the sound state
                if(prefs.getBoolean("sound",true) == true){
                    sound.setImageResource(R.drawable.sonido);
                    soundV = true;
                }else{
                    sound.setImageResource(R.drawable.mute);
                    soundV = false;
                }
                // Inflate and set the layout for the dialog
                // Pass null as the parent view because its going in the dialog layout
                builder.setView(view);
                // Add action buttons
                builder.create();
                final AlertDialog alertDialog = builder.show();
                alertDialog.setCanceledOnTouchOutside(false);

                //check the level state
                if(prefs.getInt("currentLevel",-1) == 2){
                    if (prefs.getInt("score",-1)>=700 && prefs.getInt("score",-1)<=1000){

                        editor2.putInt("currentLevel",0);
                        editor2.commit();
                        stars.setImageResource(R.drawable.star100);
                        play.setEnabled(false);
                    }else if(prefs.getInt("score",-1)>=500 && prefs.getInt("score",-1)<=699){
                        editor2.putInt("currentLevel",0);
                        editor2.commit();
                        stars.setImageResource(R.drawable.star70);
                        play.setEnabled(false);
                    }else{
                        editor2.putInt("currentLevel",0);
                        editor2.commit();
                        stars.setImageResource(R.drawable.star30);
                        play.setEnabled(false);
                    }
                }else{
                    stars.setVisibility(View.INVISIBLE);
                }




                restar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        SharedPreferences.Editor editor2 = sharedP.edit();
                        editor2.putInt("currentLevel",0);
                        editor2.putInt("score",0);
                        editor2.commit();
                        Intent intent = new Intent(context, AndroidLauncher2.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(intent);
                    }
                });

                play.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alertDialog.dismiss();
                        SharedPreferences.Editor editor2 = sharedP.edit();
                        editor2.putBoolean("pause",false);
                        editor2.commit();

                    }

                });

                final boolean finalSoundV = soundV;
                sound.setOnClickListener(new View.OnClickListener() {
                    private boolean so = finalSoundV;
                    @Override
                    public void onClick(View v) {
                        if(so){
                            sound.setImageResource(R.drawable.mute);
                            SharedPreferences.Editor editor2 = sharedP.edit();
                            editor2.putBoolean("sound",false);
                            editor2.commit();
                            so = false;
                        }else{
                            sound.setImageResource(R.drawable.sonido);
                            SharedPreferences.Editor editor2 = sharedP.edit();
                            editor2.putBoolean("sound",true);
                            editor2.commit();
                            so = true;
                        }


                    }
                });

                home.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(context, Practica.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(intent);
                    }
                });


            }
        });


    }


}
