package burbujas;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.funnyfractions.game.R;

public class GameOver extends Activity{

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_over);
    }
}
