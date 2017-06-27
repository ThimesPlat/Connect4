package GameLogic ;

import java.util.*;

import PlayerLogic.MiniMax;
import PlayerLogic.Player;
import javafx.application.Platform;

/**
 * Created by eps on 2017-06-13.
 */

public class Game extends Observable implements Observer {
    GameStatus gameStatus;

    Board board;
    PlayerLogic.Player p1;
    PlayerLogic.Player p2;
    PlayerLogic.Player currentPlayer;
    Timer timer;
    int rounds = 0;

    MiniMax miniMax;
    int miniMaxDepth = 3;

    public void setBoard(Board board) {
        this.board = board;
        this.gameStatus.setBoard(board);
    }

    public void setGameStatus(GameStatus gameStatus){
        this.gameStatus = gameStatus;
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
       /* timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
    		@Override
    		public void run() {
    			Platform.runLater(() -> newMove());
    		}
    	}, 10, 100);
        */
       // addObserver(this);

        newMove();
    }


    public void setMiniMaxDepth(int miniMaxDepth){
        this.miniMaxDepth = miniMaxDepth;
    }

    public void newMove() {

/*
        Random random = new Random();
        int column = random.nextInt(7);
*/

        miniMax = new MiniMax(this);
        miniMax.setDepth(miniMaxDepth);
        int column = miniMax.calcValue(currentPlayer);

    	Slot slot;
        if(validateMove(column)) {
            slot = discDrop(column);
            System.out.println();
            System.out.println();

        } else return;


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
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        /*
        setChanged();
        notifyObservers();
        */
        System.out.println("NEW MOVE");
        newMove();

    }

    public GameStatus getGameStatus() {
        return gameStatus;
    }
    private void setCurrentPlayer() {
        gameStatus.setCurrentPlayer(updateCurrentPlayer());
    	this.currentPlayer = gameStatus.getCurrentPlayer();
    }
    private Player updateCurrentPlayer(){
        if (gameStatus.getCurrentPlayer().getColor() == SlotState.RED) {
            return p2;
        }
        else if (gameStatus.getCurrentPlayer().getColor() == SlotState.YELLOW) {
                return p1;
            } else {
                System.err.println("updateNextPlayer error : gameStatus next player is something inappropriate");
            }
        return null;
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

    private boolean checkVertical(Slot slot) {
        ArrayList<Slot> winningSequence = new ArrayList<>();
        winningSequence.add(slot);
        SlotState playerColor = gameStatus.getCurrentPlayer().getColor();
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

    private void printMatrix(Board board){
        for (int i = 0;i<board.getBoard().length;i++){
            for (int p = 0;p<board.getBoard()[0].length;p++){
                System.out.print(board.getBoard()[i][p].getSlotState() + " ");
            }
            System.out.println();
        }
    }

    @Override
    public void update(Observable o, Object arg) {
        newMove();
    }

    private void gameIsOver(){
        gameStatus.setBoard(board);
        setCurrentPlayer();
    }
}
