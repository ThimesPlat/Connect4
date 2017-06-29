package PlayerLogic;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;

import GameLogic.*;

public class MiniMaxTest {

	Game game;
	MiniMax miniMax;

    protected SlotState yellowSlot = SlotState.YELLOW;
    protected SlotState redSlot = SlotState.RED;
    protected  SlotState emptySlot = SlotState.EMPTY;

    SlotState player;
    SlotState[][] board;
    
    protected  final SlotState[][] testingBoardHorizontalThree = {
            {emptySlot, emptySlot, emptySlot, emptySlot, emptySlot, emptySlot, emptySlot},
            {emptySlot, emptySlot, emptySlot, emptySlot, emptySlot, emptySlot, emptySlot},
            {emptySlot, emptySlot, emptySlot, emptySlot, emptySlot, emptySlot, emptySlot},
            {emptySlot, emptySlot, emptySlot, emptySlot, emptySlot, emptySlot, emptySlot},
            {emptySlot, emptySlot, emptySlot, emptySlot, emptySlot, emptySlot, emptySlot},
            {redSlot, redSlot, redSlot, emptySlot, emptySlot, emptySlot, emptySlot}
        } ;
    protected  final SlotState[][] testingBoardVerticalTwo = {
            {emptySlot, emptySlot, emptySlot, emptySlot, emptySlot, emptySlot, emptySlot},
            {emptySlot, emptySlot, emptySlot, emptySlot, emptySlot, emptySlot, emptySlot},
            {emptySlot, emptySlot, emptySlot, emptySlot, emptySlot, emptySlot, emptySlot},
            {emptySlot, emptySlot, emptySlot, emptySlot, emptySlot, emptySlot, emptySlot},
            {redSlot, emptySlot, emptySlot, emptySlot, emptySlot, emptySlot, emptySlot},
            {redSlot, emptySlot, emptySlot, emptySlot, emptySlot, emptySlot, emptySlot}
        } ;
    
    protected  final SlotState[][] testingBoardDiagonalThree = {
            {emptySlot, emptySlot, emptySlot, emptySlot, emptySlot, emptySlot, emptySlot},
            {emptySlot, emptySlot, emptySlot, emptySlot, emptySlot, emptySlot, emptySlot},
            {emptySlot, emptySlot, emptySlot, emptySlot, emptySlot, emptySlot, emptySlot},
            {emptySlot, emptySlot, redSlot, emptySlot, emptySlot, emptySlot, emptySlot},
            {emptySlot, redSlot, emptySlot, emptySlot, emptySlot, emptySlot, emptySlot},
            {redSlot, emptySlot, emptySlot, emptySlot, emptySlot, emptySlot, emptySlot}
        } ;
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		game = new Game();
		board = new SlotState[6][7];
		miniMax = new MiniMax(game);
		

	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testVerticalTwo() {
	    board = testingBoardVerticalTwo;
	    player = SlotState.RED;
		assertEquals(miniMax.checkVertical2inRow(board, player), 10);

		
	}
	@Test
	public void testHorizontalThree() {
		board = testingBoardHorizontalThree;
        player = SlotState.RED;

		assertEquals(miniMax.checkHorizontal3inRow(board, player), 3000);
		
	}
	
	/*
	@Test
	public void testDiagonal() {
		board = testingBoardDiagonalThree;
		assertEquals(miniMax.eval(board, player), 2020);
		
	}*/

}
