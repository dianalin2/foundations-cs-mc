public class Inventory {

    public final int size;
    private Item[] items;

    public Inventory(int size) {
        this.size = size;
        this.items = new Item[size];
    }

    public void setSlot(int slot, Item item) {
        if (slot < 0 || slot >= this.size) {
            return;
        }

        this.items[slot] = item;
    }

    public Item getSlot(int slot) {
        if (slot < 0 || slot >= this.size) {
            return null;
        }

        return this.items[slot];
    }

    public void removeItemFromInventory(int index) {
        items[index] = null;
    }

    public int getEmptySlot() {
        for (int i = 0; i < items.length; i++) {
            if (items[i] == null) {
                return i;
            }
        }
        return -1;
    }

    public boolean isEmpty(int slot) {
        return items[slot] == null;
    }

    public void swapSlots(int slot1, int slot2) {
        Item temp = items[slot1];
        items[slot1] = items[slot2];
        items[slot2] = temp;
    }
}
