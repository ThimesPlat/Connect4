package PlayerLogic;
import GameLogic.Board;
import GameLogic.Game;
import GameLogic.Slot;
import GameLogic.SlotState;

import java.util.List;
public class MiniMax {
   private Game game;
    //private Board board;
    private int maxDepth;

    public MiniMax(Board board, int depth) {
        this.game = new Game();
        this.game.getBoard().copyBoard(board);
        maxDepth = depth;
    }
// decide which column we have to pick, in case of it is RED
    public int calcValue() {
        int count = 0;
        for (int row = 0; row < 6; row++) {
            for (int col = 0; col < 7; col++) {
                if ((this.game.getBoard().getSlot(row, col).getSlotState().equals(SlotState.RED))) {
                    count++;
                }
            }
        }
        //Natural to put in 3rd column as middle as possible
        if (count == 1) {
            if (this.game.getBoard().getSlot(2, 5).getSlotState().equals(SlotState.RED)) {
                return 4;
            } else if (this.game.getBoard().getSlot(4, 5).getSlotState().equals(SlotState.RED)) {
                return 2;
            } else {
                return 3;
            }
        }
        //If it is not the first round, return negamax
        return negamax(this.game.getBoard(), -100, 0, SlotState.RED);
    }
// calls it self  and returns the best column that player will choose
    private int negamax(Board board, int alpha, int depth, SlotState colour) {
        int bestPath = 0;
        int bestValue = alpha;
        int player;

        //Board newBoard =new Board();
        //newBoard.copyBoard(board);

        if (colour.equals(SlotState.RED)) {
            player = 2;
        } else {
            player = 1;
        }
        Game game = new Game();
        game.getBoard().copyBoard(board);

        //Determine if game is over in current state
        //Determine if game is a draw
        //Determine pathValue using eval() if depth is reached
        if (depth == maxDepth) {
            int mid = (eval(board, player));
            if (mid != 0) {
                bestValue = mid - depth;
            } else {
                bestValue = mid;
            }
        } else {
            // Generates the best movement for each column and give the best score to coulmn

            for (int column = 0; column < 7; column++) {
                Game simulationGame = new Game();
                simulationGame.getBoard().copyBoard(game.getBoard());
                Slot slot = simulationGame.discDrop(column);


                if (slot == null) {
                    Board tempboard = new Board();
                    if (depth < maxDepth) {
                        tempboard.copyBoard(board);
                        int v = -negamax(board, -100, depth + 1, SlotState.YELLOW);
                        if (v >= bestValue) {
                            bestPath = column;
                            bestValue = v;
                        }
                    }
                }
            }
        }
        if (depth == 0) {
            return bestPath;

        } else {
            return bestValue;
        }
    }

    private int eval(Board board, int player) {
        return 0;
    }
}
