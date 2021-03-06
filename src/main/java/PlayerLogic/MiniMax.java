package PlayerLogic;

import GameLogic.Board;
import GameLogic.Game;
import GameLogic.Slot;
import GameLogic.SlotState;

import java.util.Scanner;

public class MiniMax {

	private Game game;
	private int maxDepth;
	private Player currentPlayer;
	private SlotState isEmpty = SlotState.EMPTY;

	public MiniMax(SlotState[][] board) {
		this.game = new Game();
		this.game.setBoard(board);
		maxDepth = 0;
	}

	// decide which column we have to pick
	public int calcValue(Player player) {

		currentPlayer = player;
		int count = 0;
		for (int row = 0; row < 6; row++) {
			for (int col = 0; col < 7; col++) {
				if ((this.game.getGameStatus().getBoard().getSlot(row, col).getSlotState().equals(SlotState.YELLOW))
						|| this.game.getGameStatus().getBoard().getSlot(row, col).getSlotState()
								.equals(SlotState.RED)) {
					count++;
				}
			}
		}
		// Natural to put in 3rd column as middle as possible
		if (count <= 1) {
			return 3;
		}

		// If it is not the first round, return minimax

		return minimax(this.game, 0, player);

	}


	private int minimax(Game newlySimulatedGame, int depth, Player player) {
		Boolean maxPlayer = player.getColor() == currentPlayer.getColor();
		int bestValue = (player.getColor() == currentPlayer.getColor()? -1000001:10000);

		int bestPath = 0;
		Player otherPlayer = (player.getColor() == SlotState.RED)?new Player(SlotState.YELLOW):new Player(SlotState.RED);
		newlySimulatedGame.getGameStatus().setCurrentPlayer(player);
        if(depth == maxDepth) {
            return eval(generateSlotStateMatrix(newlySimulatedGame.getGameStatus().getBoard()), player.getColor());
        }
			for (int column = 0; column < newlySimulatedGame.getGameStatus().getBoard().getBoard()[0].length; column++) {
				if(newlySimulatedGame.validateMove(column)) {
					Slot slot = newlySimulatedGame.simulatedDiscDrop(column, player);

					if(newlySimulatedGame.checkWin(slot)) {		// check win horizontal does not work as supposed
						bestValue = (maxPlayer)? 1000000:-1000000;
						bestPath = column;
					} else {
						int value = minimax(newlySimulatedGame, depth + 1, otherPlayer);
							if (value >= bestValue && maxPlayer) {
								bestValue = value;
								bestPath = column;
							}
							else if (value <= bestValue && !maxPlayer){
								bestValue = value;
								bestPath = column;
							}
					}
					newlySimulatedGame.getGameStatus().getBoard().setSlot(new Slot(SlotState.EMPTY), slot.getRow(), slot.getColumn());
				}
			}

		if(depth == 0) {
			return bestPath;
		}
		return bestValue;
	}


	public int eval(SlotState[][] board, SlotState player) {
		player = currentPlayer.getColor();
		int value = 0;
        value += checkHorizontal2inRow(board, player);
	    value += checkHorizontal3inRow(board, player);
		value += checkVertical2inRow(board, player);
        value += checkDiagonal3inRowOpenEndedLeft(board, player);
        value += checkDiagonal3inRowOpenEndedRight(board, player);
        value += checkDiagonal2inRowRight(board,player);
        value += checkDiagonal2inRowLeft(board,player);
        value += checkDiagonal3inRowLeft(board,player);
        value += checkDiagonal3inRowRight(board,player);

        return value;
	}

	/*
	 * Horizontal
	 */
	public int checkHorizontal2inRow(SlotState[][] board, SlotState player) {
	    int value = 0;
		int h = 3;
		int twoInRow = 10;
		for (int row = 0; row < board.length; row++) {
			for (int column = 0; column < 4; column++) {
				//(xx00)
				if (board[row][column] == player &&
					board[row][column + 1] == player &&
					board[row][column + 2] == isEmpty &&
					board[row][column + 3] == isEmpty
						) {
					value += twoInRow * h;
					//	System.out.println("(xx00)");

				} //(x0x0)
				else if(board[row][column] == player &&
						board[row][column + 1] == isEmpty &&
						board[row][column + 2] == player &&
						board[row][column + 3] == isEmpty
						) {
					value += twoInRow * h;
					//	System.out.println("(x0x0)");
				} //(x00x)
				else if(board[row][column] == player &&
						board[row][column + 1] == isEmpty &&
						board[row][column + 2] == isEmpty &&
						board[row][column + 3] == player
						) {
					value += twoInRow * h;
					//System.out.println("(x00x)");
				} //(0xx0)
				else if(board[row][column] == isEmpty &&
						board[row][column + 1] == player &&
						board[row][column + 2] == player &&
						board[row][column + 3] == isEmpty
						) {
					value += 2 * twoInRow * h;
					//System.out.println("(0xx0)");
				} //(0x0x)
				else if(board[row][column] == isEmpty &&
						board[row][column + 1] == player &&
						board[row][column + 2] == isEmpty &&
						board[row][column + 3] == player
						) {
					value += twoInRow * h;
					//System.out.println("(0x0x)");
				} //(00xx)
				else if(board[row][column] == isEmpty &&
						board[row][column + 1] == isEmpty &&
						board[row][column + 2] == player &&
						board[row][column + 3] == player
						) {
					value += twoInRow * h;
					//System.out.println("(00xx)");
				}
			}
		}
		return value;
	}
	public int checkHorizontal3inRow(SlotState[][] board, SlotState player) {
		int value = 0;
		int h = 3;
		int threeInRow = 1000;
		for (int row = 0; row < board.length; row++) {
			for (int column = 0; column < 4; column++) {
				//(xx0x)
				if (board[row][column] == player &&
					board[row][column + 1] == player &&
					board[row][column + 2] == isEmpty &&
					board[row][column + 3] == player
					) {
					value += threeInRow * h;
					//	System.out.println("(xx0x)");

				} //(x0xx)
				else if(board[row][column] == player &&
						board[row][column + 1] == isEmpty &&
						board[row][column + 2] == player &&
						board[row][column + 3] == player
						) {
					value += threeInRow * h;
					//	System.out.println("(x0xx)");
				} //(0xxx)
				else if(board[row][column] == isEmpty &&
						board[row][column + 1] == player &&
						board[row][column + 2] == player &&
						board[row][column + 3] == player
						) {
					value += threeInRow * h;
					//System.out.println("(0xxx)");
				} //(xxx0)
				else if(board[row][column] == player &&
						board[row][column + 1] == player &&
						board[row][column + 2] == player &&
						board[row][column + 3] == isEmpty
						) {
					value += threeInRow * h;
					//System.out.println("(xxx0)");
				}
			}
		}

		return value;
	}
	public int checkHorizontal3inRowOpenEnded(SlotState[][] board, SlotState player) {
        int value = 0;
		int h = 3;
		int threeInRow = 1000;

        for (int row = 0; row < board.length; row++) {
            for (int column = 0; column < 4; column++) {
               if (board[row][column] == isEmpty &&
                   board[row][column+1] == player &&
                   board[row][column+2] == player &&
                   board[row][column+3] == player &&
                   board[row][column+4] == isEmpty){{
                       value+=threeInRow*h*2;
				   // System.out.println("(0xxx0)");
               }}
            }
        }


		return value;
	}

	/*
	 * Vertical
	 */
	public int checkVertical2inRow(SlotState[][] board, SlotState player) {
		int v = 1;
		int twoInRow = 10;
		int value = 0;

		for (int row = 5; row > 2; row--) {
			for (int column = 0; column < 7; column++) {
				// 0
				// 0
				// x
				// x
				if (board[row][column] == player &&
					board[row-1][column] == player &&
					board[row-2][column] == isEmpty &&
					board[row-3][column] == isEmpty
						) {
					value += twoInRow * v;
					//System.out.format("(0)%n(0)%n(x)%n(x)%n");

				}
			}
		}
		return value;
	}
	public int checkVertical3inRow(SlotState[][] board, SlotState player) {
        int value = 0;
		int v = 1;
		int threeInRow = 1000;


		for (int row = 5; row > 2; row--) {
			for (int column = 0; column < 7; column++) {
				// 0
				// x
				// x
				// x
				if (board[row][column] == player &&
					board[row-1][column] == player &&
					board[row-2][column] == player &&
					board[row-3][column] == isEmpty
						) {
					value += threeInRow * v;
					//	System.out.format("0%n0%nx%nx");

				}
			}
		}
		return value;
	}

	/*
 	* Diagonal
 	*/
    /**
     * Check for diagonal spaced 2-in-a-row (/).
     *    0     x      x     x      0      0
     *   0     0      x     0      x      x
     *  x     0      0     x      0      x
     * x     x      0     0      x      0
     */
	public int checkDiagonal2inRowRight(SlotState[][] board, SlotState player) {
        int value = 0;
		int d = 2;
		int twoInRow = 10;

        for (int row = 0; row<board.length-3; row++) {
            for (int column = 3; column < board[0].length; column++) {
                if (board[row][column] == isEmpty &&
                    board[row+1][column-1] == isEmpty &&
                    board[row+2][column-2] == player &&
                    board[row+3][column-3] == player){
					//   System.out.println("   (0)");
					//   System.out.println("  (0)");
					//   System.out.println(" (x)");
					//   System.out.println("(x)");
					value+=twoInRow*d;
                }
                else if(board[row][column] == player &&
                        board[row+1][column-1] == isEmpty &&
                        board[row+2][column-2] == isEmpty &&
                        board[row+3][column-3] == player){
					//	System.out.println("   (x)");
					//	System.out.println("  (0)");
					//	System.out.println(" (0)");
					//	System.out.println("(x)");
                    value+=twoInRow*d;
                }
                else if(board[row][column] == player &&
                        board[row+1][column-1] == player &&
                        board[row+2][column-2] == isEmpty &&
                        board[row+3][column-3] == isEmpty){
					//System.out.println("   (x)");
					//	System.out.println("  (x)");
					//	System.out.println(" (0)");
					//	System.out.println("(0)");
                    value+=twoInRow*d;
                }
                else if(board[row][column] == player &&
                        board[row+1][column-1] == isEmpty &&
                        board[row+2][column-2] == player &&
                        board[row+3][column-3] == isEmpty){
					//	System.out.println("   (x)");
					//	System.out.println("  (0)");
					//	System.out.println(" (x)");
					//	System.out.println("(0)");
                    value+=twoInRow*d;
                }
                else if(board[row][column] == isEmpty &&
                        board[row+1][column-1] == player &&
                        board[row+2][column-2] == isEmpty &&
                        board[row+3][column-3] == player){
					//	System.out.println("   (0)");
					//	System.out.println("  (x)");
					//	System.out.println(" (0)");
					//	System.out.println("(x)");
                    value+=twoInRow*d;
                }
                else if(board[row][column] == isEmpty &&
                        board[row+1][column-1] == player &&
                        board[row+2][column-2] == player &&
                        board[row+3][column-3] == isEmpty){
					//	System.out.println("   (0)");
					//	System.out.println("  (x)");
					//	System.out.println(" (x)");
					//	System.out.println("(0)");
                    value+=twoInRow*d*2;
                }
            }
        }


        return value;
	}
	public int checkDiagonal2inRowLeft(SlotState[][] board, SlotState player) {
        int value = 0;
        int d = 2;
        int twoInRow = 10;

        for (int row = 0; row < board.length-3; row++) {
            for (int column = 0; column < board[0].length-3; column++) {
                if (board[row][column] == isEmpty &&
					board[row+1][column+1] == isEmpty &&
					board[row+2][column+2] == player &&
					board[row+3][column+3] == player){
					//   System.out.println("(0)");
					//  System.out.println(" (0)");
					//  System.out.println("  (x)");
					//  System.out.println("   (x)");
                    value+=twoInRow*d;
                }
                else if(board[row][column] == player &&
                        board[row+1][column+1] == isEmpty &&
                        board[row+2][column+2] == isEmpty &&
                        board[row+3][column+3] == player){
					//	System.out.println("(x)");
					//	System.out.println(" (0)");
					//	System.out.println("  (0)");
					//	System.out.println("   (x)");
						value+=twoInRow*d;
                }
                else if(board[row][column] == player &&
                        board[row+1][column+1] == player &&
                        board[row+2][column+2] == isEmpty &&
                        board[row+3][column+3] == isEmpty){
					//	System.out.println("(x)");
					//	System.out.println(" (x)");
					//	System.out.println("  (0)");
					//	System.out.println("   (0)");
						value+=twoInRow*d;
                }
                else if(board[row][column] == isEmpty &&
                        board[row+1][column+1] == player &&
                        board[row+2][column+2] == isEmpty &&
                        board[row+3][column+3] == player){
					//	System.out.println("(x)");
					//	System.out.println(" (0)");
					//	System.out.println("  (x)");
					//	System.out.println("   (0)");
						value+=twoInRow*d;
                }
                else if(board[row][column] == isEmpty &&
                        board[row+1][column+1] == player &&
                        board[row+2][column+2] == isEmpty &&
                        board[row+3][column+3] == player){
					//	System.out.println("(0)");
					//	System.out.println(" (x)");
					//	System.out.println("  (0)");
					//	System.out.println("   (x)");
						value+=twoInRow*d;
                }
                else if(board[row][column] == isEmpty &&
                        board[row+1][column+1] == player &&
                        board[row+2][column+2] == player &&
                        board[row+3][column+3] == isEmpty){
					//	System.out.println("(0)");
					//	System.out.println(" (x)");
					//	System.out.println("  (x)");
					//	System.out.println("   (0)");
						value+=twoInRow*d*2;
                }
            }
        }
        return value;
    }
    /**
 * Check for diagonal spaced 3-in-a-row (\).
 * 0     x     x     x
 *  x     0     x     x
 *   x     x     0     x
 *    x     x     x     0
 */
	public int checkDiagonal3inRowLeft(SlotState[][] board, SlotState player) {
        int value = 0;
        int d = 2;
        int threeInRow = 1000;

        for (int row = 0; row < board.length-3; row++) {
            for (int column = 0; column < board[0].length-3; column++) {
                if (board[row][column] == isEmpty &&
					board[row+1][column+1] == player &&
					board[row+2][column+2] == player &&
					board[row+3][column+3] == player){
					//  System.out.println("(0)");
					//   System.out.println(" (x)");
					//   System.out.println("  (x)");
					//   System.out.println("   (x)");
                    value+=threeInRow*d;
                }
                else if(board[row][column] == player &&
                        board[row+1][column+1] == isEmpty &&
                        board[row+2][column+2] == player &&
                        board[row+3][column+3] == player){
					//		System.out.println("(x)");
					//		System.out.println(" (0)");
					//		System.out.println("  (x)");
					//	System.out.println("   (x)");
						value+=threeInRow*d;
                }
                else if(board[row][column] == player &&
                        board[row+1][column+1] == player &&
                        board[row+2][column+2] == isEmpty &&
                        board[row+3][column+3] == player){
					//		System.out.println("(x)");
					//		System.out.println(" (x)");
					//		System.out.println("  (0)");
					//			System.out.println("   (x)");
						value+=threeInRow*d;
                }
                else if(board[row][column] == player &&
                        board[row+1][column+1] == player &&
                        board[row+2][column+2] == player &&
                        board[row+3][column+3] == isEmpty){
					//				System.out.println("(x)");
					//				System.out.println(" (x)");
					//				System.out.println("  (x)");
					//				System.out.println("   (0)");
						value+=threeInRow*d;
                }

            }
        }
        return value;
	}
    /**
     * Check for diagonal spaced 3-in-a-row (/).
     *    0     x      x     x
     *   x     0      x     x
     *  x     x      0     x
     * x     x      x     0
     */
	public int checkDiagonal3inRowRight(SlotState[][] board, SlotState player) {
        int value = 0;
        int d = 2;
        int threeInRow = 1000;

        for (int row = 0; row < board.length-3; row++) {
            for (int column = 3; column < board[0].length; column++) {
                if (board[row][column] == isEmpty &&
					board[row+1][column-1] == player &&
					board[row+2][column-2] == player &&
					board[row+3][column-3] == player){
					//	   System.out.println("   (0)");
					//	    System.out.println("  (x)");
					//	    System.out.println(" (x)");
					//	    System.out.println("(x)");
                    value+=threeInRow*d;
                }
                else if(board[row][column] == player &&
                        board[row+1][column-1] == isEmpty &&
                        board[row+2][column-2] == player &&
                        board[row+3][column-3] == player){
					//			System.out.println("   (x)");
					//			System.out.println("  (0)");
					//			System.out.println(" (x)");
					//		System.out.println("(x)");
						value+=threeInRow*d;
                }
                else if(board[row][column] == player &&
                        board[row+1][column-1] == player &&
                        board[row+2][column-2] == isEmpty &&
                        board[row+3][column-3] == player){
					//			System.out.println("   (x)");
					//		System.out.println("  (x)");
					//		System.out.println(" (0)");
					//		System.out.println("(x)");
						value+=threeInRow*d;
                }
                else if(board[row][column] == player &&
                        board[row+1][column-1] == player &&
                        board[row+2][column-2] == player &&
                        board[row+3][column-3] == isEmpty){
					//		System.out.println("   (x)");
					//			System.out.println("  (x)");
					//			System.out.println(" (x)");
					//			System.out.println("(0)");
						value+=threeInRow*d;
                }

            }
        }
        return value;
	}
	public int checkDiagonal3inRowOpenEndedLeft(SlotState[][] board, SlotState player) {
		//diag (\)(X000X)
        int value = 0;
		int d = 2;
		int threeInRow = 1000;
        for (int row=0; row< board.length-4;row++) {
            for (int column = 0; column < board[0].length-4; column++) {
                if (board[row][column] == isEmpty &&
					board[row + 1][column + 1] == player &&
					board[row + 2][column + 2] == player &&
					board[row + 3][column + 3] == player &&
					board[row + 4][column + 4] == isEmpty) {
                    value += 2 * threeInRow * d;
                }
            }
        }


		return value;
	}
	public int checkDiagonal3inRowOpenEndedRight(SlotState[][] board, SlotState player) {
		//diag (/)(X000X)
	    int value = 0;
		int d = 2;
		int threeInRow = 1000;
		for (int row=0; row< board.length-4;row++) {
            for (int column = 4; column < board[0].length; column++) {
                if (board[row][column] == isEmpty &&
					board[row + 1][column - 1] == player &&
					board[row + 2][column - 2] == player &&
					board[row + 3][column - 3] == player &&
					board[row + 4][column - 4] == isEmpty) {
                    value += 2 * threeInRow * d;
                }
            }
        }
		return value;
	}

	private SlotState [][] generateSlotStateMatrix(Board board){
		SlotState[][] slotStateMatrix = new SlotState[board.getBoard().length][board.getBoard()[0].length];
		for (int i = 0;i<board.getBoard().length;i++){
			for (int p = 0;p<board.getBoard()[0].length;p++){
				slotStateMatrix[i][p] = board.getSlot(i,p).getSlotState();
			}
		}
		return slotStateMatrix;
	}




	private void printMatrix(Board board){
		for (int i = 0;i<board.getBoard().length;i++){
			for (int p = 0;p<board.getBoard()[0].length;p++){
				System.out.print(board.getBoard()[i][p].getSlotState() + " ");
			}
			System.out.println();
		}
		System.out.println();
		System.out.println();
	}

	public void setDepth(int depth) {
		this.maxDepth = depth;
	}
}
