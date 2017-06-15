package GameLogic ;

import javafx.beans.InvalidationListener;

import java.util.ArrayList;
import java.util.Observable;

/**
 * Created by eps on 2017-06-13.
 */

public class GameStatus extends Observable {

    Board board;
    PlayerLogic.Player currentPlayer;
    Slot changedSlot;
    boolean gameOver;
    PlayerLogic.Player winner;
    ArrayList<InvalidationListener> listOfObservers;

    public GameStatus(Board board, PlayerLogic.Player currentPlayer) {
        this.board = board;
        this.currentPlayer = currentPlayer;
        gameOver = false ;
        listOfObservers = new ArrayList<>();
    }

    public Board getBoard() {
        return board;
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    public PlayerLogic.Player getCurrentPlayer() {
        return currentPlayer;
    }

    public void setCurrentPlayer(PlayerLogic.Player currentPlayer) {
        this.currentPlayer = currentPlayer;
        System.out.println("updating observer..");
        setChanged();
        notifyObservers();
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public void setGameOver(boolean gameOver) {
        this.gameOver = gameOver;
    }

    public PlayerLogic.Player getWinner() {
        return winner;
    }

    public void setWinner(PlayerLogic.Player winner) {
        this.winner = winner;
    }

    public Slot getChangedSlot() {
        return changedSlot;
    }

    public void setChangedSlot(Slot changedSlot) {
        this.changedSlot = changedSlot;
    }

}
