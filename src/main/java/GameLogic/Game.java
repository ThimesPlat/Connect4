package GameLogic ;

import PlayerLogic.Player;

/**
 * Created by eps on 2017-06-13.
 */

public class Game {
    GameStatus gameStatus;
    Board board;
    PlayerLogic.Player p1;
    PlayerLogic.Player p2;

    public Game(){
        p1 = new Player(SlotState.RED);
        p2 = new Player(SlotState.YELLOW);
        Board board = new Board();
        gameStatus = new GameStatus(board, p1);
    }

    public GameStatus getGameStatus() {
        return gameStatus;
    }

    private Player updateCurrentPlayer(){
        if (gameStatus.currentPlayer == p1) {
            return p2;
        }
        else if (gameStatus.currentPlayer == p2) {
                return p1;
            } else {
                System.err.println("updateNextPlayer error : gameStatus next player is something inappropriate");
            }
        return null;
    }

    private boolean checkWin(Slot slot){
        return false;
    }

    private boolean checkHorizontal(Slot slot) {  //handle null slots
        int column = slot.getColumn();
        boolean win;
        if (column == 3) {
            win = checkLeft(slot);
            if (win) return true;
            else {
                return checkRight(slot);
            }
        } else if(column < 3) {
            return  checkRight(slot);
        } else {
            return checkLeft(slot);
        }
    }

    private boolean checkVertical(Slot slot) {               //handle null slots
        SlotState playerColor = gameStatus.currentPlayer.getColor();
        int row = slot.getRow();
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

    }

    private boolean checkLeft(Slot slot){                //handle null slots
        int row = slot.getRow();
        int column = slot.getColumn();
        SlotState playerColor = gameStatus.currentPlayer.getColor();
        boolean win = true ;
        for(int i= column-1 ; i> column-4; column--) {
            Slot nextSlot = board.getSlot(row,i);
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

    private boolean checkRight(Slot slot){               //handle null slots
        int row = slot.getRow();
        int column = slot.getColumn();
        SlotState playerColor = gameStatus.currentPlayer.getColor();
        boolean win = true ;
        for(int i= column+1 ; i< column+4; column++) {
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
    	
    	for(int i = row + 1; i > row + 4; i++ ) {
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

    }
}
