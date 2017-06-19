package GameLogic;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import PlayerLogic.Player;

/**
 * Created by eps on 2017-06-14.
 */
@RunWith (MockitoJUnitRunner.class)
public class GameTest extends TestDataProvider
{
    @Before
    public void setUp()
    {
        realTestBoard.setSlot(realRedSlot,5,0);
        realTestBoard.setSlot(realYellowSlot,5,1);
        realTestBoard.setSlot(realYellowSlot,4,1);
        realTestGame.board = realTestBoard;
        testBoardForValidateWithMockSlots.setBoard(testingBoard);
        validateMoveGame.currentPlayer = new Player(SlotState.RED);
        validateMoveGame.board= testBoardForValidateWithMockSlots;
        when(mockYellowSlot.getSlotState()).thenReturn(SlotState.YELLOW);
        when(mockRedSlot.getSlotState()).thenReturn(SlotState.RED);
        when(mockEmptySlot.getSlotState()).thenReturn(SlotState.EMPTY);
        when(mockBoard.getBoard()).thenReturn(testingBoard);
        when(mockBoard.getSlot(5, 0)).thenReturn(mockRedSlot);
        when(mockBoard.getSlot(5, 1)).thenReturn(mockYellowSlot);
        when(mockBoard.getSlot(5, 6)).thenReturn(mockEmptySlot);

    }


    @Test
    public void newMove()
    {
    }

    @Test
    public void checkWin()
    {
        assertTrue(validateMoveGame.checkWin(new Slot(SlotState.RED,5,2)));
        assertTrue(validateMoveGame.checkWin(new Slot(SlotState.RED,5,3)));
        assertTrue(validateMoveGame.checkWin(new Slot(SlotState.RED,5,4)));
        assertTrue(validateMoveGame.checkWin(new Slot(SlotState.RED,5,5)));
    }

    @Test
    public void discDrop()
    {
        Slot testSlot = new Slot(SlotState.RED);
        testSlot.setColumn(0);
        testSlot.setRow(4);
        assertTrue(testSlot.equals(realTestGame.discDrop(0)));
        testSlot.setColumn(1);
        testSlot.setRow(3);
        assertTrue(testSlot.equals(realTestGame.discDrop(1)));
        testSlot.setColumn(2);
        testSlot.setRow(5);
        assertTrue(testSlot.equals(realTestGame.discDrop(2)));;
    }

    @Test
    public void validateMove()
    {
        assertFalse(validateMoveGame.validateMove(1));
        assertFalse(validateMoveGame.validateMove(8));
        assertFalse(validateMoveGame.validateMove(-1));
    }


    @Test
    public void startGame()
    {
    }

    @Test
    public void getGameStatusTest()
    {

    }


    @AfterEach
    public void tearDown()
    {
    }
}