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

    public MiniMax(Board board) {
        this.game = new Game();
        this.game.getGameStatus().setBoard(board);
        maxDepth = 2;
    }

    // decide which column we have to pick
    public int calcValue(Player player) {
        int count = 0;
        for (int row = 0; row < 6; row++) {
            for (int col = 0; col < 7; col++) {
                if ((this.game.getGameStatus().getBoard().getSlot(row, col).getSlotState().equals(SlotState.YELLOW)) || this.game.getGameStatus().getBoard().getSlot(row, col).getSlotState().equals(SlotState.RED)) {
                    count++;
                }
            }
        }
        //Natural to put in 3rd column as middle as possible
        if (count <= 1) {
            if (this.game.getGameStatus().getBoard().getSlot(5, 2).getSlotState().equals(SlotState.RED)) {
                return 4;
            } else if (this.game.getGameStatus().getBoard().getSlot(5, 4).getSlotState().equals(SlotState.RED)) {
                return 2;
            } else {
                return 3;
            }
        }
        //If it is not the first round, return negamax
        return negamax(this.game.getGameStatus().getBoard(), -100, 0, player);
    }

    // calls it self  and returns the best column that player will choose
    private int negamax(Board board, int alpha, int depth, Player player) {
        int bestPath = 0;
        int bestValue = alpha;
        Game game = new Game();
        game.setBoard(board.copyBoard(board));

        System.out.print(bestValue);
        if (depth == 0) {
            return bestPath;
        } else {
            return bestValue;
        }
    }

    public int eval(Board board, Player player) {
        int v = 1;
        int d = 2;
        int h = 3;

        int twoInRow = 10;
        int threeInRow = 1000;

        int value = 0;
        for (int row = 0; row < 6; row++) {
            for (int column = 0; column < 4; column++) {
                //(xx00)
                if (board.getSlot(row, column).getSlotState().equals(player.getColor()) &&
                        board.getSlot(row, column).getSlotState().equals(board.getSlot(row, column + 1)) &&
                        board.getSlot(row, column + 2).getSlotState().equals(SlotState.EMPTY) &&
                        board.getSlot(row, column + 3).getSlotState().equals(SlotState.EMPTY)
                        ) {
                    value += twoInRow * h;
                }
                //(x0x0)
                else if (board.getSlot(row, column).getSlotState().equals(player.getColor()) &&
                        board.getSlot(row, column + 2).getSlotState().equals(player.getColor()) &&
                        board.getSlot(row, column + 1).getSlotState().equals(SlotState.EMPTY) &&
                        board.getSlot(row, column + 3).getSlotState().equals(SlotState.EMPTY)) {
                    value += twoInRow * h;

                }
                //(x00x)
                else if (board.getSlot(row, column).getSlotState().equals(player.getColor()) &&
                        board.getSlot(row, column + 3).getSlotState().equals(player.getColor()) &&
                        board.getSlot(row, column + 1).getSlotState().equals(SlotState.EMPTY) &&
                        board.getSlot(row, column + 2).getSlotState().equals(SlotState.EMPTY)) {
                    value += twoInRow * h;
                }
                //(0xx0)
                else if (board.getSlot(row, column).getSlotState().equals(SlotState.EMPTY) &&
                        board.getSlot(row, column + 1).getSlotState().equals(player.getColor()) &&
                        board.getSlot(row, column + 2).getSlotState().equals(player.getColor()) &&
                        board.getSlot(row, column + 3).getSlotState().equals(SlotState.EMPTY)) {
                    value += 2 * twoInRow * h;
                }
                //(0x0x)
                else if (board.getSlot(row, column).getSlotState().equals(SlotState.EMPTY) &&
                        board.getSlot(row, column + 1).getSlotState().equals(player.getColor()) &&
                        board.getSlot(row, column + 2).getSlotState().equals(SlotState.EMPTY) &&
                        board.getSlot(row, column + 3).getSlotState().equals(player.getColor())) {
                    value += twoInRow * h;
                }
                //(00xx)
                else if (board.getSlot(row, column).getSlotState().equals(SlotState.EMPTY) &&
                        board.getSlot(row, column).getSlotState().equals(board.getSlot(row, column + 1).getSlotState()) &&
                        board.getSlot(row, column + 2).getSlotState().equals(player.getColor()) &&
                        board.getSlot(row, column + 3).getSlotState().equals(player.getColor())) {
                    value += twoInRow * h;
                }
            }
        }
        // check for vertical spaced 2-in-a-row
        for (int row = 5; row > 1; row--) {
            for (int column = 0; column < 7; column++) {
                if (board.getSlot(row, column).getSlotState().equals(player.getColor()) &&
                        board.getSlot(row, column).getSlotState().equals(board.getSlot(row, column - 1).getSlotState()) &&
                        board.getSlot(row - 2, column).getSlotState().equals(SlotState.EMPTY)) {
                    value += twoInRow * v;
                }
            }
        }
        //Check for diagonal spaced 2-in-a-row (/).
        //    0     x      x     x      0      0
        //   0     0      x     0      x      x
        //  x     0      0     x      0      x
        // x     x      0     0      x      0
        for (int row = 5; row > 2; row--) {
            for (int column = 0; column < 4; column++) {
                if (board.getSlot(row, column).getSlotState().equals(player.getColor()) &&
                        board.getSlot(row, column).getSlotState().equals(board.getSlot(row - 1, column + 1).getSlotState()) &&
                        board.getSlot(row - 2, column + 2).getSlotState().equals(SlotState.EMPTY) &&
                        board.getSlot(row - 3, column + 3).getSlotState().equals(SlotState.EMPTY)) {
                    value += twoInRow * d;
                } else if (board.getSlot(row, column).getSlotState().equals(player.getColor()) &&
                        board.getSlot(row - 1, column + 1).getSlotState().equals(SlotState.EMPTY) &&
                        board.getSlot(row - 2, column + 2).getSlotState().equals(player.getColor()) &&
                        board.getSlot(row, column).getSlotState().equals(board.getSlot(row - 3, column + 3).getSlotState())) {
                    value += twoInRow * d;
                } else if (board.getSlot(row, column).getSlotState().equals(SlotState.EMPTY) &&
                        board.getSlot(row - 1, column + 1).getSlotState().equals(SlotState.EMPTY) &&
                        board.getSlot(row - 2, column + 2).getSlotState().equals(player.getColor()) &&
                        board.getSlot(row - 3, column + 3).getSlotState().equals(player.getColor())) {
                    value += twoInRow * d;
                } else if (board.getSlot(row, column).getSlotState().equals(SlotState.EMPTY) &&
                        board.getSlot(row - 1, column + 1).getSlotState().equals(SlotState.EMPTY) &&
                        board.getSlot(row, column).getSlotState().equals(board.getSlot(row - 2, column + 2).getSlotState()) &&
                        board.getSlot(row - 1, column + 1).getSlotState().equals(board.getSlot(row - 3, column + 3).getSlotState())) {
                    value += twoInRow * d;
                } else if (board.getSlot(row, column).getSlotState().equals(player.getColor()) &&
                        board.getSlot(row - 1, column + 1).getSlotState().equals(SlotState.EMPTY) &&
                        board.getSlot(row, column).getSlotState().equals(board.getSlot(row - 2, column + 2).getSlotState()) &&
                        board.getSlot(row - 1, column + 1).getSlotState().equals(board.getSlot(row - 3, column + 3).getSlotState())) {
                    value += twoInRow * d;
                } else if (board.getSlot(row, column).getSlotState().equals(SlotState.EMPTY) &&
                        board.getSlot(row - 1, column + 1).getSlotState().equals(player.getColor()) &&
                        board.getSlot(row - 1, column + 1).getSlotState().equals(board.getSlot(row - 2, column + 2).getSlotState()) &&
                        board.getSlot(row, column).getSlotState().equals(board.getSlot(row - 3, column + 3).getSlotState())) {
                    value += 2 * twoInRow * d;
                }
            }
        }
//Check for diagonal spaced 3-in-a-row (\).
        // 0     x     x     x
        //  x     0     x     x
        //   x     x     0     x
        //    x     x     x     0
        for (int row = 0; row > 3; row--) {
            for (int column = 0; column < 4; column++) {
                if (board.getSlot(row, column).getSlotState().equals(player.getColor()) &&
                        board.getSlot(row, column).getSlotState().equals(board.getSlot(column + 1, row + 1).getSlotState()) &&
                        board.getSlot(row + 2, column + 2).getSlotState().equals(SlotState.EMPTY) &&
                        board.getSlot(row + 3, column + 3).getSlotState().equals(SlotState.EMPTY)) {
                    value += twoInRow * d;
                } else if (board.getSlot(row, column).getSlotState().equals(player.getColor()) &&
                        board.getSlot(row + 1, column + 1).getSlotState().equals(SlotState.EMPTY) &&
                        board.getSlot(row + 2, column + 2).getSlotState().equals(SlotState.EMPTY) &&
                        board.getSlot(row, column).getSlotState().equals(board.getSlot(row + 3, column + 3).getSlotState())) {
                    value += twoInRow * d;
                } else if (board.getSlot(row, column).getSlotState().equals(SlotState.EMPTY) &&
                        board.getSlot(row + 1, column + 1).getSlotState().equals(SlotState.EMPTY) &&
                        board.getSlot(row + 2, column + 2).getSlotState().equals(player.getColor()) &&
                        board.getSlot(row + 3, column + 3).getSlotState().equals(player.getColor())) {
                    value += twoInRow * d;
                } else if (board.getSlot(row, column).getSlotState().equals(SlotState.EMPTY) &&
                        board.getSlot(row + 1, column + 1).getSlotState().equals(player.getColor()) &&
                        board.getSlot(row, column).getSlotState().equals(board.getSlot(row + 2, column + 2).getSlotState()) &&
                        board.getSlot(row + 1, column + 1).getSlotState().equals(board.getSlot(row + 3, column + 3).getSlotState())) {
                    value += twoInRow * d;
                } else if (board.getSlot(row, column).getSlotState().equals(player.getColor()) &&
                        board.getSlot(row + 1, column + 1).getSlotState().equals(SlotState.EMPTY) &&
                        board.getSlot(row, column).getSlotState().equals(board.getSlot(row + 2, column + 2).getSlotState()) &&
                        board.getSlot(row + 1, column + 1).getSlotState().equals(board.getSlot(row + 3, column + 3).getSlotState())) {
                    value += twoInRow * d;
                } else if (board.getSlot(row, column).getSlotState().equals(SlotState.EMPTY) &&
                        board.getSlot(row + 1, column + 1).getSlotState().equals(player.getColor()) &&
                        board.getSlot(row + 1, column + 1).getSlotState().equals(board.getSlot(row + 2, column + 2).getSlotState()) &&
                        board.getSlot(row, column).getSlotState().equals(board.getSlot(row + 3, column + 3).getSlotState())) {
                    value += twoInRow * 2 * d;
                }
            }
        }
        //Check for horizontal 3-in-a-row.

        for (int row = 0; row < 6; row++) {
            for (int column = 0; column < 4; column++) {
                //(xx0x)
                if (board.getSlot(row, column).getSlotState().equals(player.getColor()) &&
                        board.getSlot(row, column).getSlotState().equals(board.getSlot(column + 1, row).getSlotState()) &&
                        board.getSlot(row, column + 2).getSlotState().equals(SlotState.EMPTY) &&
                        board.getSlot(row, column).getSlotState().equals(board.getSlot(column + 3, row).getSlotState())) {
                    value += threeInRow * h;
                }
                //(x0xx)
                else if (board.getSlot(row, column).getSlotState().equals(player.getColor()) &&
                        board.getSlot(row, column + 1).getSlotState().equals(SlotState.EMPTY) &&
                        board.getSlot(row, column).getSlotState().equals(board.getSlot(row, column + 2).getSlotState()) &&
                        board.getSlot(row, column).getSlotState().equals(board.getSlot(row, column + 3).getSlotState())) {
                    value += threeInRow * h;
                }
                //(0xxx)
                else if (board.getSlot(row, column).getSlotState().equals(SlotState.EMPTY) &&
                        board.getSlot(row, column + 1).getSlotState().equals(player.getColor()) &&
                        board.getSlot(row, column + 1).getSlotState().equals(board.getSlot(row, column + 2).getSlotState()) &&
                        board.getSlot(row, column + 1).getSlotState().equals(board.getSlot(row, column + 3).getSlotState())) {
                    value += threeInRow * d;
                }
                //(xxx0)
                else if (board.getSlot(row, column).getSlotState().equals(player.getColor()) &&
                        board.getSlot(row, column).getSlotState().equals(board.getSlot(row, column + 1).getSlotState()) &&
                        board.getSlot(row, column).getSlotState().equals(board.getSlot(row, column + 2).getSlotState()) &&
                        board.getSlot(row, column + 3).getSlotState().equals(SlotState.EMPTY)) {
                    value += threeInRow * h;
                }
            }
        }
        //Check for vertical spaced 3-in-a-row.
        // 0
        // x
        // x
        // x
        for (int row = 5; row > 2; row--) {
            for (int column = 0; column < 7; column++) {
                if (board.getSlot(row, column).getSlotState().equals(player.getColor()) &&
                        board.getSlot(row, column).getSlotState().equals(board.getSlot(row - 1, column).getSlotState()) &&
                        board.getSlot(row, column).getSlotState().equals(board.getSlot(row - 2, column).getSlotState()) &&
                        board.getSlot(row - 3, column).getSlotState().equals(SlotState.EMPTY)) {
                    value += threeInRow * v;
                }

            }
        }
        //Check for diagonal spaced 3-in-a-row (/).
        //    0     x      x     x
        //   x     0      x     x
        //  x     x      0     x
        // x     x      x     0
        for (int row = 0; row > 2; row--) {
            for (int column = 0; column < 4; column++) {
                if (board.getSlot(row, column).getSlotState().equals(player.getColor()) &&
                        board.getSlot(row, column).getSlotState().equals(board.getSlot(row - 1, column + 1).getSlotState()) &&
                        board.getSlot(row, column).getSlotState().equals(board.getSlot(row - 2, column + 2).getSlotState()) &&
                        board.getSlot(row - 3, column + 3).getSlotState().equals(SlotState.EMPTY)) {
                    value += threeInRow * d;
                } else if (board.getSlot(row, column).getSlotState().equals(player.getColor()) &&
                        board.getSlot(row, column + 1).getSlotState().equals(board.getSlot(row - 1, column + 1).getSlotState()) &&
                        board.getSlot(row - 2, column + 2).getSlotState().equals(SlotState.EMPTY) &&
                        board.getSlot(row, column).getSlotState().equals(board.getSlot(row - 3, column + 3).getSlotState())) {
                    value += threeInRow * d;
                } else if (board.getSlot(row, column).getSlotState().equals(player.getColor()) &&
                        board.getSlot(row - 1, column + 1).getSlotState().equals(SlotState.EMPTY) &&
                        board.getSlot(row, column).getSlotState().equals(board.getSlot(row - 2, column + 2).getSlotState()) &&
                        board.getSlot(row, column).getSlotState().equals(board.getSlot(row - 3, column + 3).getSlotState())) {
                    value += threeInRow * d;
                } else if (board.getSlot(row, column).getSlotState().equals(SlotState.EMPTY) &&
                        board.getSlot(row - 1, column + 1).getSlotState().equals(player.getColor()) &&
                        board.getSlot(row - 1, column + 1).getSlotState().equals(board.getSlot(row - 2, column + 2).getSlotState()) &&
                        board.getSlot(row - 1, column + 1).getSlotState().equals(board.getSlot(row - 3, column + 3).getSlotState())) {
                    value += threeInRow * d;
                }
            }
        }
        //Check for diagonal spaced 3-in-a-row (\).
        // 0     x     x     x
        //  x     0     x     x
        //   x     x     0     x
        //    x     x     x     0
        for (int row = 0; row < 3; row++) {
            for (int column = 0; column < 4; column++) {
                if (board.getSlot(row, column).getSlotState().equals(SlotState.EMPTY) &&
                        board.getSlot(row + 1, column + 1).getSlotState().equals(player.getColor()) &&
                        board.getSlot(row + 1, column + 1).getSlotState().equals(board.getSlot(row + 3, column + 3).getSlotState())
                        ) {
                    value += threeInRow * d;
                } else if (board.getSlot(row, column).getSlotState().equals(player.getColor()) &&
                        board.getSlot(row + 1, column + 1).getSlotState().equals(SlotState.EMPTY) &&
                        board.getSlot(row, column).getSlotState().equals(board.getSlot(row + 2, column + 2).getSlotState()) &&
                        board.getSlot(row, column).getSlotState().equals(board.getSlot(row + 3, column + 3).getSlotState())) {
                    value += threeInRow * d;
                } else if (board.getSlot(row, column).getSlotState().equals(player.getColor()) &&
                        board.getSlot(row, column).getSlotState().equals(board.getSlot(row + 1, column + 1).getSlotState()) &&
                        board.getSlot(row + 2, column + 2).getSlotState().equals(SlotState.EMPTY) &&
                        board.getSlot(row, column).getSlotState().equals(board.getSlot(row + 3, column + 3).getSlotState())) {
                    value += threeInRow * d;
                } else if (board.getSlot(row, column).getSlotState().equals(player.getColor()) &&
                        board.getSlot(row, column).getSlotState().equals(board.getSlot(row + 1, column + 1).getSlotState()) &&
                        board.getSlot(row, column).getSlotState().equals(board.getSlot(row + 2, column + 2).getSlotState()) &&
                        board.getSlot(row + 3, column + 3).getSlotState().equals(SlotState.EMPTY)) {
                    value += threeInRow * d;
                }
            }
        }
        // check for open-ended-in-a-row.(0xxx0)
        for (int row = 0; row < 5; row++) {
            for (int column = 0; column < 3; column++) {
                //horizontal
                if (board.getSlot(row, column).getSlotState().equals(SlotState.EMPTY) &&
                        board.getSlot(row, column + 1).getSlotState().equals(player.getColor()) &&
                        board.getSlot(row + 1, column + 2).getSlotState().equals(player.getColor()) &&
                        board.getSlot(row + 1, column + 3).getSlotState().equals(player.getColor()) &&
                        board.getSlot(row, column).equals(board.getSlot(row, column+4))) {
                    value += 2 * threeInRow * h;
                }
            }
        }
        for (int row = 0; row < 2; row++) {
            for (int column = 0; column < 3; column++) {
                //diag(\)
                if (board.getSlot(row, column).getSlotState().equals(SlotState.EMPTY) &&
                        board.getSlot(row + 1, column + 1).getSlotState().equals(player.getColor()) &&
                        board.getSlot(row, column).getSlotState().equals(board.getSlot(row + 2, column + 2).getSlotState()) &&
                        board.getSlot(row, column).getSlotState().equals(board.getSlot(row + 3, column + 3).getSlotState()) &&
                        board.getSlot(row + 4, column + 4).getSlotState().equals(SlotState.EMPTY)) {
                    value += 2 * threeInRow * d;
                }
            }
        }
        //diag(/)
        for (int row = 5; row > 3; row--) {
            for (int column = 0; column < 3; column++) {
                if (board.getSlot(row, column).getSlotState().equals(SlotState.EMPTY) &&
                        board.getSlot(row - 1, column + 1).getSlotState().equals(player.getColor()) &&
                        board.getSlot(row - 2, column + 2).getSlotState().equals(player.getColor()) &&
                        board.getSlot(row - 3, column + 3).getSlotState().equals(player.getColor()) &&
                        board.getSlot(row - 4, column + 4).getSlotState().equals(SlotState.EMPTY)) {
                    value += 2 * threeInRow * d;
                }
            }
        }
        return value;
    }
}
