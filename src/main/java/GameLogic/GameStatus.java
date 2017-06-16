package GameLogic ;

import PlayerLogic.Player;
import javafx.beans.InvalidationListener;

import java.util.ArrayList;
import java.util.Observable;

/**
 * Created by eps on 2017-06-13.
 */

public class GameStatus extends Observable {

    Board board;
    Player currentPlayer;
    Slot changedSlot;
    boolean gameOver;
    Player winner;
    ArrayList<InvalidationListener> listOfObservers;

    public GameStatus(Board board, Player currentPlayer) {
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

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public void setCurrentPlayer(Player currentPlayer) {
        this.currentPlayer = currentPlayer;
        setChanged();
        notifyObservers();
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public void setGameOver(boolean gameOver) {
        this.gameOver = gameOver;
    }

    public Player getWinner() {
        return winner;
    }

    public void setWinner(Player winner) {
        this.winner = winner;
    }

    public Slot getChangedSlot() {
        return changedSlot;
    }

    public void setChangedSlot(Slot changedSlot) {
        this.changedSlot = changedSlot;
    }

}
