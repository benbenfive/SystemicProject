import Game.ImprovedNoise;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import org.w3c.dom.css.Rect;

public class Noise extends Application {

    public static void main(String[] args) {
        launch(args);

    }

    @Override
    public void start(Stage stage) {
        Pane root = new Pane();
        Scene scene = new Scene(root, 1000, 1000);



        new AnimationTimer()
        {
            public void handle(long currentNanoTime)
            {
                tickAndRender(root);
            }
        }.start();



        stage.setScene(scene);
        stage.show();



    }

    public void tickAndRender(Pane root){
        Rectangle[][] array = ImprovedNoise.getNoiseArray();
        for (int i = 0; i < array.length - 1; i++) {
            for (int j = 0; j < array.length - 1; j++) {
                root.getChildren().add(array[i][j]);
            }
        }
    }
}