package Game.Tiles;

import javafx.scene.image.Image;
import javafx.scene.paint.Color;

/**
 * A dirt tile has no interesting things about it.
 */
public class DirtTile extends Tile {
    public static final String tileCode = "d";

    double movementSpeed;
    //static boolean isTextured = true;

    public DirtTile(int posX, int posY) {
        super(posX, posY, Color.BROWN);
        this.movementSpeed = 5;
        this.isInteractiveTile = true;
    }

    public Image getImage() {
        return new Image(this.url);
    }

    @Override
    public String getTileCode() {
        return tileCode;
    }
}
