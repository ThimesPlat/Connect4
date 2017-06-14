package GameLogic ;
/**
 * Created by eps on 2017-06-12.
 */
public class Slot {
    private SlotState slotState;
    private int row;
    private int column;

    public Slot(SlotState slotState) {
        this.slotState = slotState;
    }

    public SlotState getSlotState() {
        return slotState;
    }

    public void setSlotState(SlotState slotState) {
        this.slotState = slotState;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getColumn() {
        return column;
    }

    public void setColumn(int column) {
        this.column = column;
    }
}
