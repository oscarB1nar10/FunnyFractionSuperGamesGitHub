package com.funnyfractions.game.archery_game;

public interface ActionResolver {

    public void showToast(CharSequence toastMessage, int toastDuration);
    public void showAlertBox(String alertBoxTitle, String alertBoxMessage, String alertBoxButtonText);
    public void openUri(String uri);
    public void goToAndroid();
    public void cleanPreferences();
    public void saveScore(int score);
    public void pauseArcheryGame();
    public void menu ();


}
