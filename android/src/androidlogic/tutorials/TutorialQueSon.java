package androidlogic.tutorials;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.funnyfractions.game.R;

public class TutorialQueSon extends AppCompatActivity {
    private ImageView retornar, fraceje, pizcom, pizdiv, anterior, siguiente;
    private TextView txtdef, txteje;
    private Handler h1,h2,h3,h4,h5,h6,h7;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tutoriales_queson);
        h1 = new Handler();
        h2 = new Handler();
        h3 = new Handler();
        h4 = new Handler();
        h5 = new Handler();
        h6 = new Handler();
        h7 = new Handler();
        retornar = findViewById(R.id.retornar);
        fraceje = findViewById(R.id.imgejemplo);
        pizcom = findViewById(R.id.imgpizzacom);
        pizdiv = findViewById(R.id.imgpizzadiv);
        txtdef = findViewById(R.id.txtdefinicion);
        txteje = findViewById(R.id.txtejemplo);
        anterior = findViewById(R.id.back);
        siguiente = findViewById(R.id.next);
        retornar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        h1.postDelayed(new Runnable() {
            @Override
            public void run() {
                txtdef.setVisibility(View.VISIBLE);
                siguiente.setVisibility(View.VISIBLE);
                h2.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        fraceje.setVisibility(View.VISIBLE);
                    }
                }, 4000);
            }
        }, 1000);
        siguiente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                h2.removeCallbacksAndMessages(null);
                txtdef.setVisibility(View.INVISIBLE);
                fraceje.setVisibility(View.INVISIBLE);
                siguiente.setVisibility(View.INVISIBLE);
                anterior.setVisibility(View.VISIBLE);
                h3.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        txteje.setVisibility(View.VISIBLE);
                        h4.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                pizcom.setVisibility(View.VISIBLE);
                                h5.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        pizdiv.setVisibility(View.VISIBLE);
                                    }
                                }, 2000);
                            }
                        }, 3000);
                    }
                }, 1000);
            }
        });
        anterior.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                siguiente.setVisibility(View.VISIBLE);
                anterior.setVisibility(View.INVISIBLE);
                h3.removeCallbacksAndMessages(null);
                h4.removeCallbacksAndMessages(null);
                h5.removeCallbacksAndMessages(null);
                txteje.setVisibility(View.INVISIBLE);
                pizcom.setVisibility(View.INVISIBLE);
                pizdiv.setVisibility(View.INVISIBLE);
                txtdef.setVisibility(View.VISIBLE);
                fraceje.setVisibility(View.VISIBLE);
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.left_in, R.anim.left_out);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        retornar.setImageDrawable(null);
        fraceje.setImageDrawable(null);
        pizcom.setImageDrawable(null);
        pizdiv.setImageDrawable(null);
        anterior.setImageDrawable(null);
        siguiente.setImageDrawable(null);
    }
}