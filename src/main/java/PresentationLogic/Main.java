package PresentationLogic;

import GameLogic.Game;

import GameLogic.Slot;
import GameLogic.SlotState;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.scene.paint.Color;
import javafx.scene.control.Label;
import java.util.*;



public class Main extends Application implements Observer{

    private double windowWidth = 600;
    private double windowHeight = 600;

    private Board board;
    private Pane layout;
    private Button startGame;
    private Label usersTurn;
    private GraphicalSlot[][] slots;
    private int turn = 1;
    private int i = 0;
    private Game game;

    @Override
    public void start(Stage primaryStage) throws Exception{
        primaryStage.setTitle("Connect-4");

        this.layout = new Pane();
        startGame = new Button("Start Button");
        startGame.setOnAction((ActionEvent event) -> {
            game.startGame();
            setupUserLabel();
            layout.getChildren().remove(this.startGame);
        });
        game = new Game();
        game.getGameStatus().addObserver(this);
        board = new Board(layout,(int)windowWidth,(int)windowHeight);
        slots = board.getSlots();
        layout = board.getBoardLayout();
        layout.getChildren().add(startGame);


        Scene scene = new Scene(layout,windowWidth,windowHeight);
        primaryStage.setScene(scene);
        primaryStage.show();
    }


    private void setupUserLabel(){
        this.usersTurn = new Label("Yellow users turn");
        this.usersTurn.setLayoutY(20);
        this.usersTurn.setScaleX(2);
        this.usersTurn.setScaleY(2);
        this.layout.getChildren().add(this.usersTurn);
        this.usersTurn.setLayoutX(windowWidth/2-40);

    }

    private void changeLabel(int player){
        if(player == 1){
            this.usersTurn.setText("Red users turn");
            this.usersTurn.setTextFill(Color.RED);
        }else{
            this.usersTurn.setText("Yellow users turn");
            this.usersTurn.setTextFill(Color.YELLOW);
        }
    }



    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void update(Observable o, Object arg) {
        Slot newlyChangedSlot = game.getGameStatus().getChangedSlot();
        Color color = (newlyChangedSlot.getSlotState() == SlotState.RED) ? Color.RED : Color.YELLOW;
        board.changeCircleColor(newlyChangedSlot.getRow(),newlyChangedSlot.getColumn(),color);
        changeLabel(turn);
    }
}

