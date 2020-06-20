package Game.Tiles;

import Game.plants.Plant;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;


public abstract class Tile {

    static int width = 16 * 4;

    Color backColor;
    int posX;
    int posY;
    static boolean isTextured = false;
    boolean isInteractiveTile;
    private static int mapWidth;
    private static int mapHeight;
    private static Tile[][] allTiles;    // TODO: fill array with Tiles and calculate size of array
    private static int xCount = 0;
    private static int yCount = 0;
    Rectangle boundsBox;
    Image texture;
    boolean isSolid;

    protected ArrayList<Plant> plants = new ArrayList<>(); // Plants growing on this tile.

    public Tile(int posX, int posY, Color backColor){
        this.posX = posX;
        this.posY = posY;

        this.backColor = backColor;

        this.isTextured = false;

        this.boundsBox = new Rectangle(this.posX, this.posY, width, width);
        //this.boundsBox.setFill(backColor);

        allTiles[xCount][yCount] = this;
        xCount ++;
        if(xCount >= allTiles.length){
            yCount++;
            xCount = 0;
        }
    }

    public Tile(int posX, int posY){
        this.posX = posX;
        this.posY = posY;

        this.isTextured = true;

        this.boundsBox = new Rectangle(this.posX, this.posY, width, width);
        //this.boundsBox.setFill(backColor);

        allTiles[xCount][yCount] = this;
        xCount ++;
        if(xCount >= allTiles.length){
            yCount++;
            xCount = 0;
        }
    }

    public Rectangle getBoundsBox() {
        return boundsBox;
    }

    public void boundingLines(){         /* TODO: either use rectangle object for ray intersection or four lines
                                            TODO: that represent the rectangle */

    }

    public static Tile[][] getAllTiles() {
        return allTiles;
    }

    public Image getTexture() {
        return this.texture;
    }


    public static int getWidth() {
        return width;
    }

    public Color getBackColor() {
        return backColor;
    }

    public int getPosX() {
        return posX;
    }

    public int getPosY() {
        return posY;
    }

    public boolean isTextured() {
        return isTextured;
    }

    public boolean isInteractiveTile() {
        return isInteractiveTile;
    }

    public boolean isSolid() {
        return isSolid;
    }

    public static void setMapDimensions(int width, int height){
        mapWidth = width;
        mapHeight = height;
        allTiles = new Tile[mapWidth][mapHeight];
    }

    public static int getMapWidth() {
        return mapWidth;
    }

    public static int getMapHeight() {
        return mapHeight;
    }

    public void setTexture(Image texture) {
        this.texture = texture;
        isTextured = true;
    }

    /**
     * adds a plant to the tiles list of plants growing on it
     */
    public void addPlant(Plant plant){
        if(plant.canGrowOn(getTileCode())){
            plants.add(plant);
        }
    }

    /**
     * Every tile needs a string code to be printed onto a file. It should be stored
     * in a public static final string and this should return that string.
     */
    abstract String getTileCode();

    /**
     * simply tells all the plants on this tile to grow
     */
    protected void tickPlants(){
        for (int i = 0; i < plants.size(); i++) {
            plants.get(i).tick();
        }
    }

    /**
     * By default this method only tells plants to be ticked but can be overridden
     * in the subclass for something like a furnace or fire or who knows what that
     * needs to do more than just tick its plants.
     */
    public void tick(){
        tickPlants();
    }

    /**
     * Returns an arraylist of all the coordinates of each plant on this tile.
     */
    public ArrayList<Plant> getPlants(){
        return plants;
    }
}
