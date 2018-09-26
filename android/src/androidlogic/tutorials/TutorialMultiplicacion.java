package androidlogic.tutorials;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.funnyfractions.game.R;

public class TutorialMultiplicacion extends AppCompatActivity {
    private Handler h1, h2, h3;
    private ImageView mul1, mul2, retornar;
    private TextView def;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tutoriales_mul);
        h1 = new Handler();
        h2 = new Handler();
        h3 = new Handler();
        mul1 = findViewById(R.id.multi1);
        mul2 = findViewById(R.id.multi2);
        retornar = findViewById(R.id.retornarmul);
        def = findViewById(R.id.txteoriamul);
        h1.postDelayed(new Runnable() {
            @Override
            public void run() {
                def.setVisibility(View.VISIBLE);
                h2.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mul1.setVisibility(View.VISIBLE);
                        h3.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                mul2.setVisibility(View.VISIBLE);
                            }
                        },2000);
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
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.left_in, R.anim.left_out);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mul1.setImageDrawable(null);
        mul2.setImageDrawable(null);
        retornar.setImageDrawable(null);
    }
}
