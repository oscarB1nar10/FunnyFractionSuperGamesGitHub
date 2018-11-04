package com.funnyfractions.game.archery_game;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class Hud {
    public Stage stage;
    private Viewport viewport;
    private Integer worldLevel;
    private Integer scoreValue;

    Label level, levelName;
    Label score, scoreName;

    public Hud(SpriteBatch sb){
        worldLevel = 0;
        scoreValue = 0;

        viewport = new FitViewport(FirstScreen.V_WIDTH,FirstScreen.V_WIDTH, new OrthographicCamera());
        stage = new Stage(viewport, sb);

        Table table = new Table();
        table.top();
        table.setFillParent(true);

        level = new Label(String.format("%03d", worldLevel), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        score = new Label(String.format("%06d", scoreValue), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        levelName = new Label("LEVEL", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        scoreName = new Label("SCORE", new Label.LabelStyle(new BitmapFont(), Color.WHITE));

        table.add(levelName).expandX().padTop(10);
        table.add(scoreName).expandX().padTop(10);
        table.row();
        table.add(level).expandX();
        table.add(score).expandX();

        stage.addActor(table);


    }

}
