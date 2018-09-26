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

    private Button buttonLevels;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.archery_layout);

        buttonLevels=findViewById(R.id.levels);
        buttonLevels.setOnClickListener(this);


    }


    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.levels: {
                Intent intent=new Intent(getApplicationContext(),AndroidLauncher2.class);
                startActivity(intent);
                break;
            }
        }

    }
}
