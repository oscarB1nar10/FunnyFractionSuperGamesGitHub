package androidlogic.tutorials;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.funnyfractions.game.R;


public class TutorialDivision extends AppCompatActivity {
    private Handler h1, h2, h3;
    private ImageView div1, div2, retornar;
    private TextView def;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tutoriales_div);
        h1 = new Handler();
        h2 = new Handler();
        h3 = new Handler();
        div1 = findViewById(R.id.div1);
        div2 = findViewById(R.id.div2);
        retornar = findViewById(R.id.retornardiv);
        def = findViewById(R.id.txteoriadiv);
        h1.postDelayed(new Runnable() {
            @Override
            public void run() {
                def.setVisibility(View.VISIBLE);
                h2.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        div1.setVisibility(View.VISIBLE);
                        h3.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                div2.setVisibility(View.VISIBLE);
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
        div1.setImageDrawable(null);
        div2.setImageDrawable(null);
        retornar.setImageDrawable(null);
    }
}
