package androidlogic.games.archery_game;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.funnyfractions.game.R;
import com.funnyfractions.game.tutorials.AndroidLauncher2;

import interfaces.PracticeAndFragments;

public class MainMenu extends Fragment implements View.OnClickListener{

    //widgets
    private Button btn_jugar;
    private LinearLayout main_layout;
    
    //vars
    OnFragmentInteractionListener mListener;
    PracticeAndFragments practiceAndFragments;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        //practiceAndFragments = getArguments().getParcelable("practiceAndFrangment");
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.menu_division, container, false);

        main_layout = view.findViewById(R.id.main_layout);
        main_layout.setBackgroundResource(R.drawable.fondo_archery_game);

        btn_jugar = view.findViewById(R.id.btn_jugar);
        btn_jugar.setOnClickListener(this);

        return view;
    }


    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.btn_jugar: {
                Intent intent=new Intent(getContext(),AndroidLauncher2.class);
                startActivity(intent);
                break;
            }
        }

    }
}
