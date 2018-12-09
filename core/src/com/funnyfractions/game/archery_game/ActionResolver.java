package com.funnyfractions.game.archery_game;

public interface ActionResolver {

    void showToast(CharSequence toastMessage, int toastDuration);
    void goToAndroid();
    void cleanPreferences();
    void saveScore(int score);
    void pauseArcheryGame();
    void menu ();
    void menuGotas();
}
