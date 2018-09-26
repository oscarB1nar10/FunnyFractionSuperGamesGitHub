package androidlogic.tutorials;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.funnyfractions.game.R;

public class TutorialResta extends AppCompatActivity {
    private TextView def, def2, titulo;
    private ImageView res1, res2, res3, res4, retornar, back, next;
    private Handler h1, h2, h3, h4, h5, h6;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tutoriales_resta);
        titulo = findViewById(R.id.titulo_resta);
        def = findViewById(R.id.txteoriares);
        def2 = findViewById(R.id.txteoriares2);
        res1 = findViewById(R.id.resta1);
        res2 = findViewById(R.id.resta2);
        res3 = findViewById(R.id.resta3);
        res4 = findViewById(R.id.resta4);
        retornar = findViewById(R.id.retornares);
        back = findViewById(R.id.backres);
        next = findViewById(R.id.nextres);
        h1 = new Handler();
        h2 = new Handler();
        h3 = new Handler();
        h4 = new Handler();
        h5 = new Handler();
        h6 = new Handler();
        h1.postDelayed(new Runnable() {
            @Override
            public void run() {
                def.setVisibility(View.VISIBLE);
                next.setVisibility(View.VISIBLE);
                h2.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        res1.setVisibility(View.VISIBLE);
                        h3.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                res2.setVisibility(View.VISIBLE);
                            }
                        }, 2000);
                    }
                }, 2000);
            }
        }, 2000);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                titulo.setText(R.string.txtreshete);
                next.setVisibility(View.INVISIBLE);
                h2.removeCallbacksAndMessages(null);
                h3.removeCallbacksAndMessages(null);
                def.setVisibility(View.INVISIBLE);
                res1.setVisibility(View.INVISIBLE);
                res2.setVisibility(View.INVISIBLE);
                def2.setVisibility(View.VISIBLE);
                back.setVisibility(View.VISIBLE);
                h4.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        res3.setVisibility(View.VISIBLE);
                        h5.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                res4.setVisibility(View.VISIBLE);
                            }
                        }, 2000);
                    }
                }, 3000);
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                next.setVisibility(View.VISIBLE);
                back.setVisibility(View.INVISIBLE);
                h4.removeCallbacksAndMessages(null);
                h5.removeCallbacksAndMessages(null);
                def2.setVisibility(View.INVISIBLE);
                res3.setVisibility(View.INVISIBLE);
                res4.setVisibility(View.INVISIBLE);
                def.setVisibility(View.VISIBLE);
                res1.setVisibility(View.VISIBLE);
                res2.setVisibility(View.VISIBLE);

            }
        });
        retornar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
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
        res1.setImageDrawable(null);
        res2.setImageDrawable(null);
        res3.setImageDrawable(null);
        res4.setImageDrawable(null);
        retornar.setImageDrawable(null);
        back.setImageDrawable(null);
        next.setImageDrawable(null);
    }
}
