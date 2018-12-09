package game;

import com.badlogic.gdx.graphics.Texture;

public class Drops extends Texture {

    String description = "";
    public Drops(String internalPath, String description) {
        super(internalPath);
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
