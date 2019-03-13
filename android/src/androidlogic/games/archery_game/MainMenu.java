package androidlogic.games.archery_game;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.funnyfractions.game.R;
import com.funnyfractions.game.tutorials.AndroidLauncher2;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;
import interfaces.PracticeAndFragments;

public class MainMenu extends Fragment implements View.OnClickListener{

    //widgets
    private Button btn_jugar, btn_instructions, btn_sound;
    private CircleImageView civ_sound_menu;
    private ImageView logomenu;
    private LinearLayout main_layout;
    private SharedPreferences.Editor editor;
    private SharedPreferences sharedP;
    private ObjectAnimator objectAnimator1;
    private boolean soundOk;
    
    //vars
    OnFragmentInteractionListener mListener;
    PracticeAndFragments practiceAndFragments;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        //practiceAndFragments = getArguments().getParcelable("practiceAndFrangment");
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.menu_division, container, false);
        logomenu = view.findViewById(R.id.logomenu);
        logomenu.setBackgroundResource(R.drawable.logo_suma);

        main_layout = view.findViewById(R.id.main_layout);
        main_layout.setBackgroundResource(R.drawable.fondo_archery_game);


        btn_jugar = view.findViewById(R.id.btn_jugar);
        civ_sound_menu = view.findViewById(R.id.btn_sound_menu);
        btn_instructions = view.findViewById(R.id.btn_instrucciones);
        btn_sound = view.findViewById(R.id.btn_sound);
        btn_jugar.setOnClickListener(this);
        btn_sound.setOnClickListener(this);
        btn_instructions.setOnClickListener(this);

        sharedP = getContext().getSharedPreferences("SHARED_PREFERENCES", Context.MODE_PRIVATE);
        soundOk = sharedP.getBoolean("sound",true);
        editor = sharedP.edit();
        editor.putInt("currentLevel", 0);
        editor.putInt("score",0);
        editor.apply();

        return view;
    }


    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }

    private void instructions(){
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

        imageView.setImageResource(R.drawable.archery_ins1);
        imageView.setLayoutParams(params);
        imageView.setAdjustViewBounds(true);

        imageView2.setImageResource(R.drawable.archery_ins4);
        imageView2.setLayoutParams(params);
        imageView2.setAdjustViewBounds(true);

        imageView3.setImageResource(R.drawable.archery_ins3);
        imageView3.setLayoutParams(params);
        imageView3.setAdjustViewBounds(true);

        imageView4.setImageResource(R.drawable.archery_ins5);
        imageView4.setLayoutParams(params);
        imageView4.setAdjustViewBounds(true);

        imageView5.setImageResource(R.drawable.archery_ins2);
        imageView5.setLayoutParams(params);
        imageView5.setAdjustViewBounds(true);


        imagen.add(imageView);
        imagen.add(imageView2);
        imagen.add(imageView3);
        imagen.add(imageView4);
        imagen.add(imageView5);

        for(ImageView item : imagen) {
            layoutdialog.addView(item);
        }
        builder.setView(view);
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.btn_jugar:
                Intent intent = new Intent(getContext(), AndroidLauncher2.class);
                startActivity(intent);
                break;

            case R.id.btn_instrucciones:
                instructions();
                break;

            case R.id.btn_sound:
                btn_sound.setEnabled(false);
                civ_sound_menu.setVisibility(View.VISIBLE);
                if(soundOk) {
                    civ_sound_menu.setImageResource(R.drawable.mute);
                    editor.putBoolean("sound", false);
                    soundOk = false;
                }else{
                    civ_sound_menu.setImageResource(R.drawable.sonido);
                    editor.putBoolean("sound", true);
                    soundOk = true;
                }
                editor.apply();

                objectAnimator1 = ObjectAnimator.ofFloat(civ_sound_menu, "translationY", 0f, -500);
                objectAnimator1.setDuration(1000);

                objectAnimator1.addListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        civ_sound_menu.clearAnimation();
                        civ_sound_menu.animate().alpha(1.0f).setDuration(1);
                        civ_sound_menu.setVisibility(View.INVISIBLE);
                        btn_sound.setEnabled(true);
                        objectAnimator1.cancel();
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {

                    }
                });
                civ_sound_menu.animate().alpha(0.0f).setDuration(700);
                objectAnimator1.start();

                break;
        }

    }
}
