package PlayerLogic;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import GameLogic.*;

public class MiniMaxTest {

	Game game;
	Slot redSlot;
	Slot yellowSlot;
	Slot emptySlot;
	MiniMax miniMax;

    @Mock
    protected Slot mockYellowSlot = mock(Slot.class);
    @Mock
    protected Slot mockRedSlot = mock(Slot.class);
    @Mock
    protected static Slot mockEmptySlot = mock(Slot.class);

    Player player;
    Board board;
    
    protected  final Slot[][] testingBoardHorizontalThree = {
            {mockEmptySlot,mockEmptySlot,mockEmptySlot,mockEmptySlot, mockEmptySlot, mockEmptySlot, mockEmptySlot},
            {mockEmptySlot,mockEmptySlot,mockEmptySlot,mockEmptySlot, mockEmptySlot, mockEmptySlot, mockEmptySlot},
            {mockEmptySlot,mockEmptySlot,mockEmptySlot,mockEmptySlot, mockEmptySlot, mockEmptySlot, mockEmptySlot},
            {mockEmptySlot,mockEmptySlot,mockEmptySlot,mockEmptySlot, mockEmptySlot, mockEmptySlot, mockEmptySlot},
            {mockEmptySlot,mockEmptySlot,mockEmptySlot,mockEmptySlot, mockEmptySlot, mockEmptySlot, mockEmptySlot},
            {mockRedSlot,mockRedSlot,mockRedSlot,mockEmptySlot, mockEmptySlot, mockEmptySlot, mockEmptySlot}
        } ;
    protected  final Slot[][] testingBoardVerticalThree = {
            {mockEmptySlot,mockEmptySlot,mockEmptySlot,mockEmptySlot, mockEmptySlot, mockEmptySlot, mockEmptySlot},
            {mockEmptySlot,mockEmptySlot,mockEmptySlot,mockEmptySlot, mockEmptySlot, mockEmptySlot, mockEmptySlot},
            {mockEmptySlot,mockEmptySlot,mockEmptySlot,mockEmptySlot, mockEmptySlot, mockEmptySlot, mockEmptySlot},
            {mockRedSlot,mockEmptySlot,mockEmptySlot,mockEmptySlot, mockEmptySlot, mockEmptySlot, mockEmptySlot},
            {mockRedSlot,mockEmptySlot,mockEmptySlot,mockEmptySlot, mockEmptySlot, mockEmptySlot, mockEmptySlot},
            {mockRedSlot,mockEmptySlot,mockEmptySlot,mockEmptySlot, mockEmptySlot, mockEmptySlot, mockEmptySlot}
        } ;
    
    protected  final Slot[][] testingBoardDiagonalThree = {
            {mockEmptySlot,mockEmptySlot,mockEmptySlot,mockEmptySlot, mockEmptySlot, mockEmptySlot, mockEmptySlot},
            {mockEmptySlot,mockEmptySlot,mockEmptySlot,mockEmptySlot, mockEmptySlot, mockEmptySlot, mockEmptySlot},
            {mockEmptySlot,mockEmptySlot,mockEmptySlot,mockEmptySlot, mockEmptySlot, mockEmptySlot, mockEmptySlot},
            {mockEmptySlot,mockEmptySlot,mockRedSlot,mockEmptySlot, mockEmptySlot, mockEmptySlot, mockEmptySlot},
            {mockEmptySlot,mockRedSlot,mockEmptySlot,mockEmptySlot, mockEmptySlot, mockEmptySlot, mockEmptySlot},
            {mockRedSlot,mockEmptySlot,mockEmptySlot,mockEmptySlot, mockEmptySlot, mockEmptySlot, mockEmptySlot}
        } ;
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
        when(mockYellowSlot.getSlotState()).thenReturn(SlotState.YELLOW);
        when(mockRedSlot.getSlotState()).thenReturn(SlotState.RED);
        when(mockEmptySlot.getSlotState()).thenReturn(SlotState.EMPTY);
		game = new Game();
		board = new Board();
		miniMax = new MiniMax(game);
		player = new Player(SlotState.RED);
		

	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testVertical() {
		board.setBoard(testingBoardVerticalThree);
		assertEquals(miniMax.eval(board, player), 1010);
		
	}
	@Test
	public void testHorizontal() {
		board.setBoard(testingBoardHorizontalThree);
		assertEquals(miniMax.eval(board, player), 3030);
		
	}
	
	
	@Test
	public void testDiagonal() {
		board.setBoard(testingBoardDiagonalThree);
		assertEquals(miniMax.eval(board, player), 2020);
		
	}
	
}
