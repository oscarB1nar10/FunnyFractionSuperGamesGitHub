package androidlogic.tutorials;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.funnyfractions.game.R;

public class TutorialSuma extends AppCompatActivity {
    private Handler h1,h2,h3,h4,h5,h6;
    private TextView titulo, definicion, definicion2;
    private ImageView sum1,sum2,sum3,sum4, back, next, retornar;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tutoriales_suma);
        titulo = findViewById(R.id.titulo_suma);
        definicion = findViewById(R.id.txteoriasum);
        definicion2 = findViewById(R.id.txteoriasum2);
        sum1 = findViewById(R.id.suma1);
        sum2 = findViewById(R.id.suma2);
        sum3 = findViewById(R.id.suma3);
        sum4 = findViewById(R.id.suma4);
        back = findViewById(R.id.backsum);
        next = findViewById(R.id.nextsum);
        retornar = findViewById(R.id.retornarsum);
        h1 = new Handler();
        h2 = new Handler();
        h3 = new Handler();
        h4 = new Handler();
        h5 = new Handler();
        h6 = new Handler();
        h1.postDelayed(new Runnable() {
            @Override
            public void run() {
                definicion.setVisibility(View.VISIBLE);
                next.setVisibility(View.VISIBLE);
                h2.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        sum1.setVisibility(View.VISIBLE);
                        h3.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                sum2.setVisibility(View.VISIBLE);
                            }
                        }, 2000);
                    }
                }, 2000);
            }
        }, 2000);
        retornar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               h1.removeCallbacksAndMessages(null);
               h2.removeCallbacksAndMessages(null);
               h3.removeCallbacksAndMessages(null);
               titulo.setText(R.string.txtsumhete);
               definicion.setVisibility(View.INVISIBLE);
               sum1.setVisibility(View.INVISIBLE);
               sum2.setVisibility(View.INVISIBLE);
               next.setVisibility(View.INVISIBLE);
               back.setVisibility(View.VISIBLE);
               h4.postDelayed(new Runnable() {
                   @Override
                   public void run() {
                       definicion2.setVisibility(View.VISIBLE);
                       h5.postDelayed(new Runnable() {
                           @Override
                           public void run() {
                               sum3.setVisibility(View.VISIBLE);
                               h6.postDelayed(new Runnable() {
                                   @Override
                                   public void run() {
                                       sum4.setVisibility(View.VISIBLE);
                                   }
                               }, 2000);
                           }
                       }, 3000);
                   }
               }, 2000);
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                back.setVisibility(View.INVISIBLE);
                next.setVisibility(View.VISIBLE);
                h4.removeCallbacksAndMessages(null);
                h5.removeCallbacksAndMessages(null);
                h6.removeCallbacksAndMessages(null);
                definicion2.setVisibility(View.INVISIBLE);
                sum3.setVisibility(View.INVISIBLE);
                sum4.setVisibility(View.INVISIBLE);
                definicion.setVisibility(View.VISIBLE);
                sum1.setVisibility(View.VISIBLE);
                sum2.setVisibility(View.VISIBLE);
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
        sum1.setImageDrawable(null);
        sum2.setImageDrawable(null);
        sum3.setImageDrawable(null);
        sum4.setImageDrawable(null);
        retornar.setImageDrawable(null);
        back.setImageDrawable(null);
        next.setImageDrawable(null);
    }
}
