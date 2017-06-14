package PlayerLogic;
import GameLogic.SlotState;

public class Player {
	
	private SlotState color;

	public Player(GameLogic.SlotState color) {
		this.color = color;
	}

	public SlotState getColor() {
		return color;
	}
}
