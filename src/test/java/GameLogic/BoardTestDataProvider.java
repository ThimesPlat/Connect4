package GameLogic;

import org.mockito.Mock;

import static org.mockito.Mockito.mock;

/**
 * Created by eps on 2017-06-15.
 */
public abstract class BoardTestDataProvider {


    @Mock
    private Slot mockRedSlot = mock(Slot.class);
    @Mock
    private Slot mockYellowSlot = mock(Slot.class);

    protected  final Slot[][] testingBoard = {
        {null,null,null,null, null, null, null},
        {null,null,null,null, null, null, null},
        {null,mockYellowSlot,null,null, null, null, null},
        {null,mockYellowSlot,null,null, null, null, null},
        {mockYellowSlot,mockYellowSlot,null,null, null, null, null},
        {mockRedSlot,mockYellowSlot,mockRedSlot,mockRedSlot, mockRedSlot, mockRedSlot, null}
    } ;

}
