
/**
 * Created by eps on 2017-06-13.
 */
public class GameStatus {

    Board board;
    Player currentPlayer;
    boolean gameOver;
    Player winner;

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
}
