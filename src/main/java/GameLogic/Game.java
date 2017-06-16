package GameLogic ;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import PlayerLogic.Player;
import javafx.application.Platform;

/**
 * Created by eps on 2017-06-13.
 */

public class Game {
    GameStatus gameStatus;
    Board board;
    PlayerLogic.Player p1;
    PlayerLogic.Player p2;
    PlayerLogic.Player currentPlayer;
    Timer timer = new Timer();
    int rounds = 0;

    public Game(){
        p1 = new Player(SlotState.RED);
        p2 = new Player(SlotState.YELLOW);
        currentPlayer = p1;
        board = new Board();
        gameStatus = new GameStatus(board, p1);
    }
    
    public void startGame() {
        timer.scheduleAtFixedRate(new TimerTask() {
    		@Override
    		public void run() {
    			Platform.runLater(() -> {
    				newMove();
    			});
    		}
    	}, 100, 100);
    }
    
    public void newMove() {
        Random random = new Random();
    	int column = random.nextInt(7);
    	Slot slot;
        rounds++;

        //System.out.println(gameStatus.currentPlayer.getColor());
        if(validateMove(column)) {
    		slot = discDrop(column);
    		board.setSlot(slot,slot.getRow(),slot.getColumn());
        } else {
        	return;
        }
    	
        /*
    	if (checkWin(slot)) {
            System.out.println("WE HAVE A WINNER: " + currentPlayer.getColor());
            gameStatus.setGameOver(true);
			gameStatus.setWinner(currentPlayer);
			timer.cancel();
		}

        if (checkBoardFull()) {
			gameStatus.setGameOver(true);
			timer.cancel();
		}*/

		gameStatus.setBoard(board);
		gameStatus.setChangedSlot(slot);
		
		setCurrentPlayer(currentPlayer);
		
    }

    public GameStatus getGameStatus() {
        return gameStatus;
    }
    private void setCurrentPlayer(Player currentPlayer) {
    	gameStatus.setCurrentPlayer(updateCurrentPlayer());
    	this.currentPlayer = gameStatus.currentPlayer;
    }
    private Player updateCurrentPlayer(){
        if (gameStatus.currentPlayer.getColor() == SlotState.RED) {
            return p2;
        }
        else if (gameStatus.currentPlayer.getColor() == SlotState.YELLOW) {
                return p1;
            } else {
                System.err.println("updateNextPlayer error : gameStatus next player is something inappropriate");
            }
        return null;
    }

    public boolean checkWin(Slot slot){
    	System.out.format("checkHorizontal: %b%n", checkHorizontal(slot));
        return checkHorizontal(slot) || checkVertical(slot) || checkDiagonal(slot);
    }
    
    private boolean checkBoardFull() {
    	Slot[][] slots = board.getBoard();
    	for(int i = 0; i < slots[0].length; i++) {
			if(slots[0][i] == null) {
				return false;
			}
    	}
    	return true;
    }

    private boolean checkHorizontal(Slot slot) {
    	int column = slot.getColumn();
    	int row = slot.getRow();
    	SlotState playerColor = gameStatus.currentPlayer.getColor();
    	boolean win = true;
    	int counter = 0;

    	while(counter < 4) {
    		column--;
    		counter++;
    		//System.out.println(column);
    	}
		return win;
	}
  


    private boolean checkLeft(Slot slot){
        int row = slot.getRow();
        int column = slot.getColumn();
        SlotState playerColor = gameStatus.currentPlayer.getColor();
        boolean win = true;

        for(int i = column-1; i > column-4; i--) {
            Slot nextSlot = board.getSlot(row,i);
            if (nextSlot == null) {
                return false;
            }
            SlotState slotColor = nextSlot.getSlotState();
			if (slotColor != playerColor) {
                win = false;
                return false;
            }
        }
        return win;
    }

    private boolean checkRight(Slot slot){
        int row = slot.getRow();
        int column = slot.getColumn();
        SlotState playerColor = gameStatus.currentPlayer.getColor();
        boolean win = true;
        for(int i= column+1 ; i< column+4; i++) {
            Slot nextSlot = board.getSlot(row, i);
            if (nextSlot == null) {
                return false;
            }
            SlotState slotColor = nextSlot.getSlotState();
            if (slotColor != playerColor) {
                win = false;
            }
        }
        return win;
    }

    private boolean checkVertical(Slot slot) {
        SlotState playerColor = gameStatus.currentPlayer.getColor();
        int row = slot.getRow();
        if (row > 2) {
            return false;
        }
        int column = slot.getColumn();
        boolean win = true;
        for(int i= row+1; i< row+4; i++){
            SlotState slotColor = board.getSlot(i,column).getSlotState();

            if (slotColor != playerColor) {
                win = false;
            }
        }
        return win;
    }

    private boolean checkDiagonal (Slot slot) {
        int row = slot.getRow();
        int column = slot.getColumn();
        if (row >= 3) {
            if (column == 3) {
                boolean win = checkUpLeft(slot);
                if (win) return win;
                else return checkUpRight(slot);
            } else if(column < 3) {
                return checkUpRight(slot);
            } else {
                return checkUpLeft(slot);
            }
        } else {
            if (column == 3) {
                boolean win = checkDownLeft(slot);
                if (win) return win;
                else return checkDownRight(slot);
            } else if(column < 3) {
                return checkDownRight(slot);
            } else {
                return checkDownLeft(slot);
            }
        }
    }

 
    private boolean checkDownLeft(Slot slot) {
        int row = slot.getRow();
        int column = slot.getColumn();
        SlotState playerColor = gameStatus.currentPlayer.getColor();
        boolean win = true;
        for(int i = row + 1; i < row+4; i++) {
            column--;
            Slot nextSlot = board.getSlot(i,column);
            if (nextSlot == null) {
                return false;
            }
            SlotState slotColor = nextSlot.getSlotState();
            if (playerColor != slotColor) {
                return false;
            }
        }
        return win;
    }

    private boolean checkDownRight(Slot slot) {
    	int row = slot.getRow();
    	int column = slot.getColumn();
    	SlotState playerColor = gameStatus.currentPlayer.getColor();
		boolean win = true;

    	
    	for(int i = row + 1; i < row + 4; i++ ) {

    		column++;
    		Slot nextSlot = board.getSlot(i, column);
            if (nextSlot == null) {
                return false;
            }
            SlotState slotColor = nextSlot.getSlotState();
            
            if (playerColor != slotColor) {
                return false;
            }
        }
      
        return win;
    		
    	}

    private boolean checkUpLeft(Slot slot) {
        int row = slot.getRow();
        int column = slot.getColumn();
        SlotState playerColor = gameStatus.currentPlayer.getColor();
        boolean win = true;
        for(int i = row - 1; i > row-4; i--) {
            column--;
            Slot nextSlot = board.getSlot(i,column);
            if (nextSlot == null) {
                return false;
            }
            SlotState slotColor = nextSlot.getSlotState();
            if (playerColor != slotColor) {
                return false;
            }
        }
        return win;
    }

    private boolean checkUpRight(Slot slot) {
        int row = slot.getRow();
        int column = slot.getColumn();
        SlotState playerColor = gameStatus.currentPlayer.getColor();
        boolean win = true;
        for(int i = row - 1; i > row-4; i--) {
            column++;
            Slot nextSlot = board.getSlot(i,column);
            if (nextSlot == null) {
                return false;
            }
            SlotState slotColor = nextSlot.getSlotState();
            if (playerColor != slotColor) {
                return false;
            }
        }
        return win;
    }

    public Slot discDrop(int column) {
        Slot nextSlot = new Slot(currentPlayer.getColor());
        for(int i=5; i >= 0 ; i--) {
            Slot temp = board.getSlot(i,column);
            if (temp == null){
            	nextSlot.setColumn(column);
                nextSlot.setRow(i);
                return nextSlot;
            }
        }
        return null;
    }

    private boolean validateMove(int column){
        return (validateColumn(column) && columnNotFull(column) /*&& validateRow(row)*/);
    }

    private boolean validateColumn(int column){
        return (column >= 0 && column <= 6) ;
    }

    private boolean validateRow(int row){
        return (row >= 0 && row < 5) ;
    }
    
    private boolean columnNotFull(int column) {
        return (board.getSlot(0,column) == null);
    }

    public Board getBoard() {
        return board;
    }
}
