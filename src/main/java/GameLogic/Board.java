/**
 * Created by eps on 2017-06-12.
 */
package GameLogic;
public class Board
{
private Slot[][] board;

    public Board() {
        board = new Slot[6][7] ;
    }

    public Slot[][] getBoard() {
        return board;
    }

    public void setBoard(Slot[][] board) {
        this.board = board;
    }

    public Slot getSlot(int row, int column){
        return board[row][column];
    }

    public void setSlot (Slot slot, int row, int column){
        board[row][column] = slot;
    }

    public void setSlotState (SlotState slotState, int row, int column) {
        board[row][column].setSlotState(slotState);
    }


}
