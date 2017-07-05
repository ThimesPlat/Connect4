package GameLogic ;

import java.util.ArrayList;
import java.util.Observable;

import PlayerLogic.MiniMax;
import PlayerLogic.Player;

/**
 * Created by eps on 2017-06-13.
 */

public class Game extends Observable{
    GameStatus gameStatus;
    Board board;
    PlayerLogic.Player p1;
    PlayerLogic.Player p2;
    PlayerLogic.Player currentPlayer;

    MiniMax miniMax;
    int miniMaxDepth = 3;

    public void setBoard(SlotState[][] board) {

        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                Slot slot = new Slot(board[i][j], i,j);
                this.board.setSlot(slot,i,j);
            }
        }
        this.gameStatus.setBoard(this.board);
    }


    public Game(){
        p1 = new Player(SlotState.RED);
        p2 = new Player(SlotState.YELLOW);
        currentPlayer = p1;
        board = new Board();
        gameStatus = new GameStatus(board, p1);
        gameStatus.setBoard(board);
    }
    
    public void startGame() {
        while(!gameStatus.isGameOver()) {
            newMove();
            delay(750);


        }
    }

    private void delay(int delay){
        try {
            Thread.sleep(delay);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    public void setMiniMaxDepth(int miniMaxDepth){
        this.miniMaxDepth = miniMaxDepth;
    }

    public void newMove() {
        SlotState[][] slotStateBoard = generateSlotStateMatrix(this.board);
        miniMax = new MiniMax(slotStateBoard);
        miniMax.setDepth(miniMaxDepth);
        int column = miniMax.calcValue(currentPlayer);
    	Slot slot;
        if(validateMove(column)) {
            slot = discDrop(column);
        }
        else {
            return;
        }
    	if (checkWin(slot)) {
            gameStatus.setGameOver(true);
			gameStatus.setWinner(currentPlayer);
            gameIsOver();
            return;
		}
        if (checkBoardFull()) {
			gameStatus.setGameOver(true);
            gameIsOver();
            return;
        }
        gameStatus.setBoard(board);
        setCurrentPlayer();
    }

    public GameStatus getGameStatus() {
        return gameStatus;
    }
    private void setCurrentPlayer() {
        gameStatus.setCurrentPlayer(updateCurrentPlayer());
    	this.currentPlayer = gameStatus.getCurrentPlayer();
    }
    private Player updateCurrentPlayer(){
        return (currentPlayer.getColor()==SlotState.RED)? p2:p1;
    }


    public boolean checkWin(Slot slot){
        return checkHorizontal(slot) || checkVertical(slot) || checkDiagonal(slot);

    }
    
    public boolean checkBoardFull() {
    	Slot[][] slots = board.getBoard();
    	for(int i = 0; i < slots[0].length; i++) {
			if(board.getSlot(0,i).getSlotState().equals(SlotState.EMPTY)) {
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
    	SlotState playerColor = slot.getSlotState();
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

    public boolean checkVertical(Slot slot) {
        ArrayList<Slot> winningSequence = new ArrayList<>();
        winningSequence.add(slot);
        SlotState playerColor = slot.getSlotState();
        int row = slot.getRow();
        if (row > 2) {
            return false;
        }
        int column = slot.getColumn();
        boolean keepGoingVertical = true;
        int counter=0;
        int currentRow = slot.getRow()+1;
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
        if(counter>=3) {
            gameStatus.getBoard().setWinningSequence(winningSequence);
            return true;
        }
        return false;
    }

    private boolean checkDiagonal(Slot slot,Boolean upLeftAndDownRight){
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
            if (board.getSlot(tempRow,tempCol).getSlotState()==slot.getSlotState()){
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
            if (board.getSlot(tempRow,tempCol).getSlotState()==gameStatus.getCurrentPlayer().getColor()){
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
                gameStatus.setChangedSlot(nextSlot);
                this.board.setSlot(nextSlot,nextSlot.getRow(),nextSlot.getColumn());
                return nextSlot;
            }
        }
        return null;
    }

    public Slot discDrop(int column, Player player) {
        Slot nextSlot = new Slot(player.getColor());
        for(int i=5; i >= 0 ; i--) {
            Slot temp = board.getSlot(i,column);
            if (temp.getSlotState() == SlotState.EMPTY){
                nextSlot.setColumn(column);
                nextSlot.setRow(i);
                board.setSlot(nextSlot,nextSlot.getRow(),nextSlot.getColumn());
                gameStatus.getBoard().setSlot(nextSlot,nextSlot.getRow(),nextSlot.getColumn());
                return nextSlot;
            }
        }
        return null;
    }
    public boolean validateMove(int column){
        return (validateColumn(column) && columnNotFull(column));
    }

    private boolean validateColumn(int column){
        return (column >= 0 && column <= 6) ;
    }
    
    private boolean columnNotFull(int column) {
        return (board.getSlot(0,column).getSlotState() == SlotState.EMPTY);
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

    private void gameIsOver(){
        gameStatus.setBoard(board);
        setCurrentPlayer();
    }
}
