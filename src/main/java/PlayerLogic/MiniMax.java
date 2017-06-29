package PlayerLogic;

import GameLogic.Board;
import GameLogic.Game;
import GameLogic.Slot;
import GameLogic.SlotState;

public class MiniMax {

	private Game game;
	private int maxDepth;
	int roflcoptr = 0;
	SlotState isEmpty = SlotState.EMPTY;

	public MiniMax(Game game) {

		this.game = game;

		maxDepth = 0;
	}

	// decide which column we have to pick
	public int calcValue(Player player) {
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
			if (this.game.getGameStatus().getBoard().getSlot(5, 2).getSlotState().equals(SlotState.RED)) {
				return 4;
			} else if (this.game.getGameStatus().getBoard().getSlot(5, 4).getSlotState().equals(SlotState.RED)) {
				return 2;
			} else {
				return 3;
			}
		}
		// If it is not the first round, return negamax


		player = (player.getColor() == SlotState.RED)?new Player(SlotState.YELLOW):new Player(SlotState.RED);

		return negamax(this.game, -100000, 0, player);

	}

	// calls it self and returns the best column that player will choose

	private int negamax(Game newlySimulatedGame, int alpha, int depth, Player player) {
		Slot currentSlot;

		int bestPath = 0;
		int bestValue = alpha;

		Game copiedGame = new Game();
		copiedGame.setBoard(newlySimulatedGame.getGameStatus().getBoard());
		copiedGame.getGameStatus().setChangedSlot(newlySimulatedGame.getGameStatus().getChangedSlot());
		copiedGame.getGameStatus().setCurrentPlayer(newlySimulatedGame.getGameStatus().getCurrentPlayer());

		int playerFactor = (player.getColor() == copiedGame.getGameStatus().getCurrentPlayer().getColor())?1:-1;

		currentSlot = copiedGame.getGameStatus().getChangedSlot();
		if (copiedGame.checkWin(currentSlot)) {
			bestValue = playerFactor * (10000000-depth);
		} else if (copiedGame.checkBoardFull() && !copiedGame.checkWin(currentSlot)) {
			bestValue = 0;
		} else if (depth == maxDepth) {
			SlotState[][] slotStateMatrix = generateSlotStateMatrix(copiedGame.getGameStatus().getBoard());
			int score = (eval(slotStateMatrix, player.getColor()));
			if (score != 0) {
				bestValue = playerFactor * (score-depth);
			} else {
				bestValue = score-depth;
			}
		} else {
			for (int c = 0; c < 7; c++) {
				Game simGame = new Game();
				simGame.setBoard(copiedGame.getGameStatus().getBoard());
				simGame.getGameStatus().setChangedSlot(copiedGame.getGameStatus().getChangedSlot());
				simGame.getGameStatus().setCurrentPlayer(copiedGame.getGameStatus().getCurrentPlayer());

				if (depth < maxDepth) {
					if (simGame.validateMove(c)) {
						Player otherPlayer = (player.getColor() == SlotState.RED)?new Player(SlotState.YELLOW):new Player(SlotState.RED);
						Slot slot = simGame.discDrop(c, otherPlayer);
						simGame.getGameStatus().getBoard().setSlot(slot,slot.getRow(),slot.getColumn());
						simGame.getGameStatus().setChangedSlot(slot);
						int value = -negamax(simGame, -100000, depth + 1, otherPlayer);

						if (value >= bestValue) {
							bestPath = c;
							bestValue = value;
						}
						simGame.getGameStatus().getBoard().setSlot(new Slot(SlotState.EMPTY),slot.getRow(),slot.getColumn());
					}

				}
			}

			//	System.out.println(bestValue);
			if (depth == 0) {
				return bestPath;
			} else {
				return bestValue;
			}
		}


		if (depth == 0) {
			return bestPath;
		} else {
			return bestValue;
		}
	}

	public int eval(SlotState[][] board, SlotState player) {

        for (int i = 0;i<board.length;i++){
            for (int p = 0;p<board[0].length;p++){
                System.out.print(board[i][p] + " ");
            }
            System.out.println();
        }
        System.out.println();
        System.out.println();

		int value = 0;
        System.out.println("Player: " + player);
        value+= checkHorizontal2inRow(board, player);
		value+= checkHorizontal3inRow(board, player);
		System.out.println("VALUE: "+value);
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println("___________________________________________________________________________________");
        return value;
	}

	/*
	 * Horizontal
	 */

	private int checkHorizontal2inRow(SlotState[][] board, SlotState player) {
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
					System.out.println("(xx00)");

				} //(x0x0)
				else if (board[row][column] == player &&
						board[row][column + 1] == isEmpty &&
						board[row][column + 2] == player &&
						board[row][column + 3] == isEmpty
						) {
					value += twoInRow * h;
					System.out.println("(x0x0)");
				} //(x00x)
				else if (board[row][column] == player &&
						board[row][column + 1] == isEmpty &&
						board[row][column + 2] == isEmpty &&
						board[row][column + 3] == player
						) {
					value += twoInRow * h;
					System.out.println("(x00x)");
				} //(0xx0)
				else if (board[row][column] == isEmpty &&
						board[row][column + 1] == player &&
						board[row][column + 2] == player &&
						board[row][column + 3] == isEmpty
						) {
					value += 2 * twoInRow * h;
					System.out.println("(0xx0)");
				} //(0x0x)
				else if (board[row][column] == isEmpty &&
						board[row][column + 1] == player &&
						board[row][column + 2] == isEmpty &&
						board[row][column + 3] == player
						) {
					value += twoInRow * h;
					System.out.println("(0x0x)");
				} //(00xx)
				else if (board[row][column] == isEmpty &&
						board[row][column + 1] == isEmpty &&
						board[row][column + 2] == player &&
						board[row][column + 3] == player
						) {
					value += twoInRow * h;
					System.out.println("(00xx)");
				}
			}
		}
		return value;
	}


	private int checkHorizontal3inRow(SlotState[][] board, SlotState player) {
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
					System.out.println("(xx0x)");

				} //(x0xx)
				else if(board[row][column] == player &&
						board[row][column + 1] == isEmpty &&
						board[row][column + 2] == player &&
						board[row][column + 3] == player
						) {
					value += threeInRow * h;
					System.out.println("(x0xx)");
				} //(0xxx)
				else if(board[row][column] == isEmpty &&
						board[row][column + 1] == player &&
						board[row][column + 2] == player &&
						board[row][column + 3] == player
						) {
					value += threeInRow * h;
					System.out.println("(0xxx)");
				} //(xxx0)
				else if(board[row][column] == player &&
						board[row][column + 1] == player &&
						board[row][column + 2] == player &&
						board[row][column + 3] == isEmpty
						) {
					value += threeInRow * h;
					System.out.println("(xxx0)");
				}
			}
		}

		return value;
	}
	private int checkHorizontal3inRowOpenEnded(SlotState[][] board, SlotState player, int value) {
		int h = 3;
		int threeInRow = 1000;
		return value;
	}

	/*
	 * Vertical
	 */
	private int checkVertical2inRow(SlotState[][] board, SlotState player, int value) {
		int v = 1;
		int twoInRow = 10;
		return value;
	}
	private int checkVertical3inRow(SlotState[][] board, SlotState player, int value) {
		int v = 1;
		int threeInRow = 1000;
		return value;
	}

	/*
 	* Diagonal
 	*/
	private int checkDiagonal2inRowRight(SlotState[][] board, SlotState player, int value) {
		int d = 2;
		int twoInRow = 10;

		return value;
	}
	private int checkDiagonal3inRowLeft(SlotState[][] board, SlotState player, int value) {
		int d = 2;
		int threeInRow = 1000;

		return value;
	}
	private int checkDiagonal3inRowRight(SlotState[][] board, SlotState player, int value) {
		int d = 2;
		int threeInRow = 1000;

		return value;
	}
	private int checkDiagonal2inRowLeft(SlotState[][] board, SlotState player, int value) {
		int d = 2;
		int twoInRow = 10;

		return value;
	}
	private int checkDiagonal3inRowOpenEndedLeft(SlotState[][] board, SlotState player, int value) {
		int d = 2;
		int threeInRow = 1000;

		return value;
	}
	private int checkDiagonal3inRowOpenEndedRight(SlotState[][] board, SlotState player, int value) {
		int d = 2;
		int threeInRow = 1000;

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
