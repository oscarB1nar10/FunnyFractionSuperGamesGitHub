package androidlogic.games.archery_game;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.funnyfractions.game.R;
import com.funnyfractions.game.tutorials.AndroidLauncher2;

public class MainMenu extends Activity implements View.OnClickListener{

    //widgets
    private Button btn_jugar;
    private LinearLayout main_layout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_division);

        main_layout = findViewById(R.id.main_layout);
        main_layout.setBackgroundResource(R.drawable.fondo_archery_game);

        btn_jugar = findViewById(R.id.btn_jugar);
        btn_jugar.setOnClickListener(this);


        //buttonLevels=findViewById(R.id.levels);
        //buttonLevels.setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.btn_jugar: {
                Intent intent=new Intent(getApplicationContext(),AndroidLauncher2.class);
                startActivity(intent);
                break;
            }
        }

    }
}
