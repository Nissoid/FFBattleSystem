/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ffbattlesystem;

/**
 * Represents a magical spell or physical ability used in battle.
 */
public class Skill {

    private String name;
    private int damage;
    private int mpCost;
    private SkillType type; // NEW: Replaces boolean isHealing
    private StatusEffect effectToApply;

    public Skill(String name, int damage, int mpCost, SkillType type) {
        this.name = name;
        this.damage = damage;
        this.mpCost = mpCost;
        this.type = type;
        this.effectToApply = null;
    }

    public Skill(String name, int damage, int mpCost, SkillType type, StatusEffect effectToApply) {
        this.name = name;
        this.damage = damage;
        this.mpCost = mpCost;
        this.type = type;
        this.effectToApply = effectToApply;
    }

    // --- Getters ---
    public String getName() {
        return name;
    }

    public int getDamage() {
        return damage;
    }

    public int getMpCost() {
        return mpCost;
    }

    public SkillType getType() {
        return type;
    }

    public StatusEffect getEffectToApply() {
        return effectToApply;
    }
}
