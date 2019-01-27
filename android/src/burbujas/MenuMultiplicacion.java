package burbujas;

import android.support.v4.app.Fragment;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.funnyfractions.game.R;

public class MenuMultiplicacion extends Fragment {
    public Button jugar, configuraciones, instrucciones;
    OnFragmentInteractionListener mListener;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.menu_multiplicacion, container, false);
        jugar = view.findViewById(R.id.btn_jugar_multiplicacion);
        configuraciones = view.findViewById(R.id.btn_settings_multiplicacion);
        instrucciones = view.findViewById(R.id.btn_instrucciones_multiplicacion);
        jugar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), BubblesMain.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });
        return view;
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }
}
