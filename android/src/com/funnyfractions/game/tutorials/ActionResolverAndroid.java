package com.funnyfractions.game.tutorials;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.funnyfractions.game.archery_game.ActionResolver;

import androidlogic.games.archery_game.MainMenu;
import androidlogic.home.Home;

public class ActionResolverAndroid implements ActionResolver {

    Context context;

    public ActionResolverAndroid(Context androidLauncher2) {
        context = androidLauncher2;

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
        Intent intent = new Intent(context, Home.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        intent.putExtra("usuario", "MiUsuario");
        context.startActivity(intent);

    }


}
