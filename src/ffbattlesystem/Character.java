/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ffbattlesystem;

import java.util.ArrayList;

/**
 * Core entity representing a fighter in the battle system. Manages stats,
 * inventory, skills, and active status effects.
 */
public class Character {

    // --- Attributes ---
    private String name;
    private int maxHp;
    private int currentHp;
    private int maxMp;
    private int currentMp;
    private int attack;
    private int defense;
    private int speed;

    // --- Data Structures ---
    private ArrayList<Item> items;
    private ArrayList<Skill> skills;
    private ArrayList<StatusEffect> activeStatuses;

    /**
     * Standard constructor to create a new Character.
     *
     * @param name Character's name
     * @param maxHp Maximum Health Points
     * @param maxMp Maximum Mana Points
     * @param attack Base attack power
     * @param defense Base defense (reduces incoming damage)
     * @param speed Determines turn order (for future implementations)
     */
    public Character(String name, int maxHp, int maxMp, int attack, int defense, int speed) {
        this.name = name;
        this.maxHp = maxHp;
        this.currentHp = maxHp; // Starts with full HP
        this.maxMp = maxMp;
        this.currentMp = maxMp; // Starts with full MP
        this.attack = attack;
        this.defense = defense;
        this.speed = speed;

        // Initialize lists to avoid NullPointerExceptions
        this.items = new ArrayList<>();
        this.skills = new ArrayList<>();
        this.activeStatuses = new ArrayList<>();
    }

    // ==========================================
    //           BATTLE MECHANICS
    // ==========================================
    /**
     * Performs a basic physical attack against a target. Damage is calculated
     * based on attacker's attack and target's defense.
     */
    public String attack(Character target) {
        // Calculate basic damage (Minimum 1 damage)
        int damageDealt = this.attack - target.getDefense();
        if (damageDealt < 1) {
            damageDealt = 1;
        }

        String log = this.name + " attacks " + target.getName() + "!\n";
        log += target.takeDamage(damageDealt);
        return log;
    }

    /**
     * Reduces the character's HP by a specific amount.
     */
    public String takeDamage(int damage) {
        this.currentHp -= damage;
        if (this.currentHp < 0) {
            this.currentHp = 0; // Prevent negative HP
        }
        return this.name + " takes " + damage + " damage!";
    }

    /**
     * Restores the character's HP without exceeding the maximum limit.
     */
    public String healing(int healAmount) {
        this.currentHp += healAmount;
        if (this.currentHp > this.maxHp) {
            this.currentHp = this.maxHp; // Prevent overheal
        }
        return this.name + " recovers " + healAmount + " HP!";
    }

    /**
     * Executes a skill against a target character.
     */
    public String useSkill(Character target, int skillIndex) {
        Skill selectedSkill = this.skills.get(skillIndex);

        if (this.currentMp < selectedSkill.getMpCost()) {
            return "Not enough MP to cast this spell!";
        }

        this.currentMp -= selectedSkill.getMpCost();
        String actionLog = this.name + " uses " + selectedSkill.getName() + "!\n";

        // If it's White Magic, heal. Otherwise, deal damage.
        if (selectedSkill.getType() == SkillType.WHITE_MAGIC) {
            actionLog += this.healing(selectedSkill.getDamage());
            if (selectedSkill.getName().equalsIgnoreCase("Esna")) {
                if (!this.activeStatuses.isEmpty()) {
                    this.clearAllStatuses();
                    actionLog += "\n" + this.name + " is cured of all status effects!";
                } else {
                    actionLog += "\nIt had no effect.";
                }
            }
        } else {
            actionLog += target.takeDamage(selectedSkill.getDamage());
        }

        if (selectedSkill.getEffectToApply() != null) {
            StatusEffect cloneEffect = new StatusEffect(selectedSkill.getEffectToApply());
            target.addStatusEffect(cloneEffect);
            actionLog += "\n>> " + target.getName() + " has been afflicted with [" + cloneEffect.getName() + "]! <<";
        }

        return actionLog;
    }

    /**
     * Uses a consumable item from the inventory.
     */
    public String useItem(int itemIndex) {
        Item selectedItem = this.items.get(itemIndex);

        if (selectedItem.getQuantity() <= 0) {
            return "You don't have any " + selectedItem.getName() + " left!";
        }

        selectedItem.decreaseQuantity();
        String log = this.name + " uses a " + selectedItem.getName() + "!\n";

        if (selectedItem.getName().equalsIgnoreCase("Antidote")) {
            if (this.removeStatusEffect("Poison")) {
                log += this.name + " is cured of Poison!";
            } else {
                log += "It had no effect.";
            }
        } else if (selectedItem.getName().equalsIgnoreCase("Alarm Clock")) {
            if (this.removeStatusEffect("Sleep")) {
                log += this.name + " is cured of Sleep!";
            } else {
                log += "It had no effect.";
            }
        } else if (selectedItem.getName().equalsIgnoreCase("Remedy")) {
            if (!this.activeStatuses.isEmpty()) {
                this.clearAllStatuses();
                log += this.name + " is cured of all status effects!";
            } else {
                log += "It had no effect.";
            }
        } else if (selectedItem.isRestoresHp()) {
            log += this.healing(selectedItem.getRestoreAmount());
        } else {
            // Restore MP
            this.currentMp += selectedItem.getRestoreAmount();
            if (this.currentMp > this.maxMp) {
                this.currentMp = this.maxMp;
            }
            log += this.name + " recovers " + selectedItem.getRestoreAmount() + " MP!";
        }

        return log;
    }

    // ==========================================
    //           STATUS EFFECT LOGIC
    // ==========================================
    /**
     * Processes active status effects that trigger at the start of the turn
     * (e.g., periodic damage from Poison).
     *
     * @return A detailed battle log string describing what happened.
     */
    public String processStatuses() {
        String log = "";

        for (int i = this.activeStatuses.size() - 1; i >= 0; i--) {
            StatusEffect effect = this.activeStatuses.get(i);

            // 1. Apply periodic damage (e.g., Poison)
            if (effect.getDamage() > 0) {
                this.currentHp -= effect.getDamage();
                if (this.currentHp < 0) {
                    this.currentHp = 0;
                }

                // Format: "Cloud suffers 15 damage from [Poison]! (2 turns remaining)"
                log += this.name + " suffers " + effect.getDamage() + " damage from [" + effect.getName() + "]! (" + effect.getTime() + " turns remaining)\n";
            }
        }
        return log;
    }

    /**
     * Decreases the duration of all active status effects by 1 turn and removes
     * any that have expired. This should be called at the end of the turn.
     *
     * @return A detailed battle log string describing which effects expired.
     */
    public String decayStatuses() {
        String log = "";
        for (int i = this.activeStatuses.size() - 1; i >= 0; i--) {
            StatusEffect effect = this.activeStatuses.get(i);
            effect.decreaseTime();
            if (effect.getTime() <= 0) {
                log += this.name + " has completely recovered from [" + effect.getName() + "]!\n";
                this.activeStatuses.remove(i);
            }
        }
        return log;
    }

    /**
     * Removes a specific status effect by name.
     *
     * @param statusName The name of the effect to remove.
     * @return true if the effect was found and removed, false otherwise.
     */
    public boolean removeStatusEffect(String statusName) {
        boolean removed = false;
        for (int i = this.activeStatuses.size() - 1; i >= 0; i--) {
            if (this.activeStatuses.get(i).getName().equalsIgnoreCase(statusName)) {
                this.activeStatuses.remove(i);
                removed = true;
            }
        }
        return removed;
    }

    /**
     * Clears all active status effects.
     */
    public void clearAllStatuses() {
        this.activeStatuses.clear();
    }

    /**
     * Checks if the character is prevented from moving due to a status effect.
     *
     * @return true if the character can act, false if they lose their turn.
     */
    public boolean canAct() {
        for (StatusEffect effect : this.activeStatuses) {
            if (effect.isLosesTurn()) {
                return false;
            }
        }
        return true;
    }

    public void addStatusEffect(StatusEffect newEffect) {
        this.activeStatuses.add(newEffect);
    }

    // ==========================================
    //           UTILITIES & GETTERS
    // ==========================================
    public boolean isAlive() {
        return this.currentHp > 0;
    }

    public void learnSkill(Skill skill) {
        this.skills.add(skill);
    }

    public void addItem(Item item) {
        this.items.add(item);
    }

    public String getName() {
        return name;
    }

    public int getMaxHp() {
        return maxHp;
    }

    public int getCurrentHp() {
        return currentHp;
    }

    public int getMaxMp() {
        return maxMp;
    }

    public int getCurrentMp() {
        return currentMp;
    }

    public int getAttack() {
        return attack;
    }

    public int getDefense() {
        return defense;
    }

    public int getSpeed() {
        return speed;
    }

    public ArrayList<Item> getItems() {
        return items;
    }

    public ArrayList<Skill> getSkills() {
        return skills;
    }
}
