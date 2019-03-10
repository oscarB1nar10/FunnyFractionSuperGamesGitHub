package androidlogic.practice;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.funnyfractions.game.R;

public class PracticeCover extends Fragment {
    ImageView home;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        FrameLayout fondo;
        SharedPreferences sharedPreferences;
        View vista = inflater.inflate(R.layout.fragment_practice_cover, container, false);
        home = vista.findViewById(R.id.homepractice);
        fondo = vista.findViewById(R.id.cover_page_practices);
        sharedPreferences =  this.getActivity().getSharedPreferences("SHARED_PREFERENCES",Context.MODE_PRIVATE);
        if(sharedPreferences.getString("idioma","Spanish").equals("Spanish")){
            fondo.setBackgroundResource(R.drawable.practices_cover_page);
        }
        else {
            fondo.setBackgroundResource(R.drawable.practices_cover_page_english);
        }
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });
        return vista;
    }

}
