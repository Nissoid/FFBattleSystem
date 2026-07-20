/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ffbattlesystem;

/**
 * Represents a temporary condition that affects a Character during battle. It
 * can deal damage over time (e.g., Poison) or prevent actions (e.g., Sleep,
 * Paralyze).
 */
public class StatusEffect {

    private String name;
    private int damage;
    private int time; // Turns remaining
    private boolean losesTurn;

    /**
     * Standard Constructor.
     *
     * @param name The name of the effect (e.g., "Poison")
     * @param damage Damage dealt per turn (0 if it only disables the character)
     * @param time How many turns the effect lasts
     * @param losesTurn True if the character skips their turn while afflicted
     */
    public StatusEffect(String name, int damage, int time, boolean losesTurn) {
        this.name = name;
        this.damage = damage;
        this.time = time;
        this.losesTurn = losesTurn;
    }

    /**
     * Copy Constructor (Prototype Pattern). Used to create a fresh clone of the
     * effect when applying it to a target.
     *
     * @param original The original StatusEffect to clone
     */
    public StatusEffect(StatusEffect original) {
        this.name = original.name;
        this.damage = original.damage;
        this.time = original.time;
        this.losesTurn = original.losesTurn;
    }

    /**
     * Decreases the remaining time of the effect by 1 turn.
     */
    public void decreaseTime() {
        if (this.time > 0) {
            this.time--;
        }
    }

    // --- Getters & Setters ---
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public boolean isLosesTurn() {
        return losesTurn;
    }

    public void setLosesTurn(boolean losesTurn) {
        this.losesTurn = losesTurn;
    }
}
