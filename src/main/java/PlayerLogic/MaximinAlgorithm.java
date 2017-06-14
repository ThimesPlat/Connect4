package PlayerLogic;
import GameLogic.Board;
import GameLogic.Slot;
import GameLogic.SlotState;

import java.util.List;
public class MaximinAlgorithm {
    private List<Integer> score;
    private Board board;
    private int maxDepth;

    public MaximinAlgorithm(Board board, int depth) {
        this.board = new Board();
        this.board.copyBoard(board);
        maxDepth = depth;
    }

    public int calcValue() {
        int count = 0;
        for (int row = 0; row < 6; row++) {
            for (int col = 0; col < 7; col++) {
                if ((board.getSlot(row, col).getSlotState().equals(SlotState.RED))) {
                    count++;
                }
            }
        }
        //Natural to put in 3rd column.
        if (count == 1) {
            if (board.getSlot(2, 5).getSlotState().equals(SlotState.RED)) {
                return 4;
            } else if (board.getSlot(4, 5).getSlotState().equals(SlotState.RED)) {
                return 2;
            } else {
                return 3;
            }
        }
        //If it is not the first round, return negamax
        return negamax(board, -100, 0, SlotState.RED);
    }

    private int negamax(Board board, int alpha, int depth, SlotState colour) {
	    int bestPath = 0;
	    int bestValue = alpha;
	    int player;

	    if (colour.equals(SlotState.RED)) {
	        player = 2;
        }
        else {
	        player = 1;
        }

        //Determine if game is over in current state

        //Determine if game is a draw

        //Determine pathValue using eval() if depth is reached
        /*else*/ if (depth == maxDepth) {
	        int mid = (eval(board, player));
	        if(mid != 0) {
	            bestValue = mid-depth;
            }
            else {
	            bestValue = mid;
            }
        }
        else {

        }
        return 0;
    }

    private int eval(Board board, int player) {
        return 0;
    }
}
