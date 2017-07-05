/**
 * Created by eps on 2017-06-12.
 */
package GameLogic;

import java.util.ArrayList;

public class Board {
    private Slot[][] board;
    private ArrayList<Slot> winningSequence;

    public Board() {
        winningSequence = new ArrayList<>();
        board = new Slot[6][7];
        for (int row = 0; row < 6; row++) {
            for (int col = 0; col < 7; col++) {
               board[row][col] = new Slot(SlotState.EMPTY);
            }
        }
    }

    public Slot[][] getBoard() {
        return board;
    }

    public void setBoard(Slot[][] board) {
        this.board = board;
    }

    public Slot getSlot(int row, int column) {

        return board[row][column];
    }

    public void setSlot(Slot slot, int row, int column) {
        board[row][column] = slot;
    }

    public ArrayList<Slot> getWinningSequence() {
        return winningSequence;
    }

    public void setWinningSequence(ArrayList<Slot> winningSequence) {
        this.winningSequence = winningSequence;
    }


}
