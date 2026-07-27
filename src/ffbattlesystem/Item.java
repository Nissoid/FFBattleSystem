package ffbattlesystem;

/**
 * Represents a consumable item in a character's inventory.
 * Items can restore Health Points (HP), Magic Points (MP), and cure status effects.
 */
public class Item {
    private String name;
    private int restoreAmount; // Amount of HP or MP restored upon use
    private int quantity;      // Current stock of this item
    private boolean restoresHp;// True if it restores HP, false if it restores MP

    public Item(String name, int restoreAmount, int quantity, boolean restoresHp) {
        this.name = name;
        this.restoreAmount = restoreAmount;
        this.quantity = quantity;
        this.restoresHp = restoresHp;
    }

    /**
     * Consumes one instance of the item.
     */
    public void decreaseQuantity() { 
        this.quantity--; 
    }

    // --- GETTERS ---
    public String getName() { return name; }
    public int getRestoreAmount() { return restoreAmount; }
    public int getQuantity() { return quantity; }
    public boolean isRestoresHp() { return restoresHp; }
}