package PresentationLogic;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.scene.paint.Color;
import javafx.scene.control.Label;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import javafx.beans.value.ObservableValue;
import javafx.beans.value.ChangeListener;


public class Main extends Application {

    double windowWidth = 600;
    double windowHeight = 600;

    Board board;
    Pane layout;
    Button startGame;
    Label usersTurn;
    GraphicalSlot[][] slots;
    int turn = 1;
    int i = 0;
    TestObservers test;

    @Override
    public void start(Stage primaryStage) throws Exception{
     //   Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Connect-4");


        this.layout = new Pane();
        startGame = new Button("Start Button");
        startGame.setOnAction((ActionEvent event)-> {



            test.hejsan();

/*
            Timer timer = new Timer();
            timer.scheduleAtFixedRate(new TimerTask() {
                @Override
                public void run() {
                    Platform.runLater(() -> {
                        fillMatix();
                    });
                }
            }, 1000, 1000);
*/
/*
            while(true){
                fillMatix();
                i++;
                try{
                    Thread.sleep(500);
                }catch (Exception e){
                    System.out.println(e);
                }
            }

*/
/*
            for(GraphicalSlot[] row : slots){
                for(GraphicalSlot value: row){
                    System.out.print(value.getColor());
                }
                System.out.println();
            }

*/



        });

        test = new TestObservers();

        test.i.addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                matrixChanged();
            }
        });




        board = new Board(layout,(int)windowWidth,(int)windowHeight);
        slots = board.getSlots();
        layout = board.getBoardLayout();
        layout.getChildren().add(startGame);

        setupUserLabel();


        Scene scene = new Scene(layout,windowWidth,windowHeight);
        primaryStage.setScene(scene);
        primaryStage.show();
    }


    private void setupUserLabel(){
        this.usersTurn = new Label("Yellow users turn");
        this.usersTurn.setLayoutX(windowWidth/2);


        this.usersTurn.setScaleX(2);
        this.usersTurn.setScaleY(2);

        this.layout.getChildren().add(this.usersTurn);
    }

    private void changeLabel(int player){
        if(player == 1){
            this.usersTurn.setText("Red users turn");
            this.usersTurn.setTextFill(Color.YELLOW);
        }else{
            this.usersTurn.setText("Yellow users turn");
            this.usersTurn.setTextFill(Color.RED);
        }
    }

    public void fillMatix(){
        /*
        try {
            //  System.out.println("Game sleeping...");
            Random rand = new Random();
            int randomPositionRow = rand.nextInt(6);
            int randomPositionCol = rand.nextInt(7);
            if (turn == 1) {
                board.changeCircleColor(randomPositionRow,randomPositionCol, Color.YELLOW);
                System.out.println("Changing color!");
                turn = 2;
            }
            else{
                board.changeCircleColor(randomPositionRow,randomPositionCol, Color.RED);
                System.out.println("Changing color!");
                turn = 1;
            }
            changeLabel(turn);
            //  layout.getChildren().remove(startGame);
        }catch (Exception e){
            System.out.println(e);
        }
        */
    }


    public void matrixChanged(){
        try {
            //  System.out.println("Game sleeping...");
            Random rand = new Random();
            int randomPositionRow = rand.nextInt(6);
            int randomPositionCol = rand.nextInt(7);
            if (turn == 1) {
                board.changeCircleColor(randomPositionRow,randomPositionCol, Color.YELLOW);
                turn = 2;
            }
            else{
                board.changeCircleColor(randomPositionRow,randomPositionCol, Color.RED);
                turn = 1;
            }
            changeLabel(turn);
            //  layout.getChildren().remove(startGame);
        }catch (Exception e){
            System.out.println(e);
        }
    }



    public static void main(String[] args) {
        launch(args);
    }
}

