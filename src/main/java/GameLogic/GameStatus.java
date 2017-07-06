package GameLogic ;

import java.util.ArrayList;
import java.util.Observable;

import PlayerLogic.Player;
import javafx.beans.InvalidationListener;

/**
 * Created by eps on 2017-06-13.
 */

public class GameStatus extends Observable {

    private Board board;
    private Player currentPlayer;
    private Slot changedSlot;
    private boolean gameOver;
    private Player winner;

    public GameStatus(Board board, Player currentPlayer) {
        this.board = board;
        this.currentPlayer = currentPlayer;
        gameOver = false ;
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
