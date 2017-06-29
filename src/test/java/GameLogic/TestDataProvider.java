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
    protected Slot realEmptySlot = new Slot(SlotState.EMPTY);

    protected Slot realYellowSlot = new Slot(SlotState.YELLOW);
    protected Board testBoardForValidateWithMockSlots = new Board();
    protected Game validateMoveGame = new Game();


    protected  final Slot[][] testingBoardHorizontalWin = {
        {mockEmptySlot,realRedSlot,mockEmptySlot,mockEmptySlot, mockEmptySlot, mockEmptySlot, mockEmptySlot},
        {mockEmptySlot,realRedSlot,mockEmptySlot,mockEmptySlot, mockEmptySlot, mockEmptySlot, mockEmptySlot},
        {mockEmptySlot,realYellowSlot,mockEmptySlot,mockEmptySlot, mockEmptySlot, mockEmptySlot, mockEmptySlot},
        {mockEmptySlot,realYellowSlot,mockEmptySlot,mockEmptySlot, mockEmptySlot, mockEmptySlot, mockEmptySlot},
        {realYellowSlot,realYellowSlot,mockEmptySlot,mockEmptySlot, mockEmptySlot, mockEmptySlot, mockEmptySlot},
        {realRedSlot,realYellowSlot,realRedSlot,realRedSlot, realRedSlot, realRedSlot, mockEmptySlot}
    } ;

    protected  final Slot[][] testingBoardDiagonalWin = {
            {mockEmptySlot,mockRedSlot,mockEmptySlot,mockEmptySlot, mockEmptySlot, mockEmptySlot, mockEmptySlot},
            {realYellowSlot,mockRedSlot,mockEmptySlot,mockEmptySlot, mockEmptySlot, mockEmptySlot, mockEmptySlot},
            {mockEmptySlot,realYellowSlot,mockEmptySlot,mockEmptySlot, mockEmptySlot, mockEmptySlot, realRedSlot},
            {mockEmptySlot,mockEmptySlot,realYellowSlot,mockEmptySlot, mockEmptySlot, realRedSlot, mockEmptySlot},
            {realYellowSlot,mockEmptySlot,mockEmptySlot,realYellowSlot, realRedSlot, mockEmptySlot, mockEmptySlot},
            {mockRedSlot,realYellowSlot,mockEmptySlot,realRedSlot, mockEmptySlot, mockEmptySlot, mockEmptySlot}
    };

    protected final Slot[][] testingBoardHorizontalTwo = {
            {realEmptySlot, realEmptySlot, realEmptySlot, realEmptySlot, realEmptySlot, realEmptySlot, realEmptySlot},
            {realEmptySlot, realEmptySlot, realEmptySlot, realEmptySlot, realEmptySlot, realEmptySlot, realEmptySlot},
            {realEmptySlot, realEmptySlot, realEmptySlot, realEmptySlot, realEmptySlot, realEmptySlot, realEmptySlot},
            {realEmptySlot, realEmptySlot, realEmptySlot, realEmptySlot, realRedSlot, realEmptySlot, realEmptySlot},
            {realEmptySlot, realEmptySlot, realEmptySlot, realEmptySlot, realRedSlot, realEmptySlot, realEmptySlot},
            {realEmptySlot, realEmptySlot, realEmptySlot, realEmptySlot, realRedSlot, realEmptySlot, realEmptySlot}
    };



}
