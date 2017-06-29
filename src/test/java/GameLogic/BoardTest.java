package GameLogic;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

/**
 * Created by eps on 2017-06-15.
 */
@RunWith (MockitoJUnitRunner.class)
public class BoardTest extends TestDataProvider
{



    @Before
    public void setUp()
    {
        when(mockYellowSlot.getSlotState()).thenReturn(SlotState.YELLOW);
        when(mockRedSlot.getSlotState()).thenReturn(SlotState.RED);
        when(mockBoard.getBoard()).thenReturn(testingBoardHorizontalWin);
        when(mockBoard.getSlot(5, 0)).thenReturn(mockRedSlot);
        when(mockBoard.getSlot(5, 1)).thenReturn(mockYellowSlot);
        when(mockEmptySlot.getSlotState()).thenReturn(SlotState.EMPTY);
    }

    @Test
    public void testConstructor(){
        Board board = new Board();
        assertNotNull(board);
        for (int row= 0; row < 6; row ++) {
            for (int col= 0; col < 7; col++){
                Slot slot = board.getSlot(row,col);
                assertEquals(SlotState.EMPTY,slot.getSlotState());
            }
        }
}

    @Test
    public void getBoard()
    {
        assertNotNull(mockBoard.getBoard());
    }

    @Test
    public void setBoard()
    {

    }
    @Test
    public void verticalWin(){
        Game game = new Game();
        Board board = new Board();
        board.setBoard(testingBoardHorizontalTwo);
        game.setBoard(board);
        Slot newSlot = new Slot(SlotState.YELLOW);
        newSlot.setRow(2);
        newSlot.setColumn(4);
        assertEquals(game.checkVertical(newSlot),false);

    }

    @Test
    public void getSlot()
    {
        Slot redSlot = mockBoard.getSlot(5, 0);
        Slot yellowSlot = mockBoard.getSlot(5, 1);
        assertNotNull(redSlot);
        assertEquals(SlotState.RED, redSlot.getSlotState());
        assertNotNull(yellowSlot);
        assertEquals(SlotState.YELLOW, yellowSlot.getSlotState());
    }

    @Test
    public void setSlot()
    {
    }

    @Test
    public void setSlotState()
    {
        Board board = new Board();
        board.setSlot(new Slot(SlotState.YELLOW),5,0);
        assertEquals(SlotState.YELLOW,board.getSlot(5,0).getSlotState());
        board.setSlotState(SlotState.RED,5,0);
        assertEquals(SlotState.RED,board.getSlot(5,0).getSlotState());
    }

}