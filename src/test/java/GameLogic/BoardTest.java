package GameLogic;


import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by eps on 2017-06-15.
 */
@RunWith (MockitoJUnitRunner.class)
public class BoardTest extends BoardTestDataProvider
{

    @Mock
    private Board mockBoard = mock(Board.class);
    @Mock
    private Slot mockRedSlot = mock(Slot.class);
    @Mock
    private Slot mockYellowSlot = mock(Slot.class);

    @Before
    public void setUp()
    {
        when(mockYellowSlot.getSlotState()).thenReturn(SlotState.YELLOW);
        when(mockRedSlot.getSlotState()).thenReturn(SlotState.RED);
        when(mockBoard.getBoard()).thenReturn(testingBoard);
        when(mockBoard.getSlot(5, 0)).thenReturn(mockRedSlot);
        when(mockBoard.getSlot(5, 1)).thenReturn(mockYellowSlot);
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