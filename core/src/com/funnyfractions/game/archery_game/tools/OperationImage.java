package com.funnyfractions.game.archery_game.tools;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.funnyfractions.game.archery_game.FirstScreen;

public class OperationImage extends Sprite {


    public OperationImage(){
        super(new Texture(Gdx.files.internal("img1.png")));
        setBounds(Gdx.graphics.getWidth()/2+100,8000/FirstScreen.PPM,33700/FirstScreen.PPM,25600/FirstScreen.PPM);

    }


}
