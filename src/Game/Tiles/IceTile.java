package Game.Tiles;

import javafx.scene.image.Image;
import javafx.scene.paint.Color;

/**
 * An ice tile has no interesting things about it.
 */
public class IceTile extends Tile {
    public static final String tileCode = "i";

    double movementSpeed;
    //static Image texture;
    static boolean isTextured = true;

    public IceTile(int posX, int posY) {
        super(posX, posY, Color.LIGHTBLUE);
        this.movementSpeed = 5;
        this.isInteractiveTile = true;
    }

    public Image getTexture() {
        return texture;
    }

    @Override
    public String getTileCode() {
        return tileCode;
    }
}
