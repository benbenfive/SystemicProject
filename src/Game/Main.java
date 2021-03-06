package Game;

import Game.Entities.Player;
import Game.testing.NoiseBiomeGen;
import handlers.MovementHandler;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.util.HashSet;
import java.util.Scanner;

/**
 * This is where the setup and running happens. Everything from drawing a frame to
 * deciding what each creature does in that from is decided from here.
 */
public class Main extends Application {

    public static int numOfFrames = 0;

    public static void main(String[] args)
    {
        launch(args);
    }

    private static Stage stage;
    private static Scene mainScene;
    private static Camera root;
    public static int slowVal = 1;

    private static long last_time = System.nanoTime();
    private static int delta_time = 0;
    public static boolean colliding;

    private static HashSet<String> currentlyActiveKeys;

    private static Player player = new Player(Color.AQUA, new Rectangle(6500, 6500, 64, 32));
    public static Group group = new Group();



    @Override
    public void start(Stage stage) {
        Main.stage = stage;
        stage.setTitle("Systemic Project");

        root = new Camera();

        mainScene = new Scene(root);
        stage.setScene(mainScene);


        prepareActionHandlers();

        System.out.println("Would you like to generate a new world file?");
        System.out.println("Yes [1]");
        System.out.println("No [0]");

        Scanner reader = new Scanner(System.in);
        int response = reader.nextInt();

        if (response == 1) {
            NoiseBiomeGen.generateWorld();
            System.out.println("generated");
            //makeWorldFromFile();
        } else {
            boolean exists = true;//new File("map.txt").exists();
            if(exists){
                System.out.println("Running with existing map files");

                for (int chunkY = 0; chunkY < World.getWorldChunkHeight(); chunkY++) {
                    for (int chunkX = 0; chunkX < World.getWorldChunkWidth(); chunkX++) {
                        Chunk.loadChunk(chunkX, chunkY);
                    }
                }
            } else {
                System.out.println("No existing map file");
                System.out.println("Stopping...");
                System.exit(0);
            }
        }

        //Game.Main "game" loop
        new AnimationTimer()
        {
            public void handle(long currentNanoTime)
            {
                numOfFrames ++;

                if (numOfFrames % slowVal == 0) {
                    tickAndRender();
                }
            }

        }.start();

        stage.show();

    }
    
    /**
     * Switches the program in and out of fullscreen
     */
    public static void switchFullscreen() {
        if (stage.isFullScreen()) {
            stage.setFullScreen(false);
        } else {
            stage.setFullScreen(true);
        }
    }
    /**
     * Adds all the action handlers to the game.
     */
    private static void prepareActionHandlers() {
        // HashSets don't allow duplicate values
        //currentlyActiveKeys = new HashSet<String>();
        currentlyActiveKeys = MovementHandler.getCurrentlyActiveKeys();
        mainScene.setOnKeyPressed(e -> {
            currentlyActiveKeys.add(e.getCode().toString());
        });
        mainScene.setOnKeyReleased(e -> {
            player.setHasChangedFullscreen(false);
            currentlyActiveKeys.remove(e.getCode().toString());
        });
    }

    /**
     * A tick is what needs to be done in a frame like plant growth or creature ai choices and movement.
     * This method does all of that and everything related to displaying that frame.
     */
    private static void tickAndRender() {
        // a calculation for the time between updates taken from:
        // https://gamedev.stackexchange.com/questions/111741/calculating-delta-time
        // don't use for now VVV
        long time = System.nanoTime();
        delta_time = (int) ((time - last_time) / 1000000);
        last_time = time;

        player.tick();
        player.setBoundsBox(new Rectangle(player.getPosX(), player.getPosY(),
                player.getBoundsBox().getWidth(), player.getBoundsBox().getHeight()));

        // Plant lag happens here
        //World.tick();

        root.update();
    }


    public static Player getPlayer(){
        return player;
    }

    /**
     * returns the time between frames in milliseconds?
     */
    public static int getDelta_time() {
        return delta_time;
    }

    public static double[] shift(double x, double y){
        int px = (int)Math.round(Main.getPlayer().getPosX());
        int py = (int)Math.round(Main.getPlayer().getPosY());
        int halfWidth = (int)root.getWidth() / 2;
        int halfHeight = (int)root.getHeight() / 2;

        x = x - root.getScreenCenterX() + halfWidth;
        y = y - root.getScreenCenterY() + halfHeight;

        return new double[] {x, y};
    }

    public static Camera getRoot() {
        return root;
    }
}
