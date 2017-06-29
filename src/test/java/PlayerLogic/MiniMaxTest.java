package PlayerLogic;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import GameLogic.*;

public class MiniMaxTest extends TestDataProvider {



	@Before
	public void setUp() throws Exception {
		game = new Game();
		board = new SlotState[6][7];
		miniMax = new MiniMax(game);
	}


    // Vertical
	@Test
	public void testVerticalTwo() {
	    board = testingBoardVerticalTwo;
	    player = SlotState.RED;
		assertEquals(miniMax.checkVertical2inRow(board, player), 10);
	}
    @Test
    public void testVerticalThree() {
        board = testBoardVerticalThree;
        player = SlotState.RED;
        assertEquals(miniMax.checkVertical3inRow(board, player), 1000);
    }


    // Horizontal
	@Test
	public void testHorizontalTwo() {
		board = testingBoardHorizontalTwo;
		player = SlotState.RED;

		assertEquals(miniMax.checkHorizontal2inRow(board, player), 30);
	}
	@Test
	public void testHorizontalThree() {
		board = testingBoardHorizontalThree;
        player = SlotState.RED;

		assertEquals(miniMax.checkHorizontal3inRow(board, player), 15000);
		
	}
	@Test
    public void testHorizontalOpenEnded() {
	    board = testingHorizontalThreeOpenEnded;
	    player = SlotState.RED;

	    assertEquals(miniMax.checkHorizontal3inRowOpenEnded(board,player), 6000);
    }



}
