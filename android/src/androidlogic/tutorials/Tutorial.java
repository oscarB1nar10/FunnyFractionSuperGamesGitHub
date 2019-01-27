package androidlogic.tutorials;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.funnyfractions.game.R;

import java.util.ArrayList;

import Util.SumaTutorialInformation;
import adapters.MyAdapter;

public class Tutorial extends Activity {
    private Button qson, sum, res, mul, div;
    private ImageButton hom;
    private ImageView img;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tutoriales);
        qson = (Button) findViewById(R.id.btntuto_def);
        sum = (Button) findViewById(R.id.btntuto_suma);
        res = (Button) findViewById(R.id.btntuto_resta);
        mul = (Button) findViewById(R.id.btntuto_multli);
        div = (Button) findViewById(R.id.btntuto_divi);
        hom = (ImageButton) findViewById(R.id.btntuto_home);
        img = (ImageView) findViewById(R.id.img_fracc);
        if(sum.getText().toString().equals("Suma")){
            img.setImageResource(R.drawable.fracciones);
        }
        else{
            img.setImageResource(R.drawable.fractions);
        }
        hom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        qson.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), TutorialQueSon.class);
                startActivity(intent);
                overridePendingTransition(R.anim.right_in,R.anim.right_out);
            }
        });
        sum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), TutorialSuma.class);
                startActivity(intent);
                overridePendingTransition(R.anim.right_in,R.anim.right_out);
            }
        });
        res.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), TutorialResta.class);
                startActivity(intent);
                overridePendingTransition(R.anim.right_in, R.anim.right_out);
            }
        });
        mul.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), TutorialMultiplicacion.class);
                startActivity(intent);
                overridePendingTransition(R.anim.right_in, R.anim.right_out);
            }
        });
        div.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), TutorialDivision.class);
                startActivity(intent);
                overridePendingTransition(R.anim.right_in, R.anim.right_out);
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.zoom_back_in, R.anim.zoom_back_out);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        hom.setImageDrawable(null);
        img.setImageDrawable(null);
    }
}
