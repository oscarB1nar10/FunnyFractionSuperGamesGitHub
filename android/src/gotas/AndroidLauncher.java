package gotas;

import android.os.Bundle;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.funnyfractions.game.tutorials.ActionResolverAndroid;

import game.MainActivity;

public class AndroidLauncher extends AndroidApplication {

	ActionResolverAndroid actionResolver;

	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		Bundle dato = getIntent().getExtras();
		String operacion = dato.getString("operacion");
		actionResolver = new ActionResolverAndroid(this);
		initialize(new MainActivity(operacion, actionResolver), config);
	}
}
