/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ffbattlesystem;

/**
 * Represents a special move or magic spell in the game. Acts as a simple data
 * container for ability stats.
 *
 * @author Dani
 */
public class Skill {

    private String name;
    private int damage;      // Used for both damage dealt OR health restored
    private int mpCost;      // The magic points required to cast this skill
    private boolean isHealing; // True if it heals the user, false if it damages the enemy

    public Skill(String name, int damage, int mpCost, boolean isHealing) {
        this.name = name;
        this.damage = damage;
        this.mpCost = mpCost;
        this.isHealing = isHealing;
    }

    // --- GETTERS & SETTERS ---
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

    public int getMpCost() {
        return mpCost;
    }

    public void setMpCost(int mpCost) {
        this.mpCost = mpCost;
    }

    public boolean isIsHealing() {
        return isHealing;
    }

    public void setIsHealing(boolean isHealing) {
        this.isHealing = isHealing;
    }
}
