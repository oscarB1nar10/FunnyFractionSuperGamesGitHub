package com.funnyfractions.game.tutorials;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.Toast;

import com.funnyfractions.game.archery_game.ActionResolver;

import androidlogic.games.archery_game.MainMenu;
import androidlogic.games.archery_game.MenuArcheryActivity;
import androidlogic.home.Home;

import static android.content.Context.MODE_PRIVATE;

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


}
