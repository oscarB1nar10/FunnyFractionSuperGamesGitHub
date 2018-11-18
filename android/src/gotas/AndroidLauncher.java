package gotas;

import android.os.Bundle;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;

import game.MainActivity;

public class AndroidLauncher extends AndroidApplication {
	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		Bundle dato = getIntent().getExtras();
		String operacion = dato.getString("operacion");
		System.out.println(operacion);
		initialize(new MainActivity(operacion), config);
	}
}
