
/**
 * Created by eps on 2017-06-13.
 */
package GameLogic;
import PlayerLogic.Player;

public class GameStatus {

    Board board;
    PlayerLogic.Player currentPlayer;
    boolean gameOver;
    PlayerLogic.Player winner;

    public GameStatus(Board board, PlayerLogic.Player currentPlayer) {
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

    public PlayerLogic.Player getCurrentPlayer() {
        return currentPlayer;
    }

    public void setCurrentPlayer(PlayerLogic.Player currentPlayer) {
        this.currentPlayer = currentPlayer;
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
}
