/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ffbattlesystem;

/**
 * Represents a consumable object in the character's inventory.
 */
public class Item {

    private String name;
    private int restoreAmount;
    private int quantity;
    private boolean restoresHp;

    /**
     * Constructor for consumable items.
     *
     * @param name Item name (e.g., "Potion")
     * @param restoreAmount Amount of HP or MP restored
     * @param quantity Initial amount in inventory
     * @param restoresHp True if it heals HP, False if it restores MP
     */
    public Item(String name, int restoreAmount, int quantity, boolean restoresHp) {
        this.name = name;
        this.restoreAmount = restoreAmount;
        this.quantity = quantity;
        this.restoresHp = restoresHp;
    }

   
    public void decreaseQuantity() {
        if (this.quantity > 0) {
            this.quantity--;
        }
    }

    public String getName() {
        return name;
    }

    public int getRestoreAmount() {
        return restoreAmount;
    }

    public int getQuantity() {
        return quantity;
    }

    public boolean isRestoresHp() {
        return restoresHp;
    }
}
