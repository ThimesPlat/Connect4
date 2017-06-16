package GameLogic ;

import PlayerLogic.Player;
import javafx.application.Platform;

import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

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
    	}, 100, 5);
    }
    
    public void newMove() {
        Random random = new Random();
    	int column = random.nextInt(7);
    	Slot slot;
        rounds++;

        if(validateMove(column)) {
            slot = discDrop(column);
    		board.setSlot(slot,slot.getRow(),slot.getColumn());
        } else {
        	return;
        }

    	if (checkWin(slot)) {
            gameStatus.setGameOver(true);
			gameStatus.setWinner(currentPlayer);
			timer.cancel();
		}
        if (checkBoardFull()) {
			gameStatus.setGameOver(true);
			timer.cancel();

		}
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
        return checkHorizontal(slot) || checkVertical(slot) || checkDiagonal(slot);

    }
    
    private boolean checkBoardFull() {
    	Slot[][] slots = board.getBoard();
    	for(int i = 0; i < slots[0].length; i++) {
			if(board.getSlot(0,i).getSlotState() == SlotState.EMPTY) {
				return false;
			}
    	}
    	return true;
    }

    private boolean checkHorizontal(Slot slot) {
        ArrayList<Slot> winningSequence = new ArrayList<>();
        winningSequence.add(slot);
        int column = slot.getColumn();
    	int row = slot.getRow();
    	boolean checkLeft = true;
    	boolean checkRight = true;
    	
		Slot currentSlot;
    	SlotState playerColor = gameStatus.getCurrentPlayer().getColor();
  
    	int counter = 0;
    	while(checkRight) {
    		column++;
            if(!checkMatrixBoundaries(row,column)) break;
    		currentSlot = board.getSlot(row, column);
			if(playerColor != currentSlot.getSlotState()) {
			    checkRight=false;
			} else {
				counter++;
                winningSequence.add(currentSlot);

			}

    	} 
    	column = slot.getColumn();
    	while(checkLeft) {
    		column--;
            if(!checkMatrixBoundaries(row,column)) break;
            currentSlot = board.getSlot(row, column);
            if(playerColor != currentSlot.getSlotState()) {
                checkLeft=false;
			} else {
				counter++;
                winningSequence.add(currentSlot);
			}
    	}
    	if (counter >= 3)
    	    gameStatus.getBoard().setWinningSequence(winningSequence);
		return (counter >= 3);
	}
  

/*
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
    }*/

    private boolean checkVertical(Slot slot) {
        ArrayList<Slot> winningSequence = new ArrayList<>();
        winningSequence.add(slot);
        SlotState playerColor = gameStatus.currentPlayer.getColor();
        int row = slot.getRow();
        if (row > 2) {
            return false;
        }
        int column = slot.getColumn();
        boolean win = true;
        boolean keepGoingVertical = true;
        int counter=0;
        int currentRow = slot.getRow()+1;
        //for(int i= row+1; i< row+4; i++){
        while(keepGoingVertical){
            if(!checkMatrixBoundaries(currentRow,column)) break;
            Slot currentSlot = board.getSlot(currentRow,column);
            SlotState slotColor = currentSlot.getSlotState();
            if (slotColor != playerColor) {
                break;
            }
            counter++;
            currentRow++;
            winningSequence.add(currentSlot);

        }
        System.out.println(counter);
        if(counter>=3) {
            gameStatus.getBoard().setWinningSequence(winningSequence);
            return true;
        }
        return false;
    }

    private boolean checkDiagonal(Slot slot,Boolean upLeftAndDownRight){
     //   Slot[] winningSequence = new Slot[4];
        ArrayList<Slot> winningSequence = new ArrayList<>();
        int counter = 0;
        winningSequence.add(slot);
        Boolean keepLookingLeft = true;
        Boolean keepLookingRight = true;
        int tempRow = slot.getRow();
        int tempCol = slot.getColumn();

        while(keepLookingLeft){
            tempCol--;
            if (upLeftAndDownRight) tempRow--;
            else tempRow++;
            if(!checkMatrixBoundaries(tempRow,tempCol)) break;
            if (board.getSlot(tempRow,tempCol).getSlotState()==currentPlayer.getColor()){
                counter++;
                winningSequence.add(board.getSlot(tempRow,tempCol));
            }
            else keepLookingLeft=false;
        }

        tempRow=slot.getRow();
        tempCol=slot.getColumn();
        while(keepLookingRight){
            tempCol++;
            if (upLeftAndDownRight) tempRow++;
            else tempRow--;
            if(!checkMatrixBoundaries(tempRow,tempCol)) break;
            if (board.getSlot(tempRow,tempCol).getSlotState()==currentPlayer.getColor()){
                counter++;
                winningSequence.add(board.getSlot(tempRow,tempCol));
            }
            else keepLookingRight=false;
        }
        if(counter>=3) {
            gameStatus.getBoard().setWinningSequence(winningSequence);
            return true;    // all colors in the same line - the slot you are in.
        }
        return false;
    }

    private boolean checkMatrixBoundaries(int row, int column){
        return (column <=board.getBoard()[0].length-1 && column>=0 && row<=board.getBoard().length-1 && row>=0);
    }

    private boolean checkDiagonal (Slot slot) {
        return checkDiagonal(slot, true) || checkDiagonal(slot, false);
    }
    public Slot discDrop(int column) {
        Slot nextSlot = new Slot(currentPlayer.getColor());
        for(int i=5; i >= 0 ; i--) {
            Slot temp = board.getSlot(i,column);
            if (temp.getSlotState() == SlotState.EMPTY){
            	nextSlot.setColumn(column);
                nextSlot.setRow(i);
                return nextSlot;
            }
        }
        return null;
    }

    private boolean validateMove(int column){
        return (validateColumn(column) && columnNotFull(column));
    }

    private boolean validateColumn(int column){
        return (column >= 0 && column <= 6) ;
    }

    private boolean validateRow(int row){
        return (row >= 0 && row < 5) ;
    }
    
    private boolean columnNotFull(int column) {
        return (board.getSlot(0,column).getSlotState() == SlotState.EMPTY);
    }


}
