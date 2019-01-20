package burbujas;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;

import com.funnyfractions.game.R;

public class MenuMultiplicacion extends Activity {
    public Button jugar, configuraciones, instrucciones;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_multiplicacion);
        jugar = findViewById(R.id.btn_jugar_multiplicacion);
        configuraciones = findViewById(R.id.btn_settings_multiplicacion);
        instrucciones = findViewById(R.id.btn_instrucciones_multiplicacion);
        jugar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), BubblesMain.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });
    }


}
