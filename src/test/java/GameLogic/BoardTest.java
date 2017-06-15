package GameLogic;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static junit.framework.TestCase.assertNotNull;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by eps on 2017-06-15.
 */
@RunWith(MockitoJUnitRunner.class)
public class BoardTest extends BoardTestDataProvider {

    @Mock
    private Board mockBoard = mock(Board.class);
    @Mock
    private Slot mockRedSlot = mock(Slot.class);
    @Mock
    private Slot mockYellowSlot = mock(Slot.class);

    @Before
    public void setUp() {
        when(mockYellowSlot.getSlotState()).thenReturn(SlotState.YELLOW);
        when(mockRedSlot.getSlotState()).thenReturn(SlotState.RED);
        when(mockBoard.getBoard()).thenReturn(testingBoard);
    }

    @Test
    public void getBoard() {
        assertNotNull(mockBoard.getBoard());
    }

    @Test
    public void setBoard() {

    }

    @Test
    public void getSlot() {
        Board testGetSlotBoard = new Board();
        testGetSlotBoard.setBoard(testingBoard);
        Slot redSlot = testGetSlotBoard.getSlot(5, 0);
        Slot yellowSlot = testGetSlotBoard.getSlot(5, 1);
        assertNotNull(redSlot);
        assertNotNull(yellowSlot);
    }

    @Test
    public void setSlot() {
    }

    @Test
    public void setSlotState() {
        Board testSetSlotStateBoard = new Board();
        testSetSlotStateBoard.setBoard(mockBoard.getBoard());
        Slot redSlot = testSetSlotStateBoard.getSlot(5, 0);
        assertNotNull(redSlot);
        assertEquals(SlotState.RED, redSlot.getSlotState());
        testSetSlotStateBoard.setSlotState(SlotState.YELLOW, redSlot.getRow(), redSlot.getColumn());
        assertEquals(SlotState.YELLOW, testSetSlotStateBoard.getSlot(redSlot.getRow(), redSlot.getColumn()));
    }

}