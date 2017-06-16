package PresentationLogic;

import GameLogic.Game;

import GameLogic.GameStatus;
import GameLogic.Slot;
import GameLogic.SlotState;
import PlayerLogic.Player;
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
    private Game game;
    private boolean gameStartedOnce = false;
    private double startGameButtonWidth;

    @Override
    public void start(Stage primaryStage) throws Exception{
        primaryStage.setTitle("Connect-4");

        this.layout = new Pane();
        startGame = new Button();
        startGame.setLayoutX(10);
        startGame.setLayoutY(20);
        startGame = buttonSetup(startGame,"Start Game","2dcddc");
        startGame.setOnAction((ActionEvent event) -> {
            startGameButtonWidth = startGame.getWidth();
            if (gameStartedOnce) {  // called second time game starts
                layout.getChildren().remove(this.usersTurn);
                game.getGameStatus().deleteObserver(this);
                this.game = new Game();
                game.getGameStatus().addObserver(this);
                board.clearBoard();
            }
            else{

            }
            setupUserLabel();
            game.startGame();
            this.startGame.setVisible(false);
            gameStartedOnce=true;
        });
        game = new Game();
        game.getGameStatus().addObserver(this);
        board = new Board(layout,(int)windowWidth,(int)windowHeight);
        slots = board.getSlots();
        layout = board.getBoardLayout();
        layout.getChildren().add(startGame);


        Scene scene = new Scene(layout,windowWidth,windowHeight);
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.sizeToScene();
        primaryStage.show();
    }


    private void setupUserLabel(){
        this.usersTurn = new Label("Yellow users turn");
        this.usersTurn.setLayoutY(20);
        this.usersTurn.setScaleX(3);
        this.usersTurn.setScaleY(3);
     //   if (!gameStartedOnce) {
            this.layout.getChildren().add(this.usersTurn);
   //     }
        this.usersTurn.setLayoutX(windowWidth/2-40);

    }

    private void changeLabel(int player, Boolean isGameOver, Player winner){
        if (isGameOver){
            String winnerText = (winner.getColor() == SlotState.RED) ? "RED":"YELLOW";
            Color winnerColor = (winner.getColor() == SlotState.RED) ? Color.RED:Color.YELLOW;
            this.usersTurn.setText(winnerText + " IS THE WINNER!");
            this.usersTurn.setTextFill(winnerColor);
            gameIsOver();
        }
        else {
            if (player == 1) {
                this.usersTurn.setText("Red users turn");
                this.usersTurn.setTextFill(Color.RED);
            } else {
                this.usersTurn.setText("Yellow users turn");
                
                this.usersTurn.setTextFill(Color.YELLOW);
            }
        }
    }

    public void gameIsOver(){
        usersTurn.setLayoutX((startGame.getLayoutX()+startGameButtonWidth)*2);
        usersTurn.setLayoutY(startGame.getLayoutY()+10);
        startGame.setVisible(true);
    }

    public Button buttonSetup(Button button,String text,String color){
        button.setText(text);
        button.setStyle("-fx-font: 22 arial; -fx-base: #"+color+";");

        return button;
    }


    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void update(Observable o, Object arg) {
        GameStatus newGameStatus = game.getGameStatus();
        Slot newlyChangedSlot = newGameStatus.getChangedSlot();
        Color color = (newlyChangedSlot.getSlotState() == SlotState.RED) ? Color.RED : Color.YELLOW;
        board.changeCircleColor(newlyChangedSlot.getRow(),newlyChangedSlot.getColumn(),color);
        turn = (game.getGameStatus().getCurrentPlayer().getColor() == SlotState.RED)?2:1;
        changeLabel(turn,newGameStatus.isGameOver(),newGameStatus.getWinner());
    }
}




