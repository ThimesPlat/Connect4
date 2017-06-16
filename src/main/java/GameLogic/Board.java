/**
 * Created by eps on 2017-06-12.
 */
package GameLogic;

public class Board {
    private Slot[][] board;
    private Slot[] winningSequence;

    public Board() {
        board = new Slot[6][7];
        for (int row = 0; row < 6; row++) {
            for (int col = 0; col < 7; col++) {
               board[row][col] = new Slot(SlotState.EMPTY);
            }
        }
    }

    public void copyBoard(Board board) {
        Slot[][] newSlot = new Slot[6][7];
        for (int row = 0; row < 6; row++) {
            for (int col = 0; col < 7; col++) {
                newSlot[row][col] = board.getSlot(row, col);
            }
        }
        this.setBoard(newSlot);
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

    public void setSlotState(SlotState slotState, int row, int column) {
        board[row][column].setSlotState(slotState);
    }

    public Slot[] getWinningSequence() {
        return winningSequence;
    }

    public void setWinningSequence(Slot[] winningSequence) {
        this.winningSequence = winningSequence;
    }

}
