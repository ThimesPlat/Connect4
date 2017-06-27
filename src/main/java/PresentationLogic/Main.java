package PresentationLogic;

import GameLogic.Game;

import GameLogic.GameStatus;
import GameLogic.Slot;
import GameLogic.SlotState;
import PlayerLogic.Player;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.scene.paint.Color;
import javafx.scene.control.Label;
import sun.plugin2.util.ColorUtil;
import javafx.stage.WindowEvent;

import javax.xml.soap.Text;
import java.io.IOException;
import java.util.*;



public class Main extends Application implements Observer{

    private double windowWidth = 600;
    private double windowHeight = 700;

    private GraphicalBoard board;
    private Pane layout;
    private Button startGame;
    private Label usersTurn;
    private TextField textField;
    private Label difficultyLevelLabel;
    private GraphicalSlot[][] slots;
    private int turn = 1;
    private Game game;
    private boolean gameStartedOnce = false;
    private double startGameButtonWidth;
    private Timer firstTimer;
    private Timer secondTimer;
    private Thread thread;

    @Override
    public void start(Stage primaryStage) throws Exception{
        primaryStage.setTitle("Connect-4");

        firstTimer = new Timer();
        secondTimer = new Timer();
        this.layout = new Pane();
        startGame = new Button();
        startGame.setLayoutX(10);
        startGame.setLayoutY(20);
        startGame = buttonSetup(startGame,"Start Game","2dcddc");
        startGame.setOnAction((ActionEvent event) -> {      // button event for the "Start Game" button
            startGameButtonWidth = startGame.getWidth();
            if (gameStartedOnce)  // called second time game starts
                resetApplicationVariables();
            setupUserLabel();
       //     if (isInteger(textField.getText()))
            game.setMiniMaxDepth(Integer.valueOf(textField.getText()));

            thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    System.out.println("Starting Game!");
                    game.startGame();
                }
            });
            thread.start();
          //  thread.run();
       //     game.startGame();       // start the simulation
            //Slot slot = new Slot(S)
            System.out.println("AASASDASDASDASDASDASDLOOOL");
            this.textField.setVisible(false);
            this.difficultyLevelLabel.setVisible(false);
            this.startGame.setVisible(false);
            gameStartedOnce=true;
        });

        game = new Game();
        game.getGameStatus().addObserver(this);
        board = new GraphicalBoard(layout,windowWidth,windowHeight);
        slots = board.getSlots();


        setupGame();
        layout = board.getBoardLayout();
        layout.getChildren().add(startGame);
        Scene scene = new Scene(layout,windowWidth,windowHeight);
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.sizeToScene();
        primaryStage.show();
        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                Platform.exit();
                System.exit(0);
            }
        });
    }
/*
    private boolean isInteger(String text) {
        for(int i = 0; i<text.length();i++){
            if (text.charAt(i))
        }
    }
*/
    private void startWinningAnimation(){       // "show where the winning sequence is
        firstTimer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(() -> {
                    for(Slot currentSlot: game.getGameStatus().getBoard().getWinningSequence()){
                        slots[currentSlot.getRow()][currentSlot.getColumn()].setColor(Color.rgb(102, 255, 102));
                    }
                });
            }
        }, 500, 500);

        firstTimer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(() -> {
                    Color color = (game.getGameStatus().getWinner().getColor()==SlotState.RED)?Color.RED:Color.YELLOW;
                    for(Slot currentSlot: game.getGameStatus().getBoard().getWinningSequence()){
                        slots[currentSlot.getRow()][currentSlot.getColumn()].setColor(color);
                    }
                });
            }
        }, 1000, 500);
    }

    private void setupUserLabel(){
        this.usersTurn = new Label("Yellow users turn");
        this.usersTurn.setLayoutY(20);
        this.usersTurn.setScaleX(3);
        this.usersTurn.setScaleY(3);
        this.layout.getChildren().add(this.usersTurn);
        this.usersTurn.setLayoutX(windowWidth/2-40);

    }

    private void changeLabel(int player, Boolean isGameOver, Player winner){
        if (isGameOver){    // if the game is over, change the label accordingly
            String winnerText = (winner.getColor() == SlotState.RED) ? "RED":"YELLOW";
            Color winnerColor = (winner.getColor() == SlotState.RED) ? Color.RED:Color.YELLOW;
            this.usersTurn.setText(winnerText + " IS THE WINNER!");
            this.usersTurn.setTextFill(winnerColor);
            this.usersTurn.setScaleX(4);
            this.usersTurn.setScaleY(4);
            gameIsOver();
        }
        else {      // if game is NOT over, display next user
            if (player == 1) {
                this.usersTurn.setText("Red users turn");
                this.usersTurn.setTextFill(Color.RED);
            } else {
                this.usersTurn.setText("Yellow users turn");
                
                this.usersTurn.setTextFill(Color.YELLOW);
            }
        }
    }

    private void resetApplicationVariables(){
        layout.getChildren().remove(this.usersTurn);
        this.game = new Game();
        game.getGameStatus().addObserver(this);
        board.clearBoard();
        firstTimer.cancel();
        secondTimer.cancel();
        firstTimer = new Timer();
        secondTimer = new Timer();
    }

    private void gameIsOver(){
        this.usersTurn.setLayoutX((startGame.getLayoutX()+startGameButtonWidth)+100);
        this.usersTurn.setLayoutY(startGame.getLayoutY()+100);
        this.startGame.setVisible(true);
        this.difficultyLevelLabel.setVisible(true);
        this.textField.setText("");
        this.textField.setVisible(true);
        startWinningAnimation();
    }

    private void setupGame(){
        difficultyLevelLabel = new Label("Enter difficulty level: ");
        difficultyLevelLabel.setLayoutY(30);
        difficultyLevelLabel.setScaleX(2);
        difficultyLevelLabel.setScaleY(2);
        difficultyLevelLabel.setTextFill(Color.rgb(102,255,102));
        this.layout.getChildren().add(difficultyLevelLabel);
        difficultyLevelLabel.setLayoutX(windowWidth/2-80);

        this.textField = new TextField();
        this.textField.setLayoutY(30);
        this.textField.setScaleX(2);
        this.textField.setScaleY(2);
        this.textField.setPrefWidth(50);
        this.layout.getChildren().add(textField);
        this.textField.setLayoutX(windowWidth-180);


    }

    private Button buttonSetup(Button button,String text,String color){
        button.setText(text);
        button.setStyle("-fx-font: 22 arial; -fx-base: #"+color+";");
        return button;
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void update(Observable o, Object arg) {
        Platform.runLater(() -> {
            System.out.println("updated...");
            GameStatus newGameStatus = game.getGameStatus();
            Slot newlyChangedSlot = newGameStatus.getChangedSlot();
            Color color = (newlyChangedSlot.getSlotState() == SlotState.RED) ? Color.RED : Color.YELLOW;
            board.changeCircleColor(newlyChangedSlot.getRow(), newlyChangedSlot.getColumn(), color);
            turn = (game.getGameStatus().getCurrentPlayer().getColor() == SlotState.RED) ? 2 : 1;
            Main.this.changeLabel(turn, newGameStatus.isGameOver(), newGameStatus.getWinner());
            System.out.println("button is clicked");
        });





    }
}




