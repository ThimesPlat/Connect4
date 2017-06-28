package PlayerLogic;

import GameLogic.*;

/**
 * Created by ndg on 2017-06-19.
 */
public class testMini {
    public static void main(String[] args) {
        Game game = new Game();
        Slot slot = new Slot(SlotState.RED);
        Slot slot2 = new Slot(SlotState.RED);
        Slot slot3 = new Slot(SlotState.RED);

        game.getGameStatus().getBoard().setSlot(slot, 5, 0);
        game.getGameStatus().getBoard().setSlot(slot2, 5, 1);
        //game.getGameStatus().getBoard().setSlot(slot3, 5, 4);
        MiniMax test = new MiniMax(game);
      //  int r = test.eval(game.getGameStatus().getBoard(), new Player(SlotState.RED));
      //  System.out.print(r);
    }
}
