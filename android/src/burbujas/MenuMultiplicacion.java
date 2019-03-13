package burbujas;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.v4.app.Fragment;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.funnyfractions.game.R;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class MenuMultiplicacion extends Fragment implements View.OnClickListener {
    public Button jugar, btn_sound, instrucciones;
    CircleImageView civ_sound_menu;
    OnFragmentInteractionListener mListener;
    private SharedPreferences.Editor editor;
    private SharedPreferences sharedP;
    private ObjectAnimator objectAnimator1;
    private boolean soundOk;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.menu_multiplicacion, container, false);
        jugar = view.findViewById(R.id.btn_jugar_multiplicacion);
        btn_sound = view.findViewById(R.id.btn_settings_multiplicacion);
        instrucciones = view.findViewById(R.id.btn_instrucciones_multiplicacion);
        civ_sound_menu = view.findViewById(R.id.btn_sound_multi);
        sharedP = getContext().getSharedPreferences("SHARED_PREFERENCES", Context.MODE_PRIVATE);
        soundOk = sharedP.getBoolean("sound_rain",true);
        editor = sharedP.edit();
        jugar.setOnClickListener(this);
        btn_sound.setOnClickListener(this);
        instrucciones.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_jugar_multiplicacion:
                Intent intent = new Intent(getActivity(), BubblesMain.class);
                startActivity(intent);
                break;
            case R.id.btn_instrucciones_multiplicacion:
                instrucciones();

            case R.id.btn_settings_multiplicacion:
                btn_sound.setEnabled(false);
                civ_sound_menu.setVisibility(View.VISIBLE);
                if(soundOk) {
                    civ_sound_menu.setImageResource(R.drawable.mute);
                    editor.putBoolean("sound_rain", false);
                    soundOk = false;
                }else{
                    civ_sound_menu.setImageResource(R.drawable.sonido);
                    editor.putBoolean("sound_rain", true);
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

    private void instrucciones() {

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

        imageView.setImageResource(R.drawable.burbujas_ins1);
        imageView.setLayoutParams(params);
        imageView.setAdjustViewBounds(true);

        imageView2.setImageResource(R.drawable.burbujas_ins2);
        imageView2.setLayoutParams(params);
        imageView2.setAdjustViewBounds(true);

        imageView3.setImageResource(R.drawable.burbujas_ins3);
        imageView3.setLayoutParams(params);
        imageView3.setAdjustViewBounds(true);

        imageView4.setImageResource(R.drawable.burbujas_ins4);
        imageView4.setLayoutParams(params);
        imageView4.setAdjustViewBounds(true);

        imageView5.setImageResource(R.drawable.burbujas_ins5);
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

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }
}
