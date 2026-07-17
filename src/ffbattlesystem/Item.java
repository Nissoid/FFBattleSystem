/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ffbattlesystem;

/**
 * Represents a consumable object in the game inventory.
 *
 * @author Dani
 */
public class Item {

    private String name;
    private int power;
    private int quantity;
    private boolean restoresHP;

    public Item(String name, int power, int quantity, boolean restoresHP) {
        this.name = name;
        this.power = power;
        this.quantity = quantity;
        this.restoresHP = restoresHP;
    }

    /**
     * Reduces the item quantity by one when used.
     */
    public void consume() {
        if (this.quantity > 0) {
            this.quantity--;
        }
    }

    // --- GETTERS & SETTERS ---
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPower() {
        return power;
    }

    public void setPower(int power) {
        this.power = power;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public boolean isRestoresHP() {
        return restoresHP;
    }

    public void setRestoresHP(boolean restoresHP) {
        this.restoresHP = restoresHP;
    }
}
