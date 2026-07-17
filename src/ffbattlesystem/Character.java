/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ffbattlesystem;

import java.util.ArrayList;

/**
 * The core blueprint for any combatant in the game. Pure domain entity: handles
 * stats, health management, and combat math. It is completely decoupled from
 * the console UI.
 *
 * @author Dani
 */
public class Character {

    private ArrayList<Skill> skills;
    private ArrayList<Item> items;
    private String name;
    private int maxHp, currentHp, maxMp, currentMp;
    private int attackPower, defense, speed;

    /**
     * Initializes a new character with full HP and MP, and empty
     * inventory/skill lists.
     */
    public Character(String name, int maxHp, int maxMp, int attackPower, int defense, int speed) {
        this.name = name;
        this.maxHp = maxHp;
        this.currentHp = maxHp;
        this.maxMp = maxMp;
        this.currentMp = maxMp;
        this.attackPower = attackPower;
        this.defense = defense;
        this.speed = speed;

        // Prevent NullPointerExceptions by initializing empty collections
        this.skills = new ArrayList<>();
        this.items = new ArrayList<>();
    }

    // --- BEHAVIOR METHODS ---
    public void learnSkill(Skill newSkill) {
        this.skills.add(newSkill);
    }

    public void addItem(Item newItem) {
        this.items.add(newItem);
    }

    /**
     * Reduces HP based on incoming damage, ensuring it doesn't drop below zero.
     *
     * @param damage The amount of damage received.
     * @return A log message describing the outcome.
     */
    public String takeDamage(int damage) {
        this.currentHp = this.currentHp - damage;
        if (this.currentHp < 0) {
            this.currentHp = 0;
        }
        return this.name + " takes " + damage + " damage! Remaining HP: " + this.currentHp;
    }

    /**
     * Restores HP ensuring it does not exceed the character's maximum HP.
     *
     * @param healingAmount The amount of HP to restore.
     * @return A log message describing the outcome.
     */
    public String healing(int healingAmount) {
        if (this.currentHp + healingAmount > this.maxHp) {
            this.currentHp = this.maxHp;
        } else {
            this.currentHp = this.currentHp + healingAmount;
        }
        return this.name + " feels revitalized! Current HP: " + this.currentHp;
    }

    /**
     * Consumes an item from the inventory and applies its effects (HP or MP
     * restoration).
     *
     * @param itemIndex The index of the item in the inventory list.
     * @return A log message detailing the item usage result.
     */
    public String useItem(int itemIndex) {
        if (itemIndex < 0 || itemIndex >= this.items.size()) {
            return "Invalid item selection.";
        }

        Item chosenItem = this.items.get(itemIndex);

        if (chosenItem.getQuantity() <= 0) {
            return "You don't have any " + chosenItem.getName() + " left!";
        }

        String resultMessage = "";

        if (chosenItem.isRestoresHP()) {
            resultMessage = this.healing(chosenItem.getPower());
        } else {
            if (this.currentMp + chosenItem.getPower() > this.maxMp) {
                this.currentMp = this.maxMp;
            } else {
                this.currentMp = this.currentMp + chosenItem.getPower();
            }
            resultMessage = this.name + " recovers " + chosenItem.getPower() + " MP!";
        }

        chosenItem.consume();
        return resultMessage;
    }

    /**
     * Performs a standard physical attack against a target.
     *
     * @param target The character receiving the attack.
     * @return A combined log message of the attack and the resulting damage.
     */
    public String attack(Character target) {
        int damage = this.attackPower - target.getDefense();

        if (damage > 0) {
            String attackLog = this.name + " attacks " + target.getName() + " and deals " + damage + " damage.\n";
            String targetLog = target.takeDamage(damage);
            return attackLog + targetLog;
        } else {
            return this.name + " attacks " + target.getName() + " but their armor is too thick! No damage taken.";
        }
    }

    /**
     * Casts a skill, spending MP, and applies its effect (damage or healing).
     *
     * @param target The character receiving the skill effect (if hostile).
     * @param skillIndex The index of the skill in the character's spellbook.
     * @return A log message detailing the action.
     */
    public String useSkill(Character target, int skillIndex) {
        Skill selectedSkill = this.skills.get(skillIndex);

        if (this.currentMp < selectedSkill.getMpCost()) {
            return "Not enough MP to cast this spell!";
        }

        this.currentMp = this.currentMp - selectedSkill.getMpCost();
        String actionLog = this.name + " casts " + selectedSkill.getName() + "!\n";

        if (selectedSkill.isIsHealing()) {
            actionLog += this.healing(selectedSkill.getDamage());
        } else {
            actionLog += target.takeDamage(selectedSkill.getDamage());
        }

        return actionLog;
    }

    /**
     * Checks if the character is still capable of fighting.
     *
     * @return True if HP is greater than 0, false otherwise.
     */
    public boolean isAlive() {
        return this.currentHp > 0;
    }

    // --- GETTERS & SETTERS ---
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getMaxHp() {
        return maxHp;
    }

    public void setMaxHp(int maxHp) {
        this.maxHp = maxHp;
    }

    public int getCurrentHp() {
        return currentHp;
    }

    public void setCurrentHp(int currentHp) {
        this.currentHp = currentHp;
    }

    public int getMaxMp() {
        return maxMp;
    }

    public void setMaxMp(int maxMp) {
        this.maxMp = maxMp;
    }

    public int getCurrentMp() {
        return currentMp;
    }

    public void setCurrentMp(int currentMp) {
        this.currentMp = currentMp;
    }

    public int getAttackPower() {
        return attackPower;
    }

    public void setAttackPower(int attackPower) {
        this.attackPower = attackPower;
    }

    public int getDefense() {
        return defense;
    }

    public void setDefense(int defense) {
        this.defense = defense;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public ArrayList<Skill> getSkills() {
        return skills;
    }

    public void setSkills(ArrayList<Skill> skills) {
        this.skills = skills;
    }

    public ArrayList<Item> getItems() {
        return items;
    }

    public void setItems(ArrayList<Item> items) {
        this.items = items;
    }
}
