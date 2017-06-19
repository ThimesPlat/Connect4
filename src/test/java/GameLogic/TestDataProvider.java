package GameLogic;

import static org.mockito.Mockito.mock;

import org.mockito.Mock;

/**
 * Created by eps on 2017-06-15.
 */
public abstract class TestDataProvider
{


    @Mock
    protected Slot mockRedSlot = mock(Slot.class);
    @Mock
    protected Slot mockYellowSlot = mock(Slot.class);
    @Mock
    protected  Board mockBoard = mock(Board.class);
    @Mock
    protected static Slot mockEmptySlot = mock(Slot.class);
    protected Game realTestGame = new Game();
    protected Board realTestBoard = new Board();
    protected Slot realRedSlot = new Slot(SlotState.RED);
    protected Slot realYellowSlot = new Slot(SlotState.YELLOW);
    protected Board testBoardForValidateWithMockSlots = new Board();
    protected Game validateMoveGame = new Game();


    protected  final Slot[][] testingBoard = {
        {mockEmptySlot,mockRedSlot,mockEmptySlot,mockEmptySlot, mockEmptySlot, mockEmptySlot, mockEmptySlot},
        {mockEmptySlot,mockRedSlot,mockEmptySlot,mockEmptySlot, mockEmptySlot, mockEmptySlot, mockEmptySlot},
        {mockEmptySlot,mockYellowSlot,mockEmptySlot,mockEmptySlot, mockEmptySlot, mockEmptySlot, mockEmptySlot},
        {mockEmptySlot,mockYellowSlot,mockEmptySlot,mockEmptySlot, mockEmptySlot, mockEmptySlot, mockEmptySlot},
        {mockYellowSlot,mockYellowSlot,mockEmptySlot,mockEmptySlot, mockEmptySlot, mockEmptySlot, mockEmptySlot},
        {mockRedSlot,mockYellowSlot,mockRedSlot,mockRedSlot, mockRedSlot, mockRedSlot, mockEmptySlot}
    } ;

}
