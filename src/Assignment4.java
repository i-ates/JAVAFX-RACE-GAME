import javafx.animation.*;
import javafx.application.Application;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.input.KeyEvent;


import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class Assignment4 extends Application {


    private long start = System.nanoTime();
    private Canvas canvas;
    private int WIDTH = 500;
    private int HEIGHT = 700;
    private int carWidth = 40;
    private int carHeight = 60;
    private int mainCarX = 0;
    private int mainCarY = 0;
    private int enemyX = 0;
    private int enemyY = 0;
    private Random rand;
    private int speed = 0;
    private int score = 0;
    private int level = 1;
    private Rectangle mainCar;
    private Rectangle enemyCar;
    private int index=1;
    private ArrayList<Rectangle> enemyArray = new ArrayList<>();
    private Group root;
    private int levelCountrer =0;
    private GraphicsContext scoreLevelTable;
    private boolean increaseScore = true;
    private boolean increaseLevel = true;
    private Stage st;
    private DoubleProperty rectanglerVelocity = new SimpleDoubleProperty();

    public static void main(String[] args) {
        launch(args);
    }

    private void gameOver(Canvas canvas){
        //// Game over part is ...

        Font theFont = Font.font( "Times New Roman", FontWeight.BOLD, 35 );

        Text text;
        text = new Text(100,100,"GAME OVER!");

        text.setStroke(Color.BLACK);
        text.setFont(theFont);
        text.setVisible(true);
        text.setFill(Color.RED);
        root.getChildren().add(text);

        text = new Text (70, 150, "Your Score: ");
        text.setStroke(Color.BLACK);
        text.setFont(theFont);
        text.setVisible(true);
        text.setFill(Color.RED);
        root.getChildren().add(text);

        text = new Text (330, 150, Integer.toString(score));
        text.setStroke(Color.BLACK);
        text.setFont(theFont);
        text.setVisible(true);
        text.setFill(Color.RED);
        root.getChildren().add(text);

        text = new Text(20, 200, "Press ENTER to restart!");
        text.setStroke(Color.BLACK);
        text.setFont(theFont);
        text.setVisible(true);
        text.setFill(Color.RED);
        root.getChildren().add(text);


    }


    public void start(Stage theStage) {


        //Spinner <Integer> spinner = new Spinner<>(Integer.MIN_VALUE, Integer.MAX_VALUE,0);

        this.st = theStage;
        theStage.setTitle("\tHUBBM-Racer");
        /// Color all map
        root = new Group();

        Scene scene1 = new Scene(root);


        theStage.setScene(scene1);
        scene1.setFill(Color.GRAY);


        //label.setAlignment(Pos.BASELINE_CENTER);
        //label.setVisible(true);


        //// Color side way .....
        Rectangle l1 = new Rectangle(0, 0, 100, HEIGHT);
        l1.setFill(Color.GREEN);

        Rectangle l2 = new Rectangle(WIDTH - 100, 0, 100, HEIGHT);
        l2.setFill(Color.GREEN);
        root.getChildren().addAll(l1, l2);
        /////

        //// Road line part ....
        int lineStartY = HEIGHT;
        int lineEndY = HEIGHT - 50;
        for (int i = 0; i < 10; i++) {
            Line line = new Line(WIDTH / 2, lineStartY, WIDTH / 2, lineEndY);
            lineStartY -= 100;
            lineEndY -= 100;
            line.setStroke(Color.BLACK);
            line.setStrokeWidth(4);
            root.getChildren().addAll(line); // adding lines in root....
        }
        ////

        canvas = new Canvas(WIDTH, HEIGHT);

        // adding canvas in root ....
        canvas.setFocusTraversable(true);
        canvas.setOnKeyPressed(keyReleased);
        root.getChildren().add(canvas);


        mainCarX = WIDTH / 2 + 15;
        mainCarY = HEIGHT - 70-150;

        rand = new Random();



        mainCar = new Rectangle(mainCarX, mainCarY, carWidth, carHeight);
        mainCar.setFill(Color.RED);
        mainCar.setStroke(Color.BLACK);

        scoreLevelTable = canvas.getGraphicsContext2D();
        scoreLevelTable.setLineWidth(1);
        scoreLevelTable.setStroke(Color.GREY);
        Font theFont2 = Font.font( "Helvetica", FontWeight.BOLD, 17  );
        scoreLevelTable.setFont( theFont2 );

        String strScore = "Score: " + score;
        String strLevel = "Level: " + level;
        scoreLevelTable.fillText( strScore, 40, 20 );
        scoreLevelTable.strokeText( strScore, 40, 20 );


        scoreLevelTable.fillText( strLevel, 40, 40 );
        scoreLevelTable.strokeText( strLevel, 40, 40 );

        enemyX = ThreadLocalRandom.current().nextInt(120, 300);
        enemyY = ThreadLocalRandom.current().nextInt(100, 500);
        enemyCar = new Rectangle(enemyX, enemyY, carWidth, carHeight);
        enemyCar.setFill(Color.YELLOW);
        enemyCar.setStroke(Color.BLACK);
        //root.getChildren().add(enemyCar);
        timer.start();

        root.getChildren().add(mainCar);
        theStage.show();

    }
    private EventHandler<KeyEvent> keyPressed = new EventHandler<KeyEvent>(){

        @Override
        public void handle(KeyEvent keyEvent) {

            if (keyEvent.getCode() == KeyCode.ENTER){
                st.close();
                Assignment4 d = new Assignment4();
                Stage stage = new Stage();
                d.start(stage);
            }
        }
    };


    private EventHandler<KeyEvent> keyReleased  = new EventHandler<KeyEvent>() {
        @Override
        public void handle(KeyEvent keyEvent) {
            if (keyEvent.getCode() == KeyCode.UP) {

                speed ++;


            } else if (keyEvent.getCode() == KeyCode.DOWN) {

                if (speed > 0) {
                    speed--;
                }else{
                    speed = 5;
                }



            } else if (keyEvent.getCode() == KeyCode.RIGHT) {


                if (100 > mainCar.getX() || mainCar.getX()< WIDTH - 140 ) {
                    //rectanglerVelocity.set(speed++);
                    //
                    //rectanglerVelocity.set(mainCar.getX()); // rectanglerVelocity.get()+10
                    mainCar.setX(mainCar.getX() + 10);
                }



            } else if (keyEvent.getCode() == KeyCode.LEFT) {

                if (mainCar.getX() > 100) {
                    mainCar.setX(mainCar.getX() - 10);
                }
            }
        }

    };


    private AnimationTimer timer = new AnimationTimer() {
        @Override
        public void handle(long l) {

            DrawEnemy();

            AddEnemy();

            DeleteEnemy();


        }
    };




    private void DeleteEnemy() {



        for (int i=enemyArray.size()-1;i>=0;i--){

            enemyArray.get(i).setY( enemyArray.get(i).getY()+speed);

            if (enemyArray.get(i).getY() >= mainCar.getY()) {


                if (increaseScore) {
                    score++;
                    levelCountrer++;
                    increaseLevel = true;
                    increaseScore = false;
                }

                enemyArray.get(i).setFill(Color.GREEN);

                Text text;
                text = new Text(100,100,"GAME OVER!");


                String strScore = "Score: " + score;

                scoreLevelTable.fillText(strScore, 40, 20);
                scoreLevelTable.strokeText(strScore, 40, 20);

            }

            if (enemyArray.get(i).intersects(mainCar.getLayoutBounds())){

                mainCar.setFill(Color.BLACK);
                enemyArray.get(i).setFill(Color.BLACK);
                timer.stop();
                gameOver(canvas);
                canvas.setOnKeyPressed(keyPressed);

            }

            if (levelCountrer == 5){
                if (increaseLevel){
                    level++;
                    increaseLevel = false;
                }
                levelCountrer = 0;


                String strLevel = "Level: " + level;
                scoreLevelTable.fillText( strLevel, 40, 40 );
                scoreLevelTable.strokeText( strLevel, 40, 40 );
            }

            if (enemyArray.get(i).getY()>HEIGHT){

                increaseScore = true;
                root.getChildren().remove(enemyArray.get(i));
                enemyArray.remove(i);
            }

        }
    }

    private void  AddEnemy() {

        long end = System.nanoTime();
        long elapsedTime = end - start;
        double elapsedTimeInSecond = (double) elapsedTime / 1_000_000_00;
        int r = rand.nextInt(10) + 8;

        if (elapsedTimeInSecond > r) {

            float x = ThreadLocalRandom.current().nextInt(120, 300);
            float y = ThreadLocalRandom.current().nextInt(60, 120);
            Rectangle enemy = new Rectangle(x, -y, carWidth, carHeight);
            enemyArray.add(enemy);
            start = System.nanoTime();
            root.getChildren().addAll(enemy);
        }


    }

    private void  DrawEnemy(){


        for (Rectangle  e:  enemyArray) {

            e.setFill(Color.YELLOW);
            e.setStroke(Color.BLACK);

        }
    }

}