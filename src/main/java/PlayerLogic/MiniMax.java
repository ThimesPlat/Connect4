package PlayerLogic;

import GameLogic.Board;
import GameLogic.Game;
import GameLogic.Slot;
import GameLogic.SlotState;

public class MiniMax {

	private Game game;
	// private Board board;
	private int maxDepth;
	private Slot currentSlot ;

	public MiniMax(Game game) {
		this.game = game;
		maxDepth = 4;
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
		return negamax(this.game.getGameStatus().getBoard(), -100000, 0, player);
	}

	// calls it self and returns the best column that player will choose
	private int negamax(Board board, int alpha, int depth, Player player) {
		int bestPath = 0;
		int bestValue = alpha;
		Game copiedGame = new Game();
		copiedGame.setGameStatus(this.game.getGameStatus());
		int playerFactor;
		if (player.getColor() == copiedGame.getGameStatus().getCurrentPlayer().getColor()) {
			playerFactor = 1;
		} else {
			playerFactor = -1;
		}
        currentSlot = copiedGame.getGameStatus().getChangedSlot();
		if (copiedGame.checkWin(currentSlot)) {
			bestValue = playerFactor * (10000000-depth);
		} else if (copiedGame.checkBoardFull() && !copiedGame.checkWin(currentSlot)) {
			bestValue = 0;
		} else if (depth == maxDepth) {
			int score = (eval(copiedGame.getGameStatus().getBoard(), player));
			if (score != 0) {
				bestValue = playerFactor * (score-depth);
			} else {
				bestValue = score-depth;
			}
		} else {
			for (int c = 0; c < 7; c++) {
				Game simGame = new Game();
				simGame.setBoard(board.copyBoard(board));
				if (depth < maxDepth) {
					if (!simGame.validateMove(c)) {
						continue;
					} else {
						Player otherPlayer;
						if (player.getColor() == SlotState.RED) {
							otherPlayer = new Player(SlotState.YELLOW);
						} else {
							otherPlayer = new Player(SlotState.RED);
						}
						currentSlot = simGame.discDrop(c, otherPlayer);
						int value = -negamax(simGame.getGameStatus().getBoard(), -100000, depth + 1, otherPlayer);
						if (value >= bestValue) {
							bestPath = c;
							bestValue = value;
						}
					}
				}

			}
		}

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
				// (xx00)
				if (board.getSlot(row, column).getSlotState().equals(player.getColor())
						&& board.getSlot(row, column).getSlotState()
								.equals(board.getSlot(row, column + 1).getSlotState())
						&& board.getSlot(row, column + 2).getSlotState().equals(SlotState.EMPTY)
						&& board.getSlot(row, column + 3).getSlotState().equals(SlotState.EMPTY)) {
					System.out.println("FSDESFSDFSD");
					value += twoInRow * h;
				}
				// (x0x0)
				else if (board.getSlot(row, column).getSlotState().equals(player.getColor())
						&& board.getSlot(row, column + 2).getSlotState().equals(player.getColor())
						&& board.getSlot(row, column + 1).getSlotState().equals(SlotState.EMPTY)
						&& board.getSlot(row, column + 3).getSlotState().equals(SlotState.EMPTY)) {
					System.out.println("adffdssdf");
					value += twoInRow * h;

				}
				// (x00x)
				else if (board.getSlot(row, column).getSlotState().equals(player.getColor())
						&& board.getSlot(row, column + 3).getSlotState().equals(player.getColor())
						&& board.getSlot(row, column + 1).getSlotState().equals(SlotState.EMPTY)
						&& board.getSlot(row, column + 2).getSlotState().equals(SlotState.EMPTY)) {
					System.out.println("87ti8776786432§§§§§");
					value += twoInRow * h;
				}
				// (0xx0)
				else if (board.getSlot(row, column).getSlotState().equals(SlotState.EMPTY)
						&& board.getSlot(row, column + 1).getSlotState().equals(player.getColor())
						&& board.getSlot(row, column + 2).getSlotState().equals(player.getColor())
						&& board.getSlot(row, column + 3).getSlotState().equals(SlotState.EMPTY)) {
					System.out.println("tjena");
					value += 2 * twoInRow * h;
				}
				// (0x0x)
				else if (board.getSlot(row, column).getSlotState().equals(SlotState.EMPTY)
						&& board.getSlot(row, column + 1).getSlotState().equals(player.getColor())
						&& board.getSlot(row, column + 2).getSlotState().equals(SlotState.EMPTY)
						&& board.getSlot(row, column + 3).getSlotState().equals(player.getColor())) {
					System.out.println("9998876544");

					value += twoInRow * h;
				}
				// (00xx)
				else if (board.getSlot(row, column).getSlotState().equals(SlotState.EMPTY)
						&& board.getSlot(row, column).getSlotState()
								.equals(board.getSlot(row, column + 1).getSlotState())
						&& board.getSlot(row, column + 2).getSlotState().equals(player.getColor())
						&& board.getSlot(row, column + 3).getSlotState().equals(player.getColor())) {
					System.out.println("00000000");

					value += twoInRow * h;
				}
			}
		}

		// check for vertical spaced 2-in-a-row
		for (int row = 5; row > 1; row--) { // does not work :D
			for (int column = 0; column < 7; column++) {
				if (board.getSlot(row, column).getSlotState().equals(player.getColor())
						&& board.getSlot(row, column).getSlotState()
								.equals(board.getSlot(row - 1, column).getSlotState())
						&& board.getSlot(row - 2, column).getSlotState().equals(SlotState.EMPTY)) {
					System.out.println("+++++++");

					value += twoInRow * v;
				}
			}
		}
		// Check for diagonal spaced 2-in-a-row (/).
		// 0 x x x 0 0
		// 0 0 x 0 x x
		// x 0 0 x 0 x
		// x x 0 0 x 0
		for (int row = 5; row > 2; row--) {
			for (int column = 0; column < 4; column++) {
				if (board.getSlot(row, column).getSlotState().equals(player.getColor())
						&& board.getSlot(row, column).getSlotState()
								.equals(board.getSlot(row - 1, column + 1).getSlotState())
						&& board.getSlot(row - 2, column + 2).getSlotState().equals(SlotState.EMPTY)
						&& board.getSlot(row - 3, column + 3).getSlotState().equals(SlotState.EMPTY)) {
					System.out.println("´´´´´´");

					value += twoInRow * d;
				} else if (board.getSlot(row, column).getSlotState().equals(player.getColor())
						&& board.getSlot(row - 1, column + 1).getSlotState().equals(SlotState.EMPTY)
						&& board.getSlot(row - 2, column + 2).getSlotState().equals(player.getColor())
						&& board.getSlot(row, column).getSlotState()
								.equals(board.getSlot(row - 3, column + 3).getSlotState())) {
					System.out.println("]{");

					value += twoInRow * d;
				} else if (board.getSlot(row, column).getSlotState().equals(SlotState.EMPTY)
						&& board.getSlot(row - 1, column + 1).getSlotState().equals(SlotState.EMPTY)
						&& board.getSlot(row - 2, column + 2).getSlotState().equals(player.getColor())
						&& board.getSlot(row - 3, column + 3).getSlotState().equals(player.getColor())) {
					System.out.println("£$");

					value += twoInRow * d;
				} else if (board.getSlot(row, column).getSlotState().equals(SlotState.EMPTY)
						&& board.getSlot(row - 1, column + 1).getSlotState().equals(player.getColor())
						&& board.getSlot(row, column).getSlotState()
								.equals(board.getSlot(row - 2, column + 2).getSlotState())
						&& board.getSlot(row - 1, column + 1).getSlotState()
								.equals(board.getSlot(row - 3, column + 3).getSlotState())) {
					System.out.println("€£$€£$€£$€£$€£$€");

					value += twoInRow * d;
				} else if (board.getSlot(row, column).getSlotState().equals(player.getColor())
						&& board.getSlot(row - 1, column + 1).getSlotState().equals(SlotState.EMPTY)
						&& board.getSlot(row, column).getSlotState()
								.equals(board.getSlot(row - 2, column + 2).getSlotState())
						&& board.getSlot(row - 1, column + 1).getSlotState()
								.equals(board.getSlot(row - 3, column + 3).getSlotState())) {
					System.out.println("43576876854382368697538967536897538795326879527689543");

					value += twoInRow * d;
				} else if (board.getSlot(row, column).getSlotState().equals(SlotState.EMPTY)
						&& board.getSlot(row - 1, column + 1).getSlotState().equals(player.getColor())
						&& board.getSlot(row - 1, column + 1).getSlotState()
								.equals(board.getSlot(row - 2, column + 2).getSlotState())
						&& board.getSlot(row, column).getSlotState()
								.equals(board.getSlot(row - 3, column + 3).getSlotState())) {
					System.out.println("1234567890");

					value += 2 * twoInRow * d;
				}
			}
		}
		// Check for diagonal spaced 3-in-a-row (\).
		// 0 x x x
		// x 0 x x
		// x x 0 x
		// x x x 0
		for (int row = 0; row < 3; row++) {
			for (int column = 0; column < 4; column++) {
				if (board.getSlot(row, column).getSlotState().equals(player.getColor())
						&& board.getSlot(row, column).getSlotState()
								.equals(board.getSlot(column + 1, row + 1).getSlotState())
						&& board.getSlot(row + 2, column + 2).getSlotState().equals(SlotState.EMPTY)
						&& board.getSlot(row + 3, column + 3).getSlotState().equals(SlotState.EMPTY)) {
					System.out.println("FSDESFSDFSD");

					value += twoInRow * d;
				} else if (board.getSlot(row, column).getSlotState().equals(player.getColor())
						&& board.getSlot(row + 1, column + 1).getSlotState().equals(SlotState.EMPTY)
						&& board.getSlot(row + 2, column + 2).getSlotState().equals(SlotState.EMPTY)
						&& board.getSlot(row, column).getSlotState()
								.equals(board.getSlot(row + 3, column + 3).getSlotState())) {
					System.out.println("+00+909+09+09+09+9");

					value += twoInRow * d;
				} else if (board.getSlot(row, column).getSlotState().equals(SlotState.EMPTY)
						&& board.getSlot(row + 1, column + 1).getSlotState().equals(SlotState.EMPTY)
						&& board.getSlot(row + 2, column + 2).getSlotState().equals(player.getColor())
						&& board.getSlot(row + 3, column + 3).getSlotState().equals(player.getColor())) {
					System.out.println("öööööööö");

					value += twoInRow * d;
				} else if (board.getSlot(row, column).getSlotState().equals(SlotState.EMPTY)
						&& board.getSlot(row + 1, column + 1).getSlotState().equals(player.getColor())
						&& board.getSlot(row, column).getSlotState()
								.equals(board.getSlot(row + 2, column + 2).getSlotState())
						&& board.getSlot(row + 1, column + 1).getSlotState()
								.equals(board.getSlot(row + 3, column + 3).getSlotState())) {
					System.out.println("FSDESFSDFSD");

					value += twoInRow * d;
				} else if (board.getSlot(row, column).getSlotState().equals(player.getColor())
						&& board.getSlot(row + 1, column + 1).getSlotState().equals(SlotState.EMPTY)
						&& board.getSlot(row, column).getSlotState()
								.equals(board.getSlot(row + 2, column + 2).getSlotState())
						&& board.getSlot(row + 1, column + 1).getSlotState()
								.equals(board.getSlot(row + 3, column + 3).getSlotState())) {
					System.out.println("ååååååååå");

					value += twoInRow * d;
				} else if (board.getSlot(row, column).getSlotState().equals(SlotState.EMPTY)
						&& board.getSlot(row + 1, column + 1).getSlotState().equals(player.getColor())
						&& board.getSlot(row + 1, column + 1).getSlotState()
								.equals(board.getSlot(row + 2, column + 2).getSlotState())
						&& board.getSlot(row, column).getSlotState()
								.equals(board.getSlot(row + 3, column + 3).getSlotState())) {
					System.out.println("ääääääääää");

					value += twoInRow * 2 * d;
				}
			}
		}
		// Check for horizontal 3-in-a-row.

		for (int row = 0; row < 6; row++) {
			for (int column = 0; column < 4; column++) {
				// (xx0x)
				if (board.getSlot(row, column).getSlotState().equals(player.getColor())
						&& board.getSlot(row, column).getSlotState()
								.equals(board.getSlot(row, column + 1).getSlotState())
						&& board.getSlot(row, column + 2).getSlotState().equals(SlotState.EMPTY)
						&& board.getSlot(row, column).getSlotState()
								.equals(board.getSlot(row, column + 3).getSlotState())) {
					System.out.println("äöåäööäöäöäåöäåöåääöåöå");

					value += threeInRow * h;
				}
				// (x0xx)
				else if (board.getSlot(row, column).getSlotState().equals(player.getColor())
						&& board.getSlot(row, column + 1).getSlotState().equals(SlotState.EMPTY)
						&& board.getSlot(row, column).getSlotState()
								.equals(board.getSlot(row, column + 2).getSlotState())
						&& board.getSlot(row, column).getSlotState()
								.equals(board.getSlot(row, column + 3).getSlotState())) {
					System.out.println("1ä1ää1ä1ä1ä1ä1ä1ä1ää");

					value += threeInRow * h;
				}
				// (0xxx)
				else if (board.getSlot(row, column).getSlotState().equals(SlotState.EMPTY)
						&& board.getSlot(row, column + 1).getSlotState().equals(player.getColor())
						&& board.getSlot(row, column + 1).getSlotState()
								.equals(board.getSlot(row, column + 2).getSlotState())
						&& board.getSlot(row, column + 1).getSlotState()
								.equals(board.getSlot(row, column + 3).getSlotState())) {
					System.out.println("<444<4<4<<4<44<4<4<4<444<<4");

					value += threeInRow * d;
				}
				// (xxx0)
				else if (board.getSlot(row, column).getSlotState().equals(player.getColor())
						&& board.getSlot(row, column).getSlotState()
								.equals(board.getSlot(row, column + 1).getSlotState())
						&& board.getSlot(row, column).getSlotState()
								.equals(board.getSlot(row, column + 2).getSlotState())
						&& board.getSlot(row, column + 3).getSlotState().equals(SlotState.EMPTY)) {
					System.out.println("6<6<6666<<66<6<6<");

					value += threeInRow * h;
				}
			}
		}
		// Check for vertical spaced 3-in-a-row.
		// 0
		// x
		// x
		// x
		for (int row = 5; row > 2; row--) {
			for (int column = 0; column < 7; column++) {
				if (board.getSlot(row, column).getSlotState().equals(player.getColor())
						&& board.getSlot(row, column).getSlotState()
								.equals(board.getSlot(row - 1, column).getSlotState())
						&& board.getSlot(row, column).getSlotState()
								.equals(board.getSlot(row - 2, column).getSlotState())
						&& board.getSlot(row - 3, column).getSlotState().equals(SlotState.EMPTY)) {
					System.out.println("5<5<5<555<55");

					value += threeInRow * v;
				}

			}
		}
		// Check for diagonal spaced 3-in-a-row (/).
		// 0 x x x
		// x 0 x x
		// x x 0 x
		// x x x 0
		for (int row = 5; row > 2; row--) {
			for (int column = 0; column < 4; column++) {
				if (board.getSlot(row, column).getSlotState().equals(player.getColor())
						&& board.getSlot(row, column).getSlotState()
								.equals(board.getSlot(row - 1, column + 1).getSlotState())
						&& board.getSlot(row, column).getSlotState()
								.equals(board.getSlot(row - 2, column + 2).getSlotState())
						&& board.getSlot(row - 3, column + 3).getSlotState().equals(SlotState.EMPTY)) {
					System.out.println("<<<<5");

					value += threeInRow * d;
				} else if (board.getSlot(row, column).getSlotState().equals(player.getColor())
						&& board.getSlot(row, column + 1).getSlotState()
								.equals(board.getSlot(row - 1, column + 1).getSlotState())
						&& board.getSlot(row - 2, column + 2).getSlotState().equals(SlotState.EMPTY)
						&& board.getSlot(row, column).getSlotState()
								.equals(board.getSlot(row - 3, column + 3).getSlotState())) {
					System.out.println("ryhsths5hsb");

					value += threeInRow * d;
				} else if (board.getSlot(row, column).getSlotState().equals(player.getColor())
						&& board.getSlot(row - 1, column + 1).getSlotState().equals(SlotState.EMPTY)
						&& board.getSlot(row, column).getSlotState()
								.equals(board.getSlot(row - 2, column + 2).getSlotState())
						&& board.getSlot(row, column).getSlotState()
								.equals(board.getSlot(row - 3, column + 3).getSlotState())) {
					System.out.println("ytjxfthdfhdr6u");

					value += threeInRow * d;
				} else if (board.getSlot(row, column).getSlotState().equals(SlotState.EMPTY)
						&& board.getSlot(row - 1, column + 1).getSlotState().equals(player.getColor())
						&& board.getSlot(row - 1, column + 1).getSlotState()
								.equals(board.getSlot(row - 2, column + 2).getSlotState())
						&& board.getSlot(row - 1, column + 1).getSlotState()
								.equals(board.getSlot(row - 3, column + 3).getSlotState())) {
					System.out.println("tyj567w5ys");

					value += threeInRow * d;
				}
			}
		}
		// Check for diagonal spaced 3-in-a-row (\).
		// 0 x x x
		// x 0 x x
		// x x 0 x
		// x x x 0
		for (int row = 0; row < 3; row++) {
			for (int column = 0; column < 4; column++) {
				if (board.getSlot(row, column).getSlotState().equals(SlotState.EMPTY)
						&& board.getSlot(row + 1, column + 1).getSlotState().equals(player.getColor())
						&& board.getSlot(row + 1, column + 1).getSlotState()
								.equals(board.getSlot(row + 3, column + 3).getSlotState())) {
					System.out.println("dy457r6uyx");

					value += threeInRow * d;
				} else if (board.getSlot(row, column).getSlotState().equals(player.getColor())
						&& board.getSlot(row + 1, column + 1).getSlotState().equals(SlotState.EMPTY)
						&& board.getSlot(row, column).getSlotState()
								.equals(board.getSlot(row + 2, column + 2).getSlotState())
						&& board.getSlot(row, column).getSlotState()
								.equals(board.getSlot(row + 3, column + 3).getSlotState())) {
					System.out.println("5757u56eue6u");

					value += threeInRow * d;
				} else if (board.getSlot(row, column).getSlotState().equals(player.getColor())
						&& board.getSlot(row, column).getSlotState()
								.equals(board.getSlot(row + 1, column + 1).getSlotState())
						&& board.getSlot(row + 2, column + 2).getSlotState().equals(SlotState.EMPTY)
						&& board.getSlot(row, column).getSlotState()
								.equals(board.getSlot(row + 3, column + 3).getSlotState())) {
					System.out.println("rs66s6sr");

					value += threeInRow * d;
				} else if (board.getSlot(row, column).getSlotState().equals(player.getColor())
						&& board.getSlot(row, column).getSlotState()
								.equals(board.getSlot(row + 1, column + 1).getSlotState())
						&& board.getSlot(row, column).getSlotState()
								.equals(board.getSlot(row + 2, column + 2).getSlotState())
						&& board.getSlot(row + 3, column + 3).getSlotState().equals(SlotState.EMPTY)) {
					System.out.println("6y6y66eye6");

					value += threeInRow * d;
				}
			}
		}
		// check for open-ended-in-a-row.(0xxx0)
		for (int row = 0; row < 5; row++) {
			for (int column = 0; column < 3; column++) {
				// horizontal
				if (board.getSlot(row, column).getSlotState().equals(SlotState.EMPTY)
						&& board.getSlot(row, column + 1).getSlotState().equals(player.getColor())
						&& board.getSlot(row + 1, column + 2).getSlotState().equals(player.getColor())
						&& board.getSlot(row + 1, column + 3).getSlotState().equals(player.getColor())
						&& board.getSlot(row, column).equals(board.getSlot(row, column + 4))) {
					System.out.println("uiki67");

					value += 2 * threeInRow * h;
				}
			}
		}
		for (int row = 0; row < 2; row++) {
			for (int column = 0; column < 3; column++) {
				// diag(\)
				if (board.getSlot(row, column).getSlotState().equals(SlotState.EMPTY)
						&& board.getSlot(row + 1, column + 1).getSlotState().equals(player.getColor())
						&& board.getSlot(row, column).getSlotState()
								.equals(board.getSlot(row + 2, column + 2).getSlotState())
						&& board.getSlot(row, column).getSlotState()
								.equals(board.getSlot(row + 3, column + 3).getSlotState())
						&& board.getSlot(row + 4, column + 4).getSlotState().equals(SlotState.EMPTY)) {
					System.out.println("asdfasdfdf");

					value += 2 * threeInRow * d;
				}
			}
		}
		// diag(/)
		for (int row = 5; row > 3; row--) {
			for (int column = 0; column < 3; column++) {
				if (board.getSlot(row, column).getSlotState().equals(SlotState.EMPTY)
						&& board.getSlot(row - 1, column + 1).getSlotState().equals(player.getColor())
						&& board.getSlot(row - 2, column + 2).getSlotState().equals(player.getColor())
						&& board.getSlot(row - 3, column + 3).getSlotState().equals(player.getColor())
						&& board.getSlot(row - 4, column + 4).getSlotState().equals(SlotState.EMPTY)) {
					System.out.println("ghjfghj");

					value += 2 * threeInRow * d;
				}
			}
		}
		return value;
	}
}
