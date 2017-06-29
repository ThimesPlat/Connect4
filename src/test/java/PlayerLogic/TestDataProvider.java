package PlayerLogic;

import GameLogic.Game;
import GameLogic.SlotState;

/**
 * Created by ktu on 2017-06-29.
 */
public class TestDataProvider {
    Game game;
    MiniMax miniMax;

    protected SlotState yellowSlot = SlotState.YELLOW;
    protected SlotState redSlot = SlotState.RED;
    protected  SlotState emptySlot = SlotState.EMPTY;

    SlotState player;
    SlotState[][] board;

    // Horizontal
    protected final SlotState[][] testingBoardHorizontalThree = {
            {emptySlot, emptySlot, emptySlot, emptySlot, emptySlot, emptySlot, emptySlot},
            {emptySlot, emptySlot, emptySlot, emptySlot, emptySlot, emptySlot, emptySlot},
            {redSlot, emptySlot, redSlot, redSlot, redSlot, emptySlot, emptySlot},
            {emptySlot, emptySlot, emptySlot, emptySlot, emptySlot, emptySlot, emptySlot},
            {emptySlot, emptySlot, emptySlot, emptySlot, emptySlot, emptySlot, emptySlot},
            {redSlot, redSlot, redSlot, redSlot, emptySlot, redSlot, emptySlot}
    };

    protected final SlotState[][] testingBoardHorizontalTwo = {
            {emptySlot, emptySlot, emptySlot, emptySlot, emptySlot, emptySlot, emptySlot},
            {emptySlot, emptySlot, emptySlot, emptySlot, emptySlot, emptySlot, emptySlot},
            {emptySlot, emptySlot, emptySlot, emptySlot, emptySlot, emptySlot, emptySlot},
            {emptySlot, emptySlot, emptySlot, emptySlot, emptySlot, emptySlot, emptySlot},
            {emptySlot, emptySlot, emptySlot, emptySlot, emptySlot, emptySlot, emptySlot},
            {emptySlot, emptySlot, emptySlot, emptySlot, redSlot, emptySlot, redSlot}
    };

    protected final SlotState[][] testingHorizontalThreeOpenEnded = {
            {emptySlot, emptySlot, emptySlot, emptySlot, emptySlot, emptySlot, emptySlot},
            {emptySlot, emptySlot, emptySlot, emptySlot, emptySlot, emptySlot, emptySlot},
            {emptySlot, emptySlot, redSlot, redSlot, redSlot, emptySlot, emptySlot},
            {emptySlot, emptySlot, emptySlot, emptySlot, emptySlot, emptySlot, emptySlot},
            {emptySlot, emptySlot, emptySlot, emptySlot, emptySlot, emptySlot, emptySlot},
            {emptySlot, emptySlot, emptySlot, emptySlot, emptySlot, emptySlot, redSlot}
    };

    // Vertical
    protected final SlotState[][] testingBoardVerticalTwo = {
            {emptySlot, emptySlot, emptySlot, emptySlot, emptySlot, emptySlot, emptySlot},
            {emptySlot, emptySlot, emptySlot, emptySlot, emptySlot, emptySlot, emptySlot},
            {emptySlot, emptySlot, emptySlot, emptySlot, emptySlot, emptySlot, emptySlot},
            {emptySlot, emptySlot, emptySlot, emptySlot, emptySlot, emptySlot, emptySlot},
            {redSlot, emptySlot, emptySlot, emptySlot, emptySlot, emptySlot, emptySlot},
            {redSlot, emptySlot, emptySlot, emptySlot, emptySlot, emptySlot, emptySlot}
    };

    protected final SlotState[][] testBoardVerticalThree = {
            {emptySlot, emptySlot, emptySlot, emptySlot, emptySlot, emptySlot, emptySlot},
            {emptySlot, emptySlot, emptySlot, emptySlot, emptySlot, emptySlot, emptySlot},
            {emptySlot, emptySlot, emptySlot, emptySlot, emptySlot, emptySlot, emptySlot},
            {redSlot, emptySlot, emptySlot, emptySlot, emptySlot, emptySlot, emptySlot},
            {redSlot, emptySlot, emptySlot, emptySlot, emptySlot, emptySlot, emptySlot},
            {redSlot, emptySlot, emptySlot, emptySlot, emptySlot, emptySlot, emptySlot}
    };

    // Diagonal
    protected final SlotState[][] testingBoardDiagonalThree = {
            {emptySlot, emptySlot, emptySlot, emptySlot, emptySlot, emptySlot, redSlot},
            {emptySlot, emptySlot, emptySlot, emptySlot, emptySlot, emptySlot, emptySlot},
            {emptySlot, emptySlot, emptySlot, emptySlot, redSlot, emptySlot, emptySlot},
            {emptySlot, emptySlot, emptySlot, redSlot, emptySlot, emptySlot, emptySlot},
            {emptySlot, emptySlot, emptySlot, emptySlot, emptySlot, emptySlot, emptySlot},
            {emptySlot, emptySlot, emptySlot, emptySlot, emptySlot, emptySlot, emptySlot}
    };
}
