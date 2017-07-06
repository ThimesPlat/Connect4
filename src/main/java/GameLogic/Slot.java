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

    public Slot(SlotState slotState, int row, int column) {
        this.slotState = slotState;
        this.row = row;
        this.column = column;
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

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;

        Slot slot = (Slot) o;

        if (row != slot.row)
            return false;
        if (column != slot.column)
            return false;
        return slotState == slot.slotState;
    }
}
